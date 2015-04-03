package com.bioxx.tfc.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.bioxx.tfc.Containers.Slots.SlotBlocked;
import com.bioxx.tfc.Containers.Slots.SlotQuern;
import com.bioxx.tfc.Containers.Slots.SlotQuernGrain;
import com.bioxx.tfc.Core.Player.PlayerInventory;
import com.bioxx.tfc.TileEntities.TEQuern;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Crafting.QuernManager;

public class ContainerQuern extends ContainerTFC
{
	private World world;
	private int posX;
	private int posY;
	private int posZ;
	private TEQuern te;
	private EntityPlayer player;

	public ContainerQuern(InventoryPlayer playerinv, TEQuern pile, World world, int x, int y, int z)
	{
		this.player = playerinv.player;
		this.te = pile;
		this.world = world;
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		pile.openInventory();
		layoutContainer(playerinv, pile, 0, 0);
		PlayerInventory.buildInventoryLayout(this, playerinv, 8, 90, false, true);
	}

	/**
	 * Callback for when the crafting gui is closed.
	 */
	@Override
	public void onContainerClosed(EntityPlayer par1EntityPlayer)
	{
		super.onContainerClosed(par1EntityPlayer);
		if(!world.isRemote)
			te.closeInventory();
		else
			te.validate();
	}

	@Override
	public boolean canInteractWith(EntityPlayer var1)
	{
		return true;
	}

	protected void layoutContainer(IInventory playerInventory, IInventory chestInventory, int xSize, int ySize)
	{
		this.addSlotToContainer(new SlotQuernGrain(chestInventory, 0, 66, 47));
		this.addSlotToContainer(new SlotBlocked(chestInventory, 1, 93, 47));
		this.addSlotToContainer(new SlotQuern(chestInventory, 2, 93, 20));
	}

	public EntityPlayer getPlayer()
	{
		return player;
	}

	@Override
	public ItemStack transferStackInSlotTFC(EntityPlayer player, int clickedIndex)
	{
		ItemStack returnedStack = null;
		Slot clickedSlot = (Slot)this.inventorySlots.get(clickedIndex);

		if (clickedSlot != null && clickedSlot.getHasStack())
		{
			ItemStack clickedStack = clickedSlot.getStack();
			returnedStack = clickedStack.copy();

			if (clickedIndex < 3)
			{
				if (!this.mergeItemStack(clickedStack, 3, inventorySlots.size(), true))
					return null;
			}
			else if (clickedIndex >= 3
					&& clickedIndex < inventorySlots.size()
					&& QuernManager.getInstance().isValidItem(clickedStack))
			{
				if (!this.mergeItemStack(clickedStack, 0, 1, false))
					return null;
			}
			else if (clickedIndex >= 3
					&& clickedIndex < inventorySlots.size()
					&& clickedStack.getItem() == TFCItems.Quern)
			{
				if (!this.mergeItemStack(clickedStack, 2, 3, false))
					return null;
			}
			else if (clickedIndex >= 3 && clickedIndex < inventorySlots.size())
			{
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
}
