package TFC.Items.ItemBlocks;

import TFC.API.Constant.Global;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;

public class ItemCustomLeaves extends ItemTerraBlock
{
	public ItemCustomLeaves(int i)
	{
		super(i);
		setMaxDamage(0);
		setHasSubtypes(true);
		MetaNames = Global.WOOD_ALL;
	}
	
	@Override
	public void registerIcons(IconRegister registerer)
    {

    }
}
