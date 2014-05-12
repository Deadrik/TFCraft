package com.bioxx.tfc.Commands;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import com.bioxx.tfc.WorldGen.TFCBiome;
import com.bioxx.tfc.api.TFCOptions;

public class PrintBiomeMapCommand extends CommandBase
{
	@Override
	public String getCommandName()
	{
		return "printbiomes";
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

		if(params.length == 2)
		{
			String name = params[0];
			int size = Integer.parseInt(params[1]);
			drawImage((int)Math.floor(player.posX), (int)Math.floor(player.posZ), size, world, name);
		}
	}

	public static void drawImage(int xCoord, int zCoord, int size, World world, String name)
	{
		try 
		{
			File outFile = new File(name+".bmp");
			TFCBiome[] biomes = new TFCBiome[size*size];
			//world.getWorldChunkManager().getBiomesForGeneration(biomes, xCoord-size/2, zCoord-size/2, size, size);
			BufferedImage outBitmap = new BufferedImage(size,size,BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics = (Graphics2D) outBitmap.getGraphics();
			graphics.clearRect(0, 0, size, size);
			System.out.println(name+".bmp");
			float perc = 0.1f;
			for(int x = -size/2; x < size/2; x++)
			{
				for(int z = -size/2; z < size/2; z++)
				{
					graphics.setColor(Color.getColor("", ((TFCBiome)world.getWorldChunkManager().getBiomeGenAt(x+xCoord, z+zCoord)).getBiomeColor()));	
					graphics.drawRect(x+size/2, z+size/2, 1, 1);
					if(((x+size/2) * (z+size/2)) / size*size > perc)
					{
						System.out.println(perc*100+"%");
						perc+=0.1f;
					}
				}
			}
			System.out.println(name+".bmp Done!");
			ImageIO.write(outBitmap, "BMP", outFile);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender)
	{
		return null;
	}

}
