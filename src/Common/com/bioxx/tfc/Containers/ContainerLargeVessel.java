package com.bioxx.tfc.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.bioxx.tfc.Containers.Slots.SlotChest;
import com.bioxx.tfc.Containers.Slots.SlotForShowOnly;
import com.bioxx.tfc.TileEntities.TEVessel;
import com.bioxx.tfc.api.Enums.EnumSize;

public class ContainerLargeVessel extends ContainerBarrel
{

	public ContainerLargeVessel(InventoryPlayer inventoryplayer, TEVessel tileentitybarrel, World world, int x, int y, int z, int tab)
	{
		super(inventoryplayer, tileentitybarrel, world, x, y, z, tab);
	}

	@Override
	protected void buildLayout()
	{
		if(guiTab == 0)
		{
			//Input slot
			if(!barrel.getSealed())
				addSlotToContainer(new SlotChest(barrel, 0, 80, 29).setSize(EnumSize.MEDIUM));
			else
				addSlotToContainer(new SlotForShowOnly(barrel, 0, 80, 29));
		}
		else if(guiTab == 1)
		{
			for(int i = 0; i < 3; i++)
			{
				for(int k = 0; k < 3; k++)
				{
					if(!barrel.getSealed())
						addSlotToContainer(new SlotChest(barrel, k+(i*3), 71+(i*18), 17+(k*18)).setSize(EnumSize.MEDIUM));
					else
						addSlotToContainer(new SlotForShowOnly(barrel, k+(i*3), 71+(i*18), 17+(k*18)));
				}
			}
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityplayer, int i)
	{
		Slot slot = (Slot)inventorySlots.get(i);
		if(slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			if(i == 0 && guiTab == 0 && !barrel.getSealed())
			{
				if(!this.mergeItemStack(itemstack1, 1, this.inventorySlots.size(), true))
					return null;
			}
			else if(i < 9 && guiTab == 1)
			{
				if(!this.mergeItemStack(itemstack1, 9, this.inventorySlots.size(), true))
					return null;
			}
			else
			{
				if (!barrel.getSealed() && !this.mergeItemStack(itemstack1, 0, 9, false))
					return null;
			}

			if(itemstack1.stackSize == 0)
				slot.putStack(null);
			else
				slot.onSlotChanged();
		}
		return null;
	}
}
