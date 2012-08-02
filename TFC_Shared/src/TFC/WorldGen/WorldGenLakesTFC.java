package TFC.WorldGen;

import java.util.Random;

import net.minecraft.src.*;

public class WorldGenLakesTFC extends WorldGenerator
{
	private int blockIndex;

	public WorldGenLakesTFC(int par1)
	{
		this.blockIndex = par1;
	}

	public boolean generate(World world, Random random, int i, int j, int k)
	{
		i -= 8;

		for (k -= 8; j > 5 && world.isAirBlock(i, j, k); --j)
		{
			;
		}

		if (j <= 4)
		{
			return false;
		}
		else
		{
			j -= 4;
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
							{
								var6[(var21 * 16 + var22) * 8 + var23] = true;
							}
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
						var33 = !var6[(i2 * 16 + j3) * 8 + k4] && (i2 < 15 && var6[((i2 + 1) * 16 + j3) * 8 + k4] || i2 > 0 && var6[((i2 - 1) * 16 + j3) * 8 + k4] || j3 < 15 && var6[(i2 * 16 + j3 + 1) * 8 + k4] || j3 > 0 && var6[(i2 * 16 + j3 - 1) * 8 + k4] || k4 < 7 && var6[(i2 * 16 + j3) * 8 + k4 + 1] || k4 > 0 && var6[(i2 * 16 + j3) * 8 + k4 - 1]);

						if (var33)
						{
							Material var12 = world.getBlockMaterial(i + i2, j + k4, k + j3);

							if (k4 >= 4 && var12.isLiquid())
							{
								return false;
							}

							if (k4 < 4 && !var12.isSolid() && world.getBlockId(i + i2, j + k4, k + j3) != this.blockIndex)
							{
								return false;
							}
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
						{
							world.setBlock(i + i2, j + k4, k + j3, k4 >= 4 ? 0 : this.blockIndex);
						}
					}
				}
			}

			for (i2 = 0; i2 < 16; ++i2)
			{
				for (j3 = 0; j3 < 16; ++j3)
				{
					for (k4 = 4; k4 < 8; ++k4)
					{
						int id = world.getBlockId(i + i2, j + k4 - 1, k + j3);
						if (var6[(i2 * 16 + j3) * 8 + k4] && (id == Block.dirt.blockID || id == mod_TFC_Core.terraDirt.blockID
								|| id == mod_TFC_Core.terraDirt2.blockID || id == mod_TFC_Core.terraGrass.blockID || id == mod_TFC_Core.terraGrass2.blockID || 
								id == mod_TFC_Core.terraClayGrass.blockID || id == mod_TFC_Core.terraClayGrass2.blockID || 
								id == mod_TFC_Core.terraClay.blockID || id == mod_TFC_Core.terraClay2.blockID || 
								id == mod_TFC_Core.terraPeat.blockID || id == mod_TFC_Core.terraPeatGrass.blockID) && 
								world.getSavedLightValue(EnumSkyBlock.Sky, i + i2, j + k4, k + j3) > 0)
						{
							BiomeGenBase var35 = world.getBiomeGenForCoords(i + i2, k + j3);

							if (var35.topBlock == Block.mycelium.blockID)
							{
								world.setBlock(i + i2, j + k4 - 1, k + j3, Block.mycelium.blockID);
							}
							else
							{
								world.setBlock(i + i2, j + k4 - 1, k + j3, var35.GrassID);
							}
						}
					}
				}
			}

			if (Block.blocksList[this.blockIndex].blockMaterial == Material.lava)
			{
				int id = mod_TFC_Core.terraStoneIgEx.blockID;
				int meta = random.nextInt(4);
				for (i2 = 0; i2 < 16; ++i2)
				{
					for (j3 = 0; j3 < 16; ++j3)
					{
						for (k4 = 0; k4 < 8; ++k4)
						{
							var33 = !var6[(i2 * 16 + j3) * 8 + k4] && (i2 < 15 && var6[((i2 + 1) * 16 + j3) * 8 + k4] || i2 > 0 && var6[((i2 - 1) * 16 + j3) * 8 + k4] || j3 < 15 && var6[(i2 * 16 + j3 + 1) * 8 + k4] || j3 > 0 && var6[(i2 * 16 + j3 - 1) * 8 + k4] || k4 < 7 && var6[(i2 * 16 + j3) * 8 + k4 + 1] || k4 > 0 && var6[(i2 * 16 + j3) * 8 + k4 - 1]);

							if (var33 && (k4 < 4 || random.nextInt(2) != 0) && world.getBlockMaterial(i + i2, j + k4, k + j3).isSolid())
							{
								world.setBlockAndMetadata(i + i2, j + k4, k + j3, id, meta);

								if(world.getBlockId(i + i2 + 1, j + k4, k + j3) == 0)
								{
									world.setBlockAndMetadataWithNotify(i + i2 + 1, j + k4, k + j3, mod_TFC_Core.terraSulfur.blockID,new Random().nextInt(4));
								}
								if(world.getBlockId(i + i2 - 1, j + k4, k + j3) == 0)
								{
									world.setBlockAndMetadataWithNotify(i + i2 - 1, j + k4, k + j3, mod_TFC_Core.terraSulfur.blockID,new Random().nextInt(4));
								}
								if(world.getBlockId(i + i2, j + k4 + 1, k + j3) == 0)
								{
									world.setBlockAndMetadataWithNotify(i + i2, j + k4 + 1, k + j3, mod_TFC_Core.terraSulfur.blockID,new Random().nextInt(4));
								}
								if(world.getBlockId(i + i2, j + k4 - 1, k + j3) == 0)
								{
									world.setBlockAndMetadataWithNotify(i + i2, j + k4 - 1, k + j3, mod_TFC_Core.terraSulfur.blockID,new Random().nextInt(4));
								}
								if(world.getBlockId(i + i2, j + k4, k + j3 + 1) == 0)
								{
									world.setBlockAndMetadataWithNotify(i + i2, j + k4, k + j3 + 1, mod_TFC_Core.terraSulfur.blockID,new Random().nextInt(4));
								}
								if(world.getBlockId(i + i2, j + k4, k + j3 - 1) == 0)
								{
									world.setBlockAndMetadataWithNotify(i + i2, j + k4, k + j3 - 1, mod_TFC_Core.terraSulfur.blockID,new Random().nextInt(4));
								}
							}
						}
					}
				}
			}

			if (Block.blocksList[this.blockIndex].blockMaterial == Material.water)
			{
				for (i2 = 0; i2 < 16; ++i2)
				{
					for (j3 = 0; j3 < 16; ++j3)
					{
						byte var34 = 4;

						if (world.isBlockFreezable(i + i2, j + var34, k + j3))
						{
							world.setBlock(i + i2, j + var34, k + j3, Block.ice.blockID);
						}
					}
				}
			}

			return true;
		}
	}
}
