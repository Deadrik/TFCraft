package net.minecraft.src.TFC_Core;

import java.util.ArrayList;
import java.util.Random;
import net.minecraft.src.*;
import net.minecraft.src.TFC_Core.General.TFCSettings;
import net.minecraft.src.forge.MinecraftForge;

public class TFC_Core
{
	public enum Direction{PosX,PosZ,NegX,NegZ,None,PosXPosZ,PosXNegZ,NegXPosZ,NegXNegZ,NegY,PosY}

	public static Item[] Axes = {mod_TFC_Core.terraSedAxe,mod_TFC_Core.terraIgInAxe,mod_TFC_Core.terraIgExAxe,mod_TFC_Core.terraMMAxe,
		mod_TFC_Core.terraBismuthAxe,mod_TFC_Core.terraBismuthBronzeAxe,mod_TFC_Core.terraBlackBronzeAxe,
		mod_TFC_Core.terraBlackSteelAxe,mod_TFC_Core.terraBlueSteelAxe,mod_TFC_Core.terraBronzeAxe,mod_TFC_Core.terraCopperAxe,
		mod_TFC_Core.terraWroughtIronAxe,mod_TFC_Core.terraRedSteelAxe,mod_TFC_Core.terraRoseGoldAxe,mod_TFC_Core.terraSteelAxe,
		mod_TFC_Core.terraTinAxe,mod_TFC_Core.terraZincAxe,
		mod_TFC_Core.boneSedAxe,mod_TFC_Core.boneIgInAxe,mod_TFC_Core.boneIgExAxe,mod_TFC_Core.boneMMAxe};

	public static Item[] Chisels = {mod_TFC_Core.BismuthChisel,mod_TFC_Core.BismuthBronzeChisel,mod_TFC_Core.BlackBronzeChisel,
		mod_TFC_Core.BlackSteelChisel,mod_TFC_Core.BlueSteelChisel,mod_TFC_Core.BronzeChisel,mod_TFC_Core.CopperChisel,
		mod_TFC_Core.WroughtIronChisel,mod_TFC_Core.RedSteelChisel,mod_TFC_Core.RoseGoldChisel,mod_TFC_Core.SteelChisel,
		mod_TFC_Core.TinChisel,mod_TFC_Core.ZincChisel};


	public static Item[] Saws = {mod_TFC_Core.BismuthSaw,mod_TFC_Core.BismuthBronzeSaw,mod_TFC_Core.BlackBronzeSaw,
		mod_TFC_Core.BlackSteelSaw,mod_TFC_Core.BlueSteelSaw,mod_TFC_Core.BronzeSaw,mod_TFC_Core.CopperSaw,
		mod_TFC_Core.WroughtIronSaw,mod_TFC_Core.RedSteelSaw,mod_TFC_Core.RoseGoldSaw,mod_TFC_Core.SteelSaw,
		mod_TFC_Core.TinSaw,mod_TFC_Core.ZincSaw};
	
	private static void createOre(int i, int j, int[] Layers, int rarity, int veinSize, 
			int veinAmount, int height, int diameter, int vDensity, int hDensity,World world, Random rand, int chunkX, int chunkZ, int min, int max)
	{
		for(int n = 0; n < Layers.length/2;)
		{
			new WorldGenMinableTFC(i, j,Layers[n],Layers[n+1],rarity,veinSize,veinAmount,height,diameter,vDensity,hDensity).generate(
					world, rand, chunkX, chunkZ, min, max);
			n+=2;
		}
	}
	public static void CreateTreeLimb(World world, int i, int j, int k, int meta, Direction dir, Random R)
	{
		if(dir == Direction.PosX)
		{
			if(world.getBlockId(i+1, j, k) == Block.leaves.blockID)
			{
				world.setBlockAndMetadata(i+1, j, k, Block.wood.blockID, meta);
				SurroundWithLeaves(world,i+1,j,k,meta,R);
				if(world.getBlockId(i+2, j, k) == Block.leaves.blockID)
				{
					world.setBlockAndMetadata(i+2, j, k, Block.wood.blockID, meta);
					SurroundWithLeaves(world,i+2,j,k,meta,R);
					if(world.getBlockId(i+3, j, k) == Block.leaves.blockID && R.nextInt(10) == 0)
					{
						world.setBlockAndMetadata(i+3, j, k, Block.wood.blockID, meta);
						SurroundWithLeaves(world,i+3,j,k,meta,R);
						if(world.getBlockId(i+4, j-1, k) != Block.leaves.blockID && R.nextInt(10) == 0)
						{
							world.setBlockAndMetadata(i+4, j-1, k, Block.wood.blockID, meta);
							SurroundWithLeaves(world,i+4,j-1,k,meta,R);
						}
					}
				}
			}
		}
		if(dir == Direction.NegX)
		{
			if(world.getBlockId(i-1, j, k) == Block.leaves.blockID)
			{
				world.setBlockAndMetadata(i-1, j, k, Block.wood.blockID, meta);
				SurroundWithLeaves(world,i-1,j,k,meta,R);
				if(world.getBlockId(i-2, j, k) == Block.leaves.blockID)
				{
					world.setBlockAndMetadata(i-2, j, k, Block.wood.blockID, meta);
					SurroundWithLeaves(world,i-2,j,k,meta,R);
					if(world.getBlockId(i-3, j, k) == Block.leaves.blockID && R.nextInt(10) == 0)
					{
						world.setBlockAndMetadata(i-3, j, k, Block.wood.blockID, meta);
						SurroundWithLeaves(world,i-3,j,k,meta,R);
						if(world.getBlockId(i-4, j-1, k) == Block.leaves.blockID && R.nextInt(10) == 0)
						{
							world.setBlockAndMetadata(i-4, j-1, k, Block.wood.blockID, meta);
							SurroundWithLeaves(world,i-4,j-1,k,meta,R);
						}
					}
				}
			}
		}
		if(dir == Direction.PosZ)
		{
			if(world.getBlockId(i, j, k+1) == Block.leaves.blockID)
			{
				world.setBlockAndMetadata(i, j, k+1, Block.wood.blockID, meta);
				SurroundWithLeaves(world,i,j,k+1,meta,R);
				if(world.getBlockId(i, j, k+2) == Block.leaves.blockID)
				{
					world.setBlockAndMetadata(i, j, k+2, Block.wood.blockID, meta);
					SurroundWithLeaves(world,i,j,k+2,meta,R);
					if(world.getBlockId(i, j, k+3) == Block.leaves.blockID && R.nextInt(10) == 0)
					{
						world.setBlockAndMetadata(i, j, k+3, Block.wood.blockID, meta);
						SurroundWithLeaves(world,i,j,k+3,meta,R);
						if(world.getBlockId(i, j-1, k+4) == Block.leaves.blockID && R.nextInt(10) == 0)
						{
							world.setBlockAndMetadata(i, j-1, k+4, Block.wood.blockID, meta);
							SurroundWithLeaves(world,i,j-1,k+4,meta,R);
						}
					}
				}
			}


		}
		if(dir == Direction.NegZ)
		{
			if(world.getBlockId(i, j, k-1) == Block.leaves.blockID)
			{
				world.setBlockAndMetadata(i, j, k-1, Block.wood.blockID, meta);
				SurroundWithLeaves(world,i,j,k-1,meta,R);
				if(world.getBlockId(i, j, k-2) == Block.leaves.blockID)
				{
					world.setBlockAndMetadata(i, j, k-2, Block.wood.blockID, meta);
					SurroundWithLeaves(world,i,j,k-2,meta,R);
					if(world.getBlockId(i, j, k-3) == Block.leaves.blockID && R.nextInt(10) == 0)
					{
						world.setBlockAndMetadata(i, j, k-3, Block.wood.blockID, meta);
						SurroundWithLeaves(world,i,j,k-3,meta,R);
						if(world.getBlockId(i, j-1, k-4) == Block.leaves.blockID && R.nextInt(10) == 0)
						{
							world.setBlockAndMetadata(i, j-1, k-4, Block.wood.blockID, meta);
							SurroundWithLeaves(world,i,j-1,k-4,meta,R);
						}
					}
				}
			}	
		}
	}

