package com.bioxx.tfc.Items.ItemBlocks;

import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.api.Enums.EnumSize;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemPBearCarpet extends ItemTerraBlock
{

	public ItemPBearCarpet(Block b) {
		super(b);
		this.setCreativeTab(TFCTabs.TFC_TOOLS);
	}
	
	@Override
	public EnumSize getSize(ItemStack is) {
		return EnumSize.HUGE;
	}
}
