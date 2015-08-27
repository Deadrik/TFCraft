package com.bioxx.tfc.Commands;

import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCOptions;

public class StripChunkCommand extends CommandBase
{
	@Override
	public String getCommandName()
	{
		return "stripchunk";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] params)
	{
		EntityPlayerMP player = getCommandSenderAsPlayer(sender);
		
		if(!TFCOptions.enableDebugMode)
		{
			TFC_Core.sendInfoMessage(player, new ChatComponentText("Debug Mode Required"));
			return;
		}

		MinecraftServer server = MinecraftServer.getServer();
		WorldServer world = server.worldServerForDimension(player.getEntityWorld().provider.dimensionId);
		if(params.length == 0)
		{
			TFC_Core.sendInfoMessage(player, new ChatComponentText("Stripping Chunk"));
			Chunk chunk = world.getChunkFromBlockCoords((int)player.posX, (int)player.posZ);
			for(int x = 0; x < 16; x++)
			{
				for(int z = 0; z < 16; z++)
				{
					for(int y = 0; y < 256; y++)
					{
						Block id = chunk.getBlock(x, y, z);
						if(id != TFCBlocks.Ore && id != TFCBlocks.Ore2 && id != TFCBlocks.Ore3 && id != Blocks.bedrock)
							world.setBlock(x + (chunk.xPosition * 16), y, z + (chunk.zPosition * 16), Blocks.air, 0, 2);
					}
				}
			}

			TFC_Core.sendInfoMessage(player, new ChatComponentText("Stripping Chunk Complete"));
		}
		else if(params.length == 1)
		{
			TFC_Core.sendInfoMessage(player, new ChatComponentText("Stripping Chunks Within a Radius of " + Integer.parseInt(params[0])));
			int radius = Integer.parseInt(params[0]);
			for(int i = -radius; i <= radius; i++)
			{
				for(int k = -radius; k <= radius; k++)
				{
					Chunk chunk = world.getChunkFromBlockCoords((int) player.posX + i * 16, (int) player.posZ + k * 16);
					for(int x = 0; x < 16; x++)
					{
						for(int z = 0; z < 16; z++)
						{
							for(int y = 0; y < 256; y++)
							{
								Block id = chunk.getBlock(x, y, z);
								if(id != TFCBlocks.Ore && id != TFCBlocks.Ore2 && id != TFCBlocks.Ore3 && id != Blocks.bedrock)
									world.setBlock(x + (chunk.xPosition * 16), y, z + (chunk.zPosition * 16), Blocks.air, 0, 2);
							}
						}
					}
				}
			}

			TFC_Core.sendInfoMessage(player, new ChatComponentText("Stripping Chunk Complete"));
		}
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender)
	{
		return "";
	}

}
