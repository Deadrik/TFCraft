package com.bioxx.tfc.Containers.Slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.bioxx.tfc.Items.ItemArrow;
import com.bioxx.tfc.Items.Tools.ItemJavelin;

public class SlotQuiver extends Slot
{
	public SlotQuiver(IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack)
	{
		return itemstack.getItem() instanceof ItemJavelin || itemstack.getItem() instanceof ItemArrow;
	}
}
