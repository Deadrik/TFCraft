package com.bioxx.tfc.WorldGen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.Chunkdata.ChunkData;
import com.bioxx.tfc.Chunkdata.ChunkDataManager;
import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Entities.Mobs.EntityBear;
import com.bioxx.tfc.Entities.Mobs.EntityChickenTFC;
import com.bioxx.tfc.Entities.Mobs.EntityCowTFC;
import com.bioxx.tfc.Entities.Mobs.EntityDeer;
import com.bioxx.tfc.Entities.Mobs.EntityHorseTFC;
import com.bioxx.tfc.Entities.Mobs.EntityPheasantTFC;
import com.bioxx.tfc.Entities.Mobs.EntityPigTFC;
import com.bioxx.tfc.Entities.Mobs.EntitySheepTFC;
import com.bioxx.tfc.Entities.Mobs.EntityWolfTFC;
import com.bioxx.tfc.WorldGen.Generators.WorldGenFissure;
import com.bioxx.tfc.api.TFCOptions;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderGenerate;
import net.minecraft.world.gen.NoiseGeneratorOctaves;

public class TFCChunkProviderGenerate extends ChunkProviderGenerate
{
	/** RNG. */
	private Random rand;

	/** A NoiseGeneratorOctaves used in generating terrain */
	private NoiseGeneratorOctaves noiseGen1;

	/** A NoiseGeneratorOctaves used in generating terrain */
	private NoiseGeneratorOctaves noiseGen2;

	/** A NoiseGeneratorOctaves used in generating terrain */
	private NoiseGeneratorOctaves noiseGen3;

	/** A NoiseGeneratorOctaves used in generating terrain */
	private NoiseGeneratorOctaves noiseGen4;

	/** A NoiseGeneratorOctaves used in generating terrain */
	public NoiseGeneratorOctaves noiseGen5;

	/** A NoiseGeneratorOctaves used in generating terrain */
	public NoiseGeneratorOctaves noiseGen6;
	public NoiseGeneratorOctaves mobSpawnerNoise;

	/** Reference to the World object. */
	private World worldObj;

	/** Holds the overall noise array used in chunk generation */
	private double[] noiseArray;
	private double[] stoneNoise = new double[256];

	/** The biomes that are used to generate the chunk */
	private TFCBiome[] biomesForGeneration;

	private DataLayer[] rockLayer1;
	private DataLayer[] rockLayer2;
	private DataLayer[] rockLayer3;
	private DataLayer[] evtLayer;
	private DataLayer[] rainfallLayer;
	private DataLayer[] stabilityLayer;

	private Block[] idsTop;
	private Block[] idsBig;
	private byte[] metaBig;

	/** A double array that hold terrain noise from noiseGen3 */
	double[] noise3;

	/** A double array that hold terrain noise */
	double[] noise1;

	/** A double array that hold terrain noise from noiseGen2 */
	double[] noise2;

	/** A double array that hold terrain noise from noiseGen5 */
	double[] noise5;

	/** A double array that holds terrain noise from noiseGen6 */
	double[] noise6;

	/**
	 * Used to store the 5x5 parabolic field that is used during terrain generation.
	 */
	float[] parabolicField;

	int[] heightMap = new int[256];

	WorldGenFissure fissureGen = new WorldGenFissure(TFCBlocks.FreshWaterFlowing, 1, false, 10);
	MapGenCavesTFC caveGen = new MapGenCavesTFC();
	MapGenRavineTFC surfaceRavineGen = new MapGenRavineTFC(125, 30);//surface
	MapGenRavineTFC ravineGen = new MapGenRavineTFC(20, 50);//deep
	MapGenRiverRavine riverRavineGen = new MapGenRiverRavine();

	public TFCChunkProviderGenerate(World par1World, long par2, boolean par4)
	{
		super(par1World, par2, par4);

		this.worldObj = par1World;
		this.rand = new Random(par2);
		this.noiseGen1 = new NoiseGeneratorOctaves(this.rand, 16);
		this.noiseGen2 = new NoiseGeneratorOctaves(this.rand, 16);
		this.noiseGen3 = new NoiseGeneratorOctaves(this.rand, 8);
		this.noiseGen4 = new NoiseGeneratorOctaves(this.rand, 4);
		this.noiseGen5 = new NoiseGeneratorOctaves(this.rand, 10);
		this.noiseGen6 = new NoiseGeneratorOctaves(this.rand, 16);
		this.mobSpawnerNoise = new NoiseGeneratorOctaves(this.rand, 8);

		this.idsTop = new Block[32768];
		this.idsBig = new Block[16*16*256];
		this.metaBig = new byte[16*16*256];
	}

