package com.bioxx.tfc.WorldGen.Biomes;

import com.bioxx.tfc.Entities.Mobs.EntitySquidTFC;
import com.bioxx.tfc.WorldGen.TFCBiome;

public class BiomeGenOceanTFC extends TFCBiome
{
	public BiomeGenOceanTFC(int i)
	{
		super(i);
		spawnableCreatureList.clear();
		this.spawnableWaterCreatureList.add(new SpawnListEntry(EntitySquidTFC.class, 6, 1, 3));
	}
}
