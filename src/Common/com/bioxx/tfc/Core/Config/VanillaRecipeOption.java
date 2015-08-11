package com.bioxx.tfc.Core.Config;

import java.util.Iterator;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.api.TFCCrafting;
import com.google.common.collect.ImmutableList;

/**
 * @author Dries007
 */
public class VanillaRecipeOption extends SyncingOption
{
	private boolean ourConfigValue;
	private boolean currentValue;
	public final ImmutableList<IRecipe> recipesToRemove;

	public VanillaRecipeOption(String name, ItemStack... toBeRemoved) throws NoSuchFieldException, IllegalAccessException
	{
		super(name, TFCCrafting.class);
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
		this.recipesToRemove = builder.build();
		//noinspection unchecked
		CraftingManager.getInstance().getRecipeList().removeAll(recipesToRemove); // To get the initial condition set up properly.
		this.ourConfigValue = TFC_ConfigFiles.getCraftingConfig().getBoolean(name, TFC_ConfigFiles.ENABLE_VANILLA_RECIPES, defaultValue, "");
		this.field.setBoolean(null, ourConfigValue);

		apply(ourConfigValue);
	}

	@SuppressWarnings("unchecked")
	public void apply(boolean enabled) throws IllegalAccessException
	{
		if (currentValue != enabled) // if we need to change states
		{
			boolean result = enabled ? CraftingManager.getInstance().getRecipeList().addAll(recipesToRemove) : CraftingManager.getInstance().getRecipeList().removeAll(recipesToRemove);
			TerraFirmaCraft.log.info("Remove option {} changed from {} to {}. Result: {}", name, currentValue, enabled, result);
			field.setBoolean(null, enabled); // Keep the field up to date as well
			currentValue = enabled;
		}
	}

	@Override
	public boolean inConfig()
	{
		return ourConfigValue;
	}

	public boolean isAplied()
	{
		return currentValue;
	}

	@Override
	public void reloadFromConfig() throws IllegalAccessException
	{
		this.ourConfigValue = TFC_ConfigFiles.getCraftingConfig().getBoolean(name, TFC_ConfigFiles.ENABLE_VANILLA_RECIPES, defaultValue, "");
		apply(this.ourConfigValue);
	}

	@Override
	public String toString()
	{
		return name + "[default:" + defaultValue + " current:" + currentValue + " config:" + ourConfigValue + " #ofRecipes:" + recipesToRemove.size() + "]";
	}
}
