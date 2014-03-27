package TFC.WorldGen.Generators.Trees;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import TFC.TFCBlocks;
import TFC.Core.TFC_Core;
import TFC.WorldGen.TFCBiome;

public class WorldGenPineTall extends WorldGenerator
{
	Block blockLeaf, blockWood;
	final int metaLeaf, metaWood;
	public WorldGenPineTall(int id)
	{
		metaLeaf = id;
		blockLeaf = TFCBlocks.Leaves;
		metaWood = id;
		blockWood = TFCBlocks.Wood;
	}

	public boolean generate(World world, Random par2Random, int i, int j, int k)
	{
		int var6 = par2Random.nextInt(5) + 7;
		int var7 = var6 - par2Random.nextInt(2) - 3;
		int var8 = var6 - var7;
		int var9 = 1 + par2Random.nextInt(var8 + 1);
		boolean var10 = true;

		if (j >= 1 && j + var6 + 1 <= 128)
		{
			int var11;
			int var13;
			int var14;
			int var15;
			int var18;
			Block block;

			for (var11 = j; var11 <= j + 1 + var6 && var10; ++var11)
			{
				boolean var12 = true;

				if (var11 - j < var7)
				{
					var18 = 0;
				}
				else
				{
					var18 = var9;
				}

				for (var13 = i - var18; var13 <= i + var18 && var10; ++var13)
				{
					for (var14 = k - var18; var14 <= k + var18 && var10; ++var14)
					{
						if (var11 >= 0 && var11 < 128)
						{
							block = world.getBlock(var13, var11, var14);
							if (block != Blocks.air && (block == null || !block.isLeaves(world, var13, var11, var14)))
							{
								var10 = false;
							}
						}
						else
						{
							var10 = false;
						}
					}
				}
			}

			if (!var10)
			{
				return false;
			}
			else
			{
				block = world.getBlock(i, j - 1, k);
				int meta = TFCBiome.getSurfaceRockLayer(world, i, k);
				Block dirt = TFC_Core.getTypeForDirt(meta);
				int dirtMeta = TFC_Core.getSoilMetaFromStone(dirt, meta);

				if (TFC_Core.isSoil(block) && j < world.getActualHeight() - var6 - 1)
				{
					this.setBlockAndNotifyAdequately(world, i, j - 1, k, dirt, dirtMeta);
					var18 = 0;

					for (var13 = j + var6; var13 >= j + var7; --var13)
					{
						for (var14 = i - var18; var14 <= i + var18; ++var14)
						{
							var15 = var14 - i;

							for (int var16 = k - var18; var16 <= k + var18; ++var16)
							{
								int var17 = var16 - k;

								block = world.getBlock(var14, var13, var16);
								if ((Math.abs(var15) != var18 || Math.abs(var17) != var18 || var18 <= 0) && 
										(block == null || block.canBeReplacedByLeaves(world, var14, var13, var16)))
								{
									this.setBlockAndNotifyAdequately(world, var14, var13, var16, blockLeaf, metaLeaf);
								}
							}
						}

						if (var18 >= 1 && var13 == j + var7 + 1)
						{
							--var18;
						}
						else if (var18 < var9)
						{
							++var18;
						}
					}

					for (var13 = 0; var13 < var6 - 1; ++var13)
					{
						block = world.getBlock(i, j + var13, k);
						if (block == Blocks.air || block == null || block.isLeaves(world, i, j + var13, k) || 
								block.canBeReplacedByLeaves(world, i, j + var13, k))
						{
							this.setBlockAndNotifyAdequately(world, i, j + var13, k, blockWood, metaWood);
						}
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
