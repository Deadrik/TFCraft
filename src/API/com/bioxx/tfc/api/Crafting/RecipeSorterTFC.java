package com.bioxx.tfc.api.Crafting;

import java.util.Comparator;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;

public class RecipeSorterTFC implements Comparator
{
	public final CraftingManagerTFC craftingManager;

	RecipeSorterTFC(CraftingManagerTFC craftingmanager)
	{
		craftingManager = craftingmanager;
	}

	@Override
	public int compare(Object obj, Object obj1)
	{
		return compareRecipes((IRecipe)obj, (IRecipe)obj1);
	}

	public int compareRecipes(IRecipe irecipe, IRecipe irecipe1)
	{
		if (irecipe instanceof ShapelessRecipes && irecipe1 instanceof ShapedRecipes)
		{
			return 1;
		}
		if (irecipe1 instanceof ShapelessRecipes && irecipe instanceof ShapedRecipes)
		{
			return -1;
		}
		if (irecipe1.getRecipeSize() < irecipe.getRecipeSize())
		{
			return -1;
		}
		return irecipe1.getRecipeSize() <= irecipe.getRecipeSize() ? 0 : 1;
	}
}
