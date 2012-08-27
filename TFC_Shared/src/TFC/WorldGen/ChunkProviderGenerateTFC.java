package TFC.WorldGen;

import java.util.Random;

import TFC.WorldGen.Generators.WorldGenLakesTFC;

import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Block;
import net.minecraft.src.BlockSand;
import net.minecraft.src.Chunk;
import net.minecraft.src.ChunkProviderGenerate;
import net.minecraft.src.EnumSkyBlock;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.MapGenBase;
import net.minecraft.src.MapGenMineshaft;
import net.minecraft.src.MapGenScatteredFeature;
import net.minecraft.src.MapGenStronghold;
import net.minecraft.src.MapGenVillage;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NoiseGeneratorOctaves;
import net.minecraft.src.SpawnerAnimals;
import net.minecraft.src.TFCBlocks;
import net.minecraft.src.World;

public class ChunkProviderGenerateTFC extends ChunkProviderGenerate
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

	private MapGenRiverRavineTFC underRiverGen = new MapGenRiverRavineTFC();

	/** The biomes that are used to generate the chunk */
	private BiomeGenBase[] biomesForGeneration;

	private DataLayer[] rockLayer1;
	private DataLayer[] rockLayer2;
	private DataLayer[] rockLayer3;

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
	int[][] field_73219_j = new int[32][32];

	public ChunkProviderGenerateTFC(World par1World, long par2, boolean par4) {
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
	}

	public Chunk provideChunk(int par1, int par2)
	{
		this.rand.setSeed((long)par1 * 341873128712L + (long)par2 * 132897987541L);

		byte[] ids = new byte[32768];
		byte[] ids2 = new byte[32768];
		byte[] meta = new byte[32768];
		byte[] meta2 = new byte[32768];

		byte[] ids3 = new byte[16*16*256];
		byte[] meta3 = new byte[16*16*256];

		this.generateTerrainHigh(par1, par2, ids2);
		
		this.biomesForGeneration = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, par1 * 16, par2 * 16, 16, 16);
		rockLayer1 = ((TFCWorldChunkManager)this.worldObj.getWorldChunkManager()).loadRockLayerGeneratorData(rockLayer1, par1 * 16, par2 * 16, 16, 16, 0);
		rockLayer2 = ((TFCWorldChunkManager)this.worldObj.getWorldChunkManager()).loadRockLayerGeneratorData(rockLayer2, par1 * 16, par2 * 16, 16, 16, 1);
		rockLayer3 = ((TFCWorldChunkManager)this.worldObj.getWorldChunkManager()).loadRockLayerGeneratorData(rockLayer3, par1 * 16, par2 * 16, 16, 16, 2);

		replaceBlocksForBiomeHigh(par1, par2, ids2,meta2, rand, ids3, meta3);
		replaceBlocksForBiomeLow(par1, par2, ids,meta, rand, ids3, meta3);


		new MapGenCaves256TFC().generate(this, this.worldObj, par1, par2, ids3, meta3);
		new MapGenRavine256TFC().generate(this, this.worldObj, par1, par2, ids3, meta3);
		new MapGenRiverRavine256TFC().generate(this, this.worldObj, par1, par2, ids3, meta3);

		Chunk var4 = new ChunkTFC(this.worldObj, ids3, meta3, par1, par2);

		var4.generateSkylightMap();
		return var4;
	}

	public void populate(IChunkProvider par1IChunkProvider, int par2, int par3)
	{
		BlockSand.fallInstantly = true;
		int var4 = par2 * 16;
		int var5 = par3 * 16;
		TFCBiome var6 = (TFCBiome)this.worldObj.getBiomeGenForCoords(var4 + 16, var5 + 16);
		this.rand.setSeed(this.worldObj.getSeed());
		long var7 = this.rand.nextLong() / 2L * 2L + 1L;
		long var9 = this.rand.nextLong() / 2L * 2L + 1L;
		this.rand.setSeed((long)par2 * var7 + (long)par3 * var9 ^ this.worldObj.getSeed());
		boolean var11 = false;

		int var12;
		int var13;
		int var14;

		if (!var11 && this.rand.nextInt(4) == 0)
		{
			var12 = var4 + this.rand.nextInt(16) + 8;
			var13 = this.rand.nextInt(128);
			var14 = var5 + this.rand.nextInt(16) + 8;
			(new WorldGenLakesTFC(Block.waterStill.blockID)).generate(this.worldObj, this.rand, var12, var13, var14);
		}

		if (!var11 && this.rand.nextInt(8) == 0)
		{
			var12 = var4 + this.rand.nextInt(16) + 8;
			var13 = this.rand.nextInt(this.rand.nextInt(120) + 8);
			var14 = var5 + this.rand.nextInt(16) + 8;

			if (var13 < 40)
			{
				(new WorldGenLakesTFC(Block.lavaStill.blockID)).generate(this.worldObj, this.rand, var12, var13, var14);
			}
		}

		var6.decorate(this.worldObj, this.rand, var4, var5);
		SpawnerAnimals.performWorldGenSpawning(this.worldObj, var6, var4 + 8, var5 + 8, 16, 16, this.rand);
		var4 += 8;
		var5 += 8;

		for (var12 = 0; var12 < 16; ++var12)
		{
			for (var13 = 0; var13 < 16; ++var13)
			{
				var14 = this.worldObj.getPrecipitationHeight(var4 + var12, var5 + var13);

				if (this.worldObj.isBlockFreezable(var12 + var4, var14 - 1, var13 + var5))
				{
					this.worldObj.setBlockWithNotify(var12 + var4, var14 - 1, var13 + var5, Block.ice.blockID);
				}

				if (canSnowAt(worldObj, var12 + var4, var14, var13 + var5))
				{
					this.worldObj.setBlockWithNotify(var12 + var4, var14, var13 + var5, Block.snow.blockID);
				}
			}
		}

		BlockSand.fallInstantly = false;
	}

	public boolean canSnowAt(World world, int par1, int par2, int par3)
	{
		TFCBiome var4 = (TFCBiome)world.getBiomeGenForCoords(par1, par3);
		float var5 = var4.getHeightAdjustedTemperature(par2);

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

	public void generateTerrainHigh(int par1, int par2, byte[] blockArray)
	{
		byte var4 = 4;
		byte var5 = 16;
		int seaLevel = 16;
		int var7 = var4 + 1;
		byte var8 = 17;
		int var9 = var4 + 1;
		this.biomesForGeneration = this.worldObj.getWorldChunkManager().getBiomesForGeneration(this.biomesForGeneration, par1 * 4 - 2, par2 * 4 - 2, var7 + 5, var9 + 5);
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
									blockArray[var43 += var44] = (byte)Block.stone.blockID;
								}
								else if (var12 * 8 + var31 < seaLevel)
								{
									blockArray[var43 += var44] = (byte)Block.waterStill.blockID;
								}
								else
								{
									blockArray[var43 += var44] = 0;
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
					float var10 = 10.0F / MathHelper.sqrt_float((float)(var8 * var8 + var9 * var9) + 0.2F);
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
						float blendedHeight = this.parabolicField[var21 + 2 + (var22 + 2) * 5] / (blendBiome.minHeight + 2.0F);//<---blendBiome.minHeight was commented out

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
					double var48 = (double)var17;
					double var26 = (double)var16;
					var48 += var47 * 0.2D;
					var48 = var48 * (double)par6 / 16.0D;
					double var28 = (double)par6 / 2.0D + var48 * 4.0D;
					double var30 = 0.0D;
					double var32 = ((double)var46 - var28) * 12.0D * 256.0D / 256.0D / (2.70 + var26);

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
						double var40 = (double)((float)(var46 - (par6 - 4)) / 3.0F);
						var30 = var30 * (1.0D - var40) + -10.0D * var40;
					}

					par1ArrayOfDouble[var12] = var30;
					++var12;
				}
			}
		}

		return par1ArrayOfDouble;
	}

	/**This version performs all the intial block placement in the initial loop, eliminating
	 * a major source of chunkloader lag.*/
	public void replaceBlocksForBiomeHigh(int par1, int par2, byte[] blockArray, byte[] metaArray, Random rand, byte[] blockArrayBig, byte[] metaArrayBig)
	{
		int var5 = 16;
		double var6 = 0.03125D;
		stoneNoise = noiseGen4.generateNoiseOctaves(stoneNoise, par1 * 16, par2 * 16, 0, 16, 16, 1, var6 * 2.0D, var6 * 2.0D, var6 * 2.0D);
		
		

		for (int xCoord = 0; xCoord < 16; ++xCoord)
		{
			for (int zCoord = 0; zCoord < 16; ++zCoord)
			{
				TFCBiome biomegenbase = (TFCBiome) biomesForGeneration[zCoord + xCoord * 16];
				DataLayer rock1 = rockLayer1[zCoord + xCoord * 16];
				DataLayer rock2 = rockLayer2[zCoord + xCoord * 16];
				DataLayer rock3 = rockLayer3[zCoord + xCoord * 16];

				float var11 = biomegenbase.getFloatTemperature();
				int var12 = (int)(stoneNoise[xCoord + zCoord * 16] / 3.0D + 3.0D + rand.nextDouble() * 0.25D);  
				int var13 = -1;
				int var14 = biomegenbase.GrassID;
				int var15 = biomegenbase.DirtID;
				int soilMeta = 0;

				for (int height = 127; height >= 0; --height)
				{
					int indexBig = ((zCoord * 16 + xCoord) * 256 + height + 128);
					int index = ((zCoord * 16 + xCoord) * 128 + height);
					metaArrayBig[indexBig] = 0;

					int var18 = blockArray[index];
					blockArrayBig[indexBig] = blockArray[index];
					metaArrayBig[indexBig] = metaArray[index];

					if (var18 == 0)
					{
						var13 = -1;
					}
					else if (var18 == Block.stone.blockID)
					{

						blockArrayBig[indexBig] = (byte) rock1.data1; 
						metaArrayBig[indexBig] = (byte)  rock1.data2;    
						
						

						if(rock1.data1 == TFCBlocks.terraStoneIgIn.blockID)
							soilMeta = rock1.data2;
				        else if(rock1.data1 == TFCBlocks.terraStoneSed.blockID)
				        	soilMeta = rock1.data2+3;
				        else if(rock1.data1 == TFCBlocks.terraStoneIgEx.blockID)
				        	soilMeta = rock1.data2+13;
				        else
				        	soilMeta = rock1.data2+17;
				            

				        if(soilMeta < 16)
				        {
				            var14 = TFCBlocks.terraGrass.blockID;
				            var15 = TFCBlocks.terraDirt.blockID;
				        }
				        else
				        {
				            var14 = TFCBlocks.terraGrass2.blockID;
				            var15 = TFCBlocks.terraDirt2.blockID;
				        }

						if (var13 == -1)
						{
							if (var12 <= 0)
							{
								var14 = 0;
							}

							if (height < var5 && var14 == 0)
							{
								if (var11 < 0.15F)
								{
									var14 = Block.ice.blockID;
								}
								else if (biomegenbase.getFloatRain() == 0)
								{
									var14 = 0;
								}
								else
								{
									var14 = Block.waterStill.blockID;
								}
							}

							var13 = var12;

							if (height >= var5 - 1 && index+1 < blockArray.length && blockArray[index+1] != Block.waterStill.blockID)
							{
								blockArrayBig[indexBig] = (byte) var14;
								metaArrayBig[indexBig] = (byte) biomegenbase.TopSoilMetaID;
							}
							else
							{
								blockArrayBig[indexBig] = (byte) var15;
								metaArrayBig[indexBig] = (byte) biomegenbase.TopSoilMetaID;
							}
						}
						else if (var13 > 0)
						{
							--var13;
							blockArrayBig[indexBig] = (byte) var15;
							metaArrayBig[indexBig] = (byte) biomegenbase.TopSoilMetaID;

							if (var13 == 0 && var15 == Block.sand.blockID)
							{
								var13 = rand.nextInt(4);
								var15 = Block.sandStone.blockID;
							}
						}

						if(biomegenbase.biomeID == 0)
						{
							if(((height > var5-2 && height <= var5+1) || (height < var5 && blockArray[index+2] == Block.waterStill.blockID)))//If its an ocean give it a sandy bottom
							{

								blockArrayBig[indexBig] = (byte) Block.sand.blockID;
								metaArrayBig[indexBig] = 0;
							}
						}
						else if(!(biomegenbase instanceof BiomeGenSwampTFC))
						{
							if(((height > var5-2 && height < var5 && blockArray[index+1] == Block.waterStill.blockID) || (height < var5 && blockArray[index+1] == Block.waterStill.blockID)))//If its an ocean give it a sandy bottom
							{
								if(blockArrayBig[indexBig] != Block.sand.blockID && rand.nextInt(3) != 0)
								{
									blockArrayBig[indexBig] = (byte) Block.gravel.blockID;
									metaArrayBig[indexBig] = 0;
								}
							}
						}
					}
				}
			}
		}
	}

	private double[] layer2Noise = new double[256];
	public void replaceBlocksForBiomeLow(int par1, int par2, byte[] blockArray, byte[] metaArray,  Random rand, byte[] blockArrayBig, byte[] metaArrayBig)
	{
		int var5 = 63;
		double var6 = 0.03125D;
		stoneNoise = noiseGen4.generateNoiseOctaves(stoneNoise, par1 * 16, par2 * 16, 0, 16, 16, 1, var6 * 2.0D, var6 * 2.0D, var6 * 2.0D);

		for (int xCoord = 0; xCoord < 16; ++xCoord)
		{
			for (int zCoord = 0; zCoord < 16; ++zCoord)
			{
				TFCBiome biomegenbase = (TFCBiome) biomesForGeneration[zCoord + xCoord * 16];
				DataLayer rock1 = rockLayer1[zCoord + xCoord * 16];
				DataLayer rock2 = rockLayer2[zCoord + xCoord * 16];
				DataLayer rock3 = rockLayer3[zCoord + xCoord * 16];
				
				float var11 = biomegenbase.getFloatTemperature();
				int var12 = (int)(stoneNoise[xCoord + zCoord * 16] / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
				int var13 = -1;
				int var14 = biomegenbase.GrassID;
				int var15 = biomegenbase.DirtID;
				

				for (int height = 127; height >= 0; --height)
				{
					int index = ((zCoord * 16 + xCoord) * 128 + height);
					int indexBig = ((zCoord * 16 + xCoord) * 256 + height);

					metaArrayBig[indexBig] = 0;

					if (height >= 2 && height <= 6)
					{
						blockArrayBig[indexBig] = (byte) Block.lavaStill.blockID;
						metaArrayBig[indexBig] = 0; 

						if(blockArrayBig[indexBig+1] != (byte) Block.lavaStill.blockID && rand.nextBoolean())
						{
							blockArrayBig[indexBig+1] = (byte) Block.lavaStill.blockID;
							metaArrayBig[indexBig+1] = 0; 
						}
					}
					else if (height <= 1)
					{
						blockArrayBig[indexBig] = (byte) Block.bedrock.blockID;
					}
					else
					{
						if(height <= 55)
						{
							blockArrayBig[indexBig] = (byte) rock3.data1; 
							metaArrayBig[indexBig] = (byte) rock3.data2;
							if(height == 55)
							{
								if(rand.nextBoolean())
								{
									blockArrayBig[indexBig+1] = (byte) rock3.data1; 
									metaArrayBig[indexBig+1] = (byte) rock3.data2;
									if(rand.nextBoolean())
									{
										blockArrayBig[indexBig+2] = (byte) rock3.data1; 
										metaArrayBig[indexBig+2] = (byte) rock3.data2;
										if(rand.nextBoolean())
										{
											blockArrayBig[indexBig+3] = (byte) rock3.data1; 
											metaArrayBig[indexBig+3] = (byte) rock3.data2;
										}
									}
								}
							}
						}
						else if(height <= 110 && height > 55)
						{
							blockArrayBig[indexBig] = (byte) rock2.data1; 
							metaArrayBig[indexBig] = (byte) rock2.data2;
							if(height == 110)
							{
								if(rand.nextBoolean())
								{
									blockArrayBig[indexBig+1] = (byte) rock2.data1; 
									metaArrayBig[indexBig+1] = (byte) rock2.data2;
									if(rand.nextBoolean())
									{
										blockArrayBig[indexBig+2] = (byte) rock2.data1; 
										metaArrayBig[indexBig+2] = (byte) rock2.data2;
										if(rand.nextBoolean())
										{
											blockArrayBig[indexBig+3] = (byte) rock2.data1; 
											metaArrayBig[indexBig+3] = (byte) rock2.data2;
										}
									}
								}
							}
						}
						else
						{
							blockArrayBig[indexBig] = (byte) rock1.data1; 
							metaArrayBig[indexBig] = (byte) rock1.data2;
						}
					}
				}
			}
		}
	}
}


