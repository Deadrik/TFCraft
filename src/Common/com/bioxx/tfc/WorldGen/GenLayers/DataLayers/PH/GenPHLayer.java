package com.bioxx.tfc.WorldGen.GenLayers.DataLayers.PH;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import net.minecraft.world.WorldType;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.WorldGen.DataLayer;
import com.bioxx.tfc.WorldGen.GenLayers.GenLayerFuzzyZoomTFC;
import com.bioxx.tfc.WorldGen.GenLayers.GenLayerSmoothTFC;
import com.bioxx.tfc.WorldGen.GenLayers.GenLayerTFC;
import com.bioxx.tfc.WorldGen.GenLayers.GenLayerZoomTFC;

public abstract class GenPHLayer extends GenLayerTFC
{
	public static final int MIN = DataLayer.PH_ACID_HIGH.layerID;
	public static final int MAX = DataLayer.PH_ALKALINE_HIGH.layerID;
	public static GenLayerTFC initialize(long seed, WorldType worldType)
	{
		GenLayerTFC continent = genContinent(0);
		drawImage(512,continent, "PH 0");
		continent = GenLayerZoomTFC.magnify(1000L, continent, 2);
		continent = new GenLayerSmoothTFC(1000L, continent);
		drawImage(512,continent, "PH 1");
		for (int zoomLevel = 0; zoomLevel < 2; ++zoomLevel)
		{
			if(zoomLevel == 0)
			{
				continent = new GenLayerPHMix(1000 + zoomLevel, continent);
				drawImage(512,continent , "PH 2-" + zoomLevel +" Mix");
			}
			continent = new GenLayerZoomTFC(1000 + zoomLevel, continent);
			drawImage(512,continent , "PH 2-" + zoomLevel +" Smoothed");
		}

		GenLayerSmoothTFC finalCont = new GenLayerSmoothTFC(1000L, continent);
		//GenLayerTFC voronoiZoom = new GenLayerVoronoiZoomTFC(10L, finalCont);
		drawImage(512,finalCont, "PH Final");
		finalCont.initWorldGenSeed(seed);
		return finalCont;
	}

	public static GenLayerTFC genContinent(long seed)
	{
		GenLayerTFC continent = new GenLayerPHInit(1L+seed);
		drawImage(512, continent, "PH Init 0");
		continent = new GenLayerAddPH(1L, continent);
		drawImage(512, continent, "PH Init 0b Add PH");
		continent = new GenLayerFuzzyZoomTFC(2000L, continent);
		drawImage(512, continent, "PH Init 1");
		continent = new GenLayerAddPH(1L, continent);
		drawImage(512, continent, "PH Init 2 Add PH");
		continent = new GenLayerZoomTFC(2001L, continent);
		drawImage(512, continent, "PH Init 3 Zoom");
		continent = new GenLayerAddPH(2L, continent);
		drawImage(512, continent, "PH Init 4 Add PH");
		continent = new GenLayerPHMix(88L, continent);
		drawImage(512,continent , "PH Init 4b Mix");
		continent = new GenLayerZoomTFC(2002L, continent);
		drawImage(512, continent, "PH Init 5 Zoom");
		continent = new GenLayerAddPH(3L, continent);
		drawImage(512, continent, "PH Init 6 Add PH");
		continent = new GenLayerZoomTFC(2003L, continent);
		drawImage(512, continent, "PH Init 7 Zoom");
		continent = new GenLayerAddPH(4L, continent);
		drawImage(512, continent, "PH Init 8 Add PH");
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
			TerraFirmaCraft.LOG.info("Starting " + name + ".bmp");
			for(int x = 0; x < size; x++)
			{
				for(int z = 0; z < size; z++)
				{
					int value = ints[x*size+z];
					if(value-130 >= 0 && value-130 <= 4)
						graphics.setColor(Color.getColor("", ((value-130)*32) << 8));	
					else
						graphics.setColor(Color.getColor("", 0xffffff));	
					graphics.drawRect(x, z, 1, 1);
				}
			}
			TerraFirmaCraft.LOG.info("Finished " + name + ".bmp");
			ImageIO.write(outBitmap, "BMP", outFile);
		}
		catch (Exception e) 
		{
			TerraFirmaCraft.LOG.catching(e);
		}
	}

	public GenPHLayer(long par1)
	{
		super(par1);
	}
}
