package net.minecraft.src.TFC_Game;

import net.minecraft.src.*;
import net.minecraft.src.TFC_Core.ItemTerraSmallOre;

public class ContainerTerraForge extends Container
{
	private TileEntityTerraForge firepit;
	private int coolTime;
	private int freezeTime;
	private int itemFreezeTime;


	public ContainerTerraForge(InventoryPlayer inventoryplayer, TileEntityTerraForge tileentityforge)
	{
		firepit = tileentityforge;

		coolTime = 0;
		freezeTime = 0;
		itemFreezeTime = 0;

		//Input slot
		addSlot(new SlotForge(inventoryplayer.player,tileentityforge, 0, 44, 8));
		addSlot(new SlotForge(inventoryplayer.player,tileentityforge, 1, 62, 26));
		addSlot(new SlotForge(inventoryplayer.player,tileentityforge, 2, 80, 44));
		addSlot(new SlotForge(inventoryplayer.player,tileentityforge, 3, 98, 26));
		addSlot(new SlotForge(inventoryplayer.player,tileentityforge, 4, 116, 8));
		//fuel stack
		addSlot(new SlotForgeFuel(inventoryplayer.player, tileentityforge, 5, 44, 26));
		addSlot(new SlotForgeFuel(inventoryplayer.player, tileentityforge, 6, 62, 44));
		addSlot(new SlotForgeFuel(inventoryplayer.player, tileentityforge, 7, 80, 62));
		addSlot(new SlotForgeFuel(inventoryplayer.player, tileentityforge, 8, 98, 44));
		addSlot(new SlotForgeFuel(inventoryplayer.player, tileentityforge, 9, 116, 26));
		//Storage slot
		addSlot(new Slot(tileentityforge, 10, 152, 8));
		addSlot(new Slot(tileentityforge, 11, 152, 26));
		addSlot(new Slot(tileentityforge, 12, 152, 44));
		addSlot(new Slot(tileentityforge, 13, 152, 62));

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
				ItemStack itemstack1 = transferStackInSlot(i);
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
						if (itemstack2.itemID != itemstack3.itemID || itemstack2.getHasSubtypes() && 
								itemstack2.getItemDamage() != itemstack3.getItemDamage() || !mod_TFC_Core.proxy.areItemStacksEqual(itemstack2, itemstack3))
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


	public ItemStack transferStackInSlot(int i)
	{
		Slot slot = (Slot)inventorySlots.get(i);
		Slot[] slotinput = {(Slot)inventorySlots.get(2), (Slot)inventorySlots.get(1), (Slot)inventorySlots.get(3), (Slot)inventorySlots.get(0), (Slot)inventorySlots.get(4)};
		Slot[] slotstorage = {(Slot)inventorySlots.get(10), (Slot)inventorySlots.get(11), (Slot)inventorySlots.get(12), (Slot)inventorySlots.get(13)};
		Slot[] slotfuel = {(Slot)inventorySlots.get(7), (Slot)inventorySlots.get(6), (Slot)inventorySlots.get(8), (Slot)inventorySlots.get(5), (Slot)inventorySlots.get(9)};
		if(slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			if(i <= 13)
			{
				//commented out in order to get everything to compile
//				if(!ModLoader.getMinecraftInstance().thePlayer.inventory.addItemStackToInventory(itemstack1.copy()))
//				{
//					return null;
//				}
				slot.putStack(null);
			}
			else
			{
				if(itemstack1.itemID == Item.coal.shiftedIndex)
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
							ItemStack stack = itemstack1.copy();
							stack.stackSize = 1;
							slotfuel[j].putStack(stack);
							itemstack1.stackSize--;
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
								ItemStack stack = itemstack1.copy();
								stack.stackSize = 1;
								slotstorage[j].putStack(stack);
								itemstack1.stackSize--;
								break;
							}
						}
					}
				}
				else if(!(itemstack1.getItem() instanceof ItemTerraSmallOre))
				{
					int j = 0;
					while(j < 5)
					{
						if(slotinput[j].getHasStack())
						{
							j++;
						}
						else
						{
							ItemStack stack = itemstack1.copy();
							stack.stackSize = 1;
							slotinput[j].putStack(stack);
							itemstack1.stackSize--;
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
								ItemStack stack = itemstack1.copy();
								stack.stackSize = 1;
								slotstorage[j].putStack(stack);
								itemstack1.stackSize--;
								break;
							}
						}
					}
				}
				else
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
							ItemStack stack = itemstack1.copy();
							stack.stackSize = 1;
							slotstorage[j].putStack(stack);
							itemstack1.stackSize--;
							break;
						}
					}
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



	public void updateCraftingResults()
	{
		super.updateCraftingResults();
		for(int i = 0; i < inventorySlots.size(); i++)
		{
			ICrafting icrafting = (ICrafting)inventorySlots.get(i);
		}
	}
}
