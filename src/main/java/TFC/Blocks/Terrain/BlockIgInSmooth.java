package TFC.Blocks.Terrain;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.IIcon;
import TFC.API.Constant.Global;

public class BlockIgInSmooth extends BlockSmooth
{
	public BlockIgInSmooth()
	{
		super(Material.rock);
		this.setCreativeTab(CreativeTabs.tabBlock);
		names = Global.STONE_IGIN;
		icons = new IIcon[names.length];
	}
}
