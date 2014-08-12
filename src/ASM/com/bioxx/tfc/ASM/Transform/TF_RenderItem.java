package com.bioxx.tfc.ASM.Transform;

/*
 * Invaluable help from AtomicStryker's MultiMine coremod code <3
 */

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

import com.bioxx.tfc.ASM.ClassTransformer;

public class TF_RenderItem extends ClassTransformer
{

	public TF_RenderItem()
	{
		mcpClassName = "net.minecraft.client.renderer.entity.RenderItem";
		obfClassName = "bnq";

		List<InstrSet> nodes = new ArrayList<InstrSet>();
		InsnList list = new InsnList();
		list.add(new LineNumberNode(581, new LabelNode()));
		list.add(new VarInsnNode(Opcodes.ILOAD, 4));
		list.add(new VarInsnNode(Opcodes.ILOAD, 5));
		list.add(new VarInsnNode(Opcodes.ALOAD, 3));
		list.add(new VarInsnNode(Opcodes.BIPUSH, 16));
		list.add(new VarInsnNode(Opcodes.BIPUSH, 16));
		list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "com/bioxx/tfc/ClientOverrides","renderIcon","(IILnet/minecraft/item/ItemStack;II)V"));
		nodes.add(new InstrSet(list, 581, InstrOpType.InsertBefore));

		mcpMethodNodes.put("renderItemIntoGUI | (Lnet/minecraft/client/gui/FontRenderer;Lnet/minecraft/client/renderer/texture/TextureManager;Lnet/minecraft/item/ItemStack;IIZ)V", new Patch(nodes, PatchOpType.Modify));
		obfMethodNodes.put("a | (Lbag;Lbpx;Labp;IIZ)V", new Patch(nodes, PatchOpType.Modify));
	}

}