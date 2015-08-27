package com.bioxx.tfc.api.Crafting;

import net.minecraft.item.ItemStack;

public class KilnRecipe
{
	public ItemStack result;
	public ItemStack input1;
	public int KilnLevel;
	public boolean inheritsTag = true;

	public KilnRecipe(ItemStack in, int kl, ItemStack res)
	{
		this.input1 = in;
		this.result = res;
		this.KilnLevel = kl;
	}

	public KilnRecipe(ItemStack in, int kl)
	{
		this.input1 = in;
		this.KilnLevel = kl;
	}

	/**
	 * Used to check if a recipe matches current crafting inventory
	 */    
	public boolean matches(KilnRecipe A)
	{   
		return areItemStacksEqual(input1, A.input1) && A.KilnLevel == this.KilnLevel;
	}

	public boolean isComplete(KilnRecipe A)
	{
		return areItemStacksEqual(input1, A.input1) && A.KilnLevel == this.KilnLevel;
	}

	public boolean getInheritsTag()
	{
		return this.inheritsTag;
	}

	private boolean areItemStacksEqual(ItemStack is1, ItemStack is2)
	{
		if (is1 != null && is2 != null)
		{
			if (is1.getItem() != is2.getItem())
				return false;

			if (is1.getItemDamage() != -1 && is1.getItemDamage() != is2.getItemDamage())
				return false;
		}
		else if (is1 == null && is2 != null || is1 != null && is2 == null) // XOR, if both are null return true
			return false;

		return true;
	}

	/**
	 * Returns an Item that is the result of this recipe
	 */
	public ItemStack getCraftingResult()
	{
		return result;
	}

	public int getKilnLevel()
	{
		return KilnLevel;
	}

	public ItemStack getInput1()
	{
		return input1;
	}
}


