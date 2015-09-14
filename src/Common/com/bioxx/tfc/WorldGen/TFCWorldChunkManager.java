package com.bioxx.tfc.WorldGen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.gen.layer.IntCache;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.WorldGen.GenLayers.GenLayerTFC;

public class TFCWorldChunkManager extends WorldChunkManager
{
	protected World worldObj;
	protected GenLayerTFC genBiomes;
	protected GenLayerTFC biomeIndexLayer;

	/** The BiomeCache object for this world. */
	protected BiomeCache biomeCache;

	/** A list of biomes that the player can spawn in. */
	protected List<BiomeGenBase> biomesToSpawnIn;

	public long seed;
	public TFCWorldChunkManager()
	{
		super();
		biomeCache = new BiomeCache(this);
		this.biomesToSpawnIn = new ArrayList<BiomeGenBase>();
		//this.biomesToSpawnIn.add(TFCBiome.beach);
		this.biomesToSpawnIn.add(BiomeGenBase.forest); // There is no TFCBiome.forest
		this.biomesToSpawnIn.add(TFCBiome.PLAINS);
		this.biomesToSpawnIn.add(TFCBiome.ROLLING_HILLS);
		this.biomesToSpawnIn.add(TFCBiome.SWAMPLAND);
		this.biomesToSpawnIn.add(TFCBiome.MOUNTAINS);
		this.biomesToSpawnIn.add(TFCBiome.HIGH_PLAINS);
	}

	public TFCWorldChunkManager(World world)
	{
		this(world.getSeed(), world.getWorldInfo().getTerrainType());
		worldObj = world;
	}

	public TFCWorldChunkManager(long genSeed, WorldType worldtype)
	{
		this();
		seed = genSeed;
		//TerraFirmaCraft.log.info("================= "+worldtype.getWorldTypeName()+" =================");
		// Making sure that only TFC World Types can be used
		GenLayerTFC[] var4;
		if(worldtype == TFCWorldType.flatWorldType)
			var4 = GenLayerTFC.initialize(genSeed, TFCWorldType.flatWorldType);
		else
			var4 = GenLayerTFC.initialize(genSeed, TFCWorldType.defaultWorldType);

		this.genBiomes = var4[0];
		this.biomeIndexLayer = var4[1];
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
	 * Returns a list of rainfall values for the specified blocks. Args: listToReuse, x, z, width, length.
	 */
	@Override
	public float[] getRainfall(float[] par1ArrayOfFloat, int par2, int par3, int par4, int par5)
	{
		if (TFC_Climate.getCacheManager(worldObj) != null)
			return TFC_Climate.getCacheManager(worldObj).getRainfall(par1ArrayOfFloat, par2, par3, par4, par5);
		return par1ArrayOfFloat;
	}

	/**
	 * Returns an array of biomes for the location input.
	 */
	@Override
	public TFCBiome[] getBiomesForGeneration(BiomeGenBase[] par1, int par2, int par3, int par4, int par5)
	{
		IntCache.resetIntCache();

		TFCBiome[] biome = (TFCBiome[]) par1;
		if (biome == null || biome.length < par4 * par5)
			biome = new TFCBiome[par4 * par5];

		int[] var6 = this.genBiomes.getInts(par2, par3, par4, par5);
		for (int var7 = 0; var7 < par4 * par5; ++var7)
		{
			int index = Math.max(var6[var7], 0);
			biome[var7] = TFCBiome.getBiome(index);
		}

		return biome;
	}

	/**
	 * Returns biomes to use for the blocks and loads the other data like temperature and humidity onto the
	 * WorldChunkManager Args: oldBiomeList, x, z, width, depth
	 */
	@Override
	public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase[] par1, int par2, int par3, int par4, int par5)
	{
		return this.getBiomeGenAt(par1, par2, par3, par4, par5, true);
	}

