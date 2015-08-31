package com.bioxx.tfc.Containers.Slots;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;

public class SlotFirepitFuel extends Slot
{
	public SlotFirepitFuel(EntityPlayer entityplayer, IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack)
	{
		return itemstack.getItem() == TFCItems.logs || itemstack.getItem() == Item.getItemFromBlock(TFCBlocks.peat);
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