	public static void Generate(World world, Random rand, int chunkX, int chunkZ, int min, int max)
    {
	    int height = max-min;
        //============Copper
        createOre(mod_TFC_Core.terraOre.blockID, 0,new int[]{mod_TFC_Core.terraStoneIgEx.blockID,-1,Block.sandStone.blockID,-1},//IgEx and Sandstone, veins
                /*rarity*/30,/*veinSize*/20,/*veinAmt*/25,/*height*/height,/*diameter*/50,/*vDensity*/10,/*hDensity*/60,         world, rand, chunkX, chunkZ, min, max);

        //============Gold
        createOre(mod_TFC_Core.terraOre.blockID, 1,new int[]{mod_TFC_Core.terraStoneIgEx.blockID,-1,mod_TFC_Core.terraStoneIgIn.blockID,-1},//Ig veins
                /*rarity*/50,/*veinSize*/10,/*veinAmt*/15,/*height*/height,/*diameter*/40,/*vDensity*/10,/*hDensity*/60,         world, rand, chunkX, chunkZ, min, max);

        //============Hematite
        createOre(mod_TFC_Core.terraOre.blockID, 3,new int[]{mod_TFC_Core.terraStoneIgEx.blockID,-1},//IgEx veins
                /*rarity*/30,/*veinSize*/20,/*veinAmt*/12,/*height*/height,/*diameter*/50,/*vDensity*/10,/*hDensity*/60,         world, rand, chunkX, chunkZ, min, max);

        //============Silver
        createOre(mod_TFC_Core.terraOre.blockID, 4,new int[]{mod_TFC_Core.terraStoneIgIn.blockID,0,mod_TFC_Core.terraStoneMM.blockID,4},//granite and gneiss, veins
                /*rarity*/30,/*veinSize*/10,/*veinAmt*/15,/*height*/height,/*diameter*/50,/*vDensity*/10,/*hDensity*/60,         world, rand, chunkX, chunkZ, min, max);

        //============Cassiterite
        createOre(mod_TFC_Core.terraOre.blockID, 5,new int[]{mod_TFC_Core.terraStoneIgIn.blockID,0},//Granite Veins
                /*rarity*/30,/*veinSize*/20,/*veinAmt*/15,/*height*/height,/*diameter*/50,/*vDensity*/10,/*hDensity*/60,         world, rand, chunkX, chunkZ, min, max);

        //============Galena
        createOre(mod_TFC_Core.terraOre.blockID, 6,new int[]{mod_TFC_Core.terraStoneIgEx.blockID,-1,mod_TFC_Core.terraStoneMM.blockID,-1,
                mod_TFC_Core.terraStoneIgIn.blockID,0,mod_TFC_Core.terraStoneSed.blockID,5},//igex, mm, granite, limestone as veins
                /*rarity*/35,/*veinSize*/20,/*veinAmt*/25,/*height*/height,/*diameter*/50,/*vDensity*/20,/*hDensity*/60,         world, rand, chunkX, chunkZ, min, max);

        //============Bismuthinite
        createOre(mod_TFC_Core.terraOre.blockID, 7,new int[]{mod_TFC_Core.terraStoneIgIn.blockID,0},//Granite Veins
                /*rarity*/28,/*veinSize*/20,/*veinAmt*/15,/*height*/height,/*diameter*/50,/*vDensity*/10,/*hDensity*/60,         world, rand, chunkX, chunkZ, min, max);

        //============Garnierite
        createOre(mod_TFC_Core.terraOre.blockID, 8,new int[]{mod_TFC_Core.terraStoneIgIn.blockID,2},//Gabbro Veins
                /*rarity*/48,/*veinSize*/10,/*veinAmt*/25,/*height*/height,/*diameter*/40,/*vDensity*/10,/*hDensity*/60,         world, rand, chunkX, chunkZ, min, max);

        //============Malachite
        createOre(mod_TFC_Core.terraOre.blockID, 9,new int[]{mod_TFC_Core.terraStoneSed.blockID,5,mod_TFC_Core.terraStoneMM.blockID,5},//limestone and marble veins
                /*rarity*/39,/*veinSize*/20,/*veinAmt*/15,/*height*/height,/*diameter*/20,/*vDensity*/30,/*hDensity*/20,         world, rand, chunkX, chunkZ, min, max);

        //============Magnetite
        createOre(mod_TFC_Core.terraOre.blockID, 10,new int[]{mod_TFC_Core.terraStoneSed.blockID,-1},//Sedimentary, Large Cluster
                /*rarity*/55,/*veinSize*/30,/*veinAmt*/6,/*height*/height,/*diameter*/40,/*vDensity*/10,/*hDensity*/80,         world, rand, chunkX, chunkZ, min, max);

        //============Limonite
        createOre(mod_TFC_Core.terraOre.blockID, 11,new int[]{mod_TFC_Core.terraStoneSed.blockID,-1},//Sedimentary, Large Cluster
                /*rarity*/55,/*veinSize*/12,/*veinAmt*/20,/*height*/height,/*diameter*/25,/*vDensity*/40,/*hDensity*/40,         world, rand, chunkX, chunkZ, min, max);

        //============Sphalerite
        createOre(mod_TFC_Core.terraOre.blockID, 12,new int[]{mod_TFC_Core.terraStoneMM.blockID,-1},//mm, veins
                /*rarity*/40,/*veinSize*/20,/*veinAmt*/8,/*height*/height,/*diameter*/15,/*vDensity*/60,/*hDensity*/40,         world, rand, chunkX, chunkZ, min, max);

        //============Tetrahedrite
        createOre(mod_TFC_Core.terraOre.blockID, 13,new int[]{mod_TFC_Core.terraStoneIgEx.blockID,-1,mod_TFC_Core.terraStoneMM.blockID,-1,
                mod_TFC_Core.terraStoneIgIn.blockID,-1,mod_TFC_Core.terraStoneSed.blockID,-1},//everything, veins
                /*rarity*/65,/*veinSize*/25,/*veinAmt*/15,/*height*/height,/*diameter*/40,/*vDensity*/30,/*hDensity*/30,         world, rand, chunkX, chunkZ, min, max);

        //============Bituminous Coal
        createOre(mod_TFC_Core.terraOre.blockID, 14,new int[]{mod_TFC_Core.terraStoneSed.blockID,-1},//sedimentary, veins
                /*rarity*/48,/*veinSize*/28,/*veinAmt*/6,/*height*/height,/*diameter*/22,/*vDensity*/60,/*hDensity*/70,         world, rand, chunkX, chunkZ, min, max);

        //============Lignite
        createOre(mod_TFC_Core.terraOre.blockID, 15,new int[]{mod_TFC_Core.terraStoneSed.blockID,-1},//sedimentary, veins
                /*rarity*/48,/*veinSize*/28,/*veinAmt*/6,/*height*/height,/*diameter*/25,/*vDensity*/10,/*hDensity*/30,         world, rand, chunkX, chunkZ, min, max);

        //============Kaolinite
        createOre(mod_TFC_Core.terraOre2.blockID, 0,new int[]{mod_TFC_Core.terraStoneSed.blockID,-1},//sedimentary, large clusters
                /*rarity*/60,/*veinSize*/40,/*veinAmt*/2,/*height*/height,/*diameter*/40,/*vDensity*/50,/*hDensity*/90,         world, rand, chunkX, chunkZ, min, max);

        //============Gypsum
        createOre(mod_TFC_Core.terraOre2.blockID, 1,new int[]{mod_TFC_Core.terraStoneSed.blockID,-1},//sedimentary, large clusters
                /*rarity*/60,/*veinSize*/40,/*veinAmt*/2,/*height*/height,/*diameter*/40,/*vDensity*/50,/*hDensity*/90,         world, rand, chunkX, chunkZ, min, max);

        //============Satinspar
        createOre(mod_TFC_Core.terraOre2.blockID, 2,new int[]{mod_TFC_Core.terraOre2.blockID,8},//gypsum, small clusters
                /*rarity*/42,/*veinSize*/6,/*veinAmt*/10,/*height*/height,/*diameter*/40,/*vDensity*/40,/*hDensity*/40,         world, rand, chunkX, chunkZ, min, max);

        //============Selenite
        createOre(mod_TFC_Core.terraOre2.blockID, 3,new int[]{mod_TFC_Core.terraOre2.blockID,8},//gypsum, small clusters
                /*rarity*/42,/*veinSize*/6,/*veinAmt*/10,/*height*/height,/*diameter*/40,/*vDensity*/40,/*hDensity*/40,         world, rand, chunkX, chunkZ, min, max);

        //============Graphite
        createOre(mod_TFC_Core.terraOre2.blockID, 4,new int[]{mod_TFC_Core.terraStoneMM.blockID,4,mod_TFC_Core.terraStoneMM.blockID,0,
                mod_TFC_Core.terraStoneMM.blockID,5, mod_TFC_Core.terraStoneMM.blockID,3},//gneiss, quartzite, marble, schist, small clusters
                /*rarity*/42,/*veinSize*/6,/*veinAmt*/14,/*height*/height,/*diameter*/40,/*vDensity*/40,/*hDensity*/40,         world, rand, chunkX, chunkZ, min, max);

        //============Kimberlite
        createOre(mod_TFC_Core.terraOre2.blockID, 5,new int[]{mod_TFC_Core.terraStoneIgIn.blockID,2},//Gabbro, large clusters
                /*rarity*/60,/*veinSize*/40,/*veinAmt*/10,/*height*/height,/*diameter*/40,/*vDensity*/40,/*hDensity*/90,         world, rand, chunkX, chunkZ, min, max);

        //============Petrified Wood
        createOre(mod_TFC_Core.terraOre2.blockID, 6,new int[]{mod_TFC_Core.terraStoneSed.blockID,-1},//Sedimentary, small clusters 
                /*rarity*/70,/*veinSize*/10,/*veinAmt*/5,/*height*/height,/*diameter*/20,/*vDensity*/10,/*hDensity*/40,         world, rand, chunkX, chunkZ, min, max);

        //============Sulfur
        //      createOre(mod_TFCraft.terraOre.blockID, 14,new int[]{mod_TFCraft.terraStoneIgEx.blockID,-1,mod_TFCraft.terraOre2.blockID,8},//igex, gypsum small clusters
        //              /*rarity*/4,/*veinSize*/6,/*veinAmt*/10,/*height*/128,/*diameter*/40,/*vDensity*/40,/*hDensity*/40,         world, rand, chunkX, chunkZ);

        //============Jet
        createOre(mod_TFC_Core.terraOre2.blockID, 8,new int[]{mod_TFC_Core.terraStoneSed.blockID,-1},//Sedimentary, med clusters 
                /*rarity*/55,/*veinSize*/30,/*veinAmt*/10,/*height*/height,/*diameter*/40,/*vDensity*/60,/*hDensity*/60,         world, rand, chunkX, chunkZ, min, max);

        //============Microcline
        createOre(mod_TFC_Core.terraOre2.blockID, 9,new int[]{mod_TFC_Core.terraStoneIgIn.blockID,0},//granite, large clusters 
                /*rarity*/45,/*veinSize*/64,/*veinAmt*/2,/*height*/height,/*diameter*/50,/*vDensity*/10,/*hDensity*/40,         world, rand, chunkX, chunkZ, min, max);

        //============Pitchblende
        createOre(mod_TFC_Core.terraOre2.blockID, 10,new int[]{mod_TFC_Core.terraStoneIgIn.blockID,0},//granite, small clusters 
                /*rarity*/50,/*veinSize*/10,/*veinAmt*/10,/*height*/height,/*diameter*/50,/*vDensity*/10,/*hDensity*/40,         world, rand, chunkX, chunkZ, min, max);

        //============Cinnabar
        createOre(mod_TFC_Core.terraOre2.blockID, 11,new int[]{mod_TFC_Core.terraStoneIgEx.blockID,-1,mod_TFC_Core.terraStoneSed.blockID,2,
                mod_TFC_Core.terraStoneMM.blockID,0},//igex, shale, quartzite small clusters
                /*rarity*/45,/*veinSize*/15,/*veinAmt*/20,/*height*/height,/*diameter*/50,/*vDensity*/40,/*hDensity*/40,         world, rand, chunkX, chunkZ, min, max);

        //============Cryolite
        createOre(mod_TFC_Core.terraOre2.blockID, 12,new int[]{mod_TFC_Core.terraStoneIgIn.blockID,0},//granite, small clusters 
                /*rarity*/50,/*veinSize*/10,/*veinAmt*/10,/*height*/height,/*diameter*/50,/*vDensity*/10,/*hDensity*/40,         world, rand, chunkX, chunkZ, min, max);

        //============Saltpeter
        createOre(mod_TFC_Core.terraOre2.blockID, 13,new int[]{mod_TFC_Core.terraStoneSed.blockID,-1},//sed, small clusters 
                /*rarity*/50,/*veinSize*/10,/*veinAmt*/10,/*height*/height,/*diameter*/50,/*vDensity*/10,/*hDensity*/40,         world, rand, chunkX, chunkZ, min, max);

        //============Olivine(Out of Order) must come before serpentine
        createOre(mod_TFC_Core.terraOre3.blockID, 1,new int[]{mod_TFC_Core.terraStoneIgIn.blockID,2},//gabbro, large clusters 
                /*rarity*/40,/*veinSize*/30,/*veinAmt*/4,/*height*/height,/*diameter*/50,/*vDensity*/10,/*hDensity*/60,         world, rand, chunkX, chunkZ, min, max);

        //============Serpentine
        createOre(mod_TFC_Core.terraOre2.blockID, 14,new int[]{mod_TFC_Core.terraOre3.blockID,8},//Olivine, small clusters 
                /*rarity*/40,/*veinSize*/10,/*veinAmt*/8,/*height*/height,/*diameter*/50,/*vDensity*/10,/*hDensity*/60,         world, rand, chunkX, chunkZ, min, max);

        //============Sylvite
        createOre(mod_TFC_Core.terraOre2.blockID, 15,new int[]{mod_TFC_Core.terraStoneSed.blockID,4},//Rock Salt, large clusters 
                /*rarity*/40,/*veinSize*/40,/*veinAmt*/4,/*height*/height,/*diameter*/50,/*vDensity*/10,/*hDensity*/60,         world, rand, chunkX, chunkZ, min, max);

        //============Borax
        createOre(mod_TFC_Core.terraOre3.blockID, 0,new int[]{mod_TFC_Core.terraStoneSed.blockID,4},//Rock Salt, large clusters 
                /*rarity*/40,/*veinSize*/30,/*veinAmt*/4,/*height*/height,/*diameter*/50,/*vDensity*/50,/*hDensity*/60,         world, rand, chunkX, chunkZ, min, max);
        createOre(mod_TFC_Core.terraOre3.blockID, 0,new int[]{mod_TFC_Core.terraOre2.blockID,8},//Gypsum, small clusters 
                /*rarity*/40,/*veinSize*/12,/*veinAmt*/12,/*height*/height,/*diameter*/50,/*vDensity*/40,/*hDensity*/40,         world, rand, chunkX, chunkZ, min, max);
        //============Lapis Lazuli
        createOre(mod_TFC_Core.terraOre3.blockID, 2,new int[]{mod_TFC_Core.terraStoneMM.blockID,5},//Marble, small clusters 
                /*rarity*/44,/*veinSize*/8,/*veinAmt*/8,/*height*/height,/*diameter*/60,/*vDensity*/40,/*hDensity*/40,         world, rand, chunkX, chunkZ, min, max);

        //============Platinum -- (out of order) must follow magnetite and olivine
        createOre(mod_TFC_Core.terraOre.blockID, 2,new int[]{mod_TFC_Core.terraOre.blockID,1,mod_TFC_Core.terraOre3.blockID,8},//magnetite, veins
                /*rarity*/38,/*veinSize*/8,/*veinAmt*/10,/*height*/height,/*diameter*/15,/*vDensity*/60,/*hDensity*/40,         world, rand, chunkX, chunkZ, min, max);

        //============Gravel
        createOre(Block.gravel.blockID, 0,new int[]{mod_TFC_Core.terraDirt.blockID,-1,
                mod_TFC_Core.terraDirt2.blockID,-1,mod_TFC_Core.terraGrass.blockID,-1,mod_TFC_Core.terraGrass2.blockID,-1},//Everywhere, Clusters
                /*rarity*/12,/*veinSize*/40,/*veinAmt*/5,/*height*/height,/*diameter*/40,/*vDensity*/10,/*hDensity*/60,         world, rand, chunkX, chunkZ, min, max);
	}

