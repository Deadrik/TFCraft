package TFC.Items.ItemBlocks;

import net.minecraft.item.ItemStack;
import TFC.API.ISmeltable;
import TFC.API.Metal;
import TFC.API.Constant.Global;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.API.ISmeltable.EnumTier;

public class ItemAnvil2 extends ItemTerraBlock implements ISmeltable
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
	
	@Override
	public Metal GetMetalType(ItemStack is) 
	{
		int meta = is.getItemDamage();
		switch(meta)
		{
		case 0: return Global.ROSEGOLD;
		case 1: return Global.BISMUTHBRONZE;
		case 2: return Global.BLACKBRONZE;
		default : return Global.UNKNOWN;
		}
	}
	
	@Override
	public short GetMetalReturnAmount(ItemStack is) {

		return 1400;
	}

	@Override
	public boolean isSmeltable(ItemStack is) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public EnumTier GetSmeltTier(ItemStack is) {
		// TODO Auto-generated method stub
		return EnumTier.TierI;
	}
}
