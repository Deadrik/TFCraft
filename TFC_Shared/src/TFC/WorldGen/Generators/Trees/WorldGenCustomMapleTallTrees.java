package TFC.WorldGen.Generators.Trees;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import TFC.TFCBlocks;
import TFC.Core.TFC_Core;
import TFC.WorldGen.DataLayer;
import TFC.WorldGen.TFCWorldChunkManager;

public class WorldGenCustomMapleTallTrees extends WorldGenerator
{
	private int treeId;

	public WorldGenCustomMapleTallTrees(boolean flag, int id)
	{
		super(flag);
		treeId=id;
	}
	@Override
	public boolean generate(World world, Random random, int xCoord, int yCoord, int zCoord)
	{
		int l = random.nextInt(3) + 8;
		boolean flag = true;
		if (yCoord < 1 || yCoord + l + 1 > world.getHeight())
		{
			return false;
		}
		for (int i1 = yCoord; i1 <= yCoord + 1 + l; i1++)
		{
			byte byte0 = 1;
			if (i1 == yCoord)
			{
				byte0 = 0;
			}
			if (i1 >= yCoord + 1 + l - 2)
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
						if (j3 != 0 && j3 != TFCBlocks.Leaves.blockID)
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
		if (treeId == 15)
		{
			int x = 0;
		}
		int var3 = world.getBlockId(xCoord, yCoord - 1, zCoord);
		if (!(TFC_Core.isSoil(var3))|| yCoord >= world.getHeight() - l - 1)
		{
			return false;
		}
		DataLayer rockLayer1 = ((TFCWorldChunkManager)world.getWorldChunkManager()).getRockLayerAt(xCoord, zCoord, 0);
		//set the block below the tree to dirt.
		world.setBlock(xCoord, yCoord - 1, zCoord, TFC_Core.getTypeForGrass(rockLayer1.data2), TFC_Core.getSoilMetaFromStone(rockLayer1.data1, rockLayer1.data2), 0x2);
		for (int k1 = yCoord - 3 + l; k1 <= yCoord + l; k1++)
		{
			int j2 = k1 - (yCoord + l);
			int i3 = 1 - j2 / 2;
			for (int k3 = xCoord - i3; k3 <= xCoord + i3; k3++)
			{
				int l3 = k3 - xCoord;
				for (int i4 = zCoord - i3; i4 <= zCoord + i3; i4++)
				{
					int j4 = i4 - zCoord;
					if ((Math.abs(l3) != i3 || Math.abs(j4) != i3 || random.nextInt(2) != 0 && j2 != 0) && world.getBlockId(k3, k1, i4) == 0)
					{
						setBlockAndMetadata(world, k3, k1, i4, TFCBlocks.Leaves.blockID, treeId);
					}
				}
			}
		}

		for (int l1 = 0; l1 < l; l1++)
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
