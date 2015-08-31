package com.bioxx.tfc.WorldGen;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;

import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Food.CropIndex;
import com.bioxx.tfc.Food.CropManager;
import com.bioxx.tfc.WorldGen.Generators.*;
import com.bioxx.tfc.api.TFCBlocks;

public class BiomeDecoratorTFC extends BiomeDecorator
{
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

	public int waterPlantsPerChunk;

	/**
	 * The number of reeds to generate per chunk. Reeds won't generate if the
	 * randomly selected placement is unsuitable.
	 */
	public int reedsPerChunk;

	/** Amount of waterlilys per chunk. */
	public int lilyPadPerChunk;


	/**Added By TFC**/
	public BiomeDecoratorTFC(TFCBiome par1)
	{
		super();
		this.grassPerChunk = 1;
		this.mushroomsPerChunk = 0;
		treesPerChunk = 30;
		this.cactiPerChunk = 2;
		this.waterPlantsPerChunk = 30;
		this.reedGen = new WorldGenCustomReed();
		this.sandGen = new WorldGenCustomSand(7, Blocks.sand);
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
		int cropid = rand.nextInt(CropManager.getInstance().getTotalCrops());
		CropIndex crop = CropManager.getInstance().getCropFromId(cropid);
		WorldGenGrowCrops cropGen = new WorldGenGrowCrops(cropid);

		if(randomGenerator.nextInt(20) == 0 && crop != null)
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
		for (var2 = 0; var2 < this.lilyPadPerChunk; ++var2)
		{
			xCoord = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
			zCoord = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
			yCoord = this.currentWorld.getHeightValue(xCoord, zCoord);

			generateLilyPads(this.currentWorld, this.randomGenerator, xCoord, yCoord, zCoord);
		}

		for (var2 = 0; var2 < 10; ++var2)
		{
			if (randomGenerator.nextInt(100) < 10)
			{
				xCoord = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
				zCoord = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
				yCoord = this.currentWorld.getHeightValue(xCoord, zCoord);
				if(TFC_Climate.getBioTemperatureHeight(currentWorld, xCoord, yCoord, zCoord) >= 25)
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
			float temperature = TFC_Climate.getBioTemperatureHeight(currentWorld, xCoord, this.currentWorld.getHeightValue(xCoord, zCoord), zCoord);
			float rainfall = TFC_Climate.getRainfall(currentWorld, xCoord, yCoord, zCoord);
			if (temperature > 20 && rainfall < 125)
				new WorldGenCustomCactus().generate(this.currentWorld, this.randomGenerator, xCoord, yCoord, zCoord);
		}

		for (var2 = 0; var2 < this.waterPlantsPerChunk; ++var2)
		{
			xCoord = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
			zCoord = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
			yCoord = this.currentWorld.getPrecipitationHeight(xCoord, zCoord)-1;
			if (TFC_Climate.getBioTemperatureHeight(currentWorld, xCoord, yCoord, zCoord) >= 7)
				new WorldGenWaterPlants(TFCBlocks.waterPlant).generate(this.currentWorld, this.randomGenerator, xCoord, yCoord, zCoord);
		}
	}

	public boolean generateLilyPads(World world, Random random, int x, int y, int z)
	{
		for (int l = 0; l < 10; ++l)
		{
			int i1 = x + random.nextInt(8) - random.nextInt(8);
			int j1 = y + random.nextInt(4) - random.nextInt(4);
			int k1 = z + random.nextInt(8) - random.nextInt(8);

			if (world.isAirBlock(i1, j1, k1) && TFCBlocks.lilyPad.canPlaceBlockAt(world, i1, j1, k1) &&
					TFC_Core.isFreshWater(world.getBlock(i1, j1 - 1, k1)) && !TFC_Core.isFreshWater(world.getBlock(i1, j1 - 2, k1))) // Only 1 deep water
			{
				world.setBlock(i1, j1, k1, TFCBlocks.lilyPad, 0, 2);
			}
		}

		return true;
	}

	/**
	 * Decorates the world. Calls code that was formerly (pre-1.8) in
	 * ChunkProviderGenerate.populate
	 */
	@Override
	public void decorateChunk(World par1World, Random par2Random, BiomeGenBase bgb, int par3, int par4)
	{
		if (this.currentWorld == null)
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
}
