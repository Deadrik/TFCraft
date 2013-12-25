package TFC.Blocks.Terrain;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import TFC.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSand2 extends TFC.Blocks.Terrain.BlockSand
{
	public BlockSand2(int i)
	{
		super(i);
	}

	@SideOnly(Side.CLIENT)
	@Override
	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for(int i = 0; i < 5; i++) {
			par3List.add(new ItemStack(par1, 1, i));
		}
	}

	/**
	 * Retrieves the block texture to use based on the display side. Args: iBlockAccess, x, y, z, side
	 */
	@Override
	public Icon getBlockTexture(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		return icons[par1IBlockAccess.getBlockMetadata(par2, par3, par4)];
	}

	/**
	 * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	 */
	@Override
	public Icon getIcon(int par1, int par2)
	{
		return icons[par2];
	}

	Icon[] icons = new Icon[5];

	@Override
	public void registerIcons(IconRegister registerer)
	{
		for(int i = 0; i < 5; i++)
		{
			icons[i] = registerer.registerIcon(Reference.ModID + ":" + "sand/Sand"+(i+16));
		}
	}

}
