package com.bioxx.tfc.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.bioxx.tfc.Containers.Slots.SlotLiquidVessel;
import com.bioxx.tfc.Core.Player.PlayerInventory;
import com.bioxx.tfc.Items.ItemMeltedMetal;
import com.bioxx.tfc.TileEntities.TECrucible;
import com.bioxx.tfc.api.TFCItems;

public class ContainerCrucible extends ContainerTFC
{
	private TECrucible te;
	private float firetemp;

	public ContainerCrucible(InventoryPlayer inventoryplayer, TECrucible tileentityforge, World world, int x, int y, int z)
	{
		te = tileentityforge;
		firetemp = 0;
		//Input slot
		addSlotToContainer(new Slot(tileentityforge, 0, 152, 7)
		{
			@Override
			public boolean isItemValid(ItemStack itemstack)
			{
				return !(itemstack.getItem() == TFCItems.rawBloom || itemstack.getItem() == TFCItems.bloom && itemstack.getItemDamage() > 100);
			}
		});

		addSlotToContainer(new SlotLiquidVessel(tileentityforge, 1, 152, 90));

		PlayerInventory.buildInventoryLayout(this, inventoryplayer, 8, 118, false, true);

		te.updateGui((byte) 0);
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
		Slot slot = (Slot)this.inventorySlots.get(slotNum);

		if (slot != null && slot.getHasStack())
		{
			ItemStack slotStack = slot.getStack();
			origStack = slotStack.copy();

			// From crucible to inventory
			if (slotNum < 2)
			{
				if (!this.mergeItemStack(slotStack, 2, inventorySlots.size(), true))
					return null;
			}
			// Ceramic molds into output slot
			else if (slotStack.getItem() == TFCItems.ceramicMold && slotStack.getItemDamage() == 1 || slotStack.getItem() instanceof ItemMeltedMetal)
			{
				if (!this.mergeItemStack(slotStack, 1, 2, true))
					return null;
			}
			// To input slot
			else
			{
				if (!this.mergeItemStack(slotStack, 0, 1, true))
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

		for (int var1 = 0; var1 < this.crafters.size(); ++var1)
		{
			ICrafting var2 = (ICrafting)this.crafters.get(var1);
			if (this.firetemp != this.te.temperature)
				var2.sendProgressBarUpdate(this, 0, this.te.temperature);
		}
	}

	@Override
	public void updateProgressBar(int id, int value)
	{
		if (id == 0)
			this.te.temperature = value;
	}
}
