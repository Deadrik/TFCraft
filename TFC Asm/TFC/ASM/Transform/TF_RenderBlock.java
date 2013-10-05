package TFC.ASM.Transform;

/*
 * Invaluable help from AtomicStryker's MultiMine coremod code <3
 */

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

import TFC.ASM.ClassTransformer;

public class TF_RenderBlock extends ClassTransformer
{

	public TF_RenderBlock()
	{
		mcpClassName = "net.minecraft.client.renderer.RenderBlocks";
		obfClassName = "bfo";

		/**
		 * renderFaceZNeg
		 */
		List nodes = new ArrayList<InstrSet>();

		nodes.add(new InstrSet(new LdcInsnNode(16.0D), 7189, 2, OperationType.InsertAfter));
		nodes.add(new InstrSet(new InsnNode(Opcodes.DSUB), 7189, 6, OperationType.InsertAfter));

		nodes.add(new InstrSet(new LdcInsnNode(16.0D), 7190, 1, OperationType.InsertAfter));
		nodes.add(new InstrSet(new InsnNode(Opcodes.DSUB),7190, 5, OperationType.InsertAfter));

		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 10), 7267, 5, OperationType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 14), 7267, 6, OperationType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 18), 7270, 5, OperationType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 22), 7270, 6, OperationType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 12), 7273, 5, OperationType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 16), 7273, 6, OperationType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 20), 7276, 5, OperationType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 24), 7276, 6, OperationType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 10), 7280, 5, OperationType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 14), 7280, 6, OperationType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 18), 7281, 5, OperationType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 22), 7281, 6, OperationType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 12), 7282, 5, OperationType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 16), 7282, 6, OperationType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 20), 7283, 5, OperationType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 24), 7283, 6, OperationType.Replace));

		this.mcpMethodNodes.put("renderFaceZNeg | (Lnet/minecraft/block/Block;DDDLnet/minecraft/util/Icon;)V", nodes);
		this.obfMethodNodes.put("c | (Laqw;DDDLmr;)V", nodes);

		/**
		 * renderFaceXPos
		 */
		nodes = new ArrayList<InstrSet>();

		nodes.add(new InstrSet(new LdcInsnNode(16.0D), 7519, 2, OperationType.InsertAfter));
		nodes.add(new InstrSet(new InsnNode(Opcodes.DSUB), 7519, 6, OperationType.InsertAfter));
		nodes.add(new InstrSet(new LdcInsnNode(16.0D), 7520, 1, OperationType.InsertAfter));
		nodes.add(new InstrSet(new InsnNode(Opcodes.DSUB), 7520, 5, OperationType.InsertAfter));

		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 30), 7597, 3, OperationType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 18), 7597, 5, OperationType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 22), 7597, 6, OperationType.Replace));

		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 34), 7600, 3, OperationType.Replace));

		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 28), 7603, 3, OperationType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 20), 7603, 5, OperationType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 24), 7603, 6, OperationType.Replace));

		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 32), 7606, 3, OperationType.Replace));

		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 30), 7610, 3, OperationType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 18), 7610, 5, OperationType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 22), 7610, 6, OperationType.Replace));

		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 34), 7611, 3, OperationType.Replace));

		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 28), 7612, 3, OperationType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 20), 7612, 5, OperationType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 24), 7612, 6, OperationType.Replace));

		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 32), 7613, 3, OperationType.Replace));

		this.mcpMethodNodes.put("renderFaceXPos | (Lnet/minecraft/block/Block;DDDLnet/minecraft/util/Icon;)V", nodes);
		this.obfMethodNodes.put("f | (Laqw;DDDLmr;)V", nodes);

	}
}