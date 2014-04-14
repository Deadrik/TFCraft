package TFC.WorldGen.Biomes;

import TFC.TFCBlocks;
import TFC.WorldGen.TFCBiome;

public class BiomeGenBeachTFC extends TFCBiome
{
	public BiomeGenBeachTFC(int i)
	{
		super(i);
		spawnableCreatureList.clear();
		topBlock = TFCBlocks.Sand;
		fillerBlock = TFCBlocks.Sand;
		this.theBiomeDecorator.treesPerChunk = -999;
		this.theBiomeDecorator.deadBushPerChunk = 0;
		this.theBiomeDecorator.reedsPerChunk = 0;
		this.theBiomeDecorator.cactiPerChunk = 0;
	}
}
