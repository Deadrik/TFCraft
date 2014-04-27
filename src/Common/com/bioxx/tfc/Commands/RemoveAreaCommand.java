package com.bioxx.tfc.Commands;

import com.bioxx.tfc.api.TFCOptions;

import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;

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
						Block id = world.getBlock(x+(int)player.posX, y+(int)player.posY, z+(int)player.posZ);
						if(id != Blocks.bedrock)
							world.setBlock(x+(int)player.posX, y+(int)player.posY, z+(int)player.posZ, Blocks.air, 0, 2);
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
						Block id = world.getBlock(x+(int)player.posX, y+(int)player.posY, z+(int)player.posZ);
						if(id != Blocks.bedrock)
							world.setBlock(x+(int)player.posX, y+(int)player.posY, z+(int)player.posZ, Blocks.air, 0, 2);
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
