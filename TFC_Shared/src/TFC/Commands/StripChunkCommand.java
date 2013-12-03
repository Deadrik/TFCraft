package TFC.Commands;

import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import TFC.TFCBlocks;
import TFC.API.TFCOptions;

public class StripChunkCommand extends CommandBase{

	@Override
	public String getCommandName() {
		return "stripchunk";
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
			Chunk chunk = world.getChunkFromBlockCoords((int)player.posX, (int)player.posZ);
			for(int x = 0; x < 16; x++)
			{
				for(int z = 0; z < 16; z++)
				{
					for(int y = 0; y < 256; y++)
					{
						int id = chunk.getBlockID(x, y, z);
						if(id != TFCBlocks.Ore.blockID && id != TFCBlocks.Ore2.blockID && 
								id != TFCBlocks.Ore3.blockID && id != Block.bedrock.blockID)
						{
							world.setBlock(x+(chunk.xPosition*16), y, z+(chunk.zPosition*16), 0, 0, 2);
						}
					}
				}
			}
		}
		else if(params.length == 1)
		{
			int radius = Integer.parseInt(params[0]);
			for(int i = -radius; i <= radius;i++)
			{
				for(int k = -radius; k <= radius;k++)
				{
					Chunk chunk = world.getChunkFromBlockCoords((int)player.posX+(i*16), (int)player.posZ+(k*16));
					for(int x = 0; x < 16; x++)
					{
						for(int z = 0; z < 16; z++)
						{
							for(int y = 0; y < 256; y++)
							{
								int id = chunk.getBlockID(x, y, z);
								if(id != TFCBlocks.Ore.blockID && id != TFCBlocks.Ore2.blockID && 
										id != TFCBlocks.Ore3.blockID && id != Block.bedrock.blockID)
								{
									world.setBlock(x+(chunk.xPosition*16), y, z+(chunk.zPosition*16), 0, 0, 2);
								}
							}
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