	public static void GenerateLooseRocks(World currentWorld, Random randomGenerator, int chunk_X, int chunk_Z)
	{
		BiomeGenBase biome = currentWorld.getBiomeGenForCoords(chunk_X*16, chunk_Z*16);


		for (int var2 = 0; var2 < ((BiomeDecoratorTFC)biome.biomeDecorator).looseRocksPerChunk; var2++)
		{
			int var7 = chunk_X + randomGenerator.nextInt(16) + 8;
			int var3 = chunk_Z + randomGenerator.nextInt(16) + 8;

			new WorldGenLooseRocks().generate(currentWorld, randomGenerator, var7, currentWorld.getTopSolidOrLiquidBlock(var7, var3)-1, var3);

		}

	}

	public static void GeneratePits(World world, Random rand, int chunkX, int chunkZ)
	{
		for (int var1 = 0; var1 < 1; ++var1)
		{
			int var2 = chunkX + rand.nextInt(16) + 8;
			int var3 = chunkZ + rand.nextInt(16) + 8;
			new WorldGenClayPit(16, world.getBiomeGenForCoords(var2, var3)).generate(world, rand, var2, world.getTopSolidOrLiquidBlock(var2, var3), var3);
		}

		for (int var1 = 0; var1 < 1; ++var1)
		{
			int var2 = chunkX + rand.nextInt(16) + 8;
			int var3 = chunkZ + rand.nextInt(16) + 8;
			new WorldGenPeatPit(24, world.getBiomeGenForCoords(var2, var3)).generate(world, rand, var2, world.getTopSolidOrLiquidBlock(var2, var3), var3);
		}
	}

