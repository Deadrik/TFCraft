package net.minecraft.src.TFC_Core.Containers;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.*;
import net.minecraft.src.Slot;

public class SlotFirepitCharcoal extends Slot

{
	public SlotFirepitCharcoal(EntityPlayer entityplayer, IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);

	}

	public boolean isItemValid(ItemStack itemstack)
	{
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
