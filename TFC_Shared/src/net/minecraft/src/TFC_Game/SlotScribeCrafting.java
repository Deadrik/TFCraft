package net.minecraft.src.TFC_Game;

import net.minecraft.src.*;

public class SlotScribeCrafting extends Slot
{
	EntityPlayer player;
	public SlotScribeCrafting(EntityPlayer entityplayer, IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);
		player = entityplayer;
	}

	public boolean isItemValid(ItemStack itemstack)
	{
		if(itemstack.itemID == mod_TFC_Game.terraInk.shiftedIndex)
		{
			return true;
		}
		return false;
	}

	public void onPickupFromSlot(ItemStack itemstack)
	{
		super.onPickupFromSlot(itemstack);
	}

	public void onSlotChanged()
	{
		inventory.onInventoryChanged();

	}
}
