package TFC.WorldGen;

import java.util.Random;

import net.minecraft.src.*;

public class WorldGenPeatPit extends WorldGenerator
{
	/** The block ID for clay. */
	private int clayBlockId;
	private BiomeGenBase biome;

	/** The number of blocks to generate. */
	private int numberOfBlocks;

	public WorldGenPeatPit(int par1, BiomeGenBase b)
	{
		biome = b;
		this.numberOfBlocks = par1;
	}

	public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
	{
		int var6 = par2Random.nextInt(this.numberOfBlocks - 8) + 8;
		byte var7 = 2;

		if(par2Random.nextInt(30) == 0 && par4 <= 146)
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
							TFCBiome biome = (TFCBiome) par1World.getBiomeGenForCoords(var8, var9);
							if(biome == BiomeGenBase.swampland|| biome == TFCBiome.swamp2 || biome == TFCBiome.swamp3 || 
							        biome == TFCBiome.swamp4 || biome == TFCBiome.swamp5 || biome == TFCBiome.swamp6 || 
							        biome == TFCBiome.swamp7 || biome == TFCBiome.swamp8 || biome == TFCBiome.swamp9 || 
							        biome == TFCBiome.swamp10)
							{
								if (var13 == mod_TFC_Core.terraDirt.blockID || var13 == mod_TFC_Core.terraDirt2.blockID || 
										var13 == mod_TFC_Core.terraClay.blockID || var13 == mod_TFC_Core.terraClay2.blockID || var13 == mod_TFC_Core.terraPeat.blockID)
								{
									par1World.setBlock(var8, var12, var9, mod_TFC_Core.terraPeat.blockID);
								}
								else if(var13 == mod_TFC_Core.terraGrass.blockID || var13 == mod_TFC_Core.terraGrass2.blockID ||
										var13 == mod_TFC_Core.terraClayGrass.blockID || var13 == mod_TFC_Core.terraClayGrass2.blockID || var13 == mod_TFC_Core.terraPeatGrass.blockID)
								{
									par1World.setBlock(var8, var12, var9, mod_TFC_Core.terraPeatGrass.blockID);
								}
							}
						}
					}
				}
			}
		}

		return true;
	}
}
