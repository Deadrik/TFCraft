package com.bioxx.tfc.WorldGen;

import java.util.Iterator;
import java.util.LinkedHashMap;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.layer.IntCache;

import com.bioxx.tfc.WorldGen.Data.DataCache;
import com.bioxx.tfc.WorldGen.GenLayers.DataLayers.Drainage.GenDrainageLayer;
import com.bioxx.tfc.WorldGen.GenLayers.DataLayers.EVT.GenEVTLayer;
import com.bioxx.tfc.WorldGen.GenLayers.DataLayers.PH.GenPHLayer;
import com.bioxx.tfc.WorldGen.GenLayers.DataLayers.Rain.GenRainLayerTFC;
import com.bioxx.tfc.WorldGen.GenLayers.DataLayers.Rock.GenRockLayer;
import com.bioxx.tfc.WorldGen.GenLayers.DataLayers.Stability.GenStabilityLayer;
import com.bioxx.tfc.WorldGen.GenLayers.DataLayers.Tree.GenTreeLayer;
import com.bioxx.tfc.WorldGen.GenLayers.GenLayerTFC;

public class WorldCacheManager 
{	
	World localWorld;
	//Rocks
	protected GenLayerTFC[] rocksIndexLayer;
	protected DataCache[] rockCache;

	//Trees
	protected GenLayerTFC[] treesIndexLayer;
	protected DataCache[] treeCache;

	//Humidity
	protected GenLayerTFC evtIndexLayer;
	protected DataCache evtCache;

	//Rainfall
	protected GenLayerTFC rainfallIndexLayer;
	protected DataCache rainfallCache;

	//Stability
	protected GenLayerTFC stabilityIndexLayer;
	protected DataCache stabilityCache;

	//Stability
	protected GenLayerTFC phIndexLayer;
	protected DataCache phCache;

	//Stability
	protected GenLayerTFC drainageIndexLayer;
	protected DataCache drainageCache;

	protected LinkedHashMap<String, Float> worldTempCache;

	public long seed = 0;

	public static DataLayer[] RockLayer1 = new DataLayer[]{
		DataLayer.Shale, DataLayer.Claystone, DataLayer.RockSalt, DataLayer.Limestone,
		DataLayer.Conglomerate, DataLayer.Dolomite, DataLayer.Chert, DataLayer.Chalk,
		DataLayer.Rhyolite, DataLayer.Basalt, DataLayer.Andesite, DataLayer.Dacite,
		DataLayer.Quartzite, DataLayer.Slate, DataLayer.Phyllite, DataLayer.Schist,
		DataLayer.Gneiss, DataLayer.Marble, DataLayer.Granite, DataLayer.Diorite, DataLayer.Gabbro};
	public static DataLayer[] RockLayer2 = new DataLayer[]{
		DataLayer.Rhyolite, DataLayer.Basalt, DataLayer.Andesite, DataLayer.Dacite,
		DataLayer.Quartzite, DataLayer.Slate, DataLayer.Phyllite, DataLayer.Schist,
		DataLayer.Gneiss, DataLayer.Marble, DataLayer.Granite, DataLayer.Diorite,
		DataLayer.Gabbro};
	public static DataLayer[] RockLayer3 = new DataLayer[]{
		DataLayer.Rhyolite, DataLayer.Basalt, DataLayer.Andesite,
		DataLayer.Dacite, DataLayer.Granite, DataLayer.Diorite, DataLayer.Gabbro};

	public static DataLayer[] treeArray = new DataLayer[] {DataLayer.Ash, DataLayer.Aspen, DataLayer.Birch, DataLayer.Chestnut, DataLayer.DouglasFir, 
		DataLayer.Hickory, DataLayer.Maple, DataLayer.Oak, DataLayer.Pine, DataLayer.Redwood, DataLayer.Pine, DataLayer.Spruce, DataLayer.Sycamore, 
		DataLayer.WhiteCedar, DataLayer.WhiteElm, DataLayer.Willow, DataLayer.NoTree};

