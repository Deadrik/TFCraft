package TFC.Blocks;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.Icon;
import TFC.API.Constant.Global;

public class BlockPlanks2 extends BlockPlanks
{
	public BlockPlanks2(int i, Material material) 
	{
		super(i, Material.wood);
		this.setCreativeTab(CreativeTabs.tabBlock);
		woodNames = new String[Global.WOOD_ALL.length-16];
		System.arraycopy(Global.WOOD_ALL, 16, woodNames, 0, Global.WOOD_ALL.length-16);
		icons = new Icon[woodNames.length];
		this.setBurnProperties(i, 5, 20);
	}
}
