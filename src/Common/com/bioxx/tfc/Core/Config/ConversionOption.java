package com.bioxx.tfc.Core.Config;

import net.minecraft.item.crafting.IRecipe;

import com.bioxx.tfc.api.TFCCrafting;
import com.google.common.collect.ImmutableList;

/**
 * Used to represent the "conversion" option
 * When the value (config or from server, depending on context) is true the recipes are (re)added to the recipe list.
 * Also keeps the static values from the TFCCrafting class in sync with the actual status of things.
 * @author Dries007
 */
public class ConversionOption extends SyncingOption
{
	public final ImmutableList<IRecipe> recipes;

	public ConversionOption(String name, IRecipe... shapelessRecipes) throws NoSuchFieldException, IllegalAccessException
	{
		super(name, TFCCrafting.class, TFC_ConfigFiles.getCraftingConfig(), TFC_ConfigFiles.CONVERSION);
		if (shapelessRecipes.length == 0) throw new IllegalArgumentException("No recipes for conversion " + name);
		this.recipes = ImmutableList.copyOf(shapelessRecipes);
	}

	@Override
	public ImmutableList<IRecipe> getRecipes()
	{
		return recipes;
	}

	@Override
	public String toString()
	{
		return name + "[default:" + defaultValue + " current:" + isAplied() + " config:" + inConfig() + " #ofRecipes:" + recipes.size() + "]";
	}
}
