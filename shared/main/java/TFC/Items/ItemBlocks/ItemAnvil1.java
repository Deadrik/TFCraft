package TFC.Items.ItemBlocks;

import net.minecraft.item.ItemStack;
import TFC.API.ISmeltable;
import TFC.API.Metal;
import TFC.API.Constant.Global;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;

public class ItemAnvil1 extends ItemAnvil
{
	public ItemAnvil1(int i) 
	{
		super(i);
		this.MetaNames = new String[]{"Stone", "Copper", "Bronze", "Wrought Iron", "Steel", "Black Steel", "Blue Steel", "Red Steel"};
	}

	@Override
	public Metal GetMetalType(ItemStack is) 
	{
		int meta = is.getItemDamage();
		switch(meta)
		{
		case 1: return Global.COPPER;
		case 2: return Global.BRONZE;
		case 3: return Global.WROUGHTIRON;
		case 4: return Global.STEEL;
		case 5: return Global.BLACKSTEEL;
		case 6: return Global.BLUESTEEL;
		case 7: return Global.REDSTEEL;
		default : return Global.UNKNOWN;
		}
	}
}