	@Override
	public Chunk provideChunk(int chunkX, int chunkZ)
	{
		this.rand.setSeed(chunkX * 341873128712L + chunkZ * 132897987541L);

		//	To reduce GC churn, we allocate these arrays once in the
		//	constructor and then just clear them to all zeroes before each
		//	use.
		//
		Arrays.fill(idsTop, null);
		Arrays.fill(idsBig, null);
		Arrays.fill(metaBig, (byte)0);

		this.generateTerrainHigh(chunkX, chunkZ, idsTop);

		biomesForGeneration = ((TFCWorldChunkManager)this.worldObj.getWorldChunkManager()).loadBlockGeneratorData(biomesForGeneration, chunkX * 16, chunkZ * 16, 16, 16);
		rockLayer1 = ((TFCWorldChunkManager)this.worldObj.getWorldChunkManager()).loadRockLayerGeneratorData(rockLayer1, chunkX * 16, chunkZ * 16, 16, 16, 0);
		rockLayer2 = ((TFCWorldChunkManager)this.worldObj.getWorldChunkManager()).loadRockLayerGeneratorData(rockLayer2, chunkX * 16, chunkZ * 16, 16, 16, 1);
		rockLayer3 = ((TFCWorldChunkManager)this.worldObj.getWorldChunkManager()).loadRockLayerGeneratorData(rockLayer3, chunkX * 16, chunkZ * 16, 16, 16, 2);
		evtLayer = ((TFCWorldChunkManager)this.worldObj.getWorldChunkManager()).loadEVTLayerGeneratorData(evtLayer, chunkX * 16, chunkZ * 16, 16, 16);
		rainfallLayer = ((TFCWorldChunkManager)this.worldObj.getWorldChunkManager()).loadRainfallLayerGeneratorData(rainfallLayer, chunkX * 16, chunkZ * 16, 16, 16);
		stabilityLayer = ((TFCWorldChunkManager)this.worldObj.getWorldChunkManager()).loadStabilityLayerGeneratorData(stabilityLayer, chunkX * 16, chunkZ * 16, 16, 16);

		heightMap = new int[256];

		replaceBlocksForBiomeHigh(chunkX, chunkZ, idsTop, rand, idsBig, metaBig);
		replaceBlocksForBiomeLow(chunkX, chunkZ, rand, idsBig, metaBig);

		caveGen.generate(this, this.worldObj, chunkX, chunkZ, idsBig, metaBig);
		surfaceRavineGen.generate(this, this.worldObj, chunkX, chunkZ, idsBig, metaBig);//surface
		ravineGen.generate(this, this.worldObj, chunkX, chunkZ, idsBig, metaBig);//deep
		riverRavineGen.generate(this, this.worldObj, chunkX, chunkZ, idsBig, metaBig);

		ChunkTFC chunk = new ChunkTFC(this.worldObj, idsBig, metaBig, chunkX, chunkZ);
		ChunkData data = new ChunkData().CreateNew(chunkX, chunkZ);
		data.heightmap = heightMap;

		if(!this.worldObj.isRemote)
			ChunkDataManager.addData(data.chunkX, data.chunkZ, data);

		chunk.generateSkylightMap();
		return chunk;
	}

	@Override
	public void populate(IChunkProvider par1IChunkProvider, int chunkX, int chunkZ)
	{
		int xCoord = chunkX * 16;
		int zCoord = chunkZ * 16;
		TFCBiome biome = (TFCBiome) this.worldObj.getBiomeGenForCoords(xCoord + 16, zCoord + 16);
		this.rand.setSeed(this.worldObj.getSeed());
		long var7 = this.rand.nextLong() / 2L * 2L + 1L;
		long var9 = this.rand.nextLong() / 2L * 2L + 1L;
		this.rand.setSeed(chunkX * var7 + chunkZ * var9 ^ this.worldObj.getSeed());
		boolean var11 = false;

		int x;
		int y;
		int z;

		int waterRand = 4;
		if(TFC_Climate.getStability(xCoord, zCoord) == 1)
			waterRand = 6;

		//Underground Lava
		//new WorldGenFissure(Block.lavaStill,2, true, 25).setUnderground(true, 20).setSeed(1).generate(this, rand, worldObj, chunkX, chunkZ);
		//Surface Hotsprings
		//new WorldGenFissureCluster().generate(this, rand, worldObj, chunkX, chunkZ);

		if (!var11 && this.rand.nextInt(waterRand) == 0)
		{
			x = xCoord + this.rand.nextInt(16) + 8;
			z = zCoord + this.rand.nextInt(16) + 8;
			y = 145 - rand.nextInt(45);
			fissureGen.generate(this.worldObj, this.rand, x, y, z);
		}

		biome.decorate(this.worldObj, this.rand, xCoord, zCoord);
		SpawnerAnimalsTFC.performWorldGenSpawning(this.worldObj, biome, xCoord + 8, zCoord + 8, 16, 16, this.rand);

		for (x = 0; x < 16; x++)
		{
			for (z = 0; z < 16; z++)
			{
				y = this.worldObj.getPrecipitationHeight(xCoord + x, zCoord + z);

				if(!worldObj.isBlockFreezable(x + xCoord, y - 1, z + zCoord))
				{

				}

				if (canSnowAt(worldObj, x + xCoord, y, z + zCoord))
					this.worldObj.setBlock(x + xCoord, y, z + zCoord, Blocks.snow, 0, 0x2);
			}
		}
	}

