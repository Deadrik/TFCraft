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

	static WorldGenCustomFruitTree plumTree = new WorldGenCustomFruitTree(false, TFCBlocks.fruitTreeLeaves2.blockID, 8);
	static WorldGenCustomFruitTree cacaoTree = new WorldGenCustomFruitTree(false, TFCBlocks.fruitTreeLeaves2.blockID, 9);

	static WorldGenBerryBush wintergreenGen = new WorldGenBerryBush(false, 0, 12, 1, 5);
	static WorldGenBerryBush blueberryGen = new WorldGenBerryBush(false, 1, 6, 1, 4);
	static WorldGenBerryBush raspberryGen = new WorldGenBerryBush(false, 2, 5, 2, 4);
	static WorldGenBerryBush strawberryGen = new WorldGenBerryBush(false, 3, 8, 1, 4);
	static WorldGenBerryBush blackberryGen = new WorldGenBerryBush(false, 4, 5, 2, 4);
	static WorldGenBerryBush bunchberryGen = new WorldGenBerryBush(false, 5, 8, 1, 4);
	//static WorldGenBerryBush cranberryGen = new WorldGenBerryBush(false, 6, 15, 1, 6, TFCBlocks.Peat.blockID);
	static WorldGenBerryBush snowberryGen = new WorldGenBerryBush(false, 7, 6, 1, 4);
	static WorldGenBerryBush elderberryGen = new WorldGenBerryBush(false, 8, 5, 2, 4);
	static WorldGenBerryBush gooseberryGen = new WorldGenBerryBush(false, 9, 8, 1, 4);
	static WorldGenBerryBush cloudberryGen = new WorldGenBerryBush(false, 10, 12, 1, 6, TFCBlocks.Peat.blockID);

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
		float rain = TFC_Climate.getRainfall(chunkX, 144, chunkZ);
		float bioTemperature;

		if(rain >= 62.5f) 
		{

		}
		if(rain >= 125) 
		{
			grassPerChunk+=12;
			flowersPerChunk += 1;
			mushroomsPerChunk += 1;
		}
		if(rain >= 250) 
		{
			grassPerChunk+=18;
			flowersPerChunk += 1;
			mushroomsPerChunk += 1;
		}
		if(rain >= 500) 
		{
			grassPerChunk+=24;
			flowersPerChunk += 1;
			mushroomsPerChunk += 1;
		}
		if(rain >= 1000) 
		{
			mushroomsPerChunk += 1;
		}
		if(rain >= 2000) 
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

		genBushes(random, chunkX, chunkZ, world);


		for (int i  = 0; i < grassPerChunk; ++i)
		{

			xCoord = chunkX + random.nextInt(16) + 8;
			zCoord = chunkZ + random.nextInt(16) + 8;
			yCoord = world.getTopSolidOrLiquidBlock(xCoord, zCoord);

			bioTemperature = TFC_Climate.getBioTemperatureHeight(xCoord, yCoord, zCoord);
			if(bioTemperature >= 5)
			{
				if (world.isAirBlock(xCoord, yCoord, zCoord) && 
						((BlockCustomTallGrass)Block.blocksList[Block.tallGrass.blockID]).canBlockStay(world, xCoord, yCoord, zCoord) &&
						!TFC_Core.isDryGrass(world.getBlockId(xCoord, yCoord-1, zCoord)))
				{
					world.setBlock(xCoord, yCoord, zCoord, Block.tallGrass.blockID, 1, 0x2);
				}
			}

			if(bioTemperature >= 0)
			{
				//				WorldGenerator var6 = new WorldGenCustomTallGrass(Block.tallGrass.blockID, 1);
				//				var6.generate(world, random, xCoord, yCoord, zCoord);
				if (world.isAirBlock(xCoord, yCoord, zCoord) && 
						((BlockCustomTallGrass)Block.blocksList[Block.tallGrass.blockID]).canBlockStay(world, xCoord, yCoord, zCoord) &&
						TFC_Core.isDryGrass(world.getBlockId(xCoord, yCoord-1, zCoord)))
				{
					world.setBlock(xCoord, yCoord, zCoord, Block.tallGrass.blockID, 3, 0x2);
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


		if (random.nextInt(70) == 0 && rain > 500)
		{
			xCoord = chunkX + random.nextInt(16) + 8;
			zCoord = chunkZ + random.nextInt(16) + 8;
			yCoord = world.getTopSolidOrLiquidBlock(xCoord, zCoord);
			bioTemperature = TFC_Climate.getBioTemperatureHeight(xCoord, yCoord, zCoord);
			switch(random.nextInt(9))
			{
			default:
			{
				if(bioTemperature > 8 && world.getBlockId(xCoord, yCoord, zCoord) == 0 && TFC_Core.isGrass(world.getBlockId(xCoord, yCoord-1, zCoord))) {
					appleTree.generate(world, random, xCoord, yCoord, zCoord);
				}
				break;
			}
			case 1:
			{
				if(bioTemperature > 15 && world.getBlockId(xCoord, yCoord, zCoord) == 0 && TFC_Core.isGrass(world.getBlockId(xCoord, yCoord-1, zCoord))) {
					bananaTree.generate(world, random, xCoord, yCoord, zCoord);
				}
				break;
			}
			case 2:
			{
				if(bioTemperature > 12 && bioTemperature > 8 && world.getBlockId(xCoord, yCoord, zCoord) == 0 && TFC_Core.isGrass(world.getBlockId(xCoord, yCoord-1, zCoord))) {
					orangeTree.generate(world, random, xCoord, yCoord, zCoord);
				}
				break;
			}
			case 3:
			{
				if(bioTemperature > 8 && world.getBlockId(xCoord, yCoord, zCoord) == 0 && TFC_Core.isGrass(world.getBlockId(xCoord, yCoord-1, zCoord))) {
					grappleTree.generate(world, random, xCoord, yCoord, zCoord);
				}
				break;
			}
			case 4:
			{
				if(bioTemperature > 10 && world.getBlockId(xCoord, yCoord, zCoord) == 0 && TFC_Core.isGrass(world.getBlockId(xCoord, yCoord-1, zCoord))) {
					lemonTree.generate(world, random, xCoord, yCoord, zCoord);
				}
				break;
			}
			case 5:
			{
				if(bioTemperature > 10 && world.getBlockId(xCoord, yCoord, zCoord) == 0 && TFC_Core.isGrass(world.getBlockId(xCoord, yCoord-1, zCoord))) {
					oliveTree.generate(world, random, xCoord, yCoord, zCoord);
				}
				break;
			}
			case 6:
			{
				if(bioTemperature > 8 && world.getBlockId(xCoord, yCoord, zCoord) == 0 && TFC_Core.isGrass(world.getBlockId(xCoord, yCoord-1, zCoord))) {
					cherryTree.generate(world, random, xCoord, yCoord, zCoord);
				}
				break;
			}
			case 7:
			{
				if(bioTemperature > 10 && world.getBlockId(xCoord, yCoord, zCoord) == 0 && TFC_Core.isGrass(world.getBlockId(xCoord, yCoord-1, zCoord))) {
					peachTree.generate(world, random, xCoord, yCoord, zCoord);
				}
				break;
			}
			case 8:
			{
				if(bioTemperature > 10 && world.getBlockId(xCoord, yCoord, zCoord) == 0 && TFC_Core.isGrass(world.getBlockId(xCoord, yCoord-1, zCoord))) {
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


	private void genBushes(Random random, int chunkX, int chunkZ, World world) {
		int xCoord;
		int yCoord;
		int zCoord;
		if (random.nextInt(12) == 0)
		{
			xCoord = chunkX + random.nextInt(16) + 8;
			zCoord = chunkZ + random.nextInt(16) + 8;
			yCoord = world.getTopSolidOrLiquidBlock(xCoord, zCoord);
			switch(random.nextInt(11))
			{
			default:
				wintergreenGen.generate(world, random, xCoord, yCoord, zCoord);
				break;
			case 1:
				blueberryGen.generate(world, random, xCoord, yCoord, zCoord);
				break;
			case 2:
				raspberryGen.generate(world, random, xCoord, yCoord, zCoord);
				break;
			case 3:
				strawberryGen.generate(world, random, xCoord, yCoord, zCoord);
				break;
			case 4:
				blackberryGen.generate(world, random, xCoord, yCoord, zCoord);
				break;
			case 5:
				bunchberryGen.generate(world, random, xCoord, yCoord, zCoord);
				break;
				/*case 6:
				cranberryGen.generate(world, random, xCoord, yCoord, zCoord);
				break;*/
			case 7:
				snowberryGen.generate(world, random, xCoord, yCoord, zCoord);
				break;
			case 8:
				elderberryGen.generate(world, random, xCoord, yCoord, zCoord);
				break;
			case 9:
				gooseberryGen.generate(world, random, xCoord, yCoord, zCoord);
				break;
				case 10:
				cloudberryGen.generate(world, random, xCoord, yCoord, zCoord);
				break;
			}
		}
	}
}
