package com.bioxx.tfc.WorldGen.Generators.Trees;

import java.util.Random;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.WorldGen.DataLayer;
import com.bioxx.tfc.WorldGen.TFCWorldChunkManager;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenCustomWillowTrees extends WorldGenerator
{
	private int treeId;

	public WorldGenCustomWillowTrees(boolean flag, int id)
	{
		super(flag);
		treeId=id;
	}

	void addBranch (int x, int y, int z, int x1, int z1, Random random, World world)
	{
		if (random.nextInt (3) == 0)
		{
			int X = (random.nextInt (8) - 4) * z1;
			X += (random.nextInt (2) + 3) * x1;
			int Z = (random.nextInt (8) - 4) * x1;
			Z += (random.nextInt (2) + 3) * z1;
			int Y = random.nextInt (3) + random.nextInt (1);
			int length = (int) Math.sqrt (Math.pow (X, 2) + Math.pow (Y, 2) + Math.pow (Z, 2));
			for (int a = 0 ; a < length ; a++)
			{
				if(world.isAirBlock(X * a / length + x, Y * a / length + y, Z * a / length + z))
					world.setBlock (X * a / length + x, Y * a / length + y, Z * a / length + z, TFCBlocks.LogNatural, treeId, 0x2);
			}
			createLeafGroup (X + x, Y + y, Z + z, random, world);
		}
	}

	void createLeafGroup (int x, int y, int z, Random random, World world)
	{
		for (int y1 = 0 ; y1 < 2 ; y1++)
		{
			for (int x1 = x - 2 + y1 ; x1 < x + 2 - y1 ; x1++)
			{
				for (int z1 = z - 2 + y1 ; z1 < z + 2 - y1 ; z1++)
				{
					if (!world.getBlock(x1, y1 + y, z1).isOpaqueCube())
					{
						world.setBlock (x1, y1 + y, z1, TFCBlocks.Leaves, treeId, 0x2);
						for (int a = 0 ; a < random.nextInt (2) + 2 ; a++)
						{
							Block b = world.getBlock(x1, y1 - 1 - a + y, z1);
							if (!b.isOpaqueCube() && b == Blocks.air)
								world.setBlock (x1, y1 - 1 - a + y, z1, TFCBlocks.Leaves, treeId, 0x2);
						}
					}
				}
			}
		}
	}

	private void func_35265_a (World world, int i, int j, int k, int l)
	{
		if(world.isAirBlock(i, j, k) && Blocks.vine.canBlockStay(world, i, j, k))
			world.setBlock(i, j, k, Blocks.vine, l, 0x2);

		for (int i1 = 4 ; world.isAirBlock(i, --j, k) && i1 > 0 ; i1--)
		{
			if(world.isAirBlock(i, j, k) && Blocks.vine.canBlockStay(world, i, j, k))
				world.setBlock (i, j, k, Blocks.vine, l, 0x2);
		}
	}

	public boolean generate (World world, Random random, int xCoord, int yCoord, int zCoord)
	{
		int height = random.nextInt (2) + 3;
		for (; world.getBlock(xCoord, yCoord - 1, zCoord).getMaterial() == Material.water ; yCoord--)
		{
		}
		boolean flag = true;
		if (yCoord < 1 || yCoord + height + 5 > world.getHeight())
			return false;

		for (int i1 = yCoord ; i1 <= yCoord + 1 + height ; i1++)
		{
			byte byte0 = 1;
			if (i1 == yCoord)
				byte0 = 0;
			if (i1 >= yCoord + 1 + height - 2)
				byte0 = 3;

			for (int j2 = xCoord - byte0 ; j2 <= xCoord + byte0 && flag ; j2++)
			{
				for (int j3 = zCoord - byte0 ; j3 <= zCoord + byte0 && flag ; j3++)
				{
					if (i1 >= 0 && i1 < world.getHeight())
					{
						Block i4 = world.getBlock(j2, i1, j3);
						if (i4 == Blocks.air || i4 == TFCBlocks.Leaves || i4 == TFCBlocks.Leaves2)
							continue;
						if (i4 == TFCBlocks.SaltWaterStill || i4 == TFCBlocks.SaltWaterFlowing)
						{
							if (i1 > yCoord)
								flag = false;
						}
						else
						{
							flag = false;
						}
					}
					else
					{
						flag = false;
					}
				}
			}
		}

		if (!flag)
			return false;

		Block var3 = world.getBlock(xCoord, yCoord - 1, zCoord);
		if (!(TFC_Core.isSoil(var3))|| yCoord >= world.getHeight() - height - 1 ||
				world.getBlock(xCoord, yCoord, zCoord).getMaterial() == Material.water)
		{
			return false;
		}

		if(world.isAirBlock(xCoord, yCoord - 1, zCoord))
		{
			DataLayer rockLayer1 = ((TFCWorldChunkManager)world.getWorldChunkManager()).getRockLayerAt(xCoord, zCoord, 0);
			//set the block below the tree to dirt.
			world.setBlock(xCoord, yCoord - 1, zCoord, TFC_Core.getTypeForGrass(rockLayer1.data2), TFC_Core.getSoilMetaFromStone(rockLayer1.block, rockLayer1.data2), 0x2);
		}

		int z1, x1, y,x,z;
		y = height+yCoord;
		x = xCoord;
		z = zCoord;

		for (int n = 0 ; n < 2 ; n++)
		{
			x1 = 1 - n * (random.nextInt (1) - random.nextInt (1));
			z1 = 0 - n * (random.nextInt (1) - random.nextInt (1));
			int X = (random.nextInt (6) - 3) * z1;
			X += (random.nextInt (1) + 2) * x1;
			int Z = (random.nextInt (6) - 3) * x1;
			Z += (random.nextInt (1) + 2) * z1;
			int Y = random.nextInt (2) + 3;
			int length = (int) Math.sqrt (Math.pow (X, 2) + Math.pow (Y, 2) + Math.pow (Z, 2));
			for (int a = 0 ; a < length ; a++)
			{
				if(world.isAirBlock(X * a / length + x, Y * a / length + y, Z * a / length + z))
					world.setBlock (X * a / length + x, Y * a / length + y, Z * a / length + z, TFCBlocks.LogNatural, treeId, 0x2);
				addBranch (X * a / length + x, Y * a / length + y, Z * a / length + z, -1, 0, random, world);
				addBranch (X * a / length + x, Y * a / length + y, Z * a / length + z, 0, -1, random, world);
				addBranch (X * a / length + x, Y * a / length + y, Z * a / length + z, 1, 0, random, world);
				addBranch (X * a / length + x, Y * a / length + y, Z * a / length + z, 0, 1, random, world);
			}
			createLeafGroup (X + x, Y + y, Z + z, random, world);
		}
		createLeafGroup (xCoord, yCoord + height + 1, zCoord, random, world);
		for (int l1 = 0 ; l1 < height ; l1++)
		{
			Block l2 = world.getBlock(xCoord, yCoord + l1, zCoord);
			if (l2 == Blocks.air || l2 == TFCBlocks.Leaves || l2 == TFCBlocks.Leaves2 || l2 == TFCBlocks.SaltWaterFlowing || l2 == TFCBlocks.SaltWaterStill ||
					l2.canBeReplacedByLeaves(world, xCoord, yCoord + l1, zCoord))
			{
				world.setBlock(xCoord, yCoord + l1, zCoord, TFCBlocks.LogNatural, treeId, 0x2);
			}
		}

//		for (int i2 = yCoord + height ; i2 <= yCoord + height + 5 ; i2++)
//		{
//			for (int k4 = xCoord - 6 ; k4 <= xCoord + 6 ; k4++)
//			{
//				for (int i5 = zCoord - 6 ; i5 <= zCoord + 6 ; i5++)
//				{
//					if (world.getBlockId (k4, i2, i5) != Block.leaves)
//					{
//						continue;
//					}
//					if (random.nextInt (4) == 0 && world.getBlockId (k4 - 1, i2, i5) == 0)
//					{
//						func_35265_a (world, k4 - 1, i2, i5, 8);
//					}
//					if (random.nextInt (4) == 0 && world.getBlockId (k4 + 1, i2, i5) == 0)
//					{
//						func_35265_a (world, k4 + 1, i2, i5, 2);
//					}
//					if (random.nextInt (4) == 0 && world.getBlockId (k4, i2, i5 - 1) == 0)
//					{
//						func_35265_a (world, k4, i2, i5 - 1, 1);
//					}
//					if (random.nextInt (4) == 0 && world.getBlockId (k4, i2, i5 + 1) == 0)
//					{
//						func_35265_a (world, k4, i2, i5 + 1, 4);
//					}
//				}
//			}
//		}

		return true;
	}
}