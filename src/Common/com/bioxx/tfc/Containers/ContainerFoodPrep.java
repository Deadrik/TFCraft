package com.bioxx.tfc.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.bioxx.tfc.Containers.Slots.SlotBlocked;
import com.bioxx.tfc.Containers.Slots.SlotFoodOnly;
import com.bioxx.tfc.Core.Player.PlayerInventory;
import com.bioxx.tfc.TileEntities.TEFoodPrep;
import com.bioxx.tfc.api.Interfaces.IFood;

public class ContainerFoodPrep extends ContainerTFC
{
	private World world;
	private int posX;
	private int posY;
	private int posZ;
	private TEFoodPrep te;
	private EntityPlayer player;
	int guiTab = 0;

	public ContainerFoodPrep(InventoryPlayer playerinv, TEFoodPrep pile, World world, int x, int y, int z, int tab)
	{
		this.player = playerinv.player;
		this.te = pile;
		this.world = world;
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		guiTab = tab;
		pile.openInventory();
		layoutContainer(playerinv, pile, 0, 0);
		pile.lastTab = tab;
		PlayerInventory.buildInventoryLayout(this, playerinv, 8, 90, false, true);
	}

	/**
	 * Callback for when the crafting gui is closed.
	 */
	@Override
	public void onContainerClosed(EntityPlayer par1EntityPlayer)
	{
		super.onContainerClosed(par1EntityPlayer);
		te.closeInventory();
	}

	@Override
	public boolean canInteractWith(EntityPlayer var1)
	{
		return true;
	}

	protected void layoutContainer(IInventory playerInventory, IInventory chestInventory, int xSize, int ySize)
	{
		this.addSlotToContainer(new SlotFoodOnly(chestInventory, 4, 35, 11));
		this.addSlotToContainer(new SlotFoodOnly(chestInventory, 0, 53, 11));
		this.addSlotToContainer(new SlotFoodOnly(chestInventory, 1, 71, 11));
		this.addSlotToContainer(new SlotFoodOnly(chestInventory, 2, 89, 11));
		this.addSlotToContainer(new SlotFoodOnly(chestInventory, 3, 107, 11));
		this.addSlotToContainer(new SlotFoodOnly(chestInventory, 5, 125, 11));
		this.addSlotToContainer(new SlotBlocked(chestInventory, 6, 80, 35));
		this.addSlotToContainer(new Slot(chestInventory, 7, 53, 59));
		this.addSlotToContainer(new Slot(chestInventory, 8, 71, 59));
		this.addSlotToContainer(new Slot(chestInventory, 9, 89, 59));
		this.addSlotToContainer(new Slot(chestInventory, 10, 107, 59));
	}

	public EntityPlayer getPlayer()
	{
		return player;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int clickedIndex)
	{
		ItemStack returnedStack = null;
		Slot clickedSlot = (Slot)this.inventorySlots.get(clickedIndex);

		if (clickedSlot != null
				&& clickedSlot.getHasStack()
				&& (clickedSlot.getStack().getItem() instanceof IFood || clickedSlot.getStack().getItem() == Items.bowl))
		{
			ItemStack clickedStack = clickedSlot.getStack();
			returnedStack = clickedStack.copy();

			if (clickedIndex < 11)
			{
				if (!this.mergeItemStack(clickedStack, 11, inventorySlots.size(), true))
					return null;
			}
			else if (clickedIndex >= 11 && clickedIndex < inventorySlots.size())
			{
				if (!this.mergeItemStack(clickedStack, 0, 11, false))
					return null;
			}
			else if (!this.mergeItemStack(clickedStack, 11, inventorySlots.size(), false))
				return null;

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
