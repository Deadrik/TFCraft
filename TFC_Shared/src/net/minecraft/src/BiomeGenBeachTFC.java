package net.minecraft.src;

public class BiomeGenBeachTFC extends BiomeGenBase
{
	public BiomeGenBeachTFC(int i)
	{
		super(i);
		spawnableCreatureList.clear();
		topBlock = (byte)Block.sand.blockID;
		fillerBlock = (byte)Block.sand.blockID;
		biomeDecorator.treesPerChunk = -999;
		biomeDecorator.deadBushPerChunk = 0;
		biomeDecorator.reedsPerChunk = 0;
		biomeDecorator.cactiPerChunk = 0;
	}
}
