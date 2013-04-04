package TFC.WorldGen.Biomes;

import TFC.WorldGen.BiomeDecoratorTFC;
import TFC.WorldGen.TFCBiome;

// Referenced classes of package net.minecraft.src:
//            BiomeGenBase, SpawnListEntry, EntityWolf, BiomeDecorator, 
//            WorldGenerator

public class BiomeGenPlainsTFC extends TFCBiome
{
	public BiomeGenPlainsTFC(int i)
	{
		super(i);
		((BiomeDecoratorTFC)this.theBiomeDecorator).treesPerChunk = -999;
		((BiomeDecoratorTFC)this.theBiomeDecorator).flowersPerChunk = 4;
		((BiomeDecoratorTFC)this.theBiomeDecorator).grassPerChunk = 20;
	}
}
