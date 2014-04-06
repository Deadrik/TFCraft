package TFC.WorldGen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;
import TFC.TFCBlocks;
import TFC.Core.TFC_Climate;
import TFC.Food.CropIndex;
import TFC.Food.CropManager;
import TFC.WorldGen.Generators.WorldGenCustomCactus;
import TFC.WorldGen.Generators.WorldGenCustomPumpkin;
import TFC.WorldGen.Generators.WorldGenCustomReed;
import TFC.WorldGen.Generators.WorldGenCustomSand;
import TFC.WorldGen.Generators.WorldGenCustomWaterlily;
import TFC.WorldGen.Generators.WorldGenGrowCrops;
import TFC.WorldGen.Generators.WorldGenLiquidsTFC;
import TFC.WorldGen.Generators.WorldGenSeaGrass;

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

	public BiomeGenBase biome;

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

	public int waterlilyPerChunk;

	/** Added By TFC **/

	public BiomeDecoratorTFC(BiomeGenBase par1BiomeGenBase)
	{
		super(par1BiomeGenBase);
		this.flowersPerChunk = 2;
		this.grassPerChunk = 1;
		this.mushroomsPerChunk = 0;
		treesPerChunk = 30;

		this.cactiPerChunk = 2;
		this.seaweedPerChunk = 3;

		this.reedGen = new WorldGenCustomReed();
		this.sandGen = new WorldGenCustomSand(7, Block.sand.blockID);
		this.waterlilyGen = new WorldGenCustomWaterlily();
		biome = par1BiomeGenBase;
	}

	/**
	 * The method that does the work of actually decorating chunks
	 */
	@Override
	protected void decorate()
	{
		this.generateOres();
		int var1;
		int var2;
		int xCoord;
		int yCoord;
		int zCoord;

		Random rand = new Random(this.currentWorld.getSeed() + ((chunk_X >> 7) - (chunk_Z >> 7)) * (chunk_Z >> 7));
		int cropid = rand.nextInt(24);
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

		for (var2 = 0; var2 < this.waterlilyPerChunk; ++var2)
		{
			xCoord = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
			zCoord = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
			yCoord = this.currentWorld.getHeightValue(xCoord, zCoord);

			if (TFC_Climate.isSwamp(xCoord, yCoord, zCoord))
			{
				this.waterlilyGen.generate(this.currentWorld, this.randomGenerator, xCoord, yCoord, zCoord);
			}
		}

		for (var2 = 0; var2 < 10; ++var2)
		{
			if (randomGenerator.nextInt(100) < 10)
			{
				xCoord = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
				zCoord = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
				yCoord = this.currentWorld.getHeightValue(xCoord, zCoord);
				if (TFC_Climate.getBioTemperatureHeight(xCoord, yCoord, zCoord) >= 14)
				{
					this.reedGen.generate(this.currentWorld, this.randomGenerator, xCoord, yCoord, zCoord);
				}
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
			{
				new WorldGenCustomCactus().generate(this.currentWorld, this.randomGenerator, xCoord, yCoord, zCoord);
			}
		}

		for (var2 = 0; var2 < this.seaweedPerChunk; ++var2)
		{
			xCoord = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
			zCoord = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
			yCoord = this.currentWorld.getHeightValue(xCoord, zCoord);
			if (TFC_Climate.getBioTemperatureHeight(xCoord, yCoord, zCoord) >= 7)
			{
				new WorldGenSeaGrass(TFCBlocks.SeaGrassStill.blockID, TFC_Climate.isSwamp(xCoord, yCoord, zCoord)).generate(this.currentWorld,
						this.randomGenerator, xCoord, yCoord, zCoord);
			}
		}

		if (this.generateLakes)
		{
			for (var2 = 0; var2 < 50; ++var2)
			{
				xCoord = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
				zCoord = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
				yCoord = this.currentWorld.getHeightValue(xCoord, zCoord);
				new WorldGenLiquidsTFC(Block.waterMoving.blockID).generate(this.currentWorld, this.randomGenerator, xCoord, yCoord, zCoord);
			}

			for (var2 = 0; var2 < 20; ++var2)
			{
				xCoord = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
				zCoord = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
				yCoord = this.currentWorld.getHeightValue(xCoord, zCoord);
				new WorldGenLiquidsTFC(Block.lavaMoving.blockID).generate(this.currentWorld, this.randomGenerator, xCoord, yCoord, zCoord);
			}
		}
	}

	/**
	 * Decorates the world. Calls code that was formerly (pre-1.8) in
	 * ChunkProviderGenerate.populate
	 */
	@Override
	public void decorate(World par1World, Random par2Random, int par3, int par4)
	{
		if (this.currentWorld != null)
		{
			// throw new RuntimeException("Already decorating!!");
		} else
		{
			this.currentWorld = par1World;
			this.randomGenerator = par2Random;
			this.chunk_X = par3;
			this.chunk_Z = par4;
			this.decorate();
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
