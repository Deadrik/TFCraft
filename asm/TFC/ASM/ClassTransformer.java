package TFC.ASM;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;

import TFC.TFCASMLoadingPlugin;
import cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;

public class ClassTransformer implements net.minecraft.launchwrapper.IClassTransformer
{
	protected HashMap<String, List<InstrSet>> mcpMethodNodes = new HashMap<String, List<InstrSet>>();
	protected HashMap<String, List<InstrSet>> obfMethodNodes = new HashMap<String, List<InstrSet>>();
	protected String mcpClassName;
	protected String obfClassName;


	@Override
	public byte[] transform(String name, String transformedName, byte[] bytes)
	{
		// System.out.println("transforming: " + name);
		if (name.equals(obfClassName))
		{
			return transform(bytes);
		}
		else if (name.equals(mcpClassName))
		{
			return transform(bytes);
		}

		return bytes;
	}

	static int numInsertions = 0;
	private byte[] transform(byte[] bytes)
	{
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		System.out.println("Attempting to Transform: "+ classNode.name + " | Found " + getMethodNodeList().size() + " injections");
		// find method to inject into
		Iterator<MethodNode> methods = classNode.methods.iterator();
		while (methods.hasNext())
		{
			MethodNode m = methods.next();
			if (getMethodNodeList().containsKey(m.name + " | " + m.desc))
			{
				numInsertions = 0;
				List<InstrSet> instructions = (List<InstrSet>) getMethodNodeList().get(m.name + " | " + m.desc);
				InstrSet target = null;
				if(instructions.size() > 0) {
					target = instructions.get(0);
				} else {
					System.out.println("Error in: {"+m.name + " | " + m.desc+"} No Instructions");
				}

				for (int index = 0; index < m.instructions.size() && instructions.size() > 0; index++)
				{
					numInsertions = 0;
					while(target != null)
					{
						if(target.startLine == -1) {
							performDirectOperation(m.instructions, target);
							instructions.remove(0);
						} else if(this.isLineNumber(m.instructions.get(index), target.startLine)) {
							performAnchorOperation(m.instructions, target, index);
							instructions.remove(0);
						}
						else
						{
							break;
						}


						if(instructions.size() > 0) 
						{
							target = instructions.get(0);
						} 
						else 
						{
							target = null;
						}
					}
				}
				m.visitMaxs(m.maxStack+numInsertions, m.maxLocals);
				System.out.println("Inserted: "+ classNode.name +" : {"+m.name + " | " + m.desc+"}");
			}
		}
		System.out.println("Attempting to Transform: "+ classNode.name + " Complete");
		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	private int findLine(InsnList methodList, int line)
	{
		for (int index = 0; index < methodList.size(); index++)
		{
			if(this.isLineNumber(methodList.get(index), line))
			{
				return index;
			}
		}
		return -1;
	}

	private void performDirectOperation(InsnList methodInsn, InstrSet input)
	{
		AbstractInsnNode _current = methodInsn.get(input.offset+numInsertions);

		switch(input.opType)
		{
		case InsertAfter:
			numInsertions+=input.iList.size();
			methodInsn.insert(_current, input.iList);
			break;
		case InsertBefore:
			numInsertions+=input.iList.size();
			methodInsn.insertBefore(_current, input.iList);
			break;
		case Remove:
			numInsertions--;
			methodInsn.remove(_current);
			break;
		case Replace:
			methodInsn.insert(_current, input.iList);
			methodInsn.remove(_current);
			break;
		default:
			break;
		}
	}

	private void performAnchorOperation(InsnList methodInsn, InstrSet input, int anchor)
	{
		AbstractInsnNode _current = methodInsn.get(anchor + input.offset + numInsertions);
		switch(input.opType)
		{
		case InsertAfter:
			numInsertions+=input.iList.size();
			methodInsn.insert(_current, input.iList);
			break;
		case InsertBefore:
			numInsertions+=input.iList.size();
			methodInsn.insertBefore(_current, input.iList);
			break;
		case Remove:
			numInsertions--;
			methodInsn.remove(_current);
			break;
		case Replace:
			methodInsn.insert(_current, input.iList);
			methodInsn.remove(_current);
			break;
		case Switch:
			/**
			 * Current is the first node. We will remove it and insert after the chosen index
			 * and then move back one, grab that node and move it to the original location
			 * */
			int otherAnchor = findLine(methodInsn, input.offsetLine);
			AbstractInsnNode _other = methodInsn.get(otherAnchor + input.offsetSwitch + numInsertions);
			methodInsn.remove(_current);
			methodInsn.insert(_other, _current);
			_current = methodInsn.get(anchor + input.offset + numInsertions);
			methodInsn.remove(_other);
			methodInsn.insertBefore(_current, _other);
			break;
		default:
			break;
		}
	}

	private HashMap getMethodNodeList()
	{
		if(TFCASMLoadingPlugin.runtimeDeobf)
		{
			return obfMethodNodes;
		}
		return mcpMethodNodes;
	}

	private boolean isLineNumber(AbstractInsnNode current, int line)
	{
		if(current instanceof LineNumberNode)
		{
			int l = ((LineNumberNode)current).line;
			if(l == line)
			{
				return true;
			}
		}
		return false;
	}

	private boolean CompareNodes(AbstractInsnNode current,AbstractInsnNode target){
		if(current.getType() != target.getType()) {
			return false;
		}
		if(current.getOpcode() != target.getOpcode()) {
			return false;
		}
		switch(current.getType()){
		case AbstractInsnNode.FIELD_INSN:{
			FieldInsnNode na=(FieldInsnNode)deobf(current);
			FieldInsnNode nb=(FieldInsnNode)deobf(target);
			return na.owner.equals(nb.owner) && na.name.equals(nb.name) && na.desc.equals(nb.desc);
		}
		case AbstractInsnNode.METHOD_INSN:{
			MethodInsnNode na=(MethodInsnNode)deobf(current);
			MethodInsnNode nb=(MethodInsnNode)deobf(target);
			return na.owner.equals(nb.owner) && na.name.equals(nb.name) && na.desc.equals(nb.desc);
		}
		case AbstractInsnNode.TYPE_INSN:{
			TypeInsnNode na=(TypeInsnNode)deobf(current);
			TypeInsnNode nb=(TypeInsnNode)deobf(target);
			return na.desc.equals(nb.desc);
		}
		}
		return false;
	}

	private AbstractInsnNode deobf(AbstractInsnNode obf){
		/*boolean needDeobf=tfc_carpentersblocks_adapter.coremod.TFC_CarpBlock_IFMLLoadingPlugin.runtimeDeobf;
		if(!needDeobf){
			return obf;
		}*/
		FMLDeobfuscatingRemapper mapper=FMLDeobfuscatingRemapper.INSTANCE;
		String owner,name,desc;
		switch(obf.getType()){
		case AbstractInsnNode.FIELD_INSN:{
			FieldInsnNode n=(FieldInsnNode)obf;
			owner=mapper.map(n.owner);
			name=mapper.mapFieldName(n.owner, n.name, n.desc);
			desc=mapper.mapDesc(n.desc);
			return new FieldInsnNode (n.getOpcode(),owner,name,desc);
		}
		case AbstractInsnNode.METHOD_INSN:{
			MethodInsnNode n=(MethodInsnNode)obf;
			owner=mapper.map(n.owner);
			name=mapper.mapMethodName(n.owner, n.name, n.desc);
			desc=mapper.mapMethodDesc(n.desc);
			return new MethodInsnNode(n.getOpcode(),owner,name,desc);
		}
		case AbstractInsnNode.TYPE_INSN:{
			TypeInsnNode n=(TypeInsnNode)obf;
			desc=mapper.mapDesc(n.desc);
			return new TypeInsnNode(n.getOpcode(),desc);
		}
		default:
			return obf;
		}
	}

	public class InstrSet
	{
		/**
		 * InsnList of instructions that should be inserted at the specified point
		 */
		InsnList iList;

		/**
		 * Insertion offset to from either the top of the file, or from the provided startLine
		 */
		int offset;

		/**
		 * The line number of the LineNumberNode to use as the starting offset, also known as the anchor point. 
		 * If this is -1 then the top of the method is used as the anchor point
		 */
		int startLine = -1;

		/**
		 * The type of operation that should be performed at the given offset
		 */
		OperationType opType;


		int offsetSwitch = -1;
		int offsetLine = -1;

		public InstrSet(InsnList list, int off, OperationType op)
		{
			iList = list;
			offset = off;
			opType = op;
		}
		public InstrSet(AbstractInsnNode node, int off, OperationType op)
		{
			iList = new InsnList();
			iList.add(node);
			offset = off;
			opType = op;
		}
		public InstrSet(InsnList list, int startLineNum, int off, OperationType op)
		{
			iList = list;
			startLine = startLineNum;
			offset = off;
			opType = op;
		}
		public InstrSet(AbstractInsnNode node, int startLineNum, int off, OperationType op)
		{
			iList = new InsnList();
			iList.add(node);
			startLine = startLineNum;
			offset = off;
			opType = op;
		}
		public InstrSet(int startLineNum, int off, int swLineNum, int swOffset)
		{
			startLine = startLineNum;
			offset = off;
			opType = OperationType.Switch;
			offsetSwitch = swOffset;
			offsetLine = swLineNum;
		}
	}

	public class MethodPatch
	{
		/**
		 * Name of the Method
		 */
		public String name;
		/**
		 * Method Signature
		 */
		public String desc;
		/**
		 * InstrSet Instance that should be used to modify the given method
		 */
		public InstrSet instructions;

		public MethodPatch(String methodName, InstrSet instr)
		{
			name = methodName;
			instructions = instr;
		}
	}

	public enum OperationType
	{
		InsertAfter,
		InsertBefore,
		Switch,
		Replace,
		Remove;
	}
}
