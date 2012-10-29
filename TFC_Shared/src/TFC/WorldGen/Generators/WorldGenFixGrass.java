package TFC.WorldGen.Generators;

import java.util.Random;

import cpw.mods.fml.common.IWorldGenerator;

import TFC.*;
import TFC.Core.TFC_Climate;
import TFC.Core.TFC_Core;
import TFC.Enums.EnumTree;
import TFC.WorldGen.BiomeDecoratorTFC;
import TFC.WorldGen.DataLayer;
import TFC.WorldGen.TFCBiome;
import TFC.WorldGen.TFCWorldChunkManager;

import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Block;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.World;

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
				if(TFC_Core.isDryGrass(world.getBlockId(xCoord, yCoord, zCoord)))
				{
					if(getNearWater(world, xCoord, yCoord, zCoord))
					{
						rainfall*=2;
					}
					
					if(rainfall >= 500)
					{
						world.setBlock(xCoord, yCoord, zCoord, TFC_Core.getTypeForGrass(world.getBlockMetadata(xCoord, yCoord, zCoord)));
						converted = true;
					}
					for (int x1 = -4; x1 < 5 && !converted; ++x1)
					{
						for (int z1 = -4; z1 < 5 && !converted; ++z1)
						{
							for (int y1 = -2; y1 < 1 && !converted; ++y1)
							{
								if(TFC_Core.isWater(world.getBlockId(xCoord+x1, yCoord+y1, zCoord+z1)))
								{
									world.setBlock(xCoord, yCoord, zCoord, TFC_Core.getTypeForGrass(world.getBlockMetadata(xCoord, yCoord, zCoord)));
									converted = true;
									int numX = x1;
									int numZ = z1;
									if(numX < 0)
										numX = -numX;
									if(numZ < 0)
										numZ = -numZ;

									if(random.nextInt(1+((numX+numZ)/2)) == 0)
										world.setBlock(xCoord, yCoord+1, zCoord,Block.tallGrass.blockID);
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
				biome = world.getBiomeGenForCoords(x+(i * 8), z+(k * 8));
				if(biome.biomeID == TFCBiome.ocean.biomeID || biome.biomeID == TFCBiome.river.biomeID || biome.biomeID == TFCBiome.swampland.biomeID)
					return true;
			}
		}
		return false;
	}
}
