// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.Random;

// Referenced classes of package net.minecraft.src:
//            BiomeGenBase, SpawnListEntry, EntityWolf, BiomeDecorator, 
//            WorldGenerator

public class BiomeGenSwampTFC extends BiomeGenBase
{
    int swampType;
    int treeCommon1 = -1;
    Boolean treeCommon1Size;
    int treeCommon2 = -1;
    Boolean treeCommon2Size;
    int treeUncommon = -1;
    Boolean treeUncommonSize;
    int treeRare = -1;
    Boolean treeRareSize;

    WorldGenerator[] HardwoodGenList = {worldGenWillowShortTrees,worldGenWillowShortTrees,worldGenAshShortTrees,worldGenWhiteElmShortTrees,
            worldGenOakShortTrees,worldGenBirchShortTrees,worldGenAshTallTrees,worldGenWhiteElmTallTrees,
            worldGenOakTallTrees,worldGenBirchTallTrees,worldGenBirchTallTrees,worldGenMapleShortTrees,worldGenAspenShortTrees,worldGenAspenTallTrees};

    WorldGenerator[] ConiferGenList = {worldGenWhiteCedarTallTrees,worldGenPineShortTrees,worldGenPineTallTrees,worldGenSpruceShortTrees,worldGenSpruceTallTrees,
            worldGenBirchShortTrees,worldGenAshTallTrees,worldGenWhiteElmTallTrees,worldGenWhiteElmShortTrees};

    public BiomeGenSwampTFC(int i)
    {
        super(i);
        biomeDecorator.treesPerChunk = 4;
        biomeDecorator.flowersPerChunk = -999;
        biomeDecorator.deadBushPerChunk = 1;
        biomeDecorator.mushroomsPerChunk = 8;
        biomeDecorator.reedsPerChunk = 10;
        biomeDecorator.clayPerChunk = 2;
        biomeDecorator.waterlilyPerChunk = 4;
        swampType = -1;
        treeCommon1 = -1;
        treeCommon2 = -1;
        treeUncommon = -1;
        treeRare = -1;
    }

    @Override
    public WorldGenerator getRandomWorldGenForTrees(Random random, World world)
    {


        if(swampType == -1)
        {
            Random R = new Random(world.getSeed() + this.biomeID);
            swampType = R.nextInt(100);

        }

        if(this.temperature > 27)
        {
            int rand = random.nextInt(100);
            if(rand < 40) {
                return HardwoodGenList[treeCommon1];
            } else if(rand < 80) {
                return HardwoodGenList[treeCommon2];
            } else if(rand < 95) {
                return HardwoodGenList[treeUncommon];
            } else {
                return HardwoodGenList[treeRare];
            }
        }
        else
        {
            int rand = random.nextInt(100);
            if(rand < 40) {
                return ConiferGenList[treeCommon1];
            } else if(rand < 80) {
                return ConiferGenList[treeCommon2];
            } else if(rand < 95) {
                return ConiferGenList[treeUncommon];
            } else {
                return ConiferGenList[treeRare];
            }	
        }
    }

    public void SetupTrees(World world, Random R)
    {
        if(this.temperature > 27)
        {
            while(treeCommon1 == -1 && treeCommon2 == -1 && treeUncommon == -1 && treeRare == -1)
            {
                treeCommon1 = 0;
                treeCommon1Size = R.nextBoolean();
                treeCommon2 = R.nextInt(HardwoodGenList.length);
                treeCommon2Size = R.nextBoolean();
                treeUncommon = R.nextInt(HardwoodGenList.length);
                treeUncommonSize = R.nextBoolean();
                treeRare = R.nextInt(HardwoodGenList.length);
                treeRareSize = R.nextBoolean();
            }
        }
        else
        {
            while(treeCommon1 == -1 || treeCommon2 == -1 || treeUncommon == -1 || treeRare == -1)
            {
                biomeDecorator.treesPerChunk = 6;
                treeCommon1 = R.nextInt(ConiferGenList.length);
                treeCommon1Size = R.nextBoolean();
                treeCommon2 = R.nextInt(ConiferGenList.length);
                treeCommon2Size = R.nextBoolean();
                treeUncommon = R.nextInt(ConiferGenList.length);
                treeUncommonSize = R.nextBoolean();
                treeRare = R.nextInt(ConiferGenList.length);
                treeRareSize = R.nextBoolean();
            }
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