	public static List getCreatureSpawnsByChunk(World world, TFCBiome biome, int x, int z)
	{
		List spawnableCreatureList = new ArrayList();
		spawnableCreatureList.add(new SpawnListEntry(EntityChickenTFC.class, 24, 0, 0));
		float temp = TFC_Climate.getBioTemperatureHeight(x, world.getTopSolidOrLiquidBlock(x, z), z);
		float rain = TFC_Climate.getRainfall(x, 150, z);
		float evt = TFC_Climate.manager.getEVTLayerAt(x, z).floatdata1;
		boolean isMountainous = biome == TFCBiome.Mountains || biome == TFCBiome.HighHills;
		//To adjust animal spawning at higher altitudes
		int mountainousAreaModifier = isMountainous? - 1 : 0;
		if(isMountainous)
		{
			if(temp<25 && temp > -10)
			{
				spawnableCreatureList.add(new SpawnListEntry(EntitySheepTFC.class, 2, 2, 4));
				if(rain >250 && evt < 0.75)
				{
					spawnableCreatureList.add(new SpawnListEntry(EntityWolfTFC.class, 2, 1, 3));
					spawnableCreatureList.add(new SpawnListEntry(EntityBear.class, 1, 1, 1));
				}
			}
		}
		else //run of the mill plains, but not too cold
			if(temp > 0 && rain > 100 && rain <= 500)
			{
				if(temp > 20)
				{
					//Pigs spawn on the warmer end of the spectrum
					spawnableCreatureList.add(new SpawnListEntry(EntityPigTFC.class, 1, 1, 2));
				}
				if(temp < 30)
				{
					spawnableCreatureList.add(new SpawnListEntry(EntityCowTFC.class, 2, 2, 4));
					spawnableCreatureList.add(new SpawnListEntry(EntityHorseTFC.class, 2, 2, 3));
				}
			}
		//regular temperate forest
		if(temp > 0 &&temp < 21 && rain > 250)
		{
			spawnableCreatureList.add(new SpawnListEntry(EntityPigTFC.class, 2 + mountainousAreaModifier, 2 + mountainousAreaModifier, 3 + mountainousAreaModifier));
			spawnableCreatureList.add(new SpawnListEntry(EntityWolfTFC.class, 1, 1, 2 + mountainousAreaModifier));
			spawnableCreatureList.add(new SpawnListEntry(EntityBear.class, 1, 1, 1));
			//spawnableCreatureList.add(new SpawnListEntry(EntityDeer.class, 2 + mountainousAreaModifier, 1, 3 + mountainousAreaModifier));
			spawnableCreatureList.add(new SpawnListEntry(EntityPheasantTFC.class, 3 + mountainousAreaModifier, 1, 3));

		}
		//colder climate
		if(temp > -20 && temp <=0)
		{
			//boreal forest
			if(rain > 250)
			{
				spawnableCreatureList.add(new SpawnListEntry(EntityPigTFC.class, 1 + mountainousAreaModifier, 1, 2));
				spawnableCreatureList.add(new SpawnListEntry(EntityWolfTFC.class, 2 + mountainousAreaModifier, 1, 2 + mountainousAreaModifier));
				spawnableCreatureList.add(new SpawnListEntry(EntityBear.class, 2 + mountainousAreaModifier, 1, 1));
				spawnableCreatureList.add(new SpawnListEntry(EntityDeer.class, 1 + mountainousAreaModifier, 2, 3));
				spawnableCreatureList.add(new SpawnListEntry(EntityPheasantTFC.class, 1 + mountainousAreaModifier, 1, 2));
				spawnableCreatureList.add(new SpawnListEntry(EntitySheepTFC.class, 2, 2, 4));
			}
			//closer to tundra or taiga
			else if(rain >100)
			{
				spawnableCreatureList.add(new SpawnListEntry(EntityWolfTFC.class, 1 + mountainousAreaModifier, 1, 1));
				//spawnableCreatureList.add(new SpawnListEntry(EntityDeer.class, 1 + mountainousAreaModifier, 1, 1));
			}
		}
		//Jungle
		if(temp >= 23 && temp < 44 && rain > 1500)
		{
			spawnableCreatureList.add(new SpawnListEntry(EntityPigTFC.class, 2 + mountainousAreaModifier, 2 + mountainousAreaModifier, 4 + mountainousAreaModifier));
			spawnableCreatureList.add(new SpawnListEntry(EntityChickenTFC.class, 3 + mountainousAreaModifier, 1, 4 + mountainousAreaModifier));
		}
		//Swamp
		if(TFC_Climate.isSwamp(x,150,z))
			spawnableCreatureList.add(new SpawnListEntry(EntityPigTFC.class, 1, 1, 2));
		spawnableCreatureList.add(new SpawnListEntry(EntityPheasantTFC.class, 1 + mountainousAreaModifier, 1, 1));
		return spawnableCreatureList;
	}

