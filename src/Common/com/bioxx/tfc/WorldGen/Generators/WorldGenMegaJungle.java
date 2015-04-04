package com.bioxx.tfc.WorldGen.Generators;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenHugeTrees;

public class WorldGenMegaJungle extends WorldGenHugeTrees
{
	public WorldGenMegaJungle(boolean p_i45456_1_, int p_i45456_2_, int p_i45456_3_, int p_i45456_4_, int p_i45456_5_)
	{
		super(p_i45456_1_, p_i45456_2_, p_i45456_3_, p_i45456_4_, p_i45456_5_);
	}

	@Override
	public boolean generate(World world, Random rand, int x, int y, int z)
	{
		int l = this.func_150533_a(rand);

		if (!this.func_150537_a(world, rand, x, y, z, l))
		{
			return false;
		}
		else
		{
			this.func_150543_c(world, x, z, y + l, 2, rand);

			for (int i1 = y + l - 2 - rand.nextInt(4); i1 > y + l / 2; i1 -= 2 + rand.nextInt(4))
			{
				float f = rand.nextFloat() * (float)Math.PI * 2.0F;
				int j1 = x + (int)(0.5F + MathHelper.cos(f) * 4.0F);
				int k1 = z + (int)(0.5F + MathHelper.sin(f) * 4.0F);
				int l1;

				for (l1 = 0; l1 < 5; ++l1)
				{
					j1 = x + (int) (1.5F + MathHelper.cos(f) * l1);
					k1 = z + (int) (1.5F + MathHelper.sin(f) * l1);
					this.setBlockAndNotifyAdequately(world, j1, i1 - 3 + l1 / 2, k1, Blocks.log, this.woodMetadata);
				}

				l1 = 1 + rand.nextInt(2);
				int i2 = i1;

				for (int j2 = i1 - l1; j2 <= i2; ++j2)
				{
					int k2 = j2 - i2;
					this.func_150534_b(world, j1, j2, k1, 1 - k2, rand);
				}
			}

			for (int l2 = 0; l2 < l; ++l2)
			{
				Block block = world.getBlock(x, y + l2, z);

				if (block.isAir(world, x, y + l2, z) || block.isLeaves(world, x, y + l2, z))
				{
					this.setBlockAndNotifyAdequately(world, x, y + l2, z, Blocks.log, this.woodMetadata);

					if (l2 > 0)
					{
						if (rand.nextInt(3) > 0 && world.isAirBlock(x - 1, y + l2, z))
						{
							this.setBlockAndNotifyAdequately(world, x - 1, y + l2, z, Blocks.vine, 8);
						}

						if (rand.nextInt(3) > 0 && world.isAirBlock(x, y + l2, z - 1))
						{
							this.setBlockAndNotifyAdequately(world, x, y + l2, z - 1, Blocks.vine, 1);
						}
					}
				}

				if (l2 < l - 1)
				{
					block = world.getBlock(x + 1, y + l2, z);

					if (block.isAir(world, x + 1, y + l2, z) || block.isLeaves(world, x + 1, y + l2, z))
					{
						this.setBlockAndNotifyAdequately(world, x + 1, y + l2, z, Blocks.log, this.woodMetadata);

						if (l2 > 0)
						{
							if (rand.nextInt(3) > 0 && world.isAirBlock(x + 2, y + l2, z))
							{
								this.setBlockAndNotifyAdequately(world, x + 2, y + l2, z, Blocks.vine, 2);
							}

							if (rand.nextInt(3) > 0 && world.isAirBlock(x + 1, y + l2, z - 1))
							{
								this.setBlockAndNotifyAdequately(world, x + 1, y + l2, z - 1, Blocks.vine, 1);
							}
						}
					}

					block = world.getBlock(x + 1, y + l2, z + 1);

					if (block.isAir(world, x + 1, y + l2, z + 1) || block.isLeaves(world, x + 1, y + l2, z + 1))
					{
						this.setBlockAndNotifyAdequately(world, x + 1, y + l2, z + 1, Blocks.log, this.woodMetadata);

						if (l2 > 0)
						{
							if (rand.nextInt(3) > 0 && world.isAirBlock(x + 2, y + l2, z + 1))
							{
								this.setBlockAndNotifyAdequately(world, x + 2, y + l2, z + 1, Blocks.vine, 2);
							}

							if (rand.nextInt(3) > 0 && world.isAirBlock(x + 1, y + l2, z + 2))
							{
								this.setBlockAndNotifyAdequately(world, x + 1, y + l2, z + 2, Blocks.vine, 4);
							}
						}
					}

					block = world.getBlock(x, y + l2, z + 1);

					if (block.isAir(world, x, y + l2, z + 1) || block.isLeaves(world, x, y + l2, z + 1))
					{
						this.setBlockAndNotifyAdequately(world, x, y + l2, z + 1, Blocks.log, this.woodMetadata);

						if (l2 > 0)
						{
							if (rand.nextInt(3) > 0 && world.isAirBlock(x - 1, y + l2, z + 1))
							{
								this.setBlockAndNotifyAdequately(world, x - 1, y + l2, z + 1, Blocks.vine, 8);
							}

							if (rand.nextInt(3) > 0 && world.isAirBlock(x, y + l2, z + 2))
							{
								this.setBlockAndNotifyAdequately(world, x, y + l2, z + 2, Blocks.vine, 4);
							}
						}
					}
				}
			}

			return true;
		}
	}

	private void func_150543_c(World p_150543_1_, int p_150543_2_, int p_150543_3_, int p_150543_4_, int p_150543_5_, Random p_150543_6_)
	{
		byte b0 = 2;

		for (int i1 = p_150543_4_ - b0; i1 <= p_150543_4_; ++i1)
		{
			int j1 = i1 - p_150543_4_;
			this.func_150535_a(p_150543_1_, p_150543_2_, i1, p_150543_3_, p_150543_5_ + 1 - j1, p_150543_6_);
		}
	}
}
