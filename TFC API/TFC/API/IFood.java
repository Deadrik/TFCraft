package TFC.API;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import TFC.API.Enums.EnumFoodGroup;

public interface IFood 
{
	public EnumFoodGroup getFoodGroup();
	public int getFoodID();
	public float getDecayRate();
	/**
	 * @return Returns an ItemStack that will replace the current ItemStack when the food has reached maximum decay.
	 * Normally returns null.
	 */
	public ItemStack onDecayed(ItemStack is, World world, int i, int j, int k);
}
