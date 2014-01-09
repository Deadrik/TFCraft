package TFC.WorldGen.Biomes;

import net.minecraft.world.biome.SpawnListEntry;
import TFC.Entities.Mobs.EntitySquidTFC;
import TFC.WorldGen.TFCBiome;

public class BiomeGenOceanTFC extends TFCBiome
{
	public BiomeGenOceanTFC(int i)
	{
		super(i);
		spawnableCreatureList.clear();
		this.spawnableWaterCreatureList.add(new SpawnListEntry(EntitySquidTFC.class, 6, 1, 3));
	}
}
