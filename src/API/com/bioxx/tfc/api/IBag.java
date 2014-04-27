package com.bioxx.tfc.api;

import net.minecraft.item.ItemStack;

public interface IBag
{
	public abstract ItemStack[] loadBagInventory(ItemStack is);
}
