package TFC.Blocks.Terrain;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSand2 extends TFC.Blocks.Terrain.BlockSand
{
	public BlockSand2(int i, int texOff)
	{
		super(i, texOff);
		this.setCreativeTab(CreativeTabs.tabBlock);
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

}
