package com.bioxx.tfc.ASM.Transform;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

import com.bioxx.tfc.ASM.ClassTransformer;

public class TF_EntityPlayer extends ClassTransformer
{

	public TF_EntityPlayer()
	{
		mcpClassName = "net.minecraft.entity.player.EntityPlayer";
		obfClassName = "yz";

		List nodes = new ArrayList();
		InsnList list = new InsnList();

		LabelNode ln = new LabelNode();

		// if ( ServerOverrides.canPlayerMove( this ) == false ) {
		list.add(new VarInsnNode(Opcodes.ALOAD, 0));
		list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "com/bioxx/tfc/ServerOverrides","canPlayerMove","(Lnet/minecraft/entity/EntityLivingBase;)Z"));
		list.add(new JumpNode(Opcodes.IFNE,ln));

		// forward=0;
		list.add(new InsnNode(Opcodes.FCONST_0));
		list.add(new VarInsnNode(Opcodes.FSTORE,1));

		// strafe=0;
		list.add(new InsnNode(Opcodes.FCONST_0));
		list.add(new VarInsnNode(Opcodes.FSTORE,2));

		// }
		list.add(ln);

		nodes.add(new InstrSet(list, 0, InstrOpType.InsertBefore));

		mcpMethodNodes.put("moveEntityWithHeading | (FF)V", new Patch(nodes, PatchOpType.Modify));
		obfMethodNodes.put("e | (FF)V", new Patch(nodes, PatchOpType.Modify));
	}

}