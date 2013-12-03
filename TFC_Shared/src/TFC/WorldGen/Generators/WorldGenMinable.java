//world gen minable
package TFC.WorldGen.Generators;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.gen.feature.WorldGenerator;
import TFC.API.TFCOptions;
import TFC.Core.TFC_Core;
import TFC.WorldGen.DataLayer;
// remove for multiplayer server
import TFC.WorldGen.TFCWorldChunkManager;

// Referenced classes of package net.minecraft.src:
//                      WorldGenerator, MathHelper, World, Block

public class WorldGenMinable extends WorldGenerator
{
	//==========================================mp mod
	//private static int[] aOreCheck = new int[256];// setup array to store oreIDs for this chunk // has to be static to survive instance calls                
	//private static int[] metaOreCheck = new int[16];// this is used to check the metaIDs of a given ore ID
	private static ArrayList oreList = new ArrayList();
	public static int MPChunk_X;
	public static int MPChunk_Z;
	private int x_Chunk;
	private int z_Chunk;
	public int MPBlockID;
	private int minableBlockMeta;
	public static int MPPrevX;
	public static int MPPrevZ;
	public static int MPPrevID;
	public static int MPPrevMeta;
	//public static int MPPrevID3;
	//public static int MPPrevID4;
	private static boolean genBeforeCheck;
	public static int mineCount;
	public static int mineCountM;

	private static Random randomOut;
	private static Random rand;
	private static World worldObj;

	private static WorldChunkManager worldChunkManager;
	private static WorldChunkManagerHell worldChunkManagerHell;

	private int mineGen = 1;
	private int subMineGen = 1;
	private int rarity = 2;
	private int veinSi = 2;
	private int veinAm = 2;
	private int height = 2;
	private int mineHeight = 2;
	private int diameter = 2;
	private int vDens = 2;
	private int hDens = 2;
	private int genInBlock = 1;
	private int genInBlockMeta = 1;
	private boolean useMarcoVeins = false;


	//==========================================mp mod
	private int minableBlockId;
	private int numberOfBlocks;

	public WorldGenMinable(int i, int j, int layerId, int layerMeta, int rarity, int veinSize, 
			int veinAmount, int height, int diameter, int vDensity, int hDensity, boolean vein)
	{
		int emptyHolder = 0;
		minableBlockId = i;
		minableBlockMeta = j;
		emptyHolder = j;

		genInBlock= layerId;
		genInBlockMeta = layerMeta;

		this.rarity = rarity;
		this.veinSi = veinSize;
		this.veinAm = veinAmount;
		this.height = height;
		this.diameter = diameter;
		this.vDens = vDensity;
		this.hDens = hDensity;
		this.useMarcoVeins = vein;
	}

	public boolean generateBeforeCheck() // takes a set of current global variables and checks to see if this ore has spawned before in this chunk
	{
		genBeforeCheck = false;
		genBeforeCheck = oreList.contains(Arrays.asList(MPBlockID, minableBlockMeta));

		if(oreList.contains(Arrays.asList(MPBlockID, minableBlockMeta)) == false)
		{
			oreList.add(Arrays.asList(MPBlockID, minableBlockMeta));

		}
		return genBeforeCheck;
	}

	void createMine( World worldObj, Random rand, int x, int z)
	{
		for(int loopCount = 0; loopCount < veinAm; loopCount++)
		{
			int temp1 = mPCalculateDensity(diameter, hDens);
			int temp2 = mineHeight + mPCalculateDensity(height, vDens);
			int temp3 = mPCalculateDensity(diameter, hDens);
			int l5 = x + temp1;
			int i9 = temp2;
			int k13 = z + temp3;
			if(useMarcoVeins == false)
			{
				BODgenerate(worldObj, rand, l5, i9, k13, veinSi); // generate based on values
			}
			else
			{
				BODgenerateVein(worldObj, rand, l5, i9, k13, veinSi);
			}
		}
	}
	void createMineWithChance(World worldObj, Random rand, int x, int z)
	{
		if (rarity == 1 || (rarity > 0 && rand.nextInt(rarity) == 0)) // use values
		{
			createMine(worldObj, rand, x, z);
		}
	}

