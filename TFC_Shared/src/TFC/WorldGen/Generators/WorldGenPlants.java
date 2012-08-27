package TFC.WorldGen.Generators;

import java.util.Random;

import TFC.WorldGen.BiomeDecoratorTFC;

import cpw.mods.fml.common.IWorldGenerator;

import net.minecraft.src.*;

public class WorldGenPlants implements IWorldGenerator
{
	public WorldGenPlants()
	{

	}


	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) 
	{
		BiomeGenBase biome = world.getBiomeGenForCoords(chunkX, chunkZ);
        WorldGenCustomFlowers plantYellowGen = new WorldGenCustomFlowers(Block.plantYellow.blockID);
        WorldGenCustomFlowers plantRedGen = new WorldGenCustomFlowers(Block.plantRed.blockID);
        WorldGenCustomFlowers mushroomBrownGen = new WorldGenCustomFlowers(Block.mushroomBrown.blockID);
        WorldGenCustomFlowers mushroomRedGen = new WorldGenCustomFlowers(Block.mushroomRed.blockID);

        WorldGenCustomFruitTree appleTree = new WorldGenCustomFruitTree(false, TFCBlocks.fruitTreeLeaves.blockID, 0);
        WorldGenCustomFruitTree bananaTree = new WorldGenCustomFruitTree(false, TFCBlocks.fruitTreeLeaves.blockID, 1);
        WorldGenCustomFruitTree orangeTree = new WorldGenCustomFruitTree(false, TFCBlocks.fruitTreeLeaves.blockID, 2);
        WorldGenCustomFruitTree grappleTree = new WorldGenCustomFruitTree(false, TFCBlocks.fruitTreeLeaves.blockID, 3);
        WorldGenCustomFruitTree lemonTree = new WorldGenCustomFruitTree(false, TFCBlocks.fruitTreeLeaves.blockID, 4);
        WorldGenCustomFruitTree oliveTree = new WorldGenCustomFruitTree(false, TFCBlocks.fruitTreeLeaves.blockID, 5);
        WorldGenCustomFruitTree cherryTree = new WorldGenCustomFruitTree(false, TFCBlocks.fruitTreeLeaves.blockID, 6);
        WorldGenCustomFruitTree peachTree = new WorldGenCustomFruitTree(false, TFCBlocks.fruitTreeLeaves.blockID, 7);

        WorldGenCustomFruitTree2 plumTree = new WorldGenCustomFruitTree2(false, TFCBlocks.fruitTreeLeaves2.blockID, 0);
        WorldGenCustomFruitTree2 cacaoTree = new WorldGenCustomFruitTree2(false, TFCBlocks.fruitTreeLeaves2.blockID, 1);

        int var2;
        int var3;
        int var4;
        int var7;

        for (var2 = 0; var2 < ((BiomeDecoratorTFC)biome.theBiomeDecorator).flowersPerChunk; ++var2)
        {
            var3 = chunkX + random.nextInt(16) + 8;
            var4 = random.nextInt(256);
            var7 = chunkZ + random.nextInt(16) + 8;

            plantYellowGen.generate(world, random, var3, var4, var7);

            if (random.nextInt(4) == 0)
            {
                var3 = chunkX + random.nextInt(16) + 8;
                var4 = random.nextInt(256);
                var7 = chunkZ + random.nextInt(16) + 8;
                plantRedGen.generate(world, random, var3, var4, var7);
            }
        }

        for (var2 = 0; var2 < ((BiomeDecoratorTFC)biome.theBiomeDecorator).grassPerChunk; ++var2)
        {
            var3 = chunkX + random.nextInt(16) + 8;
            var4 = random.nextInt(256);
            var7 = chunkZ + random.nextInt(16) + 8;
            WorldGenerator var6 = new WorldGenCustomTallGrass(Block.tallGrass.blockID, 1);
            var6.generate(world, random, var3, var4, var7);
        }

        for (var2 = 0; var2 < ((BiomeDecoratorTFC)biome.theBiomeDecorator).mushroomsPerChunk; ++var2)
        {
            if (random.nextInt(4) == 0)
            {
                var3 = chunkX + random.nextInt(16) + 8;
                var4 = chunkZ + random.nextInt(16) + 8;
                var7 = world.getHeightValue(var3, var4);
                mushroomBrownGen.generate(world, random, var3, var7, var4);
            }

            if (random.nextInt(8) == 0)
            {
                var3 = chunkX + random.nextInt(16) + 8;
                var4 = chunkZ + random.nextInt(16) + 8;
                var7 = random.nextInt(128);
                mushroomRedGen.generate(world, random, var3, var7, var4);
            }
        }

        if (random.nextInt(4) == 0)
        {
            var2 = chunkX + random.nextInt(16) + 8;
            var3 = random.nextInt(256);
            var4 = chunkZ + random.nextInt(16) + 8;
            mushroomBrownGen.generate(world, random, var2, var3, var4);
        }

        if (random.nextInt(8) == 0)
        {
            var2 = chunkX + random.nextInt(16) + 8;
            var3 = random.nextInt(256);
            var4 = chunkZ + random.nextInt(16) + 8;
            mushroomRedGen.generate(world, random, var2, var3, var4);
        }

        if (random.nextInt(50) == 0 && biome.temperature >= 18)
        {
            var2 = chunkX + random.nextInt(16) + 8;

            var4 = chunkZ + random.nextInt(16) + 8;

            var3 = world.getTopSolidOrLiquidBlock(var2, var4);
            switch(random.nextInt(9))
            {
                default:
                {
                    if(world.getBlockId(var2, var3, var4) == 0 && (world.getBlockId(var2, var3-1, var4) == TFCBlocks.terraGrass.blockID || world.getBlockId(var2, var3-1, var4) == TFCBlocks.terraGrass2.blockID))
                        appleTree.generate(world, random, var2, var3, var4);
                    break;
                }
                case 1:
                {
                    if(world.getBlockId(var2, var3, var4) == 0 && (world.getBlockId(var2, var3-1, var4) == TFCBlocks.terraGrass.blockID || world.getBlockId(var2, var3-1, var4) == TFCBlocks.terraGrass2.blockID))
                        bananaTree.generate(world, random, var2, var3, var4);
                    break;
                }
                case 2:
                {
                    if(world.getBlockId(var2, var3, var4) == 0 && (world.getBlockId(var2, var3-1, var4) == TFCBlocks.terraGrass.blockID || world.getBlockId(var2, var3-1, var4) == TFCBlocks.terraGrass2.blockID))
                        orangeTree.generate(world, random, var2, var3, var4);
                    break;
                }
                case 3:
                {
                    if(world.getBlockId(var2, var3, var4) == 0 && (world.getBlockId(var2, var3-1, var4) == TFCBlocks.terraGrass.blockID || world.getBlockId(var2, var3-1, var4) == TFCBlocks.terraGrass2.blockID))
                        grappleTree.generate(world, random, var2, var3, var4);
                    break;
                }
                case 4:
                {
                    if(world.getBlockId(var2, var3, var4) == 0 && (world.getBlockId(var2, var3-1, var4) == TFCBlocks.terraGrass.blockID || world.getBlockId(var2, var3-1, var4) == TFCBlocks.terraGrass2.blockID))
                        lemonTree.generate(world, random, var2, var3, var4);
                    break;
                }
                case 5:
                {
                    if(world.getBlockId(var2, var3, var4) == 0 && (world.getBlockId(var2, var3-1, var4) == TFCBlocks.terraGrass.blockID || world.getBlockId(var2, var3-1, var4) == TFCBlocks.terraGrass2.blockID))
                        oliveTree.generate(world, random, var2, var3, var4);
                    break;
                }
                case 6:
                {
                    if(world.getBlockId(var2, var3, var4) == 0 && (world.getBlockId(var2, var3-1, var4) == TFCBlocks.terraGrass.blockID || world.getBlockId(var2, var3-1, var4) == TFCBlocks.terraGrass2.blockID))
                        cherryTree.generate(world, random, var2, var3, var4);
                    break;
                }
                case 7:
                {
                    if(world.getBlockId(var2, var3, var4) == 0 && (world.getBlockId(var2, var3-1, var4) == TFCBlocks.terraGrass.blockID || world.getBlockId(var2, var3-1, var4) == TFCBlocks.terraGrass2.blockID))
                        peachTree.generate(world, random, var2, var3, var4);
                    break;
                }
                case 8:
                {
                    if(world.getBlockId(var2, var3, var4) == 0 && (world.getBlockId(var2, var3-1, var4) == TFCBlocks.terraGrass.blockID || world.getBlockId(var2, var3-1, var4) == TFCBlocks.terraGrass2.blockID))
                        plumTree.generate(world, random, var2, var3, var4);
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
