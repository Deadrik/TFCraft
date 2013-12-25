package TFC.WorldGen.Generators;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import TFC.TFCBlocks;
import TFC.Blocks.Vanilla.BlockCustomTallGrass;
import TFC.Core.TFC_Climate;
import TFC.Core.TFC_Core;
import TFC.WorldGen.DataLayer;
import TFC.WorldGen.TFCWorldChunkManager;
import TFC.WorldGen.Generators.Trees.WorldGenCustomFruitTree;
import TFC.WorldGen.Generators.Trees.WorldGenCustomFruitTree2;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenPlants implements IWorldGenerator
{
	static WorldGenCustomFlowers plantYellowGen = new WorldGenCustomFlowers(Block.plantYellow.blockID);
	static WorldGenCustomFlowers plantRedGen = new WorldGenCustomFlowers(Block.plantRed.blockID);
	static WorldGenCustomFlowers mushroomBrownGen = new WorldGenCustomFlowers(Block.mushroomBrown.blockID);
	static WorldGenCustomFlowers mushroomRedGen = new WorldGenCustomFlowers(Block.mushroomRed.blockID);

	static WorldGenCustomFruitTree appleTree = new WorldGenCustomFruitTree(false, TFCBlocks.fruitTreeLeaves.blockID, 0);
	static WorldGenCustomFruitTree bananaTree = new WorldGenCustomFruitTree(false, TFCBlocks.fruitTreeLeaves.blockID, 1);
	static WorldGenCustomFruitTree orangeTree = new WorldGenCustomFruitTree(false, TFCBlocks.fruitTreeLeaves.blockID, 2);
	static WorldGenCustomFruitTree grappleTree = new WorldGenCustomFruitTree(false, TFCBlocks.fruitTreeLeaves.blockID, 3);
	static WorldGenCustomFruitTree lemonTree = new WorldGenCustomFruitTree(false, TFCBlocks.fruitTreeLeaves.blockID, 4);
	static WorldGenCustomFruitTree oliveTree = new WorldGenCustomFruitTree(false, TFCBlocks.fruitTreeLeaves.blockID, 5);
	static WorldGenCustomFruitTree cherryTree = new WorldGenCustomFruitTree(false, TFCBlocks.fruitTreeLeaves.blockID, 6);
	static WorldGenCustomFruitTree peachTree = new WorldGenCustomFruitTree(false, TFCBlocks.fruitTreeLeaves.blockID, 7);

	static WorldGenCustomFruitTree2 plumTree = new WorldGenCustomFruitTree2(false, TFCBlocks.fruitTreeLeaves2.blockID, 0);
	static WorldGenCustomFruitTree2 cacaoTree = new WorldGenCustomFruitTree2(false, TFCBlocks.fruitTreeLeaves2.blockID, 1);

	public WorldGenPlants()
	{

	}


	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) 
	{
		chunkX *= 16;
		chunkZ *= 16;

		int xCoord;
		int yCoord;
		int zCoord;

		int grassPerChunk = 0;
		int flowersPerChunk = 0;
		int mushroomsPerChunk = 0;

		DataLayer evt = ((TFCWorldChunkManager)world.provider.worldChunkMgr).getEVTLayerAt(chunkX, chunkZ);
		DataLayer rainfall = ((TFCWorldChunkManager)world.provider.worldChunkMgr).getRainfallLayerAt(chunkX, chunkZ);
		float bioTemperature;

		if(rainfall.floatdata1 >= 62.5f) 
		{

		}
		if(rainfall.floatdata1 >= 125) 
		{
			grassPerChunk+=12;
			flowersPerChunk += 1;
			mushroomsPerChunk += 1;
		}
		if(rainfall.floatdata1 >= 250) 
		{
			grassPerChunk+=18;
			flowersPerChunk += 1;
			mushroomsPerChunk += 1;
		}
		if(rainfall.floatdata1 >= 500) 
		{
			grassPerChunk+=24;
			flowersPerChunk += 1;
			mushroomsPerChunk += 1;
		}
		if(rainfall.floatdata1 >= 1000) 
		{
			mushroomsPerChunk += 1;
		}
		if(rainfall.floatdata1 >= 2000) 
		{
			mushroomsPerChunk += 1;
		}

		for (int i = 0; i < flowersPerChunk; ++i)
		{
			xCoord = chunkX + random.nextInt(16) + 8;
			zCoord = chunkZ + random.nextInt(16) + 8;
			yCoord = world.getTopSolidOrLiquidBlock(xCoord, zCoord);
			bioTemperature = TFC_Climate.getBioTemperatureHeight(xCoord, yCoord, zCoord);

			if(bioTemperature > 1.5)
			{
				plantYellowGen.generate(world, random, xCoord, yCoord, zCoord);

				if (random.nextInt(4) == 0)
				{
					xCoord = chunkX + random.nextInt(16) + 8;
					zCoord = chunkZ + random.nextInt(16) + 8;
					yCoord = world.getTopSolidOrLiquidBlock(xCoord, zCoord);
					bioTemperature = TFC_Climate.getBioTemperatureHeight(xCoord, yCoord, zCoord);
					if(bioTemperature > 1.5)
					{
						plantRedGen.generate(world, random, xCoord, yCoord, zCoord);
					}
				}
			}
		}



		for (int i  = 0; i < grassPerChunk; ++i)
		{

			xCoord = chunkX + random.nextInt(16) + 8;
			zCoord = chunkZ + random.nextInt(16) + 8;
			yCoord = world.getTopSolidOrLiquidBlock(xCoord, zCoord);

			bioTemperature = TFC_Climate.getBioTemperatureHeight(xCoord, yCoord, zCoord);
			if(bioTemperature >= 1.5)
			{
				//				WorldGenerator var6 = new WorldGenCustomTallGrass(Block.tallGrass.blockID, 1);
				//				var6.generate(world, random, xCoord, yCoord, zCoord);
				if (world.isAirBlock(xCoord, yCoord, zCoord) && 
						((BlockCustomTallGrass)Block.blocksList[Block.tallGrass.blockID]).canBlockStay(world, xCoord, yCoord, zCoord))
				{
					world.setBlock(xCoord, yCoord, zCoord, Block.tallGrass.blockID, 1, 0x2);
				}
			}
		}

		for (int i = 0; i < mushroomsPerChunk; ++i)
		{
			if (random.nextInt(4) == 0)
			{
				xCoord = chunkX + random.nextInt(16) + 8;
				zCoord = chunkZ + random.nextInt(16) + 8;
				yCoord = world.getTopSolidOrLiquidBlock(xCoord, zCoord);
				mushroomBrownGen.generate(world, random, xCoord, yCoord, zCoord);
			}

			if (random.nextInt(8) == 0)
			{
				xCoord = chunkX + random.nextInt(16) + 8;
				zCoord = chunkZ + random.nextInt(16) + 8;
				yCoord = world.getTopSolidOrLiquidBlock(xCoord, zCoord);
				mushroomRedGen.generate(world, random, xCoord, yCoord, zCoord);
			}
		}

		if (random.nextInt(70) == 0  && rainfall.floatdata1 > 500)
		{
			xCoord = chunkX + random.nextInt(16) + 8;
			zCoord = chunkZ + random.nextInt(16) + 8;
			yCoord = world.getTopSolidOrLiquidBlock(xCoord, zCoord);
			switch(random.nextInt(9))
			{
			default:
			{
				if(world.getBlockId(xCoord, yCoord, zCoord) == 0 && TFC_Core.isGrass(world.getBlockId(xCoord, yCoord-1, zCoord))) {
					appleTree.generate(world, random, xCoord, yCoord, zCoord);
				}
				break;
			}
			case 1:
			{
				if(world.getBlockId(xCoord, yCoord, zCoord) == 0 && TFC_Core.isGrass(world.getBlockId(xCoord, yCoord-1, zCoord))) {
					bananaTree.generate(world, random, xCoord, yCoord, zCoord);
				}
				break;
			}
			case 2:
			{
				if(world.getBlockId(xCoord, yCoord, zCoord) == 0 && TFC_Core.isGrass(world.getBlockId(xCoord, yCoord-1, zCoord))) {
					orangeTree.generate(world, random, xCoord, yCoord, zCoord);
				}
				break;
			}
			case 3:
			{
				if(world.getBlockId(xCoord, yCoord, zCoord) == 0 && TFC_Core.isGrass(world.getBlockId(xCoord, yCoord-1, zCoord))) {
					grappleTree.generate(world, random, xCoord, yCoord, zCoord);
				}
				break;
			}
			case 4:
			{
				if(world.getBlockId(xCoord, yCoord, zCoord) == 0 && TFC_Core.isGrass(world.getBlockId(xCoord, yCoord-1, zCoord))) {
					lemonTree.generate(world, random, xCoord, yCoord, zCoord);
				}
				break;
			}
			case 5:
			{
				if(world.getBlockId(xCoord, yCoord, zCoord) == 0 && TFC_Core.isGrass(world.getBlockId(xCoord, yCoord-1, zCoord))) {
					oliveTree.generate(world, random, xCoord, yCoord, zCoord);
				}
				break;
			}
			case 6:
			{
				if(world.getBlockId(xCoord, yCoord, zCoord) == 0 && TFC_Core.isGrass(world.getBlockId(xCoord, yCoord-1, zCoord))) {
					cherryTree.generate(world, random, xCoord, yCoord, zCoord);
				}
				break;
			}
			case 7:
			{
				if(world.getBlockId(xCoord, yCoord, zCoord) == 0 && TFC_Core.isGrass(world.getBlockId(xCoord, yCoord-1, zCoord))) {
					peachTree.generate(world, random, xCoord, yCoord, zCoord);
				}
				break;
			}
			case 8:
			{
				if(world.getBlockId(xCoord, yCoord, zCoord) == 0 && TFC_Core.isGrass(world.getBlockId(xCoord, yCoord-1, zCoord))) {
					plumTree.generate(world, random, xCoord, yCoord, zCoord);
				}
				break;
			}
			//                case 9:
			//                {
			//                    if(world.getBlockId(var2, var3, var4) == 0 && (world.getBlockId(var2, var3-1, var4) == mod_TFC_Core.terraGrass.blockID || world.getBlockId(var2, var3-1, var4) == mod_TFC_Core.terraGrass2.blockID))
			//                        cacaoTree.generate(world, rand, var2, var3, var4);
			//                    break;
			//                }
			}
		}

	}
}
