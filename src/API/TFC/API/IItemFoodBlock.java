package TFC.API;

import net.minecraft.item.ItemStack;

public interface IItemFoodBlock 
{
	public int getFoodId(ItemStack is);
	
	public int getHealAmount(ItemStack is);
}
