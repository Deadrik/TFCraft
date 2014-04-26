package TFC.ASM.Transform;

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

import TFC.ASM.ClassTransformer;

public class TF_RenderGlobal extends ClassTransformer
{

	public TF_RenderGlobal()
	{
		mcpClassName = "net.minecraft.client.renderer.RenderGlobal";
		obfClassName = "bls";

		List nodes = new ArrayList<InstrSet>();
		InsnList list = new InsnList();
		list.add(new LineNumberNode(435, new LabelNode()));
		list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "TFC/Render/TFC_CoreRender","loadRenderers","()V"));
		nodes.add(new InstrSet(list, 453, InstrOpType.InsertBefore));

		mcpMethodNodes.put("loadRenderers | ()V", new Patch(nodes, PatchOpType.Modify));
		obfMethodNodes.put("a | ()V", new Patch(nodes, PatchOpType.Modify));
	}

}