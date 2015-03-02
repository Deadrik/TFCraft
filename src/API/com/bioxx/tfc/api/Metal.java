package com.bioxx.tfc.api;

import net.minecraft.item.Item;
import net.minecraft.util.StatCollector;

public class Metal
{
	public String Name;
	public Item MeltedItem;
	public Item Ingot;
	public boolean canUse = true;

	public Metal(String name)
	{
		Name = name;
	}

	public Metal(String name, Item m, Item i)
	{
		this(name);
		MeltedItem = m;
		Ingot = i;
	}

	public Metal(String name, Item m, Item i, boolean use)
	{
		this(name);
		MeltedItem = m;
		Ingot = i;
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
