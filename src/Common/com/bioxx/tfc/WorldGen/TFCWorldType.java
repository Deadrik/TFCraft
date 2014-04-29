package com.bioxx.tfc.WorldGen;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.layer.GenLayer;

import com.bioxx.tfc.WorldGen.GenLayers.GenLayerBiomeEdgeTFC;
import com.bioxx.tfc.WorldGen.GenLayers.GenLayerBiomeTFC;
import com.bioxx.tfc.WorldGen.GenLayers.GenLayerTFC;
import com.bioxx.tfc.WorldGen.GenLayers.GenLayerZoomTFC;

public class TFCWorldType extends WorldType
{
	public static TFCWorldType DEFAULT;
	public static TFCWorldType FLAT;

	//	private static final  BiomeGenBase[] BiomeGenBases = new BiomeGenBase[] {
	//		BiomeGenBase.HighHills, BiomeGenBase.swampland, BiomeGenBase.plains,
	//		BiomeGenBase.plains, BiomeGenBase.rollingHills, BiomeGenBase.Mountains };
	private static final BiomeGenBase[] biomesUNKNOWN = new BiomeGenBase[] {
		BiomeGenBase.ocean
	};
	private static final BiomeGenBase[] biomesFLAT = new BiomeGenBase[] {
		BiomeGenBase.hell
	};
	private static final BiomeGenBase[] biomesDEFAULT = new BiomeGenBase[] {

	};

	public TFCWorldType(String par2Str)
	{
		super(par2Str);
	}

	public BiomeGenBase[] getBiomesForWorldType()
	{
		if(this == TFCWorldType.DEFAULT)
			return biomesDEFAULT;
		else if(this == TFCWorldType.FLAT)
			return biomesFLAT;

		return biomesUNKNOWN;
		//return new BiomeGenBase[] {BiomeGenBase.HighHills};
	}

	@Override
	public WorldChunkManager getChunkManager(World world)
	{
		if (this == FLAT)
		{
			//			FlatGeneratorInfo var1 = FlatGeneratorInfo.createFlatGeneratorFromString(world.getWorldInfo().getGeneratorOptions());
			//			return new TFCWorldChunkManagerHell(BiomeGenBase.getBiome(var1.getBiome()), 0.5F, 0.5F);
			return new TFCWorldChunkManagerHell(BiomeGenBase.hell, 0.5F, 0.5F);
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
		return 145;
	}

	@Override
	public double getHorizon(World world)
	{
		return 144.0D;
	}

	/**
	 * Creates the GenLayerBiome used for generating the world
	 * 
	 * @param worldSeed The world seed
	 * @param parentLayer The parent layer to feed into any layer you return
	 * @return A GenLayer that will return ints representing the Biomes to be generated, see GenLayerBiome
	 */
	@Override
	public GenLayer getBiomeLayer(long worldSeed, GenLayer parentLayer)
	{
		GenLayerTFC ret = new GenLayerBiomeTFC(200L, (GenLayerTFC)parentLayer, this);
		ret = GenLayerZoomTFC.magnify(1000L, ret, 2);
		ret = new GenLayerBiomeEdgeTFC(1000L, ret);
		return ret;
	}

}