	private WorldCacheManager()
	{
		rockCache = new DataCache[3];
		treeCache = new DataCache[3];
		evtCache = new DataCache(this, 0);
		rainfallCache = new DataCache(this, 0);
		rockCache[0] = new DataCache(this, 0);
		rockCache[1] = new DataCache(this, 1);
		rockCache[2] = new DataCache(this, 2);
		treeCache[0] = new DataCache(this, 0);
		treeCache[1] = new DataCache(this, 1);
		treeCache[2] = new DataCache(this, 2);
		stabilityCache = new DataCache(this, 0);
		phCache = new DataCache(this, 0);
		drainageCache = new DataCache(this, 0);
		worldTempCache = new LinkedHashMap<String, Float>();
	}

	public WorldCacheManager(World world)
	{
		this(world.getSeed(), world.getWorldInfo().getTerrainType());
		localWorld = world;
	}

	private WorldCacheManager(long Seed, WorldType worldtype)
	{
		this();
		seed = Seed;

		//Setup Rocks
		rocksIndexLayer = new GenLayerTFC[3];
		rocksIndexLayer[0] = GenRockLayer.initialize(Seed+1, RockLayer1);
		rocksIndexLayer[1] = GenRockLayer.initialize(Seed+2, RockLayer2);
		rocksIndexLayer[2] = GenRockLayer.initialize(Seed+3, RockLayer3);


		//Setup Trees
		treesIndexLayer = new GenLayerTFC[3];

		treesIndexLayer[0] = GenTreeLayer.initialize(Seed+4, treeArray);
		treesIndexLayer[1] = GenTreeLayer.initialize(Seed+5, treeArray);
		treesIndexLayer[2] = GenTreeLayer.initialize(Seed+6, treeArray);

		//Setup Evapotranspiration
		evtIndexLayer = GenEVTLayer.initialize(Seed+7, worldtype);

		//Setup Rainfall
		rainfallIndexLayer = GenRainLayerTFC.initialize(Seed+8, worldtype);

		//Setup Stability
		stabilityIndexLayer = GenStabilityLayer.initialize(Seed+9, worldtype);

		//Setup Soil PH
		phIndexLayer = GenPHLayer.initialize(Seed+10, worldtype);

		//Setup Soil Drainage
		drainageIndexLayer = GenDrainageLayer.initialize(Seed+11, worldtype);

		worldTempCache = new LinkedHashMap<String, Float>();
	}

	public float[] getRainfall(float[] par1ArrayOfFloat, int par2, int par3, int par4, int par5)
	{
		IntCache.resetIntCache();

		if (par1ArrayOfFloat == null || par1ArrayOfFloat.length < par4 * par5)
			par1ArrayOfFloat = new float[par4 * par5];

		int[] var6 = this.rainfallIndexLayer.getInts(par2, par3, par4, par5);

		for (int var7 = 0; var7 < par4 * par5; ++var7)
		{
			float var8 = var6[var7];
			var8 = 1-((8000-var8) / 8000);
			if (var8 > 1.0F)
				var8 = 1.0F;
			par1ArrayOfFloat[var7] = var8;
		}

		return par1ArrayOfFloat;
	}

	public void cleanupCache()
	{
		this.rockCache[0].cleanupCache();
		this.rockCache[1].cleanupCache();
		this.rockCache[2].cleanupCache();
		this.treeCache[0].cleanupCache();
		this.treeCache[1].cleanupCache();
		this.treeCache[2].cleanupCache();
		this.evtCache.cleanupCache();
		this.rainfallCache.cleanupCache();
		this.stabilityCache.cleanupCache();
		this.phCache.cleanupCache();
		this.drainageCache.cleanupCache();
		while(worldTempCache.size() > 51000)
		{
			trimTempCache();
		}
	}

	public float getTemp(int x, int z, int totalHours)
	{
		String key = x+","+z+","+totalHours;
		if(worldTempCache != null && worldTempCache.containsKey(key))
		{
			return worldTempCache.get(key);
		}
		return Float.MIN_VALUE;
	}

	public void addTemp(int x, int z, int totalHours, float temp)
	{
		String key = x+","+z+","+totalHours;
		if(worldTempCache != null)
			worldTempCache.put(key, temp);
		trimTempCache();
	}

