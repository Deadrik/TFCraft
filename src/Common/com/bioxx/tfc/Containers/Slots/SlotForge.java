package com.bioxx.tfc.Containers.Slots;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.Items.ItemOre;
import com.bioxx.tfc.api.HeatRegistry;

public class SlotForge extends Slot
{
	public SlotForge(EntityPlayer entityplayer, IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack)
	{
		HeatRegistry manager = HeatRegistry.getInstance();
		return !(manager.findMatchingIndex(itemstack) == null
				|| itemstack.getItem() instanceof ItemOre
				|| itemstack.getItem() instanceof ItemFoodTFC);
	}

	@Override
	public int getSlotStackLimit()
	{
		return 1;
	}
}
