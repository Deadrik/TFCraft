package com.bioxx.tfc.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.bioxx.tfc.Containers.Slots.SlotChest;
import com.bioxx.tfc.Core.Player.PlayerInventory;
import com.bioxx.tfc.TileEntities.TEBarrel;

public class ContainerBarrel extends ContainerTFC
{
	private TEBarrel barrel;
	private float liquidLevel;
	int guiTab = 0;

	public ContainerBarrel(InventoryPlayer inventoryplayer, TEBarrel tileentitybarrel, World world, int x, int y, int z, int tab)
	{
		barrel = tileentitybarrel;
		liquidLevel = 0;
		guiTab = tab;

		if(guiTab == 0)
		{
			//Input slot
			addSlotToContainer(new Slot(tileentitybarrel, 0, 80, 29));
		}
		else if(guiTab == 1)
		{
			addSlotToContainer(new SlotChest(tileentitybarrel, 0, 53, 17));
			addSlotToContainer(new SlotChest(tileentitybarrel, 1, 71, 17));
			addSlotToContainer(new SlotChest(tileentitybarrel, 2, 89, 17));
			addSlotToContainer(new SlotChest(tileentitybarrel, 3, 107, 17));
			addSlotToContainer(new SlotChest(tileentitybarrel, 4, 53, 35));
			addSlotToContainer(new SlotChest(tileentitybarrel, 5, 71, 35));
			addSlotToContainer(new SlotChest(tileentitybarrel, 6, 89, 35));
			addSlotToContainer(new SlotChest(tileentitybarrel, 7, 107, 35));
			addSlotToContainer(new SlotChest(tileentitybarrel, 8, 53, 53));
			addSlotToContainer(new SlotChest(tileentitybarrel, 9, 71, 53));
			addSlotToContainer(new SlotChest(tileentitybarrel, 10, 89, 53));
			addSlotToContainer(new SlotChest(tileentitybarrel, 11, 107, 53));
		}

		PlayerInventory.buildInventoryLayout(this, inventoryplayer, 8, 90, false, true);

	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return true;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityplayer, int i)
	{
		Slot slot = (Slot)inventorySlots.get(i);
		if(slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			if(i == 0 && guiTab == 0)
			{
				if(!this.mergeItemStack(itemstack1, 1, this.inventorySlots.size(), true))
					return null;
			}
			else if(i < 12 && guiTab == 1)
			{
				if(!this.mergeItemStack(itemstack1, 12, this.inventorySlots.size(), true))
					return null;
			}
			else
			{
				if (!this.mergeItemStack(itemstack1, 0, 12, false))
					return null;
			}

			if(itemstack1.stackSize == 0)
				slot.putStack(null);
			else
				slot.onSlotChanged();
		}
		return null;
	}

	private int updatecounter = 0;
	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		for (int var1 = 0; var1 < this.crafters.size() && guiTab == 0; ++var1)
		{
			ICrafting var2 = (ICrafting)this.crafters.get(var1);
			if (this.liquidLevel != this.barrel.liquidLevel)
				var2.sendProgressBarUpdate(this, 0, this.barrel.liquidLevel);
		}
	}

	@Override
	public void updateProgressBar(int par1, int par2)
	{
		if (par1 == 0)
			this.barrel.liquidLevel = par2;
	}
}
