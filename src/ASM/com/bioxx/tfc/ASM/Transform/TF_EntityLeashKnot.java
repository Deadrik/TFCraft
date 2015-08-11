package com.bioxx.tfc.ASM.Transform;

/*
 * Invaluable help from AtomicStryker's MultiMine coremod code <3
 */

import java.util.ArrayList;
import java.util.List;

import com.bioxx.tfc.ASM.ClassTransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;

public class TF_EntityLeashKnot extends ClassTransformer
{

	@SuppressWarnings("deprecation")
	public TF_EntityLeashKnot()
	{
		mcpClassName = "net.minecraft.entity.EntityLeashKnot";
		obfClassName = "su";

		List<InstrSet> nodes = new ArrayList<InstrSet>();
		InsnList list = new InsnList();
		/*list.add(new VarInsnNode(Opcodes.ALOAD, 0));
		list.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/entity/EntityLeashKnot", "worldobj", "Lnet/minecraft/world/World;"));
		list.add(new VarInsnNode(Opcodes.ALOAD, 0));
		list.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/entity/EntityLeashKnot", "field_146063_b", "I"));
		list.add(new VarInsnNode(Opcodes.ALOAD, 0));
		list.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/entity/EntityLeashKnot", "field_146064_c", "I"));
		list.add(new VarInsnNode(Opcodes.ALOAD, 0));
		list.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/entity/EntityLeashKnot", "field_146062_d", "I"));
		list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraft/world/World","getBlock","(III)Z"));*/
		list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "com/bioxx/tfc/ServerOverrides","isValidSurface","(Lnet/minecraft/block/Block;)I"));
		//list.add(new InsnNode(Opcodes.IRETURN));
		nodes.add(new InstrSet(list, 11, InstrOpType.Replace));

		mcpMethodNodes.put("onValidSurface | ()Z", new Patch(nodes, PatchOpType.Modify));
		obfMethodNodes.put("e | ()Z", new Patch(nodes, PatchOpType.Modify));
	}
}