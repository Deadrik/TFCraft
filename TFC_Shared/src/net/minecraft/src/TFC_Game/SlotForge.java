package net.minecraft.src.TFC_Game;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.*;
import net.minecraft.src.Slot;

public class SlotForge extends Slot

{
	public SlotForge(EntityPlayer entityplayer, IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);

	}

	public boolean isItemValid(ItemStack itemstack)
	{
		if(!itemstack.getItem().getItemNameIS(itemstack).contains(".Ore."))
		{
			return true;
		}
		return false;
	}

	public void onPickupFromSlot(ItemStack itemstack)
	{
		super.onPickupFromSlot(itemstack);
	}
}
