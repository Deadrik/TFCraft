package com.bioxx.tfc.Commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.player.EntityPlayerMP;

import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.api.Enums.EnumTree;

public class GetTreesCommand extends CommandBase{

	@Override
	public String getCommandName() {
		return "gt";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] params) 
	{

		//MinecraftServer var3 = MinecraftServer.getServer();
		EntityPlayerMP var4 = getCommandSenderAsPlayer(sender);
		int posX = (int)Math.floor(var4.posX);
		int posY = (int)Math.floor(var4.posY);
		int posZ = (int)Math.floor(var4.posZ);

		int t0ID = TFC_Climate.getTreeLayer(var4.worldObj, posX, posY, posZ, 0);
		int t1ID = TFC_Climate.getTreeLayer(var4.worldObj, posX, posY, posZ, 1);
		int t2ID = TFC_Climate.getTreeLayer(var4.worldObj, posX, posY, posZ, 2);

		String t0 = "None";
		if(t0ID != -1)
			t0 = EnumTree.values()[t0ID].name();
		String t1 = "None";
		if(t0ID != -1)
			t1 = EnumTree.values()[t1ID].name();
		String t2 = "None";
		if(t0ID != -1)
			t2 = EnumTree.values()[t2ID].name();

		throw new PlayerNotFoundException("Tree 0: "+ t0 + "   Tree 1: "+ t1 + "   Tree 2: "+ t2);

	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return "";
	}

}
