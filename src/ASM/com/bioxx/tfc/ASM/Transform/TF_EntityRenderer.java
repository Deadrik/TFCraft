package com.bioxx.tfc.ASM.Transform;

/*
 * Invaluable help from AtomicStryker's MultiMine coremod code <3
 */

import java.util.ArrayList;
import java.util.List;

import com.bioxx.tfc.ASM.ClassTransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

public class TF_EntityRenderer extends ClassTransformer
{

	public TF_EntityRenderer()
	{
		mcpClassName = "net.minecraft.client.renderer.EntityRenderer";
		obfClassName = "blt";

		List<InstrSet> nodes = new ArrayList<InstrSet>();
		InsnList list = new InsnList();
		//This version of the method patch replaces the contents of the vanilla method itself
		/*
		list.add(new LineNumberNode(1368, new LabelNode()));
		list.add(new VarInsnNode(Opcodes.ALOAD, 0));
		list.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/client/renderer/EntityRenderer", "random", "Ljava/util/Random;"));
		list.add(new VarInsnNode(Opcodes.ALOAD, 0));
		list.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/client/renderer/EntityRenderer", "rendererUpdateCount", "I"));
		list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "TFC/Core/WeatherManager","doRainClient","(Ljava/util/Random;I)V"));
		nodes.add(new InstrSet(list, 7, InstrOpType.InsertAfter));
		this.mcpMethodNodes.put("addRainParticles | ()V", new Patch(nodes, PatchOpType.Replace));

		nodes = new ArrayList<InstrSet>();
		list = new InsnList();
		list.add(new LineNumberNode(1368, new LabelNode()));
		list.add(new VarInsnNode(Opcodes.ALOAD, 0));
		list.add(new FieldInsnNode(Opcodes.GETFIELD, "bfe", "ae", "Ljava/util/Random;"));
		list.add(new VarInsnNode(Opcodes.ALOAD, 0));
		list.add(new FieldInsnNode(Opcodes.GETFIELD, "bfe", "s", "I"));
		list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "TFC/Core/WeatherManager","doRainClient","(Ljava/util/Random;I)V"));
		nodes.add(new InstrSet(list, 7, InstrOpType.InsertAfter));
		this.obfMethodNodes.put("g | ()V", new Patch(nodes, PatchOpType.Replace));*/

		//This version of the method patch simply overwrites the call to the vanilla method and calls our own method instead.
		//list.add(new VarInsnNode(Opcodes.ALOAD, 0));
		list.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/client/renderer/EntityRenderer", "random", "Ljava/util/Random;"));
		list.add(new VarInsnNode(Opcodes.ALOAD, 0));
		list.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/client/renderer/EntityRenderer", "rendererUpdateCount", "I"));
		list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "com/bioxx/tfc/ClientOverrides","doRainClient","(Ljava/util/Random;I)V"));
		nodes.add(new InstrSet(list, 208, InstrOpType.Replace));
		this.mcpMethodNodes.put("updateRenderer | ()V", new Patch(nodes, PatchOpType.Modify));

		nodes = new ArrayList<InstrSet>();
		list = new InsnList();
		//list.add(new VarInsnNode(Opcodes.ALOAD, 0));
		list.add(new FieldInsnNode(Opcodes.GETFIELD, "blt", "al", "Ljava/util/Random;"));
		list.add(new VarInsnNode(Opcodes.ALOAD, 0));
		list.add(new FieldInsnNode(Opcodes.GETFIELD, "blt", "w", "I"));
		list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "com/bioxx/tfc/ClientOverrides","doRainClient","(Ljava/util/Random;I)V"));
		nodes.add(new InstrSet(list, 208, InstrOpType.Replace));
		this.obfMethodNodes.put("d | ()V", new Patch(nodes, PatchOpType.Modify));
	}
}