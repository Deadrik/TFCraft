package com.bioxx.tfc.Containers.Slots;

import com.bioxx.tfc.Items.ItemOre;
import com.bioxx.tfc.api.HeatRegistry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotForge extends Slot
{
	public SlotForge(EntityPlayer entityplayer, IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);
	}

	public boolean isItemValid(ItemStack itemstack)
	{
		HeatRegistry manager = HeatRegistry.getInstance();
		if(manager.findMatchingIndex(itemstack) == null)
			return false;

		if(!(itemstack.getItem() instanceof ItemOre))
			return true;

		return false;
	}

	public int getSlotStackLimit()
	{
		return 1;
	}
}
