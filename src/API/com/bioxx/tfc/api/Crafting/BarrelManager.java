package com.bioxx.tfc.api.Crafting;

import java.util.ArrayList;
import java.util.List;

import com.bioxx.tfc.TileEntities.TEBarrel;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class BarrelManager
{
	private static final BarrelManager instance = new BarrelManager();
	public static final BarrelManager getInstance()
	{
		return instance;
	}

	private List<BarrelRecipe> recipes;
	private List<BarrelPreservativeRecipe> preservativeRecipes;

	private BarrelManager()
	{
		recipes = new ArrayList<BarrelRecipe>();
		preservativeRecipes = new ArrayList<BarrelPreservativeRecipe>();
	}

	public void addRecipe(BarrelRecipe recipe)
	{
		recipes.add(recipe);
	}
	
	public void addPreservative(BarrelPreservativeRecipe recipe) {
		preservativeRecipes.add(recipe);
	}

	public BarrelRecipe findMatchingRecipe(ItemStack item, FluidStack fluid, boolean sealed, int techLevel)
	{
		for(Object recipe : recipes)
		{
			BarrelRecipe br = (BarrelRecipe) recipe;
			if(/*item != null && */fluid != null &&/*(br.inItemStack != null && item != null) && (br.inFluid != null && fluid != null) &&*/ br.matches(item, fluid))
				if(br.isSealedRecipe == sealed && br.minTechLevel <= techLevel)
					return br;
		}
		return null;
	}
	
	public BarrelPreservativeRecipe findMatchinePreservativeRepice(TEBarrel teBarrel, ItemStack item, FluidStack fluid, boolean sealed)
	{
		for(BarrelPreservativeRecipe recipe : preservativeRecipes){
			if(recipe.checkForPreservation(teBarrel, fluid, item, sealed))
				return recipe;
		}
		return null;
	}

	public List<BarrelRecipe> getRecipes()
	{
		return recipes;
	}
	
	public List<BarrelPreservativeRecipe> getPreservatives()
	{
		return preservativeRecipes;
	}


}
