package TFC.WorldGen.Generators;

import java.util.Random;

import TFC.WorldGen.BiomeDecoratorTFC;
import TFC.WorldGen.TFCBiome;

import cpw.mods.fml.common.IWorldGenerator;

import net.minecraft.src.*;

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
            new WorldGenClayPit(16, (TFCBiome) world.getBiomeGenForCoords(var2, var3)).generate(world, random, var2, world.getTopSolidOrLiquidBlock(var2, var3), var3);
        }

        for (int var1 = 0; var1 < 1; ++var1)
        {
            int var2 = chunkX + random.nextInt(16) + 8;
            int var3 = chunkZ + random.nextInt(16) + 8;
            new WorldGenPeatPit(24, world.getBiomeGenForCoords(var2, var3)).generate(world, random, var2, world.getTopSolidOrLiquidBlock(var2, var3), var3);
        }
		
	}
}
