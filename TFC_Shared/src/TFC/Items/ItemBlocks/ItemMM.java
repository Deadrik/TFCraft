package TFC.Items.ItemBlocks;

import TFC.API.Constant.Global;
import net.minecraft.client.renderer.texture.IconRegister;

public class ItemMM extends ItemTerraBlock
{	
	public ItemMM(int i) 
	{
		super(i);
		MetaNames = Global.STONE_MM;
	}
	
	@Override
	public void registerIcons(IconRegister registerer)
    {

    }
}
