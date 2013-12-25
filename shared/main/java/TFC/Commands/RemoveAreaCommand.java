package TFC.Commands;

import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import TFC.API.TFCOptions;

public class RemoveAreaCommand extends CommandBase{

	@Override
	public String getCommandName() {
		return "removearea";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] params) 
	{
		if(!TFCOptions.enableDebugMode)
		{
			return;
		}
		MinecraftServer server = MinecraftServer.getServer();
		EntityPlayerMP player;

		player = getCommandSenderAsPlayer(sender);
		WorldServer world = server.worldServerForDimension(player.getEntityWorld().provider.dimensionId);

		if(params.length == 0)
		{
			for(int x = -15; x < 16; x++)
			{
				for(int z = -15; z < 16; z++)
				{
					for(int y = 0; y < 16; y++)
					{
						int id = world.getBlockId(x+(int)player.posX, y+(int)player.posY, z+(int)player.posZ);
						if(id != Block.bedrock.blockID)
						{
							world.setBlock(x+(int)player.posX, y+(int)player.posY, z+(int)player.posZ, 0, 0, 2);
						}
					}
				}
			}
		}
		else if(params.length == 3)
		{
			int radius = Integer.parseInt(params[0]);
			for(int x = -Integer.parseInt(params[0]); x <= Integer.parseInt(params[0]); x++)
			{
				for(int z = -Integer.parseInt(params[2]); z <= Integer.parseInt(params[2]); z++)
				{
					for(int y = 0; y <= Integer.parseInt(params[1]); y++)
					{
						int id = world.getBlockId(x+(int)player.posX, y+(int)player.posY, z+(int)player.posZ);
						if(id != Block.bedrock.blockID)
						{
							world.setBlock(x+(int)player.posX, y+(int)player.posY, z+(int)player.posZ, 0, 0, 2);
						}
					}
				}
			}
		}
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) 
	{
		return null;
	}

}
