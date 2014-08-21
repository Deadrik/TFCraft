package com.bioxx.tfc.Food;

import net.minecraft.util.IIcon;

public class ItemSandwich extends ItemMeal
{

	public ItemSandwich()
	{
		super();
		this.hasSubtypes = true;
		this.MetaNames = new String[]{"Sandwich0","Sandwich1","Sandwich2","Sandwich3"};
		this.MetaIcons = new IIcon[4];
		this.setFolder("food/");
	}
}
