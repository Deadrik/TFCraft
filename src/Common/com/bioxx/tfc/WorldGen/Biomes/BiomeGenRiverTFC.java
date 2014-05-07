package com.bioxx.tfc.WorldGen.Biomes;

import com.bioxx.tfc.Entities.Mobs.EntityPigTFC;
import com.bioxx.tfc.WorldGen.TFCBiome;

public class BiomeGenRiverTFC extends TFCBiome
{
	public BiomeGenRiverTFC(int i)
	{
		super(i);
		//spawnableCreatureList.clear();
		this.spawnableCreatureList.add(new SpawnListEntry(EntityPigTFC.class, 1, 1, 1));
		this.setMinMaxHeight(-0.8F, -0.4F);
	}
}
