package com.bioxx.tfc.Containers;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.Containers.Slots.SlotChest;
import com.bioxx.tfc.Containers.Slots.SlotForShowOnly;
import com.bioxx.tfc.Core.Player.PlayerInventory;
import com.bioxx.tfc.TileEntities.TEBarrel;
import com.bioxx.tfc.api.Enums.EnumSize;

public class ContainerBarrel extends ContainerTFC
{
	public TEBarrel barrel;
	public float liquidLevel;
	public int liquidID;
	public int sealedTime = -1;
	public int guiTab = 0;

	public ContainerBarrel(InventoryPlayer inventoryplayer, TEBarrel tileentitybarrel, World world, int x, int y, int z, int tab)
	{
		barrel = tileentitybarrel;
		liquidLevel = 0;
		liquidID = -1;
		guiTab = tab;

		buildLayout();

		PlayerInventory.buildInventoryLayout(this, inventoryplayer, 8, 90, false, true);

	}

	public static ArrayList<Item> getExceptions(){
		ArrayList exceptions = new ArrayList<Item>();
		exceptions.add(Item.getItemFromBlock(TFCBlocks.Barrel));
		exceptions.add(Item.getItemFromBlock(TFCBlocks.Vessel));
		return exceptions;
	}

	protected void buildLayout()
	{
		if(guiTab == 0)
		{
			//Input slot
			if(!barrel.getSealed())
				addSlotToContainer(new SlotChest(barrel, 0, 80, 29).setSize(EnumSize.LARGE).addItemException(getExceptions()));
			else
				addSlotToContainer(new SlotForShowOnly(barrel, 0, 80, 29));
		}
		else if(guiTab == 1)
		{
			for(int i = 0; i < 4; i++)
			{
				for(int k = 0; k < 3; k++)
				{
					if(!barrel.getSealed())
						addSlotToContainer(new SlotChest(barrel, k+(i*3), 53+(i*18), 17+(k*18)).setSize(EnumSize.LARGE).addItemException(ContainerChestTFC.getExceptions()));
					else
						addSlotToContainer(new SlotForShowOnly(barrel, k+(i*3), 53+(i*18), 17+(k*18)));
				}
			}
		}
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
			if(i == 0 && guiTab == 0 && !barrel.getSealed())
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
				if (!barrel.getSealed() && guiTab == 1)
				{
					if(!this.mergeItemStack(itemstack1, 0, 12, false)){return null;}
				}
				else if (!barrel.getSealed() && guiTab == 0)
				{
					if(!this.mergeItemStack(itemstack1, 0, 1, false)){return null;}
				}
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

			if (this.barrel.getFluidStack() != null && this.liquidID != this.barrel.getFluidStack().fluidID)
			{
				liquidID = barrel.getFluidStack().fluidID;
				var2.sendProgressBarUpdate(this, 0, this.barrel.getFluidStack().fluidID);
			}
			if (this.liquidLevel != this.barrel.getFluidLevel())
			{
				liquidLevel = barrel.getFluidLevel();
				var2.sendProgressBarUpdate(this, 1, this.barrel.getFluidLevel());
			}
			if(this.barrel.sealtime != this.sealedTime)
			{
				sealedTime = barrel.sealtime;
				var2.sendProgressBarUpdate(this, 2, sealedTime);
			}
		}
	}

	@Override
	public void updateProgressBar(int id, int val)
	{
		if (id == 0)
		{
			if(barrel.fluid != null)
			{
				this.barrel.fluid = new FluidStack(val, this.barrel.fluid.amount);
			}
			else
			{
				this.barrel.fluid = new FluidStack(val, 1000);
			}
			barrel.ProcessItems();
		}
		else if (id == 1)
		{
			if(barrel.fluid != null)
				this.barrel.fluid.amount = val;
		}
		else if (id == 2)
		{
			this.barrel.sealtime = val;
		}
	}
}