	public boolean canSnowAt(World world, int par1, int par2, int par3)
	{
		float var5 = TFC_Climate.getHeightAdjustedTemp(par1, par2, par3);
		if (var5 > 0F)
		{
			return false;
		}
		else
		{
			if (par2 >= 0 && par2 < 256 && world.getSavedLightValue(EnumSkyBlock.Block, par1, par2, par3) < 10)
			{
				Block var6 = world.getBlock(par1, par2 - 1, par3);
				Block var7 = world.getBlock(par1, par2, par3);
				if (var7 == Blocks.air && Blocks.snow.canPlaceBlockAt(world, par1, par2, par3) && var6 != Blocks.air && var6.getMaterial().blocksMovement())
					return true;
			}
			return false;
		}
	}

	public void generateTerrainHigh(int par1, int par2, Block[] idsTop)
	{
		byte var4 = 4;
		byte var5 = 16;
		int seaLevel = 16;
		int var7 = var4 + 1;
		byte var8 = 17;
		int var9 = var4 + 1;
		this.biomesForGeneration = ((TFCWorldChunkManager)this.worldObj.getWorldChunkManager()).getBiomesForGeneration(this.biomesForGeneration, par1 * 4 - 2, par2 * 4 - 2, var7 + 5, var9 + 5);
		this.noiseArray = this.initializeNoiseFieldHigh(this.noiseArray, par1 * var4, 0, par2 * var4, var7, var8, var9);

		for (int var10 = 0; var10 < var4; ++var10)
		{
			for (int var11 = 0; var11 < var4; ++var11)
			{
				for (int var12 = 0; var12 < var5; ++var12)
				{
					double yLerp = 0.125D;
					double var15 = this.noiseArray[((var10 + 0) * var9 + var11 + 0) * var8 + var12 + 0];
					double var17 = this.noiseArray[((var10 + 0) * var9 + var11 + 1) * var8 + var12 + 0];
					double var19 = this.noiseArray[((var10 + 1) * var9 + var11 + 0) * var8 + var12 + 0];
					double var21 = this.noiseArray[((var10 + 1) * var9 + var11 + 1) * var8 + var12 + 0];
					double var23 = (this.noiseArray[((var10 + 0) * var9 + var11 + 0) * var8 + var12 + 1] - var15) * yLerp;
					double var25 = (this.noiseArray[((var10 + 0) * var9 + var11 + 1) * var8 + var12 + 1] - var17) * yLerp;
					double var27 = (this.noiseArray[((var10 + 1) * var9 + var11 + 0) * var8 + var12 + 1] - var19) * yLerp;
					double var29 = (this.noiseArray[((var10 + 1) * var9 + var11 + 1) * var8 + var12 + 1] - var21) * yLerp;

					for (int var31 = 0; var31 < 8; ++var31)
					{
						double xLerp = 0.25D;
						double var34 = var15;
						double var36 = var17;
						double var38 = (var19 - var15) * xLerp;
						double var40 = (var21 - var17) * xLerp;

						for (int var42 = 0; var42 < 4; ++var42)
						{
							int var43 = var42 + var10 * 4 << 11 | 0 + var11 * 4 << 7 | var12 * 8 + var31;
							short var44 = 128;
							var43 -= var44;
							double zLerp = 0.25D;
							double var49 = (var36 - var34) * zLerp;
							double var47 = var34 - var49;

							for (int var51 = 0; var51 < 4; ++var51)
							{
								if ((var47 += var49) > 0.0D)
									idsTop[var43 += var44] = Blocks.stone;
								else if (var12 * 8 + var31 < seaLevel)
									idsTop[var43 += var44] = TFCBlocks.SaltWaterStill;
								else
									idsTop[var43 += var44] = Blocks.air;
							}
							var34 += var38;
							var36 += var40;
						}
						var15 += var23;
						var17 += var25;
						var19 += var27;
						var21 += var29;
					}
				}
			}
		}
	}

