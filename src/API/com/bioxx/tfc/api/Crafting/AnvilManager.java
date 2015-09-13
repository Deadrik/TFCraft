package com.bioxx.tfc.api.Crafting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class AnvilManager
{
	private static final AnvilManager INSTANCE = new AnvilManager();
	public static final AnvilManager getInstance()
	{
		return INSTANCE;
	}
	public static World world;

	private List<AnvilRecipe> recipes;
	private List<AnvilRecipe> recipesWeld;
	private Map<String, PlanRecipe> plans;

	private AnvilManager()
	{
		recipes = new ArrayList<AnvilRecipe>();
		recipesWeld = new ArrayList<AnvilRecipe>();
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

	public void clearRecipes()
	{
		recipes.clear();
		recipesWeld.clear();
		plans.clear();
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
		return plans.get(s);
	}

	public AnvilRecipe findMatchingRecipe(AnvilRecipe recipe)
	{
		for (int k = 0; k < recipes.size(); k++)
		{
			AnvilRecipe irecipe = recipes.get(k);
			if (irecipe != null && irecipe.matches(recipe))
				return irecipe;
		}

		return null;
	}

	public AnvilRecipe findMatchingWeldRecipe(AnvilRecipe recipe)
	{
		for (int k = 0; k < recipesWeld.size(); k++)
		{
			AnvilRecipe irecipe = recipesWeld.get(k);
			if (irecipe != null && irecipe.matches(recipe))
				return irecipe;
		}

		return null;
	}

	public Object[] findCompleteRecipe(AnvilRecipe recipe, int[] rules)
	{
		for (int k = 0; k < recipes.size(); k++)
		{
			AnvilRecipe irecipe = recipes.get(k);
			if (irecipe != null && irecipe.isComplete(INSTANCE, recipe, rules))
				return new Object[] {irecipe, irecipe.getCraftingResult(recipe.input1)};
		}

		return null;
	}

	public ItemStack findCompleteWeldRecipe(AnvilRecipe recipe)
	{
		for (int k = 0; k < recipesWeld.size(); k++)
		{
			AnvilRecipe irecipe = recipesWeld.get(k);
			if (irecipe != null && irecipe.matches(recipe))
				return irecipe.getCraftingResult(recipe.input1);
		}

		return null;
	}

	public List<AnvilRecipe> getRecipeList()
	{
		return recipes;
	}

	public List<AnvilRecipe> getWeldRecipeList()
	{
		return recipesWeld;
	}

	public Map<String, PlanRecipe> getPlans()
	{
		return plans;
	}

	public static NBTTagCompound getCraftTag(ItemStack is)
	{
		if(is.hasTagCompound() && is.getTagCompound().hasKey("craftingTag"))
		{
			return (NBTTagCompound) is.getTagCompound().getTag("craftingTag");
		}
		else
			return new NBTTagCompound();
	}

	public static void setCraftTag(ItemStack is, NBTTagCompound nbt)
	{
		if(!is.hasTagCompound())
			is.setTagCompound(new NBTTagCompound());
		is.getTagCompound().setTag("craftingTag", nbt);
	}

	public static float getDurabilityBuff(ItemStack is)
	{
		NBTTagCompound nbt = getCraftTag(is);
		return nbt.getFloat("durabuff");
	}

	public static void setDurabilityBuff(ItemStack is, float value)
	{
		NBTTagCompound nbt = getCraftTag(is);
		nbt.setFloat("durabuff", value);
		setCraftTag(is, nbt);
	}

	public static float getDamageBuff(ItemStack is)
	{
		NBTTagCompound nbt = getCraftTag(is);
		return nbt.getFloat("damagebuff");
	}

	public static void setDamageBuff(ItemStack is, float value)
	{
		NBTTagCompound nbt = getCraftTag(is);
		nbt.setFloat("damagebuff", value);
		setCraftTag(is, nbt);
	}
}
