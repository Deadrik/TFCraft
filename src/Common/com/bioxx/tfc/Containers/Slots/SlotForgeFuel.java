package com.bioxx.tfc.Containers.Slots;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.bioxx.tfc.TFCItems;

public class SlotForgeFuel extends Slot
{
	public SlotForgeFuel(EntityPlayer entityplayer, IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);
	}

	public boolean isItemValid(ItemStack itemstack)
	{
		if(itemstack.getItem() == TFCItems.Coal)
			return true;
		return false;
	}

	public int getSlotStackLimit()
	{
		return 1;
	}
}
