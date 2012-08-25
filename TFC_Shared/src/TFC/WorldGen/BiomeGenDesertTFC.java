// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package TFC.WorldGen;

import java.util.Random;

import TFC.Core.TFCSeasons;

import net.minecraft.src.Block;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenDesertWells;


// Referenced classes of package net.minecraft.src:
//            BiomeGenBase, SpawnListEntry, EntityWolf, BiomeDecorator, 
//            WorldGenerator

public class BiomeGenDesertTFC extends TFCBiome
{

	public BiomeGenDesertTFC(int i)
	{
		super(i);
		spawnableCreatureList.clear();
		topBlock = (byte)Block.sand.blockID;
		fillerBlock = (byte)Block.sand.blockID;
		((BiomeDecoratorTFC)this.theBiomeDecorator).treesPerChunk = -999;
		((BiomeDecoratorTFC)this.theBiomeDecorator).deadBushPerChunk = 2;
		((BiomeDecoratorTFC)this.theBiomeDecorator).reedsPerChunk = 50;
		((BiomeDecoratorTFC)this.theBiomeDecorator).cactiPerChunk = 10;
		setMinMaxHeight(0.2F, 0.3F);
		setColor(DesertColor);
	}

	public void decorate(World par1World, Random par2Random, int par3, int par4)
	{
		super.decorate(par1World, par2Random, par3, par4);

		if (par2Random.nextInt(1000) == 0)
		{
			int var5 = par3 + par2Random.nextInt(16) + 8;
			int var6 = par4 + par2Random.nextInt(16) + 8;
			WorldGenDesertWells var7 = new WorldGenDesertWells();
			var7.generate(par1World, par2Random, var5, par1World.getHeightValue(var5, var6) + 1, var6);
		}
	}
	
	protected float getMonthTemp(int month)
    {
        switch(month)
        {
            case 11:
                return 0.75F;
            case 0:
                return 0.80F;
            case 1:
                return 0.85F;
            case 2:
                return 0.90F;
            case 3:
                return 0.95F; 
            case 4:
                return 1F;
            case 5:
                return 0.95F;
            case 6:
                return 0.90F;
            case 7:
                return 0.85F;
            case 8:
                return 0.80F;
            case 9:
                return 0.75F;
            case 10:
                return 0.70F;
            default:
                return 1F;
        }
    }
	
	public float getFloatRainfall()
    {
        return this.rainfall;
    }
}
