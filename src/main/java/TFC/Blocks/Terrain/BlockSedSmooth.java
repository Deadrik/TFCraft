package TFC.Blocks.Terrain;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.IIcon;
import TFC.API.Constant.Global;

public class BlockSedSmooth extends BlockSmooth
{
	public BlockSedSmooth()
	{
		super(Material.rock);
		this.setCreativeTab(CreativeTabs.tabBlock);
		names = Global.STONE_SED;
		icons = new IIcon[names.length];
	}
}