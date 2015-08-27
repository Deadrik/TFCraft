package com.bioxx.tfc.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.bioxx.tfc.Containers.Slots.SlotSluice;
import com.bioxx.tfc.Core.Player.PlayerInventory;
import com.bioxx.tfc.TileEntities.TESluice;

public class ContainerSluice extends ContainerTFC
{
	private TESluice sluice;
	private EntityPlayer player;
	private int soilamt;
	private int progress;

	public ContainerSluice(InventoryPlayer inventoryplayer, TESluice tileentitysluice, World world, int x, int y, int z)
	{
		sluice = tileentitysluice;
		player = inventoryplayer.player;
		addSlotToContainer(new SlotSluice(player, sluice, 0, 116, 16));
		addSlotToContainer(new SlotSluice(player, sluice, 1, 134, 16));
		addSlotToContainer(new SlotSluice(player, sluice, 2, 152, 16));
		addSlotToContainer(new SlotSluice(player, sluice, 3, 116, 34));
		addSlotToContainer(new SlotSluice(player, sluice, 4, 134, 34));
		addSlotToContainer(new SlotSluice(player, sluice, 5, 152, 34));
		addSlotToContainer(new SlotSluice(player, sluice, 6, 116, 52));
		addSlotToContainer(new SlotSluice(player, sluice, 7, 134, 52));
		addSlotToContainer(new SlotSluice(player, sluice, 8, 152, 52));
		PlayerInventory.buildInventoryLayout(this, inventoryplayer, 8, 90, false, true);
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return true;
	}

	@Override
	public ItemStack transferStackInSlotTFC(EntityPlayer player, int slotNum)
	{
		ItemStack origStack = null;
		Slot slot = (Slot) inventorySlots.get(slotNum);

		if(slot != null && slot.getHasStack())
		{
			ItemStack slotStack = slot.getStack();
			origStack = slotStack.copy();

			// Slots are output only, so there's no need to do input logic.
			if (slotNum < 9)
			{
				if (!this.mergeItemStack(slotStack, 9, this.inventorySlots.size(), true))
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
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		for (int var1 = 0; var1 < this.inventorySlots.size(); ++var1)
		{
			ItemStack var2 = ((Slot)this.inventorySlots.get(var1)).getStack();
			ItemStack var3 = (ItemStack)this.inventoryItemStacks.get(var1);

			if (!ItemStack.areItemStacksEqual(var3, var2))
			{
				var3 = var2 == null ? null : var2.copy();
				this.inventoryItemStacks.set(var1, var3);

				for (int var4 = 0; var4 < this.crafters.size(); ++var4)
					((ICrafting)this.crafters.get(var4)).sendSlotContents(this, var1, var3);
			}
		}
		for (int var1 = 0; var1 < this.crafters.size(); ++var1)
		{
			ICrafting var2 = (ICrafting)this.crafters.get(var1);
			if (this.soilamt != this.sluice.soilAmount)
				var2.sendProgressBarUpdate(this, 0, this.sluice.soilAmount);
			if (this.progress != this.sluice.processTimeRemaining)
				var2.sendProgressBarUpdate(this, 1, this.sluice.processTimeRemaining);
		}

		soilamt = this.sluice.soilAmount;
		progress = this.sluice.processTimeRemaining;
	}

	@Override
	public void updateProgressBar(int par1, int par2)
	{
		if (par1 == 0)
			this.sluice.soilAmount = par2;
		if (par1 == 1)
			this.sluice.processTimeRemaining = par2;
	}
}
