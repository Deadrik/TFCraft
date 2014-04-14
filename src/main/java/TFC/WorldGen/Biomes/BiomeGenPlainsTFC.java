package TFC.WorldGen.Biomes;

import TFC.Entities.Mobs.EntityCowTFC;
import TFC.Entities.Mobs.EntityPigTFC;
import TFC.WorldGen.TFCBiome;

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
