package com.bioxx.tfc.Commands;

import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.WorldServer;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.TFCOptions;

public class RemoveAreaCommand extends CommandBase
{
	@Override
	public String getCommandName()
	{
		return "removearea";
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
			TFC_Core.sendInfoMessage(player, new ChatComponentText("Removing 16 blocks up and +/- x, z"));
			for(int x = -15; x < 16; x++)
			{
				for(int z = -15; z < 16; z++)
				{
					for(int y = 0; y < 16; y++)
					{
						Block id = world.getBlock(x + (int)player.posX, y + (int)player.posY, z + (int)player.posZ);
						if(id != Blocks.bedrock)
							world.setBlock(x + (int)player.posX, y + (int)player.posY, z + (int)player.posZ, Blocks.air, 0, 2);
					}
				}
			}

			TFC_Core.sendInfoMessage(player, new ChatComponentText("Removing Area Complete"));
		}
		else if(params.length == 3)
		{
			TFC_Core.sendInfoMessage(player, new ChatComponentText("Removing Area"));
			int radius = Integer.parseInt(params[0]);
			for (int x = -radius; x <= Integer.parseInt(params[0]); x++)
			{
				for(int z = -Integer.parseInt(params[2]); z <= Integer.parseInt(params[2]); z++)
				{
					for(int y = 0; y <= Integer.parseInt(params[1]); y++)
					{
						Block id = world.getBlock(x + (int)player.posX, y + (int)player.posY, z + (int)player.posZ);
						if(id != Blocks.bedrock)
							world.setBlock(x + (int)player.posX, y + (int)player.posY, z + (int)player.posZ, Blocks.air, 0, 2);
					}
				}
			}

			TFC_Core.sendInfoMessage(player, new ChatComponentText("Removing Area Complete"));
		}
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) 
	{
		return "";
	}

}
