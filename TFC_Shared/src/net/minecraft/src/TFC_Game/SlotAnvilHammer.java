package net.minecraft.src.TFC_Game;

import net.minecraft.src.*;

public class SlotAnvilHammer extends Slot

{
	public SlotAnvilHammer(EntityPlayer entityplayer, IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);
	}

	public boolean isItemValid(ItemStack itemstack)
	{
		if(itemstack.getItem() instanceof ItemHammer) {
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
