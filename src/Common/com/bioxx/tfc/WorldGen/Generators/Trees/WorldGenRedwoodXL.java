/**
 * Copyright (c) Scott Killen and MisterFiber, 2012
 */
package com.bioxx.tfc.WorldGen.Generators.Trees;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.TFCBlocks;

public class WorldGenRedwoodXL extends WorldGenerator
{
	private final Block blockLeaf, blockWood;
	private final int metaLeaf, metaWood;

	public WorldGenRedwoodXL(boolean doNotify)
	{
		super(doNotify);
		blockLeaf = TFCBlocks.leaves;
		metaLeaf = 9;
		blockWood = TFCBlocks.logNatural;
		metaWood = 9;
	}

	@Override
	public boolean generate(World world, Random rand, int x, int y, int z)
	{
		final int height = rand.nextInt(20) + 22;

		if (y < 1 || y + height + 1 > 256)
			return false;

		if (!TFC_Core.isSoil(world.getBlock(x, y - 1, z))
				|| !TFC_Core.isSoil(world.getBlock(x-1, y - 1, z))
				|| !TFC_Core.isSoil(world.getBlock(x, y - 1, z-1))
				|| !TFC_Core.isSoil(world.getBlock(x-1, y - 1, z-1))
				|| y >= 180)
		{
			return false;
		}

		final int l = 4 + rand.nextInt(6);
		final int j = 5 + rand.nextInt(12);
		for (int y1 = y; y1 <= y + 1 + height; y1++)
		{
			int k1 = 1;

			if (y1 - y < j)
				k1 = 0;
			else
				k1 = l;

			for (int x1 = x - k1; x1 <= x + k1; x1++) 
			{
				for (int z1 = z - k1; z1 <= z + k1; z1++) 
				{
					if (y1 >= 0 && y1 < 256) 
					{
						final Block id1 = world.getBlock(x1, y1, z1);
						if (id1 != null && id1.isLeaves(world, x1, y1, z1))
							return false;
					}
					else
					{
						return false;
					}
				}
			}
		}

		Block block = world.getBlock(x, y - 1, z);
		Block soil = null;
		int soilMeta = 0;

		if (TFC_Core.isGrass(block))
		{
			soil = TFC_Core.getTypeForSoil(block);
			soilMeta = world.getBlockMetadata(x, y - 1, z);
			world.setBlock(x, y - 1, z, soil, soilMeta, 2);
		}

		block = world.getBlock(x - 1, y - 1, z);
		if (TFC_Core.isGrass(block))
		{
			soil = TFC_Core.getTypeForSoil(block);
			soilMeta = world.getBlockMetadata(x - 1, y - 1, z);
			world.setBlock(x - 1, y - 1, z, soil, soilMeta, 2);
		}

		block = world.getBlock(x, y - 1, z - 1);
		if (TFC_Core.isGrass(block))
		{
			soil = TFC_Core.getTypeForSoil(block);
			soilMeta = world.getBlockMetadata(x, y - 1, z - 1);
			world.setBlock(x, y - 1, z - 1, soil, soilMeta, 2);
		}

		block = world.getBlock(x - 1, y - 1, z - 1);
		if (TFC_Core.isGrass(block))
		{
			soil = TFC_Core.getTypeForSoil(block);
			soilMeta = world.getBlockMetadata(x - 1, y - 1, z - 1);
			world.setBlock(x - 1, y - 1, z - 1, soil, soilMeta, 2);
		}

		int l1 = rand.nextInt(2);
		int j2 = 1;
		boolean flag1 = false;

		for (int y1 = 0; y1 < height - 3; y1++)
		{
			setBlockAndNotifyAdequately(world, x, y + y1, z, blockWood, metaWood);
			setBlockAndNotifyAdequately(world, x - 1, y + y1, z, blockWood, metaWood);
			setBlockAndNotifyAdequately(world, x, y + y1, z - 1, blockWood, metaWood);
			setBlockAndNotifyAdequately(world, x - 1, y + y1, z - 1, blockWood, metaWood);
		}

		final int k = height - j;
		for (int i3 = 0; i3 <= k; i3++)
		{
			final int y1 = y + height - i3;
			for (int x1 = x - l1; x1 <= x + l1; x1++)
			{
				final int k4 = x1 - x;
				for (int z1 = z - l1; z1 <= z + l1; z1++)
				{
					final int i5 = z1 - z;
					if (Math.abs(k4) != l1 || Math.abs(i5) != l1 || l1 <= 0)
					{
						// Must use special method so that each block is checked for if it can be replaced. Using setBlockAndNotifyAdequately directly will remove the majority of the trunk. -K
						setLeaf(world, x1, y1, z1);
						setLeaf(world, x1 - 1, y1, z1);
						setLeaf(world, x1, y1, z1 - 1);
						setLeaf(world, x1 - 1, y1, z1 - 1);
					}
				}
			}

			if (l1 >= j2)
			{
				l1 = flag1 ? 1 : 0;
				flag1 = true;
				if (++j2 > l)
					j2 = l;
			}
			else
			{
				l1++;
			}
		}
		return true;
	}

	private void setLeaf(World world, int x, int y, int z)
	{
		Block b = world.getBlock(x, y, z);
		if (b.canBeReplacedByLeaves(world, x, y, z))
			setBlockAndNotifyAdequately(world, x, y, z, blockLeaf, metaLeaf);
	}

}
