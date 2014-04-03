package TFC.WorldGen;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.FlatGeneratorInfo;

public class TFCWorldType extends WorldType
{
	public static TFCWorldType DEFAULT;
	public static TFCWorldType FLAT;

	private static final  BiomeGenBase[] tfcBiomes = new BiomeGenBase[] {
		TFCBiome.HighHills, TFCBiome.swampland, TFCBiome.plains,
		TFCBiome.plains, TFCBiome.rollingHills, TFCBiome.Mountains, TFCBiome.HighPlains };

	public TFCWorldType(int par1, String par2Str, int par3) 
	{
		super(par1, par2Str, par3);
	}

	public TFCWorldType(int par1, String par2Str) 
	{
		super(par1, par2Str);
	}

	@Override
	public BiomeGenBase[] getBiomesForWorldType() {
		return tfcBiomes;
	}

	@Override
	public WorldChunkManager getChunkManager(World world)
	{
		if (this == FLAT)
		{
			FlatGeneratorInfo var1 = FlatGeneratorInfo.createFlatGeneratorFromString(world.getWorldInfo().getGeneratorOptions());
			return new TFCWorldChunkManagerHell(BiomeGenBase.biomeList[var1.getBiome()], 0.5F, 0.5F);
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

}
