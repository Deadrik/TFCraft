package com.bioxx.tfc.Food;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import com.bioxx.tfc.api.Enums.EnumFoodGroup;

public class ItemFoodMeat extends ItemFoodTFC {

	public ItemFoodMeat(EnumFoodGroup fg, int sw, int so, int sa, int bi,
			int um, boolean edible, boolean usable) {
		super(fg, sw, so, sa, bi, um, edible, usable);
	}

	@Override
	protected String getCookedLevelString(ItemStack is)
	{
		String s = "";
		if(Food.isCooked(is))
		{
			s+= " (";
			int cookedLevel = ((int)Food.getCooked(is)-600)/120;
			switch(cookedLevel)
			{
			case 0: s+=StatCollector.translateToLocal("food.cooked.rare");break;
			case 1: s+=StatCollector.translateToLocal("food.cooked.medrare");break;
			case 2: s+=StatCollector.translateToLocal("food.cooked.med");break;
			case 3: s+=StatCollector.translateToLocal("food.cooked.medwell");break;
			default: s+=StatCollector.translateToLocal("food.cooked.well");break;
			}
			s+= ")";
		}
		return s;
	}

}
