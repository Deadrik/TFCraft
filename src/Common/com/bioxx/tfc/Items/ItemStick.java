package com.bioxx.tfc.Items;

import net.minecraft.item.ItemStack;

import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;

public class ItemStick extends ItemTerra
{
	public ItemStick()
	{
		super();
		setMaxDamage(0);
		setHasSubtypes(true);
		this.setCreativeTab(TFCTabs.TFC_MATERIALS);
	}

	@Override
	public int getMetadata(int i)
	{
		return i;
	}

	@Override
	public EnumSize getSize(ItemStack is) 
	{
		return EnumSize.TINY;
	}
	@Override
	public EnumWeight getWeight(ItemStack is) 
	{
		return EnumWeight.LIGHT;
	}
}
