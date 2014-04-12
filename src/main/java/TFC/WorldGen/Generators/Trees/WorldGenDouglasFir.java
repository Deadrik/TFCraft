package TFC.WorldGen.Generators.Trees;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import TFC.Core.TFC_Core;

public class WorldGenDouglasFir extends WorldGenerator
{
	private boolean Tall = false;

	private int metaID;

	public WorldGenDouglasFir(boolean par1, int m, boolean t)
	{
		super(par1);
		metaID = m;
		Tall = t;
	}

	@Override
	public boolean generate(World world, Random par2Random, int par3, int par4, int par5)
	{
		int i = par2Random.nextInt(10) + 10;
		if(par2Random.nextInt(20)==0)
			Tall=true;
		if(Tall)
			i += par2Random.nextInt(10);
		boolean flag = true;

		if (par4 < 1 || par4 + i + 1 > 256)
			return false;

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
						if (j2 != Blocks.air && (j2 != Blocks.leaves || j2 != Blocks.leaves2) && j2 != Blocks.grass && j2 != Blocks.dirt &&
								(j2 != Blocks.log || j2 != Blocks.log2) && j2 != Blocks.sapling)
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

		Block id = world.getBlock(par3, par4 - 1, par5);
		if (!TFC_Core.isSoil(id) || par4 >= 256 - i - 1)
			return false;

		byte byte1 = 3;
		int i1 = 0;

		for (int k1 = par4 + (i/3)-1; k1 <= par4 + i-1; k1++)
		{
			int k2 = k1 - (par4 + i);
			int j3 = 1 - k2 / 2;
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
					id = world.getBlock(l3, k1, l4);
					if ((Math.abs(j4) != 0 || Math.abs(i5) != 0 && k2 != 0) && 
							(Math.abs(j4) + Math.abs(i5)!=x*2 || (k1-par4>i/2&&k1-par4<(4*i/5)) || 
							k1-par4-(i/3)+2==2) && (par2Random.nextInt(12)!=0) && id == Blocks.air)
					{
						setBlockAndNotifyAdequately(world, l3, k1, l4, Blocks.leaves, metaID);
					}
				}
			}
		}
		setBlockAndNotifyAdequately(world, par3, par4 + i, par5, Blocks.leaves, metaID);

		for (int l1 = 0; l1 < i; l1++)
		{
			Block l2 = world.getBlock(par3, par4 + l1, par5);
			if (l2 != Blocks.air && (l2 != Blocks.leaves || l2 != Blocks.leaves2))
				continue;
			setBlockAndNotifyAdequately(world, par3, par4 + l1, par5, Blocks.log, metaID);
		}
		return true;
	}

}
