package com.bioxx.tfc.WorldGen.GenLayers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import net.minecraft.world.WorldType;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerEdge;
import net.minecraft.world.gen.layer.GenLayerRemoveTooMuchOcean;

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

	public static GenLayerTFC[] initializeAllBiomeGenerators(long par0, TFCWorldType worldType)
	{
		boolean flag = false;
		GenLayerIslandTFC genlayerisland = new GenLayerIslandTFC(1L);
		GenLayerFuzzyZoomTFC genlayerfuzzyzoom = new GenLayerFuzzyZoomTFC(2000L, genlayerisland);
		GenLayerAddIslandTFC genlayeraddisland = new GenLayerAddIslandTFC(1L, genlayerfuzzyzoom);
		GenLayerZoomTFC genlayerzoom = new GenLayerZoomTFC(2001L, genlayeraddisland);
		genlayeraddisland = new GenLayerAddIslandTFC(2L, genlayerzoom);
		genlayeraddisland = new GenLayerAddIslandTFC(50L, genlayeraddisland);
		genlayeraddisland = new GenLayerAddIslandTFC(70L, genlayeraddisland);
		GenLayerRemoveTooMuchOcean genlayerremovetoomuchocean = new GenLayerRemoveTooMuchOcean(2L, genlayeraddisland);
		GenLayerAddSnowTFC genlayeraddsnow = new GenLayerAddSnowTFC(2L, genlayeraddisland);
		genlayeraddisland = new GenLayerAddIslandTFC(3L, genlayeraddsnow);
		GenLayerEdgeTFC genlayeredge = new GenLayerEdgeTFC(2L, genlayeraddisland, GenLayerEdge.Mode.COOL_WARM);
		genlayeredge = new GenLayerEdgeTFC(2L, genlayeredge, GenLayerEdge.Mode.HEAT_ICE);
		genlayeredge = new GenLayerEdgeTFC(3L, genlayeredge, GenLayerEdge.Mode.SPECIAL);
		genlayerzoom = new GenLayerZoomTFC(2002L, genlayeredge);
		genlayerzoom = new GenLayerZoomTFC(2003L, genlayerzoom);
		genlayeraddisland = new GenLayerAddIslandTFC(4L, genlayerzoom);
		GenLayerDeepOceanTFC genlayerdeepocean = new GenLayerDeepOceanTFC(4L, genlayeraddisland);
		GenLayerTFC genlayer2 = GenLayerZoomTFC.magnify(1000L, genlayerdeepocean, 0);
		byte zoomLevel = 4;

		if (worldType == WorldType.LARGE_BIOMES)
		{
			zoomLevel = 6;
		}

		if (flag)
		{
			zoomLevel = 4;
		}
		//zoomLevel = getModdedBiomeSize(par2WorldType, zoomLevel);

		GenLayerTFC genlayer = GenLayerZoomTFC.magnify(1000L, genlayer2, 0);
		GenLayerRiverInitTFC genlayerriverinit = new GenLayerRiverInitTFC(100L, genlayer);
		Object object = worldType.getBiomeLayer(par0, genlayer2);

		GenLayerTFC genlayer1 = GenLayerZoomTFC.magnify(1000L, genlayerriverinit, 2);
		GenLayerHillsTFC genlayerhills = new GenLayerHillsTFC(1000L, (GenLayerTFC)object, genlayer1);
		genlayer = GenLayerZoomTFC.magnify(1000L, genlayerriverinit, 2);
		genlayer = GenLayerZoomTFC.magnify(1000L, genlayer, zoomLevel);
		GenLayerRiverTFC genlayerriver = new GenLayerRiverTFC(1L, genlayer);
		GenLayerSmoothTFC genlayersmooth = new GenLayerSmoothTFC(1000L, genlayerriver);
		object = new GenLayerRareBiomeTFC(1001L, genlayerhills);

		for (int j = 0; j < zoomLevel; ++j)
		{
			object = new GenLayerZoomTFC((long)(1000 + j), (GenLayerTFC)object);

			if (j == 0)
			{
				object = new GenLayerAddIslandTFC(3L, (GenLayerTFC)object);
			}

			if (j == 1)
			{
				object = new GenLayerShoreTFC(1000L, (GenLayerTFC)object);
			}
		}

		GenLayerSmoothTFC genlayersmooth1 = new GenLayerSmoothTFC(1000L, (GenLayerTFC)object);
		GenLayerRiverMixTFC genlayerrivermix = new GenLayerRiverMixTFC(100L, genlayersmooth1, genlayersmooth);
		GenLayerVoronoiZoomTFC genlayervoronoizoom = new GenLayerVoronoiZoomTFC(10L, genlayerrivermix);
		genlayerrivermix.initWorldGenSeed(par0);
		genlayervoronoizoom.initWorldGenSeed(par0);
		return new GenLayerTFC[] {genlayerrivermix, genlayervoronoizoom, genlayerrivermix};
	}

	public static GenLayerTFC[] initializeAllBiomeGeneratorsOld(long seed, TFCWorldType worldType)
	{
		GenLayerIslandTFC var3 = new GenLayerIslandTFC(1L);
		//CreateImage(var3, "stage0", 0, 1, 256);
		GenLayerFuzzyZoomTFC var9 = new GenLayerFuzzyZoomTFC(2000L, var3);
		//CreateImage(var9, "stage1", 0, 1, 256);
		GenLayerAddIslandTFC var10 = new GenLayerAddIslandTFC(1L, var9);
		//CreateImage(var10, "stage2", 0, 1, 256);
		GenLayerZoomTFC var11 = new GenLayerZoomTFC(2001L, var10);
		//CreateImage(var11, "stage3", 0, 1, 256);
		var10 = new GenLayerAddIslandTFC(2L, var11);
		//CreateImage(var10, "stage4", 0, 1, 256);
		GenLayerAddSnowTFC var12 = new GenLayerAddSnowTFC(2L, var10);
		//CreateImage(var12, "stage5", 0, 1, 256);
		var11 = new GenLayerZoomTFC(2002L, var12);
		//CreateImage(var11, "stage6", 0, 1, 256);
		var10 = new GenLayerAddIslandTFC(3L, var11);
		//CreateImage(var10, "stage7", 0, 1, 256);
		var11 = new GenLayerZoomTFC(2003L, var10);
		//CreateImage(var11, "stage8", 0, 1, 256);
		var10 = new GenLayerAddIslandTFC(4L, var11);
		//CreateImage(var10, "stage9", 0, 1, 256);
		byte zoom = 4;

		GenLayerTFC var5 = GenLayerZoomTFC.magnify(1000L, var10, 0);
		//CreateImage(var5, "stage10", 0, 1, 256);
		GenLayerRiverInitTFC riverInit = new GenLayerRiverInitTFC(100L, var5);
		//CreateImage(riverInit, "stage11", 0, 255, 256);
		//Zoom the continental map so that they are larger
		var5 = GenLayerZoomTFC.magnify(1000L, riverInit, zoom);
		//CreateImage(var5, "stage12", 0, 255, 256);
		GenLayerRiverTFC var14 = new GenLayerRiverTFC(1L, var5);
		//CreateImage(var14, "stage13", 0, 255, 256);
		GenLayerSmoothTFC var15 = new GenLayerSmoothTFC(1000L, var14);
		//CreateImage(var15, "stage14", 0, 255, 256);
		//Finished making the land/water map
		//Start creating the biome map on top of the land/water map
		GenLayerTFC var6 = GenLayerZoomTFC.magnify(1000L, var10, 0);
		//CreateImage(var6, "stage15", 0, 255, 256, 0xff);
		GenLayerBiomeTFC var17 = new GenLayerBiomeTFC(200L, var6, worldType);
		//CreateImage(var17, "stage16", 0, 255, 256, 0xff);
		var6 = GenLayerZoomTFC.magnify(1000L, var17, 2);
		/*Object var18 = new GenLayerHillsTFC(1000L, var6);

		for (int var7 = 0; var7 < zoom; ++var7)
		{
			var18 = new GenLayerZoomTFC((long)(1000 + var7), (GenLayerTFC)var18);
			if (var7 == 0)
				var18 = new GenLayerAddIslandTFC(3L, (GenLayerTFC)var18);
			if (var7 == 1)
				var18 = new GenLayerShoreTFC(1000L, (GenLayerTFC)var18);
			if (var7 == 1)
				var18 = new GenLayerSwampRiversTFC(1000L, (GenLayerTFC)var18);
		}

		GenLayerSmoothTFC var19 = new GenLayerSmoothTFC(1000L, (GenLayerTFC)var18);*/
		GenLayerSmoothTFC var19 = new GenLayerSmoothTFC(1000L, var6);
		GenLayerRiverMixTFC var20 = new GenLayerRiverMixTFC(100L, var19, var15);
		GenLayerVoronoiZoomTFC var8 = new GenLayerVoronoiZoomTFC(10L, var20);
		var20.initWorldGenSeed(seed);
		var8.initWorldGenSeed(seed);
		//CreateImage(var8, "stage final", 0, 256, 256, 0xFF);
		return new GenLayerTFC[] {var20, var8, var20};
	}

	private static void CreateImage(GenLayer layer, String fileName, int scaleMin, int scaleMax, int size)
	{
		BufferedImage outBitmap = new BufferedImage(size,size,BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) outBitmap.getGraphics();

		try 
		{
			g.clearRect(0, 0, size, size);
			System.out.println("Starting "+fileName);
			int[] ints = layer.getInts(0, 0, size, size);
			for(int x = 0; x < size; x++)
			{
				for(int z = 0; z < size; z++)
				{
					int elev = (Math.max(Math.min(ints[x+z*size], scaleMax),scaleMin) /*& mask*/) * 255;
					g.setColor(Color.getColor("", (elev << 16) + (elev << 8) + elev));				
					g.drawRect(x, z, 1, 1);
				}
			}
			System.out.println("Finished "+fileName);
			ImageIO.write(outBitmap, "BMP", new File(fileName+".bmp"));
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	private static void CreateImage(GenLayer layer, String fileName, int scaleMin, int scaleMax, int size, int mask)
	{
		BufferedImage outBitmap = new BufferedImage(size,size,BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) outBitmap.getGraphics();

		try 
		{
			g.clearRect(0, 0, size, size);
			System.out.println("Starting "+fileName);
			int[] ints = layer.getInts(0, 0, size, size);
			for(int x = 0; x < size; x++)
			{
				for(int z = 0; z < size; z++)
				{
					int elev = (Math.max(Math.min(ints[x+z*size], scaleMax),scaleMin) & mask);
					g.setColor(Color.getColor("", (elev << 16) + (elev << 8) + elev));				
					g.drawRect(x, z, 1, 1);
				}
			}
			System.out.println("Finished "+fileName);
			ImageIO.write(outBitmap, "BMP", new File(fileName+".bmp"));
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	private static Graphics createWindow() 
	{
		JFrame frame = new JFrame("Visualizer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setLayout(null);
		frame.setLocation(100, 100); 
		frame.setPreferredSize(new Dimension(512+50, 512+50));
		frame.pack(); 
		frame.setVisible(true); 
		frame.getGraphics().clearRect(0, 0, 512, 512);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 512, 512);
		panel.setBackground(Color.BLACK);

		frame.getContentPane().add(panel);
		return frame.getGraphics();

	} 

	public GenLayerTFC(long par1)
	{
		super(par1);
	}
}
