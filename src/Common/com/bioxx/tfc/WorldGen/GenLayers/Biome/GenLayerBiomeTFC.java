package com.bioxx.tfc.WorldGen.GenLayers.Biome;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.WorldGen.GenLayers.GenLayerTFC;
import com.bioxx.tfc.WorldGen.TFCBiome;
import com.bioxx.tfc.WorldGen.TFCWorldType;

public class GenLayerBiomeTFC extends GenLayerTFC
{
	/** this sets all the biomes that are allowed to appear in the overworld */
	private TFCBiome[] allowedBiomes = new TFCBiome[] {
			TFCBiome.ocean,
			TFCBiome.HighHills,
			TFCBiome.plains,
			TFCBiome.HighPlains,
			TFCBiome.swampland,
			TFCBiome.rollingHills,
			TFCBiome.Mountains,
	};

	public GenLayerBiomeTFC(long par1, GenLayer par3GenLayer, TFCWorldType par4)
	{
		super(par1);
		this.parent = (GenLayerTFC) par3GenLayer;
	}

	/**
	 * Returns a list of integer values generated by this layer. These may be interpreted as temperatures, rainfall
	 * amounts, or biomeList[] indices based on the particular GenLayer subclass.
	 */
	@Override
	public int[] getInts(int par1, int par2, int par3, int par4)
	{
		int[] var5 = this.parent.getInts(par1, par2, par3, par4);
		validateIntArray(var5, par3, par4);
		int[] var6 = IntCache.getIntCache(par3 * par4);

		for (int var7 = 0; var7 < par4; ++var7)
		{
			for (int var8 = 0; var8 < par3; ++var8)
			{
				this.initChunkSeed(var8 + par1, var7 + par2);
				int id = var5[var8 + var7 * par3];
				if (TFC_Core.isOceanicBiome(id))
					var6[var8 + var7 * par3] = id;
				else
					var6[var8 + var7 * par3] = this.allowedBiomes[this.nextInt(this.allowedBiomes.length)].biomeID;

				validateInt(var6, var8 + var7 * par3);
			}
		}
		return var6;
	}
}
