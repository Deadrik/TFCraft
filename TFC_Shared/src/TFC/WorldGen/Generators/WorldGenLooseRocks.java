package TFC.WorldGen.Generators;

import java.util.Random;

import TFC.WorldGen.BiomeDecoratorTFC;

import cpw.mods.fml.common.IWorldGenerator;

import net.minecraft.src.*;

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
			world.setBlock(var8, var9+1, var10, mod_TFC.LooseRock.blockID);

		}

		return true;
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) 
	{
		BiomeGenBase biome = world.getBiomeGenForCoords(chunkX, chunkZ);


        for (int var2 = 0; var2 < ((BiomeDecoratorTFC)biome.theBiomeDecorator).looseRocksPerChunk; var2++)
        {
            int var7 = chunkX + random.nextInt(16) + 8;
            int var3 = chunkZ + random.nextInt(16) + 8;

            new WorldGenLooseRocks().generate(world, random, var7, world.getTopSolidOrLiquidBlock(var7, var3)-1, var3);
        }
		
	}
}
