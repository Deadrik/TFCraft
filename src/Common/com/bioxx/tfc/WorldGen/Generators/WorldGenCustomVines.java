package com.bioxx.tfc.WorldGen.Generators;

import java.util.Random;

import net.minecraft.util.Direction;
import net.minecraft.util.Facing;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Constant.Global;

public class WorldGenCustomVines extends WorldGenerator
{
	@Override
	public boolean generate(World world, Random r, int x, int y, int z)
	{
		int var6 = x;
		int var7 = z;

		for (; y > Global.SEALEVEL; --y)
		{
			if (world.isAirBlock(x, y, z))
			{
				for (int var8 = 2; var8 <= 5; ++var8)
				{
					if (TFCBlocks.vine.canPlaceBlockOnSide(world, x, y, z, var8))
					{
						world.setBlock(x, y, z, TFCBlocks.vine, 1 << Direction.facingToDirection[Facing.oppositeSide[var8]], 0x2);
						break;
					}
				}
			}
			else
			{
				x = var6 + r.nextInt(4) - r.nextInt(4);
				z = var7 + r.nextInt(4) - r.nextInt(4);
			}
		}
		return true;
	}

	public boolean generate2(World world, Random random, int x, int y, int z)
	{
		int oldX = x;

		for (int i1 = z; y < 256; ++y)
		{
			if (world.isAirBlock(x, y, z))
			{
				for (int side = 2; side <= 5; ++side)
				{
					if (TFCBlocks.vine.canPlaceBlockOnSide(world, x, y, z, side))
					{
						world.setBlock(x, y, z, TFCBlocks.vine, 1 << Direction.facingToDirection[Facing.oppositeSide[side]], 2);
						break;
					}
				}
			}
			else
			{
				x = oldX + random.nextInt(4) - random.nextInt(4);
				z = i1 + random.nextInt(4) - random.nextInt(4);
			}
		}

		return true;
	}
}
