package com.bioxx.tfc.Items.ItemBlocks;

import com.bioxx.tfc.api.Metal;
import com.bioxx.tfc.api.Constant.Global;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

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
