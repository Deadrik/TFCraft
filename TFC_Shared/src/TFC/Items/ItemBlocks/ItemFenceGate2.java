package TFC.Items.ItemBlocks;

import net.minecraft.client.renderer.texture.IconRegister;
import TFC.API.Constant.Global;

public class ItemFenceGate2 extends ItemTerraBlock
{
	public ItemFenceGate2(int i) 
	{
		super(i);
		MetaNames = new String[Global.WOOD_ALL.length - 16];
		System.arraycopy(Global.WOOD_ALL, 16, MetaNames, 0, Global.WOOD_ALL.length - 16);
	}

	@Override
	public void registerIcons(IconRegister registerer)
	{

	}
}