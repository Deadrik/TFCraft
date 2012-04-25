package net.minecraft.src.TFC_Core;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.*;
import net.minecraft.src.Slot;

public class SlotLogPile extends Slot

{
	public SlotLogPile(EntityPlayer entityplayer, IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);

	}

	public boolean isItemValid(ItemStack itemstack)
	{
		if(itemstack.itemID == mod_TFC_Core.Logs.shiftedIndex) {
			return true;
		}

		return false;
	}

	public void onPickupFromSlot(ItemStack itemstack)
	{
		Boolean B = itemstack.hasTagCompound();
		if(!B)
		{
			super.onPickupFromSlot(itemstack);
		}
	}
}
