package com.bioxx.tfc.Commands;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class GetBodyTemp extends CommandBase{

	@Override
	public String getCommandName() {
		return "bodytemp";
	}

	@Override
	public int getRequiredPermissionLevel()
	{
		return 0;
	}

	@Override
	public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
	{
		return getListOfStringsMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getAllUsernames());
	}

	@Override
	public void processCommand(ICommandSender sender, String[] params) 
	{
		EntityPlayerMP var4 = getCommandSenderAsPlayer(sender);
		float temp = var4.getEntityData().hasKey("bodyTemp") ? var4.getEntityData().getFloat("bodyTemp") : -1;
		throw new PlayerNotFoundException("Body Temperature: " + temp);

	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender)
	{
		return true;
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		// TODO Auto-generated method stub
		return "";
	}

}
