package TFC.WorldGen.Generators;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
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
	public void generate(Random rand, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) 
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
				{
					oreSmall(osd.id, osd.meta, osd.base, osd.rarity, osd.min, osd.max, osd.vDensity, osd.hDensity);
				}
				else if(osd.size == 1)
				{
					oreMedium(osd.id, osd.meta, osd.base, osd.rarity, osd.min, osd.max, osd.vDensity, osd.hDensity);
				}
				else if(osd.size == 2)
				{
					oreLarge(osd.id, osd.meta, osd.base, osd.rarity, osd.min, osd.max, osd.vDensity, osd.hDensity);
				}
			}
			else if(osd.type == 1)
			{
				if(osd.size == 0)
				{
					oreSmallVein(osd.id, osd.meta, osd.base, osd.rarity, osd.min, osd.max, osd.vDensity, osd.hDensity);
				}
				else if(osd.size == 1)
				{
					oreMediumVein(osd.id, osd.meta, osd.base, osd.rarity, osd.min, osd.max, osd.vDensity, osd.hDensity);
				}
				else if(osd.size == 2)
				{
					oreLargeVein(osd.id, osd.meta, osd.base, osd.rarity, osd.min, osd.max, osd.vDensity, osd.hDensity);
				}
			}
		}
	}

	private void oreSmallVein(int id, int meta, int[] baseRocks, int rarity, int min, int max, int vDensity, int hDensity)
	{
		createOreVein(id, meta ,baseRocks,
				/*rarity*/rarity,/*veinSize*/20,/*veinAmt*/30,/*height*/5,/*diameter*/40,/*vDensity*/vDensity,/*hDensity*/hDensity,        
				worldObj, random, ChunkX, ChunkZ, min, max, "Graphite");

	}

	private void oreMediumVein(int id, int meta, int[] baseRocks, int rarity, int min, int max, int vDensity, int hDensity)
	{
		createOreVein(id, meta ,baseRocks,
				/*rarity*/rarity,/*veinSize*/30,/*veinAmt*/40,/*height*/10,/*diameter*/60,/*vDensity*/vDensity,/*hDensity*/hDensity,        
				worldObj, random, ChunkX, ChunkZ, min, max, "Graphite");

	}

	private void oreLargeVein(int id, int meta, int[] baseRocks, int rarity, int min, int max, int vDensity, int hDensity)
	{
		createOreVein(id, meta ,baseRocks,
				/*rarity*/rarity,/*veinSize*/60,/*veinAmt*/45,/*height*/20,/*diameter*/80,/*vDensity*/vDensity,/*hDensity*/hDensity,        
				worldObj, random, ChunkX, ChunkZ, min, max, "Graphite");

	}

	private void oreSmall(int id, int meta, int[] baseRocks, int rarity, int min, int max, int vDensity, int hDensity)
	{
		createOreVein(id, meta ,baseRocks,
				/*rarity*/rarity,/*veinSize*/20,/*veinAmt*/30,/*height*/5,/*diameter*/80,/*vDensity*/vDensity,/*hDensity*/hDensity,        
				worldObj, random, ChunkX, ChunkZ, min, max, "Graphite");

	}

	private void oreMedium(int id, int meta, int[] baseRocks, int rarity, int min, int max, int vDensity, int hDensity)
	{
		createOre(id, meta ,baseRocks,
				/*rarity*/rarity,/*veinSize*/30,/*veinAmt*/40,/*height*/10,/*diameter*/120,/*vDensity*/vDensity,/*hDensity*/hDensity,        
				worldObj, random, ChunkX, ChunkZ, min, max, "Graphite");

	}

	private void oreLarge(int id, int meta, int[] baseRocks, int rarity, int min, int max, int vDensity, int hDensity)
	{
		createOre(id, meta ,baseRocks,
				/*rarity*/rarity,/*veinSize*/60,/*veinAmt*/40,/*height*/5,/*diameter*/120,/*vDensity*/vDensity,/*hDensity*/hDensity,        
				worldObj, random, ChunkX, ChunkZ, min, max, "Graphite");

	}

	private static void createOre(int i, int j, int[] Layers, int rarity, int veinSize, 
			int veinAmount, int height, int diameter, int vDensity, int hDensity,World world, Random rand, int chunkX, int chunkZ, int min, int max, String name)
	{
		if(world.getWorldChunkManager() instanceof TFCWorldChunkManager)
		{
			for(int n = 0; n < Layers.length/2;)
			{
				DataLayer rockLayer1 = ((TFCWorldChunkManager)world.getWorldChunkManager()).getRockLayerAt(chunkX, chunkZ, 0);
				DataLayer rockLayer2 = ((TFCWorldChunkManager)world.getWorldChunkManager()).getRockLayerAt(chunkX, chunkZ, 1);
				DataLayer rockLayer3 = ((TFCWorldChunkManager)world.getWorldChunkManager()).getRockLayerAt(chunkX, chunkZ, 2);
				if((rockLayer1.data1 == Layers[n] && (rockLayer1.data2 == Layers[n+1] || Layers[n+1] == -1)) || 
						(rockLayer2.data1 == Layers[n] && (rockLayer2.data2 == Layers[n+1] || Layers[n+1] == -1)) ||
						(rockLayer3.data1 == Layers[n] && (rockLayer3.data2 == Layers[n+1] || Layers[n+1] == -1)))
				{
					int grade = rand.nextInt(100);
					if(grade<20)
						grade = 1;
					else if(grade <50)
						grade = 2;
					else
						grade = 0;
					new WorldGenMinable(i, j,Layers[n],Layers[n+1],rarity,veinSize,veinAmount,height,diameter,vDensity,hDensity, false, grade).generate(
							world, rand, chunkX, chunkZ, min, max, name);
				}
				n+=2;
			}
		}
	}

	private static void createOreVein(int i, int j, int[] Layers, int rarity, int veinSize, 
			int veinAmount, int height, int diameter, int vDensity, int hDensity,World world, Random rand, int chunkX, int chunkZ, int min, int max, String name)
	{
		if(world.getWorldChunkManager() instanceof TFCWorldChunkManager)
		{
			for(int n = 0; n < Layers.length/2;)
			{
				DataLayer rockLayer1 = ((TFCWorldChunkManager)world.getWorldChunkManager()).getRockLayerAt(chunkX, chunkZ, 0);
				DataLayer rockLayer2 = ((TFCWorldChunkManager)world.getWorldChunkManager()).getRockLayerAt(chunkX, chunkZ, 1);
				DataLayer rockLayer3 = ((TFCWorldChunkManager)world.getWorldChunkManager()).getRockLayerAt(chunkX, chunkZ, 2);
				if((rockLayer1.data1 == Layers[n] && (rockLayer1.data2 == Layers[n+1] || Layers[n+1] == -1)) || 
						(rockLayer2.data1 == Layers[n] && (rockLayer2.data2 == Layers[n+1] || Layers[n+1] == -1)) ||
						(rockLayer3.data1 == Layers[n] && (rockLayer3.data2 == Layers[n+1] || Layers[n+1] == -1)))
				{
					int grade = rand.nextInt(100);
					if(grade<20)
						grade = 1;
					else if(grade <50)
						grade = 2;
					else
						grade = 0;
					new WorldGenMinable(i, j,Layers[n],Layers[n+1],rarity,veinSize,veinAmount,height,diameter,vDensity,hDensity, true, grade).generate(
							world, rand, chunkX, chunkZ, min, max, name);
				}
				n+=2;
			}
		}
	}
}
