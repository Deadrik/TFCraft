// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package TFC.WorldGen.Biomes;

import net.minecraft.world.biome.SpawnListEntry;
import TFC.Entities.Mobs.EntityBear;
import TFC.Entities.Mobs.EntitySheepTFC;
import TFC.Entities.Mobs.EntityWolfTFC;
import TFC.WorldGen.BiomeDecoratorTFC;
import TFC.WorldGen.TFCBiome;


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
		((BiomeDecoratorTFC)this.theBiomeDecorator).treesPerChunk = 11;
		((BiomeDecoratorTFC)this.theBiomeDecorator).grassPerChunk = 2;
	}
}