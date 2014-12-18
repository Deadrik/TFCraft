package com.bioxx.tfc.WorldGen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderGenerate;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.Blocks.Terrain.BlockCollapsable;
import com.bioxx.tfc.Chunkdata.ChunkData;
import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
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
import com.bioxx.tfc.WorldGen.MapGen.MapGenCavesTFC;
import com.bioxx.tfc.WorldGen.MapGen.MapGenRavineTFC;
import com.bioxx.tfc.WorldGen.MapGen.MapGenRiverRavine;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.Constant.Global;

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
	private BiomeGenBase[] biomesForGeneration;

	private DataLayer[] rockLayer1;
	private DataLayer[] rockLayer2;
	private DataLayer[] rockLayer3;
	private DataLayer[] evtLayer;
	private DataLayer[] rainfallLayer;
	private DataLayer[] stabilityLayer;
	private DataLayer[] drainageLayer;


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

	int[] seaLevelOffsetMap = new int[256];
	int[] chunkHeightMap = new int[256];

	WorldGenFissure fissureGen = new WorldGenFissure(TFCBlocks.FreshWater, 1, false, 10);
	MapGenCavesTFC caveGen = new MapGenCavesTFC();
	MapGenRavineTFC surfaceRavineGen = new MapGenRavineTFC(125, 30);//surface
	MapGenRavineTFC ravineGen = new MapGenRavineTFC(20, 50);//deep
	MapGenRiverRavine riverRavineGen = new MapGenRiverRavine();

	public TFCChunkProviderGenerate(World par1World, long par2, boolean par4)
	{
		super(par1World, par2, par4);

		this.worldObj = par1World;
		this.rand = new Random(par2);
		this.noiseGen1 = new NoiseGeneratorOctaves(this.rand, 4);
		this.noiseGen2 = new NoiseGeneratorOctaves(this.rand, 16);
		this.noiseGen3 = new NoiseGeneratorOctaves(this.rand, 8);
		this.noiseGen4 = new NoiseGeneratorOctaves(this.rand, 4);
		this.noiseGen5 = new NoiseGeneratorOctaves(this.rand, 2);
		this.noiseGen6 = new NoiseGeneratorOctaves(this.rand, 1);
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

		biomesForGeneration = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(biomesForGeneration, chunkX * 16-1, chunkZ * 16-1, 18, 18);
		rockLayer1 = TFC_Climate.getCacheManager(worldObj).loadRockLayerGeneratorData(rockLayer1, chunkX * 16, chunkZ * 16, 16, 16, 0);
		rockLayer2 = TFC_Climate.getCacheManager(worldObj).loadRockLayerGeneratorData(rockLayer2, chunkX * 16, chunkZ * 16, 16, 16, 1);
		rockLayer3 = TFC_Climate.getCacheManager(worldObj).loadRockLayerGeneratorData(rockLayer3, chunkX * 16, chunkZ * 16, 16, 16, 2);
		evtLayer = TFC_Climate.getCacheManager(worldObj).loadEVTLayerGeneratorData(evtLayer, chunkX * 16, chunkZ * 16, 16, 16);
		rainfallLayer = TFC_Climate.getCacheManager(worldObj).loadRainfallLayerGeneratorData(rainfallLayer, chunkX * 16, chunkZ * 16, 16, 16);
		stabilityLayer = TFC_Climate.getCacheManager(worldObj).loadStabilityLayerGeneratorData(stabilityLayer, chunkX * 16, chunkZ * 16, 16, 16);
		drainageLayer = TFC_Climate.getCacheManager(worldObj).loadDrainageLayerGeneratorData(drainageLayer, chunkX * 16, chunkZ * 16, 16, 16);
		seaLevelOffsetMap = new int[256];

		replaceBlocksForBiomeHigh(chunkX, chunkZ, idsTop, rand, idsBig, metaBig);
		replaceBlocksForBiomeLow(chunkX, chunkZ, rand, idsBig, metaBig);

		caveGen.generate(this, this.worldObj, chunkX, chunkZ, idsBig, metaBig);
		surfaceRavineGen.generate(this, this.worldObj, chunkX, chunkZ, idsBig, metaBig);//surface
		ravineGen.generate(this, this.worldObj, chunkX, chunkZ, idsBig, metaBig);//deep
		riverRavineGen.generate(this, this.worldObj, chunkX, chunkZ, idsBig, metaBig);

		Chunk chunk = new Chunk(this.worldObj, idsBig, metaBig, chunkX, chunkZ);
		byte[] abyte1 = chunk.getBiomeArray();

		for (int x = 0; x < 16; ++x)
		{
			for (int z = 0; z < 16; ++z)
			{
				abyte1[x*z] = (byte)getBiome(x, z).biomeID;
			}
		}
		chunk.setBiomeArray(abyte1);

		ChunkData data = new ChunkData().CreateNew(worldObj, chunkX, chunkZ);
		data.heightmap = seaLevelOffsetMap;
		data.rainfallMap = this.rainfallLayer;
		TFC_Core.getCDM(worldObj).addData(chunk, data);
		//chunk.heightMap = chunkHeightMap;
		chunk.generateSkylightMap();
		return chunk;
	}

	private BiomeGenBase getBiome(int x, int z)
	{
		return this.biomesForGeneration[(z+1) + (x+1) * 18];
	}

	@Override
	public void populate(IChunkProvider chunkProvider, int chunkX, int chunkZ)
	{
		BlockCollapsable.fallInstantly = true;
		int xCoord = chunkX * 16;
		int zCoord = chunkZ * 16;
		TFCBiome biome = (TFCBiome) this.worldObj.getBiomeGenForCoords(xCoord + 16, zCoord + 16);
		this.rand.setSeed(this.worldObj.getSeed());
		long var7 = this.rand.nextLong() / 2L * 2L + 1L;
		long var9 = this.rand.nextLong() / 2L * 2L + 1L;
		this.rand.setSeed(chunkX * var7 + chunkZ * var9 ^ this.worldObj.getSeed());
		boolean var11 = false;

		MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre(chunkProvider, worldObj, rand, chunkX, chunkZ, var11));

		int x;
		int y;
		int z;

		TFC_Core.getCDM(worldObj).setFishPop(chunkX, chunkZ, ChunkData.fishPopMax);

		int waterRand = 4;
		if(TFC_Climate.getStability(worldObj, xCoord, zCoord) == 1)
			waterRand = 6;

		if (!var11 && this.rand.nextInt(waterRand) == 0)
		{
			x = xCoord + this.rand.nextInt(16) + 8;
			z = zCoord + this.rand.nextInt(16) + 8;
			y = Global.SEALEVEL - rand.nextInt(45);
			//fissureGen.generate(this.worldObj, this.rand, x, y, z);
		}

		biome.decorate(this.worldObj, this.rand, xCoord, zCoord);
		SpawnerAnimalsTFC.performWorldGenSpawning(this.worldObj, biome, xCoord + 8, zCoord + 8, 16, 16, this.rand);

		for (x = 0; x < 16; x++)
		{
			for (z = 0; z < 16; z++)
			{
				y = this.worldObj.getPrecipitationHeight(xCoord + x, zCoord + z);

				worldObj.isBlockFreezable(x + xCoord, y - 1, z + zCoord);
				if (worldObj.provider.canSnowAt(x + xCoord, y, z + zCoord, false))
					this.worldObj.setBlock(x + xCoord, y, z + zCoord,  TFCBlocks.Snow, 0, 0x2);
			}
		}

		MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Post(chunkProvider, worldObj, rand, chunkX, chunkZ, var11));
		BlockCollapsable.fallInstantly = false;
	}

	public static List getCreatureSpawnsByChunk(World world, TFCBiome biome, int x, int z)
	{
		List spawnableCreatureList = new ArrayList();
		spawnableCreatureList.add(new SpawnListEntry(EntityChickenTFC.class, 24, 0, 0));
		float temp = TFC_Climate.getBioTemperatureHeight(world, x, world.getTopSolidOrLiquidBlock(x, z), z);
		float rain = TFC_Climate.getRainfall(world, x, 150, z);
		float evt = TFC_Climate.getCacheManager(world).getEVTLayerAt( x, z).floatdata1;
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
			spawnableCreatureList.add(new SpawnListEntry(EntityDeer.class, 2 + mountainousAreaModifier, 1, 3 + mountainousAreaModifier));
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
				spawnableCreatureList.add(new SpawnListEntry(EntityDeer.class, 1 + mountainousAreaModifier, 1, 1));
			}
		}
		//Jungle
		if(temp >= 23 && temp < 44 && rain > 1500)
		{
			spawnableCreatureList.add(new SpawnListEntry(EntityPigTFC.class, 2 + mountainousAreaModifier, 2 + mountainousAreaModifier, 4 + mountainousAreaModifier));
			spawnableCreatureList.add(new SpawnListEntry(EntityChickenTFC.class, 3 + mountainousAreaModifier, 1, 4 + mountainousAreaModifier));
		}
		//Swamp
		if(TFC_Climate.isSwamp(world, x,150,z))
		{
			spawnableCreatureList.add(new SpawnListEntry(EntityPigTFC.class, 1, 1, 2));
			spawnableCreatureList.add(new SpawnListEntry(EntityPheasantTFC.class, 1 + mountainousAreaModifier, 1, 1));
		}
		return spawnableCreatureList;
	}

	public boolean canSnowAt(World world, int x, int y, int z)
	{
		float var5 = TFC_Climate.getHeightAdjustedTemp(world, x, y, z);
		if (var5 > 0F)
		{
			return false;
		}
		else
		{
			if (y >= 0 && y < 256 && world.getSavedLightValue(EnumSkyBlock.Block, x, y, z) < 10 && TFC_Time.getTotalMonths() > 1)
			{
				Block var6 = world.getBlock(x, y - 1, z);
				Block var7 = world.getBlock(x, y, z);
				if (var7.isAir(world, x, y, z) && TFCBlocks.Snow.canPlaceBlockAt(world, x, y, z) && !var6.isAir(world, x, y - 1, z) && var6.getMaterial().blocksMovement())
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
		int xSize = var4 + 1;
		byte ySize = 17;
		int zSize = var4 + 1;
		short arrayYHeight = 128;
		this.biomesForGeneration = this.worldObj.getWorldChunkManager().getBiomesForGeneration(this.biomesForGeneration, par1 * 4 - 2, par2 * 4 - 2, xSize + 5, zSize + 5);
		this.noiseArray = this.initializeNoiseFieldHigh(this.noiseArray, par1 * var4, 0, par2 * var4, xSize, ySize, zSize);

		for (int x = 0; x < var4; ++x)
		{
			for (int z = 0; z < var4; ++z)
			{
				for (int y = 0; y < var5; ++y)
				{
					double yLerp = 0.125D;
					double var15 = this.noiseArray[((x + 0) * zSize + z + 0) * ySize + y + 0];
					double var17 = this.noiseArray[((x + 0) * zSize + z + 1) * ySize + y + 0];
					double var19 = this.noiseArray[((x + 1) * zSize + z + 0) * ySize + y + 0];
					double var21 = this.noiseArray[((x + 1) * zSize + z + 1) * ySize + y + 0];
					double var23 = (this.noiseArray[((x + 0) * zSize + z + 0) * ySize + y + 1] - var15) * yLerp;
					double var25 = (this.noiseArray[((x + 0) * zSize + z + 1) * ySize + y + 1] - var17) * yLerp;
					double var27 = (this.noiseArray[((x + 1) * zSize + z + 0) * ySize + y + 1] - var19) * yLerp;
					double var29 = (this.noiseArray[((x + 1) * zSize + z + 1) * ySize + y + 1] - var21) * yLerp;

					for (int var31 = 0; var31 < 8; ++var31)
					{
						double xLerp = 0.25D;
						double var34 = var15;
						double var36 = var17;
						double var38 = (var19 - var15) * xLerp;
						double var40 = (var21 - var17) * xLerp;

						for (int var42 = 0; var42 < 4; ++var42)
						{
							int index = var42 + x * 4 << 11 | 0 + z * 4 << 7 | y * 8 + var31;

							index -= arrayYHeight;
							double zLerp = 0.25D;
							double var49 = (var36 - var34) * zLerp;
							double var47 = var34 - var49;

							for (int var51 = 0; var51 < 4; ++var51)
							{
								if ((var47 += var49) > 0.0D)
									idsTop[index += arrayYHeight] = Blocks.stone;
								else if (y * 8 + var31 < seaLevel)
									idsTop[index += arrayYHeight] = TFCBlocks.SaltWaterStationary;
								else
									idsTop[index += arrayYHeight] = Blocks.air;
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
	private double[] initializeNoiseFieldHigh(double[] outArray, int xPos, int yPos, int zPos, int xSize, int ySize, int zSize)
	{
		if (outArray == null)
			outArray = new double[xSize * ySize * zSize];

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

		//double var44 = 684.412D;
		//double var45 = 684.412D;
		double var44 = 1000D;
		double var45 = 1000D;
		this.noise5 = this.noiseGen5.generateNoiseOctaves(this.noise5, xPos, zPos, xSize, zSize, 1.121D, 1.121D, 0.5D);
		this.noise6 = this.noiseGen6.generateNoiseOctaves(this.noise6, xPos, zPos, xSize, zSize, 200.0D, 200.0D, 0.5D);
		this.noise3 = this.noiseGen3.generateNoiseOctaves(this.noise3, xPos, yPos, zPos, xSize, ySize, zSize, var44 / 80.0D, var45 / 160.0D, var44 / 80.0D);
		//this.noise3 = this.noiseGen3.generateNoiseOctaves(this.noise3, xPos, yPos, zPos, xSize, ySize, zSize, var44 / 80.0D, 0.5, var44 / 80.0D);
		this.noise1 = this.noiseGen1.generateNoiseOctaves(this.noise1, xPos, yPos, zPos, xSize, ySize, zSize, var44, var45, var44);
		this.noise2 = this.noiseGen2.generateNoiseOctaves(this.noise2, xPos, yPos, zPos, xSize, ySize, zSize, var44, var45, var44);
		boolean var43 = false;
		boolean var42 = false;
		int var12 = 0;
		int var13 = 0;

		for (int x = 0; x < xSize; ++x)
		{
			for (int z = 0; z < zSize; ++z)
			{
				float var16 = 0.0F;
				float var17 = 0.0F;
				float var18 = 0.0F;
				byte radius = 2;
				TFCBiome baseBiome = (TFCBiome)this.biomesForGeneration[x + 2 + (z + 2) * (xSize + 5)];

				for (int xR = -radius; xR <= radius; ++xR)
				{
					for (int zR = -radius; zR <= radius; ++zR)
					{
						TFCBiome blendBiome = (TFCBiome)this.biomesForGeneration[x + xR + 2 + (z + zR + 2) * (xSize + 5)];
						float blendedHeight = this.parabolicField[xR + 2 + (zR + 2) * 5] / 2.0F;
						if (blendBiome.rootHeight > baseBiome.rootHeight)
							blendedHeight *= 0.5F;

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
				for (int var46 = 0; var46 < ySize; ++var46)
				{
					double var48 = var17;
					double var26 = var16;
					var48 += var47 * 0.2D;
					var48 = var48 * ySize / 16.0D;
					double var28 = ySize / 2.0D + var48 * 4.0D;
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
					if (var46 > ySize - 4)
					{
						double var40 = (var46 - (ySize - 4)) / 3.0F;
						var30 = var30 * (1.0D - var40) + -10.0D * var40;
					}

					outArray[var12] = var30;
					++var12;
				}
			}
		}
		return outArray;
	}

	private void replaceBlocksForBiomeHigh(int chunkX, int chunkZ, Block[] idsTop, Random rand, Block[] idsBig, byte[] metaBig)
	{
		int seaLevel = 16;
		int worldHeight = 256;
		int indexOffset = 128;
		double var6 = 0.03125D;
		stoneNoise = noiseGen4.generateNoiseOctaves(stoneNoise, chunkX * 16, chunkZ * 16, 0, 16, 16, 1, var6 * 4.0D, var6 * 1.0D, var6 * 4.0D);
		boolean[] cliffMap = new boolean[256];
		for (int xCoord = 0; xCoord < 16; ++xCoord)
		{
			for (int zCoord = 0; zCoord < 16; ++zCoord)
			{
				int arrayIndex = xCoord + zCoord * 16;
				int arrayIndexDL = zCoord + xCoord * 16;
				int arrayIndex2 = xCoord+1 + zCoord+1 * 16;
				TFCBiome biome = (TFCBiome)getBiome(xCoord,zCoord);
				DataLayer rock1 = rockLayer1[arrayIndexDL] == null ? DataLayer.Granite : rockLayer1[arrayIndexDL];
				DataLayer rock2 = rockLayer2[arrayIndexDL] == null ? DataLayer.Granite : rockLayer2[arrayIndexDL];
				DataLayer rock3 = rockLayer3[arrayIndexDL] == null ? DataLayer.Granite : rockLayer3[arrayIndexDL];
				DataLayer evt = evtLayer[arrayIndexDL] == null ? DataLayer.EVT_0_125 : evtLayer[arrayIndexDL];
				float rain = rainfallLayer[arrayIndexDL] == null ? DataLayer.Rain_125.floatdata1 : rainfallLayer[arrayIndexDL].floatdata1;
				DataLayer drainage = drainageLayer[arrayIndexDL] == null ? DataLayer.DrainageNormal : drainageLayer[arrayIndexDL];
				int var12 = (int)(stoneNoise[arrayIndex2] / 3.0D + 6.0D);
				int var13 = -1;

				Block surfaceBlock = TFC_Core.getTypeForGrassWithRain(rock1.data1, rain);
				Block subSurfaceBlock = TFC_Core.getTypeForDirtFromGrass(surfaceBlock);

				float _temp = TFC_Climate.getBioTemperature(worldObj, chunkX * 16 + xCoord, chunkZ * 16 + zCoord);
				int h = 0;
				if(TFC_Core.isBeachBiome(getBiome(xCoord-1, zCoord).biomeID) || TFC_Core.isBeachBiome(getBiome(xCoord+1, zCoord).biomeID) || 
						TFC_Core.isBeachBiome(getBiome(xCoord, zCoord+1).biomeID) || TFC_Core.isBeachBiome(getBiome(xCoord, zCoord-1).biomeID))
				{
					if(!TFC_Core.isBeachBiome(getBiome(xCoord, zCoord).biomeID))
						cliffMap[arrayIndex] = true;
				}
				for (int height = 127; height >= 0; --height)
				{
					int indexBig = ((arrayIndex) * worldHeight + height + indexOffset);
					int index = ((arrayIndex) * 128 + height);
					//metaBig[indexBig] = 0;
					float temp = TFC_Climate.adjustHeightToTemp(height, _temp);
					if(TFC_Core.isBeachBiome(biome.biomeID) && height > seaLevel+h && idsTop[index] == Blocks.stone)
					{
						idsTop[index] = Blocks.air;
						if(h == 0)
							h = (height-16)/4;
					}
					if(idsBig[indexBig] == null)
					{
						idsBig[indexBig] = idsTop[index];
						if(indexBig+1 < idsBig.length && TFC_Core.isSoilOrGravel(idsBig[indexBig+1]) && idsBig[indexBig] == Blocks.air)
						{
							for(int upCount = 1; TFC_Core.isSoilOrGravel(idsBig[indexBig+upCount]); upCount++)
							{idsBig[indexBig+upCount] = Blocks.air;}
						}
					}

					if (idsBig[indexBig] == Blocks.stone)
					{
						if(seaLevelOffsetMap[arrayIndex] == 0 && height-16 >= 0)
							seaLevelOffsetMap[arrayIndex] = height-16;

						if(chunkHeightMap[arrayIndex] == 0)
							chunkHeightMap[arrayIndex] = height+indexOffset;

						convertStone(indexOffset+height, arrayIndex, indexBig, idsBig, metaBig, rock1, rock2, rock3);

						//First we check to see if its a cold desert
						if(rain < 125 && temp < 1.5f)
						{
							surfaceBlock = TFC_Core.getTypeForSand(rock1.data1);
							subSurfaceBlock = TFC_Core.getTypeForSand(rock1.data1);
						}
						//Next we check for all other warm deserts
						else if(rain < 125 && biome.heightVariation < 0.5f && temp > 20f)
						{
							surfaceBlock = TFC_Core.getTypeForSand(rock1.data1);
							subSurfaceBlock = TFC_Core.getTypeForSand(rock1.data1);
						}

						if(biome == TFCBiome.beach || biome == TFCBiome.ocean || biome == TFCBiome.DeepOcean)
						{
							subSurfaceBlock = surfaceBlock = TFC_Core.getTypeForSand(rock1.data1);
						}
						else if(biome == TFCBiome.gravelbeach)
						{
							subSurfaceBlock = surfaceBlock = TFC_Core.getTypeForGravel(rock1.data1);
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
								if(arrayIndexx >= 0 && seaLevelOffsetMap[arrayIndex]-(3*counter) > seaLevelOffsetMap[arrayIndexx])
								{
									seaLevelOffsetMap[arrayIndex]--;
									var12--;
									height--;
									indexBig = ((arrayIndex) * worldHeight + height + indexOffset);
									index = ((arrayIndex) * 128 + height);
								}
								else if(arrayIndexX >= 0 && seaLevelOffsetMap[arrayIndex]-(3*counter) > seaLevelOffsetMap[arrayIndexX])
								{
									seaLevelOffsetMap[arrayIndex]--;
									var12--;
									height--;
									indexBig = ((arrayIndex) * worldHeight + height + indexOffset);
									index = ((arrayIndex) * 128 + height);
								}
								else if(arrayIndexz >= 0 && seaLevelOffsetMap[arrayIndex]-(3*counter) > seaLevelOffsetMap[arrayIndexz])
								{
									seaLevelOffsetMap[arrayIndex]--;
									var12--;
									height--;
									indexBig = ((arrayIndex) * worldHeight + height + indexOffset);
									index = ((arrayIndex) * 128 + height);
								}
								else if(arrayIndexZ >= 0 && seaLevelOffsetMap[arrayIndex]-(3*counter) > seaLevelOffsetMap[arrayIndexZ])
								{
									seaLevelOffsetMap[arrayIndex]--;
									var12--;
									height--;
									indexBig = ((arrayIndex) * worldHeight + height + indexOffset);
									index = ((arrayIndex) * 128 + height);
								}
							}
							var13 = (int)(var12 * (1d-Math.max(Math.min(((height - 16) / 80d), 1), 0)));

							//Set soil below water
							for(int c = 1; c < 3; c++)
							{
								if(indexBig + c < idsBig.length && (
										(idsBig[indexBig + c] != surfaceBlock) &&
										(idsBig[indexBig + c] != subSurfaceBlock) &&
										(idsBig[indexBig + c] != TFCBlocks.SaltWaterStationary) &&
										(idsBig[indexBig + c] != TFCBlocks.FreshWaterStationary) &&
										(idsBig[indexBig + c] != TFCBlocks.HotWater)))
								{
									idsBig[indexBig + c] = Blocks.air;
									//metaBig[indexBig + c] = 0;
									if(indexBig + c + 1 < idsBig.length && idsBig[indexBig + c + 1] == TFCBlocks.SaltWaterStationary)
									{
										idsBig[indexBig + c] = subSurfaceBlock;
										metaBig[indexBig + c] = (byte)TFC_Core.getSoilMeta(rock1.data1);
									}
								}
							}

							//Determine the soil depth based on world height
							int dirtH = Math.max(8-((height + 96 - Global.SEALEVEL) / 16), 0);

							if(var13 > 0)
							{
								if (height >= seaLevel - 1 && index+1 < idsTop.length && idsTop[index + 1] != TFCBlocks.SaltWaterStationary && dirtH > 0)
								{
									idsBig[indexBig] = surfaceBlock;
									metaBig[indexBig] = (byte)TFC_Core.getSoilMeta(rock1.data1);


									for(int c = 1; c < dirtH && !TFC_Core.isMountainBiome(biome.biomeID) && 
											biome != TFCBiome.HighHills && biome != TFCBiome.HighHillsEdge && !cliffMap[arrayIndex]; c++)
									{
										int _height = height - c;
										int _indexBig = ((arrayIndex) * worldHeight + _height + indexOffset);
										idsBig[_indexBig] = subSurfaceBlock;
										metaBig[_indexBig] = (byte)TFC_Core.getSoilMeta(rock1.data1);

										if(c > 1+(5-drainage.data1))
										{
											idsBig[_indexBig] = TFC_Core.getTypeForGravel(rock1.data1);
											metaBig[_indexBig] = (byte)TFC_Core.getSoilMeta(rock1.data1);
										}
									}
								}
							}
						}
						if(!(biome == TFCBiome.swampland))
						{
							if(((height > seaLevel - 2 && height < seaLevel && idsTop[index + 1] == TFCBlocks.SaltWaterStationary)) || (height < seaLevel && idsTop[index + 1] == TFCBlocks.SaltWaterStationary))
							{
								if(idsBig[indexBig] != TFC_Core.getTypeForSand(rock1.data1) && rand.nextInt(5) != 0)
								{
									idsBig[indexBig] = TFC_Core.getTypeForGravel(rock1.data1);
									metaBig[indexBig] = (byte)TFC_Core.getSoilMeta(rock1.data1);
								}
							}
						}
					}
					else if(idsTop[index] == TFCBlocks.SaltWaterStationary && biome != TFCBiome.ocean && biome != TFCBiome.DeepOcean && biome != TFCBiome.beach && biome != TFCBiome.gravelbeach)
					{
						idsBig[indexBig] = TFCBlocks.FreshWaterStationary;
					}
				}
			}
		}

		/*for (int x = 0; x < 16; ++x)
		{
			for (int z = 0; z < 16; ++z)
			{
				int arrayIndex = getIndex(x, z);
				int arrayIndex2 = getIndex(x+1, z+1);

			}
		}*/
	}

	private int getIndex(int x, int z)
	{
		return x + z * 16;
	}

	private void replaceBlocksForBiomeLow(int par1, int par2, Random rand, Block[] idsBig, byte[] metaBig)
	{
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
				TFCBiome biome = (TFCBiome) getBiome(xCoord, zCoord);

				for (int height = 127; height >= 0; --height)
				{
					int index = ((arrayIndex) * 128 + height);
					int indexBig = ((arrayIndex) * 256 + height);
					metaBig[indexBig] = 0;

					if (height <= 1 + (seaLevelOffsetMap[arrayIndex] / 3) + this.rand.nextInt(3))
					{
						idsBig[indexBig] = Blocks.bedrock;
					}
					else if(idsBig[indexBig] == null)
					{
						convertStone(height, arrayIndex, indexBig, idsBig, metaBig, rock1, rock2, rock3);
						if(TFC_Core.isBeachBiome(biome.biomeID) || TFC_Core.isOceanicBiome(biome.biomeID))
						{
							if(idsBig[indexBig+1] == TFCBlocks.SaltWaterStationary)
							{
								idsBig[indexBig] = TFC_Core.getTypeForSand(rock1.data1);
								metaBig[indexBig] = (byte)TFC_Core.getSoilMeta(rock1.data1);
								idsBig[indexBig-1] = TFC_Core.getTypeForSand(rock1.data1);
								metaBig[indexBig-1] = (byte)TFC_Core.getSoilMeta(rock1.data1);
							}
						}
					}

					if (height <= 6 && stability.data1 == 1 && idsBig[indexBig] == Blocks.air)
					{
						idsBig[indexBig] = TFCBlocks.Lava;
						metaBig[indexBig] = 0; 
						if(idsBig[indexBig+1] != TFCBlocks.Lava && rand.nextBoolean())
						{
							idsBig[indexBig+1] = TFCBlocks.Lava;
							metaBig[indexBig+1] = 0; 
						}
					}
				}
			}
		}
	}

	public void convertStone(int height, int indexArray, int indexBig, Block[] idsBig, byte[] metaBig, DataLayer rock1, DataLayer rock2, DataLayer rock3)
	{
		if(idsBig[indexBig] != null && idsBig[indexBig] != Blocks.stone)
			return;
		if(height <= TFCOptions.RockLayer3Height + seaLevelOffsetMap[indexArray])
		{
			idsBig[indexBig] = rock3.block;
			metaBig[indexBig] = (byte) rock3.data2;
		}
		else if(height <= TFCOptions.RockLayer2Height + seaLevelOffsetMap[indexArray] && height > 55+seaLevelOffsetMap[indexArray] && rock2!=null)
		{
			idsBig[indexBig] = rock2.block; 
			metaBig[indexBig] = (byte) rock2.data2;
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
