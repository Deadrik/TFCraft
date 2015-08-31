package com.bioxx.tfc.WorldGen.Generators.Trees;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.TFCBlocks;

public class WorldGenCustomCedarTrees extends WorldGenerator
{
	private final int treeId;

	public WorldGenCustomCedarTrees(boolean flag, int id)
	{
		super(flag);
		treeId=id;
	}
	@Override
	public boolean generate(World world, Random random, int xCoord, int yCoord, int zCoord)
	{
		int treeHeight = random.nextInt(6) + 3;
		if (yCoord < 1 || yCoord + treeHeight + 1 > world.getHeight())
			return false;

		boolean flag = true;
		for (int y = yCoord; y <= yCoord + 1 + treeHeight; y++)
		{
			byte byte0 = 1;
			if (y == yCoord)
				byte0 = 0;
			if (y >= yCoord + 1 + treeHeight - 2)
				byte0 = 2;

			for (int x = xCoord - byte0; x <= xCoord + byte0 && flag; x++)
			{
				for (int z = zCoord - byte0; z <= zCoord + byte0 && flag; z++)
				{
					if (y >= 0 && y < world.getHeight())
					{
						Block j3 = world.getBlock(x, y, z);
						if (!j3.isAir(world, x, y, z) && !j3.canBeReplacedByLeaves(world, x, y, z))
							flag = false;
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
		if (!(TFC_Core.isSoil(var3))|| yCoord >= world.getHeight() - treeHeight - 1)
			return false;

		//Now we create the leaves. generates from the bottom up.
		for (int treeHeightOffset = yCoord + 1; treeHeightOffset <= yCoord + treeHeight; treeHeightOffset++)
		{
			int treeDiameter = treeHeightOffset - (yCoord + treeHeight);
			int treeRadius = 1 - treeDiameter / 2;
			for (int xPos = xCoord - 1; xPos <= xCoord + 1; xPos++)
			{
				int l3 = xPos - xCoord;
				for (int zPos = zCoord - 1; zPos <= zCoord + 1; zPos++)
				{
					int j4 = zPos - zCoord;
					if ((Math.abs(l3) != treeRadius || Math.abs(j4) != treeRadius || random.nextInt(2) != 0 && treeDiameter != 0) && 
							world.getBlock(xPos, treeHeightOffset, zPos).canBeReplacedByLeaves(world, xPos, treeHeightOffset, zPos))
					{
						setBlockAndNotifyAdequately(world, xPos, treeHeightOffset, zPos, TFCBlocks.leaves, treeId);
					}
				}
			}
		}
		//Here we crate the tree trunk
		for (int l1 = 0; l1 < treeHeight; l1++)
		{
			setBlockAndNotifyAdequately(world, xCoord, yCoord + l1, zCoord, TFCBlocks.logNatural, treeId);
		}
		return true;
	}
}
