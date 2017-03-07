package com.bioxx.tfc.api;

import net.minecraft.item.Item;
import net.minecraft.util.StatCollector;

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
	
	public String getMetalName()
	{
		if (Name == "Pig Iron")
			return StatCollector.translateToLocal("gui.metal.Iron");
		
		String name = Name.replace(" ", "");
		return StatCollector.translateToLocal("gui.metal."+name);
	}
}
