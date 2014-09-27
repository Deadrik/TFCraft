package com.bioxx.tfc.WorldGen.Generators;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.WorldGen.DataLayer;
import com.bioxx.tfc.WorldGen.TFCBiome;
import com.bioxx.tfc.api.Enums.EnumTree;

import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenForests implements IWorldGenerator
{
	/** The number of blocks to generate. */
	private int numberOfBlocks;

	WorldGenerator gen0;
	WorldGenerator gen1;
	WorldGenerator gen2;

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) 
	{
		chunkX *= 16;
		chunkZ *= 16;

		TFCBiome biome = (TFCBiome) world.getBiomeGenForCoords(chunkX, chunkZ);
		if(biome == TFCBiome.ocean)
			return;

		if(!generateJungle(random, chunkX, chunkZ, world))
			generateForest(random, chunkX, chunkZ, world);
	}

	private void generateForest(Random random, int chunkX, int chunkZ, World world)
	{
		int xCoord = chunkX;
		int yCoord = 145;
		int zCoord = chunkZ;

		int numTreesBase = 8;
		if (random.nextInt(10) == 0)
		{
			numTreesBase -= 6;
		}

		int numTrees = 1;
		for (int var2 = 0; var2 < numTrees; ++var2)
		{
			xCoord = chunkX + random.nextInt(16);
			zCoord = chunkZ + random.nextInt(16);
			yCoord = world.getHeightValue(xCoord, zCoord);

			float rainfall = TFC_Climate.getRainfall(world, xCoord, 0, zCoord);
			numTrees = (int) (numTreesBase + ((rainfall / 1000) * 2));
			if(numTrees > 30)
				numTrees = 30;

			//int[] trees = getTreesForClimate(random,world,xCoord,yCoord,zCoord);
			int TreeType0 = TFC_Climate.getTreeLayer(world, xCoord, yCoord, zCoord, 0);
			int TreeType1 = TFC_Climate.getTreeLayer(world, xCoord, yCoord, zCoord, 1);
			int TreeType2 = TFC_Climate.getTreeLayer(world, xCoord, yCoord, zCoord, 2);

			//This is error prevention for when the Layer system sometimes returns bad values
			/*if(TreeType0 < 0 || TreeType0 > EnumTree.values().length) 
			{
				TreeType0 = TFC_Climate.getTreeLayer(xCoord, yCoord, zCoord, 2);
			}
			if(TreeType1 < 0 || TreeType1 > EnumTree.values().length) 
			{
				TreeType1 = TFC_Climate.getTreeLayer(xCoord, yCoord, zCoord, 0);
			}
			if(TreeType2 < 0 || TreeType2 > EnumTree.values().length) 
			{
				TreeType2 = TFC_Climate.getTreeLayer(xCoord, yCoord, zCoord, 1);
			}*/

			float evt = TFC_Climate.getCacheManager(world).getEVTLayerAt(xCoord, zCoord).floatdata1;
			gen0 = TFCBiome.getTreeGen(TreeType0, random.nextBoolean());
			gen1 = TFCBiome.getTreeGen(TreeType1, random.nextBoolean());
			gen2 = TFCBiome.getTreeGen(TreeType2, random.nextBoolean());
			float temperature = TFC_Climate.getBioTemperatureHeight(world, xCoord, world.getHeightValue(xCoord, zCoord), zCoord);

			if(getNearWater(world, xCoord, yCoord, zCoord))
			{
				rainfall*=2;
				evt /= 2;
			}
			try
			{
				if(zCoord > 14500 || zCoord < -14500)
					gen2 = TFCBiome.getTreeGen(8, random.nextBoolean());
				int randomNumber = random.nextInt(100);

				float tree0EVTMin = EnumTree.values()[TreeType0].minEVT;
				float tree0EVTMax = EnumTree.values()[TreeType0].maxEVT;

				float tree0RainMin = EnumTree.values()[TreeType0].minRain;
				float tree0RainMax = EnumTree.values()[TreeType0].maxRain;

				float tree0TempMin = EnumTree.values()[TreeType0].minTemp;
				float tree0TempMax = EnumTree.values()[TreeType0].maxTemp;

				float tree1EVTMin = EnumTree.values()[TreeType1].minEVT;
				float tree1EVTMax = EnumTree.values()[TreeType1].maxEVT;

				float tree1RainMin = EnumTree.values()[TreeType1].minRain;
				float tree1RainMax = EnumTree.values()[TreeType1].maxRain;

				float tree1TempMin = EnumTree.values()[TreeType1].minTemp;
				float tree1TempMax = EnumTree.values()[TreeType1].maxTemp;

				float tree2EVTMin = EnumTree.values()[TreeType2].minEVT;
				float tree2EVTMax = EnumTree.values()[TreeType2].maxEVT;

				float tree2RainMin = EnumTree.values()[TreeType2].minRain;
				float tree2RainMax = EnumTree.values()[TreeType2].maxRain;

				float tree2TempMin = EnumTree.values()[TreeType2].minTemp;
				float tree2TempMax = EnumTree.values()[TreeType2].maxTemp;

				boolean canSpawnTemp0 = (temperature >= tree0TempMin && temperature <= tree0TempMax);
				int canSpawnEVTRain0 = (evt >= tree0EVTMin && evt <= tree0EVTMax && 
						rainfall >= tree0RainMin && rainfall <= tree0RainMax) ? 2 : 
							(evt >= tree0EVTMin && evt <= tree0EVTMax) || (rainfall >= tree0RainMin && rainfall <= tree0RainMax) ? 1 : 0;

				boolean canSpawnTemp1 = (temperature >= tree1TempMin && temperature <= tree1TempMax);
				int canSpawnEVTRain1 = (evt >= tree1EVTMin && evt <= tree1EVTMax && 
						rainfall >= tree1RainMin && rainfall <= tree1RainMax) ? 2 : 
							(evt >= tree1EVTMin && evt <= tree1EVTMax) || (rainfall >= tree1RainMin && rainfall <= tree1RainMax) ? 1 : 0;

				boolean canSpawnTemp2 = (temperature >= tree2TempMin && temperature <= tree2TempMax);
				int canSpawnEVTRain2 = (evt >= tree2EVTMin && evt <= tree2EVTMax && 
						rainfall >= tree2RainMin && rainfall <= tree2RainMax) ? 2 : 
							(evt >= tree2EVTMin && evt <= tree2EVTMax) || (rainfall >= tree2RainMin && rainfall <= tree2RainMax) ? 1 : 0;

				if(!canSpawnTemp2 || canSpawnEVTRain2 == 0)
					randomNumber -= 20;
				else if(canSpawnTemp2 && canSpawnEVTRain2 == 1)
					randomNumber -= 10;

				if(!canSpawnTemp1 || canSpawnEVTRain1 == 0)
					randomNumber -= 30;
				else if(canSpawnTemp1 && canSpawnEVTRain1 == 1)
					randomNumber -= 15;

				//if at least one of the trees is within the temperature zone otherewise no trees
				if(canSpawnTemp0 || canSpawnTemp1 || canSpawnTemp2)
				{
					//if the evt makes the location harsh for all of the trees
					if(canSpawnEVTRain0 <= 1 && canSpawnEVTRain1 <= 1 && canSpawnEVTRain2 <= 1)
					{
						//there is a 1 in 10 chance for a tree otherwise no trees
						if(random.nextInt(10) == 0)
							numTrees = 1;
						else
							return;
					}
				}
				else
				{
					return;
				}

				if(randomNumber < 40 && gen0 != null)
				{
					if(canSpawnTemp0 && canSpawnEVTRain0 > 1)
					{
						gen0.setScale(1.0D, 1.0D, 1.0D);
						gen0.generate(world, random, xCoord, yCoord, zCoord);
					}
				}
				else if(randomNumber < 70 && gen1 != null)
				{
					if(canSpawnTemp1 && canSpawnEVTRain1 > 1)
					{
						gen1.setScale(1.0D, 1.0D, 1.0D);
						gen1.generate(world, random, xCoord, yCoord, zCoord);
					}
				}
				else if(randomNumber < 100 && gen2 != null)
				{
					if(canSpawnTemp2 && canSpawnEVTRain2 > 1)
					{
						gen2.setScale(1.0D, 1.0D, 1.0D);
						gen2.generate(world, random, xCoord, yCoord, zCoord);
					}
				}
			}
			catch(IndexOutOfBoundsException e)
			{
			}
		}
	}

	public boolean generateJungle(Random random, int chunkX, int chunkZ, World world) 
	{
		boolean completed = false;
		int xCoord = chunkX;
		int yCoord = 145;
		int zCoord = chunkZ;
		int numTreesBase = 5;

		if (random.nextInt(10) == 0)
			numTreesBase -= 4;

		int numTrees = 50;
		for (int var2 = 0; var2 < numTrees; ++var2)
		{
			xCoord = chunkX + 8 + random.nextInt(16);
			zCoord = chunkZ + 8 + random.nextInt(16);
			yCoord = world.getHeightValue(xCoord, zCoord);
			float rainfall = TFC_Climate.getRainfall(world, xCoord, 0, zCoord);
			DataLayer EVT = TFC_Climate.getCacheManager(world).getEVTLayerAt(xCoord, zCoord);
			float temperature = TFC_Climate.getBioTemperatureHeight(world, xCoord, world.getHeightValue(xCoord, zCoord), zCoord);
			float temperatureAvg = TFC_Climate.getBioTemperature(world, xCoord, zCoord);

			try
			{
				if(EVT.floatdata1 <= EnumTree.KAPOK.maxEVT &&
						rainfall >= EnumTree.KAPOK.minRain &&
						rainfall <= EnumTree.KAPOK.maxRain && 
						temperatureAvg >= EnumTree.KAPOK.minTemp &&
						temperatureAvg <= EnumTree.KAPOK.maxTemp)
				{
					//WorldGenerator gen0 = ( (random.nextInt(2) == 0 ? new WorldGenCustomShrub(15, 15) :
					//	(random.nextInt(3) == 0 ? (random.nextInt(2) == 0 ?new WorldGenKapokTrees(false,15)/*new WorldGenCustomHugeTrees(false, 10 + random.nextInt(20), 15, 15)*/: new WorldGenAcaciaKoaTrees(false,0) ): new WorldGenCustomShortTrees(false, 15))));
					WorldGenerator gen0 = ( (random.nextInt(2) == 0 ? new WorldGenCustomShrub(15, 15) :
						(random.nextInt(3) == 0 ? (random.nextInt(2) == 0 ?new WorldGenTrees(false, 15, 6) : new WorldGenTrees(false, 16, 6) ): new WorldGenTrees(false, 15, 5))));

					gen0.setScale(1.0D, 1.0D, 1.0D);
					gen0.generate(world, random, xCoord, yCoord, zCoord);
					completed = true;
				}
			}
			catch(IndexOutOfBoundsException e)
			{
				//e.printStackTrace();//System.out.println("Tree0 Type: "+TreeType0);System.out.println("Tree1 Type: "+TreeType1);System.out.println("Tree2 Type: "+TreeType2);
			}
		}
		if(completed)
		{
			WorldGenCustomVines var5 = new WorldGenCustomVines();
			for (int var6 = 0; var6 < 50; ++var6)
			{
				int var7 = chunkX + random.nextInt(16);
				int var8 = 145;
				int var9 = chunkZ + random.nextInt(16);
				var5.generate(world, random, var7, var8, var9);
			}
		}
		return completed;
	}

	public int[] getTreesForClimate(Random random, World world, int xCoord, int yCoord, int zCoord){
		ArrayList list = new ArrayList<EnumTree>();
		float evt = TFC_Climate.getCacheManager(world).getEVTLayerAt(xCoord, zCoord).floatdata1;
		float rainfall = TFC_Climate.getRainfall(world, xCoord, 0, zCoord);
		if(getNearWater(world, xCoord, yCoord, zCoord))
		{
			rainfall*=2;
			evt /= 2;
		}

		float temperature = TFC_Climate.getBioTemperatureHeight(world, xCoord, world.getHeightValue(xCoord, zCoord), zCoord);
		float temperatureAvg = TFC_Climate.getBioTemperature(world, xCoord, zCoord);
		for(int i = 0; i < EnumTree.values().length;i++)
		{
			if(EnumTree.values()[i].maxRain > rainfall && EnumTree.values()[i].minRain < rainfall)
			{
				if(EnumTree.values()[i].maxEVT > evt && EnumTree.values()[i].minEVT < evt)
				{
					if(EnumTree.values()[i].maxTemp > temperatureAvg && EnumTree.values()[i].minTemp < temperatureAvg)
						list.add(EnumTree.values()[i]);
				}
			}
		}

		int[] treeTypes = new int[]{0,0,0};
		for(int i = 0; i < 3 && list.size() > 0; i++)
		{
			int n = random.nextInt(list.size());
			treeTypes[i] = n;
			list.remove(n);
		}
		return treeTypes;
	}

	public boolean getNearWater(World world, int x, int y, int z)
	{
		for (int x1 = -4; x1 < 5; ++x1)
		{
			for (int z1 = -4; z1 < 5; ++z1)
			{
				for (int y1 = -2; y1 < 1; ++y1)
				{
					if(world.blockExists(x + x1, y + y1, z + z1) == true && TFC_Core.isWater(world.getBlock(x + x1, y + y1, z + z1)))
						return true;
				}
			}
		}
		return false;
	}
}
