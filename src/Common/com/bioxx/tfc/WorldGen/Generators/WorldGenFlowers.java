package com.bioxx.tfc.WorldGen.Generators;

import java.util.Random;

import net.minecraft.world.World;

import com.bioxx.tfc.Blocks.Flora.BlockFlower;
import com.bioxx.tfc.api.TFCBlocks;

public class WorldGenFlowers
{
	public static void generate(World world, Random random, int chunkX, int chunkZ, int flowersPerChunk)
	{
		int flowerType = new Random(world.getSeed() + ((chunkX >> 7) - (chunkZ >> 7)) * (chunkZ >> 7)).nextInt(14);
		BlockFlower plantBlock = (BlockFlower) TFCBlocks.flowers;
		if(flowerType > 5)
		{
			plantBlock = (BlockFlower) TFCBlocks.flowers2;
			flowerType -= 5;
		}
		if(random.nextInt(flowersPerChunk) != 0)
			return;

		int xCoord = chunkX + random.nextInt(16);
		int zCoord = chunkZ + random.nextInt(16);
		int yCoord = world.getTopSolidOrLiquidBlock(xCoord, zCoord);
		for (int i = 0; i < flowersPerChunk; ++i)
		{
			int xx = xCoord-4 + random.nextInt(8);
			int zz = zCoord-4 + random.nextInt(8);
			int yy = yCoord;

			if (world.isAirBlock(xx, yy, zz) && plantBlock.canBlockStay(world, xx, yy, zz))
			{
				if(plantBlock.canGrowConditions(world, xx, yy, zz, flowerType))
					world.setBlock(xx, yy, zz, plantBlock, flowerType, 0x2);
			}
		}
	}

}
