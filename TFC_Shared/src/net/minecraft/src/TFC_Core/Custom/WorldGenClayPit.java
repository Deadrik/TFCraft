package net.minecraft.src.TFC_Core.Custom;

import java.util.Random;

import net.minecraft.src.*;

public class WorldGenClayPit extends WorldGenerator
{
	/** The block ID for clay. */
	private int clayBlockId;
	private BiomeGenBase biome;

	/** The number of blocks to generate. */
	private int numberOfBlocks;

	public WorldGenClayPit(int par1, BiomeGenBase b)
	{
		biome = b;
		this.numberOfBlocks = par1;
	}

	public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
	{
		int var6 = par2Random.nextInt(this.numberOfBlocks - 2) + 2;
		byte var7 = 1;

		if(par2Random.nextInt(30) == 0 && par4 <= 150)
		{
			for (int var8 = par3 - var6; var8 <= par3 + var6; ++var8)
			{
				for (int var9 = par5 - var6; var9 <= par5 + var6; ++var9)
				{
					int var10 = var8 - par3;
					int var11 = var9 - par5;

					if (var10 * var10 + var11 * var11 <= var6 * var6)
					{
						for (int var12 = par4 - var7; var12 <= par4 + var7; ++var12)
						{
							int var13 = par1World.getBlockId(var8, var12, var9);

							if (var13 == mod_TFC_Core.terraDirt.blockID || var13 == mod_TFC_Core.terraDirt2.blockID || var13 == mod_TFC_Core.terraClay.blockID|| var13 == mod_TFC_Core.terraClay2.blockID)
							{
								par1World.setBlockAndMetadata(var8, var12, var9, biome.ClayID, biome.TopSoilMetaID);
							}
							else if(var13 == mod_TFC_Core.terraGrass.blockID || var13 == mod_TFC_Core.terraGrass2.blockID ||
									var13 == mod_TFC_Core.terraClayGrass.blockID || var13 == mod_TFC_Core.terraClayGrass2.blockID)
							{
								par1World.setBlockAndMetadata(var8, var12, var9, biome.ClayGrassID, biome.TopSoilMetaID);
							}
						}
					}
				}
			}
		}

		return true;
	}
}
