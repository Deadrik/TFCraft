package com.bioxx.tfc.Core.Config;

import java.lang.reflect.Field;

import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.config.Configuration;

import com.bioxx.tfc.TerraFirmaCraft;
import com.google.common.collect.ImmutableList;

import static com.bioxx.tfc.Core.Config.TFC_ConfigFiles.STRING_SYNCING;

/**
 * @author Dries007
 */
public abstract class SyncingOption
{
	public final String name;
	public final Field field;
	public final boolean defaultValue;
	public final Configuration cfg;
	public final String cat;

	private boolean ourConfigValue;
	private boolean currentValue;

	public SyncingOption(String name, Class<?> clazz, Configuration cfg, String cat) throws NoSuchFieldException, IllegalAccessException
	{
		if (STRING_SYNCING.containsKey(name)) throw new IllegalArgumentException("Duplicate key: " + name);
		STRING_SYNCING.put(name, this);
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
			TerraFirmaCraft.log.debug("Conversion option {} changed from {} to {}. Result: {}", name, currentValue, enabled, result);
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
