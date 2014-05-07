package com.bioxx.tfc.WorldGen.Biomes;

import com.bioxx.tfc.Entities.Mobs.EntityPigTFC;
import com.bioxx.tfc.WorldGen.TFCBiome;

import net.minecraft.world.gen.feature.WorldGenerator;

// Referenced classes of package net.minecraft.src:
//            BiomeGenBase, SpawnListEntry, EntityWolf, BiomeDecorator, 
//            WorldGenerator

public class BiomeGenSwampTFC extends TFCBiome
{
	WorldGenerator[] HardwoodGenList = {worldGenWillowShortTrees,worldGenWillowShortTrees,worldGenAshShortTrees,worldGenWhiteElmShortTrees,
			worldGenOakShortTrees,worldGenBirchShortTrees,worldGenAshTallTrees,worldGenWhiteElmTallTrees,
			worldGenOakTallTrees,worldGenBirchTallTrees,worldGenBirchTallTrees,worldGenMapleShortTrees,worldGenAspenShortTrees,worldGenAspenTallTrees};

	WorldGenerator[] ConiferGenList = {worldGenWhiteCedarTallTrees,worldGenPineShortTrees,worldGenPineTallTrees,worldGenSpruceShortTrees,worldGenSpruceTallTrees,
			worldGenBirchShortTrees,worldGenAshTallTrees,worldGenWhiteElmTallTrees,worldGenWhiteElmShortTrees};

	public BiomeGenSwampTFC(int i)
	{
		super(i);
		this.spawnableCreatureList.add(new SpawnListEntry(EntityPigTFC.class, 2, 1, 3));
		this.theBiomeDecorator.treesPerChunk = 4;
		this.theBiomeDecorator.flowersPerChunk = -999;
		this.theBiomeDecorator.deadBushPerChunk = 1;
		this.theBiomeDecorator.mushroomsPerChunk = 8;
		this.theBiomeDecorator.reedsPerChunk = 2;
		this.theBiomeDecorator.clayPerChunk = 2;
		this.theBiomeDecorator.lilyPadPerChunk = 4;
	}
}
