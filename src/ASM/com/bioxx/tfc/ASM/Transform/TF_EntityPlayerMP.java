package com.bioxx.tfc.ASM.Transform;

import java.util.ArrayList;
import java.util.List;

import com.bioxx.tfc.ASM.ClassTransformer;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LineNumberNode;

public class TF_EntityPlayerMP extends ClassTransformer
{

	public TF_EntityPlayerMP()
	{
		mcpClassName = "net.minecraft.entity.player.EntityPlayerMP";
		obfClassName = "mw";

		/**
		 * This removes the "this.getHealth() != this.lastHealth" part of the if statement
		 */
		List<InstrSet> nodes = new ArrayList<InstrSet>();
		nodes.add(new InstrSet(new LineNumberNode(0, new LabelNode()), 59, InstrOpType.Remove));
		nodes.add(new InstrSet(new LineNumberNode(0, new LabelNode()), 60, InstrOpType.Remove));
		nodes.add(new InstrSet(new LineNumberNode(0, new LabelNode()), 61, InstrOpType.Remove));
		nodes.add(new InstrSet(new LineNumberNode(0, new LabelNode()), 62, InstrOpType.Remove));
		nodes.add(new InstrSet(new LineNumberNode(0, new LabelNode()), 63, InstrOpType.Remove));
		nodes.add(new InstrSet(new LineNumberNode(0, new LabelNode()), 64, InstrOpType.Remove));

		mcpMethodNodes.put("onUpdateEntity | ()V", new Patch(nodes, PatchOpType.Modify));
		obfMethodNodes.put("i | ()V", new Patch(nodes, PatchOpType.Modify));
	}

}