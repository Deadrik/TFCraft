package com.bioxx.tfc.Containers.Slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.bioxx.tfc.Items.ItemTuyere;

public class SlotTuyere extends Slot
{
	public SlotTuyere(IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack)
	{
		return itemstack.getItem() instanceof ItemTuyere;
	}

	@Override
	public int getSlotStackLimit()
	{
		return 1;
	}
}
