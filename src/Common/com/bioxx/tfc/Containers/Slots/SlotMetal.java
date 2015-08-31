package com.bioxx.tfc.Containers.Slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.bioxx.tfc.Items.ItemMeltedMetal;
import com.bioxx.tfc.api.TFCItems;

public class SlotMetal extends Slot
{
	public SlotMetal(IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack)
	{
		return itemstack.getItem() instanceof ItemMeltedMetal || itemstack.getItem() == TFCItems.ceramicMold;
	}
}
