package TFC.WorldGen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import TFC.Core.TFC_Climate;
import TFC.WorldGen.GenLayers.GenEVTLayerTFC;
import TFC.WorldGen.GenLayers.GenLayerTFC;
import TFC.WorldGen.GenLayers.GenRainLayerTFC;
import TFC.WorldGen.GenLayers.GenRockLayer2TFC;
import TFC.WorldGen.GenLayers.GenTreeLayerTFC;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

import net.minecraft.src.BiomeCache;
import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.ChunkPosition;
import net.minecraft.src.GenLayer;
import net.minecraft.src.IntCache;
import net.minecraft.src.World;
import net.minecraft.src.WorldChunkManager;
import net.minecraft.src.WorldType;

public class TFCWorldChunkManager extends WorldChunkManager 
{
	private GenLayerTFC genBiomes;

	private GenLayerTFC biomeIndexLayer;

	/** The BiomeCache object for this world. */
	private BiomeCache biomeCache;

	/** A list of biomes that the player can spawn in. */
	private List biomesToSpawnIn;

	//Rocks
	private GenLayerTFC[] genRocks;
	private GenLayerTFC[] rocksIndexLayer;
	private DataCache[] rockCache;

	//Trees
	private GenLayerTFC[] genTrees;
	private GenLayerTFC[] treesIndexLayer;
	private DataCache[] treeCache;

	//Humidity
	private GenLayerTFC genEVT;
	private GenLayerTFC evtIndexLayer;
	private DataCache evtCache;

	//Rainfall
	private GenLayerTFC genRainfall;
	private GenLayerTFC rainfallIndexLayer;
	private DataCache rainfallCache;

	protected TFCWorldChunkManager()
	{
		super();
		this.biomeCache = new BiomeCache(this);
		rockCache = new DataCache[3];
		treeCache = new DataCache[3];
		evtCache = new DataCache(this,0);
		rainfallCache = new DataCache(this,0);
		rockCache[0] = new DataCache(this,0);
		rockCache[1] = new DataCache(this,1);
		rockCache[2] = new DataCache(this,2);
		treeCache[0] = new DataCache(this,0);
		treeCache[1] = new DataCache(this,1);
		treeCache[2] = new DataCache(this,2);

		this.biomesToSpawnIn = new ArrayList();
		this.biomesToSpawnIn.add(TFCBiome.forest);
		this.biomesToSpawnIn.add(TFCBiome.HighHills);
		this.biomesToSpawnIn.add(TFCBiome.plains);
		this.biomesToSpawnIn.add(TFCBiome.rollingHills);
		this.biomesToSpawnIn.add(TFCBiome.taiga);
		this.biomesToSpawnIn.add(TFCBiome.swampland);
		this.biomesToSpawnIn.add(TFCBiome.Mountains);

	}
	
	public long seed = 0;

	public TFCWorldChunkManager(long par1, WorldType par3WorldType)
	{
		this();
		seed = par1;
		GenLayerTFC[] var4 = GenLayerTFC.initializeAllBiomeGenerators(par1, par3WorldType);
		this.genBiomes = var4[0];
		this.biomeIndexLayer = var4[1];

		//Setup Rocks
		GenLayerTFC[] var5 = GenRockLayer2TFC.initializeAllBiomeGenerators(par1+1, par3WorldType);
		GenLayerTFC[] var6 = GenRockLayer2TFC.initializeAllBiomeGenerators(par1+2, par3WorldType);
		GenLayerTFC[] var7 = GenRockLayer2TFC.initializeAllBiomeGenerators(par1+3, par3WorldType);
		genRocks = new GenLayerTFC[3];
		rocksIndexLayer = new GenLayerTFC[3];
		this.genRocks[0] = var5[0];
		this.rocksIndexLayer[0] = var5[1];

		this.genRocks[1] = var6[0];
		this.rocksIndexLayer[1] = var6[1];

		this.genRocks[2] = var7[0];
		this.rocksIndexLayer[2] = var7[1];


		//Setup Trees
		genTrees = new GenLayerTFC[3];
		treesIndexLayer = new GenLayerTFC[3];
		
		GenLayerTFC[] var8 = GenTreeLayerTFC.initializeAllBiomeGenerators(par1+4, par3WorldType);
		genTrees[0] = var8[0];
		treesIndexLayer[0] = var8[1];
		
		var8 = GenTreeLayerTFC.initializeAllBiomeGenerators(par1+5, par3WorldType);
		genTrees[1] = var8[0];
		treesIndexLayer[1] = var8[1];
		
		var8 = GenTreeLayerTFC.initializeAllBiomeGenerators(par1+6, par3WorldType);
		genTrees[2] = var8[0];
		treesIndexLayer[2] = var8[1];

		//Setup Evapotranspiration
		var8 = GenEVTLayerTFC.initializeAllBiomeGenerators(par1+7, par3WorldType);
		genEVT = var8[0];
		evtIndexLayer = var8[1];

		//Setup Rainfall
		var8 = GenRainLayerTFC.initializeAllBiomeGenerators(par1+8, par3WorldType);
		genRainfall = var8[0];
		rainfallIndexLayer = var8[1];


	}

