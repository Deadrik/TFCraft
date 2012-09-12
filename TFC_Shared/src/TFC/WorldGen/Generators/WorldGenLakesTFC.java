package TFC.WorldGen.Generators;

import java.util.Random;

import TFC.Core.TFC_Core;
import TFC.WorldGen.DataLayer;
import TFC.WorldGen.TFCBiome;
import TFC.WorldGen.TFCWorldChunkManager;

import net.minecraft.src.*;

public class WorldGenLakesTFC extends WorldGenerator
{
	private int blockIndex;

	public WorldGenLakesTFC(int par1)
	{
		this.blockIndex = par1;
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
							Material var12 = world.getBlockMaterial(xCoord + i2, yCoord + k4, zCoord + j3);

							if (k4 >= 4 && var12.isLiquid())
							{
								return false;
							}

							if (k4 < 4 && !var12.isSolid() && world.getBlockId(xCoord + i2, yCoord + k4, zCoord + j3) != this.blockIndex)
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
							world.setBlock(xCoord + i2, yCoord + k4, zCoord + j3, k4 >= 4 ? 0 : this.blockIndex);
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
						int id = world.getBlockId(xCoord + i2, yCoord + k4 - 1, zCoord + j3);
						if (var6[(i2 * 16 + j3) * 8 + k4] && (id == Block.dirt.blockID || id == TFCBlocks.Dirt.blockID
								|| id == TFCBlocks.Dirt2.blockID || id == TFCBlocks.Grass.blockID || id == TFCBlocks.Grass2.blockID || 
								id == TFCBlocks.ClayGrass.blockID || id == TFCBlocks.ClayGrass2.blockID || 
								id == TFCBlocks.Clay.blockID || id == TFCBlocks.Clay2.blockID || 
								id == TFCBlocks.Peat.blockID || id == TFCBlocks.PeatGrass.blockID) && 
								world.getSavedLightValue(EnumSkyBlock.Sky, xCoord + i2, yCoord + k4, zCoord + j3) > 0)
						{
							TFCBiome var35 = (TFCBiome) world.getBiomeGenForCoords(xCoord + i2, zCoord + j3);

							if (var35.topBlock == Block.mycelium.blockID)
							{
								world.setBlock(xCoord + i2, yCoord + k4 - 1, zCoord + j3, Block.mycelium.blockID);
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

			if (Block.blocksList[this.blockIndex].blockMaterial == Material.lava)
			{
				int id = TFCBlocks.StoneIgEx.blockID;
				int meta = random.nextInt(4);
				for (i2 = 0; i2 < 16; ++i2)
				{
					for (j3 = 0; j3 < 16; ++j3)
					{
						for (k4 = 0; k4 < 8; ++k4)
						{
							var33 = !var6[(i2 * 16 + j3) * 8 + k4] && (i2 < 15 && var6[((i2 + 1) * 16 + j3) * 8 + k4] || i2 > 0 && var6[((i2 - 1) * 16 + j3) * 8 + k4] || j3 < 15 && var6[(i2 * 16 + j3 + 1) * 8 + k4] || j3 > 0 && var6[(i2 * 16 + j3 - 1) * 8 + k4] || k4 < 7 && var6[(i2 * 16 + j3) * 8 + k4 + 1] || k4 > 0 && var6[(i2 * 16 + j3) * 8 + k4 - 1]);

							if (var33 && (k4 < 4 || random.nextInt(2) != 0) && world.getBlockMaterial(xCoord + i2, yCoord + k4, zCoord + j3).isSolid())
							{
								world.setBlockAndMetadata(xCoord + i2, yCoord + k4, zCoord + j3, id, meta);

								if(world.getBlockId(xCoord + i2 + 1, yCoord + k4, zCoord + j3) == 0)
								{
									world.setBlockAndMetadataWithNotify(xCoord + i2 + 1, yCoord + k4, zCoord + j3, TFCBlocks.Sulfur.blockID,new Random().nextInt(4));
								}
								if(world.getBlockId(xCoord + i2 - 1, yCoord + k4, zCoord + j3) == 0)
								{
									world.setBlockAndMetadataWithNotify(xCoord + i2 - 1, yCoord + k4, zCoord + j3, TFCBlocks.Sulfur.blockID,new Random().nextInt(4));
								}
								if(world.getBlockId(xCoord + i2, yCoord + k4 + 1, zCoord + j3) == 0)
								{
									world.setBlockAndMetadataWithNotify(xCoord + i2, yCoord + k4 + 1, zCoord + j3, TFCBlocks.Sulfur.blockID,new Random().nextInt(4));
								}
								if(world.getBlockId(xCoord + i2, yCoord + k4 - 1, zCoord + j3) == 0)
								{
									world.setBlockAndMetadataWithNotify(xCoord + i2, yCoord + k4 - 1, zCoord + j3, TFCBlocks.Sulfur.blockID,new Random().nextInt(4));
								}
								if(world.getBlockId(xCoord + i2, yCoord + k4, zCoord + j3 + 1) == 0)
								{
									world.setBlockAndMetadataWithNotify(xCoord + i2, yCoord + k4, zCoord + j3 + 1, TFCBlocks.Sulfur.blockID,new Random().nextInt(4));
								}
								if(world.getBlockId(xCoord + i2, yCoord + k4, zCoord + j3 - 1) == 0)
								{
									world.setBlockAndMetadataWithNotify(xCoord + i2, yCoord + k4, zCoord + j3 - 1, TFCBlocks.Sulfur.blockID,new Random().nextInt(4));
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

						if (world.isBlockFreezable(xCoord + i2, yCoord + var34, zCoord + j3))
						{
							world.setBlock(xCoord + i2, yCoord + var34, zCoord + j3, Block.ice.blockID);
						}
					}
				}
			}

			return true;
		}
	}
}
