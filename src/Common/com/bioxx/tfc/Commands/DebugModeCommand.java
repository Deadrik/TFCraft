package com.bioxx.tfc.Commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;

import com.bioxx.tfc.api.TFCOptions;

public class DebugModeCommand extends CommandBase
{
	@Override
	public String getCommandName()
	{
		return "debugmode";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] params)
	{
		EntityPlayerMP player = getCommandSenderAsPlayer(sender);

		if(params.length == 0)
		{
			if (TFCOptions.enableDebugMode)
			{
				TFCOptions.enableDebugMode = false;
				player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("commands.debugmode.disabled")));
			}
			else
			{
				TFCOptions.enableDebugMode = true;
				player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("commands.debugmode.enabled")));
			}
		}
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) 
	{
		return "";
	}

}
