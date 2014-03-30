package TFC.WorldGen;

import TFC.WorldGen.GenLayers.GenLayerTFC;

public class DataCacheBlockTFC
{
	public TFCBiome[] biomes = new TFCBiome[256];
	/** An array of chunk rainfall values saved by this cache. */
	public float[] rainfallValues = new float[256];
	/** The array of data types stored in this DataCacheBlockTFC. */
	public DataLayer[] data;
	/** The x coordinate of the DataCacheBlockTFC. */
	public int xPosition;
	/** The z coordinate of the DataCacheBlockTFC. */
	public int zPosition;
	/** The last time this DataCacheBlockTFC was accessed, in milliseconds. */
	public long lastAccessTime;
	/** The DataCache object that contains this DataCacheBlockTFC */
	final DataCache theDataCache;

	private int index;
	
	public DataCacheBlockTFC(DataCache datacache, GenLayerTFC indexLayers, int par2, int par3, int ind)
	{
		this.theDataCache = datacache;
		this.data = new DataLayer[256];
		this.xPosition = par2;
		this.zPosition = par3;
		index = ind;
		DataCache.getChunkManager(datacache).getRainfall(rainfallValues, par2 << 4, par3 << 4, 16, 16);
		DataCache.getChunkManager(datacache).getDataLayerAt(datacache, data, indexLayers, par2 << 4, par3 << 4, 16, 16, false, index);
	}

	public DataCacheBlockTFC(DataCache datacache, int par2, int par3)
	{
		this.theDataCache = null;
		this.xPosition = par2;
		this.zPosition = par3;
		DataCache.getChunkManager(datacache).getRainfall(this.rainfallValues, par2 << 4, par3 << 4, 16, 16);
		DataCache.getChunkManager(datacache).getBiomeGenAt(this.biomes, par2 << 4, par3 << 4, 16, 16, false);
	}

	/**
	 * Returns the BiomeGenBase related to the x, z position from the cache block.
	 */
	public TFCBiome getBiomeGenAt(int par1, int par2)
	{
		return this.biomes[par1 & 15 | (par2 & 15) << 4];
	}

	/**
	 * Returns the Datalayer related to the x, z position from the cache block.
	 */
	public DataLayer getDataLayerAt(int par1, int par2)
	{
		return this.data[par1 & 15 | (par2 & 15) << 4];
	}
}
