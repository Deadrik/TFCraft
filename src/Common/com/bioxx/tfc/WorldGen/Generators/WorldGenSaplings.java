package com.bioxx.tfc.WorldGen.Generators;

import java.util.Random;

import net.minecraft.world.World;

import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Enums.EnumTree;

public class WorldGenSaplings
{
	public void generate(World world, Random random, int xCoord, int yCoord, int zCoord)
	{
		boolean hasSpaceToGrow = true;

		int TreeType0 = TFC_Climate.getTreeLayer(world, xCoord, yCoord, zCoord, 0);
		int TreeType1 = TFC_Climate.getTreeLayer(world, xCoord, yCoord, zCoord, 1);
		int TreeType2 = TFC_Climate.getTreeLayer(world, xCoord, yCoord, zCoord, 2);

		for(int y = -3; y < 6 && hasSpaceToGrow; y++)
		{
			for(int x = -7; x < 8 && hasSpaceToGrow; x++)
			{
				for(int z = -7; z < 8 && hasSpaceToGrow; z++)
				{
					if(world.getBlock(xCoord + x, yCoord + y, zCoord + z) == TFCBlocks.LogNatural || 
							world.getBlock(xCoord + x, yCoord + y, zCoord + z) == TFCBlocks.Sapling)
					{
						hasSpaceToGrow = false;
					}
				}
			}
		}

		if(hasSpaceToGrow)
		{
			float rainfall = TFC_Climate.getRainfall(world, xCoord, yCoord, zCoord);
			float temperature = TFC_Climate.getBioTemperatureHeight(world, xCoord, yCoord, zCoord);
			float evt = TFC_Climate.getCacheManager(world).getEVTLayerAt(xCoord, zCoord).floatdata1;

			if(TreeType0 < 0 || TreeType0 > 15)
				TreeType0 = TFC_Climate.getTreeLayer(world, xCoord, yCoord, zCoord, 2);
			if(TreeType1 < 0 || TreeType1 > 15)
				TreeType1 = TFC_Climate.getTreeLayer(world, xCoord, yCoord, zCoord, 0);
			if(TreeType2 < 0 || TreeType2 > 15)
				TreeType2 = TFC_Climate.getTreeLayer(world, xCoord, yCoord, zCoord, 1);

			/**
			 * If the block is near a water source then we want rainfall to count for twice as much 
			 * and evt to count for half so as to make tree growth far more likely
			 * */
			if(getNearWater(world, xCoord, yCoord, zCoord))
			{
				rainfall *= 2;
				evt /= 2;
			}

			/**
			 * If the location is near the arctic circle then we want pine trees to definitely show up.
			 * */
			if(zCoord > 14500 || zCoord < -14500)
				TreeType2 = 8;


			boolean canSpawnTemp0 = false,
			        canSpawnTemp1 = false,
			        canSpawnTemp2 = false,
			        canSpawnRain0 = false,
			        canSpawnRain1 = false,
			        canSpawnRain2 = false,
			        canSpawnEVT0 = false,
			        canSpawnEVT1 = false,
			        canSpawnEVT2 = false;

			if (TreeType0 != -1)
			{
				float tree0EVTMin = EnumTree.values()[TreeType0].minEVT;
				float tree0EVTMax = EnumTree.values()[TreeType0].maxEVT;
				float tree0RainMin = EnumTree.values()[TreeType0].minRain;
				float tree0RainMax = EnumTree.values()[TreeType0].maxRain;
				float tree0TempMin = EnumTree.values()[TreeType0].minTemp;
				float tree0TempMax = EnumTree.values()[TreeType0].maxTemp;

				canSpawnTemp0 = temperature >= tree0TempMin && temperature <= tree0TempMax;
				canSpawnRain0 = rainfall >= tree0RainMin && rainfall <= tree0RainMax;
				canSpawnEVT0 = evt >= tree0EVTMin && evt <= tree0EVTMax;
			}

			if (TreeType1 != -1)
			{
				float tree1EVTMin = EnumTree.values()[TreeType1].minEVT;
				float tree1EVTMax = EnumTree.values()[TreeType1].maxEVT;
				float tree1RainMin = EnumTree.values()[TreeType1].minRain;
				float tree1RainMax = EnumTree.values()[TreeType1].maxRain;
				float tree1TempMin = EnumTree.values()[TreeType1].minTemp;
				float tree1TempMax = EnumTree.values()[TreeType1].maxTemp;

				canSpawnTemp1 = temperature >= tree1TempMin && temperature <= tree1TempMax;
				canSpawnRain0 = rainfall >= tree1RainMin && rainfall <= tree1RainMax;
				canSpawnEVT0 = evt >= tree1EVTMin && evt <= tree1EVTMax;
			}

			if (TreeType2 != -1)
			{
				float tree2EVTMin = EnumTree.values()[TreeType2].minEVT;
				float tree2EVTMax = EnumTree.values()[TreeType2].maxEVT;
				float tree2RainMin = EnumTree.values()[TreeType2].minRain;
				float tree2RainMax = EnumTree.values()[TreeType2].maxRain;
				float tree2TempMin = EnumTree.values()[TreeType2].minTemp;
				float tree2TempMax = EnumTree.values()[TreeType2].maxTemp;

				canSpawnTemp2 = temperature >= tree2TempMin && temperature <= tree2TempMax;
				canSpawnRain0 = rainfall >= tree2RainMin && rainfall <= tree2RainMax;
				canSpawnEVT0 = evt >= tree2EVTMin && evt <= tree2EVTMax;
			}

			//if at least one of the trees is within the temperature zone otherewise no trees
			if(canSpawnTemp0 || canSpawnTemp1 || canSpawnTemp2)
			{
				//if the evt makes the location harsh for all of the trees
				if(!canSpawnEVT0 && !canSpawnEVT1 && !canSpawnEVT2)
				{
					//there is a 1 in 10 chance for a tree otherwise no trees
					if(random.nextInt(10) > 0)
						return;
				}
			}
			else
			{
				return;
			}

			int randomNumber = random.nextInt(100);
			if(randomNumber < 40)
			{
				if(canSpawnTemp0 && canSpawnRain0)
					world.setBlock(xCoord, yCoord + 1, zCoord, TFCBlocks.Sapling, TreeType0, 0x2);
			}
			else if(randomNumber < 70)
			{
				if(canSpawnTemp1 && canSpawnRain1)
					world.setBlock(xCoord, yCoord + 1, zCoord, TFCBlocks.Sapling, TreeType1, 0x2);
			}
			else if(randomNumber < 100)
			{
				if(canSpawnTemp2 && canSpawnRain2)
					world.setBlock(xCoord, yCoord + 1, zCoord, TFCBlocks.Sapling, TreeType2, 0x2);
			}
		}
	}

	public boolean getNearWater(World world, int x, int y, int z)
	{
		for (int x1 = -4; x1 < 5; ++x1)
		{
			for (int z1 = -4; z1 < 5; ++z1)
			{
				for (int y1 = -2; y1 < 1; ++y1)
				{
					if (world.blockExists(x + x1, y + y1, z + z1) && TFC_Core.isWater(world.getBlock(x + x1, y + y1, z + z1)))
						return true;
				}
			}
		}
		return false;
	}
}
