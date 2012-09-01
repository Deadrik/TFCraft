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

public class WorldGenForests implements IWorldGenerator
{
	/** The number of blocks to generate. */
	private int numberOfBlocks;

	public WorldGenForests()
	{
	}


	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) 
	{
		chunkX *= 16;
		chunkZ *= 16;
		
		BiomeGenBase biome;

		int xCoord = chunkX + random.nextInt(16) + 8;
		int yCoord = 145;
		int zCoord = chunkZ + random.nextInt(16) + 8;
		
		int numTrees = 6;
		

		if (random.nextInt(10) == 0)
		{
			numTrees -= 4;
		}
		
		float rainfall = TFC_Climate.getTerrainAdjustedRainfall(xCoord, 0, zCoord);
		
		for (int var2 = 0; var2 < numTrees; ++var2)
		{
			xCoord = chunkX + random.nextInt(16);
			zCoord = chunkZ + random.nextInt(16);
			yCoord = world.getHeightValue(xCoord, zCoord);
			
			biome = world.getBiomeGenForCoords(xCoord, zCoord);
			
			numTrees += ((rainfall / 1000)*2);
			
			if(numTrees > 30)
				numTrees = 30;

			int TreeType0 = TFC_Climate.getTreeLayer(xCoord, yCoord, zCoord, 0);
			int	TreeType1 = TFC_Climate.getTreeLayer(xCoord, yCoord, zCoord, 1);
			int	TreeType2 = TFC_Climate.getTreeLayer(xCoord, yCoord, zCoord, 2);

			if(TreeType0 < 0 || TreeType0 > 15)
				TreeType0 = TFC_Climate.getTreeLayer(xCoord, yCoord, zCoord, 2);
			if(TreeType1 < 0 || TreeType1 > 15)
				TreeType1 = TFC_Climate.getTreeLayer(xCoord, yCoord, zCoord, 0);
			if(TreeType2 < 0 || TreeType2 > 15)
				TreeType2 = TFC_Climate.getTreeLayer(xCoord, yCoord, zCoord, 1);
			
			
			DataLayer EVT = ((TFCWorldChunkManager)world.provider.worldChunkMgr).getEVTLayerAt(xCoord, zCoord);
			WorldGenerator gen0 = TFCBiome.getTreeGen(TreeType0, random.nextBoolean());
			WorldGenerator gen1 = TFCBiome.getTreeGen(TreeType1, random.nextBoolean());
			WorldGenerator gen2 = TFCBiome.getTreeGen(TreeType2, random.nextBoolean());
			float temperature = TFC_Climate.getBioTemperatureHeight(xCoord, world.getHeightValue(xCoord, zCoord), zCoord);

			try
			{
				if(EVT.floatdata1 <= EnumTree.KAPOK.maxEVT && 
						rainfall >= EnumTree.KAPOK.minRain && rainfall <= EnumTree.KAPOK.maxRain && 
						temperature >= EnumTree.KAPOK.minTemp && temperature <= EnumTree.KAPOK.maxTemp)
				{
					numTrees = 50;
					gen0 = (WorldGenerator)(random.nextInt(10) == 0 ? new WorldGenCustomShortTrees(false,15) : random.nextInt(2) == 0 ? new WorldGenCustomShrub(15, 15) : 
						random.nextInt(3) == 0 ? new WorldGenCustomHugeTrees(false, 10 + random.nextInt(20), 15, 15) : new WorldGenCustomShortTrees(false, 15));

					gen0.setScale(1.0D, 1.0D, 1.0D);
					gen0.generate(world, random, xCoord, yCoord, zCoord);
				}
				else
				{
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

					boolean canSpawn0 = (EVT.floatdata1 >= tree0EVTMin && EVT.floatdata1 <= tree0EVTMax && 
							rainfall >= tree0RainMin && rainfall <= tree0RainMax && 
							temperature >= tree0TempMin && temperature <= tree0TempMax);

					boolean canSpawn1 = (EVT.floatdata1 >= tree1EVTMin && EVT.floatdata1 <= tree1EVTMax && 
							rainfall >= tree1RainMin && rainfall <= tree1RainMax && 
							temperature >= tree1TempMin && temperature <= tree1TempMax);

					boolean canSpawn2 = (EVT.floatdata1 >= tree2EVTMin && EVT.floatdata1 <= tree2EVTMax && 
							rainfall >= tree2RainMin && rainfall <= tree2RainMax && 
							temperature >= tree2TempMin && temperature <= tree2TempMax);


					if(!canSpawn2)
						randomNumber -= 20;
					if(!canSpawn1)
						randomNumber -= 30;

					if(randomNumber < 40 && gen0 != null)
					{
						if(canSpawn0)
						{
							gen0.setScale(1.0D, 1.0D, 1.0D);
							gen0.generate(world, random, xCoord, yCoord, zCoord);
						}
					}
					else if(randomNumber < 70 && gen1 != null)
					{
						if(canSpawn1)
						{
							gen1.setScale(1.0D, 1.0D, 1.0D);
							gen1.generate(world, random, xCoord, yCoord, zCoord);
						}
					}
					else if(randomNumber < 90 && gen2 != null)
					{
						if(canSpawn2)
						{
							gen2.setScale(1.0D, 1.0D, 1.0D);
							gen2.generate(world, random, xCoord, yCoord, zCoord);
						}
					}

				}
			}catch(IndexOutOfBoundsException e)
			{
				e.printStackTrace();System.out.println("Tree0 Type: "+TreeType0);System.out.println("Tree1 Type: "+TreeType1);System.out.println("Tree2 Type: "+TreeType2);
			}
		}


	}
}
