package net.minecraft.src.TFC_Core;

import net.minecraft.src.*;

public class BlockTerraPeat extends BlockTerra2
{

	public BlockTerraPeat(int i, int j)
	{
		super(i, j, Material.ground);
	}
	public void addCreativeItems(java.util.ArrayList list)
	{
		list.add(new ItemStack(this,1,0));
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
}
