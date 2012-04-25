package net.minecraft.src.TFC_Game;

import net.minecraft.src.*;

public class SlotScribePaper extends Slot
{
	EntityPlayer player;
	public SlotScribePaper(EntityPlayer entityplayer, IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);
		player = entityplayer;
	}

	public boolean isItemValid(ItemStack itemstack)
	{
		if(itemstack.itemID == Item.paper.shiftedIndex)
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
