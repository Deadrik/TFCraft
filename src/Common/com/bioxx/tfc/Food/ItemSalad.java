package com.bioxx.tfc.Food;

import net.minecraft.util.IIcon;

public class ItemSalad extends ItemMeal
{

	public ItemSalad()
	{
		super();
		this.hasSubtypes = true;
		this.MetaNames = new String[]{"Salad0","Salad1","Salad2","Salad3"};
		this.MetaIcons = new IIcon[4];
		this.setFolder("food/");
	}
}
