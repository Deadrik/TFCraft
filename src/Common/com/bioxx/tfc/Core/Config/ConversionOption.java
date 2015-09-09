package com.bioxx.tfc.Core.Config;

import net.minecraft.item.crafting.IRecipe;

import com.bioxx.tfc.api.TFCCrafting;
import com.google.common.collect.ImmutableList;

/**
 * Used to represent the "conversion" option
 * Since normally the recipes affected by this class are new recipes, they don't have to be removed in the constructor.
 * If this is not the case, you must remove them after initializing the object, but before the loadFromConfig is called. Otherwise you may end up with double entries in the recipe list.
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
