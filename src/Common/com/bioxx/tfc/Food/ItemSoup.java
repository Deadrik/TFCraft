package com.bioxx.tfc.Food;

import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemSoup extends ItemMeal
{

	public ItemSoup()
	{
		super();
		this.hasSubtypes = true;
		this.metaNames = new String[]{"Soup0","Soup1","Soup2","Soup3"};
		this.metaIcons = new IIcon[4];
		this.setFolder("food/");
	}

	@Override
	public float getFoodMaxWeight(ItemStack is) {
		return 24;
	}

	@Override
	public boolean renderDecay() {
		return true;
	}

	@Override
	public boolean renderWeight() {
		return false;
	}
}
