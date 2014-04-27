package com.bioxx.tfc.Items.ItemBlocks;

import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Enums.EnumSize;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemWoodSupport extends ItemTerraBlock
{
	public ItemWoodSupport(Block par1) 
	{
		super(par1);
		this.hasSubtypes = true;
		this.setMaxDamage(0);
		this.MetaNames = new String[16];
		System.arraycopy(Global.WOOD_ALL, 0, MetaNames, 0, 16);
	}

	@Override
	public EnumSize getSize(ItemStack is) {
		return EnumSize.MEDIUM;
	}
}
