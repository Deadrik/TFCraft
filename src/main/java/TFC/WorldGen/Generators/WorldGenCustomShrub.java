package TFC.WorldGen.Generators;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import TFC.TFCBlocks;

public class WorldGenCustomShrub extends WorldGenerator
{
	private int field_48197_a;
	private int field_48196_b;

	public WorldGenCustomShrub(int par1, int par2)
	{
		this.field_48196_b = par1;
		this.field_48197_a = par2;
	}

	public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
	{
		Block var15 = par1World.getBlock(par3, par4, par5);

		for (boolean var6 = false; (var15 == Blocks.air || var15 == Blocks.leaves || var15 == Blocks.leaves2) && par4 > 0; --par4)
		{
			;
		}

		Block var7 = par1World.getBlock(par3, par4, par5);
		if (var7 == TFCBlocks.Dirt || var7 == TFCBlocks.Dirt2 ||var7 == TFCBlocks.Grass ||var7 == TFCBlocks.Grass2 ||
				var7 == TFCBlocks.ClayGrass ||var7 == TFCBlocks.ClayGrass2)
		{
			++par4;
			par1World.setBlock(par3, par4, par5, Blocks.log, this.field_48196_b, 0x2);

			for (int var8 = par4; var8 <= par4 + 2; ++var8)
			{
				int var9 = var8 - par4;
				int var10 = 2 - var9;

				for (int var11 = par3 - var10; var11 <= par3 + var10; ++var11)
				{
					int var12 = var11 - par3;

					for (int var13 = par5 - var10; var13 <= par5 + var10; ++var13)
					{
						int var14 = var13 - par5;

						if ((Math.abs(var12) != var10 || Math.abs(var14) != var10 || par2Random.nextInt(2) != 0) && !par1World.getBlock(var11, var8, var13).isOpaqueCube())
						{
							this.setBlockAndNotifyAdequately(par1World, var11, var8, var13, Blocks.leaves, this.field_48196_b);
						}
					}
				}
			}
		}

		return true;
	}
}
