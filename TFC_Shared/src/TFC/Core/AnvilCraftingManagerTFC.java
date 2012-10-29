package TFC.Core;

import java.util.*;

import TFC.*;

import net.minecraft.src.ItemStack;

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
	
	public ItemStack findCompleteRecipe(AnvilRecipe recipe, int[] rules)
    {
        for (int k = 0; k < recipes.size(); k++)
        {
            AnvilRecipe irecipe = (AnvilRecipe)recipes.get(k);
            if (irecipe.isComplete(recipe, rules))
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
            if (irecipe.matches(recipe))
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
