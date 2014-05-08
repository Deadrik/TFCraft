package TFC.ASM.Transform;

/*
 * Invaluable help from AtomicStryker's MultiMine coremod code <3
 */

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

import TFC.ASM.ClassTransformer;

public class TF_EntityLeashKnot extends ClassTransformer
{

	public TF_EntityLeashKnot()
	{
		mcpClassName = "net.minecraft.entity.EntityLeashKnot";
		obfClassName = "oe";

		List nodes = new ArrayList<InstrSet>();
		InsnList list = new InsnList();
		list.add(new VarInsnNode(Opcodes.ILOAD, 1));
		list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "TFC/ServerOverrides","isValidSurface","(I)Z"));
		list.add(new InsnNode(Opcodes.IRETURN));
		nodes.add(new InstrSet(list, 13, InstrOpType.InsertAfter));

		mcpMethodNodes.put("onValidSurface | ()Z", new Patch(nodes, PatchOpType.Modify));
		obfMethodNodes.put("c | ()Z", new Patch(nodes, PatchOpType.Modify));
	}
}