// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package TFC.WorldGen.Biomes;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenDesertWells;
import TFC.WorldGen.BiomeDecoratorTFC;
import TFC.WorldGen.TFCBiome;


// Referenced classes of package net.minecraft.src:
//            BiomeGenBase, SpawnListEntry, EntityWolf, BiomeDecorator, 
//            WorldGenerator

public class BiomeGenDesertTFC extends TFCBiome
{
	private WorldGenDesertWells desertWells;

	public BiomeGenDesertTFC(int i)
	{
		super(i);
		spawnableCreatureList.clear();
		topBlock = Blocks.sand;
		fillerBlock = Blocks.sand;
		((BiomeDecoratorTFC)this.theBiomeDecorator).treesPerChunk = -999;
		((BiomeDecoratorTFC)this.theBiomeDecorator).deadBushPerChunk = 1;
		((BiomeDecoratorTFC)this.theBiomeDecorator).reedsPerChunk = 3;
		((BiomeDecoratorTFC)this.theBiomeDecorator).cactiPerChunk = 2;
		setMinMaxHeight(0.2F, 0.3F);
	}

	@Override
	public void decorate(World par1World, Random par2Random, int par3, int par4)
	{
		super.decorate(par1World, par2Random, par3, par4);

		if (par2Random.nextInt(1000) == 0)
		{
			int var5 = par3 + par2Random.nextInt(16) + 8;
			int var6 = par4 + par2Random.nextInt(16) + 8;

			if (desertWells == null)
				desertWells = new WorldGenDesertWells();
			desertWells.generate(par1World, par2Random, var5, par1World.getHeightValue(var5, var6) + 1, var6);
		}
	}

}
