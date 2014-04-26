package TFC.Blocks.Terrain;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.IIcon;
import TFC.API.Constant.Global;

public class BlockIgExSmooth extends BlockSmooth
{
	public BlockIgExSmooth()
	{
		super(Material.rock);
		this.setCreativeTab(CreativeTabs.tabBlock);
		names = Global.STONE_IGEX;
		icons = new IIcon[names.length];
	}
}