	/**
	 * generates a subset of the level's terrain data. Takes 7 arguments: the [empty] noise array, the position, and the
	 * size.
	 */
	private double[] initializeNoiseFieldHigh(double[] par1ArrayOfDouble, int par2, int par3, int par4, int par5, int par6, int par7)
	{
		if (par1ArrayOfDouble == null)
			par1ArrayOfDouble = new double[par5 * par6 * par7];

		if (this.parabolicField == null)
		{
			this.parabolicField = new float[25];
			for (int var8 = -2; var8 <= 2; ++var8)
			{
				for (int var9 = -2; var9 <= 2; ++var9)
				{
					float var10 = 10.0F / MathHelper.sqrt_float(var8 * var8 + var9 * var9 + 0.2F);
					this.parabolicField[var8 + 2 + (var9 + 2) * 5] = var10;
				}
			}
		}

		double var44 = 684.412D;
		double var45 = 684.412D;
		this.noise5 = this.noiseGen5.generateNoiseOctaves(this.noise5, par2, par4, par5, par7, 1.121D, 1.121D, 0.5D);
		this.noise6 = this.noiseGen6.generateNoiseOctaves(this.noise6, par2, par4, par5, par7, 200.0D, 200.0D, 0.5D);
		this.noise3 = this.noiseGen3.generateNoiseOctaves(this.noise3, par2, par3, par4, par5, par6, par7, var44 / 80.0D, var45 / 160.0D, var44 / 80.0D);
		this.noise1 = this.noiseGen1.generateNoiseOctaves(this.noise1, par2, par3, par4, par5, par6, par7, var44, var45, var44);
		this.noise2 = this.noiseGen2.generateNoiseOctaves(this.noise2, par2, par3, par4, par5, par6, par7, var44, var45, var44);
		boolean var43 = false;
		boolean var42 = false;
		int var12 = 0;
		int var13 = 0;

		for (int var14 = 0; var14 < par5; ++var14)
		{
			for (int var15 = 0; var15 < par7; ++var15)
			{
				float var16 = 0.0F;
				float var17 = 0.0F;
				float var18 = 0.0F;
				byte var19 = 2;
				TFCBiome baseBiome = this.biomesForGeneration[var14 + 2 + (var15 + 2) * (par5 + 5)];

				for (int var21 = -var19; var21 <= var19; ++var21)
				{
					for (int var22 = -var19; var22 <= var19; ++var22)
					{
						TFCBiome blendBiome = this.biomesForGeneration[var14 + var21 + 2 + (var15 + var22 + 2) * (par5 + 5)];
						float blendedHeight = this.parabolicField[var21 + 2 + (var22 + 2) * 5] / (/*blendBiome.minHeight*/ + 2.0F);//<---blendBiome.minHeight was commented out
						if (blendBiome.rootHeight > baseBiome.rootHeight)
							blendedHeight *= 0.3F;
						var16 += blendBiome.heightVariation * blendedHeight;
						var17 += blendBiome.rootHeight * blendedHeight;
						var18 += blendedHeight;
					}
				}

				var16 /= var18;
				var17 /= var18;
				var16 = var16 * 0.9F + 0.1F;
				var17 = (var17 * 4.0F - 1.0F) / 8.0F;
				double var47 = this.noise6[var13] / 8000.0D;

				if (var47 < 0.0D)
					var47 = -var47 * 0.3D;
				var47 = var47 * 3.0D - 2.0D;

				if (var47 < 0.0D)
				{
					var47 /= 2.0D;
					if (var47 < -1.0D)
						var47 = -1.0D;
					var47 /= 1.4D;
					var47 /= 2.0D;
				}
				else
				{
					if (var47 > 1.0D)
						var47 = 1.0D;
					var47 /= 8.0D;
				}

				++var13;
				for (int var46 = 0; var46 < par6; ++var46)
				{
					double var48 = var17;
					double var26 = var16;
					var48 += var47 * 0.2D;
					var48 = var48 * par6 / 16.0D;
					double var28 = par6 / 2.0D + var48 * 4.0D;
					double var30 = 0.0D;
					double var32 = (var46 - var28) * 12.0D * 256.0D / 256.0D / (2.70 + var26);

					if (var32 < 0.0D)
						var32 *= 4.0D;

					double var34 = this.noise1[var12] / 512.0D;
					double var36 = this.noise2[var12] / 512.0D;
					double var38 = (this.noise3[var12] / 10.0D + 1.0D) / 2.0D;

					if (var38 < 0.0D)
						var30 = var34;
					else if (var38 > 1.0D)
						var30 = var36;
					else
						var30 = var34 + (var36 - var34) * var38;

					var30 -= var32;
					if (var46 > par6 - 4)
					{
						double var40 = (var46 - (par6 - 4)) / 3.0F;
						var30 = var30 * (1.0D - var40) + -10.0D * var40;
					}

					par1ArrayOfDouble[var12] = var30;
					++var12;
				}
			}
		}
		return par1ArrayOfDouble;
	}

