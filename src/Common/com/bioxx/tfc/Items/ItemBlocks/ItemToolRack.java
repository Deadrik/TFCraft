package com.bioxx.tfc.Items.ItemBlocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;

public class ItemToolRack extends ItemTerraBlock
{
	public ItemToolRack(Block par1)
	{
		super(par1);
		metaNames = Global.WOOD_ALL;
	}

	@Override
	public EnumSize getSize(ItemStack is)
	{
		return EnumSize.HUGE;
	}

	@Override
	public EnumWeight getWeight(ItemStack is)
	{
		return EnumWeight.LIGHT;
	}
}
