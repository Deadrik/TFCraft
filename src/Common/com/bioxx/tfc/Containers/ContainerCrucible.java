package com.bioxx.tfc.Containers;

import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.Core.Player.PlayerInventory;
import com.bioxx.tfc.Items.ItemMeltedMetal;
import com.bioxx.tfc.TileEntities.TECrucible;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerCrucible extends ContainerTFC
{
	private TECrucible te;
	private float firetemp;

	public ContainerCrucible(InventoryPlayer inventoryplayer, TECrucible tileentityforge, World world, int x, int y, int z)
	{
		te = tileentityforge;
		firetemp = 0;
		//Input slot
		addSlotToContainer(new Slot(tileentityforge, 0, 152, 7));

		addSlotToContainer(new Slot(tileentityforge, 1, 152, 90));

		PlayerInventory.buildInventoryLayout(this, inventoryplayer, 8, 118, false, true);

		te.updateGui((byte) 0);
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return true;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int clickedIndex)
	{
		ItemStack returnedStack = null;
		Slot clickedSlot = (Slot)this.inventorySlots.get(clickedIndex);

		if (clickedSlot != null
				&& clickedSlot.getHasStack())
		{
			ItemStack clickedStack = clickedSlot.getStack();
			returnedStack = clickedStack.copy();

			if (clickedIndex <= 1)
			{
				if (!this.mergeItemStack(clickedStack, 2, inventorySlots.size(), true))
					return null;
			}
			else if (clickedIndex > 1 && clickedIndex < inventorySlots.size() && 
					((clickedStack.getItem() == TFCItems.CeramicMold && clickedStack.getItemDamage() == 1) || 
					clickedStack.getItem() instanceof ItemMeltedMetal))
			{
				if (!this.mergeItemStack(clickedStack, 1, 2, true))
					return null;
			}
			else if (clickedIndex > 1 && clickedIndex < inventorySlots.size())
			{
				if (!this.mergeItemStack(clickedStack, 0, 1, true))
					return null;
			}

			if (clickedStack.stackSize == 0)
				clickedSlot.putStack((ItemStack)null);
			else
				clickedSlot.onSlotChanged();

			if (clickedStack.stackSize == returnedStack.stackSize)
				return null;

			clickedSlot.onPickupFromSlot(player, clickedStack);
		}
		return returnedStack;
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
