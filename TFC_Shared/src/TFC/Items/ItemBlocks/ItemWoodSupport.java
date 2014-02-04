package TFC.Items.ItemBlocks;

import net.minecraft.item.ItemStack;
import TFC.API.Constant.Global;
import TFC.API.Enums.EnumSize;

public class ItemWoodSupport extends ItemTerraBlock
{
	public ItemWoodSupport(int i) 
	{
		super(i);
		this.hasSubtypes = true;
		this.setMaxDamage(0);
		this.MetaNames = new String[16];
		System.arraycopy(Global.WOOD_ALL, 0, MetaNames, 0, 16);
	}

	@Override
	public EnumSize getSize(ItemStack is) {
		return EnumSize.MEDIUM;
	}
}