	public boolean generate(World world, Random random, int x, int z, int min, int max, String n)//obsorb default system
	{
		//System.out.println(" 1: call "+minableBlockId+":"+minableBlockMeta); // for debugging
		randomOut = random;     // pad the seed so it's the same as vannila
		randomOut.nextFloat(); //   |
		randomOut.nextInt(3);  //   |                          
		randomOut.nextInt(3);  //   |
		randomOut.nextDouble();//   |   
		//System.out.println(" 1.2: found dirt, setting up"); /// for debugging
		MPChunk_X = x;// set output chunk x // snap to grid
		MPChunk_Z = z;// set output chunk z    

		/*----*/    Random randomz = new Random(world.getSeed()); // setup a random for BOD
		long l = (randomz.nextLong() / 2L) * 2L + 1L;                       // |
		long l1 = (randomz.nextLong() / 2L) * 2L + 1L;                      // |
		randomz.setSeed(x * l + z * l1 ^ world.getSeed());      // |
		/*----*/    
		rand = randomz;

		worldObj = world; // set world
		mineCount = 0; // this is a new chunk, so list gets set to the beginning

		oreList.clear(); // clear the list of ores, this is a new chunk   

		MPBlockID = minableBlockId;// set output block ID
		if(MPChunk_X != MPPrevX || MPChunk_Z != MPPrevZ || MPPrevID != MPBlockID || minableBlockMeta != MPPrevMeta)// if it is a new x or z chunk, then generate // blockID stops dirt
		{
			//System.out.println(" 2: allowed ore chunk prev test"); /// for debugging
			if (generateBeforeCheck() == false)
			{
				//System.out.println(" 2.2: procede with gen"); /// for debugging315 56 298
				MPPrevX = MPChunk_X;
				MPPrevZ = MPChunk_Z;
				x_Chunk = MPChunk_X;
				z_Chunk = MPChunk_Z;
				MPPrevID = MPBlockID;
				MPPrevMeta = minableBlockMeta;
				mineHeight = min + rand.nextInt(max-min);

				//System.out.println(" 2.3: bod file exists, checking rarity random"); /// for debugging
				//betterOreDistribution(x_Chunk, z_Chunk, MPBlockID, minableBlockMeta); // gather ore gen values from .txt

				if (rarity == 1 || (rarity > 0 && rand.nextInt(rarity) == 0)) // use values
				{
					createMine(worldObj, rand, x_Chunk, z_Chunk);
				}
			}
			//else{System.out.println(" checked, and genned before!" + minableBlockId);}// for debugging
		}
		return true;
	}

	public int mPCalculateDensity(int oreDistance, float oreDensity) // returns the density value
	{

		int loopCount = 0;
		int densityValuePassInner = 0;
		int densityValuePass = 0;
		oreDensity = oreDensity * .01F;
		oreDensity = (oreDensity * (oreDistance >> 1)) + 1F;// establishes number of times to loop
		loopCount = (int)(oreDensity); //stores number of times to loop
		densityValuePassInner = ((oreDistance/loopCount)); // distance devided by number of times it will loop, establishes the number for randomization
		densityValuePassInner += (((oreDistance - (densityValuePassInner*loopCount))/loopCount));
		densityValuePass = 0;
		while (loopCount > 0) // loops to acumulate random values
		{
			densityValuePass = densityValuePass + rand.nextInt(densityValuePassInner); // acumulate randoms
			loopCount = loopCount - 1; // decriment loop
		}
		return densityValuePass; // return proccesed random value
	}

