package com.bioxx.tfc.ASM.Transform;

import java.util.ArrayList;
import java.util.List;

import com.bioxx.tfc.ASM.ClassTransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class TF_EntityPlayer extends ClassTransformer
{

	public TF_EntityPlayer()
	{
		mcpClassName = "net.minecraft.entity.player.EntityPlayer";
		obfClassName = "yz";

		List<InstrSet> nodes = new ArrayList<InstrSet>();
		InsnList list = new InsnList();

		/**
		 * This is for making sure that overburdened works correctly
		 */
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

		/**
		 * This is for correcting the bow animation speeds
		 */
		list = new InsnList();
		nodes = new ArrayList<InstrSet>();
		nodes.add(new InstrSet(list, 33, InstrOpType.Remove));
		nodes.add(new InstrSet(list, 34, InstrOpType.Remove));
		nodes.add(new InstrSet(list, 35, InstrOpType.Remove));
		nodes.add(new InstrSet(list, 36, InstrOpType.Remove));
		nodes.add(new InstrSet(list, 37, InstrOpType.Remove));
		nodes.add(new InstrSet(list, 38, InstrOpType.Remove));
		nodes.add(new InstrSet(list, 39, InstrOpType.Remove));
		nodes.add(new InstrSet(list, 40, InstrOpType.Remove));
		nodes.add(new InstrSet(list, 41, InstrOpType.Remove));
		nodes.add(new InstrSet(list, 42, InstrOpType.Remove));
		nodes.add(new InstrSet(list, 43, InstrOpType.Remove));
		nodes.add(new InstrSet(list, 44, InstrOpType.Remove));
		nodes.add(new InstrSet(list, 45, InstrOpType.Remove));
		nodes.add(new InstrSet(list, 46, InstrOpType.Remove));
		nodes.add(new InstrSet(list, 47, InstrOpType.Remove));
		nodes.add(new InstrSet(list, 48, InstrOpType.Remove));
		nodes.add(new InstrSet(list, 49, InstrOpType.Remove));
		nodes.add(new InstrSet(list, 50, InstrOpType.Remove));
		nodes.add(new InstrSet(list, 51, InstrOpType.Remove));
		nodes.add(new InstrSet(list, 52, InstrOpType.Remove));
		nodes.add(new InstrSet(list, 53, InstrOpType.Remove));
		nodes.add(new InstrSet(list, 54, InstrOpType.Remove));
		nodes.add(new InstrSet(list, 55, InstrOpType.Remove));
		nodes.add(new InstrSet(list, 56, InstrOpType.Remove));
		nodes.add(new InstrSet(list, 57, InstrOpType.Remove));
		nodes.add(new InstrSet(list, 58, InstrOpType.Remove));
		nodes.add(new InstrSet(list, 59, InstrOpType.Remove));
		nodes.add(new InstrSet(list, 60, InstrOpType.Remove));
		nodes.add(new InstrSet(list, 61, InstrOpType.Remove));
		nodes.add(new InstrSet(list, 62, InstrOpType.Remove));
		nodes.add(new InstrSet(list, 63, InstrOpType.Remove));
		nodes.add(new InstrSet(list, 64, InstrOpType.Remove));
		nodes.add(new InstrSet(list, 65, InstrOpType.Remove));
		nodes.add(new InstrSet(list, 66, InstrOpType.Remove));
		nodes.add(new InstrSet(list, 67, InstrOpType.Remove));
		nodes.add(new InstrSet(list, 68, InstrOpType.Remove));
		nodes.add(new InstrSet(list, 69, InstrOpType.Remove));
		nodes.add(new InstrSet(list, 70, InstrOpType.Remove));
		nodes.add(new InstrSet(list, 71, InstrOpType.Remove));
		nodes.add(new InstrSet(list, 72, InstrOpType.Remove));
		nodes.add(new InstrSet(list, 73, InstrOpType.Remove));

		mcpMethodNodes.put("getItemIcon | (Lnet/minecraft/item/ItemStack;I)Lnet/minecraft/util/IIcon;", new Patch(nodes, PatchOpType.Modify));
		obfMethodNodes.put("b | (Ladd;I)Lrf;", new Patch(nodes, PatchOpType.Modify));
	}

}