package com.bioxx.tfc.WorldGen.Generators;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.bioxx.tfc.api.TFCBlocks;

public class WorldGenCustomReed extends WorldGenerator
{
	@Override
	public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
	{
		for (int var6 = 0; var6 < 20; ++var6)
		{
			int var7 = par3 + par2Random.nextInt(4) - par2Random.nextInt(4);
			int var8 = par4;
			int var9 = par5 + par2Random.nextInt(4) - par2Random.nextInt(4);

			if (par1World.isAirBlock(var7, par4, var9) &&
					(par1World.getBlock(var7 - 1, par4 - 1, var9).getMaterial() == Material.water ||
					par1World.getBlock(var7 + 1, par4 - 1, var9).getMaterial() == Material.water ||
					par1World.getBlock(var7, par4 - 1, var9 - 1).getMaterial() == Material.water ||
					par1World.getBlock(var7, par4 - 1, var9 + 1).getMaterial() == Material.water))
			{
				int var10 = 2 + par2Random.nextInt(par2Random.nextInt(3) + 1);
				for (int var11 = 0; var11 < var10; ++var11)
				{
					//if (((BlockCustomReed)Blocks.reeds).canBlockStay(par1World, var7, var8 + var11, var9))
					if (TFCBlocks.reeds.canBlockStay(par1World, var7, var8 + var11, var9))
						par1World.setBlock(var7, var8 + var11, var9, TFCBlocks.reeds);
				}
			}
		}
		return true;
	}
}