	public boolean BODgenerateVein(World world, Random rand, int parX, int parY, int parZ, int xyz)
	{
		//==========================================mp mod
		int posX = parX;
		int posY = parY;
		int posZ = parZ;
		int tempPosX =0;
		int tempPosY =0;
		int tempPosZ =0;
		int posX2 = 0;
		int posY2 = 0;
		int posZ2 = 0;
		int directionX =0;
		int directionY =0;
		int directionZ =0;
		int directionX2 = 0;
		int directionY2 = 0;
		int directionZ2 = 0;
		int directionX3 =0;
		int directionY3 =0;
		int directionZ3 =0;
		int directionChange =0;
		int directionChange2 =0;
		int blocksToUse = xyz;//input number of blocks per vein
		int blocksToUse2 =0;
		for(int blocksMade=0; blocksMade <= blocksToUse;) // make veins
		{
			blocksToUse2 = 1 + (blocksToUse/30);
			directionChange = rand.nextInt(6);
			directionX = rand.nextInt(2);
			directionY = rand.nextInt(2);
			directionZ = rand.nextInt(2);

			for(int blocksMade1 = 0; blocksMade1 <= blocksToUse2; ) // make branch
			{
				if(directionX == 0 && directionChange != 1){posX = posX + rand.nextInt(2);}
				if(directionX == 1 && directionChange != 1){posX = posX - rand.nextInt(2);}
				if(directionY == 0 && directionChange != 2){posY = posY + rand.nextInt(2);}
				if(directionY == 1 && directionChange != 2){posY = posY - rand.nextInt(2);}
				if(directionZ == 0 && directionChange != 3){posZ = posZ + rand.nextInt(2);}
				if(directionZ == 1 && directionChange != 3){posZ = posZ - rand.nextInt(2);}
				if(rand.nextInt(4) == 0){
					posX2 = posX2 + rand.nextInt(2);
					posY2 = posY2 + rand.nextInt(2);
					posZ2 = posZ2 + rand.nextInt(2);
					posX2 = posX2 - rand.nextInt(2);
					posY2 = posY2 - rand.nextInt(2);
					posZ2 = posZ2 - rand.nextInt(2);
				}
				if(rand.nextInt(3) == 0) // make sub-branch
				{
					posX2 = posX;
					posY2 = posY;
					posZ2 = posZ;

					directionX2 = rand.nextInt(2);
					directionY2 = rand.nextInt(2);
					directionZ2 = rand.nextInt(2);
					directionChange2 = rand.nextInt(6);
					if(directionX2 == 0 && directionChange2 != 0){posX2 = posX2 + rand.nextInt(2);}
					if(directionY2 == 0 && directionChange2 != 1){posY2 = posY2 + rand.nextInt(2);}
					if(directionZ2 == 0 && directionChange2 != 2){posZ2 = posZ2 + rand.nextInt(2);}
					if(directionX2 == 1 && directionChange2 != 0){posX2 = posX2 - rand.nextInt(2);}
					if(directionY2 == 1 && directionChange2 != 1){posY2 = posY2 - rand.nextInt(2);}
					if(directionZ2 == 1 && directionChange2 != 2){posZ2 = posZ2 - rand.nextInt(2);}

					for(int blocksMade2 = 0; blocksMade2 <= (1 +(blocksToUse2/5)); )
					{

						if(directionX2 == 0 && directionChange2 != 0){posX2 = posX2 + rand.nextInt(2);}
						if(directionY2 == 0 && directionChange2 != 1){posY2 = posY2 + rand.nextInt(2);}
						if(directionZ2 == 0 && directionChange2 != 2){posZ2 = posZ2 + rand.nextInt(2);}
						if(directionX2 == 1 && directionChange2 != 0){posX2 = posX2 - rand.nextInt(2);}
						if(directionY2 == 1 && directionChange2 != 1){posY2 = posY2 - rand.nextInt(2);}
						if(directionZ2 == 1 && directionChange2 != 2){posZ2 = posZ2 - rand.nextInt(2);}

						int m = world.getBlockMetadata(posX, posY, posZ);
						boolean isCorrectRockType = world.getBlockId(posX, posY, posZ) == this.genInBlock;
						boolean isCorrectMeta = (m == this.genInBlockMeta || this.genInBlockMeta == -1);

						if(TFCOptions.enableOreTest)
						{
							DataLayer rockLayer = ((TFCWorldChunkManager)this.worldObj.getWorldChunkManager()).getRockLayerAt(posX, posZ, TFC_Core.getRockLayerFromHeight(posY));
							if(rockLayer.data1 == genInBlock && (rockLayer.data2 == this.genInBlockMeta || this.genInBlockMeta == -1))
							{
								isCorrectRockType = true;
								isCorrectMeta = true;
							}						
						}
						if((isCorrectRockType && isCorrectMeta))
						{
							world.setBlock(posX, posY, posZ, MPBlockID, minableBlockMeta, 2);
						}
						blocksMade++;
						blocksMade1++;
						blocksMade2++;
					}
				}

				int m = world.getBlockMetadata(posX, posY, posZ);
				boolean isCorrectRockType = world.getBlockId(posX, posY, posZ) == this.genInBlock;
				boolean isCorrectMeta = (m == this.genInBlockMeta || this.genInBlockMeta == -1);
				if(TFCOptions.enableOreTest)
				{
					DataLayer rockLayer = ((TFCWorldChunkManager)this.worldObj.getWorldChunkManager()).getRockLayerAt(posX, posZ, TFC_Core.getRockLayerFromHeight(posY));
					if(rockLayer.data1 == genInBlock && (rockLayer.data2 == this.genInBlockMeta || this.genInBlockMeta == -1))
					{
						isCorrectRockType = true;
						isCorrectMeta = true;
					}						
				}

				if(isCorrectRockType && isCorrectMeta)
				{
					world.setBlock(posX, posY, posZ, MPBlockID, minableBlockMeta, 2);
				}

				blocksMade++;
				blocksMade1++;

			}

			parX = parX + (rand.nextInt(3) - 1);
			parY = parY + (rand.nextInt(3) - 1);
			parZ = parZ + (rand.nextInt(3) - 1);
			posX = parX;
			posY = parY;
			posZ = parZ;

		}


		return true;
	}  

