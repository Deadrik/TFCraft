package com.bioxx.tfc.api.Crafting;

import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.OreDictionary;

public class LoomRecipe
{
	public ItemStack inItemStack;
	public ItemStack outItemStack;
	public int inSize;

	public LoomRecipe(ItemStack inputItem, ItemStack outIS)
	{
		this.inItemStack = inputItem;
		this.outItemStack = outIS;
		this.inSize = inputItem.stackSize;
	}

	public Boolean matches(ItemStack item)
	{
		boolean iStack = inItemStack != null && item != null && item.stackSize == inItemStack.stackSize;

		boolean itemsEqual = OreDictionary.itemMatches(inItemStack, item, false);

		return iStack && itemsEqual;
	}

	public Boolean resultMatches(ItemStack item)
	{
		boolean iStack = outItemStack != null && item != null && item.stackSize == outItemStack.stackSize;

		boolean itemsEqual = OreDictionary.itemMatches(outItemStack, item, false);

		return iStack && itemsEqual;
	}
	
	public Boolean partiallyMatches(ItemStack item)
	{
		boolean iStack = inItemStack != null && item != null;

		boolean itemsEqual = OreDictionary.itemMatches(inItemStack, item, false);

		return iStack && itemsEqual;
	}

	public ItemStack getInItem()
	{
		return inItemStack;
	}

	public int getReqSize(){
		return inSize;
	}
	
	public String getRecipeName()
	{
		String s = "";
		if(this.outItemStack != null)
			s=/*outItemStack.stackSize+"x " +*/ outItemStack.getDisplayName();
		return s;
	}

	public ItemStack getResult(ItemStack inIS)
	{
		ItemStack is = null;
		if(outItemStack != null)
		{
			is = outItemStack.copy();
			return is;
		}
		return is;
	}

    public ItemStack getOutItemStack()
    {
        return outItemStack;
    }
}
