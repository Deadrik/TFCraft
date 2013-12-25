package TFC.WorldGen.Generators;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
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
		if ((world.isAirBlock(var8, var9+1, var10) || world.getBlockId(var8, var9+1, var10) == Block.snow.blockID || 
				world.getBlockId(var8, var9+1, var10) == Block.tallGrass.blockID) && 
				(world.getBlockMaterial(var8, var9, var10) == Material.grass || 
				world.getBlockMaterial(var8, var9, var10) == Material.rock) && world.isBlockOpaqueCube(var8, var9, var10))
		{
			world.setBlock(var8, var9+1, var10, TFCBlocks.LooseRock.blockID, 0, 2);
		}

		return true;
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) 
	{
		chunkX *= 16;
		chunkZ *= 16;
		BiomeGenBase biome = world.getBiomeGenForCoords(chunkX, chunkZ);
		if(biome == TFCBiome.ocean) {
			return;
		}

		for (int var2 = 0; var2 < 8; var2++)
		{
			int var7 = chunkX + random.nextInt(16) + 8;
			int var3 = chunkZ + random.nextInt(16) + 8;

			generate(world, random, var7, world.getTopSolidOrLiquidBlock(var7, var3)-1, var3);
		}

	}
}
