package com.bioxx.tfc.WorldGen.Generators.Trees;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.TFCBlocks;

public class WorldGenKapokTrees extends WorldGenerator
{
	/**
	 * Contains three sets of two values that provide complimentary indices for a given 'major' index - 1 and 2 for 0, 0
	 * and 2 for 1, and 0 and 1 for 2.
	 */
	private static final byte[] OTHER_COORD_PAIRS = new byte[]{ (byte) 2, (byte) 0, (byte) 0, (byte) 1, (byte) 2, (byte) 1 };
	private final Random rand = new Random();

	/** Reference to the World object. */
	private World worldObj;
	private int[] basePos = new int[]
	{ 0, 0, 0 };
	private int heightLimit;
	private int height;
	private static final double HEIGHT_ATTENUATION = 0.718D;
	//private static final double branchDensity = 1.0D;
	private static final double BRANCH_SLOPE = 0.681D;
	private double scaleWidth = 3.0D;
	private double leafDensity = 1.0D;

	/**
	 * Currently always 1, can be set to 2 in the class constructor to generate a double-sized tree trunk for big trees.
	 */
	private int trunkSize = 2;

	/**
	 * Sets the limit of the random value used to initialize the height limit.
	 */
	private int heightLimitLimit = 35;

	/**
	 * Sets the distance limit for how far away the generator will populate leaves from the base leaf node.
	 */
	private int leafDistanceLimit = 4;

	/** Contains a list of a points at which to generate groups of leaves. */
	private int[][] leafNodes;

	private final int treeId;

	public WorldGenKapokTrees(boolean par1, int id)
	{
		super(par1);
		treeId = id;
	}

	/**
	 * Checks a line of blocks in the world from the first coordinate to triplet to the second, returning the distance
	 * (in blocks) before a non-air, non-leaf block is encountered and/or the end is encountered.
	 */
	private int checkBlockLine(int[] par1ArrayOfInteger, int[] par2ArrayOfInteger)
	{
		int[] var3 = new int[] {0, 0, 0};
		byte var4 = 0;
		byte var5;

		for (var5 = 0; var4 < 3; ++var4)
		{
			var3[var4] = par2ArrayOfInteger[var4] - par1ArrayOfInteger[var4];
			if (Math.abs(var3[var4]) > Math.abs(var3[var5]))
				var5 = var4;
		}

		if (var3[var5] == 0)
		{
			return -1;
		}
		else
		{
			byte var6 = OTHER_COORD_PAIRS[var5];
			byte var7 = OTHER_COORD_PAIRS[var5 + 3];
			byte var8;

			if (var3[var5] > 0)
				var8 = 1;
			else
				var8 = -1;

			double var9 = (double)var3[var6] / (double)var3[var5];
			double var11 = (double)var3[var7] / (double)var3[var5];
			int[] var13 = new int[] {0, 0, 0};
			int var14 = 0;
			int var15;

			for (var15 = var3[var5] + var8; var14 != var15; var14 += var8)
			{
				var13[var5] = par1ArrayOfInteger[var5] + var14;
				var13[var6] = MathHelper.floor_double(par1ArrayOfInteger[var6] + var14 * var9);
				var13[var7] = MathHelper.floor_double(par1ArrayOfInteger[var7] + var14 * var11);
				Block var16 = this.worldObj.getBlock(var13[0], var13[1], var13[2]);
				if (!var16.isAir(worldObj, var13[0], var13[1], var13[2]) && (var16 != TFCBlocks.leaves || var16 != TFCBlocks.leaves2) && var16 != TFCBlocks.vine)
					break;
			}
			return var14 == var15 ? -1 : Math.abs(var14);
		}
	}

	@Override
	public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
	{
		this.worldObj = par1World;
		long var6 = par2Random.nextLong();
		this.rand.setSeed(var6);
		this.basePos[0] = par3;
		this.basePos[1] = par4;
		this.basePos[2] = par5;

		if (this.heightLimit == 0)
			this.heightLimit = 15 + this.rand.nextInt(this.heightLimitLimit/2);

		trunkSize = heightLimit > 25?2:1;

		if (!this.validTreeLocation())
		{
			return false;
		}
		else
		{
			this.generateLeafNodeList();
			this.generateBranchPoints();
			//this.generateLeaves();
			this.generateTrunk();
			this.generateLeafNodeBases();
			return true;
		}
	}

