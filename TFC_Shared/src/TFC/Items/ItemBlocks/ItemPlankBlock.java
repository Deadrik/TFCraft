package TFC.Items.ItemBlocks;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;
import TFC.API.Constant.Global;
import TFC.API.Enums.EnumSize;

public class ItemPlankBlock extends ItemTerraBlock
{
	public ItemPlankBlock(int i) 
	{
		super(i);
		MetaNames = Global.WOOD_ALL;
	}

	@Override
	public void registerIcons(IconRegister registerer)
	{

	}

	@Override
	public EnumSize getSize(ItemStack is) {
		return EnumSize.MEDIUM;
	}
}
