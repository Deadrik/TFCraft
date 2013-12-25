package TFC.Containers.Slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import TFC.Items.Tools.ItemPlan;

public class SlotAnvilPlan extends Slot
{
	public SlotAnvilPlan(IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);

	}

	@Override
	public boolean isItemValid(ItemStack itemstack)
	{
		if(itemstack.getItem() instanceof ItemPlan) {
			return true;
		} else {
			return false;
		}
	}
}
