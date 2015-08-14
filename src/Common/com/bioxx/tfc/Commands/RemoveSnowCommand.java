package com.bioxx.tfc.Commands;

import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;

import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCOptions;

public class RemoveSnowCommand extends CommandBase
{
	private int lastRadius = 0;  // just for confirmation of large radius (lag)
	
	@Override
	public String getCommandName() {
		return "removesnow";
	}
	
	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return "removesnow [radius]";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] params) {
		if(!TFCOptions.enableDebugMode)
			throw new WrongUsageException("sorry, unable to comply, gamemode need to be active");
		
		MinecraftServer server = MinecraftServer.getServer();
		EntityPlayerMP player = getCommandSenderAsPlayer(sender);
		WorldServer world = server.worldServerForDimension(player.getEntityWorld().provider.dimensionId);

		int count = 0;
		
		if(params.length == 0) {
			player.addChatMessage(new ChatComponentText("Removing Snow"));
			count += removeSnow(world, (int)player.posX, (int)player.posZ);
		} else if(params.length == 1) {
			int radius;
			try {
				radius = Integer.parseInt(params[0]);
			} catch (NumberFormatException ex) {
				throw new WrongUsageException(ex.getMessage());
			}
			if (radius < 0) 
				throw new WrongUsageException("A negative radius would disrupt the world: " + radius);
			if (radius > 10 && radius != lastRadius) {
				lastRadius = radius;
				throw new WrongUsageException("Really remove snow within a (square) radius of " + radius + "? This may lag the server, repeat to confirm!");
			} else {
				lastRadius = radius;
				player.addChatMessage(new ChatComponentText("Removing snow within a (square) radius of " + radius));
				for(int i = -radius; i <= radius; i++) {
					for(int k = -radius; k <= radius; k++) {
						count += removeSnow(world, (int)player.posX + (i * 16), (int)player.posZ + (k * 16));
					}
				}
			}
		} else {
			throw new WrongUsageException("what do you mean with \"%s\"?", params[1]);
		}
		player.addChatMessage(new ChatComponentText("removed " + count + " blocks"));
	}

	private int removeSnow(WorldServer world, int posX, int posZ) {
		int count = 0;
		Chunk chunk = world.getChunkFromBlockCoords(posX, posZ);
		for(int i = 0; i < 16; i++) {
			int x = i + (chunk.xPosition * 16);
			for(int k = 0; k < 16; k++) {
				int z = k + (chunk.zPosition * 16);
				int top = world.getTopSolidOrLiquidBlock(x, z);
				if (top < 1)
					continue;
				for (int y = top; y < 256 && y < top+40; y += 1) {
					Block block = chunk.getBlock(i, y, k);
					if (block == TFCBlocks.Snow) {
						world.setBlockToAir(x, y, z);
						count += 1;
					}
				}
			}
		}
		return count;
	}
}
