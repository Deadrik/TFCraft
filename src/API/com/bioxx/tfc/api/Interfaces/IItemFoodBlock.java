package com.bioxx.tfc.api.Interfaces;

import net.minecraft.item.ItemStack;

public interface IItemFoodBlock 
{
	public int getFoodId(ItemStack is);
	
	public int getHealAmount(ItemStack is);
}
