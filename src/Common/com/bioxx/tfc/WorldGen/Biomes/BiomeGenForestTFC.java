package com.bioxx.tfc.WorldGen.Biomes;

import com.bioxx.tfc.Entities.Mobs.EntityBear;
import com.bioxx.tfc.Entities.Mobs.EntityChickenTFC;
import com.bioxx.tfc.Entities.Mobs.EntityPigTFC;
import com.bioxx.tfc.Entities.Mobs.EntityWolfTFC;
import com.bioxx.tfc.WorldGen.TFCBiome;

public class BiomeGenForestTFC extends TFCBiome
{
	public BiomeGenForestTFC(int i)
	{
		super(i);
		this.spawnableCreatureList.add(new SpawnListEntry(EntityWolfTFC.class, 1, 1, 3));
		this.spawnableCreatureList.add(new SpawnListEntry(EntityPigTFC.class, 2, 2, 3));
		this.spawnableCreatureList.add(new SpawnListEntry(EntityChickenTFC.class, 1, 1, 1));
		this.spawnableCreatureList.add(new SpawnListEntry(EntityBear.class, 1, 1, 1));
		//this.spawnableCreatureList.add(new SpawnListEntry(EntityDeer.class, 2, 1, 3));

		this.theBiomeDecorator.treesPerChunk = 10;
		this.theBiomeDecorator.grassPerChunk = 2;
	}

}
