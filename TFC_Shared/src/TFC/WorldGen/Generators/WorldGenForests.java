package TFC.WorldGen.Generators;

import java.util.Random;

import cpw.mods.fml.common.IWorldGenerator;

import TFC.*;
import TFC.API.Enums.EnumTree;
import TFC.Core.TFC_Climate;
import TFC.Core.TFC_Core;
import TFC.WorldGen.BiomeDecoratorTFC;
import TFC.WorldGen.DataLayer;
import TFC.WorldGen.TFCBiome;
import TFC.WorldGen.TFCWorldChunkManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.feature.*;

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
		//		chunkX *= 16;
		//		chunkZ *= 16;

		BiomeGenBase biome;

		int xCoord = chunkX;
		int yCoord = 145;
		int zCoord = chunkZ;

		int numTreesBase = 5;

		if (random.nextInt(10) == 0)
		{
			numTreesBase -= 4;
		}

		int numTrees = 1;

		for (int var2 = 0; var2 < numTrees; ++var2)
		{
			xCoord = chunkX + random.nextInt(16);
			zCoord = chunkZ + random.nextInt(16);
			yCoord = world.getHeightValue(xCoord, zCoord);

			float rainfall = TFC_Climate.getRainfall(xCoord, 0, zCoord);

			

			biome = world.getBiomeGenForCoords(xCoord, zCoord);

			numTrees = (int) (numTreesBase + ((rainfall / 1000)*2));

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


			float evt = TFC_Climate.manager.getEVTLayerAt(xCoord, zCoord).floatdata1;
			WorldGenerator gen0 = TFCBiome.getTreeGen(TreeType0, random.nextBoolean());
			WorldGenerator gen1 = TFCBiome.getTreeGen(TreeType1, random.nextBoolean());
			WorldGenerator gen2 = TFCBiome.getTreeGen(TreeType2, random.nextBoolean());
			float temperature = TFC_Climate.getBioTemperatureHeight(xCoord, world.getHeightValue(xCoord, zCoord), zCoord);
			float temperatureAvg = TFC_Climate.getBioTemperature(xCoord, zCoord);

			if(getNearWater(world, xCoord, yCoord, zCoord))
			{
				rainfall*=2;
				evt /= 2;
			}
			try
			{
				if(zCoord > 14500 || zCoord < -14500)
				{
					gen2 = TFCBiome.getTreeGen(8, random.nextBoolean());
				}
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
				{randomNumber -= 10;}

				if(!canSpawnTemp1 || canSpawnEVTRain1 == 0)
					randomNumber -= 30;
				else if(canSpawnTemp1 && canSpawnEVTRain1 == 1)
				{randomNumber -= 15;}

				//if at least one of the trees is within the temperature zone otherewise no trees
				if(canSpawnTemp0 || canSpawnTemp1 || canSpawnTemp2)
				{
					//if the evt makes the location harsh for all of the trees
					if(canSpawnEVTRain0 <= 1 && canSpawnEVTRain1 <= 1 && canSpawnEVTRain2 <= 1)
					{
						//there is a 1 in 10 chance for a tree otherwise no trees
						if(random.nextInt(10) == 0)
						{
							numTrees = 1;
						}
						else return;
					}
				}
				else
				{
					return;
				}

				if(randomNumber < 40 && gen0 != null)
				{
					if(canSpawnTemp0 && canSpawnEVTRain0 > 0)
					{							
						gen0.setScale(1.0D, 1.0D, 1.0D);
						gen0.generate(world, random, xCoord, yCoord, zCoord);
					}
				}
				else if(randomNumber < 70 && gen1 != null)
				{
					if(canSpawnTemp1)
					{
						gen1.setScale(1.0D, 1.0D, 1.0D);
						gen1.generate(world, random, xCoord, yCoord, zCoord);
					}
				}
				else if(randomNumber < 100 && gen2 != null)
				{
					if(canSpawnTemp2)
					{
						gen2.setScale(1.0D, 1.0D, 1.0D);
						gen2.generate(world, random, xCoord, yCoord, zCoord);
					}
				}

			}catch(IndexOutOfBoundsException e)
			{
				
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
					if(world.blockExists(x+x1, y+y1, z+z1) == true && TFC_Core.isWater(world.getBlockId(x+x1, y+y1, z+z1)))
					{
						return true;
					}
				}
			}
		}
		return false;
	}
}
