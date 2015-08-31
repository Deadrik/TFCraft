package com.bioxx.tfc.WorldGen.Generators;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.bioxx.tfc.api.TFCBlocks;

public class WorldGenLiquidsTFC extends WorldGenerator
{
	private final Block liquidBlock;

	public WorldGenLiquidsTFC(Block block)
	{
		liquidBlock = block;
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k)
	{
		if (world.getBlock(i, j + 1, k) != TFCBlocks.stoneIgIn && world.getBlock(i, j + 1, k) != TFCBlocks.stoneSed &&
				world.getBlock(i, j + 1, k) != TFCBlocks.stoneIgEx &&world.getBlock(i, j + 1, k) != TFCBlocks.stoneMM)
		{
			return false;
		}
		if (world.getBlock(i, j - 1, k) != TFCBlocks.stoneIgIn && world.getBlock(i, j - 1, k) != TFCBlocks.stoneSed &&
				world.getBlock(i, j - 1, k) != TFCBlocks.stoneIgEx &&world.getBlock(i, j - 1, k) != TFCBlocks.stoneMM)
		{
			return false;
		}
		if (!world.isAirBlock(i, j, k) && world.getBlock(i, j, k) != TFCBlocks.stoneIgIn && world.getBlock(i, j, k) != TFCBlocks.stoneSed &&
				world.getBlock(i, j, k) != TFCBlocks.stoneIgEx &&world.getBlock(i, j, k) != TFCBlocks.stoneMM)
		{
			return false;
		}

		int l = 0;
		if (world.getBlock(i - 1, j, k) == TFCBlocks.stoneIgIn && world.getBlock(i - 1, j, k) == TFCBlocks.stoneSed &&
				world.getBlock(i - 1, j, k) == TFCBlocks.stoneIgEx &&world.getBlock(i - 1, j, k) == TFCBlocks.stoneMM)
		{
			l++;
		}
		if (world.getBlock(i + 1, j, k) == TFCBlocks.stoneIgIn && world.getBlock(i + 1, j, k) == TFCBlocks.stoneSed &&
				world.getBlock(i + 1, j, k) == TFCBlocks.stoneIgEx &&world.getBlock(i + 1, j, k) == TFCBlocks.stoneMM)
		{
			l++;
		}
		if (world.getBlock(i, j, k - 1) == TFCBlocks.stoneIgIn && world.getBlock(i, j, k - 1) == TFCBlocks.stoneSed &&
				world.getBlock(i, j, k - 1) == TFCBlocks.stoneIgEx &&world.getBlock(i, j, k - 1) == TFCBlocks.stoneMM)
		{
			l++;
		}
		if (world.getBlock(i, j, k + 1) == TFCBlocks.stoneIgIn && world.getBlock(i, j, k + 1) == TFCBlocks.stoneSed &&
				world.getBlock(i, j, k + 1) == TFCBlocks.stoneIgEx &&world.getBlock(i, j, k + 1) == TFCBlocks.stoneMM)
		{
			l++;
		}

		int i1 = 0;
		if (world.isAirBlock(i - 1, j, k))
			i1++;
		if (world.isAirBlock(i + 1, j, k))
			i1++;
		if (world.isAirBlock(i, j, k - 1))
			i1++;
		if (world.isAirBlock(i, j, k + 1))
			i1++;

		if (l == 3 && i1 == 1)
		{
			world.setBlock(i, j, k, liquidBlock, 0, 0x2);
			world.scheduledUpdatesAreImmediate = true;
			this.liquidBlock.updateTick(world, i, j, k, random);
			world.scheduledUpdatesAreImmediate = false;
		}
		return true;
	}
}
