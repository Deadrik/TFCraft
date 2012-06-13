package net.minecraft.src.TFC_Core.Blocks;

import java.util.Random;

import net.minecraft.src.*;

public class BlockTerraClay2 extends BlockTerra2
{
	public BlockTerraClay2(int par1, int par2)
	{
		super(par1, par2, Material.clay);
	}

	public void addCreativeItems(java.util.ArrayList list)
	{
		for(int i = 0; i < 7; i++) {
			list.add(new ItemStack(this,1,i));
		}
	}
	/**
	 * Retrieves the block texture to use based on the display side. Args: iBlockAccess, x, y, z, side
	 */
	public int getBlockTexture(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		return this.blockIndexInTexture + par1IBlockAccess.getBlockMetadata(par2, par3, par4);
	}

	/**
	 * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	 */
	public int getBlockTextureFromSideAndMetadata(int par1, int par2)
	{
		return this.blockIndexInTexture + par2;
	}

	/**
	 * Returns the ID of the items to drop on destruction.
	 */
	public int idDropped(int par1, Random par2Random, int par3)
	{
		return Item.clay.shiftedIndex;
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	public int quantityDropped(Random par1Random)
	{
		return par1Random.nextInt(4);
	}

}