	public static void GeneratePlants(World world, Random rand, int chunk_X, int chunk_Z)
	{
		BiomeGenBase biome = world.getBiomeGenForCoords(chunk_X*16, chunk_Z*16);
		WorldGenCustomFlowers plantYellowGen = new WorldGenCustomFlowers(Block.plantYellow.blockID);
		WorldGenCustomFlowers plantRedGen = new WorldGenCustomFlowers(Block.plantRed.blockID);
		WorldGenCustomFlowers mushroomBrownGen = new WorldGenCustomFlowers(Block.mushroomBrown.blockID);
		WorldGenCustomFlowers mushroomRedGen = new WorldGenCustomFlowers(Block.mushroomRed.blockID);

		int var2;
		int var3;
		int var4;
		int var7;

		for (var2 = 0; var2 < ((BiomeDecoratorTFC)biome.biomeDecorator).flowersPerChunk; ++var2)
		{
			var3 = chunk_X + rand.nextInt(16) + 8;
			var4 = rand.nextInt(256);
			var7 = chunk_Z + rand.nextInt(16) + 8;

			plantYellowGen.generate(world, rand, var3, var4, var7);

			if (rand.nextInt(4) == 0)
			{
				var3 = chunk_X + rand.nextInt(16) + 8;
				var4 = rand.nextInt(256);
				var7 = chunk_Z + rand.nextInt(16) + 8;
				plantRedGen.generate(world, rand, var3, var4, var7);
			}
		}

		for (var2 = 0; var2 < ((BiomeDecoratorTFC)biome.biomeDecorator).grassPerChunk; ++var2)
		{
			var3 = chunk_X + rand.nextInt(16) + 8;
			var4 = rand.nextInt(256);
			var7 = chunk_Z + rand.nextInt(16) + 8;
			WorldGenerator var6 = new WorldGenCustomTallGrass(Block.tallGrass.blockID, 1);
			var6.generate(world, rand, var3, var4, var7);
		}

		for (var2 = 0; var2 < ((BiomeDecoratorTFC)biome.biomeDecorator).mushroomsPerChunk; ++var2)
		{
			if (rand.nextInt(4) == 0)
			{
				var3 = chunk_X + rand.nextInt(16) + 8;
				var4 = chunk_Z + rand.nextInt(16) + 8;
				var7 = world.getHeightValue(var3, var4);
				mushroomBrownGen.generate(world, rand, var3, var7, var4);
			}

			if (rand.nextInt(8) == 0)
			{
				var3 = chunk_X + rand.nextInt(16) + 8;
				var4 = chunk_Z + rand.nextInt(16) + 8;
				var7 = rand.nextInt(128);
				mushroomRedGen.generate(world, rand, var3, var7, var4);
			}
		}

		if (rand.nextInt(4) == 0)
		{
			var2 = chunk_X + rand.nextInt(16) + 8;
			var3 = rand.nextInt(256);
			var4 = chunk_Z + rand.nextInt(16) + 8;
			mushroomBrownGen.generate(world, rand, var2, var3, var4);
		}

		if (rand.nextInt(8) == 0)
		{
			var2 = chunk_X + rand.nextInt(16) + 8;
			var3 = rand.nextInt(256);
			var4 = chunk_Z + rand.nextInt(16) + 8;
			mushroomRedGen.generate(world, rand, var2, var3, var4);
		}
	}

	static Boolean isBlockAboveSolid(IBlockAccess blockAccess, int i, int j, int k)
	{
		if(ServerClientProxy.getProxy().getCurrentWorld().isBlockOpaqueCube(i, j+1, k)) {
			return true;
		}

		return false;
	}

