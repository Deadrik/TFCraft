package com.bioxx.tfc.Containers.Slots;

import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.bioxx.tfc.Containers.ContainerHorseInventoryTFC;

public class ContainerHorseInventorySlotSaddle extends Slot
{
	//private final ContainerHorseInventoryTFC field_111239_a;

    public ContainerHorseInventorySlotSaddle(ContainerHorseInventoryTFC par1ContainerHorseInventory, IInventory par2IInventory, int par3, int par4, int par5)
    {
        super(par2IInventory, par3, par4, par5);
		//this.field_111239_a = par1ContainerHorseInventory;
    }

    /**
     * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
     */
    @Override
	public boolean isItemValid(ItemStack par1ItemStack)
    {
        return super.isItemValid(par1ItemStack) && par1ItemStack.getItem() == Items.saddle && !this.getHasStack();
    }
}
