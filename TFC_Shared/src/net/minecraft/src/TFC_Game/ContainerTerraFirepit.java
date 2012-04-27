package net.minecraft.src.TFC_Game;

import net.minecraft.src.*;

public class ContainerTerraFirepit extends Container
{
	private TileEntityTerraFirepit firepit;
	private int coolTime;
	private int freezeTime;
	private int itemFreezeTime;


	public ContainerTerraFirepit(InventoryPlayer inventoryplayer, TileEntityTerraFirepit tileentityfirepit)
	{
		firepit = tileentityfirepit;

		coolTime = 0;
		freezeTime = 0;
		itemFreezeTime = 0;

		//fuel in slot
		//addSlot(new SlotFirepitFuel(inventoryplayer.player,tileentityfirepit, 0, 44, 8));
		//Input slot
		addSlot(new Slot(tileentityfirepit, 1, 80, 20));
		//fuel stack
		addSlot(new SlotFirepitFuel(inventoryplayer.player, tileentityfirepit, 0, 8, 8));
		addSlot(new SlotFirepit(inventoryplayer.player, tileentityfirepit, 3, 8, 26));
		addSlot(new SlotFirepit(inventoryplayer.player, tileentityfirepit, 4, 8, 44));
		addSlot(new SlotFirepit(inventoryplayer.player, tileentityfirepit, 5, 8, 62));

		//item output
		addSlot(new SlotFirepitOut(inventoryplayer.player, tileentityfirepit, 7, 71, 48));
		addSlot(new SlotFirepitOut(inventoryplayer.player, tileentityfirepit, 8, 89, 48));

		//byproducts out
		addSlot(new SlotFirepit(inventoryplayer.player, tileentityfirepit, 2, 127, 23));
		addSlot(new SlotFirepit(inventoryplayer.player, tileentityfirepit, 6, 145, 23));
		addSlot(new SlotFirepit(inventoryplayer.player, tileentityfirepit, 9, 127, 41));
		addSlot(new SlotFirepit(inventoryplayer.player, tileentityfirepit, 10, 145, 41));


		//slag output
		//addSlot(new SlotFirepit(inventoryplayer.player, tileentityfirepit, 9, 98, 62));

		for(int i = 0; i < 3; i++)
		{
			for(int k = 0; k < 9; k++)
			{
				addSlot(new Slot(inventoryplayer, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
			}

		}

		for(int j = 0; j < 9; j++)
		{
			addSlot(new Slot(inventoryplayer, j, 8 + j * 18, 142));
		}

	}

	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return true;
	}


	public ItemStack slotClick(int i, int j, boolean flag, EntityPlayer entityplayer)
	{
		ItemStack itemstack = null;
		if (j > 1)
		{
			return null;
		}
		if (j == 0 || j == 1)
		{
			InventoryPlayer inventoryplayer = entityplayer.inventory;
			if (i == -999)
			{
				if (inventoryplayer.getItemStack() != null && i == -999)
				{
					if (j == 0)
					{
						entityplayer.dropPlayerItem(inventoryplayer.getItemStack());
						inventoryplayer.setItemStack(null);
					}
					if (j == 1)
					{
						entityplayer.dropPlayerItem(inventoryplayer.getItemStack().splitStack(1));
						if (inventoryplayer.getItemStack().stackSize == 0)
						{
							inventoryplayer.setItemStack(null);
						}
					}
				}
			}
			else if (flag)
			{
				ItemStack itemstack1 = playerTransferStackInSlot(i, entityplayer);
				if (itemstack1 != null)
				{
					int k = itemstack1.itemID;
					itemstack = itemstack1.copy();
					Slot slot1 = (Slot)inventorySlots.get(i);
					if (slot1 != null && slot1.getStack() != null && slot1.getStack().itemID == k)
					{
						retrySlotClick(i, j, flag, entityplayer);
					}
				}
			}
			else
			{
				if (i < 0)
				{
					return null;
				}
				Slot slot = (Slot)inventorySlots.get(i);
				if (slot != null)
				{
					slot.onSlotChanged();
					ItemStack itemstack2 = slot.getStack();
					ItemStack itemstack3 = inventoryplayer.getItemStack();
					if (itemstack2 != null)
					{
						itemstack = itemstack2.copy();
					}
					if (itemstack2 == null)
					{
						if (itemstack3 != null && slot.isItemValid(itemstack3))
						{
							int l = j != 0 ? 1 : itemstack3.stackSize;
							if (l > slot.getSlotStackLimit())
							{
								l = slot.getSlotStackLimit();
							}
							slot.putStack(itemstack3.splitStack(l));
							if (itemstack3.stackSize == 0)
							{
								inventoryplayer.setItemStack(null);
							}
						}
					}
					else if (itemstack3 == null)
					{
						int i1 = j != 0 ? (itemstack2.stackSize + 1) / 2 : itemstack2.stackSize;
						ItemStack itemstack5 = slot.decrStackSize(i1);
						inventoryplayer.setItemStack(itemstack5);
						if (itemstack2.stackSize == 0)
						{
							slot.putStack(null);
						}
						slot.onPickupFromSlot(inventoryplayer.getItemStack());
					}
					else if (slot.isItemValid(itemstack3))
					{
						if (itemstack2.itemID != itemstack3.itemID || itemstack2.getHasSubtypes() && itemstack2.getItemDamage() != itemstack3.getItemDamage() || !mod_TFC_Core.proxy.areItemStacksEqual(itemstack2, itemstack3))
						{
							if (itemstack3.stackSize <= slot.getSlotStackLimit())
							{
								ItemStack itemstack4 = itemstack2;
								slot.putStack(itemstack3);
								inventoryplayer.setItemStack(itemstack4);
							}
						}
						else
						{
							int j1 = j != 0 ? 1 : itemstack3.stackSize;
							if (j1 > slot.getSlotStackLimit() - itemstack2.stackSize)
							{
								j1 = slot.getSlotStackLimit() - itemstack2.stackSize;
							}
							if (j1 > itemstack3.getMaxStackSize() - itemstack2.stackSize)
							{
								j1 = itemstack3.getMaxStackSize() - itemstack2.stackSize;
							}
							itemstack3.splitStack(j1);
							if (itemstack3.stackSize == 0)
							{
								inventoryplayer.setItemStack(null);
							}
							itemstack2.stackSize += j1;
						}
					}
					else if (itemstack2.itemID == itemstack3.itemID && itemstack3.getMaxStackSize() > 1 && (!itemstack2.getHasSubtypes() || itemstack2.getItemDamage() == itemstack3.getItemDamage()) && mod_TFC_Core.proxy.areItemStacksEqual(itemstack2, itemstack3))
					{
						int k1 = itemstack2.stackSize;
						if (k1 > 0 && k1 + itemstack3.stackSize <= itemstack3.getMaxStackSize())
						{
							itemstack3.stackSize += k1;
							itemstack2.splitStack(k1);
							if (itemstack2.stackSize == 0)
							{
								slot.putStack(null);
							}
							slot.onPickupFromSlot(inventoryplayer.getItemStack());
						}
					}
				}
			}
		}
		return itemstack;
	}


	public ItemStack playerTransferStackInSlot(int i, EntityPlayer entityplayer)
	{
		Slot slot = (Slot)inventorySlots.get(i);
		Slot slotinput = (Slot)inventorySlots.get(0);
		Slot slotfuel = (Slot)inventorySlots.get(1);
		if(slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			if(i <= 10)
			{
				if(!entityplayer.inventory.addItemStackToInventory(itemstack1.copy()))
				{
					return null;
				}
				slot.putStack(null);
			}
			else
			{
				if(itemstack1.itemID == mod_TFC_Core.Logs.shiftedIndex)
				{
					if(slotfuel.getHasStack())
					{
						return null;
					}
					ItemStack stack = itemstack1.copy();
					stack.stackSize = 1;
					slotfuel.putStack(stack);
					itemstack1.stackSize--;
				}
				else
				{
					if(slotinput.getHasStack())
					{
						return null;
					}
					ItemStack stack = itemstack1.copy();
					stack.stackSize = 1;
					slotinput.putStack(stack);
					itemstack1.stackSize--;
				}
			}
			if(itemstack1.stackSize == 0)
			{
				slot.putStack(null);
			} else
			{
				slot.onSlotChanged();
			}
		}
		return null;
	}



//	public void updateCraftingResults()
//	{
//		super.updateCraftingResults();
//		for(int i = 0; i < inventorySlots.size(); i++)
//		{
//			ICrafting icrafting = (ICrafting)inventorySlots.get(i);
//		}
//	}
}
