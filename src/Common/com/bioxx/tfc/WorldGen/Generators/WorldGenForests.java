package com.bioxx.tfc.WorldGen.Generators;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenerator;

import cpw.mods.fml.common.IWorldGenerator;

import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.WorldGen.TFCBiome;
import com.bioxx.tfc.WorldGen.Generators.Trees.WorldGenAcaciaKoaTrees;
import com.bioxx.tfc.WorldGen.Generators.Trees.WorldGenCustomShortTrees;
import com.bioxx.tfc.WorldGen.Generators.Trees.WorldGenKapokTrees;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Enums.EnumTree;

public class WorldGenForests implements IWorldGenerator
{
	/** The number of blocks to generate. */
	//private int numberOfBlocks;

	private WorldGenerator gen0;
	private WorldGenerator gen1;
	private WorldGenerator gen2;
	private int treeType0;
	private int treeType1;
	private int treeType2;
	private float evt;
	private float rainfall;
	private float temperature = 20f;

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) 
	{
		chunkX *= 16;
		chunkZ *= 16;

		if (world.getBiomeGenForCoords(chunkX, chunkZ) instanceof TFCBiome) // Fixes ClassCastException
		{
			TFCBiome biome = (TFCBiome) world.getBiomeGenForCoords(chunkX, chunkZ);
			if (biome == TFCBiome.OCEAN || biome == TFCBiome.DEEP_OCEAN)
				return;

			rainfall = TFC_Climate.getRainfall(world, chunkX, 0, chunkZ);
			evt = TFC_Climate.getCacheManager(world).getEVTLayerAt(chunkX + 8, chunkZ + 8).floatdata1;
			treeType0 = TFC_Climate.getTreeLayer(world, chunkX, Global.SEALEVEL, chunkZ, 0);
			treeType1 = TFC_Climate.getTreeLayer(world, chunkX, Global.SEALEVEL, chunkZ, 1);
			treeType2 = TFC_Climate.getTreeLayer(world, chunkX, Global.SEALEVEL, chunkZ, 2);

			gen0 = TFCBiome.getTreeGen(treeType0, random.nextBoolean());
			gen1 = TFCBiome.getTreeGen(treeType1, random.nextBoolean());
			gen2 = TFCBiome.getTreeGen(treeType2, random.nextBoolean());
			//gen0 = new WorldGenTrees(false, 4, 1, 1, false);
			//gen1 = new WorldGenTrees(false, 4, 1, 1, false);
			//gen2 = new WorldGenTrees(false, 4, 1, 1, false);

			if (!generateJungle(random, chunkX, chunkZ, world))
				generateForest(random, chunkX, chunkZ, world);
		}
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

			temperature = TFC_Climate.getBioTemperatureHeight(world, xCoord, world.getHeightValue(xCoord, zCoord), zCoord);
			int spawnParam0 = this.canTreeSpawn(treeType0);
			int spawnParam1 = this.canTreeSpawn(treeType1);
			int spawnParam2 = this.canTreeSpawn(treeType2);

			if(getNearWater(world, xCoord, yCoord, zCoord))
			{
				rainfall*=2;
				evt /= 2;
			}
			try 
			{
				if(zCoord > 14500 || zCoord < -14500)
					gen2 = TFCBiome.getTreeGen(8, random.nextBoolean());

				//if at least one of the trees is within the temperature zone otherewise no trees
				if((spawnParam0 & 1) > 0 || (spawnParam1 & 1) > 0 || (spawnParam2 & 1) > 0)
				{
					//if the evt makes the location harsh for all of the trees
					if (spawnParam0 > 0 && (spawnParam0 & 2) == 0 && spawnParam1 > 0 && (spawnParam1 & 2) == 0 && spawnParam2 > 0 && (spawnParam2 & 2) == 0)
					{
						//there is a 1 in 10 chance for a tree otherwise no trees
						if(random.nextInt(8) == 0)
							numTrees = 1;
						else
							return;
					}
				}
				else
				{
					return;
				}

				int randomNumber = random.nextInt(100);
				if (randomNumber < 50 && gen0 != null && (spawnParam0 == 5 || spawnParam0 == 7))
				{
					gen0.generate(world, random, xCoord, yCoord, zCoord);
				}
				else if (randomNumber < 80 && gen1 != null && (spawnParam1 == 5 || spawnParam1 == 7))
				{
					gen1.generate(world, random, xCoord, yCoord, zCoord);
				}
				else if (randomNumber < 100 && gen2 != null && (spawnParam2 == 5 || spawnParam2 == 7))
				{
					gen2.generate(world, random, xCoord, yCoord, zCoord);
				}
			}
			catch(IndexOutOfBoundsException e)
			{
			}
		}
	}

	private int canTreeSpawn(int tree)
	{
		float treeEVTMin = tree != -1 ? EnumTree.values()[tree].minEVT : 0;
		float treeEVTMax = tree != -1 ? EnumTree.values()[tree].maxEVT : 0;

		float treeRainMin = tree != -1 ? EnumTree.values()[tree].minRain : 0;
		float treeRainMax = tree != -1 ? EnumTree.values()[tree].maxRain : 0;

		float treeTempMin = tree != -1 ? EnumTree.values()[tree].minTemp : 0;
		float treeTempMax = tree != -1 ? EnumTree.values()[tree].maxTemp : 0;

		int out = 0;

		if(temperature >= treeTempMin && temperature <= treeTempMax)
			out += 1;
		if(evt >= treeEVTMin && evt <= treeEVTMax)
			out += 2;
		if(rainfall >= treeRainMin && rainfall <= treeRainMax)
			out += 4;

		return out;
	}

	public boolean generateJungle(Random random, int chunkX, int chunkZ, World world) 
	{
		boolean completed = false;
		int xCoord = chunkX;
		int yCoord = Global.SEALEVEL+1;
		int zCoord = chunkZ;
		/*int numTreesBase = 5;

		if (random.nextInt(10) == 0)
			numTreesBase -= 4;*/

		int numTrees = 50;
		for (int var2 = 0; var2 < numTrees; ++var2)
		{
			xCoord = chunkX + 8 + random.nextInt(16);
			zCoord = chunkZ + 8 + random.nextInt(16);
			yCoord = world.getHeightValue(xCoord, zCoord);

			//float temperature = TFC_Climate.getBioTemperatureHeight(world, xCoord, world.getHeightValue(xCoord, zCoord), zCoord);
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
				//TerraFirmaCraft.log.catching(e);//TerraFirmaCraft.log.info("Tree0 Type: "+TreeType0);TerraFirmaCraft.log.info("Tree1 Type: "+TreeType1);TerraFirmaCraft.log.info("Tree2 Type: "+TreeType2);
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
					if (world.blockExists(x + x1, y + y1, z + z1) && TFC_Core.isWater(world.getBlock(x + x1, y + y1, z + z1)))
						return true;
				}
			}
		}
		return false;
	}
}