	public TFCWorldChunkManager(World par1World)
	{
		this(par1World.getSeed(), par1World.getWorldInfo().getTerrainType());
	}

	/**
	 * Gets the list of valid biomes for the player to spawn in.
	 */
	public List getBiomesToSpawnIn()
	{
		return this.biomesToSpawnIn;
	}

	/**
	 * Returns the BiomeGenBase related to the x, z position on the world.
	 */
	public BiomeGenBase getBiomeGenAt(int par1, int par2)
	{
		return this.biomeCache.getBiomeGenAt(par1, par2);
	}

	/**
	 * Returns a list of rainfall values for the specified blocks. Args: listToReuse, x, z, width, length.
	 */
	public float[] getRainfall(float[] par1ArrayOfFloat, int par2, int par3, int par4, int par5)
	{
		IntCache.resetIntCache();

		if (par1ArrayOfFloat == null || par1ArrayOfFloat.length < par4 * par5)
		{
			par1ArrayOfFloat = new float[par4 * par5];
		}

		int[] var6 = this.rainfallIndexLayer.getInts(par2, par3, par4, par5);

		for (int var7 = 0; var7 < par4 * par5; ++var7)
		{
			float var8 = var6[var7];

			var8 = 1-((8000-var8) / 8000);

			if (var8 > 1.0F)
			{
				var8 = 1.0F;
			}

			par1ArrayOfFloat[var7] = var8;
		}

		return par1ArrayOfFloat;
	}

	@SideOnly(Side.CLIENT)

	/**
	 * Return an adjusted version of a given temperature based on the y height
	 */
	public float getTemperatureAtHeight(float par1, int par2)
	{
		float temp = par1;
		if(par2 > 180)
			temp -= temp * (par2-180)/90;
		return temp;
	}

	/**
	 * Returns a list of temperatures to use for the specified blocks.  Args: listToReuse, x, y, width, length
	 */
	public float[] getTemperatures(float[] par1ArrayOfFloat, int x, int z, int par4, int par5)
	{
		IntCache.resetIntCache();

		if (par1ArrayOfFloat == null || par1ArrayOfFloat.length < par4 * par5)
		{
			par1ArrayOfFloat = new float[par4 * par5];
		}

		//        int[] var6 = this.biomeIndexLayer.getInts(x, z, par4, par5);
		//        var6 = TFC_Climate.getBioTemperature(z)

		for (int var7 = 0; var7 < par4 * par5; ++var7)
		{
			//            float var8 = (float)((TFCBiome)TFCBiome.biomeList[var6[var7]]).getFloatTemp();
			//
			//            if (var8 > 1.0F)
			//            {
			//                var8 = 1.0F;
			//            }

			par1ArrayOfFloat[var7] = TFC_Climate.getBioTemperature(x, z);
		}

		return par1ArrayOfFloat;
	}

