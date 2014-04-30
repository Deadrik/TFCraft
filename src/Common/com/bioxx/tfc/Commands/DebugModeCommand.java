package com.bioxx.tfc.Commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;

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
				player.addChatMessage(new ChatComponentText("Debug Mode Disabled"));
			}
			else
			{
				TFCOptions.enableDebugMode = true;
				player.addChatMessage(new ChatComponentText("Debug Mode Enabled"));
			}
		}
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) 
	{
		return null;
	}

}
