package TFC.Food;

import TFC.API.Enums.EnumFoodGroup;

public class ItemRawFoodDough extends ItemRawFood
{
	public ItemRawFoodDough(int foodid, EnumFoodGroup fg)
	{
		super(foodid, fg);
	}
	public ItemRawFoodDough(int foodid, EnumFoodGroup fg, boolean edible)
	{
		super(foodid, fg, edible);
	}
	public ItemRawFoodDough(int foodid, EnumFoodGroup fg, boolean edible, boolean usable)
	{
		super(foodid, fg, edible, usable);
	}
}
