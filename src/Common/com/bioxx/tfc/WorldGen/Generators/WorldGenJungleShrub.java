package com.bioxx.tfc.WorldGen.Generators;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.TFCBlocks;

public class WorldGenJungleShrub extends WorldGenerator
{
	private final int meta;

	public WorldGenJungleShrub(int m)
	{
		this.meta = m;
	}

	@Override
	public boolean generate(World world, Random rand, int x, int y, int z)
	{
		Block block;
		do
		{
			block = world.getBlock(x, y, z);
			if (!(block.isLeaves(world, x, y, z) || block.isAir(world, x, y, z)))
			{
				break;
			}
			--y;
		} while (y > 0);

		Block block1 = world.getBlock(x, y, z);

		if (TFC_Core.isSoil(block1))
		{
			++y;
			this.setBlockAndNotifyAdequately(world, x, y, z, TFCBlocks.logNatural, this.meta);

			for (int l = y; l <= y + 2; ++l)
			{
				int i1 = l - y;
				int j1 = 2 - i1;

				for (int k1 = x - j1; k1 <= x + j1; ++k1)
				{
					int l1 = k1 - x;

					for (int i2 = z - j1; i2 <= z + j1; ++i2)
					{
						int j2 = i2 - z;

						if ((Math.abs(l1) != j1 || Math.abs(j2) != j1 || rand.nextInt(2) != 0) && world.getBlock(k1, l, i2).canBeReplacedByLeaves(world, k1, l, i2))
						{
							this.setBlockAndNotifyAdequately(world, k1, l, i2, TFCBlocks.leaves, this.meta);
						}
					}
				}
			}
		}
		return true;
	}
}
