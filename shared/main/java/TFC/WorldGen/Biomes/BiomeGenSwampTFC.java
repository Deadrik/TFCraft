package TFC.WorldGen.Biomes;

import net.minecraft.world.biome.SpawnListEntry;
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

	@Override
	protected float getMonthTemp(int month)
	{
		switch(month)
		{
		case 11:
			return 0.1F;
		case 0:
			return 0.15F;
		case 1:
			return 0.25F;
		case 2:
			return 0.8F;
		case 3:
			return 0.75F; 
		case 4:
			return 1F;
		case 5:
			return 0.75F;
		case 6:
			return 0.5F;
		case 7:
			return 0.25F;
		case 8:
			return 0.15F;
		case 9:
			return 0.1F;
		case 10:
			return 0.05F;
		default:
			return 1F;
		}
	}

	/**
	 * Provides the basic grass color based on the biome temperature and rainfall
	 */
	 //    public int getBiomeGrassColor()
	//    {
		//        double var1 = (double)this.getFloatTemperature();
		//        double var3 = (double)this.getIntRainfall();
		//        return ((ColorizerGrass.getGrassColor(var1, var3) & 16711422) + 5115470) / 2;
		//    }
	//
	//    /**
	//     * Provides the basic foliage color based on the biome temperature and rainfall
	//     */
	//    public int getBiomeFoliageColor()
	//    {
	//        double var1 = (double)this.getFloatTemperature();
	//        double var3 = (double)this.getIntRainfall();
	//        return ((ColorizerFoliage.getFoliageColor(var1, var3) & 16711422) + 5115470) / 2;
	//    }
}
