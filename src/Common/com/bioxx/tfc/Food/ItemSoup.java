package com.bioxx.tfc.Food;

import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemSoup extends ItemMeal
{

	public ItemSoup()
	{
		super();
		this.hasSubtypes = true;
		this.MetaNames = new String[]{"Soup0","Soup1","Soup2","Soup3"};
		this.MetaIcons = new IIcon[4];
		this.setFolder("food/");
	}

	@Override
	protected float[] getNutritionalWeights()
	{
		//These numbers are 5% of the oz value for each slot 1/3/2/1/1/1
		return new float[]{0.1f,0.1f,0.1f,0.1f};
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
