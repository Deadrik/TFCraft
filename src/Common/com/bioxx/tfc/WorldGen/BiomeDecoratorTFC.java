package com.bioxx.tfc.WorldGen;

import java.util.Random;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Food.CropIndex;
import com.bioxx.tfc.Food.CropManager;
import com.bioxx.tfc.WorldGen.Generators.WorldGenCustomCactus;
import com.bioxx.tfc.WorldGen.Generators.WorldGenCustomPumpkin;
import com.bioxx.tfc.WorldGen.Generators.WorldGenCustomReed;
import com.bioxx.tfc.WorldGen.Generators.WorldGenCustomSand;
import com.bioxx.tfc.WorldGen.Generators.WorldGenGrowCrops;
import com.bioxx.tfc.WorldGen.Generators.WorldGenLilyPad;
import com.bioxx.tfc.WorldGen.Generators.WorldGenLiquidsTFC;
import com.bioxx.tfc.WorldGen.Generators.WorldGenSeaGrass;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeDecoratorTFC extends BiomeDecorator
{
	/**
	 * The number of yellow flower patches to generate per chunk. The game
	 * generates much less than this number, since it attempts to generate them
	 * at a random altitude.
	 */
	public int flowersPerChunk;

	/** The amount of tall grass to generate per chunk. */
	public int grassPerChunk;

	public TFCBiome biome;

	/**
	 * The number of extra mushroom patches per chunk. It generates 1/4 this
	 * number in brown mushroom patches, and 1/8 this number in red mushroom
	 * patches. These mushrooms go beyond the default base number of mushrooms.
	 */
	public int mushroomsPerChunk;

	public int treesPerChunk;

	public int deadBushPerChunk;

	public int clayPerChunk;

	public int cactiPerChunk;

	public int seaweedPerChunk;

	/**
	 * The number of reeds to generate per chunk. Reeds won't generate if the
	 * randomly selected placement is unsuitable.
	 */
	public int reedsPerChunk;

	/** The water lily generation! */
	public WorldGenLilyPad lilyPadGen;
	/** Amount of waterlilys per chunk. */
	public int lilyPadPerChunk;


	/**Added By TFC**/
	public BiomeDecoratorTFC(TFCBiome par1)
	{
		super();
		this.flowersPerChunk = 2;
		this.grassPerChunk = 1;
		this.mushroomsPerChunk = 0;
		treesPerChunk = 30;
		this.cactiPerChunk = 2;
		this.seaweedPerChunk = 3;
		this.reedGen = new WorldGenCustomReed();
		this.sandGen = new WorldGenCustomSand(7, Blocks.sand);
		this.lilyPadGen = new WorldGenLilyPad();
		biome = par1;
	}

	/**
	 * The method that does the work of actually decorating chunks
	 */
	@Override
	protected void genDecorations(BiomeGenBase bgb)
	{
		this.generateOres();
		int var2;
		int xCoord;
		int yCoord;
		int zCoord;

		Random rand = new Random(this.currentWorld.getSeed() + ((chunk_X >> 7) - (chunk_Z >> 7)) * (chunk_Z >> 7));
		int cropid = rand.nextInt(25);
		CropIndex crop = CropManager.getInstance().getCropFromId(cropid);
		WorldGenGrowCrops cropGen = new WorldGenGrowCrops(cropid);

		if (randomGenerator.nextInt(20) == 0 && crop != null)
		{
			int num = 2 + randomGenerator.nextInt(8);
			xCoord = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
			zCoord = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
			yCoord = this.currentWorld.getHeightValue(xCoord, zCoord) + 1;
			for (int count = 0; count < num; ++count)
			{
				cropGen.generate(currentWorld, randomGenerator, xCoord, zCoord, 1);
			}
		}

		/*
		 * for (var2 = 0; var2 < this.deadBushPerChunk; ++var2) { xCoord =
		 * this.chunk_X + this.randomGenerator.nextInt(16) + 8; zCoord =
		 * this.chunk_Z + this.randomGenerator.nextInt(16) + 8; yCoord =
		 * this.currentWorld.getHeightValue(xCoord, zCoord);
		 * 
		 * float rain = TFC_Climate.getRainfall(xCoord, yCoord, zCoord);
		 * 
		 * float temperature = TFC_Climate.getBioTemperatureHeight(xCoord,
		 * this.currentWorld.getHeightValue(xCoord, zCoord), zCoord);
		 * if(temperature < 18 && rain < 250) { new
		 * WorldGenDeadBush(Block.deadBush.blockID).generate(this.currentWorld,
		 * this.randomGenerator, xCoord, yCoord, zCoord); } }
		 */

		/*
		 * int catTailsNum = 10; for (var2 = 0; var2 < catTailsNum; ++var2) {
		 * xCoord = this.chunk_X + this.randomGenerator.nextInt(16) + 8; zCoord
		 * = this.chunk_Z + this.randomGenerator.nextInt(16) + 8; yCoord =
		 * this.currentWorld.getHeightValue(xCoord, zCoord);
		 * 
		 * if(TFC_Climate.isSwamp(xCoord, yCoord, zCoord)) catTailsNum = 20;
		 * 
		 * if(currentWorld.getBlockId(xCoord, yCoord, zCoord) ==
		 * Block.waterStill.blockID && currentWorld.isBlockOpaqueCube(xCoord,
		 * yCoord-1, zCoord)) { currentWorld.setBlock(xCoord, yCoord+1, zCoord,
		 * TFCBlocks.Flora.blockID, 1, 0x2); }
		 * 
		 * }
		 */

		for (var2 = 0; var2 < this.lilyPadPerChunk; ++var2)
		{
			xCoord = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
			zCoord = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
			yCoord = this.currentWorld.getHeightValue(xCoord, zCoord);
			if(TFC_Climate.isSwamp(xCoord, yCoord, zCoord))
			{
				this.lilyPadGen.generate(this.currentWorld, this.randomGenerator, xCoord, yCoord, zCoord);
			}
		}

		for (var2 = 0; var2 < 10; ++var2)
		{
			if (randomGenerator.nextInt(100) < 10)
			{
				xCoord = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
				zCoord = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
				yCoord = this.currentWorld.getHeightValue(xCoord, zCoord);
				if(TFC_Climate.getBioTemperatureHeight(xCoord, yCoord, zCoord) >= 14)
					this.reedGen.generate(this.currentWorld, this.randomGenerator, xCoord, yCoord, zCoord);
			}
		}

		if (this.randomGenerator.nextInt(300) == 0)
		{
			xCoord = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
			zCoord = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
			yCoord = this.currentWorld.getHeightValue(xCoord, zCoord);
			new WorldGenCustomPumpkin().generate(this.currentWorld, this.randomGenerator, xCoord, yCoord, zCoord);
		}

		for (var2 = 0; var2 < this.cactiPerChunk; ++var2)
		{
			xCoord = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
			zCoord = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
			yCoord = this.currentWorld.getHeightValue(xCoord, zCoord);
			float temperature = TFC_Climate.getBioTemperatureHeight(xCoord, this.currentWorld.getHeightValue(xCoord, zCoord), zCoord);
			float rainfall = TFC_Climate.getRainfall(xCoord, yCoord, zCoord);
			if (temperature > 12 && rainfall < 125)
				new WorldGenCustomCactus().generate(this.currentWorld, this.randomGenerator, xCoord, yCoord, zCoord);
		}

		for (var2 = 0; var2 < this.seaweedPerChunk; ++var2)
		{
			xCoord = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
			zCoord = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
			yCoord = this.currentWorld.getHeightValue(xCoord, zCoord);
			if (TFC_Climate.getBioTemperatureHeight(xCoord, yCoord, zCoord) >= 7)
				new WorldGenSeaGrass(TFCBlocks.SeaGrassStill,TFC_Climate.isSwamp(xCoord, yCoord, zCoord)).generate(this.currentWorld, this.randomGenerator, xCoord, yCoord, zCoord);
		}

		if (this.generateLakes)
		{
			for (var2 = 0; var2 < 50; ++var2)
			{
				xCoord = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
				zCoord = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
				yCoord = this.currentWorld.getHeightValue(xCoord, zCoord);
				new WorldGenLiquidsTFC(TFCBlocks.SaltWater).generate(this.currentWorld, this.randomGenerator, xCoord, yCoord, zCoord);
			}

			for (var2 = 0; var2 < 20; ++var2)
			{
				xCoord = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
				zCoord = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
				yCoord = this.currentWorld.getHeightValue(xCoord, zCoord);
				new WorldGenLiquidsTFC(TFCBlocks.Lava).generate(this.currentWorld, this.randomGenerator, xCoord, yCoord, zCoord);
			}
		}
	}

	/**
	 * Decorates the world. Calls code that was formerly (pre-1.8) in
	 * ChunkProviderGenerate.populate
	 */
	@Override
	public void decorateChunk(World par1World, Random par2Random, BiomeGenBase bgb, int par3, int par4)
	{
		if (this.currentWorld != null)
		{
			// throw new RuntimeException("Already decorating!!");
		}
		else
		{
			this.currentWorld = par1World;
			this.randomGenerator = par2Random;
			this.chunk_X = par3;
			this.chunk_Z = par4;
			this.genDecorations(bgb);
			this.currentWorld = null;
			this.randomGenerator = null;
		}
	}

	/**
	 * Generates ores in the current chunk
	 */
	@Override
	protected void generateOres()
	{
	}

	/**
	 * Standard ore generation helper. Generates most ores.
	 */
	@Override
	protected void genStandardOre1(int par1, WorldGenerator par2WorldGenerator, int par3, int par4)
	{
		for (int var5 = 0; var5 < par1; ++var5)
		{
			int var6 = this.chunk_X + this.randomGenerator.nextInt(16);
			int var7 = this.randomGenerator.nextInt(par4 - par3) + par3;
			int var8 = this.chunk_Z + this.randomGenerator.nextInt(16);
			par2WorldGenerator.generate(this.currentWorld, this.randomGenerator, var6, var7, var8);
		}
	}

	/**
	 * Standard ore generation helper. Generates Lapis Lazuli.
	 */
	@Override
	protected void genStandardOre2(int par1, WorldGenerator par2WorldGenerator, int par3, int par4)
	{
		for (int var5 = 0; var5 < par1; ++var5)
		{
			int var6 = this.chunk_X + this.randomGenerator.nextInt(16);
			int var7 = this.randomGenerator.nextInt(par4) + this.randomGenerator.nextInt(par4) + par3 - par4;
			int var8 = this.chunk_Z + this.randomGenerator.nextInt(16);
			par2WorldGenerator.generate(this.currentWorld, this.randomGenerator, var6, var7, var8);
		}
	}
}
