package TFC.Food;

import TFC.API.ISize;
import TFC.API.Enums.EnumFoodGroup;

public class ItemRawFood extends ItemFoodTFC implements ISize
{
	public boolean edibleRaw = false;
	public boolean canBeUsedRaw = true;

	public ItemRawFood(int id, int foodid, EnumFoodGroup fg)
	{
		super(id, foodid, fg);
	}
}
