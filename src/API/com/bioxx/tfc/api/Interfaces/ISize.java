package com.bioxx.tfc.api.Interfaces;

import net.minecraft.item.ItemStack;

import com.bioxx.tfc.api.Enums.EnumItemReach;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;

public interface ISize 
{
	EnumSize getSize(ItemStack is);

	EnumWeight getWeight(ItemStack is);
	
	EnumItemReach getReach(ItemStack is);

	/**
	 * Allows setting weather or not this item can stack regardless of the size/weight values
	 * @return Can stacksize exceed 1
	 */
	boolean canStack();
}
