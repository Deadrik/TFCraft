package com.bioxx.tfc.ASM.Transform;

/*
 * This is disabled in favor of the hacky RenderBlocksFixUV solution. This Transformer unfortunately 
 * affects every single block which causes shadow errors on normal blocks. For the detailed blocks, it's
 * a near perfect solution however. Eventually ti may be worth figuring out how to make this only affect 
 * partial blocks and not the solid blocks.
 */

import java.util.ArrayList;
import java.util.List;

import com.bioxx.tfc.ASM.ClassTransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

public class TF_RenderBlock extends ClassTransformer
{

	public TF_RenderBlock()
	{
		mcpClassName = "net.minecraft.client.renderer.RenderBlocks";
		obfClassName = "ble";

		/**
		 * renderFaceZNeg
		 */
		List<InstrSet> nodes = new ArrayList<InstrSet>();

		nodes.add(new InstrSet(new LdcInsnNode(16.0D), 7189, 2, InstrOpType.InsertAfter));
		nodes.add(new InstrSet(new InsnNode(Opcodes.DSUB), 7189, 6, InstrOpType.InsertAfter));

		nodes.add(new InstrSet(new LdcInsnNode(16.0D), 7190, 1, InstrOpType.InsertAfter));
		nodes.add(new InstrSet(new InsnNode(Opcodes.DSUB),7190, 5, InstrOpType.InsertAfter));

		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 10), 7267, 5, InstrOpType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 14), 7267, 6, InstrOpType.Replace));
		nodes.add(new InstrSet(7268, 3, 7274, 3));
		nodes.add(new InstrSet(7268, 5, 7274, 5));
		nodes.add(new InstrSet(7268, 7, 7274, 7));
		nodes.add(new InstrSet(7269, 3, 7275, 3));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 18), 7270, 5, InstrOpType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 22), 7270, 6, InstrOpType.Replace));

		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 12), 7273, 5, InstrOpType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 16), 7273, 6, InstrOpType.Replace));

		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 20), 7276, 5, InstrOpType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 24), 7276, 6, InstrOpType.Replace));

		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 10), 7280, 5, InstrOpType.Replace));

		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 14), 7280, 6, InstrOpType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 18), 7281, 5, InstrOpType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 22), 7281, 6, InstrOpType.Replace));

		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 12), 7282, 5, InstrOpType.Replace));

		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 16), 7282, 6, InstrOpType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 20), 7283, 5, InstrOpType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 24), 7283, 6, InstrOpType.Replace));

		this.mcpMethodNodes.put("renderFaceZNeg | (Lnet/minecraft/block/Block;DDDLnet/minecraft/util/IIcon;)V", new Patch(nodes));
		this.obfMethodNodes.put("c | (Lahu;DDDLps;)V", new Patch(nodes));

		/**
		 * renderFaceXPos
		 */
		nodes = new ArrayList<InstrSet>();

		nodes.add(new InstrSet(new LdcInsnNode(16.0D), 7519, 2, InstrOpType.InsertAfter));
		nodes.add(new InstrSet(new InsnNode(Opcodes.DSUB), 7519, 6, InstrOpType.InsertAfter));
		nodes.add(new InstrSet(new LdcInsnNode(16.0D), 7520, 1, InstrOpType.InsertAfter));
		nodes.add(new InstrSet(new InsnNode(Opcodes.DSUB), 7520, 5, InstrOpType.InsertAfter));

		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 30), 7597, 3, InstrOpType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 18), 7597, 5, InstrOpType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 22), 7597, 6, InstrOpType.Replace));
		nodes.add(new InstrSet(7598, 3, 7604, 3));
		nodes.add(new InstrSet(7598, 5, 7604, 5));
		nodes.add(new InstrSet(7598, 7, 7604, 7));
		nodes.add(new InstrSet(7599, 3, 7605, 3));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 34), 7600, 4, InstrOpType.Replace));

		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 28), 7603, 3, InstrOpType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 20), 7603, 5, InstrOpType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 24), 7603, 6, InstrOpType.Replace));

		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 32), 7606, 4, InstrOpType.Replace));

		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 30), 7610, 3, InstrOpType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 18), 7610, 5, InstrOpType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 22), 7610, 6, InstrOpType.Replace));

		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 34), 7611, 4, InstrOpType.Replace));

		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 28), 7612, 3, InstrOpType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 20), 7612, 5, InstrOpType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 24), 7612, 6, InstrOpType.Replace));

		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 32), 7613, 4, InstrOpType.Replace));

		this.mcpMethodNodes.put("renderFaceXPos | (Lnet/minecraft/block/Block;DDDLnet/minecraft/util/IIcon;)V", new Patch(nodes));
		this.obfMethodNodes.put("f | (Lahu;DDDLps;)V", new Patch(nodes));
	}
}