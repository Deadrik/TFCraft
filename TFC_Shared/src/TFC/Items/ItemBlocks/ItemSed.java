package TFC.Items.ItemBlocks;

import TFC.API.Constant.Global;
import net.minecraft.client.renderer.texture.IconRegister;

public class ItemSed extends ItemTerraBlock
{
	public ItemSed(int i) 
	{
		super(i);
		MetaNames = Global.STONE_SED;
	}
	@Override
	public void registerIcons(IconRegister registerer)
    {

    }
}
