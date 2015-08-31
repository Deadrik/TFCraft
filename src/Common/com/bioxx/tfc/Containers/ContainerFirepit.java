package com.bioxx.tfc.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.bioxx.tfc.Containers.Slots.*;
import com.bioxx.tfc.Core.Player.PlayerInventory;
import com.bioxx.tfc.Items.ItemOre;
import com.bioxx.tfc.TileEntities.TEFirepit;
import com.bioxx.tfc.api.HeatRegistry;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;

public class ContainerFirepit extends ContainerTFC
{
	private TEFirepit firepit;
	private float firetemp;

	public ContainerFirepit(InventoryPlayer inventoryplayer, TEFirepit tileentityfirepit, World world, int x, int y, int z)
	{
		firepit = tileentityfirepit;
		firetemp = -1111;

		//Input slot
		addSlotToContainer(new SlotFirepitIn(inventoryplayer.player,tileentityfirepit, 1, 80, 20));
		//fuel stack
		addSlotToContainer(new SlotFirepitFuel(inventoryplayer.player, tileentityfirepit, 0, 8, 8));
		addSlotToContainer(new SlotFirepit(inventoryplayer.player, tileentityfirepit, 3, 8, 26));
		addSlotToContainer(new SlotFirepit(inventoryplayer.player, tileentityfirepit, 4, 8, 44));
		addSlotToContainer(new SlotFirepit(inventoryplayer.player, tileentityfirepit, 5, 8, 62));

		//item output
		addSlotToContainer(new SlotFirepitOut(inventoryplayer.player, tileentityfirepit, 7, 71, 48));
		addSlotToContainer(new SlotFirepitOut(inventoryplayer.player, tileentityfirepit, 8, 89, 48));

		//dummy byproducts out
		addSlotToContainer(new SlotForShowOnly(tileentityfirepit, 2, -50000, 0));
		addSlotToContainer(new SlotForShowOnly(tileentityfirepit, 6, -50000, 0));
		addSlotToContainer(new SlotForShowOnly(tileentityfirepit, 9, -50000, 0));
		addSlotToContainer(new SlotForShowOnly(tileentityfirepit, 10, -50000, 0));

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
		Slot slotinput = (Slot)inventorySlots.get(0);
		Slot[] slotfuel = {(Slot)inventorySlots.get(1), (Slot)inventorySlots.get(3), (Slot)inventorySlots.get(4), (Slot)inventorySlots.get(5)};

		if(slot != null && slot.getHasStack())
		{
			ItemStack slotStack = slot.getStack();
			origStack = slotStack.copy();

			// From firepit to inventory
			if (slotNum < 11)
			{
				if (!this.mergeItemStack(slotStack, 11, this.inventorySlots.size(), true))
					return null;
			}
			else
			{
				HeatRegistry manager = HeatRegistry.getInstance();

				// Fuel to the fuel input slot
				if (slotStack.getItem() == TFCItems.logs || slotStack.getItem() == Item.getItemFromBlock(TFCBlocks.peat))
				{
					if(slotfuel[0].getHasStack())
						return null;
					ItemStack stack = slotStack.copy();
					stack.stackSize = 1;
					slotfuel[0].putStack(stack);
					slotStack.stackSize--;
				}
				// No ores, but anything else with a heat index to the input slot
				else if (!(slotStack.getItem() instanceof ItemOre) && manager.findMatchingIndex(slotStack) != null)
				{
					if(slotinput.getHasStack())
						return null;
					ItemStack stack = slotStack.copy();
					stack.stackSize = 1;
					slotinput.putStack(stack);
					slotStack.stackSize--;
				}
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
			if (this.firetemp != this.firepit.fireTemp)
				var2.sendProgressBarUpdate(this, 0, (int)this.firepit.fireTemp);
		}

		firetemp = this.firepit.fireTemp;
	}

	@Override
	public void updateProgressBar(int par1, int par2)
	{
		if (par1 == 0)
			this.firepit.fireTemp = par2;
	}
}
