package net.minecraft.src.TFC_Game;

import net.minecraft.src.*;
import net.minecraft.src.TFC_Core.TFCHeat;

public class ContainerTerraMetallurgy extends Container
{
	private TileEntityTerraMetallurgy terrametallurgy;
	public InventoryCrafting craftMatrix;
	public IInventory craftResult;
	private World worldObj;

	public ContainerTerraMetallurgy(InventoryPlayer inventoryplayer, TileEntityTerraMetallurgy scribe, World world)
	{
		terrametallurgy = scribe;
		craftMatrix = new InventoryCrafting(this, 5, 5);
		craftResult = new InventoryCraftResult();
		worldObj = world;
		//output
		addSlot(new SlotCraftingMetal(inventoryplayer.player, craftMatrix, craftResult,0, 128, 35));

		for (int l = 0; l < 5; l++)
		{
			for (int k1 = 0; k1 < 5; k1++)
			{
				addSlot(new SlotMetal(craftMatrix, k1 + l * 5, 8 + k1 * 18, l * 18 - 1));
			}
		}

		for(int i = 0; i < 3; i++)
		{
			for(int k = 0; k < 9; k++)
			{
				addSlot(new Slot(inventoryplayer, k + i * 9 + 9, 8 + k * 18, 93 + i * 18));
			}

		}

		for(int j = 0; j < 9; j++)
		{
			addSlot(new Slot(inventoryplayer, j, 8 + j * 18, 151));
		}


		onCraftMatrixChanged(craftMatrix);
	}

	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return true;
	}

	public void onCraftGuiClosed(EntityPlayer entityplayer)
	{
		super.onCraftGuiClosed(entityplayer);
		if (worldObj.isRemote)
		{
			return;
		}
		for (int i = 0; i < 25; i++)
		{
			ItemStack itemstack = craftMatrix.getStackInSlot(i);
			if (itemstack != null)
			{
				entityplayer.dropPlayerItem(itemstack);
			}
		}
	}

	public void onCraftMatrixChanged(IInventory iinventory)
	{
		float temp = terrametallurgy.checkTemps(craftMatrix);
		if(temp >= 0)
		{
			ItemStack stack = CraftingManagerTFC.getInstance().findMatchingRecipe(craftMatrix);
			if (stack != null)
			{
				if(!stack.hasTagCompound()) {
					stack.setTagCompound(new NBTTagCompound());
				}

				stack.stackTagCompound.setFloat("temperature", temp);
				if(stack.stackSize <= 0) {
					stack.stackSize = 1;
				}
			}
			craftResult.setInventorySlotContents(0, stack);
		}
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
		if(slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			if(i == 0)
			{
				if(!entityplayer.inventory.addItemStackToInventory(itemstack1.copy()))
				{
					return null;
				}
				slot.putStack(null);
				for (int j = 1; j <= 25; j++)
				{
					((Slot)inventorySlots.get(j)).putStack(null);
				}
			}
			else if(i <= 25)
			{
				if(!entityplayer.inventory.addItemStackToInventory(itemstack1.copy()))
				{
					return null;
				}
				slot.putStack(null);
			}
			else if(itemstack1.getItem() instanceof ItemTerraMeltedMetal)
			{
				int j = 0;
				while(j < 25)
				{
					if(((Slot)inventorySlots.get(j + 1)).getHasStack())
					{
						j++;
					}
					else
					{
						ItemStack stack = itemstack1.copy();
						stack.stackSize = 1;
						((Slot)inventorySlots.get(j + 1)).putStack(stack);
						itemstack1.stackSize--;
						break;
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

}
