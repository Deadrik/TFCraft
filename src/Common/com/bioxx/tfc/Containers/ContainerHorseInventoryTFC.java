package com.bioxx.tfc.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.bioxx.tfc.Containers.Slots.ContainerHorseInventorySlotArmor;
import com.bioxx.tfc.Containers.Slots.ContainerHorseInventorySlotSaddle;
import com.bioxx.tfc.Containers.Slots.SlotChest;
import com.bioxx.tfc.Core.Player.PlayerInventory;
import com.bioxx.tfc.Entities.Mobs.EntityHorseTFC;

public class ContainerHorseInventoryTFC extends ContainerTFC
{
	private IInventory horseInv;
	private EntityHorseTFC horse;

	public ContainerHorseInventoryTFC(InventoryPlayer invPlayer, IInventory invHorse, EntityHorseTFC entityHorse)
	{
		this.horseInv = invHorse;
		this.horse = entityHorse;
		invHorse.openInventory();
		this.addSlotToContainer(new ContainerHorseInventorySlotSaddle(this, invHorse, 0, 8, 18)); // Saddle Slot
		this.addSlotToContainer(new ContainerHorseInventorySlotArmor(this, horseInv, 1, 8, 36, horse)); // Armor Slot

		if (entityHorse.isChested()) //Add slots for chested donkey
		{

			for (int j = 0; j < 3; ++j)
			{
				for (int k = 0; k < 5; ++k)
				{
					this.addSlotToContainer(new SlotChest(invHorse, 2 + k + j * 5, 80 + k * 18, 18 + j * 18).addItemException(ContainerChestTFC.getExceptions()));
				}
			}
		}

		PlayerInventory.buildInventoryLayout(this, invPlayer, 8, 90, false, true);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return this.horseInv.isUseableByPlayer(player) && this.horse.isEntityAlive() && this.horse.getDistanceToEntity(player) < 8.0F;
	}

	/**
	 * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
	 */
	@Override
	public ItemStack transferStackInSlotTFC(EntityPlayer player, int slotNum)
	{
		ItemStack origStack = null;
		Slot slot = (Slot)this.inventorySlots.get(slotNum);

		if (slot != null && slot.getHasStack())
		{
			ItemStack slotStack = slot.getStack();
			origStack = slotStack.copy();
			int inventorySize = this.horseInv.getSizeInventory();

			if (slotNum < inventorySize)
			{
				if (!this.mergeItemStack(slotStack, inventorySize, this.inventorySlots.size(), true))
				{
					return null;
				}
			}
			else if (this.getSlot(1).isItemValid(slotStack) && !this.getSlot(1).getHasStack())
			{
				if (!this.mergeItemStack(slotStack, 1, 2, false))
				{
					return null;
				}
			}
			else if (this.getSlot(0).isItemValid(slotStack))
			{
				if (!this.mergeItemStack(slotStack, 0, 1, false))
				{
					return null;
				}
			}
			else if (inventorySize <= 2 || !this.mergeItemStack(slotStack, 2, inventorySize, false))
			{
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

	/**
	 * Called when the container is closed.
	 */
	@Override
	public void onContainerClosed(EntityPlayer player)
	{
		super.onContainerClosed(player);
		this.horseInv.closeInventory();
		horse.updateChestSaddle();
	}
}
