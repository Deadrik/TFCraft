package com.bioxx.tfc.WorldGen;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;

import com.bioxx.tfc.api.Constant.Global;

public class TFCWorldType extends WorldType
{
	public static TFCWorldType DEFAULT;
	public static TFCWorldType FLAT;

	//	private static final  BiomeGenBase[] tfcBiomes = new BiomeGenBase[] {
	//		TFCBiome.HighHills, TFCBiome.swampland, TFCBiome.plains,
	//		TFCBiome.plains, TFCBiome.rollingHills, TFCBiome.Mountains };
	private static final TFCBiome[] biomesUNKNOWN = new TFCBiome[] {
		TFCBiome.ocean
	};
	private static final TFCBiome[] biomesFLAT = new TFCBiome[] {
		TFCBiome.hell
	};
	private static final TFCBiome[] biomesDEFAULT = new TFCBiome[] {
		TFCBiome.ocean,
		TFCBiome.HighHills,
		TFCBiome.plains,
		TFCBiome.HighPlains,
		TFCBiome.swampland,
		TFCBiome.rollingHills,
		TFCBiome.Mountains,
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
		if(this == TFCWorldType.DEFAULT)
			return biomesDEFAULT;
		else if(this == TFCWorldType.FLAT)
			return biomesFLAT;

		return biomesUNKNOWN;
		//return new TFCBiome[] {TFCBiome.HighHills};
	}

	@Override
	public WorldChunkManager getChunkManager(World world)
	{
		if (this == FLAT)
		{
			//			FlatGeneratorInfo var1 = FlatGeneratorInfo.createFlatGeneratorFromString(world.getWorldInfo().getGeneratorOptions());
			//			return new TFCWorldChunkManagerHell(BiomeGenBase.getBiome(var1.getBiome()), 0.5F, 0.5F);
			return new TFCWorldChunkManagerHell(TFCBiome.hell, 0.5F, 0.5F, world);
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

    /**
     * Should world creation GUI show 'Customize' button for this world type?
     * @return if this world type has customization parameters
     */
	@Override
	public boolean isCustomizable() {
		return false;
	}
}
