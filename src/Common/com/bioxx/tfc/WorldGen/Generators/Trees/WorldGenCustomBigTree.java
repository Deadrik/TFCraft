package com.bioxx.tfc.WorldGen.Generators.Trees;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.TFCBlocks;

public class WorldGenCustomBigTree extends WorldGenerator
{
	/**
	 * Contains three sets of two values that provide complimentary indices for a given 'major' index - 1 and 2 for 0, 0
	 * and 2 for 1, and 0 and 1 for 2.
	 */
	private static final byte[] OTHER_COORD_PAIRS = new byte[] { (byte) 2, (byte) 0, (byte) 0, (byte) 1, (byte) 2, (byte) 1 };
	private final Random rand = new Random();

	/** Reference to the World object. */
	private World worldObj;
	private int[] basePos = new int[] { 0, 0, 0 };
	private int heightLimit;
	private int height;
	private static final double HEIGHT_ATTENUATION = 0.618D;
	//private static final double branchDensity = 1.0D;
	private static final double BRANCH_SLOPE = 0.381D;
	private double scaleWidth = 1.0D;
	private double leafDensity = 1.0D;

	/**
	 * Currently always 1, can be set to 2 in the class constructor to generate a double-sized tree trunk for big trees.
	 */
	//private static final int trunkSize = 1;

	/**
	 * Sets the limit of the random value used to initialize the height limit.
	 */
	private int heightLimitLimit = 12;

	/**
	 * Sets the distance limit for how far away the generator will populate leaves from the base leaf node.
	 */
	private int leafDistanceLimit = 4;

	/** Contains a list of a points at which to generate groups of leaves. */
	private int[][] leafNodes;

	private final int treeId;

	public WorldGenCustomBigTree(boolean par1, int id)
	{
		super(par1);
		treeId = id;
	}

	/**
	 * Checks a line of blocks in the world from the first coordinate to triplet to the second, returning the distance
	 * (in blocks) before a non-air, non-leaf block is encountered and/or the end is encountered.
	 */
	private int checkBlockLine(int[] startCoords, int[] endCoords)
	{
		int[] distance = new int[] { 0, 0, 0 };
		byte axis = 0;
		byte newAxis;

		for (newAxis = 0; axis < 3; ++axis)
		{
			distance[axis] = endCoords[axis] - startCoords[axis];
			if (Math.abs(distance[axis]) > Math.abs(distance[newAxis]))
				newAxis = axis;
		}

		if (distance[newAxis] == 0)
		{
			return -1;
		}
		else
		{
			byte newAxisInverse = OTHER_COORD_PAIRS[newAxis];
			byte newAxisInversePerp = OTHER_COORD_PAIRS[newAxis + 3];
			byte direction;

			if (distance[newAxis] > 0)
				direction = 1;
			else
				direction = -1;

			double var9 = (double) distance[newAxisInverse] / (double) distance[newAxis];
			double var11 = (double) distance[newAxisInversePerp] / (double) distance[newAxis];
			int[] coords = new int[] { 0, 0, 0 };
			int result = 0;
			int endPoint;

			for (endPoint = distance[newAxis] + direction; result != endPoint; result += direction)
			{
				coords[newAxis] = startCoords[newAxis] + result;
				coords[newAxisInverse] = MathHelper.floor_double(startCoords[newAxisInverse] + result * var9);
				coords[newAxisInversePerp] = MathHelper.floor_double(startCoords[newAxisInversePerp] + result * var11);
				Block block = this.worldObj.getBlock(coords[0], coords[1], coords[2]);
				if (!(block.isAir(worldObj, coords[0], coords[1], coords[2]) || block == TFCBlocks.leaves || block == TFCBlocks.leaves2))
					break;
			}

			return result == endPoint ? -1 : Math.abs(result);
		}
	}

