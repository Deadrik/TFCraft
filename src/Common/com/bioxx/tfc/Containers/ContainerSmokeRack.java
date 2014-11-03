package com.bioxx.tfc.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.bioxx.tfc.Containers.Slots.SlotSmokeRack;
import com.bioxx.tfc.Core.Player.PlayerInventory;
import com.bioxx.tfc.TileEntities.TESmokeRack;

public class ContainerSmokeRack extends ContainerTFC
{
	private World world;
	private int posX;
	private int posY;
	private int posZ;
	private TESmokeRack smokeRack;
	private EntityPlayer player;

	public ContainerSmokeRack(InventoryPlayer playerinv, TESmokeRack rack, World world, int x, int y, int z)
	{
		this.player = playerinv.player;
		this.smokeRack = rack;
		this.world = world;
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		
		rack.openInventory();
		
		addSlotToContainer(new SlotSmokeRack(player,rack, 0, 71, 25));
		addSlotToContainer(new SlotSmokeRack(player,rack, 1, 89, 25));
		
		PlayerInventory.buildInventoryLayout(this, playerinv, 8, 90, false, true);
	}

	/*
	 * Callback for when the crafting gui is closed.
	 */
	@Override
	public void onContainerClosed(EntityPlayer par1EntityPlayer)
	{
		super.onContainerClosed(par1EntityPlayer);

		if(!world.isRemote)
			smokeRack.closeInventory();
	}

	@Override
	public boolean canInteractWith(EntityPlayer var1)
	{
		return true;
	}
	
	/* 
	 * Called to transfer a stack from one inventory to the other eg. when shift clicking.
	 */
	@Override
	public ItemStack transferStackInSlotTFC(EntityPlayer player, int i)
	{
		Slot slot = (Slot)inventorySlots.get(i);
		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			if (itemstack1.stackSize > 0)
			{
				if (i < 2)
				{
					if (!this.mergeItemStack(itemstack1, 2, this.inventorySlots.size(), true))
						return null;
				}
				else
				{
					if (!this.mergeItemStack(itemstack1, 0, 2, false))
						return null;
				}
			}

			if (itemstack1.stackSize == 0)
				slot.putStack(null);
			else
				slot.onSlotChanged();
		}
		return null;
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		
		for (int var1 = 0; var1 < this.inventorySlots.size(); ++var1)
		{
			ItemStack var2 = ((Slot)this.inventorySlots.get(var1)).getStack();
			ItemStack var3 = (ItemStack)this.inventoryItemStacks.get(var1);

			if (!ItemStack.areItemStacksEqual(var3, var2))
			{
				var3 = var2 == null ? null : var2.copy();
				this.inventoryItemStacks.set(var1, var3);

				for (int var4 = 0; var4 < this.crafters.size(); ++var4)
					((ICrafting)this.crafters.get(var4)).sendSlotContents(this, var1, var3);
			}
		}
	}
}
