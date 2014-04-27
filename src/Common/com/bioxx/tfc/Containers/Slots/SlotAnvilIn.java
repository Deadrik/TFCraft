package com.bioxx.tfc.Containers.Slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class SlotAnvilIn extends Slot

{
	public SlotAnvilIn(IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}
}
