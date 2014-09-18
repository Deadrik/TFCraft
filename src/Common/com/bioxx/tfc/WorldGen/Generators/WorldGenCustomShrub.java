package com.bioxx.tfc.WorldGen.Generators;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.bioxx.tfc.TFCBlocks;

public class WorldGenCustomShrub extends WorldGenerator
{
	private int field_48197_a;
	private int field_48196_b;

	public WorldGenCustomShrub(int par1, int par2)
	{
		this.field_48196_b = par1;
		this.field_48197_a = par2;
	}

	@Override
	public boolean generate(World world, Random rand, int x, int y, int z)
	{
		Block var15 = world.getBlock(x, y, z);
		for (boolean var6 = false; (var15 == Blocks.air || var15 == Blocks.leaves || var15 == Blocks.leaves2) && y > 0; --y)
		{
			;
		}

		Block var7 = world.getBlock(x, y, z);
		if (var7 == TFCBlocks.Dirt || var7 == TFCBlocks.Dirt2 || var7 == TFCBlocks.Grass || var7 == TFCBlocks.Grass2 ||
				var7 == TFCBlocks.ClayGrass || var7 == TFCBlocks.ClayGrass2)
		{
			++y;
			world.setBlock(x, y, z, TFCBlocks.LogNatural, this.field_48196_b, 0x2);
			for (int j = y; j <= y + 2; ++j)
			{
				int var9 = j - y;
				int var10 = 2 - var9;
				for (int i = x - var10; i <= x + var10; ++i)
				{
					int var12 = i - x;
					for (int k = z - var10; k <= z + var10; ++k)
					{
						int var14 = k - z;
						if ((Math.abs(var12) != var10 || Math.abs(var14) != var10 || rand.nextInt(2) != 0) && !world.getBlock(i, j, k).isReplaceable(world, i, j, k))
							this.setBlockAndNotifyAdequately(world, i, j, k, Blocks.leaves, this.field_48196_b);
					}
				}
			}
		}
		return true;
	}
}
