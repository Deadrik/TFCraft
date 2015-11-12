package com.bioxx.tfc.Containers;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

import com.bioxx.tfc.Containers.Slots.SlotForShowOnly;
import com.bioxx.tfc.Containers.Slots.SlotSizeSmallVessel;
import com.bioxx.tfc.TileEntities.TEIngotPile;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Interfaces.IFood;

public class ContainerVessel extends ContainerTFC
{
	private World world;
	public InventoryCrafting containerInv = new InventoryCrafting(this, 2, 2);
	private List<Item> exceptions;
	
	public ContainerVessel(InventoryPlayer playerinv, World world, int x, int y, int z)
	{
		this.player = playerinv.player;
		this.world = world;
		bagsSlotNum = player.inventory.currentItem;
		exceptions = new ArrayList<Item>();
		for (Item ingot : TEIngotPile.getIngots())
		{
			exceptions.add(ingot);
		}
		layoutContainer(playerinv, 0, 0);

		if(!world.isRemote)
			loadBagInventory();
		this.doItemSaving = true;
	}

	@Override
	public void reloadContainer()
	{
		if(!world.isRemote)
			loadBagInventory();
	}

	public void loadBagInventory()
	{
		if(player.inventory.getStackInSlot(bagsSlotNum) != null && 
				player.inventory.getStackInSlot(bagsSlotNum).hasTagCompound())
		{
			NBTTagList nbttaglist = player.inventory.getStackInSlot(bagsSlotNum).getTagCompound().getTagList("Items", 10);
			for(int i = 0; i < nbttaglist.tagCount(); i++)
			{
                this.isLoading = true;
				NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
				byte byte0 = nbttagcompound1.getByte("Slot");
				if(byte0 >= 0 && byte0 < 4)
				{
					ItemStack is = ItemStack.loadItemStackFromNBT(nbttagcompound1);
					if(is.stackSize >= 1)
						this.containerInv.setInventorySlotContents(byte0, is);
					else
						this.containerInv.setInventorySlotContents(byte0, null);
				}
			}
		}
	}

	/**
	 * Callback for when the crafting gui is closed.
	 */
	/*	@Override
		public void onContainerClosed(EntityPlayer player)
		{
			super.onContainerClosed(player);
	//		if(ContainerTFC.areItemStacksEqual(bagStack, player.inventory.getCurrentItem())) {
	//			saveContents(player.inventory.getStackInSlot(bagsSlotNum));
	//		}
		}*/

	@Override
	public void saveContents(ItemStack is)
	{
		NBTTagList nbttaglist = new NBTTagList();
		for(int i = 0; i < containerInv.getSizeInventory(); i++)
		{
			ItemStack contentStack = containerInv.getStackInSlot(i);
			if (contentStack != null && contentStack.getItem() instanceof IFood)
			{
				if (Food.getDecay(contentStack) / Global.FOOD_MAX_WEIGHT > 0.9f)
					containerInv.setInventorySlotContents(i, null);
			}
			if (contentStack != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				contentStack.writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		if(is != null)
		{
			if(!is.hasTagCompound())
				is.setTagCompound(new NBTTagCompound());
			is.getTagCompound().setTag("Items", nbttaglist);
		}
	}

	@Override
	public ItemStack loadContents(int slot) 
	{
		if(player.inventory.getStackInSlot(bagsSlotNum) != null && 
				player.inventory.getStackInSlot(bagsSlotNum).hasTagCompound())
		{
			NBTTagList nbttaglist = player.inventory.getStackInSlot(bagsSlotNum).getTagCompound().getTagList("Items", 10);
			if(nbttaglist != null)
			{
				for(int i = 0; i < nbttaglist.tagCount(); i++)
				{
					NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
					byte byte0 = nbttagcompound1.getByte("Slot");
					if(byte0 == slot)
						return ItemStack.loadItemStackFromNBT(nbttagcompound1);
				}
			}
		}
		return null;
	}

	@Override
	public boolean canInteractWith(EntityPlayer var1)
	{
		return true;
	}

	protected void layoutContainer(IInventory playerInventory, int xSize, int ySize)
	{
		this.addSlotToContainer(new SlotSizeSmallVessel(containerInv, 0, 71, 25).addItemException(exceptions));
		this.addSlotToContainer(new SlotSizeSmallVessel(containerInv, 1, 89, 25).addItemException(exceptions));
		this.addSlotToContainer(new SlotSizeSmallVessel(containerInv, 2, 71, 43).addItemException(exceptions));
		this.addSlotToContainer(new SlotSizeSmallVessel(containerInv, 3, 89, 43).addItemException(exceptions));

		int row;
		int col;

		for (row = 0; row < 9; ++row)
		{
			if(row == bagsSlotNum)
				this.addSlotToContainer(new SlotForShowOnly(playerInventory, row, 8 + row * 18, 148));
			else
				this.addSlotToContainer(new Slot(playerInventory, row, 8 + row * 18, 148));
		}

		for (row = 0; row < 3; ++row)
		{
			for (col = 0; col < 9; ++col)
				this.addSlotToContainer(new Slot(playerInventory, col + row * 9+9, 8 + col * 18, 90 + row * 18));
		}
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

			if (slotNum < 4) {
				if (!this.mergeItemStack(slotStack, 4, inventorySlots.size(), true))
					return null;
			}
			else 
			{
				if (!this.mergeItemStack(slotStack, 0, 4, false))
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
}
