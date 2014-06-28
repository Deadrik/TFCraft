package com.bioxx.tfc.Commands;

import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Items.ItemLooseRock;
import com.bioxx.tfc.WorldGen.DataLayer;

public class GetRocksCommand extends CommandBase
{
	@Override
	public String getCommandName()
	{
		return "gr";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] params)
	{
		MinecraftServer var3 = MinecraftServer.getServer();
		EntityPlayerMP var4;
		var4 = getCommandSenderAsPlayer(sender);

		DataLayer t0 = TFC_Climate.getRockLayer(var4.worldObj, (int)var4.posX, (int)var4.posY, (int)var4.posZ, 0);
		DataLayer t1 = TFC_Climate.getRockLayer(var4.worldObj, (int)var4.posX, (int)var4.posY, (int)var4.posZ, 1);
		DataLayer t2 = TFC_Climate.getRockLayer(var4.worldObj, (int)var4.posX, (int)var4.posY, (int)var4.posZ, 2);

		String t0s = ((ItemLooseRock)TFCItems.LooseRock).MetaNames[t0.data1];
		String t1s = ((ItemLooseRock)TFCItems.LooseRock).MetaNames[t1.data1];
		String t2s = ((ItemLooseRock)TFCItems.LooseRock).MetaNames[t2.data1];

		throw new PlayerNotFoundException("Rock Layer 1: "+ t0s + "   Rock Layer 2: "+ t1s + "   Rock Layer 3: "+ t2s);
	}

	public static int getSoilMetaFromStone(Block inType, int inMeta)
	{
		if(inType == TFCBlocks.StoneIgIn)
			return inMeta;
		else if(inType == TFCBlocks.StoneSed)
			return inMeta+3;
		else if(inType == TFCBlocks.StoneIgEx)
			return inMeta+11;
		else
			return inMeta+15;
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender)
	{
		return "";
	}
}
