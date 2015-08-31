package com.bioxx.tfc.WorldGen.Generators.Trees;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.WorldGen.DataLayer;
import com.bioxx.tfc.api.TFCBlocks;

public class WorldGenCustomHugeTrees extends WorldGenerator
{
	private final int field_48195_a;

	/** Sets the metadata for the wood blocks used */
	private final int woodMetadata;

	/** Sets the metadata for the leaves used in huge trees */
	private final int leavesMetadata;

	public WorldGenCustomHugeTrees(boolean par1, int par2, int par3, int par4)
	{
		super(par1);
		this.field_48195_a = par2;
		this.woodMetadata = par3;
		this.leavesMetadata = par4;
	}

	private void genLeaves(World par1World, int par2, int par3, int par4, int par5, Random par6Random)
	{
		byte var7 = 2;
		for (int var8 = par4 - var7; var8 <= par4; ++var8)
		{
			int var9 = var8 - par4;
			int var10 = par5 + 1 - var9;
			for (int var11 = par2 - var10; var11 <= par2 + var10 + 1; ++var11)
			{
				int var12 = var11 - par2;
				for (int var13 = par3 - var10; var13 <= par3 + var10 + 1; ++var13)
				{
					int var14 = var13 - par3;
					if ((var12 >= 0 || var14 >= 0 || var12 * var12 + var14 * var14 <= var10 * var10) && 
							(var12 <= 0 && var14 <= 0 || var12 * var12 + var14 * var14 <= (var10 + 1) * (var10 + 1)) && 
							(par6Random.nextInt(4) != 0 || var12 * var12 + var14 * var14 <= (var10 - 1) * (var10 - 1)) && 
							!par1World.getBlock(var11, var8, var13).isOpaqueCube())
					{
						this.setBlockAndNotifyAdequately(par1World, var11, var8, var13, TFCBlocks.leaves, this.leavesMetadata);
					}
				}
			}
		}
	}