	@Override
	public boolean generate(World world, Random rand, int x, int y, int z)
	{
		this.worldObj = world;
		long seed = rand.nextLong();
		this.rand.setSeed(seed);
		this.basePos[0] = x;
		this.basePos[1] = y;
		this.basePos[2] = z;

		if (this.heightLimit == 0)
			this.heightLimit = 5 + this.rand.nextInt(this.heightLimitLimit);

		if (!this.validTreeLocation())
		{
			return false;
		}
		else
		{
			this.generateLeafNodeList();
			this.generateLeaves();
			this.generateTrunk();
			this.generateLeafNodeBases();
			return true;
		}
	}

	/**
	 * Generates the leaves surrounding an individual entry in the leafNodes list.
	 */
	private void generateLeafNode(int x, int y, int z)
	{
		int yCoord = y;
		for (int range = y + this.leafDistanceLimit; yCoord < range; ++yCoord)
		{
			float leafSizeAtHeight = this.leafSize(yCoord - y);
			this.genTreeLayer(x, yCoord, z, leafSizeAtHeight, (byte) 1, TFCBlocks.leaves);
		}
	}

	/**
	 * Generates additional wood blocks to fill out the bases of different leaf nodes that would otherwise degrade.
	 */
	private void generateLeafNodeBases()
	{
		int i = 0;

		for (int[] location = new int[] { this.basePos[0], this.basePos[1], this.basePos[2] }; i < this.leafNodes.length; ++i)
		{
			int[] leafPoints = this.leafNodes[i];
			int[] newLocation = new int[] { leafPoints[0], leafPoints[1], leafPoints[2] };
			location[1] = leafPoints[3];
			int baseHeight = location[1] - this.basePos[1];
			if (this.leafNodeNeedsBase(baseHeight))
				this.placeBlockLine(location, newLocation, TFCBlocks.logNatural);
		}
	}

	/**
	 * Generates a list of leaf nodes for the tree, to be populated by generateLeaves.
	 */
	private void generateLeafNodeList()
	{
		this.height = (int) (this.heightLimit * WorldGenCustomBigTree.HEIGHT_ATTENUATION);
		if (this.height >= this.heightLimit)
			this.height = this.heightLimit - 1;

		int var1 = (int) (1.382D + Math.pow(this.leafDensity * this.heightLimit / 13.0D, 2.0D));
		if (var1 < 1)
			var1 = 1;

		int[][] var2 = new int[var1 * this.heightLimit][4];
		int trunkTop = this.basePos[1] + this.heightLimit - this.leafDistanceLimit;
		int var4 = 1;
		int treeTop = this.basePos[1] + this.height;
		int height = trunkTop - this.basePos[1];
		var2[0][0] = this.basePos[0];
		var2[0][1] = trunkTop;
		var2[0][2] = this.basePos[2];
		var2[0][3] = treeTop;
		--trunkTop;

		while (height >= 0)
		{
			int var7 = 0;
			float var8 = this.layerSize(height);

			if (var8 < 0.0F)
			{
				--trunkTop;
				--height;
			}
			else
			{
				for (double var9 = 0.5D; var7 < var1; ++var7)
				{
					double var11 = this.scaleWidth * var8 * (this.rand.nextFloat() + 0.328D);
					double var13 = this.rand.nextFloat() * 2.0D * Math.PI;
					int var15 = MathHelper.floor_double(var11 * Math.sin(var13) + this.basePos[0] + var9);
					int var16 = MathHelper.floor_double(var11 * Math.cos(var13) + this.basePos[2] + var9);
					int[] var17 = new int[] { var15, trunkTop, var16 };
					int[] var18 = new int[] { var15, trunkTop + this.leafDistanceLimit, var16 };

					if (this.checkBlockLine(var17, var18) == -1)
					{
						int[] var19 = new int[] { this.basePos[0], this.basePos[1], this.basePos[2] };
						double var20 = Math.sqrt(Math.pow(Math.abs(this.basePos[0] - var17[0]), 2.0D) + Math.pow(Math.abs(this.basePos[2] - var17[2]), 2.0D));
						double var22 = var20 * WorldGenCustomBigTree.BRANCH_SLOPE;

						if (var17[1] - var22 > treeTop)
							var19[1] = treeTop;
						else
							var19[1] = (int) (var17[1] - var22);

						if (this.checkBlockLine(var19, var17) == -1)
						{
							var2[var4][0] = var15;
							var2[var4][1] = trunkTop;
							var2[var4][2] = var16;
							var2[var4][3] = var19[1];
							++var4;
						}
					}
				}
				--trunkTop;
				--height;
			}
		}
		this.leafNodes = new int[var4][4];
		System.arraycopy(var2, 0, this.leafNodes, 0, var4);
	}

