package com.bioxx.tfc.Commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;

import com.bioxx.tfc.Chunkdata.ChunkData;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.TFCOptions;

public class GSPVisualCommand extends CommandBase{

	@Override
	public String getCommandName() {
		return "vgsp";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] params) 
	{
		if(!TFCOptions.enableDebugMode)
			return;
		MinecraftServer server = MinecraftServer.getServer();
		EntityPlayerMP player;

		player = getCommandSenderAsPlayer(sender);
		WorldServer world = server.worldServerForDimension(player.getEntityWorld().provider.dimensionId);

		int px = (int)player.posX >> 4;
		int pz = (int)player.posZ >> 4;

		ChunkData d = TFC_Core.getCDM(world).getData(px, pz);

		if(params.length == 0)
		{
			Chunk chunk = world.getChunkFromBlockCoords((int)player.posX, (int)player.posZ);
			for(int x = 0; x < 16; x++)
			{
				for(int z = 0; z < 16; z++)
					world.setBlock(x+(chunk.xPosition*16), (int)player.posY-1, z+(chunk.zPosition*16), Blocks.wool, getColor(d.getSpawnProtectionWithUpdate()), 2);
			}
		}
		else if(params.length == 1)
		{
			int radius = Integer.parseInt(params[0]);
			for(int i = -radius; i <= radius;i++)
			{
				for(int k = -radius; k <= radius;k++)
				{
					Chunk chunk = world.getChunkFromBlockCoords((int) player.posX + i * 16, (int) player.posZ + k * 16);
					for(int x = 0; x < 16; x++)
					{
						for(int z = 0; z < 16; z++)
							world.setBlock(x + (chunk.xPosition * 16), (int)player.posY - 1, z + (chunk.zPosition * 16), Blocks.wool, getColor(d.getSpawnProtectionWithUpdate()), 2);
					}
				}
			}
		}
	}

	private int getColor(int val)
	{
		return val/ (4320 / 16);
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) 
	{
		return "";
	}

}
