package TFC.API;

import net.minecraft.item.ItemStack;

public interface IBag
{
	public abstract ItemStack[] loadBagInventory(ItemStack is);
}
