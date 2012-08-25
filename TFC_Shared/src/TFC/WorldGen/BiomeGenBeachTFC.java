package TFC.WorldGen;

import net.minecraft.src.Block;

public class BiomeGenBeachTFC extends TFCBiome
{
	public BiomeGenBeachTFC(int i)
	{
		super(i);
		spawnableCreatureList.clear();
		topBlock = (byte)Block.sand.blockID;
		fillerBlock = (byte)Block.sand.blockID;
		((BiomeDecoratorTFC)this.theBiomeDecorator).treesPerChunk = -999;
		((BiomeDecoratorTFC)this.theBiomeDecorator).deadBushPerChunk = 0;
		((BiomeDecoratorTFC)this.theBiomeDecorator).reedsPerChunk = 0;
		((BiomeDecoratorTFC)this.theBiomeDecorator).cactiPerChunk = 0;
	}
}