	/**
	 * Returns an array of biomes for the location input.
	 */
	public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] par1ArrayOfBiomeGenBase, int par2, int par3, int par4, int par5)
	{
		IntCache.resetIntCache();

		if (par1ArrayOfBiomeGenBase == null || par1ArrayOfBiomeGenBase.length < par4 * par5)
		{
			par1ArrayOfBiomeGenBase = new BiomeGenBase[par4 * par5];
		}

		int[] var6 = this.genBiomes.getInts(par2, par3, par4, par5);

		for (int var7 = 0; var7 < par4 * par5; ++var7)
		{
			par1ArrayOfBiomeGenBase[var7] = BiomeGenBase.biomeList[var6[var7]];
		}

		return par1ArrayOfBiomeGenBase;
	}

	/**
	 * Returns biomes to use for the blocks and loads the other data like temperature and humidity onto the
	 * WorldChunkManager Args: oldBiomeList, x, z, width, depth
	 */
	public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase[] par1ArrayOfBiomeGenBase, int par2, int par3, int par4, int par5)
	{
		return this.getBiomeGenAt(par1ArrayOfBiomeGenBase, par2, par3, par4, par5, true);
	}

	/**
	 * Return a list of biomes for the specified blocks. Args: listToReuse, x, y, width, length, cacheFlag (if false,
	 * don't check biomeCache to avoid infinite loop in BiomeCacheBlock)
	 */
	public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] par1ArrayOfBiomeGenBase, int par2, int par3, int par4, int par5, boolean par6)
	{
		IntCache.resetIntCache();

		if (par1ArrayOfBiomeGenBase == null || par1ArrayOfBiomeGenBase.length < par4 * par5)
		{
			par1ArrayOfBiomeGenBase = new BiomeGenBase[par4 * par5];
		}

		if (par6 && par4 == 16 && par5 == 16 && (par2 & 15) == 0 && (par3 & 15) == 0)
		{
			BiomeGenBase[] var9 = this.biomeCache.getCachedBiomes(par2, par3);
			System.arraycopy(var9, 0, par1ArrayOfBiomeGenBase, 0, par4 * par5);
			return par1ArrayOfBiomeGenBase;
		}
		else
		{
			int[] var7 = this.biomeIndexLayer.getInts(par2, par3, par4, par5);

			for (int var8 = 0; var8 < par4 * par5; ++var8)
			{
				par1ArrayOfBiomeGenBase[var8] = BiomeGenBase.biomeList[var7[var8]];
			}

			return par1ArrayOfBiomeGenBase;
		}
	}

	/**
	 * checks given Chunk's Biomes against List of allowed ones
	 */
	public boolean areBiomesViable(int par1, int par2, int par3, List par4List)
	{
		int var5 = par1 - par3 >> 2;
			int var6 = par2 - par3 >> 2;
		int var7 = par1 + par3 >> 2;
		int var8 = par2 + par3 >> 2;
		int var9 = var7 - var5 + 1;
		int var10 = var8 - var6 + 1;
		int[] var11 = this.genBiomes.getInts(var5, var6, var9, var10);

		for (int var12 = 0; var12 < var9 * var10; ++var12)
		{
			BiomeGenBase var13 = BiomeGenBase.biomeList[var11[var12]];

			if (!par4List.contains(var13))
			{
				return false;
			}
		}

		return true;
	}

	/**
	 * Finds a valid position within a range, that is in one of the listed biomes. Searches {par1,par2} +-par3 blocks.
	 * Strongly favors positive y positions.
	 */
	public ChunkPosition findBiomePosition(int par1, int par2, int par3, List par4List, Random par5Random)
	{
		int var6 = par1 - par3 >> 2;
		int var7 = par2 - par3 >> 2;
		int var8 = par1 + par3 >> 2;
		int var9 = par2 + par3 >> 2;
		int var10 = var8 - var6 + 1;
		int var11 = var9 - var7 + 1;
		int[] var12 = this.genBiomes.getInts(var6, var7, var10, var11);
		ChunkPosition var13 = null;
		int var14 = 0;

		for (int var15 = 0; var15 < var12.length; ++var15)
		{
			int var16 = var6 + var15 % var10 << 2;
			int var17 = var7 + var15 / var10 << 2;
			if(var15 != -1 && var12[var15] != -1)
			{
				BiomeGenBase var18 = BiomeGenBase.biomeList[var12[var15]];

				if (par4List.contains(var18) && (var13 == null || par5Random.nextInt(var14 + 1) == 0))
				{
					var13 = new ChunkPosition(var16, 0, var17);
					++var14;
				}
			}
		}

		return var13;
	}

	/**
	 * Calls the WorldChunkManager's biomeCache.cleanupCache()
	 */
	public void cleanupCache()
	{
		this.biomeCache.cleanupCache();
		this.rockCache[0].cleanupCache();
		this.rockCache[1].cleanupCache();
		this.rockCache[2].cleanupCache();
		this.treeCache[0].cleanupCache();
		this.treeCache[1].cleanupCache();
		this.treeCache[2].cleanupCache();
		this.evtCache.cleanupCache();
		this.rainfallCache.cleanupCache();
	}

	public DataLayer getDataLayerAt(DataCache cache, GenLayerTFC indexLayers, int par1, int par2, int index)
	{
		return cache.getDataLayerAt(indexLayers, par1, par2);
	}

	private DataLayer[] loadDataLayerGeneratorData(DataCache[] cache, DataLayer[] layers, GenLayerTFC[] indexLayers, int par2, int par3, int par4, int par5, int layer)
	{
		return this.getDataLayerAt(cache, layers, indexLayers, par2, par3, par4, par5, true, layer);
	}

	public DataLayer[] getDataLayerAt(DataCache[] cache, DataLayer[] layers, GenLayerTFC[] indexLayers, int par2, int par3, int par4, int par5, boolean par6, int layer)
	{
		IntCache.resetIntCache();

		if (layers == null || layers.length < par4 * par5)
		{
			layers = new DataLayer[par4 * par5];
		}

		if (par6 && par4 == 16 && par5 == 16 && (par2 & 15) == 0 && (par3 & 15) == 0)
		{
			DataLayer[] var9 = cache[layer].getCachedData(indexLayers[layer], par2, par3);
			System.arraycopy(var9, 0, layers, 0, par4 * par5);
			return layers;
		}
		else
		{
			int[] var7 = indexLayers[layer].getInts(par2, par3, par4, par5);

			for (int var8 = 0; var8 < par4 * par5; ++var8)
			{
				layers[var8] = DataLayer.layers[var7[var8]];
			}

			return layers;
		}
	}

	public DataLayer[] getDataLayerAt(DataCache cache, DataLayer[] layers, GenLayerTFC indexLayers, int par2, int par3, int par4, int par5, boolean par6, int layer)
	{
		IntCache.resetIntCache();

		if (layers == null || layers.length < par4 * par5)
		{
			layers = new DataLayer[par4 * par5];
		}

		if (par6 && par4 == 16 && par5 == 16 && (par2 & 15) == 0 && (par3 & 15) == 0)
		{
			DataLayer[] var9 = cache.getCachedData(indexLayers, par2, par3);
			System.arraycopy(var9, 0, layers, 0, par4 * par5);
			return layers;
		}
		else
		{
			int[] var7 = indexLayers.getInts(par2, par3, par4, par5);

			for (int var8 = 0; var8 < par4 * par5; ++var8)
			{
				layers[var8] = DataLayer.layers[var7[var8]];
			}

			return layers;
		}
	}

	public DataLayer getRockLayerAt(int par1, int par2, int index)
	{
		return this.rockCache[index].getDataLayerAt(rocksIndexLayer[index], par1, par2);
	}

	/**
	 * Returns biomes to use for the blocks and loads the other data like temperature and humidity onto the
	 * WorldChunkManager Args: oldBiomeList, x, z, width, depth
	 */
	 public DataLayer[] loadRockLayerGeneratorData(DataLayer[] layers, int par2, int par3, int par4, int par5, int layer)
	{
		return this.getDataLayerAt(rockCache, layers, rocksIndexLayer, par2, par3, par4, par5, true, layer);
	}

	 public DataLayer getTreeLayerAt(int par1, int par2, int index)
	 {
		 return this.treeCache[index].getDataLayerAt(treesIndexLayer[index], par1, par2);
	 }

	 /**
	  * Returns biomes to use for the blocks and loads the other data like temperature and humidity onto the
	  * WorldChunkManager Args: oldBiomeList, x, z, width, depth
	  */
	 public DataLayer[] loadTreeLayerGeneratorData(DataLayer[] layers, int par2, int par3, int par4, int par5, int layer)
	 {    	
		 return this.getDataLayerAt(treeCache, layers, treesIndexLayer, par2, par3, par4, par5, true, 0);
	 }

	 public DataLayer getEVTLayerAt(int par1, int par2)
	 {
		 return this.evtCache.getDataLayerAt(evtIndexLayer, par1, par2);
	 }

	 /**
	  * Returns biomes to use for the blocks and loads the other data like temperature and humidity onto the
	  * WorldChunkManager Args: oldBiomeList, x, z, width, depth
	  */
	 public DataLayer[] loadEVTLayerGeneratorData(DataLayer[] layers, int par2, int par3, int par4, int par5)
	 {    	
		 return this.getDataLayerAt(evtCache, layers, evtIndexLayer, par2, par3, par4, par5, true, 0);
	 }

	 public DataLayer getRainfallLayerAt(int par1, int par2)
	 {
		 return this.rainfallCache.getDataLayerAt(rainfallIndexLayer, par1, par2);
	 }

	 /**
	  * Returns biomes to use for the blocks and loads the other data like temperature and humidity onto the
	  * WorldChunkManager Args: oldBiomeList, x, z, width, depth
	  */
	 public DataLayer[] loadRainfallLayerGeneratorData(DataLayer[] layers, int par2, int par3, int par4, int par5)
	 {    	
		 return this.getDataLayerAt(rainfallCache, layers, rainfallIndexLayer, par2, par3, par4, par5, true, 0);
	 }

}
