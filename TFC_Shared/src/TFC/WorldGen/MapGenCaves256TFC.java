package TFC.WorldGen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import TFC.TFCBlocks;
import TFC.Core.TFC_Core;

public class MapGenCaves256TFC extends MapGenBaseTFC
{

	private byte[] metaArray;

	public void generate(IChunkProvider par1IChunkProvider, World par2World, int par3, int par4, short[] id, byte[] meta)
	{
		metaArray = meta;
		super.generate(par1IChunkProvider, par2World, par3, par4, id);
	}
	/**
	 * Generates a node in the current cave system recursion tree.
	 */
	protected void generateCaveNode(long par1, int par3, int par4, short[] blockArray, double par6, double par8, double par10, float par12, float par13, float par14, int par15, int par16, double par17, double width)
	{
		double var19 = par3 * 16 + 8;
		double var21 = par4 * 16 + 8;
		float var23 = 0.0F;
		float var24 = 0.0F;
		Random var25 = new Random(par1);

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
			double var29 = width + MathHelper.sin(par15 * (float)Math.PI / par16) * par12 * 1.0F;
			double var31 = var29 * par17;
			float var33 = MathHelper.cos(par14);
			float var34 = MathHelper.sin(par14);
			par6 += MathHelper.cos(par13) * var33;
			par8 += var34;
			par10 += MathHelper.sin(par13) * var33;

			if (var28)
			{
				par14 *= 0.92F;
			}
			else
			{
				par14 *= 0.7F;
			}

			par14 += var24 * 0.1F;
			par13 += var23 * 0.1F;
			var24 *= 0.9F;
			var23 *= 0.75F;
			var24 += (var25.nextFloat() - var25.nextFloat()) * var25.nextFloat() * 2.0F;
			var23 += (var25.nextFloat() - var25.nextFloat()) * var25.nextFloat() * 4.0F;

			if (!var54 && par15 == var27 && par12 > 1.0F && par16 > 0)
			{
				this.generateCaveNode(var25.nextLong(), par3, par4, blockArray, par6, par8, par10, var25.nextFloat() * 0.5F + 0.5F, par13 - (float)Math.PI / 2F, par14 / 3.0F, par15, par16, 1.0D, width);
				this.generateCaveNode(var25.nextLong(), par3, par4, blockArray, par6, par8, par10, var25.nextFloat() * 0.5F + 0.5F, par13 + (float)Math.PI / 2F, par14 / 3.0F, par15, par16, 1.0D, width);
				return;
			}

			if (var54 || var25.nextInt(4) != 0)
			{
				double var35 = par6 - var19;
				double var37 = par10 - var21;
				double var39 = par16 - par15;
				double var41 = par12 + 2.0F + 16.0F;

				if (var35 * var35 + var37 * var37 - var39 * var39 > var41 * var41)
				{
					return;
				}

				if (par6 >= var19 - 16.0D - var29 * 2.0D && par10 >= var21 - 16.0D - var29 * 2.0D && par6 <= var19 + 16.0D + var29 * 2.0D && par10 <= var21 + 16.0D + var29 * 2.0D)
				{
					int var55 = MathHelper.floor_double(par6 - var29) - par3 * 16 - 1;
					int var36 = MathHelper.floor_double(par6 + var29) - par3 * 16 + 1;
					int var57 = MathHelper.floor_double(par8 - var31) - 1;
					int yCoord = MathHelper.floor_double(par8 + var31) + 1;
					int var56 = MathHelper.floor_double(par10 - var29) - par4 * 16 - 1;
					int var40 = MathHelper.floor_double(par10 + var29) - par4 * 16 + 1;

					if (var55 < 0)
					{
						var55 = 0;
					}

					if (var36 > 16)
					{
						var36 = 16;
					}

					if (var57 < 1)
					{
						var57 = 1;
					}

					if (yCoord > 255)
					{
						yCoord = 255;
					}

					if (var56 < 0)
					{
						var56 = 0;
					}

					if (var40 > 16)
					{
						var40 = 16;
					}

					boolean var58 = false;
					int xCoord;
					int zCoord;

					for (xCoord = var55; !var58 && xCoord < var36; ++xCoord)
					{
						for (int var43 = var56; !var58 && var43 < var40; ++var43)
						{
							for (int var44 = yCoord + 1; !var58 && var44 >= var57 - 1; --var44)
							{
								zCoord = (xCoord * 16 + var43) * 256 + var44;

								if (var44 >= 0 && var44 < 256)
								{
									if (blockArray[zCoord] == Block.waterMoving.blockID || blockArray[zCoord] == Block.waterStill.blockID)
									{
										var58 = true;
									}

									if (var44 != var57 - 1 && xCoord != var55 && xCoord != var36 - 1 && var43 != var56 && var43 != var40 - 1)
									{
										var44 = var57;
									}
								}
							}
						}
					}

					if (!var58)
					{
						for (xCoord = var55; xCoord < var36; ++xCoord)
						{
							double var59 = (xCoord + par3 * 16 + 0.5D - par6) / var29;

							for (zCoord = var56; zCoord < var40; ++zCoord)
							{
								double var46 = (zCoord + par4 * 16 + 0.5D - par10) / var29;
								int index = (xCoord * 16 + zCoord) * 256 + yCoord;
								boolean var49 = false;

								if (var59 * var59 + var46 * var46 < 1.0D)
								{
									for (int var50 = yCoord - 1; var50 >= var57; --var50)
									{
										double var51 = (var50 + 0.5D - par8) / var31;

										if (var51 > -0.7D && var59 * var59 + var51 * var51 + var46 * var46 < 1.0D)
										{
											short blockID = blockArray[index];

											if (blockID == TFCBlocks.Grass.blockID || blockID == TFCBlocks.Grass2.blockID)
											{
												var49 = true;
											}

											if (TFC_Core.isRawStone(blockID) ||
													TFC_Core.isSoil(blockID))
											{
												if (var50 < 10)
												{
													blockArray[index] = (short)Block.lavaStill.blockID;
													metaArray[index] = 0;
												}
												else
												{
													byte meta = 0;
													if(var49)
													{
														meta = metaArray[index];
													}

													blockArray[index] = 0;
													metaArray[index] = 0;
												}
											}
										}

										--index;
									}
								}
							}
						}

						if (var54)
						{
							break;
						}
					}
				}
			}
		}
	}

	/**
	 * Generates a larger initial cave node than usual. Called 25% of the time.
	 */
	protected void generateLargeCaveNode(long par1, int par3, int par4, short[] par5ArrayOfByte, double par6, double par8, double par10)
	{
		this.generateCaveNode(par1, par3, par4, par5ArrayOfByte, par6, par8, par10, 1.0F + this.rand.nextFloat() * 6.0F, 0.0F, 0.0F, -1, -1, 0.5D, 2.5D);
	}

	/**
	 * Recursively called by generate() (generate) and optionally by itself.
	 */
	@Override
	protected void recursiveGenerate(World world, int par2, int par3, int par4, int par5, short[] par6ArrayOfByte)
	{
		int var7 = this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(40) + 1) + 1);

		if (this.rand.nextInt(100) <= 10)
		{
			var7 = 0;
		}

		for (int var8 = 0; var8 < var7; ++var8)
		{
			double xCoord = par2 * 16 + this.rand.nextInt(16);
			double height = this.rand.nextInt(this.rand.nextInt(247) + 8);
			double zCoord = par4 * 16 + this.rand.nextInt(16);
			int var15 = 1;

			DataLayer rockLayer1 = ((TFCWorldChunkManager)world.getWorldChunkManager()).getRockLayerAt((int)xCoord, (int)zCoord, 0);

			double width = 2;
			if(height < 32) {
				width = 4.5;
			} else if (height < 64) {
				width = 3.0;
			} else if (height < 96) {
				width = 2.5;
			}

			int layerID = rockLayer1.data1;

			if(layerID == TFCBlocks.StoneIgEx.blockID) {
				width -= 0.55;
			} else if(layerID == TFCBlocks.StoneIgIn.blockID) {
				width -= 0.55;
			} else if(layerID == TFCBlocks.StoneSed.blockID) {
				width -= 0.3;
			} else if(layerID == TFCBlocks.StoneMM.blockID) {
				width -= 0.2;
			}

			if(this.rand.nextInt(8) == 0) {
				width += 1;
			}

			if (this.rand.nextInt(4) == 0)
			{
				this.generateLargeCaveNode(this.rand.nextLong(), par4, par5, par6ArrayOfByte, xCoord, height, zCoord);
				var15 += this.rand.nextInt(4);
			}

			for (int var16 = 0; var16 < var15; ++var16)
			{
				float var17 = this.rand.nextFloat() * (float)Math.PI * 2.0F;
				float var18 = (this.rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
				float var19 = this.rand.nextFloat() * 2.0F + this.rand.nextFloat();

				if (this.rand.nextInt(10) == 0)
				{
					var19 *= this.rand.nextFloat() * this.rand.nextFloat() * 3.0F + 1.0F;
				}

				this.generateCaveNode(this.rand.nextLong(), par4, par5, par6ArrayOfByte, xCoord, height, zCoord, var19, var17, var18, 0, 0, 1.0D, width);
			}
		}
	}
}
