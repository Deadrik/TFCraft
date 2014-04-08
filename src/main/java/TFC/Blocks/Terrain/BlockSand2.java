package TFC.Blocks.Terrain;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSand2 extends TFC.Blocks.Terrain.BlockSand
{
	IIcon[] icons = new IIcon[5];
	
	public BlockSand2(int texOff)
	{
		super(texOff);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}

	@SideOnly(Side.CLIENT)
	@Override
	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for(int i = 0; i < 5; i++)
			par3List.add(new ItemStack(par1, 1, i));
	}

}
