package com.bioxx.tfc.WorldGen.Generators.Trees;

import java.util.Random;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.Core.TFC_Core;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenCustomCedarTrees extends WorldGenerator
{
	private int treeId;

	public WorldGenCustomCedarTrees(boolean flag, int id)
	{
		super(flag);
		treeId=id;
	}
	@Override
	public boolean generate(World world, Random random, int xCoord, int yCoord, int zCoord)
	{
		int treeHeight = random.nextInt(6) + 3;
		boolean flag = true;
		if (yCoord < 1 || yCoord + treeHeight + 1 > world.getHeight())
			return false;

		for (int i1 = yCoord; i1 <= yCoord + 1 + treeHeight; i1++)
		{
			byte byte0 = 1;
			if (i1 == yCoord)
				byte0 = 0;
			if (i1 >= yCoord + 1 + treeHeight - 2)
				byte0 = 2;

			for (int i2 = xCoord - byte0; i2 <= xCoord + byte0 && flag; i2++)
			{
				for (int l2 = zCoord - byte0; l2 <= zCoord + byte0 && flag; l2++)
				{
					if (i1 >= 0 && i1 < world.getHeight())
					{
						Block j3 = world.getBlock(i2, i1, l2);
						if (j3 != Blocks.air && j3 != TFCBlocks.Leaves && j3 != TFCBlocks.Sapling)
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

		//DataLayer rockLayer1 = ((TFCWorldChunkManager)world.getWorldChunkManager()).getRockLayerAt(xCoord, zCoord, 0);
		//set the block below the tree to dirt.
		//world.setBlockAndMetadata(xCoord, yCoord - 1, zCoord, TFC_Core.getTypeForGrass(rockLayer1.data2), TFC_Core.getSoilMetaFromStone(rockLayer1.block, rockLayer1.data2));
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
							world.isAirBlock(xPos, treeHeightOffset, zPos))
					{
						setBlockAndNotifyAdequately(world, xPos, treeHeightOffset, zPos, TFCBlocks.Leaves, treeId);
					}
				}
			}
		}
		//Here we crate the tree trunk
		for (int l1 = 0; l1 < treeHeight; l1++)
		{
			Block k2 = world.getBlock(xCoord, yCoord + l1, zCoord);
			if (k2 == Blocks.air || k2 == TFCBlocks.Leaves || k2.canBeReplacedByLeaves(world, xCoord, yCoord + l1, zCoord))
				setBlockAndNotifyAdequately(world, xCoord, yCoord + l1, zCoord, TFCBlocks.LogNatural, treeId);
		}
		return true;
	}
}
