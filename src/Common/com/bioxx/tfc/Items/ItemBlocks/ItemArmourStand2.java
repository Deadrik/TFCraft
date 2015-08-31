package com.bioxx.tfc.Items.ItemBlocks;

import net.minecraft.block.Block;

import com.bioxx.tfc.api.Constant.Global;

public class ItemArmourStand2 extends ItemArmourStand
{
	public ItemArmourStand2(Block par1) 
	{
		super(par1);
		metaNames = new String[Global.WOOD_ALL.length - 16];
		System.arraycopy(Global.WOOD_ALL, 16, metaNames, 0, Global.WOOD_ALL.length - 16);
	}

	@Override
	protected int getOffset()
	{
		return 16;
	}
}