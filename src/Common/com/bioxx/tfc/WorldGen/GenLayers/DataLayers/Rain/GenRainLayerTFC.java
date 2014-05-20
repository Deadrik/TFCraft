package com.bioxx.tfc.WorldGen.GenLayers.DataLayers.Rain;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import net.minecraft.world.WorldType;

import com.bioxx.tfc.WorldGen.DataLayer;
import com.bioxx.tfc.WorldGen.GenLayers.GenLayerFuzzyZoomTFC;
import com.bioxx.tfc.WorldGen.GenLayers.GenLayerSmoothTFC;
import com.bioxx.tfc.WorldGen.GenLayers.GenLayerTFC;
import com.bioxx.tfc.WorldGen.GenLayers.GenLayerVoronoiZoomTFC;
import com.bioxx.tfc.WorldGen.GenLayers.GenLayerZoomTFC;

public abstract class GenRainLayerTFC extends GenLayerTFC
{
	public static int WET = DataLayer.Rain_2000.ID;
	public static int DRY = DataLayer.Rain_125.ID;

	public static GenLayerTFC[] initializeAllBiomeGenerators(long seed, WorldType worldType)
	{
		GenLayerTFC continent = genContinent(0);
		drawImage(512,continent, "Rain 0");
		continent = GenLayerZoomTFC.magnify(1000L, continent, 2);
		continent = new GenLayerSmoothTFC(1000L, continent);
		drawImage(512,continent, "Rain 1");
		for (int zoomLevel = 0; zoomLevel < 4; ++zoomLevel)
		{
			if(zoomLevel == 0)
			{
				continent = new GenLayerRainMix(1000 + zoomLevel, continent);
				drawImage(512,continent , "Rain 2-" + zoomLevel +" Mix");
			}
			continent = new GenLayerZoomTFC(1000 + zoomLevel, continent);
			drawImage(512,continent , "Rain 2-" + zoomLevel +" Smoothed");
			/*if(zoomLevel == 0)
			{
				continent = new GenLayerRainMix(1000 + zoomLevel, continent);
				drawImage(512,continent , "Rain 2-" + zoomLevel +" Mix");
			}*/
		}

		GenLayerSmoothTFC finalCont = new GenLayerSmoothTFC(1000L, continent);
		drawImage(512,finalCont, "Rain 3 Smoothed Rain");
		GenLayerVoronoiZoomTFC voronoiZoom = new GenLayerVoronoiZoomTFC(10L, finalCont);
		drawImage(512,finalCont, "Rain 4 Voronoi Rain");
		finalCont.initWorldGenSeed(seed);
		voronoiZoom.initWorldGenSeed(seed);
		return new GenLayerTFC[] {finalCont, voronoiZoom};
	}

	public static GenLayerTFC genContinent(long seed)
	{
		GenLayerTFC continent = new GenLayerRainInit(1L+seed);
		drawImage(512, continent, "Rain Init 0");
		continent = new GenLayerAddRain(1L, continent);
		drawImage(512, continent, "Rain Init 0b Add Rain");
		continent = new GenLayerFuzzyZoomTFC(2000L, continent);
		drawImage(512, continent, "Rain Init 1");
		continent = new GenLayerAddRain(1L, continent);
		drawImage(512, continent, "Rain Init 2 Add Rain");
		continent = new GenLayerZoomTFC(2001L, continent);
		drawImage(512, continent, "Rain Init 3 Zoom");
		continent = new GenLayerAddRain(2L, continent);
		drawImage(512, continent, "Rain Init 4 Add Rain");
		continent = new GenLayerZoomTFC(2002L, continent);
		drawImage(512, continent, "Rain Init 5 Zoom");
		continent = new GenLayerAddRain(3L, continent);
		drawImage(512, continent, "Rain Init 6 Add Rain");
		continent = new GenLayerZoomTFC(2003L, continent);
		drawImage(512, continent, "Rain Init 7 Zoom");
		return continent;
	}

	static boolean shouldDraw = true;
	public static void drawImage(int size, GenLayerTFC genlayer, String name)
	{
		if(!shouldDraw)
			return;
		try 
		{
			File outFile = new File(name+".bmp");
			if(outFile.exists())
				return;
			int[] ints = genlayer.getInts(0, 0, size, size);
			BufferedImage outBitmap = new BufferedImage(size,size,BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics = (Graphics2D) outBitmap.getGraphics();
			graphics.clearRect(0, 0, size, size);
			System.out.println("Starting "+name+".bmp");
			for(int x = 0; x < size; x++)
			{
				for(int z = 0; z < size; z++)
				{
					int value = ints[x*size+z];
					if(value-100 >= 0)
						graphics.setColor(Color.getColor("", (value-100)*32));	
					else
						graphics.setColor(Color.getColor("", value*16777215));	
					graphics.drawRect(x, z, 1, 1);
				}
			}
			System.out.println("Finished "+name+".bmp");
			ImageIO.write(outBitmap, "BMP", outFile);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	public GenRainLayerTFC(long par1)
	{
		super(par1);
	}

	/**
	 * Initialize layer's local worldGenSeed based on its own baseSeed and the world's global seed (passed in as an
	 * argument).
	 */
	@Override
	public void initWorldGenSeed(long par1)
	{
		worldGenSeed = par1;
		if (this.parent != null)
			parent.initWorldGenSeed(par1);

		worldGenSeed *= worldGenSeed * 6364136223846793005L + 1442695040888963407L;
		worldGenSeed += baseSeed;
		worldGenSeed *= worldGenSeed * 6364136223846793005L + 1442695040888963407L;
		worldGenSeed += baseSeed;
		worldGenSeed *= worldGenSeed * 6364136223846793005L + 1442695040888963407L;
		worldGenSeed += baseSeed;
	}

	/**
	 * Initialize layer's current chunkSeed based on the local worldGenSeed and the (x,z) chunk coordinates.
	 */
	@Override
	public void initChunkSeed(long par1, long par3)
	{
		chunkSeed = worldGenSeed;
		chunkSeed *= chunkSeed * 6364136223846793005L + 1442695040888963407L;
		chunkSeed += par1;
		chunkSeed *= chunkSeed * 6364136223846793005L + 1442695040888963407L;
		chunkSeed += par3;
		chunkSeed *= chunkSeed * 6364136223846793005L + 1442695040888963407L;
		chunkSeed += par1;
		chunkSeed *= chunkSeed * 6364136223846793005L + 1442695040888963407L;
		chunkSeed += par3;
	}

	/**
	 * returns a LCG pseudo random number from [0, x). Args: int x
	 */
	@Override
	protected int nextInt(int par1)
	{
		int var2 = (int)((this.chunkSeed >> 24) % par1);
		if (var2 < 0)
			var2 += par1;

		chunkSeed *= chunkSeed * 6364136223846793005L + 1442695040888963407L;
		chunkSeed += worldGenSeed;
		return var2;
	}

	/**
	 * Returns a list of integer values generated by this layer. These may be interpreted as temperatures, rainfall
	 * amounts, or biomeList[] indices based on the particular GenLayer subclass.
	 */
	@Override
	public abstract int[] getInts(int var1, int var2, int var3, int var4);
}
