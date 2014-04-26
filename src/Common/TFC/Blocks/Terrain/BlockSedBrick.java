package TFC.Blocks.Terrain;

import net.minecraft.client.renderer.texture.IIconRegister;
import TFC.Reference;

public class BlockSedBrick extends BlockSedSmooth
{
	public BlockSedBrick()
	{
		super();
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		for(int i = 0; i < names.length; i++)
			icons[i] = iconRegisterer.registerIcon(Reference.ModID + ":" + "rocks/"+names[i]+" Brick");
	}
}

