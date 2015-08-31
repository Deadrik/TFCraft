package com.bioxx.tfc.WorldGen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class TFCWorldChunkManagerHell extends TFCWorldChunkManager
{
	/** The biome generator object. */
	private TFCBiome biomeGenerator;
	//private float hellTemperature;

	/** The rainfall in the world */
	private float rainfall;

	public TFCWorldChunkManagerHell(TFCBiome par1, float par2, float par3, World world)
	{
		this.biomeGenerator = par1;
		//this.hellTemperature = par2;
		this.rainfall = par3;
		this.worldObj = world;
		this.biomesToSpawnIn = new ArrayList<BiomeGenBase>();
		this.biomesToSpawnIn.add(TFCBiome.HELL);
	}

	/**
	 * Gets the list of valid biomes for the player to spawn in.
	 */
	@Override
	public List<BiomeGenBase> getBiomesToSpawnIn()
	{
		return this.biomesToSpawnIn;
	}

	/**
	 * Returns the BiomeGenBase related to the x, z position on the world.
	 */
	@Override
	public TFCBiome getBiomeGenAt(int par1, int par2)
	{
		return this.biomeGenerator;
	}

	/**
	 * Returns an array of biomes for the location input.
	 */
	@Override
	public TFCBiome[] getBiomesForGeneration(BiomeGenBase[] par1, int par2, int par3, int par4, int par5)
	{
		if (par1 == null || par1.length < par4 * par5)
			par1 = new TFCBiome[par4 * par5];
		Arrays.fill(par1, 0, par4 * par5, this.biomeGenerator);
		return (TFCBiome[]) par1;
	}

	/**
	 * Returns a list of rainfall values for the specified blocks. Args: listToReuse, x, z, width, length.
	 */
	@Override
	public float[] getRainfall(float[] par1ArrayOfFloat, int par2, int par3, int par4, int par5)
	{
		if (par1ArrayOfFloat == null || par1ArrayOfFloat.length < par4 * par5)
			par1ArrayOfFloat = new float[par4 * par5];
		Arrays.fill(par1ArrayOfFloat, 0, par4 * par5, this.rainfall);
		return par1ArrayOfFloat;
	}

	/**
	 * Returns biomes to use for the blocks and loads the other data like temperature and humidity onto the
	 * WorldChunkManager Args: oldBiomeList, x, z, width, depth
	 */
	@Override
	public TFCBiome[] loadBlockGeneratorData(BiomeGenBase[] par1, int par2, int par3, int par4, int par5)
	{
		if (par1 == null || par1.length < par4 * par5)
			par1 = new TFCBiome[par4 * par5];
		Arrays.fill(par1, 0, par4 * par5, this.biomeGenerator);
		return (TFCBiome[]) par1;
	}

	/**
	 * Return a list of biomes for the specified blocks. Args: listToReuse, x, y, width, length, cacheFlag (if false,
	 * don't check biomeCache to avoid infinite loop in BiomeCacheBlock)
	 */
	@Override
	public TFCBiome[] getBiomeGenAt(BiomeGenBase[] par1, int par2, int par3, int par4, int par5, boolean par6)
	{
		return this.loadBlockGeneratorData(par1, par2, par3, par4, par5);
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
