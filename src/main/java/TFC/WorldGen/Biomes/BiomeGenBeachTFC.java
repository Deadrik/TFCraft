package TFC.WorldGen.Biomes;

import net.minecraft.init.Blocks;
import TFC.WorldGen.BiomeDecoratorTFC;
import TFC.WorldGen.TFCBiome;

public class BiomeGenBeachTFC extends TFCBiome
{
	public BiomeGenBeachTFC(int i)
	{
		super(i);
		spawnableCreatureList.clear();
		topBlock = Blocks.sand;
		fillerBlock = Blocks.sand;
		((BiomeDecoratorTFC)this.theBiomeDecorator).treesPerChunk = -999;
		((BiomeDecoratorTFC)this.theBiomeDecorator).deadBushPerChunk = 0;
		((BiomeDecoratorTFC)this.theBiomeDecorator).reedsPerChunk = 0;
		((BiomeDecoratorTFC)this.theBiomeDecorator).cactiPerChunk = 0;
	}
}
