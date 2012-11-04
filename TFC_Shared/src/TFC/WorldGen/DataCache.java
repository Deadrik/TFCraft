package TFC.WorldGen;

import java.util.ArrayList;
import java.util.List;

import TFC.*;
import TFC.WorldGen.GenLayers.GenLayerTFC;

import net.minecraft.src.LongHashMap;

public class DataCache
{
    /** Reference to the WorldChunkManager */
    private final TFCWorldChunkManager chunkManager;

    /** The last time this BiomeCache was cleaned, in milliseconds. */
    private long lastCleanupTime = 0L;

    int index;
    /**
     * The map of keys to BiomeCacheBlocks. Keys are based on the chunk x, z coordinates as (x | z << 32).
     */
    private LongHashMap cacheMap = new LongHashMap();

    /** The list of cached BiomeCacheBlocks */
    private List cache = new ArrayList();

    public DataCache(TFCWorldChunkManager par1WorldChunkManager, int ind)
    {
        this.chunkManager = par1WorldChunkManager;
        index = ind;
    }

    /**
     * Returns a biome cache block at location specified.
     */
    public DataCacheBlockTFC getDataCacheBlock(GenLayerTFC indexLayers, int x, int y)
    {
        x >>= 4;
        y >>= 4;
        
        long var3 = (long)x & 4294967295L | ((long)y & 4294967295L) << 32;
        DataCacheBlockTFC var5 = (DataCacheBlockTFC)this.cacheMap.getValueByKey(var3);

        if (var5 == null)
        {
            var5 = new DataCacheBlockTFC(this,indexLayers, x, y, index);
            this.cacheMap.add(var3, var5);
            this.cache.add(var5);
        }

        var5.lastAccessTime = System.currentTimeMillis();
        return var5;
    }

    /**
     * Returns the BiomeGenBase related to the x, z position from the cache.
     */
    public DataLayer getDataLayerAt(GenLayerTFC indexLayers, int par1, int par2)
    {
        return this.getDataCacheBlock(indexLayers, par1, par2).getDataLayerAt(par1, par2);
    }

    public void cleanupCache()
    {
        long var1 = System.currentTimeMillis();
        long var3 = var1 - this.lastCleanupTime;

        if (var3 > 7500L || var3 < 0L)
        {
            this.lastCleanupTime = var1;

            for (int var5 = 0; var5 < this.cache.size(); ++var5)
            {
                DataCacheBlockTFC var6 = (DataCacheBlockTFC)this.cache.get(var5);
                long var7 = var1 - var6.lastAccessTime;

                if (var7 > 30000L || var7 < 0L)
                {
                    this.cache.remove(var5--);
                    long var9 = (long)var6.xPosition & 4294967295L | ((long)var6.zPosition & 4294967295L) << 32;
                    this.cacheMap.remove(var9);
                }
            }
        }
    }

    /**
     * Returns the array of cached biome types in the BiomeCacheBlock at the given location.
     * @param indexLayers 
     * @param cache2 
     */
    public DataLayer[] getCachedData( GenLayerTFC indexLayers, int par1, int par2)
    {
        return this.getDataCacheBlock(indexLayers, par1, par2).data;
    }

    /**
     * Get the world chunk manager object for a biome list.
     */
    static TFCWorldChunkManager getChunkManager(DataCache par0BiomeCache)
    {
        return par0BiomeCache.chunkManager;
    }
}
