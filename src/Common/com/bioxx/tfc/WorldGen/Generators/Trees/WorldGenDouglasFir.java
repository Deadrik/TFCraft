package com.bioxx.tfc.WorldGen.Generators.Trees;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.TFCBlocks;

public class WorldGenDouglasFir extends WorldGenerator
{
	private boolean tall;
	private final int metaID;

	public WorldGenDouglasFir(boolean par1, int m, boolean t)
	{
		super(par1);
		metaID = m;
		tall = t;
	}

	@Override
	public boolean generate(World world, Random rand, int par3, int par4, int par5)
	{
		int i = rand.nextInt(10) + 10;
		if(rand.nextInt(20)==0)
			tall=true;
		if(tall)
			i += rand.nextInt(10);

		if (par4 < 1 || par4 + i + 1 > 256)
			return false;

		boolean flag = true;
		for (int j = par4; j <= par4 + 1 + i; j++)
		{
			byte byte0 = 1;

			if (j == par4)
				byte0 = 0;

			if (j >= (par4 + 1 + i) - 2)
				byte0 = 2;

			for (int l = par3 - byte0; l <= par3 + byte0 && flag; l++)
			{
				for (int j1 = par5 - byte0; j1 <= par5 + byte0 && flag; j1++)
				{
					if (j >= 0 && j < 256)
					{
						Block j2 = world.getBlock(l, j, j1);
						if (!j2.isAir(world, l, j, j1) && !j2.isReplaceable(world, l, j, j1))
						{
							flag = false;
						}
					}
					else
					{
						flag = false;
					}
				}
			}
		}

		if (!flag)
			return false;

		if (!TFC_Core.isSoil(world.getBlock(par3, par4 - 1, par5)) || par4 >= 256 - i - 1)
			return false;

		for (int k1 = par4 + (i/3)-1; k1 <= par4 + i-1; k1++)
		{
			int k2 = k1 - (par4 + i);
			int z=i;
			if (i>20)
				z=20;
			int x = z/10 +1;
			if (k1-par4>i/2||k1-par4-(i/3)+2<3)
				x--;
			if(par4+i-k1<4)
				x=1;

			for (int l3 = par3 -x; l3 <= par3 +x; l3++)
			{
				int j4 = l3 - par3;
				for (int l4 = par5-x; l4 <= par5 +x; l4++)
				{
					int i5 = l4 - par5;
					if ((Math.abs(j4) != 0 || Math.abs(i5) != 0 && k2 != 0) &&
						(Math.abs(j4) + Math.abs(i5) != x * 2 || 
						k1 - par4 > i / 2 && k1 - par4 < (4 * i / 5) || 
						k1 - par4 - (i / 3) + 2 == 2) &&
						rand.nextInt(12) != 0 && world.isAirBlock(l3, k1, l4))
					{
						setBlockAndNotifyAdequately(world, l3, k1, l4, TFCBlocks.leaves, metaID);
					}
				}
			}
		}
		setBlockAndNotifyAdequately(world, par3, par4+i, par5, TFCBlocks.leaves, metaID);
		for (int l1 = 0; l1 < i; l1++)
		{
			setBlockAndNotifyAdequately(world, par3, par4 + l1, par5, TFCBlocks.logNatural, metaID);
		}
		return true;
	}

}
