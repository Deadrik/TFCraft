package com.bioxx.tfc.api.Crafting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class LoomManager
{
	private static final LoomManager INSTANCE = new LoomManager();
	public static final LoomManager getInstance()
	{
		return INSTANCE;
	}

	private List<LoomRecipe> recipes;
	private Map<LoomRecipe, ResourceLocation> textures;

	private LoomManager()
	{
		recipes = new ArrayList<LoomRecipe>();
		textures = new HashMap<LoomRecipe, ResourceLocation>();
	}

	public void addRecipe(LoomRecipe recipe, ResourceLocation rl)
	{
		recipes.add(recipe);
		if(recipe != null){
			textures.remove(recipe);
			textures.put(recipe, rl);
		}
	}

	public LoomRecipe findMatchingRecipe(ItemStack item)
	{
		for(Object recipe : recipes)
		{
			LoomRecipe lr = (LoomRecipe) recipe;
			if(item != null&& lr.matches(item))
				return lr;
		}
		return null;
	}

	public LoomRecipe findMatchingResult(ItemStack item)
	{
		for (Object recipe : recipes)
		{
			LoomRecipe lr = (LoomRecipe) recipe;
			if (item != null && lr.resultMatches(item))
				return lr;
		}
		return null;
	}

	public boolean hasPotentialRecipes(ItemStack item){
		for(Object recipe : recipes)
		{
			LoomRecipe lr = (LoomRecipe) recipe;
			if(item != null&& lr.partiallyMatches(item))
				return true;
		}
		return false;
	}
	
	public LoomRecipe findPotentialRecipes(ItemStack item){
		for(Object recipe : recipes)
		{
			LoomRecipe lr = (LoomRecipe) recipe;
			if(item != null&& lr.partiallyMatches(item))
				return lr;
		}
		return null;
	}

	public ResourceLocation findMatchingTexture(LoomRecipe recipe){
		ResourceLocation rl = null;
		if(recipe != null){
			rl = textures.remove(recipe);
			if(rl!= null){
				textures.put(recipe, rl);
				return rl;
			}
		}
		return null;
	}

	public List<LoomRecipe> getRecipes()
    {
        return recipes;
    }
}
