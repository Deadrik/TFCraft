package com.bioxx.tfc.api.Crafting;

import net.minecraft.item.ItemStack;

public class QuernRecipe
{
	private ItemStack inItemStack;
	private ItemStack outItemStack;

	public QuernRecipe(ItemStack inIS, ItemStack outIS)
	{
		this.inItemStack = inIS;
		this.outItemStack = outIS;
	}

	public Boolean isInItem(ItemStack is)
	{
		return is.getItem() == inItemStack.getItem() && is.getItemDamage() == inItemStack.getItemDamage() && is.stackSize >= inItemStack.stackSize;
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
