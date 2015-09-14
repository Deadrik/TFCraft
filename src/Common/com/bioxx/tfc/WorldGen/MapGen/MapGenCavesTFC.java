package com.bioxx.tfc.WorldGen.MapGen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.WorldGen.DataLayer;
import com.bioxx.tfc.api.TFCBlocks;

public class MapGenCavesTFC extends MapGenBaseTFC
{
	//private byte[] metaArray;

	public void generate(IChunkProvider par1IChunkProvider, World par2World, int par3, int par4, Block[] idsBig, byte[] metaBig)
	{
		//metaArray = metaBig;
		super.generate(par1IChunkProvider, par2World, par3, par4, idsBig);
	}
	/**
	 * Generates a larger initial cave node than usual. Called 25% of the time.
	 */
	protected void generateLargeCaveNode(long par1, int par3, int par4, Block[] par5, double par6, double par8, double par10)
	{
		this.generateCaveNode(par1, par3, par4, par5, par6, par8, par10, 1.0F + this.rand.nextFloat() * 6.0F, 0.0F, 0.0F, -1, -1, 0.5D, 2.5D);
	}

	/**
	 * Generates a node in the current cave system recursion tree.
	 */
	protected void generateCaveNode(long seed, int chunkX, int chunkZ, Block[] idArray, double i, double j, double k, float par12, float par13, float par14, int par15, int par16, double par17, double width)
	{
		double worldX = chunkX * 16 + 8;
		double worldZ = chunkZ * 16 + 8;
		float var23 = 0.0F;
		float var24 = 0.0F;
		Random var25 = new Random(seed);

		if (par16 <= 0)
		{
			int var26 = this.range * 16 - 16;
			par16 = var26 - var25.nextInt(var26 / 4);
		}

		boolean var54 = false;
		if (par15 == -1)
		{
			par15 = par16 / 2;
			var54 = true;
		}

		int var27 = var25.nextInt(par16 / 2) + par16 / 4;
		for (boolean var28 = var25.nextInt(6) == 0; par15 < par16; ++par15)
		{
			float var33 = MathHelper.cos(par14);
			float var34 = MathHelper.sin(par14);
			i += MathHelper.cos(par13) * var33;
			j += var34;
			k += MathHelper.sin(par13) * var33;

			if (var28)
				par14 *= 0.92F;
			else
				par14 *= 0.7F;

			par14 += var24 * 0.1F;
			par13 += var23 * 0.1F;
			var24 *= 0.9F;
			var23 *= 0.75F;
			var24 += (var25.nextFloat() - var25.nextFloat()) * var25.nextFloat() * 2.0F;
			var23 += (var25.nextFloat() - var25.nextFloat()) * var25.nextFloat() * 4.0F;

			if (!var54 && par15 == var27 && par12 > 1.0F && par16 > 0)
			{
				this.generateCaveNode(var25.nextLong(), chunkX, chunkZ, idArray, i, j, k, var25.nextFloat() * 0.5F + 0.5F, par13 - ((float)Math.PI / 2F), par14 / 3.0F, par15, par16, 1.0D, width);
				this.generateCaveNode(var25.nextLong(), chunkX, chunkZ, idArray, i, j, k, var25.nextFloat() * 0.5F + 0.5F, par13 + ((float)Math.PI / 2F), par14 / 3.0F, par15, par16, 1.0D, width);
				return;
			}

			double var29 = width + MathHelper.sin(par15 * (float) Math.PI / par16) * par12 * 1.0F;
			double var31 = var29 * par17;
			if (var54 || var25.nextInt(4) != 0)
			{
				double var35 = i - worldX;
				double var37 = k - worldZ;
				double var39 = par16 - par15;
				double var41 = par12 + 2.0F + 16.0F;

				if (var35 * var35 + var37 * var37 - var39 * var39 > var41 * var41)
					return;

				if (i >= worldX - 16.0D - var29 * 2.0D && k >= worldZ - 16.0D - var29 * 2.0D && i <= worldX + 16.0D + var29 * 2.0D && k <= worldZ + 16.0D + var29 * 2.0D)
				{
					int var55 = MathHelper.floor_double(i - var29) - chunkX * 16 - 1;
					int var36 = MathHelper.floor_double(i + var29) - chunkX * 16 + 1;
					int var57 = MathHelper.floor_double(j - var31) - 1;
					int yCoord = MathHelper.floor_double(j + var31) + 1;
					int var56 = MathHelper.floor_double(k - var29) - chunkZ * 16 - 1;
					int var40 = MathHelper.floor_double(k + var29) - chunkZ * 16 + 1;

					if (var55 < 0)
						var55 = 0;

					if (var36 > 16)
						var36 = 16;

					if (var57 < 1)
						var57 = 1;

					if (yCoord > 250)
						yCoord = 250;

					if (var56 < 0)
						var56 = 0;

					if (var40 > 16)
						var40 = 16;

					boolean var58 = false;
					int xCoord;
					int zCoord;

					for (xCoord = var55; !var58 && xCoord < var36; ++xCoord)
					{
						for (zCoord = var56; !var58 && zCoord < var40; ++zCoord)
						{
							for (int y = yCoord + 1; !var58 && y >= var57 - 1; --y)
							{
								int index = (xCoord * 16 + zCoord) * 256 + y;
								if (y >= 0 && y < 256)
								{
									if (idArray[index] == TFCBlocks.saltWaterStationary || idArray[index] == TFCBlocks.freshWaterStationary)
										var58 = true;
									if (y != var57 - 1 && xCoord != var55 && xCoord != var36 - 1 && zCoord != var56 && zCoord != var40 - 1)
										y = var57;
								}
							}
						}
					}

					if (!var58)
					{
						for (xCoord = var55; xCoord < var36; ++xCoord)
						{
							double var59 = (xCoord + chunkX * 16 + 0.5D - i) / var29;
							for (zCoord = var56; zCoord < var40; ++zCoord)
							{
								double var46 = (zCoord + chunkZ * 16 + 0.5D - k) / var29;
								int index = (xCoord * 16 + zCoord) * 256 + yCoord;
								boolean isGrass = false;
								Block grassBlock = Blocks.air;
								if (var59 * var59 + var46 * var46 < 1.0D)
								{
									for (int var50 = yCoord - 1; var50 >= var57; --var50)
									{
										double var51 = (var50 + 0.5D - j) / var31;
										if (var51 > -0.7D && var59 * var59 + var51 * var51 + var46 * var46 < 1.0D)
										{
											if (TFC_Core.isGrass(idArray[index]))
											{
												grassBlock = idArray[index];
												isGrass = true;
											}
											if (TFC_Core.isSoil(idArray[index]) || TFC_Core.isRawStone(idArray[index]))
											{
												if(TFC_Core.isSoilOrGravel(idArray[index+1]))
												{
													for(int upCount = 1; TFC_Core.isSoilOrGravel(idArray[index+upCount]); upCount++)
													{idArray[index+upCount] = Blocks.air;}
												}

												if (var50 < 10 && TFC_Climate.getStability(this.worldObj, (int)worldX, (int)worldZ) == 1)
												{
													idArray[index] = TFCBlocks.lava;
													//metaArray[index] = 0;
												}
												else
												{
													idArray[index] = Blocks.air;
													//metaArray[index] = 0;
													if (isGrass && TFC_Core.isDirt(idArray[index - 1]))
													{
														//int meta = metaArray[index - 1];
														idArray[index - 1] = grassBlock;
													}
												}
											}
										}
										--index;
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
	protected void recursiveGenerate(World world, int par2, int par3, int par4, int par5, Block[] ids)
	{
		int var7 = this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(40) + 1) + 1);
		double xCoord = par2 * 16 + this.rand.nextInt(16);
		double yCoord = this.rand.nextInt(1 + this.rand.nextInt(140)) + 60;
		double zCoord = par3 * 16 + this.rand.nextInt(16);
		float rain = TFC_Climate.getRainfall(world, (int) xCoord, 144, (int) zCoord);
		double width = 2;
		int caveChance = 35;

		if (rain > 1000)
		{
			width += 0.5;
			caveChance -= 5;
		}
		else if (rain > 2000)
		{
			width += 1;
			caveChance -= 10;
		}
		else if (rain < 1000)
		{
			width -= 0.5;
			caveChance += 5;
		}
		else if (rain < 500)
		{
			width -= 1;
			caveChance += 10;
		}
		else if (rain < 250)
		{
			width -= 1.25;
			caveChance += 15;
		}

		if (TFC_Climate.getCacheManager(world) != null)
		{
			DataLayer rockLayer1 = TFC_Climate.getCacheManager(world).getRockLayerAt((int)xCoord, (int)zCoord, 0);
			Block layerID = rockLayer1.block;
			if(layerID == TFCBlocks.stoneIgEx)
			{
				width -= 0.4;
			}
			else if(layerID == TFCBlocks.stoneIgIn)
			{
				width -= 0.5;
			}
			else if(layerID == TFCBlocks.stoneSed)
			{
				width += 0.2;
				var7 += 5;
			}
			else if(layerID == TFCBlocks.stoneMM)
			{
				width += 0.3;
			}
		}

		if (yCoord < 32)
			width *= 0.5;
		else if (yCoord < 64)
			width *= 0.65;
		else if (yCoord < 96)
			width *= 0.80;
		else if (yCoord < 120)
			width *= 0.90;
		else
			width *= 0.5;

		if (this.rand.nextInt(8) == 0)
			width += 1;

		if (this.rand.nextInt(caveChance) != 0)
			var7 = 0;

		for (int var8 = 0; var8 < var7; ++var8)
		{
			int var15 = 1;
			if (this.rand.nextInt(4) == 0)
			{
				this.generateLargeCaveNode(this.rand.nextLong(), par4, par5, ids, xCoord, yCoord, zCoord);
				var15 += this.rand.nextInt(4);
			}

			for (int var16 = 0; var16 < var15; ++var16)
			{
				float var17 = this.rand.nextFloat() * (float) Math.PI * 2.0F;
				float var18 = (this.rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
				float var19 = this.rand.nextFloat() * 2.0F + this.rand.nextFloat();
				if (this.rand.nextInt(10) == 0)
					var19 *= this.rand.nextFloat() * this.rand.nextFloat() * 3.0F + 1.0F;
				this.generateCaveNode(this.rand.nextLong(), par4, par5, ids, xCoord, yCoord, zCoord, var19, var17, var18, 0, 0, 1.0D, width);
			}
		}
	}
}
