package com.bioxx.tfc.ASM;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;

import com.bioxx.tfc.TFCASMLoadingPlugin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class ClassTransformer implements net.minecraft.launchwrapper.IClassTransformer
{
	public Logger log = LogManager.getLogger("TerraFirmaCraft ASM");
	protected HashMap<String, Patch> mcpMethodNodes = new HashMap<String, Patch>();
	protected HashMap<String, Patch> obfMethodNodes = new HashMap<String, Patch>();
	protected String mcpClassName;
	protected String obfClassName;


	@Override
	public byte[] transform(String name, String transformedName, byte[] bytes)
	{
		// log.info("transforming: " + name);
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
	protected byte[] transform(byte[] bytes)
	{
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		log.info("Attempting to Transform: " + classNode.name + " | Found " + getMethodNodeList().size() + " injections");
		// find method to inject into
		Iterator<MethodNode> methods = classNode.methods.iterator();
		while (methods.hasNext())
		{
			MethodNode m = methods.next();
			if (getMethodNodeList().containsKey(m.name + " | " + m.desc))
			{
				numInsertions = 0;
				Patch mPatch = getMethodNodeList().get(m.name + " | " + m.desc);
				List<InstrSet> instructions = mPatch.instructions;
				InstrSet target = null;
				if(instructions.size() > 0) {
					target = instructions.get(0);
				} else {
					log.error("Error in: {" + m.name + " | " + m.desc + "} No Instructions");
				}
				//Run this is we plan to just modify the method
				if(mPatch.opType == PatchOpType.Modify)
				{
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
				}
				else if(mPatch.opType == PatchOpType.Replace)
				{
					//InsnList old = new InsnList();
					if (target != null && target.offset != -1)
					{
						for (int index = 0; index < m.instructions.size();)
						{
							if(index > target.offset)
								m.instructions.remove(m.instructions.get(index));
							else index++;
						}

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
						m.instructions.add(new InsnNode(Opcodes.RETURN));
					}
				}
				log.info("Inserted: " + classNode.name + " : {" + m.name + " | " + m.desc + "}");
			}
		}
		log.info("Attempting to Transform: " + classNode.name + " Complete");
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

	private int findLabel(InsnList methodList, int line)
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

	private boolean isLabel(AbstractInsnNode current, int line)
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
			if(_current instanceof JumpInsnNode && input.iList.get(0) instanceof JumpInsnNode)
			{
				((JumpInsnNode)input.iList.get(0)).label = ((JumpInsnNode)_current).label;
			}
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
		if(input.iList.get(0) instanceof JumpInsnNode)
		{
			input.iList.set(input.iList.get(0), new JumpInsnNode(input.iList.get(0).getOpcode(), (LabelNode)_current.getPrevious()));
		}
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

	protected HashMap<String, Patch> getMethodNodeList()
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
		InstrOpType opType;


		int offsetSwitch = -1;
		int offsetLine = -1;

		public InstrSet(InsnList list, int off, InstrOpType op)
		{
			iList = list;
			offset = off;
			opType = op;
		}
		public InstrSet(AbstractInsnNode node, int off, InstrOpType op)
		{
			iList = new InsnList();
			iList.add(node);
			offset = off;
			opType = op;
		}
		public InstrSet(InsnList list, int startLineNum, int off, InstrOpType op)
		{
			iList = list;
			startLine = startLineNum;
			offset = off;
			opType = op;
		}
		public InstrSet(AbstractInsnNode node, int startLineNum, int off, InstrOpType op)
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
			opType = InstrOpType.Switch;
			offsetSwitch = swOffset;
			offsetLine = swLineNum;
		}
	}

	public class Patch
	{
		/**
		 * InstrSet Instance that should be used to modify the given method
		 */
		public List<InstrSet> instructions;
		public PatchOpType opType = PatchOpType.Modify;

		public Patch(List<InstrSet> set)
		{
			instructions = set;
		}

		public Patch(List<InstrSet> set, PatchOpType op)
		{
			instructions = set;
			opType = op;
		}
	}

	public enum PatchOpType
	{
		Modify,
		Replace,
	}

	public enum InstrOpType
	{
		InsertAfter,
		InsertBefore,
		Switch,
		Replace,
		Remove;
	}

	public class JumpNode extends JumpInsnNode
	{
		int line = 0;
		public JumpNode(int opcode, LabelNode label) {
			super(opcode, label);
		}

		public JumpNode(int opcode, int labelLine) 
		{
			super(opcode, null);
			line = labelLine;
		}

	}
}
