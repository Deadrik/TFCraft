package com.bioxx.tfc.Containers.Slots;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.bioxx.tfc.api.TFCItems;

public class SlotFirepitOut extends Slot
{
	private int slotMax;
	public SlotFirepitOut(EntityPlayer entityplayer, IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);
		slotMax = 64;
	}

	@Override
	public boolean isItemValid(ItemStack itemstack) {
		if(itemstack.getItem() == TFCItems.ceramicMold && itemstack.getItemDamage() == 1)
		{
			slotMax = 1;
			return true;
		}
		return false;
	}
	
	@Override
	public void putStack(ItemStack par1ItemStack) {
		if (par1ItemStack != null && par1ItemStack.getItem() == TFCItems.ceramicMold && par1ItemStack.getItemDamage() == 1)
		{
			par1ItemStack.stackSize = 1;
			slotMax = 1;
		}
		else
		{
			slotMax = 64;
		}
		super.putStack(par1ItemStack);
	}

	@Override
	public int getSlotStackLimit()
	{
		return slotMax;
	}
}
