package TFC.WorldGen.Biomes;

import net.minecraft.world.biome.SpawnListEntry;
import TFC.Entities.Mobs.EntityPigTFC;
import TFC.WorldGen.BiomeDecoratorTFC;
import TFC.WorldGen.TFCBiome;

public class BiomeGenRiverTFC extends TFCBiome
{
	public BiomeGenRiverTFC(int i)
	{
		super(i);
		//spawnableCreatureList.clear();
		this.spawnableCreatureList.add(new SpawnListEntry(EntityPigTFC.class, 1, 1, 1));
		((BiomeDecoratorTFC)this.theBiomeDecorator).looseRocksPerChunk = 4;
		((BiomeDecoratorTFC)this.theBiomeDecorator).looseRocksChancePerChunk = 90;
		setMinMaxHeight(-0.8F, -0.4F);
	}
}
