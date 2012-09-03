package TFC.WorldGen;

import java.util.Random;

import TFC.Core.TFC_Climate;
import TFC.Enums.EnumTree;
import TFC.WorldGen.Generators.*;

import net.minecraft.src.BiomeDecorator;
import net.minecraft.src.Block;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenDeadBush;
import net.minecraft.src.WorldGenerator;

public class BiomeDecoratorTFC extends BiomeDecorator
{
	/**
	 * The number of yellow flower patches to generate per chunk. The game generates much less than this number, since
	 * it attempts to generate them at a random altitude.
	 */
	public int flowersPerChunk;

	/** The amount of tall grass to generate per chunk. */
	public int grassPerChunk;

	public TFCBiome biome;

	/**
	 * The number of extra mushroom patches per chunk. It generates 1/4 this number in brown mushroom patches, and 1/8
	 * this number in red mushroom patches. These mushrooms go beyond the default base number of mushrooms.
	 */
	public int mushroomsPerChunk;

	public int treesPerChunk;

	public int deadBushPerChunk;

	public int clayPerChunk;

	public int cactiPerChunk;

	/**
	 * The number of reeds to generate per chunk. Reeds won't generate if the randomly selected placement is unsuitable.
	 */
	public int reedsPerChunk;

	public int waterlilyPerChunk;


	/**Added By TFC**/
	public int looseRocksPerChunk;
	public int looseRocksChancePerChunk;

	public BiomeDecoratorTFC(TFCBiome par1BiomeGenBase)
	{
		super(par1BiomeGenBase);
		this.flowersPerChunk = 2;
		this.grassPerChunk = 1;
		this.mushroomsPerChunk = 0;
		treesPerChunk = 30;

		this.reedGen = new WorldGenCustomReed();
		this.sandGen = new WorldGenCustomSand(7, Block.sand.blockID);
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

//		for (var1 = 0; var1 < this.sandPerChunk2; ++var1)
//		{
//			xCoord = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
//			zCoord = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
//			this.sandGen.generate(this.currentWorld, this.randomGenerator, xCoord, this.currentWorld.getTopSolidOrLiquidBlock(xCoord, zCoord), zCoord);
//		}

		//        for (var1 = 0; var1 < this.clayPerChunk; ++var1)
		//        {
		//            var2 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
		//            var3 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
		//            this.clayGen.generate(this.currentWorld, this.randomGenerator, var2, this.currentWorld.getTopSolidOrLiquidBlock(var2, var3), var3);
		//        }

//		for (var1 = 0; var1 < this.sandPerChunk; ++var1)
//		{
//			xCoord = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
//			zCoord = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
//			this.sandGen.generate(this.currentWorld, this.randomGenerator, xCoord, this.currentWorld.getTopSolidOrLiquidBlock(xCoord, zCoord), zCoord);
//		}

		//new WorldGenFixGrass().generate(this.randomGenerator,chunk_X, chunk_Z, this.currentWorld, null, null);

		new WorldGenForests().generate(this.randomGenerator,chunk_X, chunk_Z, this.currentWorld, null, null);
		
		new WorldGenLooseRocks().generate(this.randomGenerator,chunk_X, chunk_Z, this.currentWorld, null, null);
		
		new WorldGenPlants().generate(this.randomGenerator,chunk_X, chunk_Z, this.currentWorld, null, null);
		
		new WorldGenSoilPits().generate(this.randomGenerator,chunk_X, chunk_Z, this.currentWorld, null, null);		

		for (var2 = 0; var2 < this.deadBushPerChunk; ++var2)
		{
			xCoord = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
			zCoord = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
			yCoord = this.currentWorld.getHeightValue(xCoord, zCoord);

			DataLayer Rainfall = ((TFCWorldChunkManager)currentWorld.provider.worldChunkMgr).getRainfallLayerAt(xCoord, zCoord);

			float temperature = TFC_Climate.getBioTemperatureHeight(xCoord, this.currentWorld.getHeightValue(xCoord, zCoord), zCoord);
			if(temperature < 18 && Rainfall.floatdata1 < 250)
				new WorldGenDeadBush(Block.deadBush.blockID).generate(this.currentWorld, this.randomGenerator, xCoord, yCoord, zCoord);
		}

		for (var2 = 0; var2 < this.waterlilyPerChunk; ++var2)
		{
			xCoord = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
			zCoord = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
			yCoord = this.currentWorld.getHeightValue(xCoord, zCoord);


			this.waterlilyGen.generate(this.currentWorld, this.randomGenerator, xCoord, yCoord, zCoord);
		}

		for (var2 = 0; var2 < 10; ++var2)
		{
			if(randomGenerator.nextInt(100) < 10)
			{
			xCoord = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
			zCoord = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
			yCoord = this.currentWorld.getHeightValue(xCoord, zCoord);
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
			if(temperature > 18)
				this.cactusGen.generate(this.currentWorld, this.randomGenerator, xCoord, yCoord, zCoord);
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
	 * Decorates the world. Calls code that was formerly (pre-1.8) in ChunkProviderGenerate.populate
	 */
	public void decorate(World par1World, Random par2Random, int par3, int par4)
	{
		if (this.currentWorld != null)
		{
			//throw new RuntimeException("Already decorating!!");
		}
		else
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
	protected void generateOres()
	{

	}

	/**
	 * Standard ore generation helper. Generates most ores.
	 */
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
