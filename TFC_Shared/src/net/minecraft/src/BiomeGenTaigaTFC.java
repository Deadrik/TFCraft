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

	static int treeCommon1 = -1;
	static Boolean treeCommon1Size;
	static int treeCommon2 = -1;
	static Boolean treeCommon2Size;
	static int treeUncommon = -1;
	static Boolean treeUncommonSize;
	static int treeRare = -1;
	static Boolean treeRareSize;

	public BiomeGenTaigaTFC(int i)
	{
		super(i);
		spawnableCreatureList.add(new SpawnListEntry(net.minecraft.src.EntityWolf.class, 8, 4, 4));
		biomeDecorator.treesPerChunk = 10;
		biomeDecorator.grassPerChunk = 1;
		treeCommon1 = -1;
		treeCommon2 = -1;
		treeUncommon = -1;
		treeRare = -1;
	}

	public WorldGenerator getRandomWorldGenForTrees(Random random, World world)
	{
		WorldGenerator[] ConiferGenList = {worldGenWhiteCedarTallTrees,worldGenPineShortTrees,worldGenSpruceShortTrees,
				worldGenOakShortTrees,worldGenBirchShortTrees,worldGenAshTallTrees,worldGenRedwoodShortTrees,worldGenWhiteElmTallTrees,
				worldGenOakTallTrees,worldGenBirchTallTrees,worldGenPineTallTrees,worldGenAspenTallTrees,worldGenAspenShortTrees};

		if(treeCommon1 == -1)
		{
			long seed = world.getSeed() + this.biomeID;
			Random R = new Random(seed);
			treeCommon1 = R.nextInt(ConiferGenList.length);
			treeCommon1Size = R.nextBoolean();
			treeCommon2 = R.nextInt(ConiferGenList.length);
			treeCommon2Size = R.nextBoolean();
			treeUncommon = R.nextInt(ConiferGenList.length);
			treeUncommonSize = R.nextBoolean();
			treeRare = R.nextInt(ConiferGenList.length);
			treeRareSize = R.nextBoolean();
		}
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
}
