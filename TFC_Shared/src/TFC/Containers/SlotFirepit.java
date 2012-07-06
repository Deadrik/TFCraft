package TFC.Containers;

import TFC.Core.TFC_Core;
import net.minecraft.src.*;

public class SlotFirepit extends Slot

{
	public SlotFirepit(EntityPlayer entityplayer, IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);

	}

	public boolean isItemValid(ItemStack itemstack)
	{
		return false;
	}

	public void onPickupFromSlot(ItemStack itemstack)
	{
		super.onPickupFromSlot(itemstack);
	}
	
	public int getSlotStackLimit()
    {
	    return 64;
    }
}
