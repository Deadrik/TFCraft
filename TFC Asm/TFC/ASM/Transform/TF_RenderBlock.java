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
import TFC.ASM.ClassTransformer.InstrSet;
import TFC.ASM.ClassTransformer.OperationType;

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

		nodes.add(new InstrSet(new LdcInsnNode(16.0D), 17, OperationType.InsertAfter));
		nodes.add(new InstrSet(new InsnNode(Opcodes.DSUB), 21, OperationType.InsertAfter));

		nodes.add(new InstrSet(new LdcInsnNode(16.0D), 27, OperationType.InsertAfter));
		nodes.add(new InstrSet(new InsnNode(Opcodes.DSUB), 31, OperationType.InsertAfter));

		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 10), 430, OperationType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 14), 431, OperationType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 18), 455, OperationType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 22), 456, OperationType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 12), 480, OperationType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 16), 481, OperationType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 20), 505, OperationType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 24), 506, OperationType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 10), 518, OperationType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 14), 519, OperationType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 18), 527, OperationType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 22), 528, OperationType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 12), 536, OperationType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 16), 537, OperationType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 20), 545, OperationType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 24), 546, OperationType.Replace));

		this.mcpMethodNodes.put("renderFaceZNeg | (Lnet/minecraft/block/Block;DDDLnet/minecraft/util/Icon;)V", nodes);
		this.obfMethodNodes.put("func_78611_c | (Laqw;DDDLmr;)V", nodes);

		/**
		 * renderFaceXPos
		 */
		nodes = new ArrayList<InstrSet>();

		nodes.add(new InstrSet(new LdcInsnNode(16.0D), 17, OperationType.InsertAfter));
		nodes.add(new InstrSet(new InsnNode(Opcodes.DSUB), 21, OperationType.InsertAfter));
		nodes.add(new InstrSet(new LdcInsnNode(16.0D), 27, OperationType.InsertAfter));
		nodes.add(new InstrSet(new InsnNode(Opcodes.DSUB), 31, OperationType.InsertAfter));

		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 30), 428, OperationType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 18), 430, OperationType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 22), 431, OperationType.Replace));

		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 34), 454, OperationType.Replace));

		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 28), 478, OperationType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 20), 480, OperationType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 24), 481, OperationType.Replace));

		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 32), 504, OperationType.Replace));

		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 30), 516, OperationType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 18), 518, OperationType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 22), 519, OperationType.Replace));

		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 34), 526, OperationType.Replace));

		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 28), 534, OperationType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 20), 536, OperationType.Replace));
		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 24), 537, OperationType.Replace));

		nodes.add(new InstrSet(new VarInsnNode(Opcodes.DLOAD, 32), 544, OperationType.Replace));

		this.mcpMethodNodes.put("renderFaceXPos | (Lnet/minecraft/block/Block;DDDLnet/minecraft/util/Icon;)V", nodes);
		this.obfMethodNodes.put("func_78605_f | (Laqw;DDDLmr;)V", nodes);

	}
}