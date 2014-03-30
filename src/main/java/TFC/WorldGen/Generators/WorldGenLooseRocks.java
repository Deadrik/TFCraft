package TFC.WorldGen.Generators;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import TFC.TFCBlocks;
import TFC.WorldGen.TFCBiome;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenLooseRocks implements IWorldGenerator
{
	public WorldGenLooseRocks()
	{
	}

	private boolean generate(World world, Random random, int var8, int var9, int var10)
	{
		if ((world.isAirBlock(var8, var9+1, var10) || world.getBlock(var8, var9+1, var10) == Blocks.snow || world.getBlock(var8, var9+1, var10) == Blocks.tallgrass) &&
				(world.getBlock(var8, var9, var10).getMaterial() == Material.grass || world.getBlock(var8, var9, var10).getMaterial() == Material.rock) && world.getBlock(var8, var9, var10).isOpaqueCube())
		{
			world.setBlock(var8, var9+1, var10, TFCBlocks.LooseRock, 0, 2);
		}
		return true;
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) 
	{
		chunkX *= 16;
		chunkZ *= 16;
		TFCBiome biome = (TFCBiome) world.getBiomeGenForCoords(chunkX, chunkZ);
		if(biome == TFCBiome.ocean)
			return;

		for (int var2 = 0; var2 < 8; var2++)
		{
			int var7 = chunkX + random.nextInt(16) + 8;
			int var3 = chunkZ + random.nextInt(16) + 8;
			generate(world, random, var7, world.getTopSolidOrLiquidBlock(var7, var3)-1, var3);
		}
	}

}
