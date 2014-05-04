package com.bioxx.tfc.api.Crafting;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;

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
	}

	public void addValidItem(Item item)
	{
		validItems.add(item);
	}

	public Boolean isValidItem(Item item)
	{
		return validItems.contains(item);
	}

	public QuernRecipe findMatchingRecipe(Item item, int dmg)
	{
		for(Object recipe : recipes)
		{
			QuernRecipe qr = (QuernRecipe) recipe;
			if(qr.isInItem(item) && qr.getInItemDmg() == dmg)
				return qr;
		}
		return null;
	}
}
