package com.bioxx.tfc.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.bioxx.tfc.Containers.Slots.SlotOutputOnly;
import com.bioxx.tfc.Core.Player.PlayerInventory;
import com.bioxx.tfc.TileEntities.TENestBox;

public class ContainerNestBox extends ContainerTFC
{
	private World world;
	private int posX;
	private int posY;
	private int posZ;

	public ContainerNestBox(InventoryPlayer playerinv, TENestBox te, World world, int x, int y, int z)
	{
		this.player = playerinv.player;
		this.world = world;
		this.posX = x;
		this.posY = y;
		this.posZ = z;

		this.addSlotToContainer(new SlotOutputOnly(te, 0, 71, 25));
		this.addSlotToContainer(new SlotOutputOnly(te, 1, 89, 25));
		this.addSlotToContainer(new SlotOutputOnly(te, 2, 71, 43));
		this.addSlotToContainer(new SlotOutputOnly(te, 3, 89, 43));

		PlayerInventory.buildInventoryLayout(this, playerinv, 8, 90);
	}

	@Override
	public boolean canInteractWith(EntityPlayer var1)
	{
		return true;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int clickedIndex)
	{
		ItemStack returnedStack = null;
		Slot clickedSlot = (Slot)this.inventorySlots.get(clickedIndex);

		if (clickedSlot != null && clickedSlot.getHasStack())
		{
			ItemStack clickedStack = clickedSlot.getStack();
			returnedStack = clickedStack.copy();

			if (clickedIndex < 4)
				if (!this.mergeItemStack(clickedStack, 4, inventorySlots.size(), true))
					return null;
			else if (clickedIndex >= 4 && clickedIndex < inventorySlots.size())
				if (!this.mergeItemStack(clickedStack, 0, 4, false))
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
