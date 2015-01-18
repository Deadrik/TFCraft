package com.bioxx.tfc.Containers;

import java.util.ArrayList;

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

	public static ArrayList<Item> getExceptions(){
		ArrayList<Item> exceptions = new ArrayList<Item>();
		exceptions.add(TFCItems.Logs);
		exceptions.add(TFCItems.BismuthIngot);
		exceptions.add(TFCItems.BismuthBronzeIngot);
		exceptions.add(TFCItems.BlackBronzeIngot);
		exceptions.add(TFCItems.BlackSteelIngot);
		exceptions.add(TFCItems.BlueSteelIngot);
		exceptions.add(TFCItems.BrassIngot);
		exceptions.add(TFCItems.BronzeIngot);
		exceptions.add(TFCItems.CopperIngot);
		exceptions.add(TFCItems.GoldIngot);
		exceptions.add(TFCItems.WroughtIronIngot);
		exceptions.add(TFCItems.LeadIngot);
		exceptions.add(TFCItems.NickelIngot);
		exceptions.add(TFCItems.PigIronIngot);
		exceptions.add(TFCItems.PlatinumIngot);
		exceptions.add(TFCItems.RedSteelIngot);
		exceptions.add(TFCItems.RoseGoldIngot);
		exceptions.add(TFCItems.SilverIngot);
		exceptions.add(TFCItems.SteelIngot);
		exceptions.add(TFCItems.BismuthIngot);
		exceptions.add(TFCItems.SterlingSilverIngot);
		exceptions.add(TFCItems.TinIngot);
		exceptions.add(TFCItems.ZincIngot);
		exceptions.add(Item.getItemFromBlock(TFCBlocks.Barrel));
		exceptions.add(Item.getItemFromBlock(TFCBlocks.Vessel));
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
		ItemStack var2 = null;
		Slot slot = (Slot)this.inventorySlots.get(slotNum);

		if (slot != null && slot.getHasStack())
		{
			ItemStack var4 = slot.getStack();
			var2 = var4.copy();

			if (slotNum < this.numRows * 9)//First try to merge into the player's inventory
			{
				if (!this.mergeItemStack(var4, this.numRows * 9, this.inventorySlots.size(), true))
					return null;
			}
			else if (!this.mergeItemStack(var4, 0, this.numRows * 9, false))//Merge into chest if possible
				return null;

			if (var4.stackSize == 0)
				slot.putStack((ItemStack)null);
			else
				slot.onSlotChanged();
		}

		return var2;
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
