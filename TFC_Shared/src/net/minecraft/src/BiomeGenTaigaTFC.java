// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.Random;


// Referenced classes of package net.minecraft.src:
//            BiomeGenBase, SpawnListEntry, EntityWolf, BiomeDecorator, 
//            WorldGenerator

public class BiomeGenTaigaTFC extends BiomeGenBase
{

	int treeCommon1 = -1;
	Boolean treeCommon1Size;
	int treeCommon2 = -1;
	Boolean treeCommon2Size;
	int treeUncommon = -1;
	Boolean treeUncommonSize;
	int treeRare = -1;
	Boolean treeRareSize;

	public BiomeGenTaigaTFC(int i)
	{
		super(i);
		spawnableCreatureList.add(new SpawnListEntry(net.minecraft.src.EntityWolf.class, 8, 4, 4));
		biomeDecorator.treesPerChunk = 10;
		biomeDecorator.grassPerChunk = 1;
		treeCommon1 = 0;
		treeCommon2 = 0;
		treeUncommon = 0;
		treeRare = 0;
		setMinMaxHeight(0.2F, 0.4F);
		((BiomeDecoratorTFC)this.biomeDecorator).looseRocksPerChunk = 4;
        ((BiomeDecoratorTFC)this.biomeDecorator).looseRocksChancePerChunk = 90;
	}

	public WorldGenerator getRandomWorldGenForTrees(Random random, World world)
	{
		
		int rand = random.nextInt(100);
		if(rand < 40) {
			return getTreeGen(treeCommon1,treeCommon1Size);
		} else if(rand < 80) {
			return getTreeGen(treeCommon2,treeCommon2Size);
		} else if(rand < 95) {
			return getTreeGen(treeUncommon,treeUncommonSize);
		} else {
			return getTreeGen(treeRare,treeRareSize);
		}
	}
	
	public void SetupTrees(World world, Random R)
    {
	    WorldGenerator[] ConiferGenList = {
	            worldGenRedwoodShortTrees,
                worldGenWhiteCedarTallTrees,
                worldGenPineShortTrees,
                worldGenSpruceShortTrees,
                worldGenOakShortTrees,
                worldGenBirchShortTrees,
                worldGenAshTallTrees,
                worldGenWhiteElmTallTrees,
                worldGenOakTallTrees,
                worldGenBirchTallTrees,
                worldGenPineTallTrees,
                worldGenAspenTallTrees,
                worldGenAspenShortTrees};

	   while(treeCommon1 == 0 || treeCommon2 == 0)
	   {
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
