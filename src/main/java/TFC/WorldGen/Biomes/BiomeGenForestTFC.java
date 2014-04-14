package TFC.WorldGen.Biomes;

import TFC.Entities.Mobs.EntityBear;
import TFC.Entities.Mobs.EntityChickenTFC;
import TFC.Entities.Mobs.EntityDeer;
import TFC.Entities.Mobs.EntityPigTFC;
import TFC.Entities.Mobs.EntityWolfTFC;
import TFC.WorldGen.TFCBiome;

public class BiomeGenForestTFC extends TFCBiome
{
	public BiomeGenForestTFC(int i)
	{
		super(i);
		spawnableCreatureList.add(new SpawnListEntry(EntityWolfTFC.class, 1, 1, 3));
		this.spawnableCreatureList.add(new SpawnListEntry(EntityPigTFC.class, 2, 2, 3));
		this.spawnableCreatureList.add(new SpawnListEntry(EntityChickenTFC.class, 1, 1, 1));
		spawnableCreatureList.add(new SpawnListEntry(EntityBear.class, 1, 1, 1));
		spawnableCreatureList.add(new SpawnListEntry(EntityDeer.class, 2, 1, 3));

		this.theBiomeDecorator.treesPerChunk = 10;
		this.theBiomeDecorator.grassPerChunk = 2;
	}

}
