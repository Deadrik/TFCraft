package com.bioxx.tfc.Containers.Slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Interfaces.ISize;

public class SlotSizeSmall extends Slot
{
	private EnumSize size = EnumSize.SMALL;
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
