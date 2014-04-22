package TFC.Items.ItemBlocks;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;
import TFC.API.Constant.Global;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;

public class ItemArmourStand2 extends ItemTerraBlock
{
	public ItemArmourStand2(int i) 
	{
		super(i);
		MetaNames = new String[Global.WOOD_ALL.length - 16];
		System.arraycopy(Global.WOOD_ALL, 16, MetaNames, 0, Global.WOOD_ALL.length - 16);
	}

	@Override
	public void registerIcons(IconRegister registerer)
	{

	}

	@Override
	public EnumSize getSize(ItemStack is) {
		return EnumSize.VERYLARGE;
	}

	@Override
	public EnumWeight getWeight(ItemStack is) {
		return EnumWeight.HEAVY;
	}
}