	/**
	 * Generates the leaves surrounding an individual entry in the leafNodes list.
	 */
	private void generateLeafNode(int par1, int par2, int par3)
	{
		int var4 = par2;
		for (int var5 = par2 + this.leafDistanceLimit; var4 < var5; ++var4)
		{
			float var6 = this.leafSize(var4 - par2);
			this.genTreeLayer(par1, var4, par3, var6, (byte)1, TFCBlocks.leaves);
		}
	}

	private void generateBranchPoints()
	{
		double branchSlope = 0;			//starts out horizontal and becomes more vertical. should be 0.2-1.5, trying out constant 0.5
		int branchRange = height/3;			
		int branchLength = 0;			//length of the branch in XZ
		double x,z,y;
		for(int j1 = basePos[1]+height-branchRange; j1 < basePos[1]+height;j1++)
		{			
			branchSlope = 0.5;
			x = rand.nextFloat();
			z = 1 - x;
			x*=rand.nextBoolean()? 1 : -1;
			z*=rand.nextBoolean()? 1 : -1;
			branchLength = (int) ((1.5D - branchSlope) * 5) + rand.nextInt(4) + 3;
			if(trunkSize == 1)branchLength *= 0.66;
			x*=branchLength;
			z*=branchLength;
			y = branchLength * branchSlope;
			int[] branchDestination = {(int)(basePos[0] + x), (int)(j1 + y), (int)(basePos[2] + z)};
			int[] branchOrigin =  {basePos[0], j1, basePos[2]};
			placeBlockLineCurved(branchOrigin, branchDestination, TFCBlocks.logNatural);
		}
	}

	/**
	 * Generates additional wood blocks to fill out the bases of different leaf nodes that would otherwise degrade.
	 */
	private void generateLeafNodeBases()
	{
		int var1 = 0;
		int var2 = this.leafNodes.length;

		for (int[] var3 = new int[] {this.basePos[0], this.basePos[1], this.basePos[2]}; var1 < var2; ++var1)
		{
			int[] var4 = this.leafNodes[var1];
			//int[] var5 = new int[] {var4[0], var4[1], var4[2]};
			var3[1] = var4[3];
			//int var6 = var3[1] - this.basePos[1];
			/*if (this.leafNodeNeedsBase(var6))
			{
				//this.placeBlockLine(var3, var5, TFCBlocks.LogNatural2);
			}*/
		}
	}

	/**
	 * Generates a list of leaf nodes for the tree, to be populated by generateLeaves.
	 */
	private void generateLeafNodeList()
	{
		this.height = (int) (this.heightLimit * WorldGenKapokTrees.HEIGHT_ATTENUATION);

		if (this.height >= this.heightLimit)
			this.height = this.heightLimit - 1;

		int var1 = (int)(1.382D + Math.pow(this.leafDensity * this.heightLimit / 13.0D, 2.0D));
		if (var1 < 1)
			var1 = 1;

		int[][] var2 = new int[var1 * this.heightLimit][4];
		int var3 = this.basePos[1] + this.heightLimit - this.leafDistanceLimit;
		int var4 = 1;
		int var5 = this.basePos[1] + this.height;
		int var6 = var3 - this.basePos[1];
		var2[0][0] = this.basePos[0];
		var2[0][1] = var3;
		var2[0][2] = this.basePos[2];
		var2[0][3] = var5;
		--var3;

		while (var6 >= 0)
		{
			int var7 = 0;
			float var8 = this.layerSize(var6);

			if (var8 < 0.0F)
			{
				--var3;
				--var6;
			}
			else
			{
				for (double var9 = 0.5D; var7 < var1; ++var7)
				{
					double var11 = this.scaleWidth * var8 * (this.rand.nextFloat() + 0.328D);
					double var13 = this.rand.nextFloat() * 2.0D * Math.PI;
					int var15 = MathHelper.floor_double(var11 * Math.sin(var13) + this.basePos[0] + var9);
					int var16 = MathHelper.floor_double(var11 * Math.cos(var13) + this.basePos[2] + var9);
					int[] var17 = new int[] {var15, var3, var16};
					int[] var18 = new int[] {var15, var3 + this.leafDistanceLimit, var16};

					if (this.checkBlockLine(var17, var18) == -1)
					{
						int[] var19 = new int[] {this.basePos[0], this.basePos[1], this.basePos[2]};
						double var20 = Math.sqrt(Math.pow(Math.abs(this.basePos[0] - var17[0]), 2.0D) + Math.pow(Math.abs(this.basePos[2] - var17[2]), 2.0D));
						double var22 = var20 * WorldGenKapokTrees.BRANCH_SLOPE;

						if (var17[1] - var22 > var5)
							var19[1] = var5;
						else
							var19[1] = (int)(var17[1] - var22);

						if (this.checkBlockLine(var19, var17) == -1)
						{
							var2[var4][0] = var15;
							var2[var4][1] = var3;
							var2[var4][2] = var16;
							var2[var4][3] = var19[1];
							++var4;
						}
					}
				}
				--var3;
				--var6;
			}
		}
		this.leafNodes = new int[var4][4];
		System.arraycopy(var2, 0, this.leafNodes, 0, var4);
	}

