package com.bioxx.tfc.api;

import net.minecraft.item.Item;

public class Metal
{
	public String name;
	public Item meltedItem;
	public Item ingot;
	public boolean canUse = true;

	public Metal(String name)
	{
		this.name = name;
	}

	public Metal(String name, Item m, Item i)
	{
		this(name);
		meltedItem = m;
		ingot = i;
	}

	public Metal(String name, Item m, Item i, boolean use)
	{
		this(name);
		meltedItem = m;
		ingot = i;
		canUse = use;
	}
}
