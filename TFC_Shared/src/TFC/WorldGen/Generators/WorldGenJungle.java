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
import TFC.WorldGen.Generators.Trees.WorldGenCustomHugeTrees;
import TFC.WorldGen.Generators.Trees.WorldGenCustomShortTrees;

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
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenJungle
{
	/** The number of blocks to generate. */
	private int numberOfBlocks;

	public WorldGenJungle()
	{
	}


	public boolean generate(Random random, int chunkX, int chunkZ, World world) 
	{
//		chunkX *= 16;
//		chunkZ *= 16;
		
		BiomeGenBase biome;
		boolean completed = false;

		int xCoord = chunkX;
		int yCoord = 145;
		int zCoord = chunkZ;
		
		int numTreesBase = 5;
		
		if (random.nextInt(10) == 0)
		{
			numTreesBase -= 4;
		}
		
		int numTrees = 50;
		
		for (int var2 = 0; var2 < numTrees; ++var2)
		{
			xCoord = chunkX + 8 + random.nextInt(16);
			zCoord = chunkZ + 8 + random.nextInt(16);
			yCoord = world.getHeightValue(xCoord, zCoord);
			
			float rainfall = TFC_Climate.getRainfall(xCoord, 0, zCoord);
			
			
			DataLayer EVT = ((TFCWorldChunkManager)world.provider.worldChunkMgr).getEVTLayerAt(xCoord, zCoord);
			float temperature = TFC_Climate.getBioTemperatureHeight(xCoord, world.getHeightValue(xCoord, zCoord), zCoord);
			float temperatureAvg = TFC_Climate.getBioTemperature(xCoord, zCoord);

			try
			{
				
				if(EVT.floatdata1 <= EnumTree.KAPOK.maxEVT && 
						rainfall >= EnumTree.KAPOK.minRain && rainfall <= EnumTree.KAPOK.maxRain && 
								temperatureAvg >= EnumTree.KAPOK.minTemp && temperatureAvg <= EnumTree.KAPOK.maxTemp)
				{
//					WorldGenerator gen0 = (WorldGenerator)(random.nextInt(10) == 0 ? new WorldGenCustomShortTrees(false,15) : random.nextInt(2) == 0 ? new WorldGenCustomShrub(15, 15) : 
//						random.nextInt(3) == 0 ? new WorldGenCustomHugeTrees(false, 10 + random.nextInt(20), 15, 15) : new WorldGenCustomShortTrees(false, 15));
					
					WorldGenerator gen0 = (WorldGenerator)( (random.nextInt(2) == 0 ? new WorldGenCustomShrub(15, 15) : (random.nextInt(3) == 0 ? new WorldGenCustomHugeTrees(false, 10 + random.nextInt(20), 15, 15) : new WorldGenCustomShortTrees(false, 15))));

					gen0.setScale(1.0D, 1.0D, 1.0D);
					gen0.generate(world, random, xCoord, yCoord, zCoord);
					completed = true;
				}
				
			}catch(IndexOutOfBoundsException e)
			{
				//e.printStackTrace();System.out.println("Tree0 Type: "+TreeType0);System.out.println("Tree1 Type: "+TreeType1);System.out.println("Tree2 Type: "+TreeType2);
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
