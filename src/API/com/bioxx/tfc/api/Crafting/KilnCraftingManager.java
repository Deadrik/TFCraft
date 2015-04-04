package com.bioxx.tfc.api.Crafting;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

public class KilnCraftingManager
{
	private static final KilnCraftingManager instance = new KilnCraftingManager();
	public static final KilnCraftingManager getInstance()
	{
		return instance;
	}

	private List<KilnRecipe> recipes;

	private KilnCraftingManager()
	{
		recipes = new ArrayList<KilnRecipe>();
	}

	public void addRecipe(KilnRecipe recipe)
	{
		recipes.add(recipe);
	}

	public KilnRecipe findMatchingRecipe(KilnRecipe recipe)
	{
		for (int k = 0; k < recipes.size(); k++)
		{
			KilnRecipe irecipe = recipes.get(k);
			if (irecipe.matches(recipe))
			{
				return irecipe;
			}
		}

		return null;
	}
	
	public ItemStack findCompleteRecipe(KilnRecipe recipe)
    {
        for (int k = 0; k < recipes.size(); k++)
        {
			KilnRecipe irecipe = recipes.get(k);
            if (irecipe.isComplete(recipe))
            {
                return irecipe.getCraftingResult();
            }
        }

        return recipe.input1;
    }

	public List<KilnRecipe> getRecipeList()
	{
		return recipes;
	}
}
