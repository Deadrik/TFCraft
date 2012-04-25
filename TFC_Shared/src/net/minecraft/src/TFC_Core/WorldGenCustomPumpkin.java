package net.minecraft.src.TFC_Core;

import java.util.Random;

import net.minecraft.src.*;

public class WorldGenCustomPumpkin extends WorldGenerator
{
	public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
	{
		for (int var6 = 0; var6 < 64; ++var6)
		{
			int var7 = par3 + par2Random.nextInt(8) - par2Random.nextInt(8);
			int var8 = par4 + par2Random.nextInt(4) - par2Random.nextInt(4);
			int var9 = par5 + par2Random.nextInt(8) - par2Random.nextInt(8);
			int var5 = par1World.getBlockId(var7, var8 - 1, var9);
			if (par1World.isAirBlock(var7, var8, var9) && 
					(var5 == Block.grass.blockID || var5 == mod_TFC_Core.terraGrass.blockID || var5 == mod_TFC_Core.terraGrass2.blockID ||
					var5 == mod_TFC_Core.terraDirt.blockID || var5 == mod_TFC_Core.terraDirt.blockID ||
					var5 == mod_TFC_Core.terraClayGrass.blockID || var5 == mod_TFC_Core.terraClayGrass2.blockID ||
					var5 == mod_TFC_Core.terraPeatGrass.blockID || var5 == mod_TFC_Core.terraPeat.blockID) && Block.pumpkin.canPlaceBlockAt(par1World, var7, var8, var9))
			{
				par1World.setBlockAndMetadata(var7, var8, var9, Block.pumpkin.blockID, par2Random.nextInt(4));
			}
		}

		return true;
	}
}