	public boolean BODgenerate(World world, Random rand, int par3, int par4, int par5, int xyz)
	{

		//==========================================mp mod
		numberOfBlocks = xyz; //input number of blocks per vein

		//==========================================mp mod
		float var6 = rand.nextFloat() * (float)Math.PI;
		double var7 = par3 + 8 + MathHelper.sin(var6) * numberOfBlocks / 8.0F;
		double var9 = par3 + 8 - MathHelper.sin(var6) * numberOfBlocks / 8.0F;
		double var11 = par5 + 8 + MathHelper.cos(var6) * numberOfBlocks / 8.0F;
		double var13 = par5 + 8 - MathHelper.cos(var6) * numberOfBlocks / 8.0F;
		double var15 = par4 + rand.nextInt(3) - 2;
		double var17 = par4 + rand.nextInt(3) - 2;

		for (int var19 = 0; var19 <= numberOfBlocks; ++var19)
		{
			double var20 = var7 + (var9 - var7) * var19 / numberOfBlocks;
			double var22 = var15 + (var17 - var15) * var19 / numberOfBlocks;
			double var24 = var11 + (var13 - var11) * var19 / numberOfBlocks;
			double var26 = rand.nextDouble() * this.numberOfBlocks / 16.0D;
			double var28 = (MathHelper.sin(var19 * (float)Math.PI / numberOfBlocks) + 1.0F) * var26 + 1.0D;
			double var30 = (MathHelper.sin(var19 * (float)Math.PI / numberOfBlocks) + 1.0F) * var26 + 1.0D;
			int var32 = MathHelper.floor_double(var20 - var28 / 2.0D);
			int var33 = MathHelper.floor_double(var22 - var30 / 2.0D);
			int var34 = MathHelper.floor_double(var24 - var28 / 2.0D);
			int var35 = MathHelper.floor_double(var20 + var28 / 2.0D);
			int var36 = MathHelper.floor_double(var22 + var30 / 2.0D);
			int var37 = MathHelper.floor_double(var24 + var28 / 2.0D);

			for (int posX = var32; posX <= var35; ++posX)
			{
				double var39 = (posX + 0.5D - var20) / (var28 / 2.0D);

				if (var39 * var39 < 1.0D)
				{
					for (int posY = var33; posY <= var36; ++posY)
					{
						double var42 = (posY + 0.5D - var22) / (var30 / 2.0D);

						if (var39 * var39 + var42 * var42 < 1.0D)
						{
							for (int posZ = var34; posZ <= var37; ++posZ)
							{
								double var45 = (posZ + 0.5D - var24) / (var28 / 2.0D);

								int m = world.getBlockMetadata(posX, posY, posZ);
								boolean isCorrectRockType = world.getBlockId(posX, posY, posZ) == this.genInBlock;
								boolean isCorrectMeta = (m == this.genInBlockMeta || this.genInBlockMeta == -1);

								if(TFCOptions.enableOreTest)
								{
									DataLayer rockLayer = ((TFCWorldChunkManager)this.worldObj.getWorldChunkManager()).getRockLayerAt(posX, posZ, TFC_Core.getRockLayerFromHeight(posY));
									if(rockLayer.data1 == genInBlock && (rockLayer.data2 == this.genInBlockMeta || this.genInBlockMeta == -1))
									{
										isCorrectRockType = true;
										isCorrectMeta = true;
									}						
								}
								if(isCorrectRockType && isCorrectMeta)
								{
									if (var39 * var39 + var42 * var42 + var45 * var45 < 1.0D)
									{
										//world.setBlockAndMetadata(var38, var41, var44, minableBlockId, minableBlockMeta);
										world.setBlock(posX, posY, posZ, minableBlockId, minableBlockMeta, 2);
										//System.out.println("block at " + var38 +" "+var41+" "+var44); /// for debugging
									}
								}
							}
						}
					}
				}
			}
		}
		//System.out.println("a vein was placed " + minableBlockId + "." + minableBlockMeta+ " at " + par3 +" "+par4+" "+par5); /// for debugging
		return true;
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) 
	{
		return false;
	}
}