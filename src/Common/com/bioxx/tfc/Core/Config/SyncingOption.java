package com.bioxx.tfc.Core.Config;

import java.lang.reflect.Field;

import static com.bioxx.tfc.Core.Config.TFC_ConfigFiles.STRING_SYNCING;

/**
 * @author Dries007
 */
public abstract class SyncingOption
{
	public final String name;
	public final Field field;
	public final boolean defaultValue;

	public SyncingOption(String name, Class<?> clazz) throws NoSuchFieldException, IllegalAccessException
	{
		this.name = name;
		this.field = clazz.getDeclaredField(name);
		this.defaultValue = field.getBoolean(null);
		STRING_SYNCING.put(name, this);
	}

	public abstract void apply(boolean enabled) throws IllegalAccessException;

	public abstract boolean inConfig();

	public abstract boolean isAplied();

	public abstract void reloadFromConfig() throws IllegalAccessException;
}
