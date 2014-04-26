package TFC.WorldGen.Generators.Trees;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import TFC.TFCBlocks;
import TFC.Core.TFC_Core;

public class WorldGenCustomTallTrees extends WorldGenerator
{
	private int treeId;

	public WorldGenCustomTallTrees(boolean flag, int id)
	{
		super(flag);
		treeId=id;
	}

	@Override
	public boolean generate(World world, Random random, int xCoord, int yCoord, int zCoord)
	{
		int height = random.nextInt(5) + 6;
		boolean flag = true;
		if (yCoord < 1 || yCoord + height + 1 > world.getHeight())
		{
			return false;
		}
		for (int y = yCoord; y <= yCoord + 1 + height; y++)
		{
			byte byte0 = 1;
			if (y == yCoord)
			{
				byte0 = 0;
			}
			if (y >= yCoord + 1 + height - 2)
			{
				byte0 = 2;
			}
			for (int x = xCoord - byte0; x <= xCoord + byte0 && flag; x++)
			{
				for (int z = zCoord - byte0; z <= zCoord + byte0 && flag; z++)
				{
					if (y >= 0 && y+height < world.getHeight())
					{
						Block j3 = world.getBlock(x, y, z);
						if (j3 != Blocks.air && j3 != TFCBlocks.Leaves)
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
		if (treeId == 15)
		{
			int x = 0; // ???
		}

		Block var8 = world.getBlock(xCoord, yCoord - 1, zCoord);
		if (!(TFC_Core.isSoil(var8)) || yCoord >= world.getHeight() - height - 1)
			return false;

		//DataLayer rockLayer1 = ((TFCWorldChunkManager)world.getWorldChunkManager()).getRockLayerAt(xCoord, zCoord, 0);
		//set the block below the tree to dirt.
		//world.setBlockAndMetadata(xCoord, yCoord - 1, zCoord, TFC_Core.getTypeForDirt(rockLayer1.data2), TFC_Core.getSoilMetaFromStone(rockLayer1.block, rockLayer1.data2));
		for (int y = yCoord - 3 + height; y <= yCoord + height; y++)
		{
			int j2 = y - (yCoord + height);
			int i3 = 1 - j2 / 2;
			for (int x = xCoord - i3; x <= xCoord + i3; x++)
			{
				int l3 = x - xCoord;
				for (int z = zCoord - i3; z <= zCoord + i3; z++)
				{
					int j4 = z - zCoord;
					if ((Math.abs(l3) != i3 || Math.abs(j4) != i3 || random.nextInt(2) != 0 && j2 != 0) && world.isAirBlock(x, y, z))
					{
						setBlockAndNotifyAdequately(world, x, y, z, TFCBlocks.Leaves, treeId);
					}
				}
			}
		}

		for (int l1 = 0; l1 < height; l1++)
		{
			Block k2 = world.getBlock(xCoord, yCoord + l1, zCoord);
			if (k2 == Blocks.air || k2 == TFCBlocks.Leaves || k2 == TFCBlocks.Leaves2 || k2.canBeReplacedByLeaves(world, xCoord, yCoord + l1, zCoord))
				setBlockAndNotifyAdequately(world, xCoord, yCoord + l1, zCoord, TFCBlocks.LogNatural, treeId);
		}

		return true;
	}
}
