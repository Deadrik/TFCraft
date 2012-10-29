package TFC.Commands;

import TFC.Chunkdata.ChunkData;
import TFC.Chunkdata.ChunkDataManager;
import TFC.Core.TFC_Climate;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.CommandBase;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.PlayerNotFoundException;
import net.minecraft.src.WrongUsageException;

public class GetSpawnProtectionCommand extends CommandBase{

	@Override
	public String getCommandName() {
		return "gsp";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] params) 
	{
		if(sender.canCommandSenderUseCommand(0, sender.getCommandSenderName()))
		{
            MinecraftServer var3 = MinecraftServer.getServer();
            EntityPlayerMP var4;
            
             var4 = (EntityPlayerMP)getCommandSenderAsPlayer(sender);
             
             int x = (int)var4.posX >> 4;
             int z = (int)var4.posZ >> 4;
             
             ChunkData d = (ChunkData) ChunkDataManager.chunkmap.get(x+","+z);

             if(d != null)
            	 throw new PlayerNotFoundException("SP: " + d.getSpawnProtectionWithUpdate());
             else
            	 throw new PlayerNotFoundException("Unable to find ChunkData for "+x+","+z);
		}
		
	}

}