	/**
	 * Generates the leaf portion of the tree as specified by the leafNodes list.
	 */
	/*void generateLeaves()
	{
		int var1 = 0;

		for (int var2 = this.leafNodes.length; var1 < var2; ++var1)
		{
			int var3 = this.leafNodes[var1][0];
			int var4 = this.leafNodes[var1][1];
			int var5 = this.leafNodes[var1][2];
			//this.generateLeafNode(var3, var4, var5);
		}
		var1 = 0;
		for (int var2 = this.leafNodes.length; var1 < var2; ++var1)
		{
			int[] trunkBottom = {basePos[0],basePos[1]+this.height,basePos[2]};
			int[] node = {this.leafNodes[var1][0],this.leafNodes[var1][1]+2,this.leafNodes[var1][2]};
			//this.placeBlockLine(trunkBottom, node, TFCBlocks.LogNatural2);
		}
	}*/

	/**
	 * Places the trunk for the big tree that is being generated. Able to generate double-sized trunks by changing a
	 * field that is always 1 to 2.
	 */
	private void generateTrunk()
	{
		int var1 = this.basePos[0];
		int var2 = this.basePos[1];
		int var3 = this.basePos[1] + this.height;
		int var4 = this.basePos[2];
		int[] var5 = new int[] {var1, var2, var4};
		int[] var6 = new int[] {var1, var3, var4};
		this.placeBlockLine(var5, var6, TFCBlocks.logNatural);

		if (this.trunkSize == 2)
		{
			++var5[0];
			++var6[0];
			this.placeBlockLine(var5, var6, TFCBlocks.logNatural);
			++var5[2];
			++var6[2];
			this.placeBlockLine(var5, var6, TFCBlocks.logNatural);
			var5[0] += -1;
			var6[0] += -1;
			this.placeBlockLine(var5, var6, TFCBlocks.logNatural);
		}
	}