	private void trimTempCache()
	{
		if(worldTempCache.size() > 50000)
		{
			Iterator iter = worldTempCache.keySet().iterator();
			if(iter.hasNext())
				worldTempCache.remove(iter.next());
		}
	}

	public DataLayer getDataLayerAt(DataCache cache, GenLayerTFC indexLayers, int par1, int par2, int index)
	{
		return cache.getDataLayerAt(indexLayers, par1, par2);
	}

	/*private DataLayer[] loadDataLayerGeneratorData(DataCache[] cache, DataLayer[] layers, GenLayerTFC[] indexLayers, int par2, int par3, int par4, int par5, int layer)
	{
		return this.getDataLayerAt(cache, layers, indexLayers, par2, par3, par4, par5, true, layer);
	}*/

	public DataLayer[] getDataLayerAt(DataCache[] cache, DataLayer[] layers, GenLayerTFC[] indexLayers, int x, int y, int width, int height, boolean par6, int layer)
	{
		if (layers == null || layers.length < width * height)
			layers = new DataLayer[width * height];

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
			layers = new DataLayer[width * height];

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

	public DataLayer getTreeLayerAt(int x, int z, int index)
	{
		return this.treeCache[index].getDataLayerAt(treesIndexLayer[index], x, z);
	}

	/**
	 * Returns biomes to use for the blocks and loads the other data like temperature and humidity onto the
	 * WorldChunkManager Args: oldBiomeList, x, z, width, depth
	 */
	public DataLayer[] loadTreeLayerGeneratorData(DataLayer[] layers, int x, int y, int width, int height, int layer)
	{
		return this.getDataLayerAt(treeCache, layers, treesIndexLayer, x, y, width, height, true, 0);
	}

	public DataLayer getEVTLayerAt(int x, int z)
	{
		return this.evtCache.getDataLayerAt(evtIndexLayer, x, z);
	}

	/**
	 * Returns evt map to use for the blocks
	 * Args: layers, x, z, width, depth
	 */
	public DataLayer[] loadEVTLayerGeneratorData(DataLayer[] layers, int x, int y, int width, int height)
	{
		return this.getDataLayerAt(evtCache, layers, evtIndexLayer, x, y, width, height, true, 0);
	}

	public DataLayer getRainfallLayerAt(int x, int z)
	{
		return this.rainfallCache.getDataLayerAt(rainfallIndexLayer, x, z);
	}

	/**
	 * Returns rainfall map Args: layers, x, z, width, depth
	 */
	public DataLayer[] loadRainfallLayerGeneratorData(DataLayer[] layers, int x, int y, int width, int height)
	{
		return this.getDataLayerAt(rainfallCache, layers, rainfallIndexLayer, x, y, width, height, true, 0);
	}

	public DataLayer getStabilityLayerAt(int x, int z)
	{
		return this.stabilityCache.getDataLayerAt(stabilityIndexLayer, x, z);
	}

	public DataLayer[] loadStabilityLayerGeneratorData(DataLayer[] layers, int x, int y, int width, int height)
	{
		return this.getDataLayerAt(stabilityCache, layers, stabilityIndexLayer, x, y, width, height, true, 0);
	}

	public DataLayer getPHLayerAt(int x, int z)
	{
		DataLayer dl = this.phCache.getDataLayerAt(phIndexLayer, x, z);
		return dl != null ? dl : DataLayer.PH_Neutral;
	}

	public DataLayer[] loadPHLayerGeneratorData(DataLayer[] layers, int x, int y, int width, int height)
	{
		return this.getDataLayerAt(phCache, layers, phIndexLayer, x, y, width, height, true, 0);
	}

	public DataLayer getDrainageLayerAt(int x, int z)
	{
		return this.drainageCache.getDataLayerAt(drainageIndexLayer, x, z);
	}

	public DataLayer[] loadDrainageLayerGeneratorData(DataLayer[] layers, int x, int y, int width, int height)
	{
		return this.getDataLayerAt(drainageCache, layers, drainageIndexLayer, x, y, width, height, true, 0);
	}
}
