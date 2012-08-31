package TFC.Commands;

import TFC.Core.TFC_Climate;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.CommandBase;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.PlayerNotFoundException;
import net.minecraft.src.WrongUsageException;

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

             var4 = (EntityPlayerMP)getCommandSenderAsPlayer(sender);


            float t = TFC_Climate.getBioTemperatureHeight((int)var4.posX, (int)var4.posY, (int)var4.posZ);
            throw new PlayerNotFoundException("BioTemp: "+t);
		
	}

}
