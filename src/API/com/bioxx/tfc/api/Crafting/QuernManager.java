package com.bioxx.tfc.api.Crafting;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class QuernManager
{
	private static final QuernManager instance = new QuernManager();
	public static final QuernManager getInstance()
	{
		return instance;
	}

	private List recipes;
	private List validItems;

	private QuernManager()
	{
		recipes = new ArrayList();
		validItems = new ArrayList();
	}

	public void addRecipe(QuernRecipe recipe)
	{
		recipes.add(recipe);
		validItems.add(recipe.getInItem());
	}

	public Boolean isValidItem(Item item)
	{
		return validItems.contains(item);
	}

	public QuernRecipe findMatchingRecipe(ItemStack item)
	{
		for(Object recipe : recipes)
		{
			QuernRecipe qr = (QuernRecipe) recipe;
			if(qr.isInItem(item))
				return qr;
		}
		return null;
	}
}