	public static ItemStack RandomGem(Random random, int rockType)
	{
		ItemStack is = null;
		if(random.nextInt(250) == 0)
		{
			ArrayList items = new ArrayList<ItemStack>();
			items.add(new ItemStack(mod_TFC_Core.terraGemAgate,1,0));
			items.add(new ItemStack(mod_TFC_Core.terraGemAmethyst,1,0));
			items.add(new ItemStack(mod_TFC_Core.terraGemBeryl,1,0));
			items.add(new ItemStack(mod_TFC_Core.terraGemEmerald,1,0));
			items.add(new ItemStack(mod_TFC_Core.terraGemGarnet,1,0));
			items.add(new ItemStack(mod_TFC_Core.terraGemJade,1,0));
			items.add(new ItemStack(mod_TFC_Core.terraGemJasper,1,0));
			items.add(new ItemStack(mod_TFC_Core.terraGemOpal,1,0));
			items.add(new ItemStack(mod_TFC_Core.terraGemRuby,1,0));
			items.add(new ItemStack(mod_TFC_Core.terraGemSapphire,1,0));
			items.add(new ItemStack(mod_TFC_Core.terraGemTourmaline,1,0));
			items.add(new ItemStack(mod_TFC_Core.terraGemTopaz,1,0));

			is = (ItemStack)items.toArray()[random.nextInt(items.toArray().length)];

		}
		else if(random.nextInt(625) == 0)
		{
			ArrayList items = new ArrayList<ItemStack>();
			items.add(new ItemStack(mod_TFC_Core.terraGemAgate,1,1));
			items.add(new ItemStack(mod_TFC_Core.terraGemAmethyst,1,1));
			items.add(new ItemStack(mod_TFC_Core.terraGemBeryl,1,1));
			items.add(new ItemStack(mod_TFC_Core.terraGemEmerald,1,1));
			items.add(new ItemStack(mod_TFC_Core.terraGemGarnet,1,1));
			items.add(new ItemStack(mod_TFC_Core.terraGemJade,1,1));
			items.add(new ItemStack(mod_TFC_Core.terraGemJasper,1,1));
			items.add(new ItemStack(mod_TFC_Core.terraGemOpal,1,1));
			items.add(new ItemStack(mod_TFC_Core.terraGemRuby,1,1));
			items.add(new ItemStack(mod_TFC_Core.terraGemSapphire,1,1));
			items.add(new ItemStack(mod_TFC_Core.terraGemTourmaline,1,1));
			items.add(new ItemStack(mod_TFC_Core.terraGemTopaz,1,1));

			is = (ItemStack)items.toArray()[random.nextInt(items.toArray().length)];
		}
		else if(random.nextInt(1250) == 0)
		{
			ArrayList items = new ArrayList<ItemStack>();
			items.add(new ItemStack(mod_TFC_Core.terraGemAgate,1,2));
			items.add(new ItemStack(mod_TFC_Core.terraGemAmethyst,1,2));
			items.add(new ItemStack(mod_TFC_Core.terraGemBeryl,1,2));
			items.add(new ItemStack(mod_TFC_Core.terraGemEmerald,1,2));
			items.add(new ItemStack(mod_TFC_Core.terraGemGarnet,1,2));
			items.add(new ItemStack(mod_TFC_Core.terraGemJade,1,2));
			items.add(new ItemStack(mod_TFC_Core.terraGemJasper,1,2));
			items.add(new ItemStack(mod_TFC_Core.terraGemOpal,1,2));
			items.add(new ItemStack(mod_TFC_Core.terraGemRuby,1,2));
			items.add(new ItemStack(mod_TFC_Core.terraGemSapphire,1,2));
			items.add(new ItemStack(mod_TFC_Core.terraGemTourmaline,1,2));
			items.add(new ItemStack(mod_TFC_Core.terraGemTopaz,1,2));

			is = (ItemStack)items.toArray()[random.nextInt(items.toArray().length)];
		}
		else if(random.nextInt(1875) == 0)
		{
			ArrayList items = new ArrayList<ItemStack>();
			items.add(new ItemStack(mod_TFC_Core.terraGemAgate,1,3));
			items.add(new ItemStack(mod_TFC_Core.terraGemAmethyst,1,3));
			items.add(new ItemStack(mod_TFC_Core.terraGemBeryl,1,3));
			items.add(new ItemStack(mod_TFC_Core.terraGemEmerald,1,3));
			items.add(new ItemStack(mod_TFC_Core.terraGemGarnet,1,3));
			items.add(new ItemStack(mod_TFC_Core.terraGemJade,1,3));
			items.add(new ItemStack(mod_TFC_Core.terraGemJasper,1,3));
			items.add(new ItemStack(mod_TFC_Core.terraGemOpal,1,3));
			items.add(new ItemStack(mod_TFC_Core.terraGemRuby,1,3));
			items.add(new ItemStack(mod_TFC_Core.terraGemSapphire,1,3));
			items.add(new ItemStack(mod_TFC_Core.terraGemTourmaline,1,3));
			items.add(new ItemStack(mod_TFC_Core.terraGemTopaz,1,3));

			is = (ItemStack)items.toArray()[random.nextInt(items.toArray().length)];
		}
		else if(random.nextInt(2500) == 0)
		{
			ArrayList items = new ArrayList<ItemStack>();
			items.add(new ItemStack(mod_TFC_Core.terraGemAgate,1,4));
			items.add(new ItemStack(mod_TFC_Core.terraGemAmethyst,1,4));
			items.add(new ItemStack(mod_TFC_Core.terraGemBeryl,1,4));
			items.add(new ItemStack(mod_TFC_Core.terraGemEmerald,1,4));
			items.add(new ItemStack(mod_TFC_Core.terraGemGarnet,1,4));
			items.add(new ItemStack(mod_TFC_Core.terraGemJade,1,4));
			items.add(new ItemStack(mod_TFC_Core.terraGemJasper,1,4));
			items.add(new ItemStack(mod_TFC_Core.terraGemOpal,1,4));
			items.add(new ItemStack(mod_TFC_Core.terraGemRuby,1,4));
			items.add(new ItemStack(mod_TFC_Core.terraGemSapphire,1,4));
			items.add(new ItemStack(mod_TFC_Core.terraGemTourmaline,1,4));
			items.add(new ItemStack(mod_TFC_Core.terraGemTopaz,1,4));

			is = (ItemStack)items.toArray()[random.nextInt(items.toArray().length)];

		}
		return is;
	}

