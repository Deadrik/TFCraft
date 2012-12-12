package TFC.Blocks;

import java.util.List;

import TFC.*;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;

public class BlockPeat extends BlockTerra2
{

	public BlockPeat(int i, int j)
	{
		super(i, j, Material.ground);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@SideOnly(Side.CLIENT)
    @Override
    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List list)
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
