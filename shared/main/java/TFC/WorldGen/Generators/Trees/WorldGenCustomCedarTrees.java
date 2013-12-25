package TFC.WorldGen.Generators.Trees;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import TFC.TFCBlocks;
import TFC.Core.TFC_Core;

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
		{
			return false;
		}
		for (int i1 = yCoord; i1 <= yCoord + 1 + treeHeight; i1++)
		{
			byte byte0 = 1;
			if (i1 == yCoord)
			{
				byte0 = 0;
			}
			if (i1 >= yCoord + 1 + treeHeight - 2)
			{
				byte0 = 2;
			}
			for (int i2 = xCoord - byte0; i2 <= xCoord + byte0 && flag; i2++)
			{
				for (int l2 = zCoord - byte0; l2 <= zCoord + byte0 && flag; l2++)
				{
					if (i1 >= 0 && i1 < world.getHeight())
					{
						int j3 = world.getBlockId(i2, i1, l2);
						if (j3 != 0 && j3 != TFCBlocks.Leaves.blockID && j3 != TFCBlocks.Sapling.blockID)
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
		{
			return false;
		}
		int var3 = world.getBlockId(xCoord, yCoord - 1, zCoord);
		if (!(TFC_Core.isSoil(var3))|| yCoord >= world.getHeight() - treeHeight - 1)
		{
			return false;
		}
		//DataLayer rockLayer1 = ((TFCWorldChunkManager)world.getWorldChunkManager()).getRockLayerAt(xCoord, zCoord, 0);
		//set the block below the tree to dirt.
		//world.setBlockAndMetadata(xCoord, yCoord - 1, zCoord, TFC_Core.getTypeForGrass(rockLayer1.data2), TFC_Core.getSoilMetaFromStone(rockLayer1.data1, rockLayer1.data2));
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
							world.getBlockId(xPos, treeHeightOffset, zPos) == 0)
					{
						setBlockAndMetadata(world, xPos, treeHeightOffset, zPos, TFCBlocks.Leaves.blockID, treeId);
					}
				}
			}
		}
		//Here we crate the tree trunk
		for (int l1 = 0; l1 < treeHeight; l1++)
		{
			int k2 = world.getBlockId(xCoord, yCoord + l1, zCoord);
			if (k2 == 0 || k2 == TFCBlocks.Leaves.blockID || Block.blocksList[k2].canBeReplacedByLeaves(world, xCoord, yCoord + l1, zCoord))
			{
				setBlockAndMetadata(world, xCoord, yCoord + l1, zCoord, TFCBlocks.Wood.blockID, treeId);
			}
		}

		return true;
	}
}
