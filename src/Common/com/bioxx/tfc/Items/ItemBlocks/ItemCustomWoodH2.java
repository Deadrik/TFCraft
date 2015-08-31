package com.bioxx.tfc.Items.ItemBlocks;

import net.minecraft.block.Block;

import com.bioxx.tfc.api.Constant.Global;

public class ItemCustomWoodH2 extends ItemTerraBlock
{
	public ItemCustomWoodH2(Block b)
	{
		super(b);
		metaNames = new String[16];
		System.arraycopy(Global.WOOD_ALL, 8, metaNames, 0, 8);
		System.arraycopy(Global.WOOD_ALL, 8, metaNames, 8, 8);
	}
}
