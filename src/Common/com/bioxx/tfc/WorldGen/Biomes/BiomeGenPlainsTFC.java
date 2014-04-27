package com.bioxx.tfc.WorldGen.Biomes;

import com.bioxx.tfc.Entities.Mobs.EntityCowTFC;
import com.bioxx.tfc.Entities.Mobs.EntityPigTFC;
import com.bioxx.tfc.WorldGen.TFCBiome;

// Referenced classes of package net.minecraft.src:
//            BiomeGenBase, SpawnListEntry, EntityWolf, BiomeDecorator, 
//            WorldGenerator

public class BiomeGenPlainsTFC extends TFCBiome
{
	public BiomeGenPlainsTFC(int i)
	{
		super(i);
		this.spawnableCreatureList.add(new SpawnListEntry(EntityCowTFC.class, 2, 2, 4));
		this.spawnableCreatureList.add(new SpawnListEntry(EntityPigTFC.class, 1, 1, 2));
		this.theBiomeDecorator.treesPerChunk = -999;
		this.theBiomeDecorator.flowersPerChunk = 4;
		this.theBiomeDecorator.grassPerChunk = 20;
	}
}
