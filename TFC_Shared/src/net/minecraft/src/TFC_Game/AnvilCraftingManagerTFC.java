package net.minecraft.src.TFC_Game;

import java.util.*;

import net.minecraft.src.*;
import net.minecraft.src.TFC_Core.General.AnvilRecipe;

public class AnvilCraftingManagerTFC
{
	private static final AnvilCraftingManagerTFC instance = new AnvilCraftingManagerTFC();
	public static final AnvilCraftingManagerTFC getInstance()
	{
		return instance;
	}

	private List recipes;
	private List recipesWeld;

	private AnvilCraftingManagerTFC()
	{
		recipes = new ArrayList();
		recipesWeld = new ArrayList();

		System.out.println(new StringBuilder().append(recipes.size()).append(" anvil recipes").toString());
		System.out.println(new StringBuilder().append(recipes.size()).append(" anvil weld recipes").toString());
	}

	public void addRecipe(AnvilRecipe recipe)
	{
		recipes.add(recipe);
	}
	
	public void addWeldRecipe(AnvilRecipe recipe)
    {
        recipesWeld.add(recipe);
    }

	public AnvilRecipe findMatchingRecipe(AnvilRecipe recipe)
	{
		for (int k = 0; k < recipes.size(); k++)
		{
		    AnvilRecipe irecipe = (AnvilRecipe)recipes.get(k);
			if (irecipe.matches(recipe))
			{
				return irecipe;
			}
		}

		return null;
	}
	
	public AnvilRecipe findMatchingWeldRecipe(AnvilRecipe recipe)
    {
        for (int k = 0; k < recipesWeld.size(); k++)
        {
            AnvilRecipe irecipe = (AnvilRecipe)recipesWeld.get(k);
            if (irecipe.matches(recipe))
            {
                return irecipe;
            }
        }

        return null;
    }
	
	public ItemStack findCompleteRecipe(AnvilRecipe recipe)
    {
        for (int k = 0; k < recipes.size(); k++)
        {
            AnvilRecipe irecipe = (AnvilRecipe)recipes.get(k);
            if (irecipe.isComplete(recipe))
            {
                return irecipe.getCraftingResult();
            }
        }

        return null;
    }
	
	public ItemStack findCompleteWeldRecipe(AnvilRecipe recipe)
    {
        for (int k = 0; k < recipesWeld.size(); k++)
        {
            AnvilRecipe irecipe = (AnvilRecipe)recipesWeld.get(k);
            if (irecipe.isComplete(recipe))
            {
                return irecipe.getCraftingResult();
            }
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
}
