// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package TFC.WorldGen.Biomes;

import java.util.Random;

import net.minecraft.src.SpawnListEntry;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenerator;

import TFC.Entities.*;
import TFC.WorldGen.BiomeDecoratorTFC;
import TFC.WorldGen.TFCBiome;

// Referenced classes of package net.minecraft.src:
//            BiomeGenBase, SpawnListEntry, EntityWolf, BiomeDecorator, 
//            WorldGenerator

public class BiomeGenForestTFC extends TFCBiome
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
		spawnableCreatureList.add(new SpawnListEntry(EntityWolfTFC.class, 5, 1, 5));
		spawnableCreatureList.add(new SpawnListEntry(EntityBear.class, 5, 1, 2));
        spawnableCreatureList.add(new SpawnListEntry(EntityDeer.class, 10, 3, 8));
        
		((BiomeDecoratorTFC)this.theBiomeDecorator).treesPerChunk = 10;
		((BiomeDecoratorTFC)this.theBiomeDecorator).grassPerChunk = 2;
		treeCommon1 = -1;
		treeCommon2 = -1;
		treeUncommon = -1;
		treeRare = -1;
		((BiomeDecoratorTFC)this.theBiomeDecorator).looseRocksPerChunk = 4;
        ((BiomeDecoratorTFC)this.theBiomeDecorator).looseRocksChancePerChunk = 90;
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

        //System.out.println("treeCommon1:" + treeCommon1+" "+"treeCommon2:" + treeCommon2+" "+"treeUncommon:" + treeUncommon+" "+"treeRare:" + treeRare);
    }
	
	protected float getMonthTemp(int month)
    {
        switch(month)
        {
            case 11:
                return -0.1F;
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
                return 0.85F;
            case 6:
                return 0.6F;
            case 7:
                return 0.3F;
            case 8:
                return -0.1F;
            case 9:
                return -0.2F;
            case 10:
                return -0.3F;
            default:
                return 1F;
        }
    }

}