	private void genTreeLayer(int par1, int par2, int par3, float par4, byte par5, Block b)
	{
		int var7 = (int)(par4 + 0.618D);
		byte var8 = OTHER_COORD_PAIRS[par5];
		byte var9 = OTHER_COORD_PAIRS[par5 + 3];
		int[] var10 = new int[] {par1, par2, par3};
		int[] var11 = new int[] {0, 0, 0};
		int var12 = -var7;
		int var13 = -var7;

		for (var11[par5] = var10[par5]; var12 <= var7; ++var12)
		{
			var11[var8] = var10[var8] + var12;
			var13 = -var7;

			while (var13 <= var7)
			{
				double var15 = Math.sqrt(Math.pow(Math.abs(var12) + 0.5D, 2.0D) + Math.pow(Math.abs(var13) + 0.5D, 2.0D));

				if (var15 > par4)
				{
					++var13;
				}
				else
				{
					var11[var9] = var10[var9] + var13;
					Block var14 = this.worldObj.getBlock(var11[0], var11[1], var11[2]);

					if (!var14.isAir(worldObj, var11[0], var11[1], var11[2]) && (var14 != TFCBlocks.leaves || var14 != TFCBlocks.leaves2) && var14 != TFCBlocks.vine)
					{
						++var13;
					}
					else
					{
						this.setBlockAndNotifyAdequately(worldObj, var11[0], var11[1], var11[2], b, treeId);
						if(rand.nextInt(8) > 0)
						{
							int side = rand.nextInt(4);
							if (side == 0 && worldObj.isAirBlock(var11[0]-1, var11[1], var11[2]))
							{
								this.setBlockAndNotifyAdequately(worldObj, var11[0]-1, var11[1], var11[2], TFCBlocks.vine, 8);
							}
							else if (side == 1 && worldObj.isAirBlock(var11[0], var11[1], var11[2]-1))
							{
								this.setBlockAndNotifyAdequately(worldObj, var11[0], var11[1], var11[2]-1, TFCBlocks.vine, 1);
							}
							else if (side == 2 && worldObj.isAirBlock(var11[0]+1, var11[1], var11[2]))
							{
								this.setBlockAndNotifyAdequately(worldObj, var11[0]+1, var11[1], var11[2], TFCBlocks.vine, 2);
							}
							else if (side == 3 && worldObj.isAirBlock(var11[0], var11[1], var11[2]+1))
							{
								this.setBlockAndNotifyAdequately(worldObj, var11[0], var11[1], var11[2]+1, TFCBlocks.vine, 4);
							}
						}
						++var13;
					}
				}
			}
		}
	}

	/**
	 * Gets the rough size of a layer of the tree.
	 */
	private float layerSize(int par1)
	{
		if (par1 < this.heightLimit * 0.3D)
		{
			return -1.618F;
		}
		else
		{
			float var2 = this.heightLimit / 2.0F;
			float var3 = this.heightLimit / 2.0F - par1;
			float var4;

			if (var3 == 0.0F)
				var4 = var2;
			else if (Math.abs(var3) >= var2)
				var4 = 0.0F;
			else
				var4 = (float)Math.sqrt(Math.pow(Math.abs(var2), 2.0D) - Math.pow(Math.abs(var3), 2.0D));

			var4 *= 0.5F;
			return var4;
		}
	}

	/**
	 * Indicates whether or not a leaf node requires additional wood to be added to preserve integrity.
	 */
	/*private boolean leafNodeNeedsBase(int par1)
	{
		return par1 >= this.leafDistanceLimit;
	}*/

	private float leafSize(int par1)
	{
		return par1 >= 0 && par1 < this.leafDistanceLimit ? par1 != 0 && par1 != this.leafDistanceLimit - 1 ? 4.0F : 3.0F : -1.0F;
	}

