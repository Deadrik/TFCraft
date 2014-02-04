package TFC.Items.ItemBlocks;

import net.minecraft.item.ItemStack;
import TFC.API.Constant.Global;
import TFC.API.Enums.EnumSize;

public class ItemWoodSupport2 extends ItemTerraBlock
{
	public ItemWoodSupport2(int i) 
	{
		super(i);
		this.hasSubtypes = true;
		this.setMaxDamage(0);
		this.MetaNames = new String[Global.WOOD_ALL.length - 16];
		System.arraycopy(Global.WOOD_ALL, 16, MetaNames, 0,Global.WOOD_ALL.length - 16);
	}

	@Override
	public EnumSize getSize(ItemStack is) {
		return EnumSize.MEDIUM;
	}
}
