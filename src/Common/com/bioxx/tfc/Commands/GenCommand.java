package com.bioxx.tfc.Commands;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.WorldGen.Generators.WorldGenFissure;
import com.bioxx.tfc.api.TFCOptions;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChatComponentText;

public class GenCommand extends CommandBase{

	@Override
	public String getCommandName() {
		return "gen";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] params) 
	{
		if(!TFCOptions.enableDebugMode)
			return;

		EntityPlayerMP player;

		player = getCommandSenderAsPlayer(sender);

		if(params.length == 2)
		{
			if(params[0].equals("fissure"))
			{
				WorldGenFissure gen = null;
				if(params[1].equals("water"))
				{
					gen = new WorldGenFissure(TFCBlocks.HotWaterStill);
					sender.addChatMessage(new ChatComponentText("Generating hot water fissure"));
				}
				else if(params[1].equals("lava"))
				{
					gen = new WorldGenFissure(Blocks.lava);
					sender.addChatMessage(new ChatComponentText("Generating lava fissure"));
				}
				else
				{
					gen = new WorldGenFissure(null);
					sender.addChatMessage(new ChatComponentText("Generating empty fissure"));
				}
				gen.generate(sender.getEntityWorld(), sender.getEntityWorld().rand, (int)player.posX, (int)player.posY-1, (int)player.posZ);
			}
		}
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) 
	{
		return null;
	}

}
