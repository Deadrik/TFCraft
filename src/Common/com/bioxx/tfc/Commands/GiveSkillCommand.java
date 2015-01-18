package com.bioxx.tfc.Commands;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

import com.bioxx.tfc.Core.TFC_Core;

public class GiveSkillCommand extends CommandBase{

	@Override
	public String getCommandName() {
		return "giveskill";
	}

	@Override
	public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
	{
		return getListOfStringsMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getAllUsernames());
	}
	@Override
	public void processCommand(ICommandSender sender, String[] params) 
	{
		//MinecraftServer var3 = MinecraftServer.getServer();
		EntityPlayerMP var4 = getCommandSenderAsPlayer(sender);
		EntityPlayerMP player;

		if(params.length == 2)
		{
			TFC_Core.getSkillStats(var4).increaseSkill(params[0], Integer.parseInt(params[1]));
		}
		else if(params.length == 3)
		{
			player = getPlayer(sender, params[0]);
			TFC_Core.getSkillStats(player).increaseSkill(params[1], Integer.parseInt(params[2]));
		}
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return "";
	}

}
