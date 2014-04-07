package TFC.Blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import TFC.Reference;
public class BlockFireBrick extends BlockTerra
{
	public BlockFireBrick(int i)
	{
		super(i, Material.rock);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}


	@Override
	public void registerIcons(IconRegister iconRegisterer)
	{
		this.blockIcon = iconRegisterer.registerIcon(Reference.ModID + ":" + "rocks/Fire Brick");
	}
}
