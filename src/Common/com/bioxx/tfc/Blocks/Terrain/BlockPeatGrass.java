package com.bioxx.tfc.Blocks.Terrain;

import java.util.Random;

import com.bioxx.tfc.TFCBlocks;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class BlockPeatGrass extends BlockGrass
{
	public BlockPeatGrass()
	{
		super();
	}

	/**
	 * Returns the ID of the items to drop on destruction.
	 */
	@Override
	public Item getItemDropped(int par1, Random par2Random, int par3)
	{
		return Item.getItemFromBlock(TFCBlocks.Peat);
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
	public int quantityDropped(Random par1Random)
	{
		return 1;
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block l)
	{
		if(!world.blockExists(i, j-1, k))
		{
			int meta = world.getBlockMetadata(i, j, k);
			world.setBlock(i, j, k, TFCBlocks.Peat, meta, 2);
		}
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random rand)
	{
		if (world.getBlockLightValue(i, j + 1, k) < 4 && world.getBlock(i, j + 1, k).getLightOpacity() > 2)
			world.setBlock(i, j, k, TFCBlocks.Peat);
	}
}
