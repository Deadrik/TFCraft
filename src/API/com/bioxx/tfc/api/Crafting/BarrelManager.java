package com.bioxx.tfc.api.Crafting;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class BarrelManager
{
	private static final BarrelManager instance = new BarrelManager();
	public static final BarrelManager getInstance()
	{
		return instance;
	}

	private List recipes;

	private BarrelManager()
	{
		recipes = new ArrayList();
	}

	public void addRecipe(BarrelRecipe recipe)
	{
		recipes.add(recipe);
	}

	public BarrelRecipe findMatchingRecipe(ItemStack item, FluidStack fluid, boolean sealed, int techLevel)
	{
		for(Object recipe : recipes)
		{
			BarrelRecipe br = (BarrelRecipe) recipe;
			if(item != null && fluid != null &&/*(br.inItemStack != null && item != null) && (br.inFluid != null && fluid != null) &&*/ br.matches(item, fluid))
				if(br.isSealedRecipe == sealed && br.minTechLevel <= techLevel)
					return br;
		}
		return null;
	}

    public List getRecipes()
    {
        return recipes;
    }
}
