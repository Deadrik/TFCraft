package com.bioxx.tfc.api.Interfaces;

import net.minecraft.item.ItemStack;

public interface IItemFoodBlock 
{
	int getFoodId(ItemStack is);
	
	int getHealAmount(ItemStack is);
}
