package com.bioxx.tfc.Containers;

import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.bioxx.tfc.Containers.Slots.ContainerHorseInventorySlotArmor;
import com.bioxx.tfc.Containers.Slots.ContainerHorseInventorySlotSaddle;
import com.bioxx.tfc.Containers.Slots.SlotChest;
import com.bioxx.tfc.Entities.Mobs.EntityHorseTFC;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerHorseInventoryTFC extends ContainerTFC
{
	private IInventory field_111243_a;
	private EntityHorseTFC theHorse;

	public ContainerHorseInventoryTFC(final IInventory playerInv, final IInventory horseInv,final EntityHorseTFC horse)
	{
		/*
		this.field_111243_a = horseInv;
		this.theHorse = horse;
		byte b0 = 3;
		horseInv.openInventory();
		int i = (b0 - 4) * 18;
		this.addSlotToContainer(new ContainerHorseInventorySlotSaddle(this, horseInv, 0, 8, 18));
		this.addSlotToContainer(new ContainerHorseInventorySlotArmor(this, horseInv, 1, 8, 36, horse));
		int j;
		int k;

		if (horse.isChested())
		{

			for (j = 0; j < b0; ++j)
			{
				for (k = 0; k < 5; ++k)
				{
					this.addSlotToContainer(new SlotChest(horseInv, 2 + k + j * 5, 80 + k * 18, 18 + j * 18).addItemException(ContainerChestTFC.getExceptions()));
				}
			}
		}

		for (j = 0; j < 3; ++j)
		{
			for (k = 0; k < 9; ++k)
			{
				this.addSlotToContainer(new Slot(playerInv, k + j * 9 + 9, 8 + k * 18, 102 + j * 18 + i));
			}
		}

		for (j = 0; j < 9; ++j)
			this.addSlotToContainer(new Slot(playerInv, j, 8 + j * 18, 160 + i));
			*/
		this.field_111243_a = horseInv;
        this.theHorse = horse;
        byte b0 = 3;
        horseInv.openInventory();
        int i = (b0 - 4) * 18;
        this.addSlotToContainer(new Slot(horseInv, 0, 8, 18)
        {
            private static final String __OBFID = "CL_00001752";
            /**
             * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
             */
            public boolean isItemValid(ItemStack p_75214_1_)
            {
                return super.isItemValid(p_75214_1_) && p_75214_1_.getItem() == Items.saddle && !this.getHasStack();
            }
        });
        this.addSlotToContainer(new Slot(horseInv, 1, 8, 36)
        {
            private static final String __OBFID = "CL_00001753";
            /**
             * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
             */
            public boolean isItemValid(ItemStack p_75214_1_)
            {
                return super.isItemValid(p_75214_1_) && horse.func_110259_cr() && EntityHorseTFC.func_146085_a(p_75214_1_.getItem());
            }
            @SideOnly(Side.CLIENT)
            public boolean func_111238_b()
            {
                return horse.func_110259_cr();
            }
        });
        int j;
        int k;

        if (horse.isChested())
        {
            for (j = 0; j < b0; ++j)
            {
                for (k = 0; k < 5; ++k)
                {
                    this.addSlotToContainer(new Slot(horseInv, 2 + k + j * 5, 80 + k * 18, 18 + j * 18));
                }
            }
        }

        for (j = 0; j < 3; ++j)
        {
            for (k = 0; k < 9; ++k)
            {
                this.addSlotToContainer(new Slot(playerInv, k + j * 9 + 9, 8 + k * 18, 102 + j * 18 + i));
            }
        }

        for (j = 0; j < 9; ++j)
        {
            this.addSlotToContainer(new Slot(playerInv, j, 8 + j * 18, 160 + i));
        }
	}

	public boolean canInteractWith(EntityPlayer player)
	{
		return this.field_111243_a.isUseableByPlayer(player) && this.theHorse.isEntityAlive() && this.theHorse.getDistanceToEntity(player) < 8.0F;
	}

	/**
	 * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
	 */
	public ItemStack transferStackInSlotTFC(EntityPlayer player, int par2)
	{
		/*
		ItemStack itemstack = null;
		Slot slot = (Slot)this.inventorySlots.get(par2);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (par2 < this.field_111243_a.getSizeInventory())
			{
				if (!this.mergeItemStack(itemstack1, this.field_111243_a.getSizeInventory(), this.inventorySlots.size(), true))
					return null;
			}
			else if (this.getSlot(1).isItemValid(itemstack1) && !this.getSlot(1).getHasStack())
			{
				if (!this.mergeItemStack(itemstack1, 1, 2, false))
					return null;
			}
			else if (this.getSlot(0).isItemValid(itemstack1))
			{
				if (!this.mergeItemStack(itemstack1, 0, 1, false))
					return null;
			}
			else if (this.field_111243_a.getSizeInventory() <= 2 || !this.mergeItemStack(itemstack1, 2, this.field_111243_a.getSizeInventory(), false))
				return null;

			if (itemstack1.stackSize == 0)
				slot.putStack((ItemStack)null);
			else
				slot.onSlotChanged();
		}

		return itemstack;*/
		ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(par2);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (par2 < this.field_111243_a.getSizeInventory())
            {
                if (!this.mergeItemStack(itemstack1, this.field_111243_a.getSizeInventory(), this.inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (this.getSlot(1).isItemValid(itemstack1) && !this.getSlot(1).getHasStack())
            {
                if (!this.mergeItemStack(itemstack1, 1, 2, false))
                {
                    return null;
                }
            }
            else if (this.getSlot(0).isItemValid(itemstack1))
            {
                if (!this.mergeItemStack(itemstack1, 0, 1, false))
                {
                    return null;
                }
            }
            else if (this.field_111243_a.getSizeInventory() <= 2 || !this.mergeItemStack(itemstack1, 2, this.field_111243_a.getSizeInventory(), false))
            {
                return null;
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }
        }

        return itemstack;
	}

	/**
	 * Called when the container is closed.
	 */
	public void onContainerClosed(EntityPlayer player)
	{
		/*super.onContainerClosed(player);
		this.field_111243_a.closeInventory();
		this.theHorse.updateChestSaddle();*/
		super.onContainerClosed(player);
        this.field_111243_a.closeInventory();
        theHorse.updateChestSaddle();
	}
}
