package com.bioxx.tfc.api.Crafting;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

public class QuernManager
{
	private static final QuernManager INSTANCE = new QuernManager();
	public static final QuernManager getInstance()
	{
		return INSTANCE;
	}

	private List<QuernRecipe> recipes;
	private List<ItemStack> validItems;

	private QuernManager()
	{
		recipes = new ArrayList<QuernRecipe>();
		validItems = new ArrayList<ItemStack>();
	}

	public void addRecipe(QuernRecipe recipe)
	{
		recipes.add(recipe);
		validItems.add(recipe.getInItem());
	}

	public Boolean isValidItem(ItemStack is)
	{
		for(Object vi : validItems)
		{
			ItemStack vis = (ItemStack) vi;
			if(vis.getItem() == is.getItem() && vis.getItemDamage() == is.getItemDamage())
				return true;
		}
		return false;
	}

	public QuernRecipe findMatchingRecipe(ItemStack is)
	{
		for(Object recipe : recipes)
		{
			QuernRecipe qr = (QuernRecipe) recipe;
			if(qr.isInItem(is))
				return qr;
		}
		return null;
	}

	public List<QuernRecipe> getRecipes()
    {
        return recipes;
    }

	public List<ItemStack> getValidItems()
    {
        return validItems;
    }
}
