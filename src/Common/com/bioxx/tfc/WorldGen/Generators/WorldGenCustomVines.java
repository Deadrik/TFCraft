package com.bioxx.tfc.WorldGen.Generators;

import java.util.Random;

import net.minecraft.util.Direction;
import net.minecraft.util.Facing;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.bioxx.tfc.TFCBlocks;

public class WorldGenCustomVines extends WorldGenerator
{
	public boolean generate(World world, Random R, int x, int y, int z)
	{
		int var6 = x;
		int var7 = z;
		if (world.canBlockSeeTheSky(x, y, z))
		{
			for (int var8 = 2; var8 <= 5; ++var8)
			{
				if (TFCBlocks.Vine.canPlaceBlockOnSide(world, x, y, z, var8))
				{
					world.setBlock(x, y, z, TFCBlocks.Vine, 1 << Direction.facingToDirection[Facing.oppositeSide[var8]], 0x2);
					break;
				}
			}
		}
		else
		{
			x = var6 + R.nextInt(4) - R.nextInt(4);
			z = var7 + R.nextInt(4) - R.nextInt(4);
		}
		return true;
	}
}
