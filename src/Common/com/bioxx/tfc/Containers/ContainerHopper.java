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
		int i;

		for (i = 0; i < inv.getSizeInventory(); ++i)
		{
			this.addSlotToContainer(new Slot(inv, i, 44 + i * 18, 17));
		}

		PlayerInventory.buildInventoryLayout(this, playerInv, 8, 54, false, true);
	}

	@Override
	public ItemStack transferStackInSlotTFC(EntityPlayer player, int slotNum)
	{
		ItemStack origStack = null;
		Slot slot = (Slot)inventorySlots.get(slotNum);

		if(slot != null && slot.getHasStack())
		{
			ItemStack slotStack = slot.getStack();
			origStack = slotStack.copy();

			if(slotNum < 5)
			{
				if(!this.mergeItemStack(slotStack, 5, this.inventorySlots.size(), true))
					return null;
			}
			else
			{
				if (!this.mergeItemStack(slotStack, 0, 5, false))
					return null;
			}

			if (slotStack.stackSize <= 0)
				slot.putStack(null);
			else
				slot.onSlotChanged();

			if (slotStack.stackSize == origStack.stackSize)
				return null;

			slot.onPickupFromSlot(player, slotStack);
		}

		return origStack;
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
