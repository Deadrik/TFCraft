package TFC.Blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import TFC.Reference;

public class BlockFireBrick extends BlockTerra
{
	public BlockFireBrick()
	{
		super(Material.rock);
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		this.blockIcon = iconRegisterer.registerIcon(Reference.ModID + ":" + "rocks/Fire Brick");
	}
}
