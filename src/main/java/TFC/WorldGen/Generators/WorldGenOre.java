package TFC.WorldGen.Generators;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import TFC.TFCBlocks;
import TFC.WorldGen.DataLayer;
import TFC.WorldGen.TFCWorldChunkManager;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenOre implements IWorldGenerator
{
	int Min;
	int Max;
	int ChunkX;
	int ChunkZ;
	World worldObj;
	Random random;

	public static HashMap<String, OreSpawnData> OreList = new HashMap<String, OreSpawnData>();

	public WorldGenOre()
	{
	}

	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		chunkX *= 16;
		chunkZ *= 16;
		ChunkX = chunkX;
		ChunkZ = chunkZ;
		worldObj = world;
		random = rand;

		Iterator iter = OreList.values().iterator();
		while(iter.hasNext())
		{
			OreSpawnData osd = (OreSpawnData) iter.next();
			if(osd.type == 0)
			{
				if(osd.size == 0)
					oreSmall(osd.block, osd.meta, osd.base, osd.rarity, osd.min, osd.max, osd.vDensity, osd.hDensity);
				else if(osd.size == 1)
					oreMedium(osd.block, osd.meta, osd.base, osd.rarity, osd.min, osd.max, osd.vDensity, osd.hDensity);
				else if(osd.size == 2)
					oreLarge(osd.block, osd.meta, osd.base, osd.rarity, osd.min, osd.max, osd.vDensity, osd.hDensity);
			}
			else if(osd.type == 1)
			{
				if(osd.size == 0)
					oreSmallVein(osd.block, osd.meta, osd.base, osd.rarity, osd.min, osd.max, osd.vDensity, osd.hDensity);
				else if(osd.size == 1)
					oreMediumVein(osd.block, osd.meta, osd.base, osd.rarity, osd.min, osd.max, osd.vDensity, osd.hDensity);
				else if(osd.size == 2)
					oreLargeVein(osd.block, osd.meta, osd.base, osd.rarity, osd.min, osd.max, osd.vDensity, osd.hDensity);
			}
		}
	}

	public void generate2(Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		chunkX *= 16;
		chunkZ *= 16;
		ChunkX = chunkX;
		ChunkZ = chunkZ;
		worldObj = world;
		random = rand;
		int height = Min-Max;
		height = 20;
		HashMap<Block, Integer> baseRocks = new HashMap<Block, Integer>();
		
		//============Copper
		baseRocks.put(TFCBlocks.StoneIgEx, -1);
		baseRocks.put(Blocks.sandstone, -1);
		createOreVein(TFCBlocks.Ore, 0, baseRocks,//IgEx and Sandstone, veins
				/*rarity*/100,/*veinSize*/80,/*veinAmt*/55,/*height*/height,/*diameter*/100,/*vDensity*/50,/*hDensity*/40, world, rand, chunkX, chunkZ, Min, Max, "Native Copper");

		//============Gold
		baseRocks.clear();
		baseRocks.put(TFCBlocks.StoneIgEx, -1);
		baseRocks.put(TFCBlocks.StoneIgIn, -1);
		createOreVein(TFCBlocks.Ore, 1, baseRocks,//Ig veins
				/*rarity*/130,/*veinSize*/35,/*veinAmt*/45,/*height*/height,/*diameter*/100,/*vDensity*/50,/*hDensity*/20, world, rand, chunkX, chunkZ, Min, Max, "Native Gold");

		//============Hematite
		baseRocks.clear();
		baseRocks.put(TFCBlocks.StoneIgEx, -1);
		createOreVein(TFCBlocks.Ore, 3, baseRocks,//IgEx veins
				/*rarity*/100,/*veinSize*/80,/*veinAmt*/42,/*height*/height,/*diameter*/100,/*vDensity*/40,/*hDensity*/30, world, rand, chunkX, chunkZ, Min, Max, "Hematite");

		//============Silver
		baseRocks.clear();
		baseRocks.put(TFCBlocks.StoneIgIn, 0);
		baseRocks.put(TFCBlocks.StoneMM, 4);
		createOreVein(TFCBlocks.Ore, 4, baseRocks,//granite and gneiss, veins
				/*rarity*/100,/*veinSize*/80,/*veinAmt*/45,/*height*/height,/*diameter*/100,/*vDensity*/50,/*hDensity*/30, world, rand, chunkX, chunkZ, Min, Max, "Native Silver");

		//============Cassiterite
		baseRocks.clear();
		baseRocks.put(TFCBlocks.StoneIgIn, 0);
		createOreVein(TFCBlocks.Ore, 5, baseRocks,//Granite Veins
				/*rarity*/100,/*veinSize*/85,/*veinAmt*/55,/*height*/height,/*diameter*/100,/*vDensity*/50,/*hDensity*/50, world, rand, chunkX, chunkZ, Min, Max, "Cassiterite");

		//============Cassiterite2
		baseRocks.clear();
		baseRocks.put(TFCBlocks.StoneIgEx, -1);
		createOreVein(TFCBlocks.Ore, 5, baseRocks,//IgEx Veins
				/*rarity*/140,/*veinSize*/80,/*veinAmt*/45,/*height*/height,/*diameter*/100,/*vDensity*/50,/*hDensity*/60, world, rand, chunkX, chunkZ, Min, Max, "Cassiterite");

		//============Galena
		baseRocks.clear();
		baseRocks.put(TFCBlocks.StoneIgEx, -1);
		baseRocks.put(TFCBlocks.StoneMM, -1);
		baseRocks.put(TFCBlocks.StoneIgIn, 0);
		baseRocks.put(TFCBlocks.StoneSed, 3);
		createOreVein(TFCBlocks.Ore, 6, baseRocks,//igex, mm, granite, limestone as veins
				/*rarity*/120,/*veinSize*/80,/*veinAmt*/55,/*height*/height,/*diameter*/100,/*vDensity*/50,/*hDensity*/60, world, rand, chunkX, chunkZ, Min, Max, "Galena");

		//============Bismuthinite
		baseRocks.clear();
		baseRocks.put(TFCBlocks.StoneIgIn, -1);
		baseRocks.put(TFCBlocks.StoneSed, -1);
		createOreVein(TFCBlocks.Ore, 7, baseRocks,//Granite Veins
				/*rarity*/120,/*veinSize*/80,/*veinAmt*/45,/*height*/height,/*diameter*/100,/*vDensity*/50,/*hDensity*/60, world, rand, chunkX, chunkZ, Min, Max, "Bismuthinite");

		//============Garnierite
		baseRocks.clear();
		baseRocks.put(TFCBlocks.StoneIgIn, 2);
		createOreVein(TFCBlocks.Ore, 8, baseRocks,//Gabbro Veins
				/*rarity*/160,/*veinSize*/40,/*veinAmt*/35,/*height*/height,/*diameter*/100,/*vDensity*/50,/*hDensity*/10, world, rand, chunkX, chunkZ, Min, Max, "Garnierite");

		//============Malachite
		baseRocks.clear();
		baseRocks.put(TFCBlocks.StoneSed, 3);
		baseRocks.put(TFCBlocks.StoneMM, 5);
		createOreVein(TFCBlocks.Ore, 9, baseRocks,//limestone and marble veins
				/*rarity*/140,/*veinSize*/80,/*veinAmt*/45,/*height*/height,/*diameter*/100,/*vDensity*/50,/*hDensity*/20, world, rand, chunkX, chunkZ, Min, Max, "Malachite");

		//============Magnetite
		baseRocks.clear();
		baseRocks.put(TFCBlocks.StoneSed, -1);
		createOreVein(TFCBlocks.Ore, 10, baseRocks,//Sedimentary, Large Cluster
				/*rarity*/180,/*veinSize*/80,/*veinAmt*/36,/*height*/height,/*diameter*/100,/*vDensity*/50,/*hDensity*/40, world, rand, chunkX, chunkZ, Min, Max, "Magnetite");

		//============Limonite
		baseRocks.clear();
		baseRocks.put(TFCBlocks.StoneSed, -1);
		createOreVein(TFCBlocks.Ore, 11, baseRocks,//Sedimentary, Large Cluster
				/*rarity*/180,/*veinSize*/85,/*veinAmt*/40,/*height*/height,/*diameter*/100,/*vDensity*/50,/*hDensity*/40, world, rand, chunkX, chunkZ, Min, Max, "Limonite");

		//============Sphalerite
		baseRocks.clear();
		baseRocks.put(TFCBlocks.StoneMM, -1);
		createOreVein(TFCBlocks.Ore, 12, baseRocks,//mm, veins
				/*rarity*/140,/*veinSize*/80,/*veinAmt*/38,/*height*/height,/*diameter*/100,/*vDensity*/60,/*hDensity*/40, world, rand, chunkX, chunkZ, Min, Max, "Sphalerite");

		//============Tetrahedrite
		baseRocks.clear();
		baseRocks.put(TFCBlocks.StoneIgEx, -1);
		baseRocks.put(TFCBlocks.StoneMM, -1);
		baseRocks.put(TFCBlocks.StoneIgIn, -1);
		baseRocks.put(TFCBlocks.StoneSed, -1);
		createOreVein(TFCBlocks.Ore, 13, baseRocks,//everything, veins
				/*rarity*/120,/*veinSize*/85,/*veinAmt*/45,/*height*/height,/*diameter*/100,/*vDensity*/50,/*hDensity*/30, world, rand, chunkX, chunkZ, Min, Max, "Tetrahedrite");

		//============Bituminous Coal
		baseRocks.clear();
		baseRocks.put(TFCBlocks.StoneSed, -1);
		createOre(TFCBlocks.Ore, 14, baseRocks,//sedimentary, veins
				/*rarity*/20,/*veinSize*/40,/*veinAmt*/40,/*height*/5,/*diameter*/200,/*vDensity*/90,/*hDensity*/30, world, rand, chunkX, chunkZ, Min, Max, "Bituminous Coal");

		//============Lignite
		baseRocks.clear();
		baseRocks.put(TFCBlocks.StoneSed, -1);
		createOre(TFCBlocks.Ore, 15, baseRocks,//sedimentary, veins
				/*rarity*/20,/*veinSize*/40,/*veinAmt*/40,/*height*/5,/*diameter*/200,/*vDensity*/90,/*hDensity*/30, world, rand, chunkX, chunkZ, Min, Max, "Lignite");

		//============Kaolinite
		baseRocks.clear();
		baseRocks.put(TFCBlocks.StoneSed, -1);
		createOre(TFCBlocks.Ore2, 0, baseRocks,//sedimentary, large clusters
				/*rarity*/90,/*veinSize*/20,/*veinAmt*/2,/*height*/height,/*diameter*/40,/*vDensity*/80,/*hDensity*/90, world, rand, chunkX, chunkZ, Min, Max, "Kaolinite");

		//============Gypsum
		baseRocks.clear();
		baseRocks.put(TFCBlocks.StoneSed, -1);
		createOre(TFCBlocks.Ore2, 1, baseRocks,//sedimentary, large clusters
				/*rarity*/110,/*veinSize*/40,/*veinAmt*/20,/*height*/height,/*diameter*/40,/*vDensity*/50,/*hDensity*/90, world, rand, chunkX, chunkZ, Min, Max, "Gypsum");

		//============Satinspar
		baseRocks.clear();
		baseRocks.put(TFCBlocks.Ore2, 1);
		createOreVein(TFCBlocks.Ore2, 2, baseRocks,//gypsum, small clusters
				/*rarity*/2,/*veinSize*/6,/*veinAmt*/20,/*height*/height,/*diameter*/40,/*vDensity*/40,/*hDensity*/40, world, rand, chunkX, chunkZ, Min, Max, "Satinspar");

		//============Selenite
		baseRocks.clear();
		baseRocks.put(TFCBlocks.Ore2, 1);
		createOreVein(TFCBlocks.Ore2, 3, baseRocks,//gypsum, small clusters
				/*rarity*/2,/*veinSize*/6,/*veinAmt*/20,/*height*/height,/*diameter*/40,/*vDensity*/40,/*hDensity*/40, world, rand, chunkX, chunkZ, Min, Max, "Selenite");

		//============Graphite
		baseRocks.clear();
		baseRocks.put(TFCBlocks.StoneMM, 0);
		baseRocks.put(TFCBlocks.StoneMM, 3);
		baseRocks.put(TFCBlocks.StoneMM, 4);
		baseRocks.put(TFCBlocks.StoneMM, 5);
		createOreVein(TFCBlocks.Ore2, 4, baseRocks,//gneiss, quartzite, marble, schist, small clusters
				/*rarity*/100,/*veinSize*/6,/*veinAmt*/24,/*height*/height,/*diameter*/40,/*vDensity*/40,/*hDensity*/40, world, rand, chunkX, chunkZ, Min, Max, "Graphite");

		//============Kimberlite
		baseRocks.clear();
		baseRocks.put(TFCBlocks.StoneIgIn, 2);
		createOreVein(TFCBlocks.Ore2, 5, baseRocks,//Gabbro, large clusters
				/*rarity*/200,/*veinSize*/40,/*veinAmt*/20,/*height*/height,/*diameter*/40,/*vDensity*/40,/*hDensity*/90, world, rand, chunkX, chunkZ, Min, Max, "Kimberlite");

		//============Petrified Wood
		baseRocks.clear();
		baseRocks.put(TFCBlocks.StoneSed, -1);
		createOre(TFCBlocks.Ore2, 6, baseRocks,//Sedimentary, small clusters 
				/*rarity*/200,/*veinSize*/10,/*veinAmt*/5,/*height*/height,/*diameter*/20,/*vDensity*/10,/*hDensity*/40, world, rand, chunkX, chunkZ, Min, Max, "Petrified Wood");

		//============Sulfur
		//      createOre(mod_TFCraft.terraOre, 14Sulfur,new int[]{mod_TFCraft.terraStoneIgEx,-1,mod_TFCraft.terraOre2,8},//igex, gypsum small clusters
		//              /*rarity*/4,/*veinSize*/6,/*veinAmt*/10,/*height*/128,/*diameter*/40,/*vDensity*/40,/*hDensity*/40,         world, rand, chunkX, chunkZ);

		//============Jet
		baseRocks.clear();
		baseRocks.put(TFCBlocks.StoneSed, -1);
		createOre(TFCBlocks.Ore2, 8, baseRocks,//Sedimentary, med clusters 
				/*rarity*/100,/*veinSize*/30,/*veinAmt*/10,/*height*/height,/*diameter*/40,/*vDensity*/60,/*hDensity*/60, world, rand, chunkX, chunkZ, Min, Max, "Jet");

		//============Microcline
		//        createOre(mod_TFC_Core.terraOre2, 9,new int[]{mod_TFC_Core.terraStoneIgIn,0},//granite, large clusters 
		//                /*rarity*/45,/*veinSize*/64,/*veinAmt*/2,/*height*/height,/*diameter*/50,/*vDensity*/10,/*hDensity*/40, world, rand, chunkX, chunkZ, Min, Max);

		//============Pitchblende
		baseRocks.clear();
		baseRocks.put(TFCBlocks.StoneIgIn, 0);
		createOre(TFCBlocks.Ore2, 10, baseRocks,//granite, small clusters 
				/*rarity*/100,/*veinSize*/10,/*veinAmt*/10,/*height*/height,/*diameter*/50,/*vDensity*/10,/*hDensity*/40, world, rand, chunkX, chunkZ, Min, Max, "Pithcblende");

		//============Cinnabar
		baseRocks.clear();
		baseRocks.put(TFCBlocks.StoneIgEx, -1);
		baseRocks.put(TFCBlocks.StoneSed, 0);
		baseRocks.put(TFCBlocks.StoneMM, 0);
		createOreVein(TFCBlocks.Ore2, 11, baseRocks,//igex, shale, quartzite small clusters
				/*rarity*/60,/*veinSize*/35,/*veinAmt*/30,/*height*/height,/*diameter*/50,/*vDensity*/40,/*hDensity*/40, world, rand, chunkX, chunkZ, Min, Max, "Cinnabar");

		//============Cryolite
		baseRocks.clear();
		baseRocks.put(TFCBlocks.StoneIgIn, 0);
		createOre(TFCBlocks.Ore2, 12, baseRocks,//granite, small clusters 
				/*rarity*/100,/*veinSize*/10,/*veinAmt*/20,/*height*/height,/*diameter*/50,/*vDensity*/10,/*hDensity*/40, world, rand, chunkX, chunkZ, Min, Max, "Cryolite");

		//============Saltpeter
		baseRocks.clear();
		baseRocks.put(TFCBlocks.StoneSed, -1);
		createOre(TFCBlocks.Ore2, 13, baseRocks,//sed, small clusters 
				/*rarity*/100,/*veinSize*/10,/*veinAmt*/20,/*height*/height,/*diameter*/50,/*vDensity*/10,/*hDensity*/40, world, rand, chunkX, chunkZ, Min, Max, "Saltpeter");

		//============Olivine(Out of Order) must come before serpentine
		baseRocks.clear();
		baseRocks.put(TFCBlocks.StoneIgIn, 2);
		createOre(TFCBlocks.Ore3, 1, baseRocks,//gabbro, large clusters 
				/*rarity*/80,/*veinSize*/30,/*veinAmt*/14,/*height*/height,/*diameter*/50,/*vDensity*/10,/*hDensity*/60, world, rand, chunkX, chunkZ, Min, Max, "Olivine");

		//============Serpentine
		baseRocks.clear();
		baseRocks.put(TFCBlocks.Ore3, 1);
		createOre(TFCBlocks.Ore2, 14, baseRocks,//Olivine, small clusters 
				/*rarity*/2,/*veinSize*/10,/*veinAmt*/8,/*height*/height,/*diameter*/50,/*vDensity*/10,/*hDensity*/60, world, rand, chunkX, chunkZ, Min, Max, "Serpentine");

		//============Sylvite
		baseRocks.clear();
		baseRocks.put(TFCBlocks.StoneSed, 2);
		createOre(TFCBlocks.Ore2, 15, baseRocks,//Rock Salt, large clusters 
				/*rarity*/80,/*veinSize*/40,/*veinAmt*/14,/*height*/height,/*diameter*/50,/*vDensity*/10,/*hDensity*/60, world, rand, chunkX, chunkZ, Min, Max, "Sylvite");

		//============Borax
		baseRocks.clear();
		baseRocks.put(TFCBlocks.StoneSed, 2);
		createOre(TFCBlocks.Ore3, 0, baseRocks,//Rock Salt, large clusters 
				/*rarity*/50,/*veinSize*/50,/*veinAmt*/24,/*height*/height,/*diameter*/200,/*vDensity*/50,/*hDensity*/60, world, rand, chunkX, chunkZ, Min, Max, "Borax");
		baseRocks.clear();
		baseRocks.put(TFCBlocks.Ore2, 1);
		createOre(TFCBlocks.Ore3, 0, baseRocks,//Gypsum, small clusters 
				/*rarity*/3,/*veinSize*/12,/*veinAmt*/22,/*height*/height,/*diameter*/200,/*vDensity*/40,/*hDensity*/40, world, rand, chunkX, chunkZ, Min, Max, "Borax");
		//============Lapis Lazuli
		baseRocks.clear();
		baseRocks.put(TFCBlocks.StoneMM, 5);
		createOre(TFCBlocks.Ore3, 2, baseRocks,//Marble, small clusters 
				/*rarity*/90,/*veinSize*/20,/*veinAmt*/26,/*height*/height,/*diameter*/60,/*vDensity*/40,/*hDensity*/40, world, rand, chunkX, chunkZ, Min, Max, "Lapis Lazuli");

		//============Platinum -- (out of order) must follow magnetite and olivine
		baseRocks.clear();
		baseRocks.put(TFCBlocks.Ore, 1);
		baseRocks.put(TFCBlocks.Ore3, 8);
		createOre(TFCBlocks.Ore, 2, baseRocks,//magnetite, veins
				/*rarity*/10,/*veinSize*/8,/*veinAmt*/10,/*height*/height,/*diameter*/25,/*vDensity*/60,/*hDensity*/40, world, rand, chunkX, chunkZ, Min, Max, "Platinum");
	}

	private void oreSmallVein(Block block, int meta, HashMap<Block, Integer> baseRocks, int rarity, int min, int max, int vDensity, int hDensity)
	{
		createOreVein(block, meta ,baseRocks,
				/*rarity*/rarity,/*veinSize*/20,/*veinAmt*/20,/*height*/5,/*diameter*/40,/*vDensity*/vDensity,/*hDensity*/hDensity,
				worldObj, random, ChunkX, ChunkZ, min, max, "Graphite");
	}

	private void oreMediumVein(Block block, int meta, HashMap<Block, Integer> baseRocks, int rarity, int min, int max, int vDensity, int hDensity)
	{
		createOreVein(block, meta ,baseRocks,
				/*rarity*/rarity,/*veinSize*/30,/*veinAmt*/30,/*height*/10,/*diameter*/60,/*vDensity*/vDensity,/*hDensity*/hDensity,
				worldObj, random, ChunkX, ChunkZ, min, max, "Graphite");
	}

	private void oreLargeVein(Block block, int meta, HashMap<Block, Integer> baseRocks, int rarity, int min, int max, int vDensity, int hDensity)
	{
		createOreVein(block, meta ,baseRocks,
				/*rarity*/rarity,/*veinSize*/60,/*veinAmt*/45,/*height*/20,/*diameter*/80,/*vDensity*/vDensity,/*hDensity*/hDensity,
				worldObj, random, ChunkX, ChunkZ, min, max, "Graphite");
	}

	private void oreSmall(Block block, int meta, HashMap<Block, Integer> baseRocks, int rarity, int min, int max, int vDensity, int hDensity)
	{
		createOreVein(block, meta ,baseRocks,
				/*rarity*/rarity,/*veinSize*/20,/*veinAmt*/20,/*height*/5,/*diameter*/80,/*vDensity*/vDensity,/*hDensity*/hDensity,
				worldObj, random, ChunkX, ChunkZ, min, max, "Graphite");
	}

	private void oreMedium(Block block, int meta, HashMap<Block, Integer> baseRocks, int rarity, int min, int max, int vDensity, int hDensity)
	{
		createOre(block, meta ,baseRocks,
				/*rarity*/rarity,/*veinSize*/30,/*veinAmt*/30,/*height*/10,/*diameter*/120,/*vDensity*/vDensity,/*hDensity*/hDensity,
				worldObj, random, ChunkX, ChunkZ, min, max, "Graphite");
	}

	private void oreLarge(Block block, int meta, HashMap<Block, Integer> baseRocks, int rarity, int min, int max, int vDensity, int hDensity)
	{
		createOre(block, meta ,baseRocks,
				/*rarity*/rarity,/*veinSize*/60,/*veinAmt*/40,/*height*/5,/*diameter*/120,/*vDensity*/vDensity,/*hDensity*/hDensity,
				worldObj, random, ChunkX, ChunkZ, min, max, "Graphite");
	}

	private static void createOre(Block block, int j, HashMap<Block, Integer> Layers, int rarity, int veinSize, 
			int veinAmount, int height, int diameter, int vDensity, int hDensity,World world, Random rand, int chunkX, int chunkZ, int min, int max, String name)
	{
		if(world.getWorldChunkManager() instanceof TFCWorldChunkManager)
		{
			for(Block B : Layers.keySet())
			{
				DataLayer rockLayer1 = ((TFCWorldChunkManager)world.getWorldChunkManager()).getRockLayerAt(chunkX, chunkZ, 0);
				DataLayer rockLayer2 = ((TFCWorldChunkManager)world.getWorldChunkManager()).getRockLayerAt(chunkX, chunkZ, 1);
				DataLayer rockLayer3 = ((TFCWorldChunkManager)world.getWorldChunkManager()).getRockLayerAt(chunkX, chunkZ, 2);
				if((rockLayer1.block == B && (rockLayer1.data2 == Layers.get(B) || Layers.get(B) == -1)) || 
						(rockLayer2.block == B && (rockLayer2.data2 == Layers.get(B) || Layers.get(B) == -1)) ||
						(rockLayer3.block == B && (rockLayer3.data2 == Layers.get(B) || Layers.get(B) == -1)))
				{
					new WorldGenMinable(block, j, B, Layers.get(B), rarity, veinSize, veinAmount, height, diameter, vDensity, hDensity, false).generate(
							world, rand, chunkX, chunkZ, min, max, name);
				}
			}
		}
	}

	// new int[]{TFCBlocks.StoneIgEx,-1,Blocks.sandstone,-1}
	private static void createOreVein(Block block, int j, HashMap<Block, Integer> Layers, int rarity, int veinSize, 
			int veinAmount, int height, int diameter, int vDensity, int hDensity,World world, Random rand, int chunkX, int chunkZ, int min, int max, String name)
	{
		if(world.getWorldChunkManager() instanceof TFCWorldChunkManager)
		{
			for(Block B : Layers.keySet())
			{
				DataLayer rockLayer1 = ((TFCWorldChunkManager)world.getWorldChunkManager()).getRockLayerAt(chunkX, chunkZ, 0);
				DataLayer rockLayer2 = ((TFCWorldChunkManager)world.getWorldChunkManager()).getRockLayerAt(chunkX, chunkZ, 1);
				DataLayer rockLayer3 = ((TFCWorldChunkManager)world.getWorldChunkManager()).getRockLayerAt(chunkX, chunkZ, 2);
				if((rockLayer1.block == B && (rockLayer1.data2 == Layers.get(B) || Layers.get(B) == -1)) || 
						(rockLayer2.block == B && (rockLayer2.data2 == Layers.get(B) || Layers.get(B) == -1)) ||
						(rockLayer3.block == B && (rockLayer3.data2 == Layers.get(B) || Layers.get(B) == -1)))
				{
					new WorldGenMinable(block, j, B, Layers.get(B), rarity, veinSize, veinAmount, height, diameter, vDensity, hDensity, true).generate(
							world, rand, chunkX, chunkZ, min, max, name);
				}
			}
		}
	}
}
