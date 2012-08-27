package TFC.WorldGen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    /** A GenLayer containing the indices into BiomeGenBase.biomeList[] */
    private GenLayerTFC biomeIndexLayer;

    /** The BiomeCache object for this world. */
    private BiomeCache biomeCache;

    /** A list of biomes that the player can spawn in. */
    private List biomesToSpawnIn;
    
    private GenLayerTFC[] genRocks;

    /** A GenLayer containing the indices into BiomeGenBase.biomeList[] */
    private GenLayerTFC[] rocksIndexLayer;
    
    /** The BiomeCache object for this world. */
    private DataCache[] rockCache;
    
    private GenLayerTFC genTrees;

    /** A GenLayer containing the indices into BiomeGenBase.biomeList[] */
    private GenLayerTFC treesIndexLayer;
    
    private DataCache treeCache;
    
	protected TFCWorldChunkManager()
    {
		super();
		this.biomeCache = new BiomeCache(this);
		rockCache = new DataCache[3];
		rockCache[0] = new DataCache(this,0);
		rockCache[1] = new DataCache(this,1);
		rockCache[2] = new DataCache(this,2);
		treeCache = new DataCache(this,0);
        this.biomesToSpawnIn = new ArrayList();
        this.biomesToSpawnIn.add(TFCBiome.forest);
        this.biomesToSpawnIn.add(TFCBiome.forest2);
        this.biomesToSpawnIn.add(TFCBiome.forest3);
        this.biomesToSpawnIn.add(TFCBiome.forest4);
        this.biomesToSpawnIn.add(TFCBiome.forest5);
        this.biomesToSpawnIn.add(TFCBiome.forest6);
        this.biomesToSpawnIn.add(TFCBiome.forest7);
        this.biomesToSpawnIn.add(TFCBiome.forest8);
        this.biomesToSpawnIn.add(TFCBiome.forest9);
        this.biomesToSpawnIn.add(TFCBiome.extremeHills);
        this.biomesToSpawnIn.add(TFCBiome.hills2);
        this.biomesToSpawnIn.add(TFCBiome.hills3);
        this.biomesToSpawnIn.add(TFCBiome.hills4);
        this.biomesToSpawnIn.add(TFCBiome.hills5);
        this.biomesToSpawnIn.add(TFCBiome.hills6);
        this.biomesToSpawnIn.add(TFCBiome.hills7);
        this.biomesToSpawnIn.add(TFCBiome.hills8);
        this.biomesToSpawnIn.add(TFCBiome.hills9);
        this.biomesToSpawnIn.add(TFCBiome.hills10);
        
    }

    public TFCWorldChunkManager(long par1, WorldType par3WorldType)
    {
        this();
        GenLayerTFC[] var4 = GenLayerTFC.initializeAllBiomeGenerators(par1, par3WorldType);
        this.genBiomes = var4[0];
        this.biomeIndexLayer = var4[1];
        
        GenLayerTFC[] var5 = GenRockLayerTFC.initializeAllBiomeGenerators(par1+1, par3WorldType);
        GenLayerTFC[] var6 = GenRockLayerTFC.initializeAllBiomeGenerators(par1+2, par3WorldType);
        GenLayerTFC[] var7 = GenRockLayerTFC.initializeAllBiomeGenerators(par1+3, par3WorldType);
        
        GenLayerTFC[] var8 = GenTreeLayerTFC.initializeAllBiomeGenerators(par1+4, par3WorldType);

        genRocks = new GenLayerTFC[3];
        rocksIndexLayer = new GenLayerTFC[3];
        
        genTrees = var8[0];
        treesIndexLayer = var8[1];
        
        this.genRocks[0] = var5[0];
        this.rocksIndexLayer[0] = var5[1];
        
        this.genRocks[1] = var6[0];
        this.rocksIndexLayer[1] = var6[1];
        
        this.genRocks[2] = var7[0];
        this.rocksIndexLayer[2] = var7[1];
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

        int[] var6 = this.biomeIndexLayer.getInts(par2, par3, par4, par5);

        for (int var7 = 0; var7 < par4 * par5; ++var7)
        {
            float var8 = (float)((TFCBiome)TFCBiome.biomeList[var6[var7]]).getFloatRain();

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
    public float[] getTemperatures(float[] par1ArrayOfFloat, int par2, int par3, int par4, int par5)
    {
        IntCache.resetIntCache();

        if (par1ArrayOfFloat == null || par1ArrayOfFloat.length < par4 * par5)
        {
            par1ArrayOfFloat = new float[par4 * par5];
        }

        int[] var6 = this.biomeIndexLayer.getInts(par2, par3, par4, par5);

        for (int var7 = 0; var7 < par4 * par5; ++var7)
        {
            float var8 = (float)((TFCBiome)TFCBiome.biomeList[var6[var7]]).getFloatTemp();

            if (var8 > 1.0F)
            {
                var8 = 1.0F;
            }

            par1ArrayOfFloat[var7] = var8;
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
            BiomeGenBase var18 = BiomeGenBase.biomeList[var12[var15]];

            if (par4List.contains(var18) && (var13 == null || par5Random.nextInt(var14 + 1) == 0))
            {
                var13 = new ChunkPosition(var16, 0, var17);
                ++var14;
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
    
    public DataLayer getTreeLayerAt(int par1, int par2)
    {
        return this.treeCache.getDataLayerAt(treesIndexLayer, par1, par2);
    }

    /**
     * Returns biomes to use for the blocks and loads the other data like temperature and humidity onto the
     * WorldChunkManager Args: oldBiomeList, x, z, width, depth
     */
    public DataLayer[] loadTreeLayerGeneratorData(DataLayer[] layers, int par2, int par3, int par4, int par5)
    {    	
        return this.getDataLayerAt(treeCache, layers, treesIndexLayer, par2, par3, par4, par5, true, 0);
    }

}
