package com.bioxx.tfc.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.bioxx.tfc.Containers.Slots.SlotCraftingMetal;
import com.bioxx.tfc.Core.Player.PlayerInventory;
import com.bioxx.tfc.api.Crafting.CraftingManagerTFC;

public class ContainerSpecialCrafting extends ContainerTFC
{
	/** The crafting matrix inventory (5x5).
	 *  Used for knapping and leather working */
	public InventoryCrafting craftMatrix = new InventoryCrafting(this, 5, 5);

	private SlotCraftingMetal SCM;

	/** The crafting result, size 1. */
	public IInventory craftResult = new InventoryCraftResult();
	private World worldObj;

	public ContainerSpecialCrafting(InventoryPlayer inventoryplayer, ItemStack is, World world, int x, int y, int z)
	{
		for (int j1 = 0; j1 < 25; j1++)
		{
			if(is != null)
				craftMatrix.setInventorySlotContents(j1, is.copy());
		}

		this.worldObj = world;

		SCM = new SlotCraftingMetal(this, inventoryplayer.player, craftMatrix, craftResult, 0, 128, 44);
		addSlotToContainer(SCM);

		PlayerInventory.buildInventoryLayout(this, inventoryplayer, 8, 108, false, true);

		this.onCraftMatrixChanged(this.craftMatrix);
	}

	@Override
	public void onContainerClosed(EntityPlayer player)
	{
		super.onContainerClosed(player);
		if (!this.worldObj.isRemote)
		{
			ItemStack is = this.craftResult.getStackInSlotOnClosing(0);
			if (is != null)
				player.entityDropItem(is, 0);
		}
	}

	/**
	 * Callback for when the crafting matrix is changed.
	 */
	@Override
	public void onCraftMatrixChanged(IInventory ii)
	{
		this.craftResult.setInventorySlotContents(0, CraftingManagerTFC.getInstance().findMatchingRecipe(this.craftMatrix, worldObj));
	}

	/**
	 * Called to transfer a stack from one inventory to the other eg. when shift clicking.
	 * @return 
	 */
	@Override
	public ItemStack transferStackInSlotTFC(EntityPlayer player, int clickedIndex)
	{
		ItemStack isTemp = null;
		Slot grabbedSlot = (Slot)this.inventorySlots.get(clickedIndex);

		if(grabbedSlot != null && grabbedSlot.getHasStack())
		{
			ItemStack isFromSlot = grabbedSlot.getStack();
			isTemp = isFromSlot.copy();

			if(clickedIndex < 10)
			{
				if (!this.mergeItemStack(isFromSlot, 10, 36, true))
					return null;
			}
			else if(clickedIndex >= 10 && clickedIndex < 37)
			{
				if (!this.mergeItemStack(isFromSlot, 0, 9, true))
					return null;
			}
			else if(clickedIndex >= 37 && clickedIndex < 62)
			{
				if (!this.mergeItemStack(isFromSlot, 0, 36, true))
					return null;
			}

			if (isFromSlot.stackSize == 0)
				grabbedSlot.putStack((ItemStack)null);
			else
				grabbedSlot.onSlotChanged();

			if (isFromSlot.stackSize == isTemp.stackSize)
				return null;

			grabbedSlot.onPickupFromSlot(player, isFromSlot);
		}
		this.onCraftMatrixChanged(this.craftMatrix);
		return isTemp;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return true;
	}
}
