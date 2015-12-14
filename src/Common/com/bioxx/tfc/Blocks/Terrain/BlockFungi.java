package com.bioxx.tfc.Blocks.Terrain;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockMushroom;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenBigMushroom;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.Constant.Global;

public class BlockFungi extends BlockMushroom
{
	@SideOnly(Side.CLIENT)
	private IIcon[] icons;

	public BlockFungi()
	{
		super();
		float var3 = 0.2F;
		this.setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, var3 * 2.0F, 0.5F + var3);
		this.setTickRandomly(true);
		this.setCreativeTab(TFCTabs.TFC_DECORATION);
	}

	/**
	 * Gets the block's texture. Args: side, meta
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int par1, int par2)
	{
		if (par2 >= this.icons.length) par2 = 0;
		return this.icons[par2];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register)
	{
		this.icons = new IIcon[Global.FUNGI_META_NAMES.length];
		for (int i = 0; i < this.icons.length; ++i)
		{
			// The first 2 mushrooms are from vanilla
			this.icons[i] = register.registerIcon((icons.length > 2 ? Reference.MOD_ID + ":plants/" : "") + Global.FUNGI_META_NAMES[i]);
		}
	}

	/**
	 * Determines the damage on the item the block drops. Used in cloth and wood.
	 */
	@Override
	public int damageDropped(int par1)
	{
		return par1;
	}

	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list)
	{
		for (int i = 0; i < this.icons.length; ++i)
		{
			list.add(new ItemStack(item, 1, i));
		}
	}

	/**
	 * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
	 */
	@Override
	public boolean canBlockStay(World world, int x, int y, int z)
	{
		if (y >= 0 && y < 256)
		{
			Block var5 = world.getBlock(x, y - 1, z);
			return var5 == Blocks.mycelium || world.getFullBlockLightValue(x, y, z) < 13 && this.canThisPlantGrowOnThisBlock(var5);
		}
		else
		{
			return false;
		}
	}

	protected boolean canThisPlantGrowOnThisBlock(Block block)
	{
		return TFC_Core.isSoil(block) || TFC_Core.isFarmland(block);
	}

	/**
	 * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
	 */
	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z)
	{
		Block var5 = world.getBlock(x, y, z);
		return (world.isAirBlock(x, y, z) || var5.getMaterial().isReplaceable())
				&& this.canThisPlantGrowOnThisBlock(var5)
				&& this.canBlockStay(world, x, y, z);
	}

	/**
	 * Fertilize the mushroom.
	 */
	@Override
	public boolean func_149884_c/*fertilizeMushroom*/(World world, int x, int y, int z, Random rnd)
	{
		Block block = world.getBlock(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		WorldGenBigMushroom bigGen = null;

		world.setBlockToAir(x, y, z);

		if (this == block && meta == 0)
			bigGen = new WorldGenBigMushroom(0);
		else if (this == block && meta == 1)
			bigGen = new WorldGenBigMushroom(1);

		if (bigGen != null && bigGen.generate(world, rnd, x, y, z))
		{
			return true;
		}
		else
		{
			world.setBlock(x, y, z, this, meta, 3);
			return false;
		}
	}

	/**
	 * Ticks the block if it's been scheduled
	 */
	@Override
	public void updateTick(World world, int x, int y, int z, Random rnd)
	{
		int meta = world.getBlockMetadata(x, y, z);

		if (rnd.nextInt(25) == 0)
		{
			byte var6 = 4;
			int var7 = 5;
			int i;
			// These variables are used a bit oddly, but that's the way vanilla decided to do it
			int j; // Initially used for z, then switched to y
			int k; // Initially used for y, then switched to z

			for (i = x - var6; i <= x + var6; ++i)
			{
				for (j = z - var6; j <= z + var6; ++j)
				{
					for (k = y - 1; k <= y + 1; ++k)
					{
						if (world.blockExists(i, k, j) && world.getBlock(i, k, j) == this) // Correctly doing x, y, z
						{
							--var7;
							if (var7 <= 0)
								return;
						}
					}
				}
			}

			i = x + rnd.nextInt(3) - 1;
			j = y + rnd.nextInt(2) - rnd.nextInt(2);
			k = z + rnd.nextInt(3) - 1;

			for (int var11 = 0; var11 < 4; ++var11)
			{
				if (world.blockExists(i, j, k) && world.isAirBlock(i, j, k) && this.canBlockStay(world, i, j, k))
				{
					x = i;
					y = j;
					z = k;
				}

				i = x + rnd.nextInt(3) - 1;
				j = y + rnd.nextInt(2) - rnd.nextInt(2);
				k = z + rnd.nextInt(3) - 1;
			}

			if (world.blockExists(i, j, k) && world.isAirBlock(i, j, k) && this.canBlockStay(world, i, j, k))
				world.setBlock(i, j, k, this, meta, 2);
		}
	}

}
