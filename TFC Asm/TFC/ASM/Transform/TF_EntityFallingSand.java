package TFC.ASM.Transform;

/*
 * Invaluable help from AtomicStryker's MultiMine coremod code <3
 */

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;

import TFC.ASM.ClassTransformer;

public class TF_EntityFallingSand extends ClassTransformer
{

	public TF_EntityFallingSand()
	{
		mcpClassName = "net.minecraft.entity.item.EntityFallingSand";
		obfClassName = "sq";

		List nodes = new ArrayList<InstrSet>();
		nodes.add(new InstrSet(new IntInsnNode(Opcodes.SIPUSH, 2000), 13, OperationType.Replace));
		nodes.add(new InstrSet(new LdcInsnNode(100.0F), 18, OperationType.Replace));

		this.mcpMethodNodes.put("<init> | (Lnet/minecraft/world/World;DDDII)V", nodes);
		this.obfMethodNodes.put("<init> | (Labv;DDDLI;)V", nodes);

		nodes = new ArrayList<InstrSet>();
		nodes.add(new InstrSet(new IntInsnNode(Opcodes.SIPUSH, 2000), 13, OperationType.Replace));
		nodes.add(new InstrSet(new LdcInsnNode(100.0F), 18, OperationType.Replace));
		this.mcpMethodNodes.put("<init> | (Lnet/minecraft/world/World;)V", nodes);
		this.obfMethodNodes.put("<init> | (Labv;)V", nodes);

	}
}