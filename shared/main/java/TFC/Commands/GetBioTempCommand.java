package TFC.Commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import TFC.Core.TFC_Climate;

public class GetBioTempCommand extends CommandBase{

	@Override
	public String getCommandName() {
		return "gbt";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] params) 
	{

		MinecraftServer var3 = MinecraftServer.getServer();
		EntityPlayerMP var4;

		var4 = getCommandSenderAsPlayer(sender);


		float t = TFC_Climate.getBioTemperatureHeight((int)var4.posX, (int)var4.posY, (int)var4.posZ);
		throw new PlayerNotFoundException("BioTemp: "+t);

	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) 
	{
		return null;
	}

}
