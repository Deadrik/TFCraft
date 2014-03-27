package TFC.WorldGen.Biomes;

import net.minecraft.world.gen.feature.WorldGenerator;
import TFC.Entities.Mobs.EntityPigTFC;
import TFC.WorldGen.BiomeDecoratorTFC;
import TFC.WorldGen.TFCBiome;

// Referenced classes of package net.minecraft.src:
//            BiomeGenBase, SpawnListEntry, EntityWolf, BiomeDecorator, 
//            WorldGenerator

public class BiomeGenSwampTFC extends TFCBiome
{

	WorldGenerator[] HardwoodGenList = {worldGenWillowShortTrees,worldGenWillowShortTrees,worldGenAshShortTrees,worldGenWhiteElmShortTrees,
			worldGenOakShortTrees,worldGenBirchShortTrees,worldGenAshTallTrees,worldGenWhiteElmTallTrees,
			worldGenOakTallTrees,worldGenBirchTallTrees,worldGenBirchTallTrees,worldGenMapleShortTrees,worldGenAspenShortTrees,worldGenAspenTallTrees};

	WorldGenerator[] ConiferGenList = {worldGenWhiteCedarTallTrees,worldGenPineShortTrees,worldGenPineTallTrees,worldGenSpruceShortTrees,worldGenSpruceTallTrees,
			worldGenBirchShortTrees,worldGenAshTallTrees,worldGenWhiteElmTallTrees,worldGenWhiteElmShortTrees};

	public BiomeGenSwampTFC(int i)
	{
		super(i);
		this.spawnableCreatureList.add(new SpawnListEntry(EntityPigTFC.class, 2, 1, 3));
		((BiomeDecoratorTFC)this.theBiomeDecorator).treesPerChunk = 4;
		((BiomeDecoratorTFC)this.theBiomeDecorator).flowersPerChunk = -999;
		((BiomeDecoratorTFC)this.theBiomeDecorator).deadBushPerChunk = 1;
		((BiomeDecoratorTFC)this.theBiomeDecorator).mushroomsPerChunk = 8;
		((BiomeDecoratorTFC)this.theBiomeDecorator).reedsPerChunk = 2;
		((BiomeDecoratorTFC)this.theBiomeDecorator).clayPerChunk = 2;
		((BiomeDecoratorTFC)this.theBiomeDecorator).waterlilyPerChunk = 4;
	}
}
