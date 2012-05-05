// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.src;

import java.util.Random;

// Referenced classes of package net.minecraft.src:
//            BiomeGenBase, SpawnListEntry, EntityWolf, BiomeDecorator, 
//            WorldGenerator

public class BiomeGenForestTFC extends BiomeGenBase
{

	int treeCommon1 = -1;
	Boolean treeCommon1Size;
	int treeCommon2 = -1;
	Boolean treeCommon2Size;
	int treeUncommon = -1;
	Boolean treeUncommonSize;
	int treeRare = -1;
	Boolean treeRareSize;

	public BiomeGenForestTFC(int i)
	{
		super(i);
		spawnableCreatureList.add(new SpawnListEntry(net.minecraft.src.EntityWolf.class, 5, 4, 4));
		biomeDecorator.treesPerChunk = 10;
		biomeDecorator.grassPerChunk = 2;
		treeCommon1 = -1;
		treeCommon2 = -1;
		treeUncommon = -1;
		treeRare = -1;
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
        treeCommon1 = R.nextInt(13);
        treeCommon1Size = R.nextBoolean();
        treeCommon2 = R.nextInt(13);
        treeCommon2Size = R.nextBoolean();
        treeUncommon = R.nextInt(13);
        treeUncommonSize = R.nextBoolean();
        treeRare = R.nextInt(13);
        treeRareSize = R.nextBoolean();
        
        if(treeCommon1 == 15 || treeCommon1 == 9) {
            treeCommon1 = R.nextInt(8);
        }
        if(treeCommon2 == 15 || treeCommon2 == 9) {
            treeCommon2 = R.nextInt(8);
        }
        if(treeUncommon == 15) {
            treeUncommon = R.nextInt(13);
        }
        if(treeRare == 15) {
            treeRare = R.nextInt(13);
        }

        System.out.println("treeCommon1:" + treeCommon1+" "+"treeCommon2:" + treeCommon2+" "+"treeUncommon:" + treeUncommon+" "+"treeRare:" + treeRare);
    }

}
