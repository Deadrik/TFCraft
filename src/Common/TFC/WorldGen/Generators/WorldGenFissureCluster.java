package TFC.WorldGen.Generators;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import TFC.TFCBlocks;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenFissureCluster implements IWorldGenerator
{
	Random rand;
	int waterRarity = 150;

	WorldGenFissure fissureGenWater = new WorldGenFissure(TFCBlocks.FreshWaterStill);
	WorldGenFissure fissureGenLava = new WorldGenFissure(Blocks.lava);
	WorldGenFissure fissureGenAir = new WorldGenFissure(null);

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
		else if (rand.nextInt(300) == 0)
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
