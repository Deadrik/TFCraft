package com.bioxx.tfc.ASM.Transform;

/**
 * 
 * We don't actually use this file anymore, however it is being left here to demonstrate how to 
 * replace instructions that replace fields in an init method.
 * 
 */

import java.util.ArrayList;
import java.util.List;

import com.bioxx.tfc.ASM.ClassTransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;

public class TF_EntityFallingBlock extends ClassTransformer
{

	public TF_EntityFallingBlock()
	{
		mcpClassName = "net.minecraft.entity.item.EntityFallingBlock";
		obfClassName = "vv";

		List<InstrSet> nodes = new ArrayList<InstrSet>();
		nodes.add(new InstrSet(new IntInsnNode(Opcodes.SIPUSH, 2000), 13, InstrOpType.Replace));
		nodes.add(new InstrSet(new LdcInsnNode(100.0F), 18, InstrOpType.Replace));
		Patch patch = new Patch(nodes);
		this.mcpMethodNodes.put("<init> | (Lnet/minecraft/world/World;DDDLnet/minecraft/block/Block;I)V", patch);
		this.obfMethodNodes.put("<init> | (Lafn;DDDILahu;I)V", patch);

		nodes = new ArrayList<InstrSet>();
		nodes.add(new InstrSet(new IntInsnNode(Opcodes.SIPUSH, 2000), 13, InstrOpType.Replace));
		nodes.add(new InstrSet(new LdcInsnNode(100.0F), 18, InstrOpType.Replace));
		patch = new Patch(nodes);
		this.mcpMethodNodes.put("<init> | (Lnet/minecraft/world/World;)V", patch);
		this.obfMethodNodes.put("<init> | (Lafn;)V", patch);

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