	private void replaceBlocksForBiomeHigh(int chunkX, int chunkZ, Block[] idsTop, Random rand, Block[] idsBig, byte[] metaBig)
	{
		int var5 = 16;
		double var6 = 0.03125D;
		stoneNoise = noiseGen4.generateNoiseOctaves(stoneNoise, chunkX * 16, chunkZ * 16, 0, 16, 16, 1, var6 * 2.0D, var6 * 2.0D, var6 * 2.0D);

		for (int xCoord = 0; xCoord < 16; ++xCoord)
		{
			for (int zCoord = 0; zCoord < 16; ++zCoord)
			{
				int arrayIndex = xCoord + zCoord * 16;
				int arrayIndexDL = zCoord + xCoord * 16;
				TFCBiome biome = biomesForGeneration[arrayIndexDL];
				DataLayer rock1 = rockLayer1[arrayIndexDL] == null ? DataLayer.Granite : rockLayer1[arrayIndexDL];
				DataLayer rock2 = rockLayer2[arrayIndexDL] == null ? DataLayer.Granite : rockLayer2[arrayIndexDL];
				DataLayer rock3 = rockLayer3[arrayIndexDL] == null ? DataLayer.Granite : rockLayer3[arrayIndexDL];
				DataLayer evt = evtLayer[arrayIndexDL] == null ? DataLayer.EVT_0_125 : evtLayer[arrayIndexDL];
				float rain = TFC_Climate.getRainfall(chunkX*16 + xCoord, 144, chunkZ*16 + zCoord);

				int var12 = (int)(stoneNoise[arrayIndex] / 3.0D + 6.0D + rand.nextDouble() * 0.25D);
				int var13 = -1;

				Block surfaceBlock = TFC_Core.getTypeForGrassWithRain(TFC_Core.getItemMetaFromStone(rock1.block, rock1.data2), rain);
				Block subSurfaceBlock = TFC_Core.getTypeForDirtFromGrass(surfaceBlock);
				byte soilMeta = (byte) TFC_Core.getSoilMetaFromStone(rock1.block, rock1.data2);
				float _temp = TFC_Climate.getBioTemperature(chunkX * 16 + xCoord, chunkZ * 16 + zCoord);

				for (int height = 127; height >= 0; --height)
				{
					int indexBig = ((arrayIndex) * 256 + height + 128);
					int index = ((arrayIndex) * 128 + height);
					metaBig[indexBig] = 0;
					float temp = TFC_Climate.adjustHeightToTemp(height, _temp);

					idsBig[indexBig] = idsTop[index];
					if (idsTop[index] == Blocks.stone)
					{
						if(heightMap[arrayIndex] == 0 && height-16 >= 0)
							heightMap[arrayIndex] = height-16;

						convertStone(128+height, arrayIndex, indexBig, idsBig, metaBig, rock1, rock2, rock3);

						//First we check to see if its a cold desert
						if(rain < 125 && temp < 1.5f)
						{
							surfaceBlock = TFC_Core.getTypeForSand(soilMeta);
							subSurfaceBlock = TFC_Core.getTypeForSand(soilMeta);
						}
						//Next we check for all other warm deserts
						else if(rain < 125 && biome.heightVariation < 0.5f && temp > 20f)
						{
							surfaceBlock = TFC_Core.getTypeForSand(soilMeta);
							subSurfaceBlock = TFC_Core.getTypeForSand(soilMeta);
						}

						if (var13 == -1)
						{
							//The following makes dirt behave nicer and more smoothly, instead of forming sharp cliffs.
							int arrayIndexx = xCoord > 0? (xCoord - 1) + (zCoord * 16):-1;
							int arrayIndexX = xCoord < 15? (xCoord + 1) + (zCoord * 16):-1;
							int arrayIndexz = zCoord > 0? xCoord + ((zCoord-1) * 16):-1;
							int arrayIndexZ = zCoord < 15? xCoord + ((zCoord+1) * 16):-1;
							int var12Temp = var12;
							for(int counter = 1; counter < var12Temp / 3; counter++)
							{
								if(arrayIndexx >= 0 && heightMap[arrayIndex]-(3*counter) > heightMap[arrayIndexx])
								{
									heightMap[arrayIndex]--;
									var12--;
									height--;
									indexBig = ((arrayIndex) * 256 + height + 128);
									index = ((arrayIndex) * 128 + height);
								}
								else if(arrayIndexX >= 0 && heightMap[arrayIndex]-(3*counter) > heightMap[arrayIndexX])
								{
									heightMap[arrayIndex]--;
									var12--;
									height--;
									indexBig = ((arrayIndex) * 256 + height + 128);
									index = ((arrayIndex) * 128 + height);
								}
								else if(arrayIndexz >= 0 && heightMap[arrayIndex]-(3*counter) > heightMap[arrayIndexz])
								{
									heightMap[arrayIndex]--;
									var12--;
									height--;
									indexBig = ((arrayIndex) * 256 + height + 128);
									index = ((arrayIndex) * 128 + height);
								}
								else if(arrayIndexZ >= 0 && heightMap[arrayIndex]-(3*counter) > heightMap[arrayIndexZ])
								{
									heightMap[arrayIndex]--;
									var12--;
									height--;
									indexBig = ((arrayIndex) * 256 + height + 128);
									index = ((arrayIndex) * 128 + height);
								}
							}
							var13 = (int)(var12 * (1d-Math.max(Math.min(((height - 20) / 80d), 1), 0)));
							for(int c = 1; c < 4; c++)
							{
								if(indexBig + c < idsBig.length && (
										(idsBig[indexBig + c] != surfaceBlock) &&
										(idsBig[indexBig + c] != subSurfaceBlock) &&
										(idsBig[indexBig + c] != TFCBlocks.SaltWaterStill) &&
										(idsBig[indexBig + c] != TFCBlocks.FreshWaterStill) &&
										(idsBig[indexBig + c] != TFCBlocks.HotWaterStill)))
								{
									idsBig[indexBig + c] = Blocks.air;
									metaBig[indexBig + c] = 0;
									if(indexBig + c + 1 < idsBig.length && idsBig[indexBig + c + 1] == TFCBlocks.SaltWaterStill)
									{
										idsBig[indexBig + c] = subSurfaceBlock;
										metaBig[indexBig + c] = soilMeta;
									}
								}
							}

							if(var13 > 0)
							{
								if (height >= var5 - 1 && index+1 < idsTop.length && idsTop[index + 1] != TFCBlocks.SaltWaterStill)
								{
									idsBig[indexBig] = surfaceBlock;
									metaBig[indexBig] = soilMeta;
								}
								else
								{
									idsBig[indexBig] = subSurfaceBlock;
									metaBig[indexBig] = soilMeta;
								}
							}
						}
						else if (var13 > 1)
						{
							--var13;
							idsBig[indexBig] = subSurfaceBlock;
							metaBig[indexBig] = soilMeta;
						}

						if(biome == TFCBiome.ocean)
						{
							if(((height > var5 - 2 && height <= var5 + 1) || (height < var5 && idsTop[index + 2] == TFCBlocks.SaltWaterStill)))//If its an ocean give it a sandy bottom
							{
								idsBig[indexBig] = TFC_Core.getTypeForSand(soilMeta);
								metaBig[indexBig] = soilMeta;
							}
						}
						else if(!(biome == TFCBiome.swampland))
						{
							if(((height > var5 - 2 && height < var5 && idsTop[index + 1] == TFCBlocks.SaltWaterStill)) || (height < var5 && idsTop[index + 1] == TFCBlocks.SaltWaterStill))
							{
								if(idsBig[indexBig] != TFC_Core.getTypeForSand(soilMeta) && rand.nextInt(5) != 0)
								{
									idsBig[indexBig] = Blocks.gravel;
									metaBig[indexBig] = 0;
								}
							}
						}
					}
					else if(idsTop[index] == TFCBlocks.SaltWaterStill && biome != TFCBiome.ocean && biome != TFCBiome.beach)
					{
						idsBig[indexBig] = TFCBlocks.FreshWaterStill;
					}
				}
			}
		}
	}

