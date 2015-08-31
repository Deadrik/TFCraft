package com.bioxx.tfc.WorldGen.MapGen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCOptions;

public class MapGenRavineTFC extends MapGenBaseTFC
{
	private float[] multipliers = new float[1024];
	//private byte[] metaArray;
	private final int height;
	private final int variability;

	public MapGenRavineTFC(int h, int v)
	{
		height = h;
		variability = v;
	}

	public void generate(IChunkProvider par1IChunkProvider, World par2World, int par3, int par4, Block[] idsBig, byte[] metaBig)
	{
		//metaArray = metaBig;
		super.generate(par1IChunkProvider, par2World, par3, par4, idsBig);
	}

	protected void generateRavine(long seed, int chunkX, int chunkZ, Block[] blockArray, double xCoord, double yCoord, double zCoord, float par12, float par13, float par14, int par15, int par16, double yScale)
	{
		Random var19 = new Random(seed);
		double chunkMidX = chunkX * 16 + 8;
		double chunkMidZ = chunkZ * 16 + 8;
		float var24 = 0.0F;
		float var25 = 0.0F;
		//Block block;

		if (par16 <= 0)
		{
			int var26 = this.range * 16 - 16;
			par16 = var26 - var19.nextInt(var26 / 4);
		}

		boolean var54 = false;
		if (par15 == -1)
		{
			par15 = par16 / 2;
			var54 = true;
		}

		float var27 = 1.0F;
		for (int var28 = 0; var28 < 256; ++var28)
		{
			if (var28 == 0 || var19.nextInt(3) == 0)
				var27 = 1.0F + var19.nextFloat() * var19.nextFloat() * 1.0F;
			this.multipliers[var28] = var27 * var27;
		}

		for (; par15 < par16; ++par15)
		{
			double var53 = 1.5D + MathHelper.sin(par15 * (float)Math.PI / par16) * par12 * 1.0F;
			double var30 = var53 * yScale;
			var53 *= var19.nextFloat() * 0.25D + 0.75D;
			var30 *= var19.nextFloat() * 0.25D + 0.75D;
			float var32 = MathHelper.cos(par14);
			float var33 = MathHelper.sin(par14);
			xCoord += MathHelper.cos(par13) * var32;
			yCoord += var33;
			zCoord += MathHelper.sin(par13) * var32;
			par14 *= 0.7F;
			par14 += var25 * 0.05F;
			par13 += var24 * 0.05F;
			var25 *= 0.8F;
			var24 *= 0.5F;
			var25 += (var19.nextFloat() - var19.nextFloat()) * var19.nextFloat() * 2.0F;
			var24 += (var19.nextFloat() - var19.nextFloat()) * var19.nextFloat() * 4.0F;

			if (var54 || var19.nextInt(4) != 0)
			{
				double var34 = xCoord - chunkMidX;
				double var36 = zCoord - chunkMidZ;
				double var38 = par16 - par15;
				double var40 = par12 + 2.0F + 16.0F;

				if (var34 * var34 + var36 * var36 - var38 * var38 > var40 * var40)
					return;

				if (xCoord >= chunkMidX - 16.0D - var53 * 2.0D && zCoord >= chunkMidZ - 16.0D - var53 * 2.0D && xCoord <= chunkMidX + 16.0D + var53 * 2.0D && zCoord <= chunkMidZ + 16.0D + var53 * 2.0D)
				{
					int xMin = MathHelper.floor_double(xCoord - var53) - chunkX * 16 - 1;
					int xMax = MathHelper.floor_double(xCoord + var53) - chunkX * 16 + 1;
					int minY = MathHelper.floor_double(yCoord - var30) - 1;
					int maxY = MathHelper.floor_double(yCoord + var30) + 1;
					int zMin = MathHelper.floor_double(zCoord - var53) - chunkZ * 16 - 1;
					int zMax = MathHelper.floor_double(zCoord + var53) - chunkZ * 16 + 1;

					if (xMin < 0)
						xMin = 0;

					if (xMax > 16)
						xMax = 16;

					if (minY < 1)
						minY = 1;

					if (maxY > 250)
						maxY = 250;

					if (zMin < 0)
						zMin = 0;

					if (zMax > 16)
						zMax = 16;

					boolean isBlocked = false;
					int x;
					int index;

					for (x = xMin; !isBlocked && x < xMax; ++x)
					{
						for (int z = zMin; !isBlocked && z < zMax; ++z)
						{
							for (int y = maxY + 1; !isBlocked && y >= minY - 1; --y)
							{
								index = (x * 16 + z) * 256 + y;

								if (y >= 0 && y < 256)
								{
									if (blockArray[index] == TFCBlocks.saltWaterStationary ||  blockArray[index] == TFCBlocks.freshWaterStationary)
										isBlocked = true;
									if (y != minY - 1 && x != xMin && x != xMax - 1 && z != zMin && z != zMax - 1)
										y = minY;
								}
							}
						}
					}

					if (!isBlocked)
					{
						for (x = xMin; x < xMax; ++x)
						{
							double var59 = (x + chunkX * 16 + 0.5D - xCoord) / var53;

							for (index = zMin; index < zMax; ++index)
							{
								double var45 = (index + chunkZ * 16 + 0.5D - zCoord) / var53;
								int index2 = (x * 16 + index) * 256 + maxY;

								if (var59 * var59 + var45 * var45 < 1.0D)
								{
									for (int var49 = maxY - 1; var49 >= minY; --var49)
									{
										double var50 = (var49 + 0.5D - yCoord) / var30;
										if ((var59 * var59 + var45 * var45) * this.multipliers[var49] + var50 * var50 / 6.0D < 1.0D)
										{
											if (TFC_Core.isGround(blockArray[index2]))
											{
												if(TFC_Core.isSoilOrGravel(blockArray[index2+1]))
												{
													for(int upCount = 1; TFC_Core.isSoilOrGravel(blockArray[index2+upCount]); upCount++)
													{blockArray[index2+upCount] = Blocks.air;}
												}

												if (var49 < 10)
													blockArray[index2] = Blocks.lava;
												else
													blockArray[index2] = Blocks.air;
											}
										}

										--index2;
									}
								}
							}
						}
						if (var54)
							break;
					}
				}
			}
		}
	}

	/**
	 * Recursively called by generate() (generate) and optionally by itself.
	 */
	@Override
	protected void recursiveGenerate(World par1World, int chunkX, int chunkZ, int par4, int par5, Block[] par6)
	{
		if (TFCOptions.ravineRarity > 0 && this.rand.nextInt(TFCOptions.ravineRarity) == 0)
		{
			double startX = chunkX * 16 + this.rand.nextInt(16);
			double startY = this.rand.nextInt(variability) + height;
			double startZ = chunkZ * 16 + this.rand.nextInt(16);
			byte var13 = 1;
			for (int var14 = 0; var14 < var13; ++var14)
			{
				float angleY = this.rand.nextFloat() * (float)Math.PI * 2.0F;
				float angleZ = (this.rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
				float angleX = (this.rand.nextFloat() * 2.0F + this.rand.nextFloat()) * 2.0F;
				double scaleY = 1.2 + this.rand.nextFloat() + this.rand.nextFloat();
				this.generateRavine(this.rand.nextLong(), par4, par5, par6, startX, startY, startZ, angleX, angleY, angleZ, 0, 0, scaleY);
			}
		}
	}
}
