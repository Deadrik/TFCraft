package com.bioxx.tfc.Items.Tools;

import net.minecraft.item.ItemStack;

import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.api.Enums.EnumItemReach;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class ItemSpindle extends ItemTerra
{
	public ItemSpindle()
	{
		super();
		this.setMaxDamage(40);
		this.setFolder("tools/");
		setNoRepair();

		this.setSize(EnumSize.VERYSMALL);
	}

	@Override
	public Multimap getItemAttributeModifiers()
	{
		return HashMultimap.create();
	}
	
	@Override
	public EnumItemReach getReach(ItemStack is){
		return EnumItemReach.SHORT;
	}

	@Override
	public int getItemStackLimit()
	{
		return 1;
	}

	@Override
	public boolean canStack()
	{
		return false;
	}
}