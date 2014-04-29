package com.bioxx.tfc.WorldGen.Generators;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Core;

import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenFixGrass implements IWorldGenerator
{
	/** The number of blocks to generate. */
	private int numberOfBlocks;

	public WorldGenFixGrass()
	{
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) 
	{
		//		chunkX *= 16;
		//		chunkZ *= 16;

		int xCoord = chunkX; 
		int yCoord = 145;
		int zCoord = chunkZ;

		float rainfall = TFC_Climate.getRainfall(xCoord, 0, zCoord);
		for (int x = 0; x < 16; ++x)
		{
			for (int z = 0; z < 16; ++z)
			{
				xCoord = chunkX + x;
				zCoord = chunkZ + z;
				yCoord = world.getHeightValue(xCoord, zCoord);

				boolean converted = false;
				if(TFC_Core.isDryGrass(world.getBlock(xCoord, yCoord, zCoord)))
				{
					if(getNearWater(world, xCoord, yCoord, zCoord))
						rainfall*=2;

					if(rainfall >= 500)
					{
						world.setBlock(xCoord, yCoord, zCoord, TFC_Core.getTypeForGrass(world.getBlockMetadata(xCoord, yCoord, zCoord)), 0, 0x2);
						converted = true;
					}

					for (int x1 = -4; x1 < 5 && !converted; ++x1)
					{
						for (int z1 = -4; z1 < 5 && !converted; ++z1)
						{
							for (int y1 = -2; y1 < 1 && !converted; ++y1)
							{
								if(TFC_Core.isWater(world.getBlock(xCoord + x1, yCoord + y1, zCoord + z1)))
								{
									world.setBlock(xCoord, yCoord, zCoord, TFC_Core.getTypeForGrass(world.getBlockMetadata(xCoord, yCoord, zCoord)), 0, 0x2);
									converted = true;
									int numX = x1;
									int numZ = z1;
									if(numX < 0)
										numX = -numX;
									if(numZ < 0)
										numZ = -numZ;
									if(random.nextInt(1 + ((numX + numZ) / 2)) == 0)
										world.setBlock(xCoord, yCoord + 1, zCoord, TFCBlocks.TallGrass, 0, 0x2);
								}
							}
						}
					}
				}
			}
		}
	}

	public boolean getNearWater(World world, int x, int y, int z)
	{
		BiomeGenBase biome;
		for(int i = -2; i <= 2; i++)
		{
			for(int k = -2; k <= 2; k++)
			{
				biome = (BiomeGenBase) world.getBiomeGenForCoords(x + (i * 8), z + (k * 8));
				if(biome == BiomeGenBase.ocean || biome == BiomeGenBase.river || biome == BiomeGenBase.swampland)
					return true;
			}
		}
		return false;
	}

}
