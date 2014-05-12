package com.bioxx.tfc.WorldGen.GenLayers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import net.minecraft.world.gen.layer.GenLayer;

import com.bioxx.tfc.WorldGen.TFCBiome;
import com.bioxx.tfc.WorldGen.TFCWorldType;

public abstract class GenLayerTFC extends GenLayer
{
	/** seed from World#getWorldSeed that is used in the LCG prng */
	private long worldGenSeed;

	/** parent GenLayer that was provided via the constructor */
	protected GenLayerTFC parent;

	/**
	 * final part of the LCG prng that uses the chunk X, Z coords along with the other two seeds to generate
	 * pseudorandom numbers
	 */
	private long chunkSeed;

	/** base seed to the LCG prng provided via the constructor */
	private long baseSeed;

	/**
	 * the first array item is a linked list of the bioms, the second is the zoom function, the third is the same as the
	 * first.
	 */
	public static GenLayerTFC[] initializeAllBiomeGenerators(long par0, TFCWorldType par2)
	{
		GenLayerTFC continent = genContinent(0, false);
		GenLayerTFC continent2 = genContinent(0, false);
		byte var4 = 4;

		//Create Biomes
		GenLayerTFC continentCopy2 = GenLayerZoomTFC.magnify(1000L, continent, 0);
		drawImage(512, continentCopy2, "14 ContinentsZoom");
		GenLayerBiomeTFC var17 = new GenLayerBiomeTFC(200L, continentCopy2, par2);
		drawImage(512, var17, "15 Biome");
		GenLayerLakes lakes = new GenLayerLakes(200L, var17);
		drawImage(512, var17, "15b Lakes");
		continentCopy2 = GenLayerZoomTFC.magnify(1000L, lakes, 2);
		drawImage(512, continentCopy2, "16 ZoomBiome");
		Object var18 = new GenLayerBiomeEdge(1000L, continentCopy2);
		drawImage(512, (GenLayerTFC)var18, "17 BiomeEdge");
		for (int var7 = 0; var7 < var4; ++var7)
		{
			var18 = new GenLayerZoomTFC((long)(1000 + var7), (GenLayerTFC)var18);
			drawImage(512, (GenLayerTFC)var18, "18-"+var7+" Zoom");
			if (var7 == 0)
				var18 = new GenLayerAddIslandTFC(3L, (GenLayerTFC)var18);
			if (var7 == 1)
			{
				var18 = new GenLayerShoreTFC(1000L, (GenLayerTFC)var18);
				drawImage(512, (GenLayerTFC)var18, "18z Shore");
			}
		}

		//Create Rivers
		GenLayerTFC continentCopy = GenLayerZoomTFC.magnify(1000L, continent2, 2);
		drawImage(512, continentCopy, "9 ContinentsZoom");
		GenLayerRiverInitTFC riverInit = new GenLayerRiverInitTFC(100L, continentCopy);
		drawImage(512, riverInit, "10 RiverInit");
		/*GenLayerRemoveOrphanRiver riverorphan = new GenLayerRemoveOrphanRiver(100L, riverInit);
		drawImage(512, riverorphan, "10b RiverOrphan");*/
		continentCopy = GenLayerZoomTFC.magnify(1000L, riverInit, var4+2);
		drawImage(512, continentCopy, "11 RiverInitZoom");
		GenLayerRiverTFC riverGen = new GenLayerRiverTFC(1L, continentCopy);
		drawImage(512, riverGen, "12 River");
		GenLayerSmoothTFC smoothRiver = new GenLayerSmoothTFC(1000L, riverGen);
		drawImage(512, smoothRiver, "13 SmoothRiver");

		GenLayerSmoothTFC var19 = new GenLayerSmoothTFC(1000L, (GenLayerTFC)var18);
		drawImage(512, var19, "19 SmoothBiome");
		GenLayerRiverMixTFC var20 = new GenLayerRiverMixTFC(100L, var19, smoothRiver);
		drawImage(512, var20, "20 BiomeRiverMix");
		GenLayerVoronoiZoomTFC var8 = new GenLayerVoronoiZoomTFC(10L, var20);
		drawImage(512, var8, "21 BiomeVoronoiZoom");
		var20.initWorldGenSeed(par0);
		var8.initWorldGenSeed(par0);
		return new GenLayerTFC[] {var20, var8, var20};
	}

	public static GenLayerTFC genContinent(long seed, boolean oceanReduction)
	{
		GenLayerIslandTFC continentStart = new GenLayerIslandTFC(1L+seed);
		drawImage(512, continentStart, "0 ContinentsStart");
		GenLayerFuzzyZoomTFC continentFuzzyZoom = new GenLayerFuzzyZoomTFC(2000L, continentStart);
		drawImage(512, continentFuzzyZoom, "1 ContinentsFuzzyZoom");
		GenLayerTFC var10 = new GenLayerAddIslandTFC(1L, continentFuzzyZoom);
		drawImage(512, var10, "2 ContinentsAddIsland");
		if(oceanReduction)
		{
			var10 = new GenLayerRemoveOcean(2002L, var10, 12, true);
			drawImage(512, var10, "2 RemoveOcean");
		}
		GenLayerTFC var11 = new GenLayerZoomTFC(2001L, var10);
		drawImage(512, var11, "3 ContinentsAddIslandZoom");
		var10 = new GenLayerAddIslandTFC(2L, var11);
		drawImage(512, var10, "4 ContinentsAddIsland2");
		var11 = new GenLayerZoomTFC(2002L, var10);
		drawImage(512, var11, "5 ContinentsAddIslandZoom2");
		var10 = new GenLayerAddIslandTFC(3L, var11);
		drawImage(512, var10, "6 ContinentsAddIsland3");
		if(oceanReduction)
		{
			var10 = new GenLayerRemoveOcean(2002L, var10, 1, false);
			drawImage(512, var10, "6 RemoveOcean");
		}
		var11 = new GenLayerZoomTFC(2003L, var10);
		drawImage(512, var11, "7 ContinentsAddIslandZoom3");
		GenLayerTFC continent = new GenLayerAddIslandTFC(4L, var11);
		drawImage(512, continent, "8 ContinentsDone");
		continent = new GenLayerDeepOcean(4L, var11);
		drawImage(512, continent, "8b ContinentsDone DeepOcean");

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
			System.out.println(name+".bmp");
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
			System.out.println(name+".bmp");
			ImageIO.write(outBitmap, "BMP", outFile);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	public GenLayerTFC(long par1)
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