	/**
	 * Return a list of biomes for the specified blocks. Args: listToReuse, x, y, width, length, cacheFlag (if false,
	 * don't check biomeCache to avoid infinite loop in BiomeCacheBlock)
	 */
	@Override
	public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] biome, int par2, int par3, int par4, int par5, boolean par6)
	{
		IntCache.resetIntCache();

		if (biome == null || biome.length < par4 * par5)
			biome = new TFCBiome[par4 * par5];

		if (par6 && par4 == 16 && par5 == 16 && (par2 & 15) == 0 && (par3 & 15) == 0)
		{
			BiomeGenBase[] var9 = this.biomeCache.getCachedBiomes(par2, par3);
			System.arraycopy(var9, 0, biome, 0, par4 * par5);
			return biome;
		}
		else
		{
			int[] var7 = this.biomeIndexLayer.getInts(par2, par3, par4, par5);
			for (int zCoord = 0; zCoord < par4; ++zCoord)
			{
				for (int xCoord = 0; xCoord < par5; ++xCoord)
				{
					int id = var7[zCoord * par4 + xCoord] != -1 ? var7[zCoord * par4 + xCoord] : 0;
					biome[zCoord * par4 + xCoord] = TFCBiome.getBiome(id);
				}
			}
			return biome;
		}
	}

	/**
	 * checks given Chunk's Biomes against List of allowed ones
	 */
	@Override
	public boolean areBiomesViable(int par1, int par2, int par3, List par4List)
	{
		IntCache.resetIntCache();
		int var5 = par1 - par3 >> 2;
				int var6 = par2 - par3 >> 2;
		int var7 = par1 + par3 >> 2;
		int var8 = par2 + par3 >> 2;
		int var9 = var7 - var5 + 1;
		int var10 = var8 - var6 + 1;
		int[] var11 = this.genBiomes.getInts(var5, var6, var9, var10);

		for (int var12 = 0; var12 < var9 * var10; ++var12)
		{
			TFCBiome var13 = TFCBiome.getBiomeGenArray()[var11[var12]];
			if (!par4List.contains(var13))
				return false;
		}
		return true;
	}

	/**
	 * Finds a valid position within a range, that is in one of the listed biomes. Searches {par1,par2} +-par3 blocks.
	 * Strongly favors positive y positions.
	 */
	@Override
	public ChunkPosition findBiomePosition(int xCoord, int zCoord, int radius, List biomeList, Random rand)
	{
		IntCache.resetIntCache();
		int l = xCoord - radius >> 2;
		int i1 = zCoord - radius >> 2;
		int j1 = xCoord + radius >> 2;
		int k1 = zCoord + radius >> 2;
		int l1 = j1 - l + 1;
		int i2 = k1 - i1 + 1;
		int[] aint = this.genBiomes.getInts(l, i1, l1, i2);
		ChunkPosition chunkposition = null;
		int j2 = 0;

		for (int k2 = 0; k2 < l1 * i2; ++k2)
		{
			int l2 = l + k2 % l1 << 2;
			int i3 = i1 + k2 / l1 << 2;
			TFCBiome biomegenbase = TFCBiome.getBiome(aint[k2]);

			if (biomeList.contains(biomegenbase) && (chunkposition == null || rand.nextInt(j2 + 1) == 0))
			{
				chunkposition = new ChunkPosition(l2, 0, i3);
				++j2;
			}
		}

		return chunkposition;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getTemperatureAtHeight(float t, int y)
	{
		int x = (int)Math.floor(Minecraft.getMinecraft().thePlayer.posX);
		int z = (int)Math.floor(Minecraft.getMinecraft().thePlayer.posZ);
		return TFC_Climate.getHeightAdjustedTemp(Minecraft.getMinecraft().theWorld, x, y, z);
	}

	@Override
	public void cleanupCache()
	{
		this.biomeCache.cleanupCache();
		WorldCacheManager wcm = TFC_Climate.getCacheManager(this.worldObj);
		if(wcm != null) wcm.cleanupCache();
	}

}