	/**
	 * Generates the leaf portion of the tree as specified by the leafNodes list.
	 */
	private void generateLeaves()
	{
		for (int i = 0; i < this.leafNodes.length; ++i)
		{
			int x = this.leafNodes[i][0];
			int y = this.leafNodes[i][1];
			int z = this.leafNodes[i][2];
			this.generateLeafNode(x, y, z);
		}
	}

	/**
	 * Places the trunk for the big tree that is being generated. Able to generate double-sized trunks by changing a
	 * field that is always 1 to 2.
	 */
	private void generateTrunk()
	{
		int x = this.basePos[0];
		int y = this.basePos[1];
		int maxY = this.basePos[1] + this.height;
		int z = this.basePos[2];
		int[] bottom = new int[] { x, y, z };
		int[] top = new int[] { x, maxY, z };
		this.placeBlockLine(bottom, top, TFCBlocks.logNatural);

		/*if (WorldGenCustomBigTree.trunkSize == 2)
		{
			++var5[0];
			++var6[0];
			this.placeBlockLine(var5, var6, TFCBlocks.LogNatural);
			++var5[2];
			++var6[2];
			this.placeBlockLine(var5, var6, TFCBlocks.LogNatural);
			var5[0] += -1;
			var6[0] += -1;
			this.placeBlockLine(var5, var6, TFCBlocks.LogNatural);
		}*/
	}

	private void genTreeLayer(int x, int y, int z, float leafSizeAtHeight, byte axis, Block b)
	{
		int range = (int) (leafSizeAtHeight + 0.618D);
		byte axisInverse = OTHER_COORD_PAIRS[axis];
		byte axisInversePerp = OTHER_COORD_PAIRS[axis + 3];
		int[] startCoords = new int[] { x, y, z };
		int[] coords = new int[] { 0, 0, 0 };
		int width1 = -range;
		int width2 = -range;

		for (coords[axis] = startCoords[axis]; width1 <= range; ++width1)
		{
			coords[axisInverse] = startCoords[axisInverse] + width1;
			width2 = -range;

			while (width2 <= range)
			{
				double distance = Math.sqrt(Math.pow(Math.abs(width1) + 0.5D, 2.0D) + Math.pow(Math.abs(width2) + 0.5D, 2.0D));

				if (distance > leafSizeAtHeight)
				{
					++width2;
				}
				else
				{
					coords[axisInversePerp] = startCoords[axisInversePerp] + width2;
					Block block = this.worldObj.getBlock(coords[0], coords[1], coords[2]);
					if (worldObj.isAirBlock(coords[0], coords[1], coords[2]) || block == TFCBlocks.leaves || block == TFCBlocks.leaves2)
						setBlockAndNotifyAdequately(this.worldObj, coords[0], coords[1], coords[2], b, treeId);
					++width2;
				}
			}
		}
	}

	/**
	 * Gets the rough size of a layer of the tree.
	 */
	private float layerSize(int height)
	{
		if (height < this.heightLimit * 0.3D)
		{
			return -1.618F;
		}
		else
		{
			float halfLimit = this.heightLimit / 2.0F;
			float leafBase = this.heightLimit / 2.0F - height;
			float size;

			if (leafBase == 0.0F)
				size = halfLimit;
			else if (Math.abs(leafBase) >= halfLimit)
				size = 0.0F;
			else
				size = (float) Math.sqrt(Math.pow(Math.abs(halfLimit), 2.0D) - Math.pow(Math.abs(leafBase), 2.0D));

			size *= 0.5F;
			return size;
		}
	}

