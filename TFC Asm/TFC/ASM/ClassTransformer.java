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

public abstract class ClassTransformer implements net.minecraft.launchwrapper.IClassTransformer
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
					while(target != null)
					{
						performOperation(m.instructions, target);

						instructions.remove(0);
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
			}
		}

		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	private void performOperation(InsnList mInNew, InstrSet input)
	{
		AbstractInsnNode _current = mInNew.get(input.insertionOffset+numInsertions);
		switch(input.opType)
		{
		case InsertAfter:
			numInsertions+=input.iList.size();
			mInNew.insert(_current, input.iList);
			break;
		case InsertBefore:
			numInsertions+=input.iList.size();
			mInNew.insertBefore(_current, input.iList);
			break;
		case Remove:
			numInsertions--;
			mInNew.remove(_current);
			break;
		case Replace:
			mInNew.insert(_current, input.iList);
			mInNew.remove(_current);
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
		if(current instanceof LineNumberNode && target instanceof LineNumberNode)
		{
			if(((LineNumberNode)current).line == ((LineNumberNode)target).line)
			{
				return true;
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
		InsnList iList;
		int insertionOffset;
		OperationType opType;

		public InstrSet(InsnList list, int offset, OperationType op)
		{
			iList = list;
			insertionOffset = offset;
			opType = op;
		}
		public InstrSet(AbstractInsnNode node, int insert, OperationType op)
		{
			iList = new InsnList();
			iList.add(node);
			insertionOffset = insert;
			opType = op;
		}
	}

	public class MethodPatch
	{
		public String name;
		public String desc;
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
		Replace,
		Remove;
	}
}
