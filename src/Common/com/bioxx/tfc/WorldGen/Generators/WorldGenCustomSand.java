package com.bioxx.tfc.WorldGen.Generators;

import java.util.Random;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.WorldGen.TFCWorldChunkManager;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenCustomSand extends WorldGenerator
{
	/** Stores Block for WorldGenSand */
	private Block sandBlock;

	/** The maximum radius used when generating a patch of blocks. */
	private int radius;

	public WorldGenCustomSand(int par1, Block par2)
	{
		this.sandBlock = par2;
		this.radius = par1;
	}

	public boolean generate(World world, Random par2Random, int par3, int par4, int par5)
	{
		if (world.getBlock(par3, par4, par5).getMaterial() != Material.water)
		{
			return false;
		}
		else
		{
			int meta = ((TFCWorldChunkManager)world.provider.worldChunkMgr).getRockLayerAt(par3, par5, 0).data2;
			sandBlock = TFC_Core.getTypeForSand(meta);
			int var6 = par2Random.nextInt(this.radius - 2) + 2;
			byte var7 = 2;

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
							Block var13 = world.getBlock(var8, var12, var9);
							boolean notCorrectSoil = !TFC_Core.isSoil(var13) && !TFC_Core.isSand(var13) ;
							if (!notCorrectSoil)
								world.setBlock(var8, var12, var9, sandBlock, meta, 0x2);
						}
					}
				}
			}
			return true;
		}
	}
}
