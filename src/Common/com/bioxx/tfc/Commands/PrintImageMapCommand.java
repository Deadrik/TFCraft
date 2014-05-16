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

import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.WorldGen.TFCBiome;
import com.bioxx.tfc.api.TFCOptions;

public class PrintImageMapCommand extends CommandBase
{
	@Override
	public String getCommandName()
	{
		return "printimage";
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

		if(params.length == 3)
		{
			if(params[0].equals("biome"))
			{
				String name = params[1];
				int size = Integer.parseInt(params[2]);
				drawBiomeImage((int)Math.floor(player.posX), (int)Math.floor(player.posZ), size, world, name);
			}
			else if(params[0].equals("temp"))
			{
				String name = params[1];
				int size = Integer.parseInt(params[2]);
				drawTempImage((int)Math.floor(player.posX), (int)Math.floor(player.posZ), size, world, name);
			}
		}
	}
	public static void drawTempImage(int xCoord, int zCoord, int size, World world, String name)
	{
		try 
		{
			File outFile = new File(name+".bmp");
			BufferedImage outBitmap = new BufferedImage(size,size,BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics = (Graphics2D) outBitmap.getGraphics();
			graphics.clearRect(0, 0, size, size);
			System.out.println(name+".bmp");
			float perc = 0.1f;
			int sizeHalf = size/2;
			float count = 0;
			for(int x = -sizeHalf; x < sizeHalf; x++)
			{
				for(int z = -sizeHalf; z < sizeHalf; z++)
				{
					count++;
					int temp = (int)(255*(TFC_Climate.getBioTemperature(x+xCoord, z+zCoord)/50));
					graphics.setColor(Color.getColor("", (temp << 16) + (temp << 8) + temp));	
					graphics.drawRect(x+sizeHalf, z+sizeHalf, 1, 1);
					if(count / (size*size) > perc)
					{
						System.out.println((int)(perc*100)+"%");
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
	public static void drawBiomeImage(int xCoord, int zCoord, int size, World world, String name)
	{
		try 
		{
			File outFile = new File(name+".bmp");
			BufferedImage outBitmap = new BufferedImage(size,size,BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics = (Graphics2D) outBitmap.getGraphics();
			graphics.clearRect(0, 0, size, size);
			System.out.println(name+".bmp");
			float perc = 0.1f;
			float count = 0;
			for(int x = -size/2; x < size/2; x++)
			{
				for(int z = -size/2; z < size/2; z++)
				{
					count++;
					graphics.setColor(Color.getColor("", ((TFCBiome)world.getWorldChunkManager().getBiomeGenAt(x+xCoord, z+zCoord)).getBiomeColor()));	
					graphics.drawRect(x+size/2, z+size/2, 1, 1);
					if(count / (size*size) > perc)
					{
						System.out.println((int)(perc*100)+"%");
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
		return "";
	}

}
