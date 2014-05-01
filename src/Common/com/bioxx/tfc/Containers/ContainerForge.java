package com.bioxx.tfc.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.Containers.Slots.SlotForge;
import com.bioxx.tfc.Containers.Slots.SlotForgeFuel;
import com.bioxx.tfc.Core.Player.PlayerInventory;
import com.bioxx.tfc.Items.ItemOre;
import com.bioxx.tfc.TileEntities.TEForge;
import com.bioxx.tfc.api.HeatRegistry;

public class ContainerForge extends ContainerTFC
{
	private TEForge forge;
	private int coolTime;
	private int freezeTime;
	private int itemFreezeTime;
	private float firetemp;

	public ContainerForge(InventoryPlayer inventoryplayer, TEForge tileentityforge, World world, int x, int y, int z)
	{
		forge = tileentityforge;
		coolTime = 0;
		freezeTime = 0;
		itemFreezeTime = 0;

		//Input slot
		addSlotToContainer(new SlotForge(inventoryplayer.player,tileentityforge, 0, 44, 8));
		addSlotToContainer(new SlotForge(inventoryplayer.player,tileentityforge, 1, 62, 26));
		addSlotToContainer(new SlotForge(inventoryplayer.player,tileentityforge, 2, 80, 44));
		addSlotToContainer(new SlotForge(inventoryplayer.player,tileentityforge, 3, 98, 26));
		addSlotToContainer(new SlotForge(inventoryplayer.player,tileentityforge, 4, 116, 8));
		//fuel stack
		addSlotToContainer(new SlotForgeFuel(inventoryplayer.player, tileentityforge, 5, 44, 26));
		addSlotToContainer(new SlotForgeFuel(inventoryplayer.player, tileentityforge, 6, 62, 44));
		addSlotToContainer(new SlotForgeFuel(inventoryplayer.player, tileentityforge, 7, 80, 62));
		addSlotToContainer(new SlotForgeFuel(inventoryplayer.player, tileentityforge, 8, 98, 44));
		addSlotToContainer(new SlotForgeFuel(inventoryplayer.player, tileentityforge, 9, 116, 26));
		//Storage slot
		addSlotToContainer(new Slot(tileentityforge, 10, 152, 8));
		addSlotToContainer(new Slot(tileentityforge, 11, 152, 26));
		addSlotToContainer(new Slot(tileentityforge, 12, 152, 44));
		addSlotToContainer(new Slot(tileentityforge, 13, 152, 62));

		PlayerInventory.buildInventoryLayout(this, inventoryplayer, 8, 90, false, true);
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return true;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityplayer, int slotNum)
	{
		ItemStack origStack = null;
		Slot slot = (Slot)inventorySlots.get(slotNum);
		Slot[] slotinput = {(Slot)inventorySlots.get(2), (Slot)inventorySlots.get(1), (Slot)inventorySlots.get(3), (Slot)inventorySlots.get(0), (Slot)inventorySlots.get(4)};
		Slot[] slotstorage = {(Slot)inventorySlots.get(10), (Slot)inventorySlots.get(11), (Slot)inventorySlots.get(12), (Slot)inventorySlots.get(13)};
		Slot[] slotfuel = {(Slot)inventorySlots.get(7), (Slot)inventorySlots.get(6), (Slot)inventorySlots.get(8), (Slot)inventorySlots.get(5), (Slot)inventorySlots.get(9)};
		HeatRegistry manager = HeatRegistry.getInstance();

		if(slot != null && slot.getHasStack())
		{
			ItemStack slotStack = slot.getStack();
			origStack = slotStack.copy();

			if(slotNum <= 13)//If the player shift clicked one of the forge slots
			{
				if(!this.mergeItemStack(slotStack, 14, this.inventorySlots.size(), true))
					return null;
			}
			else //Otherwise the shift clicked an item in their inventory and are trying to put it in the forge.
			{
				if(slotStack.getItem() == TFCItems.Coal)
				{
					int j = 0;
					while(j < 5)
					{
						if(slotfuel[j].getHasStack())
						{
							j++;
						}
						else
						{
							ItemStack stack = slotStack.copy();
							stack.stackSize = 1;
							slotfuel[j].putStack(stack);
							slotStack.stackSize--;
							j = -1;
							break;
						}
					}
					if (j > 0)
					{
						j = 0;
						while(j < 4)
						{
							if(slotstorage[j].getHasStack())
							{
								j++;
							}
							else
							{
								ItemStack stack = slotStack.copy();
								stack.stackSize = 1;
								slotstorage[j].putStack(stack);
								slotStack.stackSize--;
								break;
							}
						}
					}
				}
				else if(!(slotStack.getItem() instanceof ItemOre) && manager.findMatchingIndex(slotStack) != null)//Try to add the item to the input slots
				{
					int j = 0;
					while(j < 5)
					{
						if(slotinput[j].getHasStack())
						{
							j++;
						}
						else if(slotStack != null)
						{
							ItemStack stack = slotStack.copy();
							stack.stackSize = 1;
							slotinput[j].putStack(stack);
							slotStack.stackSize--;
							j = -1;
							break;
						}
					}
					if (j > 0)
					{
						j = 0;
						while(j < 4)
						{
							if(slotstorage[j].getHasStack())
							{
								j++;
							}
							else
							{
								ItemStack stack = slotStack.copy();
								stack.stackSize = 1;
								slotstorage[j].putStack(stack);
								slotStack.stackSize--;
								break;
							}
						}
					}
				}
				else//Try to store the item in the side storage slots.
				{
					int j = 0;
					while(j < 4)
					{
						if(slotstorage[j].getHasStack())
						{
							j++;
						}
						else
						{
							ItemStack stack = slotStack.copy();
							stack.stackSize = 1;
							slotstorage[j].putStack(stack);
							slotStack.stackSize--;
							break;
						}
					}
				}
			}

			if(slotStack.stackSize <= 0)
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
			if (this.firetemp != this.forge.fireTemp)
				var2.sendProgressBarUpdate(this, 0, (int)this.forge.fireTemp);
		}

		firetemp = this.forge.fireTemp;
	}

	@Override
	public void updateProgressBar(int par1, int par2)
	{
		if (par1 == 0)
			this.forge.fireTemp = par2;
	}
}
