package TFC.WorldGen;

import java.util.Random;

import TFC.WorldGen.Generators.WorldGenCustomPumpkin;
import TFC.WorldGen.Generators.WorldGenCustomReed;
import TFC.WorldGen.Generators.WorldGenCustomSand;
import TFC.WorldGen.Generators.WorldGenLiquidsTFC;

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
		int var3;

		for (var1 = 0; var1 < this.sandPerChunk2; ++var1)
		{
			var2 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
			var3 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
			this.sandGen.generate(this.currentWorld, this.randomGenerator, var2, this.currentWorld.getTopSolidOrLiquidBlock(var2, var3), var3);
		}

		//        for (var1 = 0; var1 < this.clayPerChunk; ++var1)
		//        {
		//            var2 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
		//            var3 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
		//            this.clayGen.generate(this.currentWorld, this.randomGenerator, var2, this.currentWorld.getTopSolidOrLiquidBlock(var2, var3), var3);
		//        }

		for (var1 = 0; var1 < this.sandPerChunk; ++var1)
		{
			var2 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
			var3 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
			this.sandGen.generate(this.currentWorld, this.randomGenerator, var2, this.currentWorld.getTopSolidOrLiquidBlock(var2, var3), var3);
		}

		var1 = this.treesPerChunk;

		if (this.randomGenerator.nextInt(10) == 0)
		{
			++var1;
		}

		int var4;

		for (var2 = 0; var2 < var1; ++var2)
		{
			var3 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
			var4 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
			//WorldGenerator var5 = this.biome.getRandomWorldGenForTrees(this.randomGenerator,currentWorld);
			DataLayer TreeType = ((TFCWorldChunkManager)currentWorld.provider.worldChunkMgr).getTreeLayerAt(var3, var4);
			WorldGenerator var5 = this.biome.getTreeGen(TreeType.data1, false);
			if(var5 != null)
			{
				var5.setScale(1.0D, 1.0D, 1.0D);
				var5.generate(this.currentWorld, this.randomGenerator, var3, this.currentWorld.getHeightValue(var3, var4), var4);
			}
		}

		int var7;

		for (var2 = 0; var2 < this.deadBushPerChunk; ++var2)
		{
			var3 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
			var4 = this.randomGenerator.nextInt(256);
			var7 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
			new WorldGenDeadBush(Block.deadBush.blockID).generate(this.currentWorld, this.randomGenerator, var3, var4, var7);
		}

		for (var2 = 0; var2 < this.waterlilyPerChunk; ++var2)
		{
			var3 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
			var4 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;

			for (var7 = this.randomGenerator.nextInt(256); var7 > 0 && this.currentWorld.getBlockId(var3, var7 - 1, var4) == 0; --var7)
			{
				;
			}

			this.waterlilyGen.generate(this.currentWorld, this.randomGenerator, var3, var7, var4);
		}

		for (var2 = 0; var2 < this.reedsPerChunk; ++var2)
		{
			var3 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
			var4 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
			var7 = this.randomGenerator.nextInt(256);
			this.reedGen.generate(this.currentWorld, this.randomGenerator, var3, var7, var4);
		}

		for (var2 = 0; var2 < 10; ++var2)
		{
			var3 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
			var4 = this.randomGenerator.nextInt(256);
			var7 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
			this.reedGen.generate(this.currentWorld, this.randomGenerator, var3, var4, var7);
		}

		if (this.randomGenerator.nextInt(32) == 0)
		{
			var2 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
			var3 = this.randomGenerator.nextInt(256);
			var4 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
			new WorldGenCustomPumpkin().generate(this.currentWorld, this.randomGenerator, var2, var3, var4);
		}

		for (var2 = 0; var2 < this.cactiPerChunk; ++var2)
		{
			var3 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
			var4 = this.randomGenerator.nextInt(256);
			var7 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
			this.cactusGen.generate(this.currentWorld, this.randomGenerator, var3, var4, var7);
		}

		if (this.generateLakes)
		{
			for (var2 = 0; var2 < 50; ++var2)
			{
				var3 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
				var4 = this.randomGenerator.nextInt(this.randomGenerator.nextInt(247) + 8);
				var7 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
				new WorldGenLiquidsTFC(Block.waterMoving.blockID).generate(this.currentWorld, this.randomGenerator, var3, var4, var7);
			}

			for (var2 = 0; var2 < 20; ++var2)
			{
				var3 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
				var4 = this.randomGenerator.nextInt(this.randomGenerator.nextInt(this.randomGenerator.nextInt(239) + 8) + 8);
				var7 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
				new WorldGenLiquidsTFC(Block.lavaMoving.blockID).generate(this.currentWorld, this.randomGenerator, var3, var4, var7);
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
