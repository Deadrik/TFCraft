package TFC.WorldGen.Generators;

import java.util.Random;

import cpw.mods.fml.common.IWorldGenerator;

import TFC.Core.EnumTree;
import TFC.Core.TFC_Climate;
import TFC.Core.TFC_Core;
import TFC.WorldGen.BiomeDecoratorTFC;
import TFC.WorldGen.DataLayer;
import TFC.WorldGen.TFCBiome;
import TFC.WorldGen.TFCWorldChunkManager;

import net.minecraft.src.*;

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
		chunkX *= 16;
		chunkZ *= 16;

		BiomeGenBase biome;

		int xCoord = chunkX; 
		int yCoord = 145;
		int zCoord = chunkZ;

		float rainfall = TFC_Climate.getTerrainAdjustedRainfall(xCoord, 0, zCoord);
		for (int x = 0; x < 16; ++x)
		{
			for (int z = 0; z < 16; ++z)
			{

				xCoord = chunkX + x;
				zCoord = chunkZ + z;
				yCoord = world.getHeightValue(xCoord, zCoord);

				biome = world.getBiomeGenForCoords(xCoord, zCoord);

				if(TFC_Core.isDryGrass(world.getBlockId(xCoord, yCoord, zCoord)))
				{
					for (int x1 = -4; x1 < 5; ++x1)
					{
						for (int z1 = -4; z1 < 5; ++z1)
						{
							for (int y1 = -2; y1 < 1; ++y1)
							{
								if(TFC_Core.isWater(world.getBlockId(xCoord+x1, yCoord+y1, zCoord+z1)))
								{
									world.setBlock(xCoord+x1, yCoord+y1, zCoord+z1, TFC_Core.getTypeForGrass(world.getBlockMetadata(xCoord+x1, yCoord+y1, zCoord+z1)));
									
									int numX = x1;
									int numZ = z1;
									if(numX < 0)
										numX = -numX;
									if(numZ < 0)
										numZ = -numZ;
									
									if(random.nextInt(1+((numX+numZ)/2)) == 0)
										world.setBlock(xCoord+x1, yCoord+y1, zCoord+z1,Block.tallGrass.blockID);
								}
							}
						}
					}
				}
			}
		}

	}
}
