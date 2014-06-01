package com.bioxx.tfc.api.Crafting;

import net.minecraft.item.ItemStack;

public class QuernRecipe
{
	private ItemStack inItemStack;
	private ItemStack outItemStack;

	public QuernRecipe(ItemStack inputItem, ItemStack outIS)
	{
		this.inItemStack = inputItem;
		this.outItemStack = outIS;
	}

	public Boolean isInItem(ItemStack item)
	{
		return ItemStack.areItemStacksEqual(inItemStack, item);
	}

	public ItemStack getInItem()
	{
		return inItemStack;
	}

	public ItemStack getResult()
	{
		return outItemStack;
	}
}
