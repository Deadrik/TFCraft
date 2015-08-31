package com.bioxx.tfc.WorldGen.Generators;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.TFCBlocks;

public class WorldGenCustomPumpkin extends WorldGenerator
{
	@Override
	public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
	{
		for (int var6 = 0; var6 < 64; ++var6)
		{
			int var7 = par3 + par2Random.nextInt(8) - par2Random.nextInt(8);
			int var8 = par4 + par2Random.nextInt(4) - par2Random.nextInt(4);
			int var9 = par5 + par2Random.nextInt(8) - par2Random.nextInt(8);
			Block var5 = par1World.getBlock(var7, var8 - 1, var9);
			if (par1World.isAirBlock(var7, var8, var9) && TFC_Core.isSoil(var5) && TFCBlocks.pumpkin.canPlaceBlockAt(par1World, var7, var8, var9))
			{
				par1World.setBlock(var7, var8, var9, TFCBlocks.pumpkin, par2Random.nextInt(4), 0x2);
			}
		}
		return true;
	}
}
