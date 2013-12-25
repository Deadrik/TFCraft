package TFC.WorldGen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import TFC.Core.TFC_Climate;
import TFC.WorldGen.GenLayers.GenEVTLayerTFC;
import TFC.WorldGen.GenLayers.GenLayerTFC;
import TFC.WorldGen.GenLayers.GenRainLayerTFC;
import TFC.WorldGen.GenLayers.GenRockLayer2TFC;
import TFC.WorldGen.GenLayers.GenTreeLayerTFC;

import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.layer.*;

public class TFCWorldChunkManagerHell extends TFCWorldChunkManager 
{
	/** The biome generator object. */
    private BiomeGenBase biomeGenerator;
    private float hellTemperature;

    /** The rainfall in the world */
    private float rainfall;
    
	public TFCWorldChunkManagerHell(BiomeGenBase par1BiomeGenBase, float par2, float par3)
	{
		this.biomeGenerator = par1BiomeGenBase;
        this.hellTemperature = par2;
        this.rainfall = par3;
        
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
		
		//Setup Rocks
				GenLayerTFC[] var5 = GenRockLayer2TFC.initializeAllBiomeGenerators(seed+1, TFCWorldType.DEFAULT);
				GenLayerTFC[] var6 = GenRockLayer2TFC.initializeAllBiomeGenerators(seed+2, TFCWorldType.DEFAULT);
				GenLayerTFC[] var7 = GenRockLayer2TFC.initializeAllBiomeGenerators(seed+3, TFCWorldType.DEFAULT);
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
				
				GenLayerTFC[] var8 = GenTreeLayerTFC.initializeAllBiomeGenerators(seed+4, TFCWorldType.DEFAULT);
				genTrees[0] = var8[0];
				treesIndexLayer[0] = var8[1];
				
				var8 = GenTreeLayerTFC.initializeAllBiomeGenerators(seed+5, TFCWorldType.DEFAULT);
				genTrees[1] = var8[0];
				treesIndexLayer[1] = var8[1];
				
				var8 = GenTreeLayerTFC.initializeAllBiomeGenerators(seed+6, TFCWorldType.DEFAULT);
				genTrees[2] = var8[0];
				treesIndexLayer[2] = var8[1];

				//Setup Evapotranspiration
				var8 = GenEVTLayerTFC.initializeAllBiomeGenerators(seed+7, TFCWorldType.DEFAULT);
				genEVT = var8[0];
				evtIndexLayer = var8[1];

				//Setup Rainfall
				var8 = GenRainLayerTFC.initializeAllBiomeGenerators(seed+8, TFCWorldType.DEFAULT);
				genRainfall = var8[0];
				rainfallIndexLayer = var8[1];
	}
	
	/**
     * Returns the BiomeGenBase related to the x, z position on the world.
     */
	@Override
    public BiomeGenBase getBiomeGenAt(int par1, int par2)
    {
        return this.biomeGenerator;
    }

    /**
     * Returns an array of biomes for the location input.
     */
    @Override
    public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] par1ArrayOfBiomeGenBase, int par2, int par3, int par4, int par5)
    {
        if (par1ArrayOfBiomeGenBase == null || par1ArrayOfBiomeGenBase.length < par4 * par5)
        {
            par1ArrayOfBiomeGenBase = new BiomeGenBase[par4 * par5];
        }

        Arrays.fill(par1ArrayOfBiomeGenBase, 0, par4 * par5, this.biomeGenerator);
        return par1ArrayOfBiomeGenBase;
    }

    /**
     * Returns a list of temperatures to use for the specified blocks.  Args: listToReuse, x, y, width, length
     */
    @Override
    public float[] getTemperatures(float[] par1ArrayOfFloat, int par2, int par3, int par4, int par5)
    {
        if (par1ArrayOfFloat == null || par1ArrayOfFloat.length < par4 * par5)
        {
            par1ArrayOfFloat = new float[par4 * par5];
        }

        Arrays.fill(par1ArrayOfFloat, 0, par4 * par5, this.hellTemperature);
        return par1ArrayOfFloat;
    }

    /**
     * Returns a list of rainfall values for the specified blocks. Args: listToReuse, x, z, width, length.
     */
    @Override
    public float[] getRainfall(float[] par1ArrayOfFloat, int par2, int par3, int par4, int par5)
    {
        if (par1ArrayOfFloat == null || par1ArrayOfFloat.length < par4 * par5)
        {
            par1ArrayOfFloat = new float[par4 * par5];
        }

        Arrays.fill(par1ArrayOfFloat, 0, par4 * par5, this.rainfall);
        return par1ArrayOfFloat;
    }

    /**
     * Returns biomes to use for the blocks and loads the other data like temperature and humidity onto the
     * WorldChunkManager Args: oldBiomeList, x, z, width, depth
     */
    @Override
    public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase[] par1ArrayOfBiomeGenBase, int par2, int par3, int par4, int par5)
    {
        if (par1ArrayOfBiomeGenBase == null || par1ArrayOfBiomeGenBase.length < par4 * par5)
        {
            par1ArrayOfBiomeGenBase = new BiomeGenBase[par4 * par5];
        }

        Arrays.fill(par1ArrayOfBiomeGenBase, 0, par4 * par5, this.biomeGenerator);
        return par1ArrayOfBiomeGenBase;
    }

    /**
     * Return a list of biomes for the specified blocks. Args: listToReuse, x, y, width, length, cacheFlag (if false,
     * don't check biomeCache to avoid infinite loop in BiomeCacheBlock)
     */
    @Override
    public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] par1ArrayOfBiomeGenBase, int par2, int par3, int par4, int par5, boolean par6)
    {
        return this.loadBlockGeneratorData(par1ArrayOfBiomeGenBase, par2, par3, par4, par5);
    }

    /**
     * Finds a valid position within a range, that is in one of the listed biomes. Searches {par1,par2} +-par3 blocks.
     * Strongly favors positive y positions.
     */
    @Override
    public ChunkPosition findBiomePosition(int par1, int par2, int par3, List par4List, Random par5Random)
    {
        return par4List.contains(this.biomeGenerator) ? new ChunkPosition(par1 - par3 + par5Random.nextInt(par3 * 2 + 1), 0, par2 - par3 + par5Random.nextInt(par3 * 2 + 1)) : null;
    }

    /**
     * checks given Chunk's Biomes against List of allowed ones
     */
    @Override
    public boolean areBiomesViable(int par1, int par2, int par3, List par4List)
    {
        return par4List.contains(this.biomeGenerator);
    }
}
