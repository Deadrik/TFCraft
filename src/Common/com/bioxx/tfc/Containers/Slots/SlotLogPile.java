package com.bioxx.tfc.Containers.Slots;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.bioxx.tfc.api.TFCItems;

public class SlotLogPile extends Slot
{
	public SlotLogPile(EntityPlayer entityplayer, IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack)
	{
		return itemstack.getItem() == TFCItems.logs;
	}

	@Override
	public int getSlotStackLimit()
	{
		return 4;
	}
}
