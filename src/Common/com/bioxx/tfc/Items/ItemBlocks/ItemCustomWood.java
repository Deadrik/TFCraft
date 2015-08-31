package com.bioxx.tfc.Items.ItemBlocks;

import net.minecraft.block.Block;

import com.bioxx.tfc.api.Constant.Global;

public class ItemCustomWood extends ItemTerraBlock
{
	public ItemCustomWood(Block b)
	{
		super(b);
		metaNames = new String[16];
		System.arraycopy(Global.WOOD_ALL, 0, metaNames, 0, 16);
	}
}
