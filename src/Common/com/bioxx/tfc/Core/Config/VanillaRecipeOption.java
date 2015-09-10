package com.bioxx.tfc.Core.Config;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

import com.bioxx.tfc.api.TFCCrafting;
import com.google.common.collect.ImmutableList;

/**
 * Used to represent the "enable vanilla recipe" option
 * This removes the recipes affected in the constructor, to be re-added later if required.
 * @author Dries007
 */
public class VanillaRecipeOption extends SyncingOption
{
	public final ImmutableList<IRecipe> recipes;

	public VanillaRecipeOption(String name, ItemStack... toBeRemoved) throws NoSuchFieldException, IllegalAccessException
	{
		super(name, TFCCrafting.class, TFC_ConfigFiles.getCraftingConfig(), TFC_ConfigFiles.ENABLE_VANILLA_RECIPES);
		if (toBeRemoved.length == 0) throw new IllegalArgumentException("No items for removal " + name);
		ImmutableList.Builder<IRecipe> builder = new ImmutableList.Builder<IRecipe>();
		//noinspection unchecked
		for (IRecipe recipe : (Iterable<IRecipe>) CraftingManager.getInstance().getRecipeList())
		{
			if (recipe == null) continue;
			for (ItemStack out : toBeRemoved)
			{
				if (ItemStack.areItemStacksEqual(out, recipe.getRecipeOutput()))
				{
					builder.add(recipe);
					break;
				}
			}
		}
		this.recipes = builder.build();
		//noinspection unchecked
		CraftingManager.getInstance().getRecipeList().removeAll(recipes);
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
