package com.bioxx.tfc.Items.ItemBlocks;

import net.minecraft.block.Block;

import com.bioxx.tfc.api.Constant.Global;

public class ItemCustomWoodH extends ItemTerraBlock
{
	public ItemCustomWoodH(Block b)
	{
		super(b);
		MetaNames = new String[16];
		System.arraycopy(Global.WOOD_ALL, 0, MetaNames, 0, 8);
		System.arraycopy(Global.WOOD_ALL, 0, MetaNames, 8, 8);
	}
}
