package TFC.Containers.Slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import TFC.Items.ItemTuyere;

public class SlotTuyere extends Slot

{
	public SlotTuyere(IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);

	}

	@Override
	public boolean isItemValid(ItemStack itemstack)
	{    	
		if(itemstack.getItem() instanceof ItemTuyere)
		{
			return true;
		}
		return false;
	}
	
	@Override
	public int getSlotStackLimit() {
		return 1;
	}
}
