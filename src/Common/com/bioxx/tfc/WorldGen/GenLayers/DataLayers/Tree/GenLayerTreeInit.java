package com.bioxx.tfc.WorldGen.GenLayers.DataLayers.Tree;

import net.minecraft.world.gen.layer.IntCache;

import com.bioxx.tfc.WorldGen.DataLayer;
import com.bioxx.tfc.WorldGen.GenLayers.GenLayerTFC;

public class GenLayerTreeInit extends GenLayerTFC
{
	DataLayer[] layerTrees;
	public GenLayerTreeInit(long par1, DataLayer[] trees)
	{
		super(par1);
		layerTrees = trees;
	}

	@Override
	public int[] getInts(int par1, int par2, int maxX, int maxZ)
	{
		int[] cache = IntCache.getIntCache(maxX * maxZ);

		for (int z = 0; z < maxZ; ++z)
		{
			for (int x = 0; x < maxX; ++x)
			{
				this.initChunkSeed(par1 + x, par2 + z);
				cache[x + z * maxX] = layerTrees[this.nextInt(layerTrees.length)].ID;
			}
		}

		return cache;
	}
}