	public static void RegisterRecipes()
	{
		RegisterTools();

		/** Axe Recipes */
		for(int i = 0; i < 16; i++)
		{
			for(int j = 0; j < Axes.length; j++)
			{
				ModLoader.addShapelessRecipe(new ItemStack(Block.planks, 1, i), new Object[] {new ItemStack(mod_TFC_Core.Logs, 1, i), new ItemStack(Axes[j], 1, -1)});
				ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraWoodSupportItemV, 8, i), new Object[] { "A2"," 2", Character.valueOf('2'), new ItemStack(mod_TFC_Core.Logs,1,i), Character.valueOf('A'), new ItemStack(Axes[j], 1, -1)});
				ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraWoodSupportItemH, 8, i), new Object[] { "A ","22", Character.valueOf('2'), new ItemStack(mod_TFC_Core.Logs,1,i), Character.valueOf('A'), new ItemStack(Axes[j], 1, -1)});
			}
			for(int j = 0; j < Saws.length; j++)
			{
				ModLoader.addShapelessRecipe(new ItemStack(Block.planks, 3, i), new Object[] {new ItemStack(mod_TFC_Core.Logs, 1, i), new ItemStack(Saws[j], 1, -1)});
			}
			ModLoader.addShapelessRecipe(new ItemStack(Block.planks, 1, i), new Object[] {new ItemStack(mod_TFC_Core.Logs, 1, i), new ItemStack(mod_TFC_Core.FlintPaxel, 1, -1)});
		}

		//Red Stone		
		ModLoader.addRecipe(new ItemStack(Item.redstone, 8), new Object[] { 
			"1", Character.valueOf('1'),new ItemStack(mod_TFC_Core.OreChunk, 1, 28)});
		//Lapis Lazuli	
		ModLoader.addShapelessRecipe(new ItemStack(Item.dyePowder, 4,4), new Object[] {new ItemStack(mod_TFC_Core.OreChunk, 1, 35)});


		for(int i = 0; i < 13; i++)
		{			
			for(int j = 0; j < 3; j++)
			{
				ModLoader.addShapelessRecipe(new ItemStack(mod_TFC_Core.terraStoneIgInBrick,1,j), 
						new Object[] {new ItemStack(mod_TFC_Core.terraStoneIgInCobble,1,j),new ItemStack(Chisels[i],1,-1)});
			}

			for(int j = 0; j < 10; j++)
			{
				ModLoader.addShapelessRecipe(new ItemStack(mod_TFC_Core.terraStoneSedBrick,1,j), 
						new Object[] {new ItemStack(mod_TFC_Core.terraStoneSedCobble,1,j),new ItemStack(Chisels[i],1,-1)});
			}

			for(int j = 0; j < 4; j++)
			{
				ModLoader.addShapelessRecipe(new ItemStack(mod_TFC_Core.terraStoneIgExBrick,1,j), 
						new Object[] {new ItemStack(mod_TFC_Core.terraStoneIgExCobble,1,j),new ItemStack(Chisels[i],1,-1)});
			}

			for(int j = 0; j < 6; j++)
			{
				ModLoader.addShapelessRecipe(new ItemStack(mod_TFC_Core.terraStoneMMBrick,1,j), 
						new Object[] {new ItemStack(mod_TFC_Core.terraStoneMMCobble,1,j),new ItemStack(Chisels[i],1,-1)});
			}
		}

		if(TFCSettings.enableVanillaDiamondRecipe == true)
		{
			ModLoader.addRecipe(new ItemStack(Item.diamond, 1), new Object[] {"1", Character.valueOf('1'),new ItemStack(mod_TFC_Core.terraGemDiamond,1,2)});
			ModLoader.addRecipe(new ItemStack(Item.diamond, 2), new Object[] {"1", Character.valueOf('1'),new ItemStack(mod_TFC_Core.terraGemDiamond,1,3)});
			ModLoader.addRecipe(new ItemStack(Item.diamond, 3), new Object[] {"1", Character.valueOf('1'),new ItemStack(mod_TFC_Core.terraGemDiamond,1,4)});
		}
		if(TFCSettings.enableVanillaIronRecipe == true)
		{
			ModLoader.addRecipe(new ItemStack(Item.ingotIron, 1), new Object[] {"1", Character.valueOf('1'),new ItemStack(mod_TFC_Core.WroughtIronIngot,1)});

		}
		if(TFCSettings.enableVanillaGoldRecipe == true)
		{
			ModLoader.addRecipe(new ItemStack(Item.ingotGold, 1), new Object[] {"1", Character.valueOf('1'),new ItemStack(mod_TFC_Core.GoldIngot,1)});
		}
		if(TFCSettings.enableVanillaRecipes == true)
		{
			//Terrastone to Cobblestone
			ModLoader.addRecipe(new ItemStack(Block.cobblestone, 1), new Object[] {"1", Character.valueOf('1'),mod_TFC_Core.terraStoneSedCobble});
			ModLoader.addRecipe(new ItemStack(Block.cobblestone, 1), new Object[] {"1", Character.valueOf('1'),mod_TFC_Core.terraStoneIgInCobble});
			ModLoader.addRecipe(new ItemStack(Block.cobblestone, 1), new Object[] {"1", Character.valueOf('1'),mod_TFC_Core.terraStoneIgExCobble});
			ModLoader.addRecipe(new ItemStack(Block.cobblestone, 1), new Object[] {"1", Character.valueOf('1'),mod_TFC_Core.terraStoneMMCobble});
		}

		if(TFCSettings.enableVanillaFurnaceRecipes  == true)
		{
			FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraStoneIgIn.blockID, new ItemStack(Block.stone));
			FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraStoneIgEx.blockID, new ItemStack(Block.stone));
			FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraStoneSed.blockID,  new ItemStack(Block.stone));
			FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraStoneMM.blockID,   new ItemStack(Block.stone));

			FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraStoneIgInCobble.blockID, new ItemStack(mod_TFC_Core.terraStoneIgIn));
			FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraStoneIgExCobble.blockID, new ItemStack(mod_TFC_Core.terraStoneIgEx));
			FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraStoneSedCobble.blockID,  new ItemStack(mod_TFC_Core.terraStoneSed));
			FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraStoneMMCobble.blockID,   new ItemStack(mod_TFC_Core.terraStoneMM));

			FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraOre.blockID, 23,new ItemStack(mod_TFC_Core.CopperIngot));
			FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraOre.blockID, 32,new ItemStack(mod_TFC_Core.CopperIngot));
			FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraOre.blockID, 36,new ItemStack(mod_TFC_Core.CopperIngot));
			FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraOre.blockID, 35,new ItemStack(mod_TFC_Core.ZincIngot));
			FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraOre.blockID, 34,new ItemStack(mod_TFC_Core.WroughtIronIngot));
			FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraOre.blockID, 33,new ItemStack(mod_TFC_Core.WroughtIronIngot));
			FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraOre.blockID, 26,new ItemStack(mod_TFC_Core.WroughtIronIngot));
			FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraOre.blockID, 25,new ItemStack(mod_TFC_Core.PlatinumIngot));
			FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraOre.blockID, 24,new ItemStack(mod_TFC_Core.GoldIngot));
			FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraOre.blockID, 31,new ItemStack(mod_TFC_Core.NickelIngot));
			FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraOre.blockID, 29,new ItemStack(mod_TFC_Core.SilverIngot));
			FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraOre.blockID, 27,new ItemStack(mod_TFC_Core.SilverIngot));
			FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraOre.blockID, 30,new ItemStack(mod_TFC_Core.BismuthIngot));
			FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraOre.blockID, 28,new ItemStack(mod_TFC_Core.TinIngot));
		}
	}

	@SuppressWarnings("unused")
	public static void RegisterTools()
	{
		Item[] Ingots = {mod_TFC_Core.BismuthIngot, mod_TFC_Core.BismuthBronzeIngot,mod_TFC_Core.BlackBronzeIngot,
				mod_TFC_Core.BlackSteelIngot,mod_TFC_Core.BlueSteelIngot,mod_TFC_Core.BrassIngot,mod_TFC_Core.BronzeIngot,
				mod_TFC_Core.BronzeIngot,mod_TFC_Core.CopperIngot,mod_TFC_Core.GoldIngot,mod_TFC_Core.WroughtIronIngot,mod_TFC_Core.LeadIngot
				,mod_TFC_Core.NickelIngot,mod_TFC_Core.PigIronIngot,mod_TFC_Core.PlatinumIngot,mod_TFC_Core.RedSteelIngot,
				mod_TFC_Core.RoseGoldIngot,mod_TFC_Core.SilverIngot,mod_TFC_Core.SteelIngot,mod_TFC_Core.SterlingSilverIngot
				,mod_TFC_Core.TinIngot,mod_TFC_Core.ZincIngot};

		if(TFCSettings.enableVanillaRecipes == true)
		{
			//Conversion to vanilla tools for recipes in other mods
			ModLoader.addRecipe(new ItemStack(Item.pickaxeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), mod_TFC_Core.terraIgInPick});
			ModLoader.addRecipe(new ItemStack(Item.pickaxeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), mod_TFC_Core.terraIgExPick});
			ModLoader.addRecipe(new ItemStack(Item.pickaxeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), mod_TFC_Core.terraSedPick});
			ModLoader.addRecipe(new ItemStack(Item.pickaxeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), mod_TFC_Core.terraMMPick});
			ModLoader.addRecipe(new ItemStack(Item.shovelStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), mod_TFC_Core.terraIgInShovel});
			ModLoader.addRecipe(new ItemStack(Item.shovelStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), mod_TFC_Core.terraIgExShovel});
			ModLoader.addRecipe(new ItemStack(Item.shovelStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), mod_TFC_Core.terraSedShovel});
			ModLoader.addRecipe(new ItemStack(Item.shovelStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), mod_TFC_Core.terraMMShovel});
			ModLoader.addRecipe(new ItemStack(Item.hoeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), mod_TFC_Core.terraIgInHoe});
			ModLoader.addRecipe(new ItemStack(Item.hoeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), mod_TFC_Core.terraIgExHoe});
			ModLoader.addRecipe(new ItemStack(Item.hoeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), mod_TFC_Core.terraSedHoe});
			ModLoader.addRecipe(new ItemStack(Item.hoeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), mod_TFC_Core.terraMMHoe});
			ModLoader.addRecipe(new ItemStack(Item.axeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), mod_TFC_Core.terraIgInAxe});
			ModLoader.addRecipe(new ItemStack(Item.axeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), mod_TFC_Core.terraIgExAxe});
			ModLoader.addRecipe(new ItemStack(Item.axeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), mod_TFC_Core.terraSedAxe});
			ModLoader.addRecipe(new ItemStack(Item.axeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), mod_TFC_Core.terraMMAxe});

			for (int i = 0; i < 22 && false; i++)
			{
				Object[] pickaxeRecipe = new Object[] { "111"," 2 "," 2 ", Character.valueOf('1'), Ingots[i],Character.valueOf('2'), Item.stick};
				Object[] shovelRecipe = new Object[] { "1 ","2 ","2 ", Character.valueOf('1'), Ingots[i],Character.valueOf('2'), Item.stick};
				Object[] hoeRecipe = new Object[] { "11"," 2"," 2", Character.valueOf('1'), Ingots[i],Character.valueOf('2'), Item.stick};
				Object[] axeRecipe = new Object[] { "11 ","12 "," 2 ", Character.valueOf('1'), Ingots[i],Character.valueOf('2'), Item.stick};

				if (Ingots[i].getItemName().equals("ingotBismuth"))
				{
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBismuthPick, 1, 0), pickaxeRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBismuthShovel, 1, 0), shovelRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBismuthHoe, 1, 0), hoeRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBismuthAxe, 1, 0), axeRecipe);
				}
				if (Ingots[i].getItemName().equals("ingotBismuthBronze"))
				{
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBismuthBronzePick, 1, 0), pickaxeRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBismuthBronzeShovel, 1, 0), shovelRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBismuthBronzeHoe, 1, 0), hoeRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBismuthBronzeAxe, 1, 0), axeRecipe);
				}
				if (Ingots[i].getItemName().equals("ingotBlackBronze"))
				{
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBlackBronzePick, 1, 0), pickaxeRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBlackBronzeShovel, 1, 0), shovelRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBlackBronzeHoe, 1, 0), hoeRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBlackBronzeAxe, 1, 0), axeRecipe);
				}
				if (Ingots[i].getItemName().equals("ingotBlackSteel"))
				{
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBlackSteelPick, 1, 0), pickaxeRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBlackSteelShovel, 1, 0), shovelRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBlackSteelHoe, 1, 0), hoeRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBlackSteelAxe, 1, 0), axeRecipe);
				}
				if (Ingots[i].getItemName().equals("ingotBlueSteel"))
				{
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBlueSteelPick, 1, 0), pickaxeRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBlueSteelShovel, 1, 0), shovelRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBlueSteelHoe, 1, 0), hoeRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBlueSteelAxe, 1, 0), axeRecipe);
				}
				if (Ingots[i].getItemName().equals("ingotBronze"))
				{
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBronzePick, 1, 0), pickaxeRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBronzeShovel, 1, 0), shovelRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBronzeHoe, 1, 0), hoeRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBronzeAxe, 1, 0), axeRecipe);
				}
				if (Ingots[i].getItemName().equals("ingotCopper"))
				{
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraCopperPick, 1, 0), pickaxeRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraCopperShovel, 1, 0), shovelRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraCopperHoe, 1, 0), hoeRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraCopperAxe, 1, 0), axeRecipe);
				}
				if (Ingots[i].getItemName().equals("ingotGold"))
				{

				}
				if (Ingots[i].getItemName().equals("terraWroughtIronIngot"))
				{
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraWroughtIronPick, 1, 0), pickaxeRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraWroughtIronShovel, 1, 0), shovelRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraWroughtIronHoe, 1, 0), hoeRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraWroughtIronAxe, 1, 0), axeRecipe);
				}
				if (Ingots[i].getItemName().equals("ingotNickel"))
				{

				}
				if (Ingots[i].getItemName().equals("ingotPigIron"))
				{

				}
				if (Ingots[i].getItemName().equals("ingotPlatinum"))
				{

				}
				if (Ingots[i].getItemName().equals("ingotRedSteel"))
				{
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraRedSteelPick, 1, 0), pickaxeRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraRedSteelShovel, 1, 0), shovelRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraRedSteelHoe, 1, 0), hoeRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraRedSteelAxe, 1, 0), axeRecipe);
				}
				if (Ingots[i].getItemName().equals("ingotRoseGold"))
				{
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraRoseGoldPick, 1, 0), pickaxeRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraRoseGoldShovel, 1, 0), shovelRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraRoseGoldHoe, 1, 0), hoeRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraRoseGoldAxe, 1, 0), axeRecipe);
				}
				if (Ingots[i].getItemName().equals("ingotSilver"))
				{

				}
				if (Ingots[i].getItemName().equals("ingotSteel"))
				{
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraSteelPick, 1, 0), pickaxeRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraSteelShovel, 1, 0), shovelRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraSteelHoe, 1, 0), hoeRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraSteelAxe, 1, 0), axeRecipe);
				}
				if (Ingots[i].getItemName().equals("ingotSterlingSilver"))
				{

				}
				if (Ingots[i].getItemName().equals("ingotTin"))
				{
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraTinPick, 1, 0), pickaxeRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraTinShovel, 1, 0), shovelRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraTinHoe, 1, 0), hoeRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraTinAxe, 1, 0), axeRecipe);
				}
				if (Ingots[i].getItemName().equals("ingotZinc"))
				{
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraZincPick, 1, 0), pickaxeRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraZincShovel, 1, 0), shovelRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraZincHoe, 1, 0), hoeRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraZincAxe, 1, 0), axeRecipe);
				}
			}
		}
		//jimmnator's javelin disabled till beta 2
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.Javelin, 1, 0), new Object[] { 
		"1","2","2", Character.valueOf('1'), Item.flint,Character.valueOf('2'), Item.stick});

		//stone picks
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraIgInPick, 1, 0), new Object[] { 
			"111"," 2 "," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneIgInCobble,Character.valueOf('2'), Item.stick});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraIgExPick, 1, 0), new Object[] { 
			"111"," 2 "," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneIgExCobble,Character.valueOf('2'), Item.stick});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraSedPick, 1, 0), new Object[] { 
			"111"," 2 "," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneSedCobble,Character.valueOf('2'), Item.stick});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraMMPick, 1, 0), new Object[] { 
			"111"," 2 "," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneMMCobble,Character.valueOf('2'), Item.stick});
		//stone shovels
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraIgInShovel, 1, 0), new Object[] { 
			"1 ","2 ","2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneIgInCobble,Character.valueOf('2'), Item.stick});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraIgExShovel, 1, 0), new Object[] { 
			"1 ","2 ","2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneIgExCobble,Character.valueOf('2'), Item.stick});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraSedShovel, 1, 0), new Object[] { 
			"1 ","2 ","2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneSedCobble,Character.valueOf('2'), Item.stick});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraMMShovel, 1, 0), new Object[] { 
			"1 ","2 ","2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneMMCobble,Character.valueOf('2'), Item.stick});
		//stone hoes
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraIgInHoe, 1, 0), new Object[] { 
			"11"," 2"," 2", Character.valueOf('1'), mod_TFC_Core.terraStoneIgInCobble,Character.valueOf('2'), Item.stick});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraIgExHoe, 1, 0), new Object[] { 
			"11"," 2"," 2", Character.valueOf('1'), mod_TFC_Core.terraStoneIgExCobble,Character.valueOf('2'), Item.stick});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraSedHoe, 1, 0), new Object[] { 
			"11"," 2"," 2", Character.valueOf('1'), mod_TFC_Core.terraStoneSedCobble,Character.valueOf('2'), Item.stick});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraMMHoe, 1, 0), new Object[] { 
			"11"," 2"," 2", Character.valueOf('1'), mod_TFC_Core.terraStoneMMCobble,Character.valueOf('2'), Item.stick});
		//stone axes
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraIgInAxe, 1, 0), new Object[] { 
			"11 ","12 "," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneIgInCobble,Character.valueOf('2'), Item.stick});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraIgExAxe, 1, 0), new Object[] { 
			"11 ","12 "," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneIgExCobble,Character.valueOf('2'), Item.stick});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraSedAxe, 1, 0), new Object[] { 
			"11 ","12 "," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneSedCobble,Character.valueOf('2'), Item.stick});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraMMAxe, 1, 0), new Object[] { 
			"11 ","12 "," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneMMCobble,Character.valueOf('2'), Item.stick});

		//the bone versions
		//stone picks
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.boneIgInPick, 1, 0), new Object[] { 
			"111"," 2 "," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneIgInCobble,Character.valueOf('2'), Item.bone});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.boneIgExPick, 1, 0), new Object[] { 
			"111"," 2 "," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneIgExCobble,Character.valueOf('2'), Item.bone});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.boneSedPick, 1, 0), new Object[] { 
			"111"," 2 "," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneSedCobble,Character.valueOf('2'), Item.bone});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.boneMMPick, 1, 0), new Object[] { 
			"111"," 2 "," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneMMCobble,Character.valueOf('2'), Item.bone});
		//stone shovels
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.boneIgInShovel, 1, 0), new Object[] { 
			"1 ","2 ","2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneIgInCobble,Character.valueOf('2'), Item.bone});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.boneIgExShovel, 1, 0), new Object[] { 
			"1 ","2 ","2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneIgExCobble,Character.valueOf('2'), Item.bone});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.boneSedShovel, 1, 0), new Object[] { 
			"1 ","2 ","2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneSedCobble,Character.valueOf('2'), Item.bone});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.boneMMShovel, 1, 0), new Object[] { 
			"1 ","2 ","2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneMMCobble,Character.valueOf('2'), Item.bone});
		//stone hoes
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.boneIgInHoe, 1, 0), new Object[] { 
			"11"," 2"," 2", Character.valueOf('1'), mod_TFC_Core.terraStoneIgInCobble,Character.valueOf('2'), Item.bone});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.boneIgExHoe, 1, 0), new Object[] { 
			"11"," 2"," 2", Character.valueOf('1'), mod_TFC_Core.terraStoneIgExCobble,Character.valueOf('2'), Item.bone});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.boneSedHoe, 1, 0), new Object[] { 
			"11"," 2"," 2", Character.valueOf('1'), mod_TFC_Core.terraStoneSedCobble,Character.valueOf('2'), Item.bone});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.boneMMHoe, 1, 0), new Object[] { 
			"11"," 2"," 2", Character.valueOf('1'), mod_TFC_Core.terraStoneMMCobble,Character.valueOf('2'), Item.bone});
		//stone axes
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.boneIgInAxe, 1, 0), new Object[] { 
			"11 ","12 "," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneIgInCobble,Character.valueOf('2'), Item.bone});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.boneIgExAxe, 1, 0), new Object[] { 
			"11 ","12 "," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneIgExCobble,Character.valueOf('2'), Item.bone});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.boneSedAxe, 1, 0), new Object[] { 
			"11 ","12 "," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneSedCobble,Character.valueOf('2'), Item.bone});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.boneMMAxe, 1, 0), new Object[] { 
			"11 ","12 "," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneMMCobble,Character.valueOf('2'), Item.bone});

		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.FlintPaxel, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), Item.flint,Character.valueOf('2'), Item.stick});



	}

	/**This version performs all the intial block placement in the initial loop, eliminating
	 * a major source of chunkloader lag.*/
	public static void replaceBlocksForBiome(int par1, int par2, byte[] blockArray, byte[] metaArray, BiomeGenBase[] par4ArrayOfBiomeGenBase, 
			double[] stoneNoise, NoiseGeneratorOctaves noiseGen4, Random rand)
	{
		byte var5 = 63;
		double var6 = 0.03125D;
		stoneNoise = noiseGen4.generateNoiseOctaves(stoneNoise, par1 * 16, par2 * 16, 0, 16, 16, 1, var6 * 2.0D, var6 * 2.0D, var6 * 2.0D);

		for (int xCoord = 0; xCoord < 16; ++xCoord)
		{
			for (int zCoord = 0; zCoord < 16; ++zCoord)
			{
				BiomeGenBase biomegenbase = par4ArrayOfBiomeGenBase[zCoord + xCoord * 16];
				float var11 = biomegenbase.getFloatTemperature();
				int var12 = (int)(stoneNoise[xCoord + zCoord * 16] / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
				int var13 = -1;
				int var14 = biomegenbase.GrassID;
				int var15 = biomegenbase.DirtID;

				for (int height = 127; height >= 0; --height)
				{
					int var17 = (zCoord * 16 + xCoord) * 128 + height;

					int var10 = height >> 4;
		metaArray[var17] = 0;

		if (height <= 1)
		{
			blockArray[var17] = (byte) Block.bedrock.blockID;
		}
		else
		{
			int var18 = blockArray[var17];
			if (var18 == 0)
			{
				var13 = -1;
			}
			else if (var18 == Block.stone.blockID)
			{
				if(height <= biomegenbase.Layer3)
				{
					blockArray[var17] = (byte) biomegenbase.Layer3Type; 
					metaArray[var17] = (byte)  biomegenbase.Layer3Meta;
					if(height == biomegenbase.Layer3)
					{
						if(rand.nextBoolean())
						{
							blockArray[var17+1] = (byte) biomegenbase.Layer3Type; 
							metaArray[var17+1] = (byte)  biomegenbase.Layer3Meta;
							if(rand.nextBoolean())
							{
								blockArray[var17+2] = (byte) biomegenbase.Layer3Type; 
								metaArray[var17+2] = (byte)  biomegenbase.Layer3Meta;
								if(rand.nextBoolean())
								{
									blockArray[var17+3] = (byte) biomegenbase.Layer3Type; 
									metaArray[var17+3] = (byte)  biomegenbase.Layer3Meta;
								}
							}
						}
					}
				}
				else if(height <= biomegenbase.Layer2 && height > biomegenbase.Layer3)
				{
					blockArray[var17] = (byte) biomegenbase.Layer2Type; 
					metaArray[var17] = (byte)  biomegenbase.Layer2Meta;
					if(height == biomegenbase.Layer2)
					{
						if(rand.nextBoolean())
						{
							blockArray[var17+1] = (byte) biomegenbase.Layer2Type; 
							metaArray[var17+1] = (byte)  biomegenbase.Layer2Meta;
							if(rand.nextBoolean())
							{
								blockArray[var17+2] = (byte) biomegenbase.Layer2Type; 
								metaArray[var17+2] = (byte)  biomegenbase.Layer2Meta;
								if(rand.nextBoolean())
								{
									blockArray[var17+3] = (byte) biomegenbase.Layer2Type; 
									metaArray[var17+3] = (byte)  biomegenbase.Layer2Meta;
								}
							}
						}
					}
				}
				else if(height <= biomegenbase.Layer1 && height > biomegenbase.Layer2)
				{
					blockArray[var17] = (byte) biomegenbase.Layer1Type; 
					metaArray[var17] = (byte)  biomegenbase.Layer1Meta;    
					if(height == biomegenbase.Layer3)
					{
						if(rand.nextBoolean())
						{
							blockArray[var17+1] = (byte) biomegenbase.Layer1Type; 
							metaArray[var17+1] = (byte)  biomegenbase.Layer1Meta;
							if(rand.nextBoolean())
							{
								blockArray[var17+2] = (byte) biomegenbase.Layer1Type; 
								metaArray[var17+2] = (byte)  biomegenbase.Layer1Meta;
								if(rand.nextBoolean())
								{
									blockArray[var17+3] = (byte) biomegenbase.Layer1Type; 
									metaArray[var17+3] = (byte)  biomegenbase.Layer1Meta;
								}
							}
						}
					}
				}
				else
				{
					blockArray[var17] = (byte) biomegenbase.SurfaceType; 
					metaArray[var17] = (byte) biomegenbase.SurfaceMeta;
				}
				if (var13 == -1)
				{
					if (var12 <= 0)
					{
						var14 = 0;

					}
					else if (height >= var5 - 4 && height <= var5 + 1)
					{
						var14 = biomegenbase.GrassID;
						var15 =  biomegenbase.DirtID;
					}

					if (height < var5 && var14 == 0)
					{
						if (var11 < 0.15F)
						{
							var14 = Block.ice.blockID;
						}
						else
						{
							var14 = Block.waterStill.blockID;
						}
					}

					var13 = var12;

					if (height >= var5 - 1)
					{
						blockArray[var17] = (byte) var14;
					}
					else
					{
						blockArray[var17] = (byte) var15;
						metaArray[var17] = (byte) biomegenbase.SurfaceMeta;
					}
				}
				else if (var13 > 0)
				{
					--var13;
					blockArray[var17] = (byte) var15;
					metaArray[var17] = (byte) biomegenbase.SurfaceMeta;

					if (var13 == 0 && var15 == Block.sand.blockID)
					{
						var13 = rand.nextInt(4);
						var15 = Block.sandStone.blockID;
					}
				}
			}
		}
				}
			}
		}
	}

	public static void SurroundWithLeaves(World world, int i, int j, int k, int meta, Random R)
	{
		for (int y = 2; y >= -2; y--)
		{
			for (int x = 2; x >= -2; x--)
			{
				for (int z = 2; z >= -2; z--)
				{
					if(world.getBlockId(i+x, j+y, k+z) == 0) {
						world.setBlockAndMetadata(i+x, j+y, k+z, Block.leaves.blockID, meta);
					}
				}
			}
		}
	}
	
	public static boolean isClient()
	{
	    boolean b = false;
        try 
        {
            Class.forName("net.minecraft.client.MinecraftApplet", false, MinecraftForge.class.getClassLoader());
            b = true;
        } 
        catch (ClassNotFoundException e) 
        {
            b = false;
        }  
        return b;
	}
}
