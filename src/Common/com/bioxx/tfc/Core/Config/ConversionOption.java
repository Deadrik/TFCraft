package com.bioxx.tfc.Core.Config;

import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.ShapelessRecipes;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.api.TFCCrafting;
import com.google.common.collect.ImmutableList;

/**
 * @author Dries007
 */
public class ConversionOption extends SyncingOption
{
	private boolean ourConfigValue;
	private boolean currentValue;
	public final ImmutableList<ShapelessRecipes> shapelessRecipes;

	public ConversionOption(String name, ShapelessRecipes... shapelessRecipes) throws NoSuchFieldException, IllegalAccessException
	{
		super(name, TFCCrafting.class);
		if (shapelessRecipes.length == 0) throw new IllegalArgumentException("No recipes for conversion " + name);

		this.shapelessRecipes = ImmutableList.copyOf(shapelessRecipes);
		this.ourConfigValue = TFC_ConfigFiles.getCraftingConfig().getBoolean(name, TFC_ConfigFiles.CONVERSION, defaultValue, "");
		this.field.setBoolean(null, ourConfigValue);

		apply(ourConfigValue);
	}

	@SuppressWarnings("unchecked")
	public void apply(boolean enabled) throws IllegalAccessException
	{
		if (currentValue != enabled) // if we need to change states
		{
			boolean result = enabled ? CraftingManager.getInstance().getRecipeList().addAll(shapelessRecipes) : CraftingManager.getInstance().getRecipeList().removeAll(shapelessRecipes);
			TerraFirmaCraft.log.info("Conversion option {} changed from {} to {}. Result: {}", name, currentValue, enabled, result);
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
		this.ourConfigValue = TFC_ConfigFiles.getCraftingConfig().getBoolean(name, TFC_ConfigFiles.CONVERSION, defaultValue, "");
		apply(this.ourConfigValue);
	}

	@Override
	public String toString()
	{
		return name + "[default:" + defaultValue + " current:" + currentValue + " config:" + ourConfigValue + " #ofRecipes:" + shapelessRecipes.size() + "]";
	}
}
