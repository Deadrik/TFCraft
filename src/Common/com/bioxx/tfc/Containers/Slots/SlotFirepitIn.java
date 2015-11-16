package com.bioxx.tfc.Containers.Slots;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.bioxx.tfc.Items.ItemOre;
import com.bioxx.tfc.api.HeatRegistry;
import com.bioxx.tfc.api.TFCItems;

public class SlotFirepitIn extends Slot
{
	public SlotFirepitIn(EntityPlayer entityplayer, IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);
	}

	@Override
	public int getSlotStackLimit()
	{
		return 1;
	}

	@Override
	public boolean isItemValid(ItemStack is)
	{
		HeatRegistry manager = HeatRegistry.getInstance();
		return is.getItem() == TFCItems.fireStarter ||
				is.getItem() == TFCItems.flintSteel ||
				!(manager.findMatchingIndex(is) == null ||
					is.getItem() instanceof ItemOre);
	}

	@Override
	public void putStack(ItemStack is)
	{
		if (is != null)
			is.stackSize = 1;
		if (this.inventory != null)
			super.putStack(is);
	}
}
