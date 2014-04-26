package TFC.Blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.IIcon;
import TFC.Reference;
import TFC.API.Constant.Global;

public class BlockWoodSupport2 extends BlockWoodSupport
{
	public BlockWoodSupport2(Material material)
	{
		super(material);
		this.setCreativeTab(CreativeTabs.tabBlock);
		woodNames = new String[Global.WOOD_ALL.length-16];
		System.arraycopy(Global.WOOD_ALL, 16, woodNames, 0, Global.WOOD_ALL.length-16);
		icons = new IIcon[woodNames.length];
	}

	@Override
	public void registerBlockIcons(IIconRegister registerer)
	{
		for(int i = 0; i < woodNames.length; i++)
		{
			icons[i] = registerer.registerIcon(Reference.ModID + ":wood/WoodSheet/WoodSheet" + (i + 16));
		}
	}
}
