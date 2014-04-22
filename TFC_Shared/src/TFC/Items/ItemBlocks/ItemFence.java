package TFC.Items.ItemBlocks;

import net.minecraft.client.renderer.texture.IconRegister;
import TFC.API.Constant.Global;

public class ItemFence extends ItemTerraBlock
{
	public ItemFence(int i) 
	{
		super(i);
		MetaNames = new String[16];
		System.arraycopy(Global.WOOD_ALL, 0, MetaNames, 0, 16);
	}

	@Override
	public void registerIcons(IconRegister registerer)
	{

	}
}