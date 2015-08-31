package com.bioxx.tfc.WorldGen.Generators;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.bioxx.tfc.api.TFCBlocks;

public class WorldGenFungi extends WorldGenerator
{
	private int meta;

	public WorldGenFungi()
	{
	}

	public boolean genWithMeta(World world, Random rnd, int x, int y, int z, int m)
	{
		meta = m;
		return this.generate(world, rnd, x, y, z);
	}

	@Override
	public boolean generate(World world, Random rnd, int x, int y, int z)
	{
		for (int l = 0; l < 2; ++l)
		{
			int i = x + rnd.nextInt(8) - rnd.nextInt(8);
			int j = y + rnd.nextInt(4) - rnd.nextInt(4);
			int k = z + rnd.nextInt(8) - rnd.nextInt(8);

			if (world.isAirBlock(i, j, k) && TFCBlocks.fungi.canBlockStay(world, i, j, k))
			{
				world.setBlock(i, j, k, TFCBlocks.fungi, meta, 0x2);
			}
		}
		return true;
	}
}
