package com.bioxx.tfc.api.Crafting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;

public class AnvilManager
{
	private static final AnvilManager instance = new AnvilManager();
	public static final AnvilManager getInstance()
	{
		return instance;
	}

	private List recipes;
	private List recipesWeld;
	private Map plans;

	private AnvilManager()
	{
		recipes = new ArrayList();
		recipesWeld = new ArrayList();
		plans = new HashMap<String, PlanRecipe>();
	}

	public void addRecipe(AnvilRecipe recipe)
	{
		recipes.add(recipe);
	}

	public void addWeldRecipe(AnvilRecipe recipe)
	{
		recipe.flux = true;
		recipesWeld.add(recipe);
	}

	/**
	 * Adds a name for a plan type to the plans list. If it already exists then it will not be added. All types are not case sensative as it
	 * autoconverts to lowercase when adding to prevent bugs due to case.
	 */
	public void addPlan(String s, PlanRecipe r)
	{
		s = s.toLowerCase();
		if(!plans.containsKey(s))
			plans.put(s, r);
	}

	public PlanRecipe getPlan(String s)
	{
		return (PlanRecipe) plans.get(s);
	}

	public AnvilRecipe findMatchingRecipe(AnvilRecipe recipe)
	{
		for (int k = 0; k < recipes.size(); k++)
		{
			AnvilRecipe irecipe = (AnvilRecipe)recipes.get(k);
			if (irecipe.matches(recipe))
				return irecipe;
		}

		return null;
	}

	public AnvilRecipe findMatchingWeldRecipe(AnvilRecipe recipe)
	{
		for (int k = 0; k < recipesWeld.size(); k++)
		{
			AnvilRecipe irecipe = (AnvilRecipe)recipesWeld.get(k);
			if (irecipe.matches(recipe))
				return irecipe;
		}

		return null;
	}

	public Object[] findCompleteRecipe(AnvilRecipe recipe, int[] rules)
	{
		for (int k = 0; k < recipes.size(); k++)
		{
			AnvilRecipe irecipe = (AnvilRecipe)recipes.get(k);
			if (irecipe.isComplete(instance, recipe, rules))
				return new Object[] {irecipe, irecipe.getCraftingResult(recipe.input1)};
		}

		return null;
	}

	public ItemStack findCompleteWeldRecipe(AnvilRecipe recipe)
	{
		for (int k = 0; k < recipesWeld.size(); k++)
		{
			AnvilRecipe irecipe = (AnvilRecipe)recipesWeld.get(k);
			if (irecipe.matches(recipe))
				return irecipe.getCraftingResult(recipe.input1);
		}

		return null;
	}

	public List getRecipeList()
	{
		return recipes;
	}
	public List getWeldRecipeList()
	{
		return recipesWeld;
	}
	public Map getPlans()
	{
		return plans;
	}
}
