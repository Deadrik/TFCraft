package com.bioxx.tfc.api.Util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.settings.KeyBinding;

public class KeyBindings
{
	public static List<KeyBinding> keyBindingsList;
	public static List<Boolean> isRepeatingList;

	public static void addKeyBinding(String name, int value, String category)
	{
		if (keyBindingsList == null)
			keyBindingsList = new ArrayList<KeyBinding>();
		keyBindingsList.add(new KeyBinding(name, value, category));
	}

	public static void addKeyBinding(KeyBinding binding)
	{
		if (keyBindingsList == null)
			keyBindingsList = new ArrayList<KeyBinding>();
		keyBindingsList.add(binding);
	}

	public static void addIsRepeating(boolean value)
	{
		if (isRepeatingList == null)
			isRepeatingList = new ArrayList<Boolean>();
		isRepeatingList.add(value);
	}

	public static KeyBinding[] gatherKeyBindings()
	{
		return keyBindingsList.toArray(new KeyBinding[keyBindingsList.size()]);
	}

	public static boolean[] gatherIsRepeating()
	{
		boolean[] isRepeating = new boolean[isRepeatingList.size()];
		for (int x = 0; x < isRepeating.length; x++)
			isRepeating[x] = isRepeatingList.get(x).booleanValue();
		return isRepeating;
	}
}
