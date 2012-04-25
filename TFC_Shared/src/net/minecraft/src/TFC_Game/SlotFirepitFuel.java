package net.minecraft.src.TFC_Game;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.*;
import net.minecraft.src.Slot;

public class SlotFirepitFuel extends Slot

{
	public SlotFirepitFuel(EntityPlayer entityplayer, IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);

	}

	public boolean isItemValid(ItemStack itemstack)
	{
		if(itemstack.itemID == mod_TFC_Core.Logs.shiftedIndex || itemstack.itemID == Block.wood.blockID || itemstack.itemID == mod_TFC_Core.terraPeat.blockID) {
			return true;
		}
		return false;
	}

	public void onPickupFromSlot(ItemStack itemstack)
	{
		super.onPickupFromSlot(itemstack);
	}
}
