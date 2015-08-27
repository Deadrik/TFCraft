package com.bioxx.tfc.Commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.TFCOptions;

public class RemoveChunkCommand extends CommandBase
{
	@Override
	public String getCommandName()
	{
		return "removechunk";
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
			TFC_Core.sendInfoMessage(player, new ChatComponentText("Removing Chunk"));
			Chunk chunk = world.getChunkFromBlockCoords((int)player.posX, (int)player.posZ);
			chunk.setStorageArrays(new ExtendedBlockStorage[16]);
			chunk.setChunkModified();
			TFC_Core.sendInfoMessage(player, new ChatComponentText("Removing Chunk Complete"));
		}
		else if(params.length == 1)
		{
			TFC_Core.sendInfoMessage(player, new ChatComponentText("Removing Chunks Within a Radius of " + Integer.parseInt(params[0])));
			int radius = Integer.parseInt(params[0]);
			for(int i = -radius; i <= radius; i++)
			{
				for(int k = -radius; k <= radius; k++)
				{
					Chunk chunk = world.getChunkFromBlockCoords((int) player.posX + i * 16, (int) player.posZ + k * 16);
					chunk.setStorageArrays(new ExtendedBlockStorage[16]);
					chunk.setChunkModified();
				}
			}

			TFC_Core.sendInfoMessage(player, new ChatComponentText("Removing Chunk Complete"));
		}
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender)
	{
		return "";
	}

}
