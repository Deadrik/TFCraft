package com.bioxx.tfc.WorldGen.Generators;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;

import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.Util.CaseInsensitiveHashMap;
import com.bioxx.tfc.WorldGen.DataLayer;
import com.bioxx.tfc.WorldGen.TFCWorldChunkManager;

public class WorldGenOre implements IWorldGenerator
{
	int Min;
	int Max;
	int ChunkX;
	int ChunkZ;
	World worldObj;
	Random random;

	public static HashMap<String, OreSpawnData> OreList = new CaseInsensitiveHashMap<OreSpawnData>();

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

	private void oreSmallVein(Block block, int meta, HashMap<Block, List<Integer>> baseRocks, int rarity, int min, int max, int vDensity, int hDensity)
	{
		createOreVein(block, meta ,baseRocks,
				/*rarity*/rarity,/*veinSize*/20,/*veinAmt*/30,/*height*/5,/*diameter*/40,/*vDensity*/vDensity,/*hDensity*/hDensity,
				worldObj, random, ChunkX, ChunkZ, min, max);
	}

	private void oreMediumVein(Block block, int meta, HashMap<Block, List<Integer>> baseRocks, int rarity, int min, int max, int vDensity, int hDensity)
	{
		createOreVein(block, meta ,baseRocks,
				/*rarity*/rarity,/*veinSize*/30,/*veinAmt*/40,/*height*/10,/*diameter*/60,/*vDensity*/vDensity,/*hDensity*/hDensity,
				worldObj, random, ChunkX, ChunkZ, min, max);
	}

	private void oreLargeVein(Block block, int meta, HashMap<Block, List<Integer>> baseRocks, int rarity, int min, int max, int vDensity, int hDensity)
	{
		createOreVein(block, meta ,baseRocks,
				/*rarity*/rarity,/*veinSize*/60,/*veinAmt*/45,/*height*/20,/*diameter*/80,/*vDensity*/vDensity,/*hDensity*/hDensity,
				worldObj, random, ChunkX, ChunkZ, min, max);
	}

	private void oreSmall(Block block, int meta, HashMap<Block, List<Integer>> baseRocks, int rarity, int min, int max, int vDensity, int hDensity)
	{
		createOreVein(block, meta ,baseRocks,
				/*rarity*/rarity,/*veinSize*/20,/*veinAmt*/30,/*height*/5,/*diameter*/80,/*vDensity*/vDensity,/*hDensity*/hDensity,
				worldObj, random, ChunkX, ChunkZ, min, max);
	}

	private void oreMedium(Block block, int meta, HashMap<Block, List<Integer>> baseRocks, int rarity, int min, int max, int vDensity, int hDensity)
	{
		createOre(block, meta ,baseRocks,
				/*rarity*/rarity,/*veinSize*/30,/*veinAmt*/40,/*height*/10,/*diameter*/120,/*vDensity*/vDensity,/*hDensity*/hDensity,
				worldObj, random, ChunkX, ChunkZ, min, max);
	}

	private void oreLarge(Block block, int meta, HashMap<Block, List<Integer>> baseRocks, int rarity, int min, int max, int vDensity, int hDensity)
	{
		createOre(block, meta ,baseRocks,
				/*rarity*/rarity,/*veinSize*/60,/*veinAmt*/40,/*height*/5,/*diameter*/120,/*vDensity*/vDensity,/*hDensity*/hDensity,
				worldObj, random, ChunkX, ChunkZ, min, max);
	}

	private static void createOre(Block block, int j, HashMap<Block, List<Integer>> Layers, int rarity, int veinSize,
			int veinAmount, int height, int diameter, int vDensity, int hDensity, World world, Random rand, int chunkX, int chunkZ, int min, int max)
	{
		if(world.getWorldChunkManager() instanceof TFCWorldChunkManager)
		{
			for(Block B : Layers.keySet())
			{
				for(int metadata : Layers.get(B))
				{
					DataLayer rockLayer1 = TFC_Climate.getCacheManager(world).getRockLayerAt(chunkX, chunkZ, 0);
					DataLayer rockLayer2 = TFC_Climate.getCacheManager(world).getRockLayerAt(chunkX, chunkZ, 1);
					DataLayer rockLayer3 = TFC_Climate.getCacheManager(world).getRockLayerAt(chunkX, chunkZ, 2);
					if ((rockLayer1.block == B && (rockLayer1.data2 == metadata || metadata == -1)) ||
							(rockLayer2.block == B && (rockLayer2.data2 == metadata || metadata == -1)) ||
							(rockLayer3.block == B && (rockLayer3.data2 == metadata || metadata == -1)))
					{
						int grade = rand.nextInt(100);
						if(grade<20)
							grade = 1;
						else if(grade <50)
							grade = 2;
						else
							grade = 0;

						new WorldGenMinable(block, j, B, metadata, rarity, veinSize, veinAmount, height, diameter, vDensity, hDensity, false, grade)
								.generate(world, rand, chunkX, chunkZ, min, max);
					}
				}
			}
		}
	}

	// new int[]{TFCBlocks.StoneIgEx,-1,Blocks.sandstone,-1}
	private static void createOreVein(Block block, int j, HashMap<Block, List<Integer>> Layers, int rarity, int veinSize,
			int veinAmount, int height, int diameter, int vDensity, int hDensity, World world, Random rand, int chunkX, int chunkZ, int min, int max)
	{
		if(world.getWorldChunkManager() instanceof TFCWorldChunkManager)
		{
			for(Block B : Layers.keySet())
			{
				for (int metadata : Layers.get(B))
				{
					DataLayer rockLayer1 = TFC_Climate.getCacheManager(world).getRockLayerAt(chunkX, chunkZ, 0);
					DataLayer rockLayer2 = TFC_Climate.getCacheManager(world).getRockLayerAt(chunkX, chunkZ, 1);
					DataLayer rockLayer3 = TFC_Climate.getCacheManager(world).getRockLayerAt(chunkX, chunkZ, 2);
					if ((rockLayer1.block == B && (rockLayer1.data2 == metadata || metadata == -1)) ||
							(rockLayer2.block == B && (rockLayer2.data2 == metadata || metadata == -1)) ||
							(rockLayer3.block == B && (rockLayer3.data2 == metadata || metadata == -1)))
					{
						int grade = rand.nextInt(100);
						if (grade < 20)
							grade = 1;
						else if (grade < 50)
							grade = 2;
						else
							grade = 0;

						new WorldGenMinable(block, j, B, metadata, rarity, veinSize, veinAmount, height, diameter, vDensity, hDensity, true, grade)
								.generate(world, rand, chunkX, chunkZ, min, max);
					}
				}
			}
		}
	}
}
