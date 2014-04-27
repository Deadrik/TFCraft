package com.bioxx.tfc.Items.ItemBlocks;

import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Enums.EnumSize;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemWoodSupport2 extends ItemTerraBlock
{
	public ItemWoodSupport2(Block par1) 
	{
		super(par1);
		this.hasSubtypes = true;
		this.setMaxDamage(0);
		this.MetaNames = new String[Global.WOOD_ALL.length - 16];
		System.arraycopy(Global.WOOD_ALL, 16, MetaNames, 0,Global.WOOD_ALL.length - 16);
	}

	@Override
	public EnumSize getSize(ItemStack is) {
		return EnumSize.MEDIUM;
	}
}
