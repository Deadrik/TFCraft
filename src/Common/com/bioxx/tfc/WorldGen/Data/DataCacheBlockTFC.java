package com.bioxx.tfc.WorldGen.Data;

import com.bioxx.tfc.WorldGen.DataLayer;
import com.bioxx.tfc.WorldGen.GenLayers.GenLayerTFC;

public class DataCacheBlockTFC
{
	/** The array of data types stored in this DataCacheBlockTFC. */
	public DataLayer[] data;
	/** The x coordinate of the DataCacheBlockTFC. */
	public int xPosition;
	/** The z coordinate of the DataCacheBlockTFC. */
	public int zPosition;
	/** The last time this DataCacheBlockTFC was accessed, in milliseconds. */
	public long lastAccessTime;
	/** The DataCache object that contains this DataCacheBlockTFC */
	//private final DataCache theDataCache;

	private int index;

	public DataCacheBlockTFC(DataCache datacache, GenLayerTFC indexLayers, int par2, int par3, int ind)
	{
		//this.theDataCache = datacache;
		this.data = new DataLayer[256];
		this.xPosition = par2;
		this.zPosition = par3;
		index = ind;
		DataCache.getChunkManager(datacache).getDataLayerAt(datacache, data, indexLayers, par2 << 4, par3 << 4, 16, 16, false, index);
	}

	public DataCacheBlockTFC(DataCache datacache, int par2, int par3)
	{
		//this.theDataCache = null;
		this.xPosition = par2;
		this.zPosition = par3;
	}

	/**
	 * Returns the Datalayer related to the x, z position from the cache block.
	 */
	public DataLayer getDataLayerAt(int par1, int par2)
	{
		return this.data[par1 & 15 | (par2 & 15) << 4];
	}
}
