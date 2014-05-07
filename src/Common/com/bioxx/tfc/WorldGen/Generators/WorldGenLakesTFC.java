package com.bioxx.tfc.WorldGen.Generators;

import java.util.Random;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.WorldGen.DataLayer;
import com.bioxx.tfc.WorldGen.TFCBiome;
import com.bioxx.tfc.WorldGen.TFCWorldChunkManager;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenLakesTFC extends WorldGenerator
{
	private Block liquidBlock;

	public WorldGenLakesTFC(Block par1)
	{
		this.liquidBlock = par1;
	}

	public boolean generate(World world, Random random, int xCoord, int yCoord, int zCoord)
	{
		xCoord -= 8;
		for (zCoord -= 8; yCoord > 5 && world.isAirBlock(xCoord, yCoord, zCoord); --yCoord)
		{
			;
		}

		if (yCoord <= 4)
		{
			return false;
		}
		else
		{
			yCoord -= 4;
			boolean[] var6 = new boolean[2048];
			int var7 = random.nextInt(4) + 4;
			int i2;

			for (i2 = 0; i2 < var7; ++i2)
			{
				double var9 = random.nextDouble() * 6.0D + 3.0D;
				double var11 = random.nextDouble() * 4.0D + 2.0D;
				double var13 = random.nextDouble() * 6.0D + 3.0D;
				double var15 = random.nextDouble() * (16.0D - var9 - 2.0D) + 1.0D + var9 / 2.0D;
				double var17 = random.nextDouble() * (8.0D - var11 - 4.0D) + 2.0D + var11 / 2.0D;
				double var19 = random.nextDouble() * (16.0D - var13 - 2.0D) + 1.0D + var13 / 2.0D;

				for (int var21 = 1; var21 < 15; ++var21)
				{
					for (int var22 = 1; var22 < 15; ++var22)
					{
						for (int var23 = 1; var23 < 7; ++var23)
						{
							double var24 = ((double)var21 - var15) / (var9 / 2.0D);
							double var26 = ((double)var23 - var17) / (var11 / 2.0D);
							double var28 = ((double)var22 - var19) / (var13 / 2.0D);
							double var30 = var24 * var24 + var26 * var26 + var28 * var28;
							if (var30 < 1.0D)
								var6[(var21 * 16 + var22) * 8 + var23] = true;
						}
					}
				}
			}

			int k4;
			int j3;
			boolean var33;
			for (i2 = 0; i2 < 16; ++i2)
			{
				for (j3 = 0; j3 < 16; ++j3)
				{
					for (k4 = 0; k4 < 8; ++k4)
					{
						var33 = !var6[(i2 * 16 + j3) * 8 + k4] && (
								i2 < 15 &&
								var6[((i2 + 1) * 16 + j3) * 8 + k4] ||
								i2 > 0 &&
								var6[((i2 - 1) * 16 + j3) * 8 + k4] ||
								j3 < 15 &&
								var6[(i2 * 16 + j3 + 1) * 8 + k4] ||
								j3 > 0 &&
								var6[(i2 * 16 + j3 - 1) * 8 + k4] ||
								k4 < 7 &&
								var6[(i2 * 16 + j3) * 8 + k4 + 1] ||
								k4 > 0 &&
								var6[(i2 * 16 + j3) * 8 + k4 - 1]);
						if (var33)
						{
							Material var12 = world.getBlock(xCoord + i2, yCoord + k4, zCoord + j3).getMaterial();
							if (k4 >= 4 && var12.isLiquid())
								return false;
							if (k4 < 4 && !var12.isSolid() && world.getBlock(xCoord + i2, yCoord + k4, zCoord + j3) != this.liquidBlock)
								return false;
						}
					}
				}
			}

			for (i2 = 0; i2 < 16; ++i2)
			{
				for (j3 = 0; j3 < 16; ++j3)
				{
					for (k4 = 0; k4 < 8; ++k4)
					{
						if (var6[(i2 * 16 + j3) * 8 + k4])
							world.setBlock(xCoord + i2, yCoord + k4, zCoord + j3, k4 >= 4 ? Blocks.air : this.liquidBlock);
					}
				}
			}

			for (i2 = 0; i2 < 16; ++i2)
			{
				for (j3 = 0; j3 < 16; ++j3)
				{
					for (k4 = 4; k4 < 8; ++k4)
					{
						Block b = world.getBlock(xCoord + i2, yCoord + k4 - 1, zCoord + j3);
						if (var6[(i2 * 16 + j3) * 8 + k4] && (b == Blocks.dirt || b == TFCBlocks.Dirt ||
								b == TFCBlocks.Dirt2 || b == TFCBlocks.Grass || b == TFCBlocks.Grass2 || 
								b == TFCBlocks.ClayGrass || b == TFCBlocks.ClayGrass2 || 
								b == TFCBlocks.Clay || b == TFCBlocks.Clay2 || 
								b == TFCBlocks.Peat || b == TFCBlocks.PeatGrass) && 
								world.getSavedLightValue(EnumSkyBlock.Sky, xCoord + i2, yCoord + k4, zCoord + j3) > 0)
						{
							TFCBiome var35 = (TFCBiome) world.getBiomeGenForCoords(xCoord + i2, zCoord + j3);
							if (var35.topBlock == Blocks.mycelium)
							{
								world.setBlock(xCoord + i2, yCoord + k4 - 1, zCoord + j3, Blocks.mycelium);
							}
							else
							{
								DataLayer rockLayer1 = ((TFCWorldChunkManager)world.getWorldChunkManager()).getRockLayerAt(xCoord + i2, zCoord + j3, 0);
								world.setBlock(xCoord + i2, yCoord + k4 - 1, zCoord + j3, TFC_Core.getTypeForGrass(rockLayer1.data2));
							}
						}
					}
				}
			}

			if (this.liquidBlock.getMaterial() == Material.lava)
			{
				int meta = random.nextInt(4);
				for (i2 = 0; i2 < 16; ++i2)
				{
					for (j3 = 0; j3 < 16; ++j3)
					{
						for (k4 = 0; k4 < 8; ++k4)
						{
							var33 = !var6[(i2 * 16 + j3) * 8 + k4] && (i2 < 15 && var6[((i2 + 1) * 16 + j3) * 8 + k4] || i2 > 0 && var6[((i2 - 1) * 16 + j3) * 8 + k4] || j3 < 15 && var6[(i2 * 16 + j3 + 1) * 8 + k4] || j3 > 0 && var6[(i2 * 16 + j3 - 1) * 8 + k4] || k4 < 7 && var6[(i2 * 16 + j3) * 8 + k4 + 1] || k4 > 0 && var6[(i2 * 16 + j3) * 8 + k4 - 1]);
							if (var33 && (k4 < 4 || random.nextInt(2) != 0) && world.getBlock(xCoord + i2, yCoord + k4, zCoord + j3).getMaterial().isSolid())
							{
								world.setBlock(xCoord + i2, yCoord + k4, zCoord + j3, TFCBlocks.StoneIgEx, meta, 3);
								if(world.isAirBlock(xCoord + i2 + 1, yCoord + k4, zCoord + j3))
									world.setBlock(xCoord + i2 + 1, yCoord + k4, zCoord + j3, TFCBlocks.Sulfur,new Random().nextInt(4), 0x2);

								if(world.isAirBlock(xCoord + i2 - 1, yCoord + k4, zCoord + j3))
									world.setBlock(xCoord + i2 - 1, yCoord + k4, zCoord + j3, TFCBlocks.Sulfur,new Random().nextInt(4), 0x2);

								if(world.isAirBlock(xCoord + i2, yCoord + k4 + 1, zCoord + j3))
									world.setBlock(xCoord + i2, yCoord + k4 + 1, zCoord + j3, TFCBlocks.Sulfur,new Random().nextInt(4), 0x2);

								if(world.isAirBlock(xCoord + i2, yCoord + k4 - 1, zCoord + j3))
									world.setBlock(xCoord + i2, yCoord + k4 - 1, zCoord + j3, TFCBlocks.Sulfur,new Random().nextInt(4), 0x2);

								if(world.isAirBlock(xCoord + i2, yCoord + k4, zCoord + j3 + 1))
									world.setBlock(xCoord + i2, yCoord + k4, zCoord + j3 + 1, TFCBlocks.Sulfur,new Random().nextInt(4), 0x2);

								if(world.isAirBlock(xCoord + i2, yCoord + k4, zCoord + j3 - 1))
									world.setBlock(xCoord + i2, yCoord + k4, zCoord + j3 - 1, TFCBlocks.Sulfur,new Random().nextInt(4), 0x2);
							}
						}
					}
				}
			}

			if (this.liquidBlock.getMaterial() == Material.water)
			{
				for (i2 = 0; i2 < 16; ++i2)
				{
					for (j3 = 0; j3 < 16; ++j3)
					{
						byte var34 = 4;
						if (world.isBlockFreezable(xCoord + i2, yCoord + var34, zCoord + j3))
							world.setBlock(xCoord + i2, yCoord + var34, zCoord + j3, Blocks.ice, 0, 0x2);
					}
				}
			}
			return true;
		}
	}

}
