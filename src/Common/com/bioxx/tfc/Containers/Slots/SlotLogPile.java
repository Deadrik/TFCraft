package com.bioxx.tfc.Containers.Slots;

import com.bioxx.tfc.TFCItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotLogPile extends Slot
{
	public SlotLogPile(EntityPlayer entityplayer, IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack)
	{
		if(itemstack.getItem() == TFCItems.Logs)
			return true;
		return false;
	}
}
