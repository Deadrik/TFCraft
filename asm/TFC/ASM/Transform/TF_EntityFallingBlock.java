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

public class TF_EntityFallingBlock extends ClassTransformer
{

	public TF_EntityFallingBlock()
	{
		mcpClassName = "net.minecraft.entity.item.EntityFallingBlock";
		obfClassName = "vv";

		List nodes = new ArrayList<InstrSet>();
		nodes.add(new InstrSet(new IntInsnNode(Opcodes.SIPUSH, 2000), 13, OperationType.Replace));
		nodes.add(new InstrSet(new LdcInsnNode(100.0F), 18, OperationType.Replace));

		this.mcpMethodNodes.put("<init> | (Lnet/minecraft/world/World;DDDII)V", nodes);
		this.obfMethodNodes.put("<init> | (Labv;DDDII)V", nodes);

		nodes = new ArrayList<InstrSet>();
		nodes.add(new InstrSet(new IntInsnNode(Opcodes.SIPUSH, 2000), 13, OperationType.Replace));
		nodes.add(new InstrSet(new LdcInsnNode(100.0F), 18, OperationType.Replace));
		this.mcpMethodNodes.put("<init> | (Lnet/minecraft/world/World;)V", nodes);
		this.obfMethodNodes.put("<init> | (Labv;)V", nodes);

		/*nodes = new ArrayList<InstrSet>();
		InsnList list = new InsnList();
		list.add(new FieldInsnNode(Opcodes.GETSTATIC,"net/minecraft/block/Block", "blocksList", "[Lnet/minecraft/block/Block;"));
		list.add(new VarInsnNode(Opcodes.ALOAD, 0));
		list.add(new FieldInsnNode(Opcodes.GETFIELD,"net/minecraft/entity/item/EntityFallingSand", "blockID", "I"));
		list.add(new InsnNode(Opcodes.AALOAD));
		list.add(new Label());
		nodes.add(new InstrSet(list, 229, OperationType.InsertAfter));*/
	}
}