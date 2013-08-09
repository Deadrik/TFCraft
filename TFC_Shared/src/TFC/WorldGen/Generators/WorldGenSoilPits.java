package TFC.WorldGen.Generators;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenSoilPits implements IWorldGenerator
{


	public WorldGenSoilPits()
	{

	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) 
	{
		for (int var1 = 0; var1 < 1; ++var1)
		{
			int var2 = chunkX + random.nextInt(16) + 8;
			int var3 = chunkZ + random.nextInt(16) + 8;
			new WorldGenClayPit(16).generate(world, random, var2, world.getTopSolidOrLiquidBlock(var2, var3), var3);
		}

		for (int var1 = 0; var1 < 1; ++var1)
		{
			int var2 = chunkX + random.nextInt(16) + 8;
			int var3 = chunkZ + random.nextInt(16) + 8;
			new WorldGenPeatPit(24).generate(world, random, var2, world.getTopSolidOrLiquidBlock(var2, var3), var3);
		}

	}
}
