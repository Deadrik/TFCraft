package TFC.Items.ItemBlocks;

import net.minecraft.item.ItemStack;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;

public class ItemAnvil2 extends ItemTerraBlock
{
	public ItemAnvil2(int i) 
	{
		super(i);
		this.MetaNames = new String[]{ "Rose Gold", "Bismuth Bronze", "Black Bronze"};
	}

	@Override
	public EnumSize getSize(ItemStack is) {
		return EnumSize.HUGE;
	}

	@Override
	public EnumWeight getWeight(ItemStack is) {
		return EnumWeight.HEAVY;
	}
}
