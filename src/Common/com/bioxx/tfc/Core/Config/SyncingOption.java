package com.bioxx.tfc.Core.Config;

import java.lang.reflect.Field;

import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

import net.minecraftforge.common.config.Configuration;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.api.TFCOptions;
import com.google.common.collect.ImmutableList;

import static com.bioxx.tfc.Core.Config.TFC_ConfigFiles.SYNCING_OPTION_MAP;

/**
 * When the value (config or from server, depending on context) is true the recipes are (re)added to the recipe list. Otherwise they are removed.
 * This behaviour may be changed by overriding the .apply(boolean) method
 * Also keeps the static values from the class passed in the constructor in sync with the actual status of things.
 * @author Dries007
 */
public abstract class SyncingOption
{
	public final String name;
	public final Field field;
	public final boolean defaultValue;
	public final Configuration cfg;
	public final String cat;

	protected boolean ourConfigValue;
	protected boolean currentValue;

	public SyncingOption(String name, Class<?> clazz, Configuration cfg, String cat) throws NoSuchFieldException, IllegalAccessException
	{
		if (SYNCING_OPTION_MAP.containsKey(name)) throw new IllegalArgumentException("Duplicate key: " + name);
		SYNCING_OPTION_MAP.put(name, this);
		this.name = name;
		this.field = clazz.getDeclaredField(name);
		this.cfg = cfg;
		this.cat = cat;
		this.defaultValue = field.getBoolean(null);
	}

	@SuppressWarnings("unchecked")
	public void apply(boolean enabled) throws IllegalAccessException
	{
		if (currentValue != enabled) // if we need to change states
		{
			boolean result = enabled ? CraftingManager.getInstance().getRecipeList().addAll(getRecipes()) : CraftingManager.getInstance().getRecipeList().removeAll(getRecipes());
			if (TFCOptions.enableDebugMode) TerraFirmaCraft.LOG.info("Conversion option {} changed from {} to {}. Result: {}", name, currentValue, enabled, result);
			field.setBoolean(null, enabled); // Keep the field up to date as well
			currentValue = enabled;
		}
	}

	public boolean inConfig()
	{
		return ourConfigValue;
	}

	public boolean isAplied()
	{
		return currentValue;
	}

	public void loadFromConfig() throws IllegalAccessException
	{
		ourConfigValue = cfg.getBoolean(name, cat, defaultValue, "");
		apply(ourConfigValue);
	}

	public abstract ImmutableList<IRecipe> getRecipes();
}
