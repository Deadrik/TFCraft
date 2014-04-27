// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package com.bioxx.tfc.WorldGen.Biomes;

import com.bioxx.tfc.Entities.Mobs.EntityBear;
import com.bioxx.tfc.Entities.Mobs.EntitySheepTFC;
import com.bioxx.tfc.Entities.Mobs.EntityWolfTFC;
import com.bioxx.tfc.WorldGen.TFCBiome;


// Referenced classes of package net.minecraft.src:
//            BiomeGenBase, SpawnListEntry, EntityWolf, BiomeDecorator, 
//            WorldGenerator

public class BiomeGenHillsTFC extends TFCBiome
{
	public BiomeGenHillsTFC(int i)
	{
		super(i);
		//spawnableCreatureList.clear();
		spawnableCreatureList.add(new SpawnListEntry(EntityWolfTFC.class, 2, 1, 3));
		spawnableCreatureList.add(new SpawnListEntry(EntityBear.class, 1, 1, 1));
		this.spawnableCreatureList.add(new SpawnListEntry(EntitySheepTFC.class, 2, 2, 4));
		this.theBiomeDecorator.treesPerChunk = 11;
		this.theBiomeDecorator.grassPerChunk = 2;
	}
}