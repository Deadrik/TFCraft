package TFC.Blocks.Vanilla;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import TFC.TFCBlocks;
import TFC.WorldGen.TFCBiome;

public class BlockCustomStationary extends BlockCustomLiquid
{
	public BlockCustomStationary(Material par2Material)
	{
		super(par2Material);
		this.setTickRandomly(false);

		if (par2Material == Material.lava)
			this.setTickRandomly(true);
	}

	@Override
	public void registerBlockIcons(IIconRegister par1IconRegister)
	{
		super.registerBlockIcons(par1IconRegister);
		TFCBlocks.SaltWaterFlowing.registerBlockIcons(par1IconRegister);
		Blocks.flowing_lava.registerBlockIcons(par1IconRegister);
	}

	@Override
	public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		return this.blockMaterial != Material.lava;
	}

	/**
	 * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
	 * their own) Args: x, y, z, neighbor blockID
	 */
	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, Block par5)
	{
		super.onNeighborBlockChange(par1World, par2, par3, par4, par5);

		if (par1World.getBlock(par2, par3, par4) == this)
			this.setNotStationary(par1World, par2, par3, par4);
	}

	/**
	 * Changes the block ID to that of an updating fluid.
	 */
	protected void setNotStationary(World world, int i, int j, int k)
	{
		int m = world.getBlockMetadata(i, j, k);

		Block b = Blocks.flowing_lava;
		if (this == TFCBlocks.FreshWaterStill) b = TFCBlocks.FreshWaterFlowing;
		if (this == TFCBlocks.HotWaterStill) b = TFCBlocks.HotWaterFlowing;
		if (this == TFCBlocks.SaltWaterStill) b = TFCBlocks.SaltWaterFlowing;

		world.setBlock(i, j, k, b, m, 0x2);
		world.scheduleBlockUpdate(i, j, k, b, this.tickRate(world));
	}

	/**
	 * Ticks the block if it's been scheduled
	 */
	@Override
	public void updateTick(World world, int i, int j, int k, Random rand)
	{
		super.updateTick(world, i, j, k, rand);
		if (this.blockMaterial == Material.lava)
		{
			int var6 = rand.nextInt(3);
			int var7;
			int var8;
			Block block;

			for (var7 = 0; var7 < var6; ++var7)
			{
				i += rand.nextInt(3) - 1;
				++j;
				k += rand.nextInt(3) - 1;
				block = world.getBlock(i, j, k);

				if (block == Blocks.air)
				{
					if (this.isFlammable(world, i - 1, j, k) ||
						this.isFlammable(world, i + 1, j, k) ||
						this.isFlammable(world, i, j, k - 1) ||
						this.isFlammable(world, i, j, k + 1) ||
						this.isFlammable(world, i, j - 1, k) ||
						this.isFlammable(world, i, j + 1, k))
					{
						world.setBlock(i, j, k, Blocks.fire);
						return;
					}
				}
				else if (block.getMaterial().blocksMovement())
					return;
			}

			if (var6 == 0)
			{
				var7 = i;
				var8 = k;
				for (int var9 = 0; var9 < 3; ++var9)
				{
					i = var7 + rand.nextInt(3) - 1;
					k = var8 + rand.nextInt(3) - 1;
					if (world.isAirBlock(i, j + 1, k) && this.isFlammable(world, i, j, k))
						world.setBlock(i, j + 1, k, Blocks.fire);
				}
			}
		}
		else if(this == TFCBlocks.FreshWaterStill)
		{
			if(j == 144 && scanForOcean(world, i, j, k))
				world.setBlock(i, j, k, TFCBlocks.SaltWaterStill);
		}
	}
	
	private boolean scanForOcean(World world, int i, int j, int k)
	{
		if(world.getBiomeGenForCoords(i + 5, k).biomeID == TFCBiome.ocean.biomeID ||
				world.getBiomeGenForCoords(i + 10, k).biomeID == TFCBiome.ocean.biomeID || 
				world.getBiomeGenForCoords(i + 20, k).biomeID == TFCBiome.ocean.biomeID || 
				world.getBiomeGenForCoords(i - 5, k).biomeID == TFCBiome.ocean.biomeID ||
				world.getBiomeGenForCoords(i - 10, k).biomeID == TFCBiome.ocean.biomeID || 
				world.getBiomeGenForCoords(i - 20, k).biomeID == TFCBiome.ocean.biomeID || 
				world.getBiomeGenForCoords(i, k + 5).biomeID == TFCBiome.ocean.biomeID ||
				world.getBiomeGenForCoords(i, k + 10).biomeID == TFCBiome.ocean.biomeID || 
				world.getBiomeGenForCoords(i, k + 20).biomeID == TFCBiome.ocean.biomeID|| 
				world.getBiomeGenForCoords(i, k - 5).biomeID == TFCBiome.ocean.biomeID ||
				world.getBiomeGenForCoords(i, k - 10).biomeID == TFCBiome.ocean.biomeID || 
				world.getBiomeGenForCoords(i, k - 20).biomeID == TFCBiome.ocean.biomeID)
		{
			return true;
		}
		return false;
	}

	/**
	 * Checks to see if the block is flammable.
	 */
	private boolean isFlammable(World par1World, int par2, int par3, int par4)
	{
		return par1World.getBlock(par2, par3, par4).getMaterial().getCanBurn();
	}
}
