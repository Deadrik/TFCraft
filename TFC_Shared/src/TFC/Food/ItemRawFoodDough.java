package TFC.Food;

import TFC.API.Enums.EnumFoodGroup;

public class ItemRawFoodDough extends ItemRawFood
{
	public ItemRawFoodDough(int id, int foodid, EnumFoodGroup fg)
	{
		super(id, foodid, fg);
	}
	public ItemRawFoodDough(int id, int foodid, EnumFoodGroup fg, boolean edible)
	{
		super(id, foodid, fg, edible);
	}
	public ItemRawFoodDough(int id, int foodid, EnumFoodGroup fg, boolean edible, boolean usable)
	{
		super(id, foodid, fg, edible, usable);
	}
}
