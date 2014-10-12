package com.bioxx.tfc.WorldGen.Generators;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.WorldGen.TFCBiome;
import com.bioxx.tfc.WorldGen.Generators.Trees.WorldGenAcaciaKoaTrees;
import com.bioxx.tfc.WorldGen.Generators.Trees.WorldGenCustomShortTrees;
import com.bioxx.tfc.WorldGen.Generators.Trees.WorldGenKapokTrees;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Enums.EnumTree;

import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenForests implements IWorldGenerator
{
	/** The number of blocks to generate. */
	private int numberOfBlocks;

	WorldGenerator gen0;
	WorldGenerator gen1;
	WorldGenerator gen2;
	int TreeType0;
	int TreeType1;
	int TreeType2;
	float evt;
	float rainfall;

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) 
	{
		chunkX *= 16;
		chunkZ *= 16;

		TFCBiome biome = (TFCBiome) world.getBiomeGenForCoords(chunkX, chunkZ);
		if(biome == TFCBiome.ocean)
			return;

		rainfall = TFC_Climate.getRainfall(world, chunkX, 0, chunkZ);
		evt = TFC_Climate.getCacheManager(world).getEVTLayerAt(chunkX+8, chunkZ+8).floatdata1;
		TreeType0 = TFC_Climate.getTreeLayer(world, chunkX, Global.SEALEVEL, chunkZ, 0);
		TreeType1 = TFC_Climate.getTreeLayer(world, chunkX, Global.SEALEVEL, chunkZ, 1);
		TreeType2 = TFC_Climate.getTreeLayer(world, chunkX, Global.SEALEVEL, chunkZ, 2);

		gen0 = TFCBiome.getTreeGen(TreeType0, random.nextBoolean());
		gen1 = TFCBiome.getTreeGen(TreeType1, random.nextBoolean());
		gen2 = TFCBiome.getTreeGen(TreeType2, random.nextBoolean());
		//gen0 = new WorldGenTrees(false, 4, 1, 1, false);
		//gen1 = new WorldGenTrees(false, 4, 1, 1, false);
		//gen2 = new WorldGenTrees(false, 4, 1, 1, false);

		if(!generateJungle(random, chunkX, chunkZ, world))
			generateForest(random, chunkX, chunkZ, world);
	}

	private void generateForest(Random random, int chunkX, int chunkZ, World world)
	{
		int xCoord = chunkX;
		int yCoord = Global.SEALEVEL+1;
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

			numTrees = (int) (numTreesBase + ((rainfall / 1000) * 2));
			if(numTrees > 30)
				numTrees = 30;

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

				float tree0EVTMin = TreeType0 != -1 ? EnumTree.values()[TreeType0].minEVT : 0;
				float tree0EVTMax = TreeType0 != -1 ? EnumTree.values()[TreeType0].maxEVT : 0;

				float tree0RainMin = TreeType0 != -1 ? EnumTree.values()[TreeType0].minRain : 0;
				float tree0RainMax = TreeType0 != -1 ? EnumTree.values()[TreeType0].maxRain : 0;

				float tree0TempMin = TreeType0 != -1 ? EnumTree.values()[TreeType0].minTemp : 0;
				float tree0TempMax = TreeType0 != -1 ? EnumTree.values()[TreeType0].maxTemp : 0;

				float tree1EVTMin = TreeType1 != -1 ? EnumTree.values()[TreeType1].minEVT : 0;
				float tree1EVTMax = TreeType1 != -1 ? EnumTree.values()[TreeType1].maxEVT : 0;

				float tree1RainMin = TreeType1 != -1 ? EnumTree.values()[TreeType1].minRain : 0;
				float tree1RainMax = TreeType1 != -1 ? EnumTree.values()[TreeType1].maxRain : 0;

				float tree1TempMin = TreeType1 != -1 ? EnumTree.values()[TreeType1].minTemp : 0;
				float tree1TempMax = TreeType1 != -1 ? EnumTree.values()[TreeType1].maxTemp : 0;

				float tree2EVTMin = TreeType2 != -1 ? EnumTree.values()[TreeType2].minEVT : 0;
				float tree2EVTMax = TreeType2 != -1 ? EnumTree.values()[TreeType2].maxEVT : 0;

				float tree2RainMin = TreeType2 != -1 ? EnumTree.values()[TreeType2].minRain : 0;
				float tree2RainMax = TreeType2 != -1 ? EnumTree.values()[TreeType2].maxRain : 0;

				float tree2TempMin = TreeType2 != -1 ? EnumTree.values()[TreeType2].minTemp : 0;
				float tree2TempMax = TreeType2 != -1 ? EnumTree.values()[TreeType2].maxTemp : 0;

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
		int yCoord = Global.SEALEVEL+1;
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

			float temperature = TFC_Climate.getBioTemperatureHeight(world, xCoord, world.getHeightValue(xCoord, zCoord), zCoord);
			float temperatureAvg = TFC_Climate.getBioTemperature(world, xCoord, zCoord);

			try
			{
				if(evt <= EnumTree.KAPOK.maxEVT &&
						rainfall >= EnumTree.KAPOK.minRain &&
						rainfall <= EnumTree.KAPOK.maxRain && 
						temperatureAvg >= EnumTree.KAPOK.minTemp &&
						temperatureAvg <= EnumTree.KAPOK.maxTemp)
				{
					WorldGenerator gen0;
					if(random.nextInt(5) == 0)
						gen0 = new WorldGenKapokTrees(false,15);
					else if(random.nextInt(2) == 0)
						gen0 = new WorldGenCustomShortTrees(false, 15);
					else 
						gen0 = new WorldGenJungleShrub(15);

					//gen0 = random.nextInt(2) == 0 ? new WorldGenJungleShrub(15) : random.nextInt(3) == 0 ? new WorldGenKapokTrees(false,15):  new WorldGenCustomShortTrees(false, 15);
					gen0.setScale(1.0D, 1.0D, 1.0D);
					gen0.generate(world, random, xCoord, yCoord, zCoord);
					completed = true;
				}

				if(evt <= EnumTree.KOA.maxEVT &&
						rainfall >= EnumTree.KOA.minRain &&
						rainfall <= EnumTree.KOA.maxRain && 
						temperatureAvg >= EnumTree.KOA.minTemp &&
						temperatureAvg <= EnumTree.KOA.maxTemp)
				{
					WorldGenerator gen0 = new WorldGenAcaciaKoaTrees(false, 0);

					gen0.setScale(1.0D, 1.0D, 1.0D);
					gen0.generate(world, random, xCoord, yCoord, zCoord);
				}
			}
			catch(IndexOutOfBoundsException e)
			{
				//e.printStackTrace();//System.out.println("Tree0 Type: "+TreeType0);System.out.println("Tree1 Type: "+TreeType1);System.out.println("Tree2 Type: "+TreeType2);
			}
		}
		if(completed)
		{
			WorldGenCustomVines vineGen = new WorldGenCustomVines();
			/*for (int var6 = 0; var6 < 20; ++var6)
			{
				int x = chunkX + random.nextInt(16);
				int z = chunkZ + random.nextInt(16);
				int y = 255;
				vineGen.generate(world, random, x, y, z);
			}*/

			for (int l = 0; l < 50; ++l)
			{
				int i1 = chunkX + random.nextInt(16) + 8;
				short short1 = 256;
				int j1 = chunkZ + random.nextInt(16) + 8;
				vineGen.generate2(world, random, i1, short1, j1);
			}
		}
		return completed;
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
