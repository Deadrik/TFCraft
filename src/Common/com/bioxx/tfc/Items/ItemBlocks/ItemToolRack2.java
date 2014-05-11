package com.bioxx.tfc.Items.ItemBlocks;

import net.minecraft.block.Block;

import com.bioxx.tfc.api.Constant.Global;

public class ItemToolRack2 extends ItemToolRack
{
	public ItemToolRack2(Block par1)
	{
		super(par1);
		MetaNames = new String[Global.WOOD_ALL.length - 16];
		System.arraycopy(Global.WOOD_ALL, 16, MetaNames, 0, Global.WOOD_ALL.length - 16);
	}
}