	@Override
	public boolean generate(World world, Random rand, int xCoord, int yCoord, int zCoord)
	{
		int var6 = rand.nextInt(3) + this.field_48195_a;
		boolean canGenHere = true;

		if (yCoord >= 1 && yCoord + var6 + 1 <= 256)
		{
			int blockUnder;
			int tempX;
			int tempZ;
			int var12;
			for (blockUnder = yCoord; blockUnder <= yCoord + 1 + var6; ++blockUnder)
			{
				byte var9 = 2;
				if (blockUnder == yCoord)
					var9 = 1;
				if (blockUnder >= yCoord + 1 + var6 - 2)
					var9 = 2;

				for (tempX = xCoord - var9; tempX <= xCoord + var9 && canGenHere; ++tempX)
				{
					for (tempZ = zCoord - var9; tempZ <= zCoord + var9 && canGenHere; ++tempZ)
					{
						if (blockUnder >= 0 && blockUnder < 256)
						{
							Block block = world.getBlock(tempX, blockUnder, tempZ);
							if (!block.isAir(world, tempX, blockUnder, tempZ) &&
									(block != TFCBlocks.leaves || block != TFCBlocks.leaves2) &&
									(block != TFCBlocks.grass || block != TFCBlocks.grass2) &&
									(block != TFCBlocks.dirt || block != TFCBlocks.dirt2) &&
									(block != TFCBlocks.logNatural || block != TFCBlocks.logNatural2) &&
									(block != TFCBlocks.sapling || block != TFCBlocks.sapling2) &&
									!(TFC_Core.isSoil(block)))
							{
								canGenHere = false;
							}
						}
						else
						{
							canGenHere = false;
						}
					}
				}
			}

			if (!canGenHere)
			{
				return false;
			}
			else
			{
				if (TFC_Core.isSoil(world.getBlock(xCoord, yCoord - 1, zCoord)) && yCoord < 256 - var6 - 1)
				{
					DataLayer rockLayer1 = TFC_Climate.getCacheManager(world).getRockLayerAt(xCoord, zCoord, 0);
					world.setBlock(xCoord, yCoord - 1, zCoord, TFC_Core.getTypeForDirt(rockLayer1.data2), TFC_Core.getSoilMetaFromStone(rockLayer1.block, rockLayer1.data2), 0x2);
					world.setBlock(xCoord + 1, yCoord - 1, zCoord, TFC_Core.getTypeForDirt(rockLayer1.data2), TFC_Core.getSoilMetaFromStone(rockLayer1.block, rockLayer1.data2), 0x2);
					world.setBlock(xCoord, yCoord - 1, zCoord + 1, TFC_Core.getTypeForDirt(rockLayer1.data2), TFC_Core.getSoilMetaFromStone(rockLayer1.block, rockLayer1.data2), 0x2);
					world.setBlock(xCoord + 1, yCoord - 1, zCoord + 1, TFC_Core.getTypeForDirt(rockLayer1.data2), TFC_Core.getSoilMetaFromStone(rockLayer1.block, rockLayer1.data2), 0x2);
					this.genLeaves(world, xCoord, zCoord, yCoord + var6, 2, rand);

					for (int var14 = yCoord + var6 - 2 - rand.nextInt(4); var14 > yCoord + var6 / 2; var14 -= 2 + rand.nextInt(4))
					{
						float var15 = rand.nextFloat() * (float)Math.PI * 2.0F;
						tempZ = xCoord + (int)(0.5F + MathHelper.cos(var15) * 4.0F);
						var12 = zCoord + (int)(0.5F + MathHelper.sin(var15) * 4.0F);
						this.genLeaves(world, tempZ, var12, var14, 0, rand);

						for (int var13 = 0; var13 < 5; ++var13)
						{
							tempZ = xCoord + (int) (1.5F + MathHelper.cos(var15) * var13);
							var12 = zCoord + (int) (1.5F + MathHelper.sin(var15) * var13);
							this.setBlockAndNotifyAdequately(world, tempZ, var14 - 3 + var13 / 2, var12, TFCBlocks.logNatural, this.woodMetadata);
						}
					}

					for (tempX = 0; tempX < var6; ++tempX)
					{
						Block id = world.getBlock(xCoord, yCoord + tempX, zCoord);
						if (id.isAir(world, xCoord, yCoord + tempX, zCoord) || id == TFCBlocks.leaves || id == TFCBlocks.leaves2)
						{
							this.setBlockAndNotifyAdequately(world, xCoord, yCoord + tempX, zCoord, TFCBlocks.logNatural, this.woodMetadata);
							if (tempX > 0)
							{
								if (rand.nextInt(3) > 0 && world.isAirBlock(xCoord - 1, yCoord + tempX, zCoord))
									this.setBlockAndNotifyAdequately(world, xCoord - 1, yCoord + tempX, zCoord, TFCBlocks.vine, 8);
								if (rand.nextInt(3) > 0 && world.isAirBlock(xCoord, yCoord + tempX, zCoord - 1))
									this.setBlockAndNotifyAdequately(world, xCoord, yCoord + tempX, zCoord - 1, TFCBlocks.vine, 1);
							}
						}

						if (tempX < var6 - 1)
						{
							id = world.getBlock(xCoord + 1, yCoord + tempX, zCoord);
							if (id.isAir(world, xCoord, yCoord + tempX, zCoord) || id == TFCBlocks.leaves || id == TFCBlocks.leaves2)
							{
								this.setBlockAndNotifyAdequately(world, xCoord + 1, yCoord + tempX, zCoord, TFCBlocks.logNatural, this.woodMetadata);
								if (tempX > 0)
								{
									if (rand.nextInt(3) > 0 && world.isAirBlock(xCoord + 2, yCoord + tempX, zCoord))
										this.setBlockAndNotifyAdequately(world, xCoord + 2, yCoord + tempX, zCoord, TFCBlocks.vine, 2);
									if (rand.nextInt(3) > 0 && world.isAirBlock(xCoord + 1, yCoord + tempX, zCoord - 1))
										this.setBlockAndNotifyAdequately(world, xCoord + 1, yCoord + tempX, zCoord - 1, TFCBlocks.vine, 1);
								}
							}

							id = world.getBlock(xCoord + 1, yCoord + tempX, zCoord + 1);
							if (id.isAir(world, xCoord, yCoord + tempX, zCoord) || id == TFCBlocks.leaves || id == TFCBlocks.leaves2)
							{
								this.setBlockAndNotifyAdequately(world, xCoord + 1, yCoord + tempX, zCoord + 1, TFCBlocks.logNatural, this.woodMetadata);
								if (tempX > 0)
								{
									if (rand.nextInt(3) > 0 && world.isAirBlock(xCoord + 2, yCoord + tempX, zCoord + 1))
										this.setBlockAndNotifyAdequately(world, xCoord + 2, yCoord + tempX, zCoord + 1, TFCBlocks.vine, 2);
									if (rand.nextInt(3) > 0 && world.isAirBlock(xCoord + 1, yCoord + tempX, zCoord + 2))
										this.setBlockAndNotifyAdequately(world, xCoord + 1, yCoord + tempX, zCoord + 2, TFCBlocks.vine, 4);
								}
							}

							id = world.getBlock(xCoord, yCoord + tempX, zCoord + 1);
							if (id.isAir(world, xCoord, yCoord + tempX, zCoord) || id == TFCBlocks.leaves || id == TFCBlocks.leaves2)
							{
								this.setBlockAndNotifyAdequately(world, xCoord, yCoord + tempX, zCoord + 1, TFCBlocks.logNatural, this.woodMetadata);
								if (tempX > 0)
								{
									if (rand.nextInt(3) > 0 && world.isAirBlock(xCoord - 1, yCoord + tempX, zCoord + 1))
										this.setBlockAndNotifyAdequately(world, xCoord - 1, yCoord + tempX, zCoord + 1, TFCBlocks.vine, 8);
									if (rand.nextInt(3) > 0 && world.isAirBlock(xCoord, yCoord + tempX, zCoord + 2))
										this.setBlockAndNotifyAdequately(world, xCoord, yCoord + tempX, zCoord + 2, TFCBlocks.vine, 4);
								}
							}
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
