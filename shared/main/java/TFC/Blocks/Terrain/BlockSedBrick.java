package TFC.Blocks.Terrain;

import net.minecraft.client.renderer.texture.IconRegister;
import TFC.Reference;

public class BlockSedBrick extends BlockSedSmooth
{

	public BlockSedBrick(int i) {
		super(i);
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

