package com.bioxx.tfc.Blocks.Terrain;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.api.TFCBlocks;

public class BlockPeatGrass extends BlockGrass
{
	public BlockPeatGrass()
	{
		super();
	}

	@SideOnly(Side.CLIENT)
	@Override
	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	public void getSubBlocks(Item item, CreativeTabs tabs, List list)
	{
		list.add(new ItemStack(this, 1, 0));
	}

	/**
	 * Returns the ID of the items to drop on destruction.
	 */
	@Override
	public Item getItemDropped(int metadata, Random rand, int fortune)
	{
		return Item.getItemFromBlock(TFCBlocks.peat);
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.peatGrassRenderId;
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	@Override
	public int quantityDropped(Random rand)
	{
		return 1;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block b)
	{
		if(!world.blockExists(x, y-1, z))
		{
			int meta = world.getBlockMetadata(x, y, z);
			world.setBlock(x, y, z, TFCBlocks.peat, meta, 2);
		}
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand)
	{
		if (world.getBlock(x, y + 1, z).isSideSolid(world, x, y + 1, z, ForgeDirection.DOWN))
			world.setBlock(x, y, z, TFCBlocks.peat);
		else if (world.canBlockSeeTheSky(x, y + 1, z))
		{
			spreadGrass(world, x, y, z, rand);
		}

		world.markBlockForUpdate(x, y, z);
	}
}
