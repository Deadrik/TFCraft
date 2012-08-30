package TFC.WorldGen;

import java.util.Random;

import TFC.Core.EnumTree;
import TFC.Core.TFC_Climate;
import TFC.WorldGen.Generators.WorldGenCustomHugeTrees;
import TFC.WorldGen.Generators.WorldGenCustomPumpkin;
import TFC.WorldGen.Generators.WorldGenCustomReed;
import TFC.WorldGen.Generators.WorldGenCustomSand;
import TFC.WorldGen.Generators.WorldGenCustomShortTrees;
import TFC.WorldGen.Generators.WorldGenCustomShrub;
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
		treesPerChunk = 10;

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

		for (var1 = 0; var1 < this.sandPerChunk2; ++var1)
		{
			xCoord = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
			zCoord = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
			this.sandGen.generate(this.currentWorld, this.randomGenerator, xCoord, this.currentWorld.getTopSolidOrLiquidBlock(xCoord, zCoord), zCoord);
		}

		//        for (var1 = 0; var1 < this.clayPerChunk; ++var1)
		//        {
		//            var2 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
		//            var3 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
		//            this.clayGen.generate(this.currentWorld, this.randomGenerator, var2, this.currentWorld.getTopSolidOrLiquidBlock(var2, var3), var3);
		//        }

		for (var1 = 0; var1 < this.sandPerChunk; ++var1)
		{
			xCoord = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
			zCoord = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
			this.sandGen.generate(this.currentWorld, this.randomGenerator, xCoord, this.currentWorld.getTopSolidOrLiquidBlock(xCoord, zCoord), zCoord);
		}

		var1 = this.treesPerChunk;

		if (this.randomGenerator.nextInt(10) == 0)
		{
			++var1;
		}

		

		for (var2 = 0; var2 < var1; ++var2)
		{
			xCoord = this.chunk_X + this.randomGenerator.nextInt(16);
			zCoord = this.chunk_Z + this.randomGenerator.nextInt(16);
			yCoord = this.currentWorld.getHeightValue(xCoord, zCoord);

			DataLayer TreeType0 = ((TFCWorldChunkManager)currentWorld.provider.worldChunkMgr).getTreeLayerAt(xCoord, zCoord, 0);
			DataLayer TreeType1 = ((TFCWorldChunkManager)currentWorld.provider.worldChunkMgr).getTreeLayerAt(xCoord, zCoord, 1);
			DataLayer TreeType2 = ((TFCWorldChunkManager)currentWorld.provider.worldChunkMgr).getTreeLayerAt(xCoord, zCoord, 2);
			DataLayer EVT = ((TFCWorldChunkManager)currentWorld.provider.worldChunkMgr).getEVTLayerAt(xCoord, zCoord);
			DataLayer Rainfall = ((TFCWorldChunkManager)currentWorld.provider.worldChunkMgr).getRainfallLayerAt(xCoord, zCoord);
			WorldGenerator gen0 = this.biome.getTreeGen(TreeType0.data1, false);
			WorldGenerator gen1 = this.biome.getTreeGen(TreeType1.data1, false);
			WorldGenerator gen2 = this.biome.getTreeGen(TreeType2.data1, false);
			float temperature = TFC_Climate.getBioTemperatureHeight(this.currentWorld.getHeightValue(xCoord, zCoord), zCoord);

			try
			{
			if(EVT.floatdata1 >= EnumTree.KAPOK.minEVT && EVT.floatdata1 <= EnumTree.KAPOK.maxEVT && 
					Rainfall.floatdata1 >= EnumTree.KAPOK.minRain && Rainfall.floatdata1 <= EnumTree.KAPOK.maxRain && 
					temperature >= EnumTree.KAPOK.minTemp && temperature <= EnumTree.KAPOK.maxTemp)
			{
				var1 = 8;
				gen0 = (WorldGenerator)(randomGenerator.nextInt(10) == 0 ? new WorldGenCustomShortTrees(false,15) : randomGenerator.nextInt(2) == 0 ? new WorldGenCustomShrub(15, 15) : 
					randomGenerator.nextInt(3) == 0 ? new WorldGenCustomHugeTrees(false, 10 + randomGenerator.nextInt(20), 15, 15) : new WorldGenCustomShortTrees(false, 15));
				
				gen0.setScale(1.0D, 1.0D, 1.0D);
				gen0.generate(this.currentWorld, this.randomGenerator, xCoord, yCoord, zCoord);
			}
			else
			{
				int randomNumber = this.randomGenerator.nextInt(3);
				
				float tree0EVTMin = 0;
				float tree0EVTMax = 0;
				float tree1EVTMin = 0;
				float tree1EVTMax = 0;
				float tree2EVTMin = 0;
				float tree2EVTMax = 0;
				
				float tree0RainMin = 0;
				float tree0RainMax = 0;
				float tree1RainMin = 0;
				float tree1RainMax = 0;
				float tree2RainMin = 0;
				float tree2RainMax = 0;
				
				float tree0TempMin = 0;
				float tree0TempMax = 0;
				float tree1TempMin = 0;
				float tree1TempMax = 0;
				float tree2TempMin = 0;
				float tree2TempMax = 0;
				
				try
				{
					tree0EVTMin = EnumTree.values()[TreeType0.data1].minEVT;
					tree0EVTMax = EnumTree.values()[TreeType0.data1].maxEVT;
					
					tree0RainMin = EnumTree.values()[TreeType0.data1].minRain;
					tree0RainMax = EnumTree.values()[TreeType0.data1].maxRain;
					
					tree0TempMin = EnumTree.values()[TreeType0.data1].minTemp;
					tree0TempMax = EnumTree.values()[TreeType0.data1].maxTemp;
				}
				catch(Exception e){}
				
				try
				{
					tree1EVTMin = EnumTree.values()[TreeType1.data1].minEVT;
					tree1EVTMax = EnumTree.values()[TreeType1.data1].maxEVT;
					
					tree1RainMin = EnumTree.values()[TreeType1.data1].minRain;
					tree1RainMax = EnumTree.values()[TreeType1.data1].maxRain;
					
					tree1TempMin = EnumTree.values()[TreeType1.data1].minTemp;
					tree1TempMax = EnumTree.values()[TreeType1.data1].maxTemp;
				}
				catch(Exception e){}
				
				try
				{
					tree2EVTMin = EnumTree.values()[TreeType2.data1].minEVT;
					tree2EVTMax = EnumTree.values()[TreeType2.data1].maxEVT;
					
					tree2RainMin = EnumTree.values()[TreeType2.data1].minRain;
					tree2RainMax = EnumTree.values()[TreeType2.data1].maxRain;
					
					tree2TempMin = EnumTree.values()[TreeType2.data1].minTemp;
					tree2TempMax = EnumTree.values()[TreeType2.data1].maxTemp;
				}
				catch(Exception e){}
				
				switch(randomNumber)
				{
				case 0:
				{
					if(gen0 != null && TreeType0.data1 != -1 &&
							EVT.floatdata1 >= tree0EVTMin && EVT.floatdata1 <= tree0EVTMax && 
							Rainfall.floatdata1 >= tree0RainMin && Rainfall.floatdata1 <= tree0RainMax && 
							temperature >= tree0TempMin && temperature <= tree0TempMax)
					{
						gen0.setScale(1.0D, 1.0D, 1.0D);
						gen0.generate(this.currentWorld, this.randomGenerator, xCoord, yCoord, zCoord);
						break;
					}
				}
				case 1:
				{
					if(gen1 != null && TreeType1.data1 != -1 &&
							EVT.floatdata1 >= tree1EVTMin && EVT.floatdata1 <= tree1EVTMax && 
							Rainfall.floatdata1 >= tree1RainMin && Rainfall.floatdata1 <= tree1RainMax && 
							temperature >= tree1TempMin && temperature <= tree1TempMax)
					{
						gen1.setScale(1.0D, 1.0D, 1.0D);
						gen1.generate(this.currentWorld, this.randomGenerator, xCoord, yCoord, zCoord);
						break;
					}
				}
				case 2:
				{
					if(gen1 != null && TreeType2.data1 != -1 &&
							EVT.floatdata1 >= tree2EVTMin && EVT.floatdata1 <= tree2EVTMax && 
							Rainfall.floatdata1 >= tree2RainMin && Rainfall.floatdata1 <= tree2RainMax && 
							temperature >= tree2TempMin && temperature <= tree2TempMax)
					{
						gen2.setScale(1.0D, 1.0D, 1.0D);
						gen2.generate(this.currentWorld, this.randomGenerator, xCoord, yCoord, zCoord);
						break;
					}
				}
				}
			}
			}catch(IndexOutOfBoundsException e)
			{
				e.printStackTrace();System.out.println("Tree0 Type: "+TreeType0.data1);System.out.println("Tree1 Type: "+TreeType1.data1);System.out.println("Tree2 Type: "+TreeType2.data1);
			}
		}

		

		for (var2 = 0; var2 < this.deadBushPerChunk; ++var2)
		{
			xCoord = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
			zCoord = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
			yCoord = this.currentWorld.getHeightValue(xCoord, zCoord);
			
			DataLayer EVT = ((TFCWorldChunkManager)currentWorld.provider.worldChunkMgr).getEVTLayerAt(xCoord, zCoord);
			DataLayer Rainfall = ((TFCWorldChunkManager)currentWorld.provider.worldChunkMgr).getRainfallLayerAt(xCoord, zCoord);
			
			float temperature = TFC_Climate.getBioTemperatureHeight(this.currentWorld.getHeightValue(xCoord, zCoord), zCoord);
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

		for (var2 = 0; var2 < this.reedsPerChunk; ++var2)
		{
			xCoord = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
			zCoord = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
			yCoord = this.currentWorld.getHeightValue(xCoord, zCoord);
			this.reedGen.generate(this.currentWorld, this.randomGenerator, xCoord, yCoord, zCoord);
		}

		for (var2 = 0; var2 < 10; ++var2)
		{
			xCoord = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
			zCoord = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
			yCoord = this.currentWorld.getHeightValue(xCoord, zCoord);
			this.reedGen.generate(this.currentWorld, this.randomGenerator, xCoord, yCoord, zCoord);
		}

		if (this.randomGenerator.nextInt(32) == 0)
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
			float temperature = TFC_Climate.getBioTemperatureHeight(this.currentWorld.getHeightValue(xCoord, zCoord), zCoord);
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
