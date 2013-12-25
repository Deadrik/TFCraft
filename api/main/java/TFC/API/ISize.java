package TFC.API;

import net.minecraft.item.ItemStack;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;

public interface ISize 
{

	public EnumSize getSize(ItemStack is);

	public EnumWeight getWeight(ItemStack is);

	/**
	 * Allows setting weather or not this item can stack regardless of the size/weight values
	 * @return Can stacksize exceed 1
	 */
	public boolean canStack();
}
