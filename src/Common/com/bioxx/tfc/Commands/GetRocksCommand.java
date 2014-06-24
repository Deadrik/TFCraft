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
import com.bioxx.tfc.Core.Util.BlockMeta;
import com.bioxx.tfc.Items.ItemLooseRock;

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

		BlockMeta t0 = TFC_Climate.getRockLayer(var4.worldObj, (int)var4.posX, (int)var4.posY, (int)var4.posZ, 0);
		BlockMeta t1 = TFC_Climate.getRockLayer(var4.worldObj, (int)var4.posX, (int)var4.posY, (int)var4.posZ, 1);
		BlockMeta t2 = TFC_Climate.getRockLayer(var4.worldObj, (int)var4.posX, (int)var4.posY, (int)var4.posZ, 2);

		t0.meta = getSoilMetaFromStone(t0.block, t0.meta);
		t1.meta = getSoilMetaFromStone(t1.block, t1.meta);
		t2.meta = getSoilMetaFromStone(t2.block, t2.meta);

		String t0s = ((ItemLooseRock)TFCItems.LooseRock).MetaNames[t0.meta];
		String t1s = ((ItemLooseRock)TFCItems.LooseRock).MetaNames[t1.meta];
		String t2s = ((ItemLooseRock)TFCItems.LooseRock).MetaNames[t2.meta];

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
