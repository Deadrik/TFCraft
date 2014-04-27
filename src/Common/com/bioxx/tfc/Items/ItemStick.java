package com.bioxx.tfc.Items;

import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class ItemStick extends ItemTerra
{
	public ItemStick()
	{
		super();
		setMaxDamage(0);
		setHasSubtypes(true);
		this.setCreativeTab(CreativeTabs.tabMaterials);
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
