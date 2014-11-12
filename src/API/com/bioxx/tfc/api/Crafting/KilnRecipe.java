package com.bioxx.tfc.api.Crafting;

import net.minecraft.item.ItemStack;

public class KilnRecipe
{
	ItemStack result;
	ItemStack input1;
	int KilnLevel;

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
		if(areItemStacksEqual(input1, A.input1) && A.KilnLevel == this.KilnLevel)
		{
			return true;
		}
		return false;
	}

	public boolean isComplete(KilnRecipe A)
	{
		if(areItemStacksEqual(input1, A.input1) && A.KilnLevel == this.KilnLevel)
		{
			return true;
		}
		return false;
	}

	private boolean areItemStacksEqual(ItemStack is1, ItemStack is2)
	{
		if(is1 == null && is2 == null)
			return true;

		if((is1 == null && is2 != null) || (is1 != null && is2 == null)) 
			return false;

		if(is1.getItem() != is2.getItem())
			return false;

		if(is1.getItemDamage() != -1 && is1.getItemDamage() != is2.getItemDamage())
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