	/**
	 * Places a line of the specified block ID into the world from the first coordinate triplet to the second.
	 */
	private void placeBlockLine(int[] par1ArrayOfInteger, int[] par2ArrayOfInteger, Block par3)
	{
		int[] var4 = new int[] {0, 0, 0};
		byte var5 = 0;
		byte var6;

		for (var6 = 0; var5 < 3; ++var5)
		{
			var4[var5] = par2ArrayOfInteger[var5] - par1ArrayOfInteger[var5];
			if (Math.abs(var4[var5]) > Math.abs(var4[var6]))
				var6 = var5;
		}

		if (var4[var6] != 0)
		{
			byte var7 = OTHER_COORD_PAIRS[var6];
			byte var8 = OTHER_COORD_PAIRS[var6 + 3];
			byte var9;

			if (var4[var6] > 0)
				var9 = 1;
			else
				var9 = -1;

			double var10 = (double)var4[var7] / (double)var4[var6];
			double var12 = (double)var4[var8] / (double)var4[var6];
			int[] var14 = new int[] {0, 0, 0};
			int var15 = 0;

			for (int var16 = var4[var6] + var9; var15 != var16; var15 += var9)
			{
				var14[var6] = MathHelper.floor_double(par1ArrayOfInteger[var6] + var15 + 0.5D);
				var14[var7] = MathHelper.floor_double(par1ArrayOfInteger[var7] + var15 * var10 + 0.5D);
				var14[var8] = MathHelper.floor_double(par1ArrayOfInteger[var8] + var15 * var12 + 0.5D);
				if(worldObj.isAirBlock(var14[0], var14[1], var14[2]) || worldObj.getBlock(var14[0], var14[1], var14[2]) == TFCBlocks.leaves ||
						worldObj.getBlock(var14[0], var14[1], var14[2]) == TFCBlocks.leaves2)
				{
					this.setBlockAndNotifyAdequately(this.worldObj, var14[0], var14[1], var14[2], par3, treeId);
				}
			}
			if(par1ArrayOfInteger[0]==par2ArrayOfInteger[0] && par1ArrayOfInteger[2]==par2ArrayOfInteger[2] && par1ArrayOfInteger[1]<par2ArrayOfInteger[1])
			{
				for(int i = -1; i <2;i++)
				{
					for(int k = -1; k < 2; k++)
					{
						if(rand.nextBoolean() && par1ArrayOfInteger[1] > 1 &&
								Math.abs(i)!=Math.abs(k) &&
								!worldObj.isAirBlock(i + par1ArrayOfInteger[0], par1ArrayOfInteger[1] - 1, par1ArrayOfInteger[2] + k))
						{
							this.setBlockAndNotifyAdequately(this.worldObj,i + par1ArrayOfInteger[0], par1ArrayOfInteger[1], par1ArrayOfInteger[2]+k, par3, treeId);
						}
					}
				}
			}
		}
	}

	private void placeBlockLinePrimary(int[] par1ArrayOfInteger, int[] par2ArrayOfInteger, Block par3)
	{
		int[] var4 = new int[] {0, 0, 0};
		byte var5 = 0;
		byte var6;

		for (var6 = 0; var5 < 3; ++var5)
		{
			var4[var5] = par2ArrayOfInteger[var5] - par1ArrayOfInteger[var5];
			if (Math.abs(var4[var5]) > Math.abs(var4[var6]))
				var6 = var5;
		}

		if (var4[var6] != 0)
		{
			byte var7 = OTHER_COORD_PAIRS[var6];
			byte var8 = OTHER_COORD_PAIRS[var6 + 3];
			byte var9;

			if (var4[var6] > 0)
				var9 = 1;
			else
				var9 = -1;

			double var10 = (double)var4[var7] / (double)var4[var6];
			double var12 = (double)var4[var8] / (double)var4[var6];
			int[] var14 = new int[] {0, 0, 0};
			int var15 = 0;

			for (int var16 = var4[var6] + var9; var15 != var16; var15 += var9)
			{
				var14[var6] = MathHelper.floor_double(par1ArrayOfInteger[var6] + var15 + 0.5D);
				var14[var7] = MathHelper.floor_double(par1ArrayOfInteger[var7] + var15 * var10 + 0.5D);
				var14[var8] = MathHelper.floor_double(par1ArrayOfInteger[var8] + var15 * var12 + 0.5D);
				if(worldObj.isAirBlock(var14[0], var14[1], var14[2]) ||
						worldObj.getBlock(var14[0], var14[1], var14[2]) == TFCBlocks.leaves ||
						worldObj.getBlock(var14[0], var14[1], var14[2]) == TFCBlocks.leaves2)
				{
					if(rand.nextInt(3)==0 && Math.abs(var15) > 2)
					{
						int[] currentBlock = {var14[0],var14[1],var14[2]};
						int distance = 5;
						float x = rand.nextFloat();
						float y = Math.min(rand.nextFloat(), 1-x);
						float z = 1 - x -y;
						int[] destinationBlock = {
								var14[0] + (int)(distance * x * (rand.nextBoolean() ? 1 : -1)),
								var14[1] + (int)(distance * y * (rand.nextBoolean() ? 1 : -1)),
								var14[2] + (int)(distance * z * (rand.nextBoolean() ? 1 : -1))};
						placeBlockLineSecondary(currentBlock, destinationBlock,  par3);
					}
					this.setBlockAndNotifyAdequately(this.worldObj, var14[0], var14[1], var14[2], par3, treeId);
				}
			}
			this.generateLeafNode(par2ArrayOfInteger[0], par2ArrayOfInteger[1], par2ArrayOfInteger[2]);
		}
	}