	private double[] layer2Noise = new double[256];
	private void replaceBlocksForBiomeLow(int par1, int par2, Random rand, Block[] idsBig, byte[] metaBig)
	{
		int var5 = 63;
		double var6 = 0.03125D;
		stoneNoise = noiseGen4.generateNoiseOctaves(stoneNoise, par1 * 16, par2 * 16, 0, 16, 16, 1, var6 * 2.0D, var6 * 2.0D, var6 * 2.0D);

		for (int xCoord = 0; xCoord < 16; ++xCoord)
		{
			for (int zCoord = 0; zCoord < 16; ++zCoord)
			{
				int arrayIndex = xCoord + zCoord * 16;
				int arrayIndexDL = zCoord + xCoord * 16;
				DataLayer rock1 = rockLayer1[arrayIndexDL];
				DataLayer rock2 = rockLayer2[arrayIndexDL];
				DataLayer rock3 = rockLayer3[arrayIndexDL];
				DataLayer stability = stabilityLayer[arrayIndexDL];

				int var12 = (int)(stoneNoise[arrayIndex] / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
				int var13 = -1;
				int top = 0;

				for (int height = 127; height >= 0; --height)
				{
					int index = ((arrayIndex) * 128 + height);
					int indexBig = ((arrayIndex) * 256 + height);
					metaBig[indexBig] = 0;

					if (height <= 1 + (heightMap[arrayIndex] / 3) + this.rand.nextInt(3))
					{
						idsBig[indexBig] = Blocks.bedrock;
					}
					else
					{
						convertStone(height, arrayIndex, indexBig, idsBig, metaBig, rock1, rock2, rock3);
						if (var13 == -1)
						{
							if (var12 <= 0)
							{
							}
						}
					}

					if (height <= 6 && stability.data1 == 1 && idsBig[indexBig] == Blocks.air)
					{
						idsBig[indexBig] = Blocks.lava;
						metaBig[indexBig] = 0; 
						if(idsBig[indexBig+1] != Blocks.lava && rand.nextBoolean())
						{
							idsBig[indexBig+1] = Blocks.lava;
							metaBig[indexBig+1] = 0; 
						}
					}
				}
			}
		}
	}

	public void convertStone(int height, int indexArray, int indexBig, Block[] idsBig, byte[] metaBig, DataLayer rock1, DataLayer rock2, DataLayer rock3)
	{
		if(height <= TFCOptions.RockLayer3Height + heightMap[indexArray])
		{
			idsBig[indexBig] = rock3.block;
			metaBig[indexBig] = (byte) rock3.data2;
			if(height == TFCOptions.RockLayer3Height + heightMap[indexArray])
			{
				if(rand.nextBoolean())
				{
					idsBig[indexBig + 1] = rock3.block;
					metaBig[indexBig + 1] = (byte) rock3.data2;
					if(rand.nextBoolean())
					{
						idsBig[indexBig + 2] = rock3.block;
						metaBig[indexBig + 2] = (byte) rock3.data2;
						if(rand.nextBoolean())
						{
							idsBig[indexBig + 3] = rock3.block;
							metaBig[indexBig + 3] = (byte) rock3.data2;
						}
					}
				}
			}
		}
		else if(height <= TFCOptions.RockLayer2Height + heightMap[indexArray] && height > 55+heightMap[indexArray] && rock2!=null)
		{
			idsBig[indexBig] = rock2.block; 
			metaBig[indexBig] = (byte) rock2.data2;
			if(height == TFCOptions.RockLayer2Height + heightMap[indexArray])
			{
				if(rand.nextBoolean())
				{
					idsBig[indexBig + 1] = rock2.block;
					metaBig[indexBig + 1] = (byte) rock2.data2;
					if(rand.nextBoolean())
					{
						idsBig[indexBig + 2] = rock2.block; 
						metaBig[indexBig + 2] = (byte) rock2.data2;
						if(rand.nextBoolean())
						{
							idsBig[indexBig + 3] = rock2.block; 
							metaBig[indexBig + 3] = (byte) rock2.data2;
						}
					}
				}
			}
		}
		else
		{
			idsBig[indexBig] = rock1.block; 
			metaBig[indexBig] = (byte) rock1.data2;
		}
	}

	@Override
	public boolean unloadQueuedChunks()
	{
		return true;
	}
}
