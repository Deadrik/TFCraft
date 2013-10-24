package TFC.API;

import net.minecraft.item.ItemStack;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;

public interface IItemFoodBlock 
{
	public int getFoodId(ItemStack is);
	
	public int getHealAmount(ItemStack is);
}
