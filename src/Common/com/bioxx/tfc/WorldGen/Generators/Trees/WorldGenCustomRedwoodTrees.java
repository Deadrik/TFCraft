package com.bioxx.tfc.WorldGen.Generators.Trees;

import java.util.Random;

import com.bioxx.tfc.TFCBlocks;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenCustomRedwoodTrees extends WorldGenerator
{
	private int treeId;

	public WorldGenCustomRedwoodTrees (boolean flag, int id)
	{
		super (flag);
		treeId = id;
	}


	public boolean generate (World world, Random random, int xCoord, int yCoord, int zCoord)
	{
		int treeHeight = random.nextInt (7) + 20;
		int w = treeHeight / 24;
		int treeType = random.nextInt (2);
		boolean flag = true;
		if (yCoord < 1 || yCoord + treeHeight + 4 > world.getHeight())
			return false;

		for (int i1 = yCoord ; i1 <= yCoord + 4 + treeHeight ; i1++)
		{
			byte byte0 = 1;
			if (i1 == yCoord)
				byte0 = 0;
			if (i1 >= yCoord + 4 + treeHeight - 2)
				byte0 = 2;

			for (int l1 = xCoord - byte0 ; l1 <= xCoord + byte0 && flag ; l1++)
			{
				for (int k2 = zCoord - byte0 ; k2 <= zCoord + byte0 && flag ; k2++)
				{
					if (i1 >= 0 && i1 < world.getHeight())
					{
						Block j3 = world.getBlock (l1, i1, k2);
						if (j3 != Blocks.air && (j3 != TFCBlocks.Leaves || j3 != TFCBlocks.Leaves2))
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

		Block var3 = world.getBlock (xCoord, yCoord - 1, zCoord);
		if (!(var3 == TFCBlocks.Dirt || var3 == TFCBlocks.Dirt2 || var3 == TFCBlocks.Grass || var3 == TFCBlocks.Grass2 ||
				var3 == TFCBlocks.ClayGrass || var3 == TFCBlocks.ClayGrass2)|| yCoord >= world.getHeight() - treeHeight - 1)
		{
			return false;
		}

		if (treeType == 1)
		{
			int covTrunk = 16;
			int lay3 = 6;
			int lay4 = 5;
			int lay5 = 4;
			int lay6 = 3;
			for (int yPos = yCoord + treeHeight - covTrunk ; yPos <= yCoord + treeHeight + 4 ; yPos++)
			{
				int leafLayer = yPos - (yCoord + treeHeight - covTrunk);
				int width = leafLayer + 2;
				if (leafLayer <= lay3 + 1 && leafLayer > 1)
					width = 4;
				else if (leafLayer <= lay3 + 1 + lay4 && leafLayer > lay3 + 1)
					width = 3;
				else if (leafLayer <= lay3 + 1 + lay4 + lay5 && leafLayer > lay3 + 1 + lay4)
					width = 2;
				else if (leafLayer <= lay3 + 1 + lay4 + lay5 + lay6 && leafLayer > lay3 + 1 + lay4 + lay5)
					width = 1;
				else if (leafLayer > lay3 + 1 + lay4 + lay5 + lay6)
					width = 0;

				for (int xPos = xCoord - width ; xPos <= xCoord + width + w ; xPos++)
				{
					int l3 = xPos - xCoord;
					for (int zPos = zCoord - width ; zPos <= zCoord + width + w ; zPos++)
					{
						int j4 = zPos - zCoord;
						if ((Math.abs(l3) != width || Math.abs(j4) != width ||random.nextInt (2) != 0) && 
								!world.getBlock (xPos, yPos, zPos).isOpaqueCube() &&
								world.getBlock (xPos, yPos, zPos) != TFCBlocks.LogNatural)
						{
							setBlockAndNotifyAdequately(world, xPos, yPos, zPos, TFCBlocks.Leaves, treeId);
						}
					}
				}
			}
		}
		else if (treeType != 1)
		{
			int covTrunk = treeHeight * 2 / 3;
			covTrunk = 16;
			int lay2 = 3;
			int lay3 = 6;
			int lay4 = 4;
			int lay5 = 4;
			for (int yPos = yCoord + covTrunk ; yPos <= yCoord + treeHeight + 4 ; yPos++)
			{
				byte byte1 = 2;
				int leafLayer = yPos - (yCoord + treeHeight - covTrunk);
				int width = leafLayer + 1;
				if (leafLayer <= lay2 && leafLayer > 0)
					width = 2;
				else if (leafLayer <= lay2 +  lay3 && leafLayer > lay2)
					width = 3;
				else if (leafLayer <= lay2 +  lay4 + lay3 && leafLayer > lay3 +  lay2)
					width = 2;
				else if (leafLayer <= lay3 +  lay4 + lay5 + lay2 && leafLayer > lay3 +  lay4 + lay2)
					width = 1;
				else if (leafLayer > lay3 +  lay4 + lay5 + lay2)
					width = 0;

				for (int xPos = xCoord - width ; xPos <= xCoord + width + w ; xPos++)
				{
					int l3 = xPos - xCoord;
					for (int zPos = zCoord - width ; zPos <= zCoord + width + w ; zPos++)
					{
						int j4 = zPos - zCoord;
						if ((Math.abs(l3) != width || Math.abs(j4) != width ||random.nextInt (2) != 0) && 
								!world.getBlock(xPos, yPos, zPos).isOpaqueCube() &&
								world.getBlock(xPos, yPos, zPos) != TFCBlocks.LogNatural)
						{
							setBlockAndNotifyAdequately(world, xPos, yPos, zPos, TFCBlocks.Leaves, treeId);
						}
					}
				}
			}
		}
		//Create the tree Trunk
		for (int j2 = 0 ; j2 < treeHeight ; j2++)
		{
			Block i3 = world.getBlock(xCoord, yCoord + j2, zCoord);
			if (i3 == Blocks.air || i3 == TFCBlocks.Leaves || i3 == TFCBlocks.Leaves2)
			{
				for (int x = xCoord ; x <= xCoord + w ; x++)
				{
					for (int z = zCoord ; z <= zCoord + w ; z++)
					{
						setBlockAndNotifyAdequately(world, x, yCoord + j2, z, TFCBlocks.LogNatural, treeId);
					}
				}
			}
		}
//		//Create the branches
//		for (int j2 = 0 ; j2 < treeHeight ; j2+=2)
//		{
//			int i3 = world.getBlockId (xCoord, yCoord + j2, zCoord);
//			if (i3 == Block.wood)
//			{
//				for (int x = xCoord ; x <= xCoord; x++)
//				{
//					for (int z = zCoord ; z <= zCoord; z++)
//					{
//						if(random.nextInt(4) == 0) {
//							TFC_Core.CreateTreeLimb(world, x, yCoord + j2, z, treeId, TFC_Core.Direction.PosX, random);
//						}
//						if(random.nextInt(4) == 0) {
//							TFC_Core.CreateTreeLimb(world, x, yCoord + j2, z, treeId, TFC_Core.Direction.NegX, random);
//						}
//						if(random.nextInt(4) == 0) {
//							TFC_Core.CreateTreeLimb(world, x, yCoord + j2, z, treeId, TFC_Core.Direction.PosZ, random);
//						}
//						if(random.nextInt(4) == 0) {
//							TFC_Core.CreateTreeLimb(world, x, yCoord + j2, z, treeId, TFC_Core.Direction.NegZ, random);
//						}
//					}
//				}
//			}
//		}
		return true;
	}
}