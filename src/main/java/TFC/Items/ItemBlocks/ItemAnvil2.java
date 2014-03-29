package TFC.Items.ItemBlocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import TFC.API.Metal;
import TFC.API.Constant.Global;

public class ItemAnvil2 extends ItemAnvil
{
	public ItemAnvil2(Block par1) 
	{
		super(par1);
		this.MetaNames = new String[]{ "Rose Gold", "Bismuth Bronze", "Black Bronze"};
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
}
