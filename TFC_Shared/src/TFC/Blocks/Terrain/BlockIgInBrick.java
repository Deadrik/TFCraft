package TFC.Blocks.Terrain;

import net.minecraft.client.renderer.texture.IconRegister;
import TFC.Reference;


public class BlockIgInBrick extends BlockIgInSmooth
{
	
	public BlockIgInBrick(int i) {
		super(i);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void registerIcons(IconRegister iconRegisterer)
	{
		for(int i = 0; i < names.length; i++)
		{
			icons[i] = iconRegisterer.registerIcon(Reference.ModID + ":" + "rocks/"+names[i]+" Brick");
		}
	}
}
