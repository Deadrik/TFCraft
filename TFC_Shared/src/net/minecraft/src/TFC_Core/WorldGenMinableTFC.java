package net.minecraft.src.TFC_Core;

import java.util.Random;

import net.minecraft.src.*;

public class WorldGenMinableTFC extends WorldGenerator
{
	private int minableBlockId;
	private int minableBlockMeta;
	private int numberOfBlocks;
	private int LayerID;
	private int LayerMeta;
	private int BiomeId;

	private int MPChunk_X;
	private int MPChunk_Z;
	public static int MPBlockID;
	public static int MPBlockMeta;
	public static int MPLayerID;
	public static int MPLayerMeta;
	public static int MPPrevX;
	public static int MPPrevZ;
	public static int MPPrevID;
	public static int MPPrevMeta;
	public static int MPPrevLayerID;
	public static int MPPrevLayerMeta;
	private Random rand;
	private World worldObj;

	private int rarity = 2;
	private int veinSi = 2;
	private int veinAm = 2;
	private int height = 2;
	private int diameter = 2;
	private int vDens = 2;
	private int hDens = 2;

	public WorldGenMinableTFC(int i, int j)
	{
		int emptyHolder = 0;
		minableBlockId = i;
		minableBlockMeta = 0;
		emptyHolder = j;
		BiomeId = -1;
	}

	public WorldGenMinableTFC(int i, int j, int layerId, int layerMeta, int rarity, int veinSize, 
			int veinAmount, int height, int diameter, int vDensity, int hDensity)
	{
		int emptyHolder = 0;
		minableBlockId = i;
		minableBlockMeta = j;
		emptyHolder = j;

		LayerID= layerId;
		LayerMeta = layerMeta;

		this.rarity = rarity;
		this.veinSi = veinSize;
		this.veinAm = veinAmount;
		this.height = height;
		this.diameter = diameter;
		this.vDens = vDensity;
		this.hDens = hDensity;
	}

