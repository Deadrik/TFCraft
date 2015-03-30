package com.bioxx.tfc.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.bioxx.tfc.Core.Player.PlayerInventory;

public class ContainerHopper extends ContainerTFC
{
	private final IInventory hopperInv;

	public ContainerHopper(InventoryPlayer playerInv, IInventory inv)
	{
		this.hopperInv = inv;
		inv.openInventory();
		byte b0 = 51;
		int i;

		for (i = 0; i < inv.getSizeInventory(); ++i)
		{
			this.addSlotToContainer(new Slot(inv, i, 44 + i * 18, 17));
		}

		PlayerInventory.buildInventoryLayout(this, playerInv, 8, 54, false, true);
	}

	@Override
	public ItemStack transferStackInSlotTFC(EntityPlayer entityplayer, int i)
	{
		Slot slot = (Slot)inventorySlots.get(i);
		if(slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			if(i < 5)
			{
				if(!this.mergeItemStack(itemstack1, 5, this.inventorySlots.size(), true))
					return null;
			}
			else
			{
				if(!this.mergeItemStack(itemstack1, 0, 5, false)){return null;}
			}

			if(itemstack1.stackSize == 0)
				slot.putStack(null);
			else
				slot.onSlotChanged();
		}
		return null;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return this.hopperInv.isUseableByPlayer(player);
	}

	/**
	 * Called when the container is closed.
	 */
	@Override
	public void onContainerClosed(EntityPlayer player)
	{
		super.onContainerClosed(player);
		this.hopperInv.closeInventory();
	}
}
