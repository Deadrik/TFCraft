package com.bioxx.tfc.Items.ItemBlocks;

import net.minecraft.block.Block;

import com.bioxx.tfc.api.Constant.Global;

public class ItemCustomWood2 extends ItemCustomWood
{
	public ItemCustomWood2(Block b)
	{
		super(b);
		MetaNames = new String[Global.WOOD_ALL.length - 16];
		System.arraycopy(Global.WOOD_ALL, 16, MetaNames, 0, Global.WOOD_ALL.length - 16);
	}
}
