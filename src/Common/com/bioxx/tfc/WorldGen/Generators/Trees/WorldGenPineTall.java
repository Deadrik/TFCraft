package com.bioxx.tfc.WorldGen.Generators.Trees;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.TFCBlocks;

public class WorldGenPineTall extends WorldGenerator
{
	private final Block blockLeaf, blockWood;
	private final int metaLeaf, metaWood;
	public WorldGenPineTall(int id)
	{
		metaLeaf = id;
		blockLeaf = TFCBlocks.leaves;
		metaWood = id;
		blockWood = TFCBlocks.logNatural;
	}

	@Override
	public boolean generate(World world, Random par2Random, int xCoord, int yCoord, int zCoord)
	{
		int treeHeight = par2Random.nextInt(5) + 7;
		int var7 = treeHeight - par2Random.nextInt(2) - 3;
		int var8 = treeHeight - var7;
		int var9 = 1 + par2Random.nextInt(var8 + 1);
		boolean isValid = true;

		if (yCoord >= 1 && yCoord + treeHeight + 1 <= 256)
		{
			int y;
			int x;
			int z;
			int var15;
			int var18;
			Block block;

			for (y = yCoord; y <= yCoord + 1 + treeHeight && isValid; ++y)
			{
				if (y - yCoord < var7)
					var18 = 0;
				else
					var18 = var9;

				for (x = xCoord - var18; x <= xCoord + var18 && isValid; ++x)
				{
					for (z = zCoord - var18; z <= zCoord + var18 && isValid; ++z)
					{
						if (y >= 0 && y < 256)
						{
							block = world.getBlock(x, y, z);
							if (!block.isAir(world, x, y, z) && !block.isLeaves(world, x, y, z) && !block.isReplaceable(world, x, y, z))
								isValid = false;
						}
						else
						{
							isValid = false;
						}
					}
				}
			}

			if (!isValid)
			{
				return false;
			}
			else
			{
				block = world.getBlock(xCoord, yCoord - 1, zCoord);

				if (TFC_Core.isSoil(block) && yCoord < world.getActualHeight() - treeHeight - 1)
				{
					Block soil = TFC_Core.getTypeForSoil(block);
					int soilMeta = world.getBlockMetadata(xCoord, yCoord - 1, zCoord);

					this.setBlockAndNotifyAdequately(world, xCoord, yCoord - 1, zCoord, soil, soilMeta);
					var18 = 0;

					for (x = yCoord + treeHeight; x >= yCoord + var7; --x)
					{
						for (z = xCoord - var18; z <= xCoord + var18; ++z)
						{
							var15 = z - xCoord;

							for (int var16 = zCoord - var18; var16 <= zCoord + var18; ++var16)
							{
								int var17 = var16 - zCoord;
								block = world.getBlock(z, x, var16);
								if ((Math.abs(var15) != var18 || Math.abs(var17) != var18 || var18 <= 0) && 
										(block == null || block.canBeReplacedByLeaves(world, z, x, var16)))
								{
									this.setBlockAndNotifyAdequately(world, z, x, var16, blockLeaf, metaLeaf);
								}
							}
						}

						if (var18 >= 1 && x == yCoord + var7 + 1)
							--var18;
						else if (var18 < var9)
							++var18;
					}

					for (x = 0; x < treeHeight - 1; ++x)
					{
						this.setBlockAndNotifyAdequately(world, xCoord, yCoord + x, zCoord, blockWood, metaWood);
					}
					return true;
				}
				else
				{
					return false;
				}
			}
		}
		else
		{
			return false;
		}
	}
}
