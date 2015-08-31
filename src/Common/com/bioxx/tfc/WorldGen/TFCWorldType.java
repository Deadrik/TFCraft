package com.bioxx.tfc.WorldGen;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;

import com.bioxx.tfc.api.Constant.Global;

public class TFCWorldType extends WorldType
{
	public static TFCWorldType defaultWorldType;
	public static TFCWorldType flatWorldType;

	//	private static final  BiomeGenBase[] tfcBiomes = new BiomeGenBase[] {
	//		TFCBiome.HighHills, TFCBiome.swampland, TFCBiome.plains,
	//		TFCBiome.plains, TFCBiome.rollingHills, TFCBiome.Mountains };
	private static final TFCBiome[] BIOMES_UNKNOWN = new TFCBiome[] {
		TFCBiome.OCEAN
	};
	private static final TFCBiome[] BIOMES_FLAT = new TFCBiome[] {
		TFCBiome.HELL
	};
	private static final TFCBiome[] BIOMES_DEFAULT = new TFCBiome[] {
		TFCBiome.OCEAN,
		TFCBiome.HIGH_HILLS,
		TFCBiome.PLAINS,
		TFCBiome.HIGH_PLAINS,
		TFCBiome.SWAMPLAND,
		TFCBiome.ROLLING_HILLS,
		TFCBiome.MOUNTAINS,
	};

	public TFCWorldType(int i, String par2Str)
	{
		super(i, par2Str);
	}

	public TFCWorldType(String par2Str)
	{
		super(par2Str);
	}

	public TFCBiome[] getBiomesForWorldType()
	{
		if(this == TFCWorldType.defaultWorldType)
			return BIOMES_DEFAULT.clone();
		else if(this == TFCWorldType.flatWorldType)
			return BIOMES_FLAT.clone();

		return BIOMES_UNKNOWN.clone();
		//return new TFCBiome[] {TFCBiome.HighHills};
	}

	@Override
	public WorldChunkManager getChunkManager(World world)
	{
		if (this == flatWorldType)
		{
			//			FlatGeneratorInfo var1 = FlatGeneratorInfo.createFlatGeneratorFromString(world.getWorldInfo().getGeneratorOptions());
			//			return new TFCWorldChunkManagerHell(BiomeGenBase.getBiome(var1.getBiome()), 0.5F, 0.5F);
			return new TFCWorldChunkManagerHell(TFCBiome.HELL, 0.5F, 0.5F, world);
		}
		else
		{
			return new TFCWorldChunkManager(world);
		}
	}

	@Override
	public IChunkProvider getChunkGenerator(World world, String generatorOptions)
	{
		return new TFCChunkProviderGenerate(world, world.getSeed(), world.getWorldInfo().isMapFeaturesEnabled());
	}

	@Override
	public int getMinimumSpawnHeight(World world)
	{
		return Global.SEALEVEL+1;
	}

	@Override
	public double getHorizon(World world)
	{
		return Global.SEALEVEL;
	}

}