	public boolean BetterOreDistribution(int xChunk, int zChunk, int MPMinableBlockId, int MPMinableBlockMeta)//======================================================================================
	{
		if (rand.nextInt(rarity) == 0)
		{
			for(int loopCount = 0; loopCount < veinAm; loopCount++)
			{
				int temp1 = mPCalculateDensity(diameter, hDens);
				int temp2 = mPCalculateDensity(height, vDens);
				int temp3 = mPCalculateDensity(diameter, hDens);
				int l5 = xChunk + temp1;
				int i9 = temp2;
				int k13 = zChunk + temp3;
				BODgenerate(worldObj, rand, l5, i9, k13, veinSi);

			}
		}
		return true;
	}
	public boolean BODgenerate(World world, Random rand, int i, int j, int k, int xyz)
	{

		//==========================================mp mod



		numberOfBlocks = xyz; //input number of blocks per vein
		//BiomeGenBase bgb = world.worldProvider.worldChunkMgr.getBiomeGenAt(MPChunk_X, MPChunk_Z);
		//if(BiomeId == -1)
		//	BiomeId = bgb.biomeID;
		//if(bgb.biomeID != BiomeId)
		//{
		//	return true;
		//}
		//==========================================mp mod
		float f = rand.nextFloat() * 3.141593F;
		double d = (float)(i + 8) + MathHelper.sin(f) * (float)numberOfBlocks / 8F;
		double d1 = (float)(i + 8) - MathHelper.sin(f) * (float)numberOfBlocks / 8F;
		double d2 = (float)(k + 8) + MathHelper.cos(f) * (float)numberOfBlocks / 8F;
		double d3 = (float)(k + 8) - MathHelper.cos(f) * (float)numberOfBlocks / 8F;
		double d4 = j + rand.nextInt(3) - 2;
		double d5 = j + rand.nextInt(3) - 2;
		for(int l = 0; l <= numberOfBlocks; l++)
		{
			double d6 = d + (d1 - d) * (double)l / (double)numberOfBlocks;
			double d7 = d4 + (d5 - d4) * (double)l / (double)numberOfBlocks;
			double d8 = d2 + (d3 - d2) * (double)l / (double)numberOfBlocks;
			double d9 = rand.nextDouble() * (double)numberOfBlocks / 16D;
			double d10 = (double)(MathHelper.sin((float)l * 3.141593F / (float)numberOfBlocks) + 1.0F) * d9 + 1.0D;
			double d11 = (double)(MathHelper.sin((float)l * 3.141593F / (float)numberOfBlocks) + 1.0F) * d9 + 1.0D;
			int i1 = MathHelper.floor_double(d6 - d10 / 2D);
			int j1 = MathHelper.floor_double(d7 - d11 / 2D);
			int k1 = MathHelper.floor_double(d8 - d10 / 2D);
			int l1 = MathHelper.floor_double(d6 + d10 / 2D);
			int i2 = MathHelper.floor_double(d7 + d11 / 2D);
			int j2 = MathHelper.floor_double(d8 + d10 / 2D);
			for(int k2 = i1; k2 <= l1; k2++)
			{
				double d12 = ((double)k2 + 0.5D - d6) / (d10 / 2D);
				if(d12 * d12 >= 1.0D)
				{
					continue;
				}
				for(int l2 = j1; l2 <= i2; l2++)
				{
					double d13 = ((double)l2 + 0.5D - d7) / (d11 / 2D);
					if(d12 * d12 + d13 * d13 >= 1.0D)
					{
						continue;
					}
					for(int i3 = k1; i3 <= j2; i3++)
					{
						double d14 = ((double)i3 + 0.5D - d8) / (d10 / 2D);
						int m = world.getBlockMetadata(k2, l2, i3);
						boolean isCorrectRockType = world.getBlockId(k2, l2, i3) == LayerID;
						boolean isCorrectMeta = (m == LayerMeta || LayerMeta == -1);

						if(d12 * d12 + d13 * d13 + d14 * d14 < 1.0D && isCorrectRockType && 
								(m == LayerMeta || LayerMeta == -1))
						{
							world.setBlockAndMetadata(k2, l2, i3, MPBlockID, MPBlockMeta);
//							if(MPBlockID == mod_TFC_Core.terraOre.blockID && MPBlockMeta == 12)
//							{
//								System.out.println((new StringBuilder()).append("Cassiterite added! ").append(k2).append(",").append(l2).append(",").append(i3).append(",").toString());
//							}
						}
					}

				}

			}

		}
		//}
	//}

		return true;
	}

