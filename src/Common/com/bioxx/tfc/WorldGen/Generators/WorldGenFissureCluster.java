package com.bioxx.tfc.WorldGen.Generators;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

import cpw.mods.fml.common.IWorldGenerator;

import com.bioxx.tfc.api.TFCBlocks;

public class WorldGenFissureCluster implements IWorldGenerator
{
	private Random rand;
	private int waterRarity = 225;

	private WorldGenFissure fissureGenWater = new WorldGenFissure(TFCBlocks.freshWater);
	private WorldGenFissure fissureGenLava = new WorldGenFissure(TFCBlocks.lava);
	private WorldGenFissure fissureGenAir = new WorldGenFissure(null);

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		rand = random;
		chunkX *= 16;
		chunkZ *= 16;

		int startX = chunkX + random.nextInt(16) + 8;
		int startZ = chunkZ + random.nextInt(16) + 8;

		if (rand.nextInt(waterRarity) == 0)
		{
			int num = 3 + rand.nextInt(10);
			for (int i = 0; i < num; i++)
			{
				int x = startX - 30 + random.nextInt(60);
				int z = startZ - 30 + random.nextInt(60);
				int y = world.getTopSolidOrLiquidBlock(x, z) - 1;
				if (rand.nextInt(10) == 0)
					fissureGenAir.generate(world, rand, x, y, z);
				else
					fissureGenWater.generate(world, rand, x, y, z);
			}
		}
		else if (rand.nextInt(400) == 0)
		{
			int num = 3 + rand.nextInt(10);
			for (int i = 0; i < num; i++)
			{
				int x = startX - 30 + random.nextInt(60);
				int z = startZ - 30 + random.nextInt(60);
				int y = world.getTopSolidOrLiquidBlock(x, z) - 1;

				if (rand.nextInt(10) == 0)
					fissureGenAir.generate(world, rand, x, y, z);
				else
					fissureGenLava.generate(world, rand, x, y, z);
			}
		}
	}

}
