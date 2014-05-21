package com.bioxx.tfc.WorldGen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.gen.layer.IntCache;

import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.WorldGen.GenLayers.GenLayerTFC;
import com.bioxx.tfc.WorldGen.GenLayers.DataLayers.EVT.GenEVTLayer;
import com.bioxx.tfc.WorldGen.GenLayers.DataLayers.Rain.GenRainLayerTFC;
import com.bioxx.tfc.WorldGen.GenLayers.DataLayers.Rock.GenRockLayer;
import com.bioxx.tfc.WorldGen.GenLayers.DataLayers.Stability.GenStabilityLayer;
import com.bioxx.tfc.WorldGen.GenLayers.DataLayers.Tree.GenTreeLayer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TFCWorldChunkManager extends WorldChunkManager
{
	protected GenLayerTFC genBiomes;
	protected GenLayerTFC biomeIndexLayer;

	/** The BiomeCache object for this world. */
	protected DataCache biomeCache;

	/** A list of biomes that the player can spawn in. */
	protected List biomesToSpawnIn;

	//Rocks
	protected GenLayerTFC[] rocksIndexLayer;
	protected DataCache[] rockCache;

	//Trees
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

	//Stability
	protected GenLayerTFC genStability;
	protected GenLayerTFC stabilityIndexLayer;
	protected DataCache stabilityCache;

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

	public static DataLayer[] treeArray = new DataLayer[] {DataLayer.Ash, DataLayer.Aspen, DataLayer.Birch, DataLayer.Chestnut, DataLayer.DouglasFir, DataLayer.Hickory,DataLayer.Koa, DataLayer.Maple, DataLayer.Oak, DataLayer.Pine, DataLayer.Redwood, 
		DataLayer.Pine, DataLayer.Spruce, DataLayer.Sycamore, DataLayer.WhiteCedar, DataLayer.WhiteElm, DataLayer.Willow, DataLayer.NoTree};

	public TFCWorldChunkManager()
	{
		super();
		this.biomeCache = new DataCache(this);
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

		this.biomesToSpawnIn = new ArrayList();
		this.biomesToSpawnIn.add(TFCBiome.HighHills);
		this.biomesToSpawnIn.add(TFCBiome.plains);
		this.biomesToSpawnIn.add(TFCBiome.rollingHills);
		this.biomesToSpawnIn.add(TFCBiome.swampland);
		this.biomesToSpawnIn.add(TFCBiome.Mountains);
		this.biomesToSpawnIn.add(TFCBiome.HighPlains);
	}

	public TFCWorldChunkManager(World world)
	{
		this(world.getSeed(), world.getWorldInfo().getTerrainType());
	}

	public TFCWorldChunkManager(long Seed, WorldType worldtype)
	{
		this();
		seed = Seed;
		//System.out.println("================= "+worldtype.getWorldTypeName()+" =================");
		// Making sure that only TFC World Types can be used
		GenLayerTFC[] var4;
		if(worldtype == TFCWorldType.FLAT)
			var4 = GenLayerTFC.initialize(Seed, TFCWorldType.FLAT);
		else
			var4 = GenLayerTFC.initialize(Seed, TFCWorldType.DEFAULT);

		this.biomeIndexLayer = var4[1];

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
	public TFCBiome getBiomeGenAt(int par1, int par2)
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

	@Override
	@SideOnly(Side.CLIENT)

	/**
	 * Return an adjusted version of a given temperature based on the y height
	 */
	public float getTemperatureAtHeight(float t, int y)
	{
		int x = (int)Math.floor(Minecraft.getMinecraft().thePlayer.posX);
		int z = (int)Math.floor(Minecraft.getMinecraft().thePlayer.posZ);
		float temp = TFC_Climate.getHeightAdjustedTemp(x, y, z);
		/*if(y > 180)
			temp -= temp * (y-180)/90;*/
		return temp;
	}

	/**
	 * Returns a list of temperatures to use for the specified blocks.  Args: listToReuse, x, y, width, length
	 */
	//@Override
	public float[] getTemperatures(float[] par1ArrayOfFloat, int x, int z, int par4, int par5)
	{
		//	IntCache.resetIntCache();

		if (par1ArrayOfFloat == null || par1ArrayOfFloat.length < par4 * par5)
			par1ArrayOfFloat = new float[par4 * par5];

		//        int[] var6 = this.biomeIndexLayer.getInts(x, z, par4, par5);
		//        var6 = TFC_Climate.getBioTemperature(z)

		for (int var7 = 0; var7 < par4 * par5; ++var7)
			par1ArrayOfFloat[var7] = TFC_Climate.getBioTemperature(x, z);

		return par1ArrayOfFloat;
	}

	/**
	 * Returns an array of biomes for the location input.
	 */
	@Override
	public TFCBiome[] getBiomesForGeneration(BiomeGenBase[] par1, int par2, int par3, int par4, int par5)
	{
		IntCache.resetIntCache();

		TFCBiome[] biome = (TFCBiome[]) par1;
		if (biome == null || biome.length < par4 * par5)
			biome = new TFCBiome[par4 * par5];

		int[] var6 = this.biomeIndexLayer.getInts(par2, par3, par4, par5);
		for (int var7 = 0; var7 < par4 * par5; ++var7)
		{
			int index = Math.max(var6[var7], 0);
			biome[var7] = TFCBiome.getBiome(index);
		}

		return biome;
	}

	/**
	 * Returns biomes to use for the blocks and loads the other data like temperature and humidity onto the
	 * WorldChunkManager Args: oldBiomeList, x, z, width, depth
	 */
	@Override
	public TFCBiome[] loadBlockGeneratorData(BiomeGenBase[] par1, int par2, int par3, int par4, int par5)
	{
		return this.getBiomeGenAt(par1, par2, par3, par4, par5, true);
	}

	/**
	 * Return a list of biomes for the specified blocks. Args: listToReuse, x, y, width, length, cacheFlag (if false,
	 * don't check biomeCache to avoid infinite loop in BiomeCacheBlock)
	 */
	@Override
	public TFCBiome[] getBiomeGenAt(BiomeGenBase[] par1, int par2, int par3, int par4, int par5, boolean par6)
	{
		IntCache.resetIntCache();

		TFCBiome[] biome = (TFCBiome[]) par1;
		if (biome == null || biome.length < par4 * par5)
			biome = new TFCBiome[par4 * par5];

		if (par6 && par4 == 16 && par5 == 16 && (par2 & 15) == 0 && (par3 & 15) == 0)
		{
			TFCBiome[] var9 = this.biomeCache.getCachedBiomes(par2, par3);
			System.arraycopy(var9, 0, biome, 0, par4 * par5);
			return biome;
		}
		else
		{
			int[] var7 = this.biomeIndexLayer.getInts(par2, par3, par4, par5);
			for (int var8 = 0; var8 < par4 * par5; ++var8)
			{
				int id = var7[var8] != -1 ? var7[var8] : 0;
				if(var7[var8] == -1)
					System.out.println("var7[var8] is " + var7[var8]);
				if(var8 == -1)
					System.out.println("var8 is " + var8);
				biome[var8] = TFCBiome.getBiome(id);
			}
			return biome;
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
		int[] var11 = this.biomeIndexLayer.getInts(var5, var6, var9, var10);

		for (int var12 = 0; var12 < var9 * var10; ++var12)
		{
			TFCBiome var13 = TFCBiome.getBiomeGenArray()[var11[var12]];
			if (!par4List.contains(var13))
				return false;
		}
		return true;
	}

	/**
	 * Finds a valid position within a range, that is in one of the listed biomes. Searches {par1,par2} +-par3 blocks.
	 * Strongly favors positive y positions.
	 */
	@Override
	public ChunkPosition findBiomePosition(int xCoord, int zCoord, int radius, List biomeList, Random rand)
	{
		IntCache.resetIntCache();
		int l = xCoord - radius >> 2;
		int i1 = zCoord - radius >> 2;
		int j1 = xCoord + radius >> 2;
		int k1 = zCoord + radius >> 2;
		int l1 = j1 - l + 1;
		int i2 = k1 - i1 + 1;
		int[] aint = this.biomeIndexLayer.getInts(l, i1, l1, i2);
		ChunkPosition chunkposition = null;
		int j2 = 0;

		for (int k2 = 0; k2 < l1 * i2; ++k2)
		{
			int l2 = l + k2 % l1 << 2;
			int i3 = i1 + k2 / l1 << 2;
			TFCBiome biomegenbase = TFCBiome.getBiome(aint[k2]);

			if (biomeList.contains(biomegenbase) && (chunkposition == null || rand.nextInt(j2 + 1) == 0))
			{
				chunkposition = new ChunkPosition(l2, 0, i3);
				++j2;
			}
		}

		return chunkposition;
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

	public DataLayer getStabilityLayerAt(int par1, int par2)
	{
		return this.stabilityCache.getDataLayerAt(stabilityIndexLayer, par1, par2);
	}

	public DataLayer[] loadStabilityLayerGeneratorData(DataLayer[] layers, int x, int y, int width, int height)
	{
		return this.getDataLayerAt(stabilityCache, layers, stabilityIndexLayer, x, y, width, height, true, 0);
	}

}
