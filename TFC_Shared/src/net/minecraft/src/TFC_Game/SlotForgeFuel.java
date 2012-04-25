package net.minecraft.src.TFC_Game;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.*;
import net.minecraft.src.Slot;

public class SlotForgeFuel extends Slot

{
	public SlotForgeFuel(EntityPlayer entityplayer, IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);

	}

	public boolean isItemValid(ItemStack itemstack)
	{
		if(itemstack.itemID == Item.coal.shiftedIndex) {
			return true;
		}
		return false;
	}

	public void onPickupFromSlot(ItemStack itemstack)
	{
		super.onPickupFromSlot(itemstack);
	}
}
