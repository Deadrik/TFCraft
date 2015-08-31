package com.bioxx.tfc.WorldGen.GenLayers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import net.minecraft.world.gen.layer.GenLayer;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.WorldGen.TFCBiome;
import com.bioxx.tfc.WorldGen.TFCWorldType;
import com.bioxx.tfc.WorldGen.GenLayers.Biome.*;
import com.bioxx.tfc.WorldGen.GenLayers.River.GenLayerRiverInitTFC;
import com.bioxx.tfc.WorldGen.GenLayers.River.GenLayerRiverMixTFC;
import com.bioxx.tfc.WorldGen.GenLayers.River.GenLayerRiverTFC;

public abstract class GenLayerTFC extends GenLayer
{
	protected long worldGenSeed;
	protected GenLayerTFC parent;
	protected long chunkSeed;
	protected long baseSeed;

	public static GenLayerTFC[] initialize(long par0, TFCWorldType par2)
	{
		GenLayerTFC continent = genContinent(0, false);
		continent = new GenLayerDeepOcean(4L, continent);
		drawImage(512, continent, "8b Continents Done Deep Ocean");
		byte var4 = 4;

		//Create Biomes
		GenLayerTFC continentCopy2 = GenLayerZoomTFC.magnify(1000L, continent, 0);
		drawImage(512, continentCopy2, "14 ContinentsZoom");
		GenLayerTFC var17 = new GenLayerBiomeTFC(200L, continentCopy2, par2);
		drawImage(512, var17, "15 Biome");
		GenLayerLakes lakes = new GenLayerLakes(200L, var17);
		drawImage(512, var17, "15b Lakes");
		continentCopy2 = GenLayerZoomTFC.magnify(1000L, lakes, 2);
		drawImage(512, continentCopy2, "16 ZoomBiome");
		GenLayerTFC var18 = new GenLayerBiomeEdge(1000L, continentCopy2);
		drawImage(512, var18, "17 BiomeEdge");
		for (int var7 = 0; var7 < var4; ++var7)
		{
			var18 = new GenLayerZoomTFC(1000 + var7, var18);
			drawImage(512, var18, "18-"+var7+" Zoom");
			if (var7 == 0)
				var18 = new GenLayerAddIslandTFC(3L, var18);
			if (var7 == 1)
			{
				var18 = new GenLayerShoreTFC(1000L, var18);
				drawImage(512, var18, "18z Shore");
			}
		}

		//Create Rivers
		GenLayerTFC riverCont = GenLayerZoomTFC.magnify(1000L, continent, 2);
		drawImage(512, riverCont, "9 ContinentsZoom");
		riverCont = new GenLayerRiverInitTFC(100L, riverCont);
		drawImage(512, riverCont, "10 RiverInit");
		riverCont = GenLayerZoomTFC.magnify(1000L, riverCont, 6);
		drawImage(512, riverCont, "11 RiverInitZoom");
		riverCont = new GenLayerRiverTFC(1L, riverCont);
		drawImage(512, riverCont, "12 River");
		riverCont = new GenLayerSmoothTFC(1000L, riverCont);
		drawImage(512, riverCont, "13 SmoothRiver");

		GenLayerSmoothBiomeTFC smoothContinent = new GenLayerSmoothBiomeTFC(1000L, var18);
		drawImage(512, smoothContinent, "Biome 19");
		GenLayerRiverMixTFC riverMix = new GenLayerRiverMixTFC(100L, smoothContinent, riverCont);
		drawImage(512, riverMix, "Biome 20");
		GenLayerTFC finalCont = GenLayerZoomTFC.magnify(1000L, riverMix, 2);
		drawImage(512, finalCont, "Biome 20-zoom");
		finalCont = new GenLayerSmoothBiomeTFC(1001L, finalCont);
		drawImage(512, finalCont, "Biome 21");
		riverMix.initWorldGenSeed(par0);
		finalCont.initWorldGenSeed(par0);
		return new GenLayerTFC[]{riverMix, finalCont};
	}

	public static GenLayerTFC genContinent(long seed, boolean oceanReduction)
	{
		GenLayerTFC continentStart = new GenLayerIslandTFC(1L+seed);
		drawImage(512, continentStart, "0 ContinentsStart");
		GenLayerFuzzyZoomTFC continentFuzzyZoom = new GenLayerFuzzyZoomTFC(2000L, continentStart);
		drawImage(512, continentFuzzyZoom, "1 ContinentsFuzzyZoom");
		GenLayerTFC var10 = new GenLayerAddIslandTFC(1L, continentFuzzyZoom);
		drawImage(512, var10, "2 ContinentsAddIsland");
		GenLayerTFC var11 = new GenLayerZoomTFC(2001L, var10);
		drawImage(512, var11, "3 ContinentsAddIslandZoom");
		var10 = new GenLayerAddIslandTFC(2L, var11);
		drawImage(512, var10, "4 ContinentsAddIsland2");
		var11 = new GenLayerZoomTFC(2002L, var10);
		drawImage(512, var11, "5 ContinentsAddIslandZoom2");
		var10 = new GenLayerAddIslandTFC(3L, var11);
		drawImage(512, var10, "6 ContinentsAddIsland3");
		var11 = new GenLayerZoomTFC(2003L, var10);
		drawImage(512, var11, "7 ContinentsAddIslandZoom3");
		GenLayerTFC continent = new GenLayerAddIslandTFC(4L, var11);
		drawImage(512, continent, "8 ContinentsDone");
		return continent;
	}

	private static boolean shouldDraw;
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
			TerraFirmaCraft.LOG.info(name + ".bmp");
			for(int x = 0; x < size; x++)
			{
				for(int z = 0; z < size; z++)
				{
					if(ints[x*size+z] != -1 && TFCBiome.getBiomeGenArray()[ints[x*size+z]] != null)
					{
						graphics.setColor(Color.getColor("", TFCBiome.getBiome(ints[x*size+z]).getBiomeColor()));	
						graphics.drawRect(x, z, 1, 1);
					}
				}
			}
			TerraFirmaCraft.LOG.info(name + ".bmp");
			ImageIO.write(outBitmap, "BMP", outFile);
		}
		catch (Exception e) 
		{
			TerraFirmaCraft.LOG.catching(e);
		}
	}

	public GenLayerTFC(long par1)
	{
		super(par1);
		this.baseSeed = par1;
		this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
		this.baseSeed += par1;
		this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
		this.baseSeed += par1;
		this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
		this.baseSeed += par1;

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

	public static int validateInt(int[] array, int index)
	{
		/*if(TFCBiome.biomeList[array[index]] == null)
			TerraFirmaCraft.log.error("Error garbage data: "+array[index]);*/
		return array[index];
	}

	public static void validateIntArray(int[] array, int xSize, int zSize)
	{
		for(int z = 0; z < zSize; z++)
		{
			for(int x = 0; x < xSize; x++)
			{
				if(TFCBiome.biomeList[array[x+z*xSize]] == null)
				{
					TerraFirmaCraft.LOG.error("Error Array garbage data: " + array[x + z * xSize]);
					return;
				}
			}
		}
	}
}
