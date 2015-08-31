package com.bioxx.tfc.ASM.Transform;

/*
 * Invaluable help from AtomicStryker's MultiMine coremod code <3
 */

import java.util.ArrayList;
import java.util.List;

import com.bioxx.tfc.ASM.ClassTransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.MethodInsnNode;

public class TF_RenderGlobal extends ClassTransformer
{

	public TF_RenderGlobal()
	{
		mcpClassName = "net.minecraft.client.renderer.RenderGlobal";
		obfClassName = "bls";

		List<InstrSet> nodes = new ArrayList<InstrSet>();
		InsnList list = new InsnList();
		list.add(new LineNumberNode(445, new LabelNode()));
		list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "com/bioxx/tfc/ClientOverrides","loadRenderers","()V"));
		nodes.add(new InstrSet(list, 445, InstrOpType.InsertBefore));

		mcpMethodNodes.put("loadRenderers | ()V", new Patch(nodes, PatchOpType.Modify));
		obfMethodNodes.put("a | ()V", new Patch(nodes, PatchOpType.Modify));
	}

}