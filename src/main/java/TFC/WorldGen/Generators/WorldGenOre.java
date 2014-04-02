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
