package com.bioxx.tfc.WorldGen.Generators.Trees;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Core;

public class WorldGenPineShort extends WorldGenerator
{
	Block blockLeaf, blockWood;
	final int metaLeaf, metaWood;

	public WorldGenPineShort(boolean par1, int id)
	{
		super(par1);
		metaLeaf = id;
		blockLeaf = TFCBlocks.Leaves;
		metaWood = id;
		blockWood = TFCBlocks.LogNatural;
	}

	@Override
	public boolean generate(World world, Random par2Random, int x, int y, int z)
	{
		int var6 = par2Random.nextInt(4) + 6;
		int var7 = 1 + par2Random.nextInt(2);
		int var8 = var6 - var7;
		int var9 = 2 + par2Random.nextInt(2);
		boolean var10 = true;

		if (y >= 1 && y + var6 + 1 <= 256)
		{
			int var11;
			int var13;
			int var15;
			int var21;
			Block block;

			for (var11 = y; var11 <= y + 1 + var6 && var10; ++var11)
			{
				boolean var12 = true;

				if (var11 - y < var7)
					var21 = 0;
				else
					var21 = var9;

				for (var13 = x - var21; var13 <= x + var21 && var10; ++var13)
				{
					for (int var14 = z - var21; var14 <= z + var21 && var10; ++var14)
					{
						if (var11 >= 0 && var11 < 256)
						{
							block = world.getBlock(var13, var11, var14);
							if (block != Blocks.air && block != null && !block.isLeaves(world, var13, var11, var14))
								var10 = false;
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
				block = world.getBlock(x, y - 1, z);
				int meta = TFC_Climate.getRockLayer(x, y, z, 0).meta;
				Block dirt =  TFC_Core.getTypeForDirt(meta);
				int dirtMeta =  TFC_Core.getSoilMetaFromStone(dirt, meta);

				if (TFC_Core.isSoil(block) && y < world.getActualHeight() - var6 - 1)
				{
					this.setBlockAndNotifyAdequately(world, x, y - 1, z, dirt, dirtMeta);
					var21 = par2Random.nextInt(2);
					var13 = 1;
					byte var22 = 0;
					int var17;
					int var16;

					for (var15 = 0; var15 <= var8; ++var15)
					{
						var16 = y + var6 - var15;
						for (var17 = x - var21; var17 <= x + var21; ++var17)
						{
							int var18 = var17 - x;
							for (int var19 = z - var21; var19 <= z + var21; ++var19)
							{
								int var20 = var19 - z;
								block = world.getBlock(var17, var16, var19);
								if ((Math.abs(var18) != var21 || Math.abs(var20) != var21 || var21 <= 0) && 
										(block == null || block.canBeReplacedByLeaves(world, var17, var16, var19)))
								{
									this.setBlockAndNotifyAdequately(world, var17, var16, var19, blockLeaf, metaLeaf);
								}
							}
						}

						if (var21 >= var13)
						{
							var21 = var22;
							var22 = 1;
							++var13;
							if (var13 > var9)
								var13 = var9;
						}
						else
						{
							++var21;
						}
					}

					var15 = par2Random.nextInt(3);
					for (var16 = 0; var16 < var6 - var15; ++var16)
					{
						block = world.getBlock(x, y + var16, z);
						if (block == Blocks.air || block == null || block.isLeaves(world, x, y + var16, z) || 
								block.canBeReplacedByLeaves(world, x, y + var16, z))
						{
							this.setBlockAndNotifyAdequately(world, x, y + var16, z, blockWood, metaWood);
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
