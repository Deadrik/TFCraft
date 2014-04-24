package TFC.Commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import TFC.API.TFCOptions;

public class DebugModeCommand extends CommandBase{

	@Override
	public String getCommandName() {
		return "debugmode";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] params) 
	{
		if(!TFCOptions.enableDebugMode)
			return;

		EntityPlayerMP player;

		player = getCommandSenderAsPlayer(sender);

		if(params.length == 0)
		{
			NBTTagCompound nbt = player.getEntityData();
			if(nbt != null && nbt.hasKey("inDebugMode"))
			{
				player.getEntityData().removeTag("inDebugMode");
				sender.addChatMessage(new ChatComponentText("Debug Mode is OFF"));
				TFCOptions.enableDebugMode = false;
			}
			else
			{
				player.getEntityData().setBoolean("inDebugMode", true);
				sender.addChatMessage(new ChatComponentText("Debug Mode is ON"));
				TFCOptions.enableDebugMode = true;
			}
		}
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) 
	{
		return null;
	}

}