	/**
	 * Indicates whether or not a leaf node requires additional wood to be added to preserve integrity.
	 */
	private boolean leafNodeNeedsBase(int par1)
	{
		return par1 >= this.heightLimit * 0.2D;
	}

	private float leafSize(int height)
	{
		return height >= 0 && height < this.leafDistanceLimit ? height != 0 && height != this.leafDistanceLimit - 1 ? 3.0F : 2.0F : -1.0F;
	}

	/**
	 * Places a line of the specified block ID into the world from the first coordinate triplet to the second.
	 */
	private void placeBlockLine(int[] bottom, int[] top, Block b)
	{
		int[] location = new int[] { 0, 0, 0 };
		byte axis = 0;
		byte newAxis;

		for (newAxis = 0; axis < 3; ++axis)
		{
			location[axis] = top[axis] - bottom[axis];
			if (Math.abs(location[axis]) > Math.abs(location[newAxis]))
				newAxis = axis;
		}

		if (location[newAxis] != 0)
		{
			byte newAxisInverse = OTHER_COORD_PAIRS[newAxis];
			byte newAxisInversePerp = OTHER_COORD_PAIRS[newAxis + 3];
			byte direction;

			if (location[newAxis] > 0)
				direction = 1;
			else
				direction = -1;

			double var10 = (double) location[newAxisInverse] / (double) location[newAxis];
			double var12 = (double) location[newAxisInversePerp] / (double) location[newAxis];
			int[] coords = new int[] { 0, 0, 0 };

			for (int loc = 0; loc != location[newAxis] + direction; loc += direction)
			{
				coords[newAxis] = MathHelper.floor_double(bottom[newAxis] + loc + 0.5D);
				coords[newAxisInverse] = MathHelper.floor_double(bottom[newAxisInverse] + loc * var10 + 0.5D);
				coords[newAxisInversePerp] = MathHelper.floor_double(bottom[newAxisInversePerp] + loc * var12 + 0.5D);
				Block block = worldObj.getBlock(coords[0], coords[1], coords[2]);
				if (worldObj.isAirBlock(coords[0], coords[1], coords[2]) || block == TFCBlocks.leaves || block == TFCBlocks.leaves2)
					this.setBlockAndNotifyAdequately(this.worldObj, coords[0], coords[1], coords[2], b, treeId);
			}
		}
	}

	/**
	 * Rescales the generator settings, only used in WorldGenBigTree
	 */
	@Override
	public void setScale(double heightScale, double widthScale, double densityScale)
	{
		this.heightLimitLimit = (int) (heightScale * 12.0D);
		if (heightScale > 0.5D)
			this.leafDistanceLimit = 5;
		this.scaleWidth = widthScale;
		this.leafDensity = densityScale;
	}

	/**
	 * Returns a boolean indicating whether or not the current location for the tree, spanning basePos to to the height
	 * limit, is valid.
	 */
	private boolean validTreeLocation()
	{
		int[] coords = new int[] { this.basePos[0], this.basePos[1], this.basePos[2] };
		int[] topCoords = new int[] { this.basePos[0], this.basePos[1] + this.heightLimit - 1, this.basePos[2] };
		Block block = this.worldObj.getBlock(this.basePos[0], this.basePos[1] - 1, this.basePos[2]);

		if (!(TFC_Core.isSoil(block)))
		{
			return false;
		}
		else
		{
			int distance = this.checkBlockLine(coords, topCoords);
			if (distance == -1)
			{
				return true;
			}
			else if (distance < 6)
			{
				return false;
			}
			else
			{
				this.heightLimit = distance;
				return true;
			}
		}
	}
}
