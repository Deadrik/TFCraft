package com.bioxx.tfc.Commands;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.WorldServer;

import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.api.TFCOptions;

public class CommandTime extends CommandBase
{
	@Override
	public String getCommandName()
	{
		return "time";
	}

	/**
	 * Return the required permission level for this command.
	 */
	@Override
	public int getRequiredPermissionLevel()
	{
		return 2;
	}

	@Override
	public String getCommandUsage(ICommandSender par1ICommandSender)
	{
		return "commands.time.usage";
	}

	@Override
	public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
	{
		if(!TFCOptions.enableDebugMode){
			if (par2ArrayOfStr.length > 1)
			{
				int i;
				long currentTime = TFC_Time.getTotalTicks();

				if (par2ArrayOfStr[0].equals("set"))
				{
					if (par2ArrayOfStr[1].equals("day"))
					{
						i = (int)(currentTime + (24000 - (currentTime%24000)));
					}
					else if (par2ArrayOfStr[1].equals("night"))
					{
						i = 12500 + (int)(currentTime + (24000 - (currentTime%24000)));
					}
					else
					{
						i = parseIntWithMin(par1ICommandSender, par2ArrayOfStr[1], 0);
					}
					if(i < currentTime)
					{
						par1ICommandSender.addChatMessage(new ChatComponentText("Cannot set time to before current time."));
					}
					else
					{
						this.setTime(par1ICommandSender, i);
						func_152373_a(par1ICommandSender, this, "commands.time.set", new Object[] {Integer.valueOf(i)});//notifyAdmins
					}
					return;
				}

				if (par2ArrayOfStr[0].equals("add"))
				{
					i = parseIntWithMin(par1ICommandSender, par2ArrayOfStr[1], 0);
					if(i+ currentTime < currentTime)
					{
						par1ICommandSender.addChatMessage(new ChatComponentText("Cannot set time to before current time."));
					}
					else
					{
						this.addTime(par1ICommandSender, i);
						func_152373_a(par1ICommandSender, this, "commands.time.added", new Object[] {Integer.valueOf(i)});
					}
					return;
				}
			}
		}
		else{
			(new net.minecraft.command.CommandTime()).processCommand(par1ICommandSender,par2ArrayOfStr);
			return;
		}
		throw new WrongUsageException("commands.time.usage", new Object[0]);
	}

	/**
	 * Adds the strings available in this command to the given list of tab completion options.
	 */
	@Override
	public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
	{
		return par2ArrayOfStr.length == 1 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, new String[] {"set", "add"}): par2ArrayOfStr.length == 2 && par2ArrayOfStr[0].equals("set") ? getListOfStringsMatchingLastWord(par2ArrayOfStr, new String[] {"day", "night"}): null;
	}

	/**
	 * Set the time in the server object.
	 */
	protected void setTime(ICommandSender par1ICommandSender, int par2)
	{
		for (int j = 0; j < MinecraftServer.getServer().worldServers.length; ++j)
		{
			MinecraftServer.getServer().worldServers[j].setWorldTime(par2);
		}
	}

	/**
	 * Adds (or removes) time in the server object.
	 */
	protected void addTime(ICommandSender par1ICommandSender, int par2)
	{
		for (int j = 0; j < MinecraftServer.getServer().worldServers.length; ++j)
		{
			WorldServer worldserver = MinecraftServer.getServer().worldServers[j];
			worldserver.setWorldTime(worldserver.getWorldTime() + par2);
		}
	}
}
