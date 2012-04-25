package net.minecraft.src.TFC_Game;

import net.minecraft.src.*;

public class SlotAnvilFlux extends Slot

{
	public SlotAnvilFlux(EntityPlayer entityplayer, IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);

	}

	public boolean isItemValid(ItemStack itemstack)
	{
		if(itemstack.itemID == mod_TFC_Game.Flux.shiftedIndex) {
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
