package TFC.WorldGen;

import java.util.Random;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.feature.*;
import TFC.TFCBlocks;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Settings;

public class MapGenRiverRavine256TFC extends MapGenBaseTFC
{
	private float[] field_35627_a = new float[1024];
	
	private int[] metaArray;
	
	public void generate(IChunkProvider par1IChunkProvider, World par2World, int par3, int par4, int[] id, int[] meta)
    {
	    metaArray = meta;
	    super.generate(par1IChunkProvider, par2World, par3, par4, id);
    }

	protected void generateRavine(long par1, int xCoord, int zCoord, int[] blockArray, double par6, double par8, double par10, float par12, float par13, float par14, int par15, int par16, double par17, double waterHeight)
	{
		Random var19 = new Random(par1);
		double var20 = (double)(xCoord * 16 + 8);
		double var22 = (double)(zCoord * 16 + 8);
		float var24 = 0.0F;
		float var25 = 0.0F;

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
			{
				var27 = 1.0F + var19.nextFloat() * var19.nextFloat() * 1.0F;
			}

			this.field_35627_a[var28] = var27 * var27;
		}

		for (; par15 < par16; ++par15)
		{
			double var53 = 3.5D + (double)(MathHelper.sin((float)par15 * (float)Math.PI / (float)par16) * par12 * 1.0F);
			double var30 = var53 * par17;
			var53 *= (double)var19.nextFloat() * 0.25D + 0.75D;
			var30 *= (double)var19.nextFloat() * 0.25D + 0.75D;
			float var32 = MathHelper.cos(par14);
			float var33 = MathHelper.sin(par14);
			par6 += (double)(MathHelper.cos(par13) * var32);
			par8 += (double)var33;
			par10 += (double)(MathHelper.sin(par13) * var32);
			par14 *= 0.7F;
			par14 += var25 * 0.05F;
			par13 += var24 * 0.05F;
			var25 *= 0.8F;
			var24 *= 0.5F;
			var25 += (var19.nextFloat() - var19.nextFloat()) * var19.nextFloat() * 2.0F;
			var24 += (var19.nextFloat() - var19.nextFloat()) * var19.nextFloat() * 4.0F;

			if (var54 || var19.nextInt(4) != 0)//<--Determines the length of the ravine
			{
				double var34 = par6 - var20;
				double var36 = par10 - var22;
				double var38 = (double)(par16 - par15);
				double var40 = (double)(par12 + 2.0F + 16.0F);

				if (var34 * var34 + var36 * var36 - var38 * var38 > var40 * var40)
				{
					return;
				}

				if (par6 >= var20 - 16.0D - var53 * 2.0D && par10 >= var22 - 16.0D - var53 * 2.0D && par6 <= var20 + 16.0D + var53 * 2.0D && par10 <= var22 + 16.0D + var53 * 2.0D)
				{
					int var56 = MathHelper.floor_double(par6 - var53) - xCoord * 16 - 1;
					int var35 = MathHelper.floor_double(par6 + var53) - xCoord * 16 + 1;
					int var55 = MathHelper.floor_double(par8 - var30) - 1;
					int var37 = MathHelper.floor_double(par8 + var30) + 1;
					int var57 = MathHelper.floor_double(par10 - var53) - zCoord * 16 - 1;
					int var39 = MathHelper.floor_double(par10 + var53) - zCoord * 16 + 1;

					if (var56 < 0)
					{
						var56 = 0;
					}

					if (var35 > 16)
					{
						var35 = 16;
					}

					if (var55 < 1)
					{
						var55 = 1;
					}

					if (var37 > 250)
					{
						var37 = 250;
					}

					if (var57 < 0)
					{
						var57 = 0;
					}

					if (var39 > 16)
					{
						var39 = 16;
					}

					boolean var58 = false;
					int x;
					int z;

					for (x = var56; !var58 && x < var35; ++x)
					{
						for (int var42 = var57; !var58 && var42 < var39; ++var42)
						{
							for (int var43 = var37 + 1; !var58 && var43 >= var55 - 1; --var43)
							{
								z = (x * 16 + var42) * 256 + var43;

								if (var43 >= 0 && var43 < 256)
								{
//									if (par5ArrayOfByte[var44] == Block.waterMoving.blockID || par5ArrayOfByte[var44] == Block.waterStill.blockID)
//									{
//										var58 = true;
//									}

									if (var43 != var55 - 1 && x != var56 && x != var35 - 1 && var42 != var57 && var42 != var39 - 1)
									{
										var43 = var55;
									}
								}
							}
						}
					}

					if (!var58)
					{
						for (x = var56; x < var35; ++x)
						{
							double var59 = ((double)(x + xCoord * 16) + 0.5D - par6) / var53;

							for (z = var57; z < var39; ++z)
							{
								double var45 = ((double)(z + zCoord * 16) + 0.5D - par10) / var53;
								int var47 = (x * 16 + z) * 256 + var37;
								boolean var48 = false;

								if (var59 * var59 + var45 * var45 < 1.0D)
								{
									for (int var49 = var37 - 1; var49 >= var55; --var49)
									{
										double var50 = ((double)var49 + 0.5D - par8) / var30;

										if ((var59 * var59 + var45 * var45) * (double)this.field_35627_a[var49] + var50 * var50 / 6.0D < 1.0D)
										{
											int var52 = blockArray[var47];

											if (var52 == TFCBlocks.Grass.blockID || var52 == TFCBlocks.Grass2.blockID)
											{
												var48 = true;
											}

											if (var52 == (byte)TFCBlocks.StoneIgIn.blockID || var52 == (byte)TFCBlocks.StoneIgEx.blockID || 
                                                    var52 == (byte)TFCBlocks.StoneSed.blockID || var52 == (byte)TFCBlocks.StoneMM.blockID || 
                                                    var52 == (byte)Block.stone.blockID || var52 == (byte)Block.dirt.blockID || (byte)var52 == Block.grass.blockID ||
                                                            var52 == (byte)TFCBlocks.Grass.blockID || var52 == (byte)TFCBlocks.Grass2.blockID||
                                                                    var52 == (byte)TFCBlocks.Dirt.blockID || var52 == (byte)TFCBlocks.Dirt2.blockID||
                                                                            var52 == (byte)TFCBlocks.ClayGrass.blockID || var52 == (byte)TFCBlocks.ClayGrass2.blockID||
                                                                                    var52 == (byte)TFCBlocks.Clay.blockID || var52 == (byte)TFCBlocks.Clay2.blockID)
											{
												if (var49 < 10)
												{
													blockArray[var47] = (byte)Block.lavaMoving.blockID;
												}
												else
												{
												    if(var49 < waterHeight)
												    {
												        blockArray[var47] = (byte)Block.waterStill.blockID;
												        metaArray[var47] = 0;
												    }
                                                    else
                                                    {
                                                        blockArray[var47] = 0;
                                                        metaArray[var47] = 0;
                                                    }
												    
												    DataLayer rockLayer1 = ((TFCWorldChunkManager)worldObj.getWorldChunkManager()).getRockLayerAt((int)x + xCoord * 16, (int)z + zCoord * 16, 0);
												    
													if (var48 && TFC_Core.isDirt(blockArray[var47 - 1]))
													{
														blockArray[var47 - 1] = (byte) TFC_Core.getTypeForGrass(rockLayer1.data2);
													}

												}
											}
										}

										--var47;
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
	 * Recursively called by generate() (generate) and optionally by itself.
	 */
	 protected void recursiveGenerate(World world, int par2, int par3, int par4, int par5, int[] par6ArrayOfByte)
	{
		if (this.rand.nextInt(400) == 0)
		{
		    range = 32;
			double var7 = (double)(par2 * 16 + this.rand.nextInt(16));
			double var9 = (double)(this.rand.nextInt(this.rand.nextInt(90) + 8) + 40);
			double var11 = (double)(par3 * 16 + this.rand.nextInt(16));
			int var13 = 1;

			for (int var14 = 0; var14 < var13; ++var14)
			{
				float var15 = this.rand.nextFloat() * (float)Math.PI * 2.0F;
				float var16 = (this.rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
				float var17 = (this.rand.nextFloat() * 2.0F + this.rand.nextFloat()) * 2.0F;
				this.generateRavine(this.rand.nextLong(), par4, par5, par6ArrayOfByte, var7, var9, var11, var17, var15, var16, 0, 0, 1.5D, var9);
			}
		}
	}
}
