package TFC.WorldGen.Generators.Trees;

import java.util.Random;

import TFC.*;
import TFC.Core.TFC_Core;
import TFC.WorldGen.DataLayer;
import TFC.WorldGen.TFCBiome;
import TFC.WorldGen.TFCWorldChunkManager;

import net.minecraft.src.Block;
import net.minecraft.src.MathHelper;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenerator;

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

	private void func_48192_a(World par1World, int par2, int par3, int par4, int par5, Random par6Random)
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
							!Block.opaqueCubeLookup[par1World.getBlockId(var11, var8, var13)])
					{
						this.setBlockAndMetadata(par1World, var11, var8, var13, Block.leaves.blockID, this.leavesMetadata);
					}
				}
			}
		}
	}

	public boolean generate(World world, Random par2Random, int xCoord, int yCoord, int zCoord)
	{
		int var6 = par2Random.nextInt(3) + this.field_48195_a;
		boolean var7 = true;

		if (yCoord >= 1 && yCoord + var6 + 1 <= 256)
		{
			int blockUnder;
			int var10;
			int var11;
			int var12;

			for (blockUnder = yCoord; blockUnder <= yCoord + 1 + var6; ++blockUnder)
			{
				byte var9 = 2;

				if (blockUnder == yCoord)
				{
					var9 = 1;
				}

				if (blockUnder >= yCoord + 1 + var6 - 2)
				{
					var9 = 2;
				}

				for (var10 = xCoord - var9; var10 <= xCoord + var9 && var7; ++var10)
				{
					for (var11 = zCoord - var9; var11 <= zCoord + var9 && var7; ++var11)
					{
						if (blockUnder >= 0 && blockUnder < 256)
						{
							var12 = world.getBlockId(var10, blockUnder, var11);

							if (var12 != 0 && var12 != Block.leaves.blockID && var12 != Block.grass.blockID && var12 != Block.dirt.blockID && var12 != Block.wood.blockID && var12 != Block.sapling.blockID && 
									!(TFC_Core.isSoil(var12)))
							{
								var7 = false;
							}
						}
						else
						{
							var7 = false;
						}
					}
				}
			}

			if (!var7)
			{
				return false;
			}
			else
			{
				blockUnder = world.getBlockId(xCoord, yCoord - 1, zCoord);

				if ((TFC_Core.isSoil(blockUnder)) && yCoord < 256 - var6 - 1)
				{
					DataLayer rockLayer1 = ((TFCWorldChunkManager)world.getWorldChunkManager()).getRockLayerAt(xCoord, zCoord, 0);
			        
					world.setBlockAndMetadata(xCoord, yCoord - 1, zCoord, TFC_Core.getTypeForGrass(rockLayer1.data2), TFC_Core.getSoilMetaFromStone(rockLayer1.data1, rockLayer1.data2));
					world.setBlockAndMetadata(xCoord + 1, yCoord - 1, zCoord, TFC_Core.getTypeForGrass(rockLayer1.data2), TFC_Core.getSoilMetaFromStone(rockLayer1.data1, rockLayer1.data2));
					world.setBlockAndMetadata(xCoord, yCoord - 1, zCoord + 1, TFC_Core.getTypeForGrass(rockLayer1.data2), TFC_Core.getSoilMetaFromStone(rockLayer1.data1, rockLayer1.data2));
					world.setBlockAndMetadata(xCoord + 1, yCoord - 1, zCoord + 1, TFC_Core.getTypeForGrass(rockLayer1.data2), TFC_Core.getSoilMetaFromStone(rockLayer1.data1, rockLayer1.data2));
					this.func_48192_a(world, xCoord, zCoord, yCoord + var6, 2, par2Random);

					for (int var14 = yCoord + var6 - 2 - par2Random.nextInt(4); var14 > yCoord + var6 / 2; var14 -= 2 + par2Random.nextInt(4))
					{
						float var15 = par2Random.nextFloat() * (float)Math.PI * 2.0F;
						var11 = xCoord + (int)(0.5F + MathHelper.cos(var15) * 4.0F);
						var12 = zCoord + (int)(0.5F + MathHelper.sin(var15) * 4.0F);
						this.func_48192_a(world, var11, var12, var14, 0, par2Random);

						for (int var13 = 0; var13 < 5; ++var13)
						{
							var11 = xCoord + (int)(1.5F + MathHelper.cos(var15) * (float)var13);
							var12 = zCoord + (int)(1.5F + MathHelper.sin(var15) * (float)var13);
							this.setBlockAndMetadata(world, var11, var14 - 3 + var13 / 2, var12, Block.wood.blockID, this.woodMetadata);
						}
					}

					for (var10 = 0; var10 < var6; ++var10)
					{
						var11 = world.getBlockId(xCoord, yCoord + var10, zCoord);

						if (var11 == 0 || var11 == Block.leaves.blockID)
						{
							this.setBlockAndMetadata(world, xCoord, yCoord + var10, zCoord, Block.wood.blockID, this.woodMetadata);

							if (var10 > 0)
							{
								if (par2Random.nextInt(3) > 0 && world.isAirBlock(xCoord - 1, yCoord + var10, zCoord))
								{
									this.setBlockAndMetadata(world, xCoord - 1, yCoord + var10, zCoord, Block.vine.blockID, 8);
								}

								if (par2Random.nextInt(3) > 0 && world.isAirBlock(xCoord, yCoord + var10, zCoord - 1))
								{
									this.setBlockAndMetadata(world, xCoord, yCoord + var10, zCoord - 1, Block.vine.blockID, 1);
								}
							}
						}

						if (var10 < var6 - 1)
						{
							var11 = world.getBlockId(xCoord + 1, yCoord + var10, zCoord);

							if (var11 == 0 || var11 == Block.leaves.blockID)
							{
								this.setBlockAndMetadata(world, xCoord + 1, yCoord + var10, zCoord, Block.wood.blockID, this.woodMetadata);

								if (var10 > 0)
								{
									if (par2Random.nextInt(3) > 0 && world.isAirBlock(xCoord + 2, yCoord + var10, zCoord))
									{
										this.setBlockAndMetadata(world, xCoord + 2, yCoord + var10, zCoord, Block.vine.blockID, 2);
									}

									if (par2Random.nextInt(3) > 0 && world.isAirBlock(xCoord + 1, yCoord + var10, zCoord - 1))
									{
										this.setBlockAndMetadata(world, xCoord + 1, yCoord + var10, zCoord - 1, Block.vine.blockID, 1);
									}
								}
							}

							var11 = world.getBlockId(xCoord + 1, yCoord + var10, zCoord + 1);

							if (var11 == 0 || var11 == Block.leaves.blockID)
							{
								this.setBlockAndMetadata(world, xCoord + 1, yCoord + var10, zCoord + 1, Block.wood.blockID, this.woodMetadata);

								if (var10 > 0)
								{
									if (par2Random.nextInt(3) > 0 && world.isAirBlock(xCoord + 2, yCoord + var10, zCoord + 1))
									{
										this.setBlockAndMetadata(world, xCoord + 2, yCoord + var10, zCoord + 1, Block.vine.blockID, 2);
									}

									if (par2Random.nextInt(3) > 0 && world.isAirBlock(xCoord + 1, yCoord + var10, zCoord + 2))
									{
										this.setBlockAndMetadata(world, xCoord + 1, yCoord + var10, zCoord + 2, Block.vine.blockID, 4);
									}
								}
							}

							var11 = world.getBlockId(xCoord, yCoord + var10, zCoord + 1);

							if (var11 == 0 || var11 == Block.leaves.blockID)
							{
								this.setBlockAndMetadata(world, xCoord, yCoord + var10, zCoord + 1, Block.wood.blockID, this.woodMetadata);

								if (var10 > 0)
								{
									if (par2Random.nextInt(3) > 0 && world.isAirBlock(xCoord - 1, yCoord + var10, zCoord + 1))
									{
										this.setBlockAndMetadata(world, xCoord - 1, yCoord + var10, zCoord + 1, Block.vine.blockID, 8);
									}

									if (par2Random.nextInt(3) > 0 && world.isAirBlock(xCoord, yCoord + var10, zCoord + 2))
									{
										this.setBlockAndMetadata(world, xCoord, yCoord + var10, zCoord + 2, Block.vine.blockID, 4);
									}
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
