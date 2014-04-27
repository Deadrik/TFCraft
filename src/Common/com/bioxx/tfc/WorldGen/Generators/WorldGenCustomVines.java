package com.bioxx.tfc.WorldGen.Generators;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.Facing;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenCustomVines extends WorldGenerator
{
	public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
	{
		int var6 = par3;
		int var7 = par5;
		if (par1World.canBlockSeeTheSky(par3, par4, par5))
		{
			for (int var8 = 2; var8 <= 5; ++var8)
			{
				if (Blocks.vine.canPlaceBlockOnSide(par1World, par3, par4, par5, var8))
				{
					par1World.setBlock(par3, par4, par5, Blocks.vine, 1 << Direction.facingToDirection[Facing.oppositeSide[var8]], 0x2);
					break;
				}
			}
		}
		else
		{
			par3 = var6 + par2Random.nextInt(4) - par2Random.nextInt(4);
			par5 = var7 + par2Random.nextInt(4) - par2Random.nextInt(4);
		}
		return true;
	}
}
