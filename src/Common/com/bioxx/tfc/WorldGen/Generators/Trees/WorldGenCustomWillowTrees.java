package com.bioxx.tfc.WorldGen.Generators.Trees;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.Core.TFC_Core;

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
					if (world.getBlock(x1, y1 + y, z1).canBeReplacedByLeaves(world, x1, y1 + y, z1))
					{
						world.setBlock (x1, y1 + y, z1, TFCBlocks.Leaves, treeId, 0x2);
						for (int a = 0 ; a < random.nextInt (2) + 2 ; a++)
						{
							Block b = world.getBlock(x1, y1 - 1 - a + y, z1);
							if (b.canBeReplacedByLeaves(world, x1, y1 - 1 - a + y, z1))
								world.setBlock (x1, y1 - 1 - a + y, z1, TFCBlocks.Leaves, treeId, 0x2);
						}
					}
				}
			}
		}
	}

	@Override
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
						if (i4 == TFCBlocks.FreshWaterStationary)
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

		if (!(TFC_Core.isSoil(world.getBlock(xCoord, yCoord - 1, zCoord))) || yCoord >= world.getHeight() - height - 1)
			return false;

		int z1, x1, y, x, z;
		y = height + yCoord;
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
			world.setBlock(xCoord, yCoord + l1, zCoord, TFCBlocks.LogNatural, treeId, 0x2);
		}

		return true;
	}
}