	public boolean generate(World world, Random random, int x, int z)//absorb default system
	{
		MPChunk_X = x/16*16;// set output chunk x
		MPChunk_Z = z/16*16;// set output chunk z
		MPBlockID = minableBlockId;// set output block ID
		MPBlockMeta = minableBlockMeta;
		worldObj = world;
		rand = random;
		if(MPChunk_X != MPPrevX || MPChunk_Z != MPPrevZ || MPBlockID != MPPrevID || 
				MPBlockID == MPPrevID && MPBlockMeta != MPPrevMeta || MPLayerID != MPPrevLayerID || MPLayerMeta != MPPrevLayerMeta)// if it is a new x or y chunk or is a new ore, then generate
		{ 

			MPPrevX = MPChunk_X;
			MPPrevZ = MPChunk_Z;
			MPPrevID = MPBlockID;
			MPPrevMeta = MPBlockMeta;
			BetterOreDistribution(MPChunk_X, MPChunk_Z, MPBlockID, MPBlockMeta);


		}
		return true;
	}
	public boolean generate(World world, Random random, int i, int j, int k)
	{
		float f = random.nextFloat() * 3.141593F;
		double d = (float)(i + 8) + MathHelper.sin(f) * (float)numberOfBlocks / 8F;
		double d1 = (float)(i + 8) - MathHelper.sin(f) * (float)numberOfBlocks / 8F;
		double d2 = (float)(k + 8) + MathHelper.cos(f) * (float)numberOfBlocks / 8F;
		double d3 = (float)(k + 8) - MathHelper.cos(f) * (float)numberOfBlocks / 8F;
		double d4 = j + random.nextInt(3) - 2;
		double d5 = j + random.nextInt(3) - 2;
		for (int l = 0; l <= numberOfBlocks; l++)
		{
			double d6 = d + (d1 - d) * (double)l / (double)numberOfBlocks;
			double d7 = d4 + (d5 - d4) * (double)l / (double)numberOfBlocks;
			double d8 = d2 + (d3 - d2) * (double)l / (double)numberOfBlocks;
			double d9 = random.nextDouble() * (double)numberOfBlocks / 16D;
			double d10 = (double)(MathHelper.sin((float)l * 3.141593F / (float)numberOfBlocks) + 1.0F) * d9 + 1.0D;
			double d11 = (double)(MathHelper.sin((float)l * 3.141593F / (float)numberOfBlocks) + 1.0F) * d9 + 1.0D;
			int i1 = MathHelper.floor_double(d6 - d10 / 2D);
			int j1 = MathHelper.floor_double(d7 - d11 / 2D);
			int k1 = MathHelper.floor_double(d8 - d10 / 2D);
			int l1 = MathHelper.floor_double(d6 + d10 / 2D);
			int i2 = MathHelper.floor_double(d7 + d11 / 2D);
			int j2 = MathHelper.floor_double(d8 + d10 / 2D);
			for (int k2 = i1; k2 <= l1; k2++)
			{
				double d12 = ((double)k2 + 0.5D - d6) / (d10 / 2D);
				if (d12 * d12 >= 1.0D)
				{
					continue;
				}
				for (int l2 = j1; l2 <= i2; l2++)
				{
					double d13 = ((double)l2 + 0.5D - d7) / (d11 / 2D);
					if (d12 * d12 + d13 * d13 >= 1.0D)
					{
						continue;
					}
					for (int i3 = k1; i3 <= j2; i3++)
					{
						double d14 = ((double)i3 + 0.5D - d8) / (d10 / 2D);
						if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D && isRock(world.getBlockId(k2, l2, i3)))
						{
							world.setBlock(k2, l2, i3, minableBlockId);
						}
					}
				}
			}
		}

		return true;
	}
	private boolean isRock(int i)
	{
		if(i == mod_TFC_Core.terraStoneIgIn.blockID || i == mod_TFC_Core.terraStoneIgEx.blockID || 
				i == mod_TFC_Core.terraStoneMM.blockID || i == mod_TFC_Core.terraStoneSed.blockID ||
				i == mod_TFC_Core.terraOre.blockID || i == mod_TFC_Core.terraOre2.blockID || 
				i == mod_TFC_Core.terraOre3.blockID)
		{
			return true;
		}

		return false;
	}

	//======================================================================================
	public int mPCalculateDensity(int oreDist, float oreDens) // returns the density value
	{
		int lpCnt = 0;
		int dValPassInr = 0;
		int dValPass = 0;
		oreDens = oreDens * .01F;
		oreDens = oreDens * (oreDist / 2) + 1F;// establishes number of times to loop
		lpCnt = (int)oreDens; //stores number of times to loop
		dValPassInr = (int)(oreDist/oreDens+.5F); // distance devided by number of times it will loop, establishes the number for randomization
		dValPass = 0;
		while (lpCnt > 0) // loops to acumulate random values
		{
			dValPass = dValPass + rand.nextInt(dValPassInr); // acumulate randoms
			lpCnt--;// decriment loop
		}
		if (dValPass < 5){dValPass = 5;}
		if (dValPass > 128) {dValPass = 128;}
		return dValPass; // return proccesed random value
	}
}
