package com.bioxx.tfc.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.bioxx.tfc.Containers.Slots.SlotLogPile;
import com.bioxx.tfc.Core.Player.PlayerInventory;
import com.bioxx.tfc.TileEntities.TELogPile;

public class ContainerLogPile extends ContainerTFC
{
	private World world;
	//private int posX;
	//private int posY;
	//private int posZ;
	private TELogPile logpile;
	private EntityPlayer player;

	public ContainerLogPile(InventoryPlayer playerinv, TELogPile pile, World world, int x, int y, int z)
	{
		this.player = playerinv.player;
		this.logpile = pile;
		this.world = world;
		//this.posX = x;
		//this.posY = y;
		//this.posZ = z;
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
			logpile.closeInventory();
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

			// From pile to inventory
			if (slotNum < 4)
			{
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

	@Override
	public boolean canInteractWith(EntityPlayer var1)
	{
		return true;
	}

	protected void layoutContainer(IInventory playerInventory, IInventory chestInventory, int xSize, int ySize)
	{
		this.addSlotToContainer(new SlotLogPile(getPlayer(),chestInventory, 0, 71, 25));
		this.addSlotToContainer(new SlotLogPile(getPlayer(),chestInventory, 1, 89, 25));
		this.addSlotToContainer(new SlotLogPile(getPlayer(),chestInventory, 2, 71, 43));
		this.addSlotToContainer(new SlotLogPile(getPlayer(),chestInventory, 3, 89, 43));
	}
	
	public EntityPlayer getPlayer()
	{
		return player;
	}
}
