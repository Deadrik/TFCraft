package com.bioxx.tfc.WorldGen.Generators;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.bioxx.tfc.Blocks.Flora.BlockFlower;

public class WorldGenFlowers extends WorldGenerator
{
	private BlockFlower plantBlock;

	public WorldGenFlowers(Block b)
	{
		plantBlock = (BlockFlower) b;
	}

	@Override
	public boolean generate(World world, Random r, int x, int y, int z)
	{
		int all = plantBlock.flowerNames.length;

		for (int var6 = 0; var6 < 2; ++var6)
		{
			int xx = x + r.nextInt(8) - r.nextInt(8);
			int yy = y + r.nextInt(4) - r.nextInt(4);
			int zz = z + r.nextInt(8) - r.nextInt(8);

			if (world.isAirBlock(xx, yy, zz) && plantBlock.canBlockStay(world, xx, yy, zz))
			{
				world.setBlock(xx, yy, zz, plantBlock, r.nextInt(all), 0x2);
			}
		}
		return true;
	}

}
