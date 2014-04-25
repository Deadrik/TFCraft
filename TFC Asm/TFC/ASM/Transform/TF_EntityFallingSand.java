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
		nodes.add(new InstrSet(new IntInsnNode(Opcodes.SIPUSH, 2000), 13, InstrOpType.Replace));
		nodes.add(new InstrSet(new LdcInsnNode(100.0F), 18, InstrOpType.Replace));

		Patch patch = new Patch(nodes);
		this.mcpMethodNodes.put("<init> | (Lnet/minecraft/world/World;DDDII)V", patch);
		this.obfMethodNodes.put("<init> | (Labv;DDDII)V", patch);

		nodes = new ArrayList<InstrSet>();
		nodes.add(new InstrSet(new IntInsnNode(Opcodes.SIPUSH, 2000), 13, InstrOpType.Replace));
		nodes.add(new InstrSet(new LdcInsnNode(100.0F), 18, InstrOpType.Replace));
		patch = new Patch(nodes);
		this.mcpMethodNodes.put("<init> | (Lnet/minecraft/world/World;)V", patch);
		this.obfMethodNodes.put("<init> | (Labv;)V", patch);

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