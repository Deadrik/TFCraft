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
		((BiomeDecoratorTFC)this.biomeDecorator).treesPerChunk = -999;
		((BiomeDecoratorTFC)this.biomeDecorator).deadBushPerChunk = 0;
		((BiomeDecoratorTFC)this.biomeDecorator).reedsPerChunk = 0;
		((BiomeDecoratorTFC)this.biomeDecorator).cactiPerChunk = 0;
	}
}
