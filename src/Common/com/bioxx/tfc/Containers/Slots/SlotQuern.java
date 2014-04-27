package com.bioxx.tfc.Containers.Slots;

import com.bioxx.tfc.TFCItems;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotQuern extends Slot
{
	public SlotQuern(IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack)
	{
		if(itemstack.getItem() == TFCItems.Quern)
			return true;
		return false;
	}

	@Override
	public int getSlotStackLimit()
	{
		return 1;
	}

	@Override
	public void putStack(ItemStack par1ItemStack)
	{
		if (par1ItemStack != null) par1ItemStack.stackSize = 1;
		super.putStack(par1ItemStack);
	}
}
