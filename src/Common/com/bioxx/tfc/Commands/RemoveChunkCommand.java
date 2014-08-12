package com.bioxx.tfc.Commands;

import java.util.regex.Pattern;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

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
		if(!TFCOptions.enableDebugMode)
		{
			return;
		}
		MinecraftServer server = MinecraftServer.getServer();
		EntityPlayerMP player = getCommandSenderAsPlayer(sender);
		WorldServer world = server.worldServerForDimension(player.getEntityWorld().provider.dimensionId);

		if(params.length == 0)
		{
			player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("commands.removingchunk")));
			Chunk chunk = world.getChunkFromBlockCoords((int)player.posX, (int)player.posZ);
			chunk.setStorageArrays(new ExtendedBlockStorage[16]);
			chunk.setChunkModified();
		}
		else if(params.length == 1)
		{
			String message = StatCollector.translateToLocal("commands.removingchunk.size").replaceAll(Pattern.quote("$RADIUS"), params[0]);
			player.addChatMessage(new ChatComponentText(message));
			int radius = Integer.parseInt(params[0]);
			for(int i = -radius; i <= radius; i++)
			{
				for(int k = -radius; k <= radius; k++)
				{
					Chunk chunk = world.getChunkFromBlockCoords((int)player.posX + (i * 16), (int)player.posZ + (k * 16));
					chunk.setStorageArrays(new ExtendedBlockStorage[16]);
					chunk.setChunkModified();
				}
			}
		}
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender)
	{
		return "";
	}

}
