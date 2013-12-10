package TFC.WorldGen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.gen.layer.IntCache;
import TFC.Core.TFC_Climate;
import TFC.WorldGen.GenLayers.GenEVTLayerTFC;
import TFC.WorldGen.GenLayers.GenLayerTFC;
import TFC.WorldGen.GenLayers.GenRainLayerTFC;
import TFC.WorldGen.GenLayers.GenRockLayer1TFC;
import TFC.WorldGen.GenLayers.GenRockLayer2TFC;
import TFC.WorldGen.GenLayers.GenRockLayer3TFC;
import TFC.WorldGen.GenLayers.GenTreeLayerTFC;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TFCWorldChunkManager extends WorldChunkManager 
{
	protected GenLayerTFC genBiomes;

	protected GenLayerTFC biomeIndexLayer;

	/** The BiomeCache object for this world. */
	protected BiomeCache biomeCache;

	/** A list of biomes that the player can spawn in. */
	protected List biomesToSpawnIn;

	//Rocks
	protected GenLayerTFC[] genRocks;
	protected GenLayerTFC[] rocksIndexLayer;
	protected DataCache[] rockCache;

	//Trees
	protected GenLayerTFC[] genTrees;
	protected GenLayerTFC[] treesIndexLayer;
	protected DataCache[] treeCache;

	//Humidity
	protected GenLayerTFC genEVT;
	protected GenLayerTFC evtIndexLayer;
	protected DataCache evtCache;

	//Rainfall
	protected GenLayerTFC genRainfall;
	protected GenLayerTFC rainfallIndexLayer;
	protected DataCache rainfallCache;

	public TFCWorldChunkManager()
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
	public TFCWorldChunkManager(long Seed, WorldType par3WorldType)
	{
		this();
		seed = Seed;
		GenLayerTFC[] var4 = GenLayerTFC.initializeAllBiomeGenerators(Seed, par3WorldType);
		this.genBiomes = var4[0];
		this.biomeIndexLayer = var4[1];

		//Setup Rocks
		GenLayerTFC[] var5 = GenRockLayer1TFC.initializeAllBiomeGenerators(Seed+1, par3WorldType);
		GenLayerTFC[] var6 = GenRockLayer2TFC.initializeAllBiomeGenerators(Seed+2, par3WorldType);
		GenLayerTFC[] var7 = GenRockLayer3TFC.initializeAllBiomeGenerators(Seed+3, par3WorldType);
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

		GenLayerTFC[] var8 = GenTreeLayerTFC.initializeAllBiomeGenerators(Seed+4, par3WorldType);
		genTrees[0] = var8[0];
		treesIndexLayer[0] = var8[1];

		var8 = GenTreeLayerTFC.initializeAllBiomeGenerators(Seed+5, par3WorldType);
		genTrees[1] = var8[0];
		treesIndexLayer[1] = var8[1];

		var8 = GenTreeLayerTFC.initializeAllBiomeGenerators(Seed+6, par3WorldType);
		genTrees[2] = var8[0];
		treesIndexLayer[2] = var8[1];

		//Setup Evapotranspiration
		var8 = GenEVTLayerTFC.initializeAllBiomeGenerators(Seed+7, par3WorldType);
		genEVT = var8[0];
		evtIndexLayer = var8[1];

		//Setup Rainfall
		var8 = GenRainLayerTFC.initializeAllBiomeGenerators(Seed+8, par3WorldType);
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
	@Override
	public List getBiomesToSpawnIn()
	{
		return this.biomesToSpawnIn;
	}

	/**
	 * Returns the BiomeGenBase related to the x, z position on the world.
	 */
	@Override
	public BiomeGenBase getBiomeGenAt(int par1, int par2)
	{
		return this.biomeCache.getBiomeGenAt(par1, par2);
	}

	/**
	 * Returns a list of rainfall values for the specified blocks. Args: listToReuse, x, z, width, length.
	 */
	@Override
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

	@Override
	@SideOnly(Side.CLIENT)

	/**
	 * Return an adjusted version of a given temperature based on the y height
	 */
	public float getTemperatureAtHeight(float par1, int par2)
	{
		float temp = par1;
		if(par2 > 180) {
			temp -= temp * (par2-180)/90;
		}
		return temp;
	}

	/**
	 * Returns a list of temperatures to use for the specified blocks.  Args: listToReuse, x, y, width, length
	 */
	@Override
	public float[] getTemperatures(float[] par1ArrayOfFloat, int x, int z, int par4, int par5)
	{
		//	IntCache.resetIntCache();

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
	@Override
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
			int index = Math.max(var6[var7], 0);
			par1ArrayOfBiomeGenBase[var7] = BiomeGenBase.biomeList[index];
		}

		return par1ArrayOfBiomeGenBase;
	}

	/**
	 * Returns biomes to use for the blocks and loads the other data like temperature and humidity onto the
	 * WorldChunkManager Args: oldBiomeList, x, z, width, depth
	 */
	@Override
	public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase[] par1ArrayOfBiomeGenBase, int par2, int par3, int par4, int par5)
	{
		return this.getBiomeGenAt(par1ArrayOfBiomeGenBase, par2, par3, par4, par5, true);
	}

	/**
	 * Return a list of biomes for the specified blocks. Args: listToReuse, x, y, width, length, cacheFlag (if false,
	 * don't check biomeCache to avoid infinite loop in BiomeCacheBlock)
	 */
	@Override
	public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] par1ArrayOfBiomeGenBase, int par2, int par3, int par4, int par5, boolean par6)
	{
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
			IntCache.resetIntCache();

			int[] var7 = this.biomeIndexLayer.getInts(par2, par3, par4, par5);

			for (int var8 = 0; var8 < par4 * par5; ++var8)
			{
				int id = var7[var8] != -1 ? var7[var8] : 0;

				if(var7[var8] == -1) {
					System.out.println("var7[var8] is " + var7[var8]);
				}

				if(var8 == -1) {
					System.out.println("var8 is " + var8);
				}

				par1ArrayOfBiomeGenBase[var8] = BiomeGenBase.biomeList[id];
			}

			return par1ArrayOfBiomeGenBase;
		}
	}

	/**
	 * checks given Chunk's Biomes against List of allowed ones
	 */
	@Override
	public boolean areBiomesViable(int par1, int par2, int par3, List par4List)
	{
		IntCache.resetIntCache();
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
	@Override
	public ChunkPosition findBiomePosition(int par1, int par2, int par3, List par4List, Random par5Random)
	{
		IntCache.resetIntCache();
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
	@Override
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

	public DataLayer[] getDataLayerAt(DataCache[] cache, DataLayer[] layers, GenLayerTFC[] indexLayers, int x, int y, int width, int height, boolean par6, int layer)
	{
		if (layers == null || layers.length < width * height)
		{
			layers = new DataLayer[width * height];
		}

		if (par6 && width == 16 && height == 16 && (x & 15) == 0 && (y & 15) == 0)
		{
			DataLayer[] var9 = cache[layer].getCachedData(indexLayers[layer], x, y);
			System.arraycopy(var9, 0, layers, 0, width * height);
			return layers;
		}
		else
		{
			IntCache.resetIntCache();

			int[] var7 = indexLayers[layer].getInts(x, y, width, height);

			for (int var8 = 0; var8 < width * height; ++var8)
			{
				layers[var8] = DataLayer.layers[var7[var8]];
			}

			return layers;
		}
	}

	public DataLayer[] getDataLayerAt(DataCache cache, DataLayer[] layers, GenLayerTFC indexLayers, int x, int y, int width, int height, boolean par6, int layer)
	{
		if (layers == null || layers.length < width * height)
		{
			layers = new DataLayer[width * height];
		}

		if (par6 && width == 16 && height == 16 && (x & 15) == 0 && (y & 15) == 0)
		{
			DataLayer[] var9 = cache.getCachedData(indexLayers, x, y);
			System.arraycopy(var9, 0, layers, 0, width * height);
			return layers;
		}
		else
		{
			IntCache.resetIntCache();

			int[] var7 = indexLayers.getInts(x, y, width, height);

			for (int var8 = 0; var8 < width * height; ++var8)
			{
				layers[var8] = DataLayer.layers[var7[var8]];
			}

			return layers;
		}
	}

	public DataLayer getRockLayerAt(int x, int y, int index)
	{
		return this.rockCache[index].getDataLayerAt(rocksIndexLayer[index], x, y);
	}

	/**
	 * Returns biomes to use for the blocks and loads the other data like temperature and humidity onto the
	 * WorldChunkManager Args: oldBiomeList, x, z, width, depth
	 */
	public DataLayer[] loadRockLayerGeneratorData(DataLayer[] layers, int x, int y, int width, int height, int layer)
	{
		return this.getDataLayerAt(rockCache, layers, rocksIndexLayer, x, y, width, height, true, layer);
	}

	public DataLayer getTreeLayerAt(int par1, int par2, int index)
	{
		return this.treeCache[index].getDataLayerAt(treesIndexLayer[index], par1, par2);
	}

	/**
	 * Returns biomes to use for the blocks and loads the other data like temperature and humidity onto the
	 * WorldChunkManager Args: oldBiomeList, x, z, width, depth
	 */
	public DataLayer[] loadTreeLayerGeneratorData(DataLayer[] layers, int x, int y, int width, int height, int layer)
	{    	
		return this.getDataLayerAt(treeCache, layers, treesIndexLayer, x, y, width, height, true, 0);
	}

	public DataLayer getEVTLayerAt(int par1, int par2)
	{
		return this.evtCache.getDataLayerAt(evtIndexLayer, par1, par2);
	}

	/**
	 * Returns evt map to use for the blocks
	 * Args: layers, x, z, width, depth
	 */
	public DataLayer[] loadEVTLayerGeneratorData(DataLayer[] layers, int x, int y, int width, int height)
	{    	
		return this.getDataLayerAt(evtCache, layers, evtIndexLayer, x, y, width, height, true, 0);
	}

	public DataLayer getRainfallLayerAt(int par1, int par2)
	{
		return this.rainfallCache.getDataLayerAt(rainfallIndexLayer, par1, par2);
	}

	/**
	 * Returns rainfall map Args: layers, x, z, width, depth
	 */
	public DataLayer[] loadRainfallLayerGeneratorData(DataLayer[] layers, int x, int y, int width, int height)
	{    	
		return this.getDataLayerAt(rainfallCache, layers, rainfallIndexLayer, x, y, width, height, true, 0);
	}

}
