package com.bioxx.tfc.Containers;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.bioxx.tfc.Containers.Slots.SlotChest;
import com.bioxx.tfc.Core.Player.PlayerInventory;
import com.bioxx.tfc.TileEntities.TEChest;
import com.bioxx.tfc.TileEntities.TEIngotPile;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;

public class ContainerChestTFC extends ContainerTFC
{
	private IInventory lowerChestInventory;
	private int numRows;

	public ContainerChestTFC(IInventory playerInv, IInventory chestInv, World world, int x, int y, int z)
	{
		TEChest chest = (TEChest)chestInv;
		this.lowerChestInventory = chestInv;

		if (chest.adjacentChestXNeg != null)
			lowerChestInventory = new InventoryLargeChest("Large chest", chest.adjacentChestXNeg, chestInv);

		if (chest.adjacentChestXPos != null)
			lowerChestInventory = new InventoryLargeChest("Large chest", chestInv, chest.adjacentChestXPos);

		if (chest.adjacentChestZNeg != null)
			lowerChestInventory = new InventoryLargeChest("Large chest", chest.adjacentChestZNeg, chestInv);

		if (chest.adjacentChestZPos != null)
			lowerChestInventory = new InventoryLargeChest("Large chest", chestInv, chest.adjacentChestZPos);

		this.numRows = lowerChestInventory.getSizeInventory() / 9;
		lowerChestInventory.openInventory();
		int var3 = (this.numRows - 4) * 18;
		int var4;
		int var5;



		for (var4 = 0; var4 < this.numRows; ++var4)
		{
			for (var5 = 0; var5 < 9; ++var5)
			{
				this.addSlotToContainer(new SlotChest(lowerChestInventory, var5 + var4 * 9, 8 + var5 * 18, 18 + var4 * 18).addItemException(getExceptions()));
			}
		}

		PlayerInventory.buildInventoryLayout(this, (InventoryPlayer) playerInv, 8, var3 + 109, false, true);
	}

	public static List<Item> getExceptions()
	{
		List<Item> exceptions = new ArrayList<Item>();
		for (Item ingot : TEIngotPile.getIngots())
		{
			exceptions.add(ingot);
		}
		exceptions.add(TFCItems.logs);
		exceptions.add(Item.getItemFromBlock(TFCBlocks.barrel));
		exceptions.add(Item.getItemFromBlock(TFCBlocks.vessel));
		return exceptions;
	}

	@Override
	public boolean canInteractWith(EntityPlayer par1EntityPlayer)
	{
		return this.lowerChestInventory.isUseableByPlayer(par1EntityPlayer);
	}

	/**
	 * Called to transfer a stack from one inventory to the other eg. when shift clicking.
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
			int chestSlotCount = this.numRows * 9;

			// From chest to inventory
			if (slotNum < chestSlotCount)
			{
				if (!this.mergeItemStack(slotStack, chestSlotCount, this.inventorySlots.size(), true))
					return null;
			}
			// From inventory to chest
			else
			{
				if (!this.mergeItemStack(slotStack, 0, chestSlotCount, false))
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
	 * Callback for when the crafting gui is closed.
	 */
	@Override
	public void onContainerClosed(EntityPlayer par1EntityPlayer)
	{
		super.onContainerClosed(par1EntityPlayer);
		this.lowerChestInventory.closeInventory();
	}

	/**
	 * Return this chest container's lower chest inventory.
	 */
	public IInventory getLowerChestInventory()
	{
		return this.lowerChestInventory;
	}	
}
