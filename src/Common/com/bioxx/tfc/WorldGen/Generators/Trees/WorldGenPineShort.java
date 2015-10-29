package com.bioxx.tfc.WorldGen.Generators.Trees;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.TFCBlocks;

public class WorldGenPineShort extends WorldGenerator
{
	private final Block blockLeaf, blockWood;
	private final int metaLeaf, metaWood;

	public WorldGenPineShort(boolean par1, int id)
	{
		super(par1);
		metaLeaf = id;
		blockLeaf = TFCBlocks.leaves;
		metaWood = id;
		blockWood = TFCBlocks.logNatural;
	}

	@Override
	public boolean generate(World world, Random par2Random, int xCoord, int yCoord, int zCoord)
	{
		int treeHeight = par2Random.nextInt(4) + 6;
		int var7 = 1 + par2Random.nextInt(2);
		int var8 = treeHeight - var7;
		int var9 = 2 + par2Random.nextInt(2);
		boolean isValid = true;

		if (yCoord >= 1 && yCoord + treeHeight + 1 <= 256)
		{
			int var15;
			int var21;
			Block block;

			for (int y = yCoord; y <= yCoord + 1 + treeHeight && isValid; ++y)
			{
				if (y - yCoord < var7)
					var21 = 0;
				else
					var21 = var9;

				for (int x = xCoord - var21; x <= xCoord + var21 && isValid; ++x)
				{
					for (int z = zCoord - var21; z <= zCoord + var21 && isValid; ++z)
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
					var21 = par2Random.nextInt(2);
					int i = 1;
					byte var22 = 0;
					int x;
					int y;

					for (var15 = 0; var15 <= var8; ++var15)
					{
						y = yCoord + treeHeight - var15;
						for (x = xCoord - var21; x <= xCoord + var21; ++x)
						{
							int var18 = x - xCoord;
							for (int z = zCoord - var21; z <= zCoord + var21; ++z)
							{
								int var20 = z - zCoord;
								block = world.getBlock(x, y, z);
								if ((Math.abs(var18) != var21 || Math.abs(var20) != var21 || var21 <= 0) && 
										(block == null || block.canBeReplacedByLeaves(world, x, y, z)))
								{
									this.setBlockAndNotifyAdequately(world, x, y, z, blockLeaf, metaLeaf);
								}
							}
						}

						if (var21 >= i)
						{
							var21 = var22;
							var22 = 1;
							++i;
							if (i > var9)
								i = var9;
						}
						else
						{
							++var21;
						}
					}

					for (y = 0; y < treeHeight - 1; ++y)
					{
						this.setBlockAndNotifyAdequately(world, xCoord, yCoord + y, zCoord, blockWood, metaWood);
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
