package TFC.WorldGen.Biomes;

import TFC.Entities.Mobs.EntityPigTFC;
import TFC.WorldGen.TFCBiome;

public class BiomeGenRiverTFC extends TFCBiome
{
	public BiomeGenRiverTFC(int i)
	{
		super(i);
		//spawnableCreatureList.clear();
		this.spawnableCreatureList.add(new SpawnListEntry(EntityPigTFC.class, 1, 1, 1));
		setMinMaxHeight(-0.8F, -0.4F);
	}
}
