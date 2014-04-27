package com.bioxx.tfc.Containers.Slots;

import com.bioxx.tfc.api.ISize;
import com.bioxx.tfc.api.Enums.EnumSize;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotSizeSmall extends Slot
{
	EnumSize size = EnumSize.SMALL;
	public SlotSizeSmall(IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack)
	{
		if(itemstack.getItem() instanceof ISize && ((ISize)itemstack.getItem()).getSize(itemstack).stackSize >= size.stackSize)
			return true;
		else if (!(itemstack.getItem() instanceof ISize))
			return true;
		return false;
	}
}
