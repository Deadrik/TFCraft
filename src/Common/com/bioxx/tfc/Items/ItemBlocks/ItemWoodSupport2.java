package com.bioxx.tfc.Items.ItemBlocks;

import net.minecraft.block.Block;

import com.bioxx.tfc.api.Constant.Global;

public class ItemWoodSupport2 extends ItemWoodSupport
{
	public ItemWoodSupport2(Block par1) 
	{
		super(par1);
		this.hasSubtypes = true;
		this.setMaxDamage(0);
		this.metaNames = new String[Global.WOOD_ALL.length - 16];
		System.arraycopy(Global.WOOD_ALL, 16, metaNames, 0,Global.WOOD_ALL.length - 16);
	}


}
