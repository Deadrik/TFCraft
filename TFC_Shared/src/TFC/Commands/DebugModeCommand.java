package TFC.Commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
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
				TFCOptions.enableDebugMode = false;
			}
			else
			{
				player.getEntityData().setBoolean("inDebugMode", true);
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
