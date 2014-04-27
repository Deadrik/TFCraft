package com.bioxx.tfc.WorldGen.Generators;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenCustomFlowers extends WorldGenerator
{
	/** The ID of the plant block used in this plant generator. */
	private Block plantBlock;

	public WorldGenCustomFlowers(Block par1)
	{
		this.plantBlock = par1;
	}

	public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
	{
		for (int var6 = 0; var6 < 2; ++var6)
		{
			int var7 = par3 + par2Random.nextInt(8) - par2Random.nextInt(8);
			int var8 = par4 + par2Random.nextInt(4) - par2Random.nextInt(4);
			int var9 = par5 + par2Random.nextInt(8) - par2Random.nextInt(8);

			if (par1World.isAirBlock(var7, var8, var9) && this.plantBlock.canBlockStay(par1World, var7, var8, var9)) // ((BlockFlower)this.plantBlock).canBlockStay(par1World, var7, var8, var9))
				par1World.setBlock(var7, var8, var9, this.plantBlock);
		}
		return true;
	}
}
