package TFC.Commands;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import TFC.Core.Player.TFC_PlayerServer;

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

            MinecraftServer var3 = MinecraftServer.getServer();
            EntityPlayerMP var4;
            EntityPlayerMP var5;
            var4 = getCommandSenderAsPlayer(sender);
            TFC_PlayerServer var6 = TFC_PlayerServer.getFromEntityPlayer(var4);
            throw new PlayerNotFoundException("Body Temperature: "+var6.bodyTemp);
		
	}

}
