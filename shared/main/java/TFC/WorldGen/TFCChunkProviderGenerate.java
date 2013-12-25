package TFC.WorldGen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderGenerate;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import TFC.Chunkdata.ChunkData;
import TFC.Chunkdata.ChunkDataManager;
import TFC.Core.TFC_Climate;
import TFC.Core.TFC_Core;
import TFC.Entities.Mobs.EntityBear;
import TFC.Entities.Mobs.EntityChickenTFC;
import TFC.Entities.Mobs.EntityCowTFC;
import TFC.Entities.Mobs.EntityDeer;
import TFC.Entities.Mobs.EntityPigTFC;
import TFC.Entities.Mobs.EntitySheepTFC;
import TFC.Entities.Mobs.EntityWolfTFC;
import TFC.WorldGen.Generators.WorldGenLakesTFC;

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

	private short[] idsTop;
	private short[] idsBig;
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


	public TFCChunkProviderGenerate(World par1World, long par2, boolean par4) {
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

		this.idsTop = new short[32768];
		this.idsBig = new short[16*16*256];
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
		Arrays.fill(idsTop, (short)0);
		Arrays.fill(idsBig, (short)0);
		Arrays.fill(metaBig, (byte)0);

		this.generateTerrainHigh(chunkX, chunkZ, idsTop);

		biomesForGeneration = ((TFCWorldChunkManager)this.worldObj.getWorldChunkManager()).loadBlockGeneratorData(biomesForGeneration, chunkX * 16, chunkZ * 16, 16, 16);
		rockLayer1 = ((TFCWorldChunkManager)this.worldObj.getWorldChunkManager()).loadRockLayerGeneratorData(rockLayer1, chunkX * 16, chunkZ * 16, 16, 16, 0);
		rockLayer2 = ((TFCWorldChunkManager)this.worldObj.getWorldChunkManager()).loadRockLayerGeneratorData(rockLayer2, chunkX * 16, chunkZ * 16, 16, 16, 1);
		rockLayer3 = ((TFCWorldChunkManager)this.worldObj.getWorldChunkManager()).loadRockLayerGeneratorData(rockLayer3, chunkX * 16, chunkZ * 16, 16, 16, 2);
		evtLayer = ((TFCWorldChunkManager)this.worldObj.getWorldChunkManager()).loadEVTLayerGeneratorData(evtLayer, chunkX * 16, chunkZ * 16, 16, 16);
		rainfallLayer = ((TFCWorldChunkManager)this.worldObj.getWorldChunkManager()).loadRainfallLayerGeneratorData(rainfallLayer, chunkX * 16, chunkZ * 16, 16, 16);

		heightMap = new int[256];
		replaceBlocksForBiomeHigh(chunkX, chunkZ, idsTop, rand, idsBig, metaBig);
		replaceBlocksForBiomeLow(chunkX, chunkZ, rand, idsBig, metaBig);


		new MapGenCavesTFC().generate(this, this.worldObj, chunkX, chunkZ, idsBig, metaBig);
		new MapGenRavine256TFC().generate(this, this.worldObj, chunkX, chunkZ, idsBig, metaBig);
		new MapGenRiverRavine256TFC().generate(this, this.worldObj, chunkX, chunkZ, idsBig, metaBig);

		ChunkTFC var4 = new ChunkTFC(this.worldObj, idsBig, metaBig, chunkX, chunkZ);

		ChunkData data = new ChunkData().CreateNew(chunkX, chunkZ);
		String key = data.chunkX + "," + data.chunkZ;
		if(!this.worldObj.isRemote) {
			ChunkDataManager.chunkmap.put(key, data);
		}

		var4.generateSkylightMap();
		return var4;
	}

	@Override
	public void populate(IChunkProvider par1IChunkProvider, int chunkX, int chunkZ)
	{
		int xCoord = chunkX * 16;
		int zCoord = chunkZ * 16;
		BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(xCoord + 16, zCoord + 16);
		this.rand.setSeed(this.worldObj.getSeed());
		long var7 = this.rand.nextLong() / 2L * 2L + 1L;
		long var9 = this.rand.nextLong() / 2L * 2L + 1L;
		this.rand.setSeed(chunkX * var7 + chunkZ * var9 ^ this.worldObj.getSeed());
		boolean var11 = false;

		int var12;
		int var13;
		int var14;

		if (!var11 && this.rand.nextInt(4) == 0)
		{
			var12 = xCoord + this.rand.nextInt(16) + 8;
			var13 = this.rand.nextInt(128);
			var14 = zCoord + this.rand.nextInt(16) + 8;
			(new WorldGenLakesTFC(Block.waterStill.blockID)).generate(this.worldObj, this.rand, var12, var13, var14);
		}

		if (!var11 && this.rand.nextInt(8) == 0)
		{
			var12 = xCoord + this.rand.nextInt(16) + 8;
			var13 = this.rand.nextInt(this.rand.nextInt(120) + 8);
			var14 = zCoord + this.rand.nextInt(16) + 8;

			if (var13 < 40)
			{
				(new WorldGenLakesTFC(Block.lavaStill.blockID)).generate(this.worldObj, this.rand, var12, var13, var14);
			}
		}

		biome.decorate(this.worldObj, this.rand, xCoord, zCoord);
		SpawnerAnimalsTFC.performWorldGenSpawning(this.worldObj, biome, xCoord + 8, zCoord + 8, 16, 16, this.rand);
		xCoord += 8;
		zCoord += 8;

		for (var12 = 0; var12 < 16; ++var12)
		{
			for (var13 = 0; var13 < 16; ++var13)
			{
				var14 = this.worldObj.getPrecipitationHeight(xCoord + var12, zCoord + var13);

				if (this.worldObj.isBlockFreezable(var12 + xCoord, var14 - 1, var13 + zCoord))
				{
					this.worldObj.setBlock(var12 + xCoord, var14 - 1, var13 + zCoord, Block.ice.blockID, 0, 0x2);
				}

				if (canSnowAt(worldObj, var12 + xCoord, var14, var13 + zCoord))
				{
					this.worldObj.setBlock(var12 + xCoord, var14, var13 + zCoord, Block.snow.blockID, 0, 0x2);
				}
			}
		}
	}

	public static List getCreatureSpawnsByChunk(World world,BiomeGenBase biome, int x, int z){
		List spawnableCreatureList = new ArrayList();
		spawnableCreatureList.add(new SpawnListEntry(EntityChickenTFC.class,24,0,0));
		float temp = TFC_Climate.getBioTemperature(x, z);
		float rain = TFC_Climate.getRainfall(x, 150, z);
		float evt = TFC_Climate.manager.getEVTLayerAt(x, z).floatdata1;
		boolean isMountainous = biome == TFCBiome.Mountains || biome == TFCBiome.HighHills;
		//To adjust animal spawning at higher altitudes
		int mountainousAreaModifier = isMountainous?-1:0;
		if(isMountainous){
			//Mountains, but not too hot or too dry
			if(temp<25 && rain >250 && evt < 0.75 && temp > -10){
				spawnableCreatureList.add(new SpawnListEntry(EntityWolfTFC.class, 2, 1, 3));
				spawnableCreatureList.add(new SpawnListEntry(EntityBear.class, 1, 1, 1));
				spawnableCreatureList.add(new SpawnListEntry(EntitySheepTFC.class, 2, 2, 4));
			}
		}
		else{
			//run of the mill plains, but not too cold
			if(temp > 10 && rain > 100 && rain <= 500)
			{
				if(temp < 30)
				{
					spawnableCreatureList.add(new SpawnListEntry(EntityCowTFC.class, 2, 2, 4));
					spawnableCreatureList.add(new SpawnListEntry(EntityPigTFC.class, 1, 1, 2));
				}
				else
				{
					spawnableCreatureList.add(new SpawnListEntry(EntityCowTFC.class, 1, 1, 2));
				}
			}
		}
		//regular temperate forest
		if(temp > 15 &&temp < 28 && rain > 250){
			spawnableCreatureList.add(new SpawnListEntry(EntityPigTFC.class, 2+mountainousAreaModifier, 2+mountainousAreaModifier, 3+mountainousAreaModifier));
			spawnableCreatureList.add(new SpawnListEntry(EntityWolfTFC.class, 1, 1, 2+mountainousAreaModifier));
			spawnableCreatureList.add(new SpawnListEntry(EntityBear.class, 1, 1, 1));
			spawnableCreatureList.add(new SpawnListEntry(EntityDeer.class, 2+mountainousAreaModifier, 1, 3+mountainousAreaModifier));
			spawnableCreatureList.add(new SpawnListEntry(EntityChickenTFC.class, 1+mountainousAreaModifier, 1, 1));

		}
		//colder climate
		if(temp > -10 && temp <=15){
			//boreal forest
			if(rain > 250){
				spawnableCreatureList.add(new SpawnListEntry(EntityPigTFC.class, 1+mountainousAreaModifier, 1, 2));
				spawnableCreatureList.add(new SpawnListEntry(EntityWolfTFC.class, 2+mountainousAreaModifier, 1, 2+mountainousAreaModifier));
				spawnableCreatureList.add(new SpawnListEntry(EntityBear.class, 2+mountainousAreaModifier, 1, 1));
				spawnableCreatureList.add(new SpawnListEntry(EntityDeer.class, 1+mountainousAreaModifier, 2, 3));
			}
			//closer to tundra or taiga
			else if(rain >100){
				spawnableCreatureList.add(new SpawnListEntry(EntityWolfTFC.class, 1+mountainousAreaModifier, 1, 1));
				spawnableCreatureList.add(new SpawnListEntry(EntityDeer.class, 1+mountainousAreaModifier, 1, 1));
			}
		}
		//Jungle
		if(temp >= 28 && temp < 44 && rain > 1500){
			spawnableCreatureList.add(new SpawnListEntry(EntityPigTFC.class, 2+mountainousAreaModifier, 2+mountainousAreaModifier, 4+mountainousAreaModifier));
			spawnableCreatureList.add(new SpawnListEntry(EntityChickenTFC.class, 3+mountainousAreaModifier, 1, 4+mountainousAreaModifier));
		}
		//Swamp
		if(TFC_Climate.isSwamp(x,150,z)){
			spawnableCreatureList.add(new SpawnListEntry(EntityPigTFC.class, 1, 1, 2));
		}

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
				int var6 = world.getBlockId(par1, par2 - 1, par3);
				int var7 = world.getBlockId(par1, par2, par3);

				if (var7 == 0 && Block.snow.canPlaceBlockAt(world, par1, par2, par3) && var6 != 0 && var6 != Block.ice.blockID && Block.blocksList[var6].blockMaterial.blocksMovement())
				{
					return true;
				}
			}

			return false;
		}
	}

	public void generateTerrainHigh(int par1, int par2, short[] idsTop)
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
								{
									idsTop[var43 += var44] = (short)Block.stone.blockID;
								}
								else if (var12 * 8 + var31 < seaLevel)
								{
									idsTop[var43 += var44] = (short)Block.waterStill.blockID;
								}
								else
								{
									idsTop[var43 += var44] = 0;
								}
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
		{
			par1ArrayOfDouble = new double[par5 * par6 * par7];
		}

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
				BiomeGenBase baseBiome = this.biomesForGeneration[var14 + 2 + (var15 + 2) * (par5 + 5)];

				for (int var21 = -var19; var21 <= var19; ++var21)
				{
					for (int var22 = -var19; var22 <= var19; ++var22)
					{
						BiomeGenBase blendBiome = this.biomesForGeneration[var14 + var21 + 2 + (var15 + var22 + 2) * (par5 + 5)];
						float blendedHeight = this.parabolicField[var21 + 2 + (var22 + 2) * 5] / (/*blendBiome.minHeight*/ + 2.0F);//<---blendBiome.minHeight was commented out

						if (blendBiome.minHeight > baseBiome.minHeight)
						{
							blendedHeight *= 0.3F;
						}

						var16 += blendBiome.maxHeight * blendedHeight;
						var17 += blendBiome.minHeight * blendedHeight;
						var18 += blendedHeight;
					}
				}

				var16 /= var18;
				var17 /= var18;
				var16 = var16 * 0.9F + 0.1F;
				var17 = (var17 * 4.0F - 1.0F) / 8.0F;
				double var47 = this.noise6[var13] / 8000.0D;

				if (var47 < 0.0D)
				{
					var47 = -var47 * 0.3D;
				}

				var47 = var47 * 3.0D - 2.0D;

				if (var47 < 0.0D)
				{
					var47 /= 2.0D;

					if (var47 < -1.0D)
					{
						var47 = -1.0D;
					}

					var47 /= 1.4D;
					var47 /= 2.0D;
				}
				else
				{
					if (var47 > 1.0D)
					{
						var47 = 1.0D;
					}

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
					{
						var32 *= 4.0D;
					}

					double var34 = this.noise1[var12] / 512.0D;
					double var36 = this.noise2[var12] / 512.0D;
					double var38 = (this.noise3[var12] / 10.0D + 1.0D) / 2.0D;

					if (var38 < 0.0D)
					{
						var30 = var34;
					}
					else if (var38 > 1.0D)
					{
						var30 = var36;
					}
					else
					{
						var30 = var34 + (var36 - var34) * var38;
					}

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

	private void replaceBlocksForBiomeHigh(int chunkX, int chunkZ, short[] idsTop, Random rand, short[] idsBig, byte[] metaBig)
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
				BiomeGenBase biomegenbase = biomesForGeneration[arrayIndexDL];
				DataLayer rock1 = rockLayer1[arrayIndexDL];
				DataLayer rock2 = rockLayer2[arrayIndexDL];
				DataLayer rock3 = rockLayer3[arrayIndexDL];
				DataLayer evt = evtLayer[arrayIndexDL];
				DataLayer rainfall = rainfallLayer[arrayIndexDL];

				int var12 = (int)(stoneNoise[arrayIndex] / 3.0D + 6.0D + rand.nextDouble() * 0.25D);  
				int var13 = -1;

				short surfaceBlock = (short) TFC_Core.getTypeForGrassWithRain(TFC_Core.getItemMetaFromStone(rock1.data1, rock1.data2), rainfall.floatdata1);
				short subSurfaceBlock = (short)TFC_Core.getTypeForDirt(TFC_Core.getItemMetaFromStone(rock1.data1, rock1.data2));
				byte soilMeta = (byte) TFC_Core.getSoilMetaFromStone(rock1.data1, rock1.data2);

				float _temp = TFC_Climate.getBioTemperature(chunkX * 16 + xCoord, chunkZ * 16 + zCoord);

				for (int height = 127; height >= 0; --height)
				{
					int indexBig = ((arrayIndex) * 256 + height + 128);
					int index = ((arrayIndex) * 128 + height);
					metaBig[indexBig] = 0;

					float temp = TFC_Climate.adjustHeightToTemp(height, _temp);

					int var18 = idsTop[index];
					idsBig[indexBig] = idsTop[index];

					if (var18 == 0)
					{
						if(heightMap[arrayIndex] != 0 && height-16 >= 0)
						{
							heightMap[arrayIndex] = 0;
						}
						var13 = -1;
					}
					else if (var18 == Block.stone.blockID)
					{
						if(heightMap[arrayIndex] == 0 && height-16 >= 0)
						{
							heightMap[arrayIndex] = height-16;
						}

						convertStone(128+height, arrayIndex, indexBig, idsBig, metaBig, rock1, rock2, rock3);       


						//First we check to see if its a cold desert
						if(rainfall.floatdata1 < 125 && 
								temp < 1.5f)
						{
							surfaceBlock = (short) TFC_Core.getTypeForSand(soilMeta);
							subSurfaceBlock = (short) TFC_Core.getTypeForSand(soilMeta);
						}
						//Next we check for all other warm deserts
						else if(rainfall.floatdata1 < 125 && biomegenbase.maxHeight < 0.5f && 
								temp > 20f)
						{
							surfaceBlock = (short) TFC_Core.getTypeForSand(soilMeta);
							subSurfaceBlock = (short) TFC_Core.getTypeForSand(soilMeta);
						}				        


						if (var13 == -1)
						{
							//							if (var12 <= 0)
							//							{
							//								surfaceBlock = 0;
							//							}

							var13 = var12;


							if (height >= var5 - 1 && index+1 < idsTop.length && idsTop[index+1] != Block.waterStill.blockID)
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
						else if (var13 > 0)
						{
							--var13;
							idsBig[indexBig] = subSurfaceBlock;
							metaBig[indexBig] = soilMeta;

							//							if (var13 == 0 && subSurfaceBlock == TFC_Core.getTypeForSand(soilMeta))
							//							{
							//								var13 = rand.nextInt(4);
							//								subSurfaceBlock = TFC_Core.getTypeForSand(soilMeta);
							//							}
						}

						if(biomegenbase.biomeID == 0)
						{
							if(((height > var5-2 && height <= var5+1) || (height < var5 && idsTop[index+2] == Block.waterStill.blockID)))//If its an ocean give it a sandy bottom
							{

								idsBig[indexBig] = (short) TFC_Core.getTypeForSand(soilMeta);
								metaBig[indexBig] = soilMeta;
							}
						}
						//						else if(biomegenbase.biomeID == BiomeGenBase.beach.biomeID)
						//						{
						//							if((height >= var5 && height < var5+2 && blockArray[index] != Block.waterStill.blockID))//If its a beach make it sandy
						//							{
						//								blockArrayBig[indexBig] = (byte) TFC_Core.getTypeForSand(soilMeta);
						//								metaArrayBig[indexBig] = (byte) soilMeta;
						//							}
						//						}
						else if(!(biomegenbase.biomeID == TFCBiome.swampland.biomeID))
						{
							if(((height > var5-2 && height < var5 && idsTop[index+1] == Block.waterStill.blockID) || (height < var5 && idsTop[index+1] == Block.waterStill.blockID)))//If its an ocean give it a sandy bottom
							{
								if(idsBig[indexBig] != TFC_Core.getTypeForSand(soilMeta) && rand.nextInt(5) != 0)
								{
									idsBig[indexBig] = (short) Block.gravel.blockID;
									metaBig[indexBig] = 0;
								}
							}
						}
					}
				}
			}
		}
	}

	private double[] layer2Noise = new double[256];
	private void replaceBlocksForBiomeLow(int par1, int par2,  Random rand, short[] idsBig, byte[] metaBig)
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

				int var12 = (int)(stoneNoise[arrayIndex] / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
				int var13 = -1;

				int top = 0;

				for (int height = 127; height >= 0; --height)
				{
					int index = ((arrayIndex) * 128 + height);
					int indexBig = ((arrayIndex) * 256 + height);

					metaBig[indexBig] = 0;

					if (height >= 2 && height <= 6)
					{
						idsBig[indexBig] = (short) Block.lavaStill.blockID;
						metaBig[indexBig] = 0; 

						if(idsBig[indexBig+1] != (short) Block.lavaStill.blockID && rand.nextBoolean())
						{
							idsBig[indexBig+1] = (short) Block.lavaStill.blockID;
							metaBig[indexBig+1] = 0; 
						}
					}
					else if (height <= 1)
					{
						idsBig[indexBig] = (byte) Block.bedrock.blockID;
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
				}
			}
		}
	}

	public void convertStone(int height, int indexArray, int indexBig, short[] idsBig, byte[] metaBig, DataLayer rock1, DataLayer rock2, DataLayer rock3)
	{
		if(height <= 55+heightMap[indexArray])
		{
			idsBig[indexBig] = (short) rock3.data1; 
			metaBig[indexBig] = (byte) rock3.data2;
			if(height == 55+heightMap[indexArray])
			{
				if(rand.nextBoolean())
				{
					idsBig[indexBig+1] = (short) rock3.data1; 
					metaBig[indexBig+1] = (byte) rock3.data2;
					if(rand.nextBoolean())
					{
						idsBig[indexBig+2] = (short) rock3.data1; 
						metaBig[indexBig+2] = (byte) rock3.data2;
						if(rand.nextBoolean())
						{
							idsBig[indexBig+3] = (short) rock3.data1; 
							metaBig[indexBig+3] = (byte) rock3.data2;
						}
					}
				}
			}
		}
		else if(height <= 110+heightMap[indexArray] && height > 55+heightMap[indexArray])
		{
			idsBig[indexBig] = (short) rock2.data1; 
			metaBig[indexBig] = (byte) rock2.data2;
			if(height == 110+heightMap[indexArray])
			{
				if(rand.nextBoolean())
				{
					idsBig[indexBig+1] = (short) rock2.data1; 
					metaBig[indexBig+1] = (byte) rock2.data2;
					if(rand.nextBoolean())
					{
						idsBig[indexBig+2] = (short) rock2.data1; 
						metaBig[indexBig+2] = (byte) rock2.data2;
						if(rand.nextBoolean())
						{
							idsBig[indexBig+3] = (short) rock2.data1; 
							metaBig[indexBig+3] = (byte) rock2.data2;
						}
					}
				}
			}
		}
		else
		{
			idsBig[indexBig] = (short) rock1.data1; 
			metaBig[indexBig] = (byte) rock1.data2;
		}
	}

	@Override
	public boolean unloadQueuedChunks()
	{
		return true;
	}
}