	private void placeBlockLineCurved(int[] startingPos, int[] endingPos, Block par3)
	{	
		int[] midPointRaised = {(endingPos[0] - startingPos[0]) / 2, (int)((endingPos[1] - startingPos[1]) / 1.5D), (endingPos[1] - startingPos[1]) / 2};
		int[] newDest = {startingPos[0] + midPointRaised[0], startingPos[1] + midPointRaised[1], startingPos[2] + midPointRaised[2]};
		placeBlockLinePrimary(startingPos, newDest, par3);
		placeBlockLinePrimary(newDest, endingPos, par3);
	}

	private void placeBlockLineSecondary(int[] par1ArrayOfInteger, int[] par2ArrayOfInteger, Block par3)
	{
		int[] var4 = new int[] {0, 0, 0};
		byte var5 = 0;
		byte var6;

		for (var6 = 0; var5 < 3; ++var5)
		{
			var4[var5] = par2ArrayOfInteger[var5] - par1ArrayOfInteger[var5];
			if (Math.abs(var4[var5]) > Math.abs(var4[var6]))
				var6 = var5;
		}

		if (var4[var6] != 0)
		{
			byte var7 = OTHER_COORD_PAIRS[var6];
			byte var8 = OTHER_COORD_PAIRS[var6 + 3];
			byte var9;

			if (var4[var6] > 0)
				var9 = 1;
			else
				var9 = -1;

			double var10 = (double)var4[var7] / (double)var4[var6];
			double var12 = (double)var4[var8] / (double)var4[var6];
			int[] var14 = new int[] {0, 0, 0};
			int var15 = 0;

			for (int var16 = var4[var6] + var9; var15 != var16; var15 += var9)
			{
				var14[var6] = MathHelper.floor_double(par1ArrayOfInteger[var6] + var15 + 0.5D);
				var14[var7] = MathHelper.floor_double(par1ArrayOfInteger[var7] + var15 * var10 + 0.5D);
				var14[var8] = MathHelper.floor_double(par1ArrayOfInteger[var8] + var15 * var12 + 0.5D);
				if(worldObj.isAirBlock(var14[0], var14[1], var14[2]) ||
						worldObj.getBlock(var14[0], var14[1], var14[2]) == TFCBlocks.leaves ||
						worldObj.getBlock(var14[0], var14[1], var14[2]) == TFCBlocks.leaves2)
				{
					this.setBlockAndNotifyAdequately(this.worldObj, var14[0], var14[1], var14[2], par3, treeId);
				}
			}
			this.generateLeafNode(par2ArrayOfInteger[0], par2ArrayOfInteger[1]-1, par2ArrayOfInteger[2]);
		}
	}

	/**
	 * Rescales the generator settings, only used in WorldGenBigTree
	 */
	@Override
	public void setScale(double par1, double par3, double par5)
	{
		this.heightLimitLimit = (int)(par1 * 35.0D);

		if (par1 > 0.5D)
			this.leafDistanceLimit = 5;

		this.scaleWidth = par3;
		this.leafDensity = par5;
	}

	/**
	 * Returns a boolean indicating whether or not the current location for the tree, spanning basePos to to the height
	 * limit, is valid.
	 */
	private boolean validTreeLocation()
	{
		int[] var1 = new int[] {this.basePos[0], this.basePos[1], this.basePos[2]};
		int[] var2 = new int[] {this.basePos[0], this.basePos[1] + this.heightLimit - 1, this.basePos[2]};
		Block var3 = this.worldObj.getBlock(this.basePos[0], this.basePos[1] - 1, this.basePos[2]);

		if (!(TFC_Core.isSoil(var3)))
		{
			return false;
		}
		else
		{
			int var4 = this.checkBlockLine(var1, var2);

			if (var4 == -1)
			{
				return true;
			}
			else if (var4 < 6)
			{
				return false;
			}
			else
			{
				this.heightLimit = var4;
				return true;
			}
		}
	}
}
