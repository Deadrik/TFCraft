package com.bioxx.tfc.WorldGen.GenLayers.DataLayers.Drainage;

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

public abstract class GenDrainageLayer extends GenLayerTFC
{
	public static final int MIN = DataLayer.DRAINAGE_NONE.layerID;
	public static final int MAX = DataLayer.DRAINAGE_VERY_GOOD.layerID;
	public static GenLayerTFC initialize(long seed, WorldType worldType)
	{
		GenLayerTFC continent = genContinent(0);
		drawImage(512,continent, "Drainage 0");
		continent = GenLayerZoomTFC.magnify(1000L, continent, 2);
		continent = new GenLayerSmoothTFC(1000L, continent);
		drawImage(512,continent, "Drainage 1");
		for (int zoomLevel = 0; zoomLevel < 2; ++zoomLevel)
		{
			if(zoomLevel == 0)
			{
				continent = new GenLayerDrainageMix(1000 + zoomLevel, continent);
				drawImage(512,continent , "Drainage 2-" + zoomLevel +" Mix");
			}
			continent = new GenLayerZoomTFC(1000 + zoomLevel, continent);
			drawImage(512,continent , "Drainage 2-" + zoomLevel +" Smoothed");
		}

		GenLayerSmoothTFC finalCont = new GenLayerSmoothTFC(1000L, continent);
		//GenLayerTFC voronoiZoom = new GenLayerVoronoiZoomTFC(10L, finalCont);
		drawImage(512,finalCont, "Drainage Final");
		finalCont.initWorldGenSeed(seed);
		return finalCont;
	}

	public static GenLayerTFC genContinent(long seed)
	{
		GenLayerTFC continent = new GenLayerDrainageInit(1L+seed);
		drawImage(512, continent, "Drainage Init 0");
		continent = new GenLayerAddDrainage(1L, continent);
		drawImage(512, continent, "Drainage Init 0b Add Drainage");
		continent = new GenLayerFuzzyZoomTFC(2000L, continent);
		drawImage(512, continent, "Drainage Init 1");
		continent = new GenLayerAddDrainage(1L, continent);
		drawImage(512, continent, "Drainage Init 2 Add Drainage");
		continent = new GenLayerZoomTFC(2001L, continent);
		drawImage(512, continent, "Drainage Init 3 Zoom");
		continent = new GenLayerAddDrainage(2L, continent);
		drawImage(512, continent, "Drainage Init 4 Add Drainage");
		continent = new GenLayerDrainageMix(88L, continent);
		drawImage(512,continent , "Drainage Init 4b Mix");
		continent = new GenLayerZoomTFC(2002L, continent);
		drawImage(512, continent, "Drainage Init 5 Zoom");
		continent = new GenLayerAddDrainage(3L, continent);
		drawImage(512, continent, "Drainage Init 6 Add Drainage");
		continent = new GenLayerZoomTFC(2003L, continent);
		drawImage(512, continent, "Drainage Init 7 Zoom");
		continent = new GenLayerAddDrainage(4L, continent);
		drawImage(512, continent, "Drainage Init 8 Add Drainage");
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
			Graphics2D graDrainageics = (Graphics2D) outBitmap.getGraphics();
			graDrainageics.clearRect(0, 0, size, size);
			TerraFirmaCraft.LOG.info("Starting " + name + ".bmp");
			for(int x = 0; x < size; x++)
			{
				for(int z = 0; z < size; z++)
				{
					int value = ints[x*size+z];
					int r = ((value-120)*42)/2 << 16;
					int g = ((value-120)*42)/4 << 8;
					if(value-120 >= 0 && value-120 <= 5)
						graDrainageics.setColor(Color.getColor("", r+g));	
					else
						graDrainageics.setColor(Color.getColor("", 0xffffff));	
					graDrainageics.drawRect(x, z, 1, 1);
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

	public GenDrainageLayer(long par1)
	{
		super(par1);
	}
}
