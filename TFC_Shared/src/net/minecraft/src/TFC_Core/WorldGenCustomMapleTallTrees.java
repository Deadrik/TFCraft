package net.minecraft.src.TFC_Core;

import java.util.Random;

import net.minecraft.src.*;

public class WorldGenCustomMapleTallTrees extends WorldGenerator
{
	private int treeId;

	public WorldGenCustomMapleTallTrees(boolean flag, int id)
	{
		super(flag);
		treeId=id;
	}
	public boolean generate(World world, Random random, int i, int j, int k)
	{
		int l = random.nextInt(3) + 8;
		boolean flag = true;
		if (j < 1 || j + l + 1 > world.getHeight())
		{
			return false;
		}
		for (int i1 = j; i1 <= j + 1 + l; i1++)
		{
			byte byte0 = 1;
			if (i1 == j)
			{
				byte0 = 0;
			}
			if (i1 >= j + 1 + l - 2)
			{
				byte0 = 2;
			}
			for (int i2 = i - byte0; i2 <= i + byte0 && flag; i2++)
			{
				for (int l2 = k - byte0; l2 <= k + byte0 && flag; l2++)
				{
					if (i1 >= 0 && i1 < world.getHeight())
					{
						int j3 = world.getBlockId(i2, i1, l2);
						if (j3 != 0 && j3 != mod_TFC_Core.terraLeaves.blockID)
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
		int var3 = world.getBlockId(i, j - 1, k);
		if (!(var3 == mod_TFC_Core.terraDirt.blockID || var3 == mod_TFC_Core.terraDirt2.blockID || var3 == mod_TFC_Core.terraGrass.blockID || var3 == mod_TFC_Core.terraGrass2.blockID ||
				var3 == mod_TFC_Core.terraClayGrass.blockID || var3 == mod_TFC_Core.terraClayGrass2.blockID)|| j >= world.getHeight() - l - 1)
		{
			return false;
		}
		world.setBlockAndMetadata(i, j - 1, k, world.getBiomeGenForCoords(i, k).DirtID,world.getBiomeGenForCoords(i, k).SurfaceMeta);
		for (int k1 = j - 3 + l; k1 <= j + l; k1++)
		{
			int j2 = k1 - (j + l);
			int i3 = 1 - j2 / 2;
			for (int k3 = i - i3; k3 <= i + i3; k3++)
			{
				int l3 = k3 - i;
				for (int i4 = k - i3; i4 <= k + i3; i4++)
				{
					int j4 = i4 - k;
					if ((Math.abs(l3) != i3 || Math.abs(j4) != i3 || random.nextInt(2) != 0 && j2 != 0) && !Block.opaqueCubeLookup[world.getBlockId(k3, k1, i4)])
					{
						setBlockAndMetadata(world, k3, k1, i4, mod_TFC_Core.terraLeaves.blockID, treeId);
					}
				}
			}
		}

		for (int l1 = 0; l1 < l; l1++)
		{
			int k2 = world.getBlockId(i, j + l1, k);
			if (k2 == 0 || k2 == mod_TFC_Core.terraLeaves.blockID)
			{
				setBlockAndMetadata(world, i, j + l1, k, mod_TFC_Core.terraWood.blockID, treeId);
			}
		}

		return true;
	}
}
