package net.minecraft.src.TFC_Core.Containers;

import net.minecraft.src.*;

public class SlotAnvilFlux extends Slot

{
	public SlotAnvilFlux(EntityPlayer entityplayer, IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);

	}

	public boolean isItemValid(ItemStack itemstack)
	{
		if(itemstack.itemID == TFCItems.Flux.shiftedIndex) {
			return true;
		} else {
			return false;
		}
	}

	public void onPickupFromSlot(ItemStack itemstack)
	{
		super.onPickupFromSlot(itemstack);
	}
}
