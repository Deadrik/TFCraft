package com.bioxx.tfc.WorldGen.Generators;

import java.util.Random;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.api.Constant.Global;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenFlowers extends WorldGenerator
{
	private Block plantBlock = TFCBlocks.Flowers;

	public WorldGenFlowers()
	{
	}

	@Override
	public boolean generate(World world, Random r, int x, int y, int z)
	{
		int all = Global.FLOWER_META_NAMES.length;

		for (int var6 = 0; var6 < 2; ++var6)
		{
			int xx = x + r.nextInt(8) - r.nextInt(8);
			int yy = y + r.nextInt(4) - r.nextInt(4);
			int zz = z + r.nextInt(8) - r.nextInt(8);

			if (world.isAirBlock(xx, yy, zz) && TFCBlocks.Flowers.canBlockStay(world, xx, yy, zz))
			{
				world.setBlock(xx, yy, zz, this.plantBlock, r.nextInt(all), 0x2);
			}
		}
		return true;
	}

}
