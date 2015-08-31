package com.bioxx.tfc.Items.ItemBlocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import com.bioxx.tfc.api.Metal;
import com.bioxx.tfc.api.Constant.Global;

public class ItemAnvil1 extends ItemAnvil
{
	public ItemAnvil1(Block par1) 
	{
		super(par1);
		this.metaNames = new String[]{"Stone", "Copper", "Bronze", "Wrought Iron", "Steel", "Black Steel", "Blue Steel", "Red Steel"};
	}

	@Override
	public Metal getMetalType(ItemStack is) 
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
