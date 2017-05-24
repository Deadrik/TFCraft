package com.bioxx.tfc.Containers.Slots;

import com.bioxx.tfc.api.TFCItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotCoalPile extends Slot
{
    public SlotCoalPile(EntityPlayer entityplayer, IInventory iinventory, int i, int j, int k)
    {
        super(iinventory, i, j, k);
    }

    @Override
    public boolean isItemValid(ItemStack itemstack)
    {
        return (itemstack.getItem() == TFCItems.coal && itemstack.getItemDamage() == 0);
    }

    @Override
    public int getSlotStackLimit()
    {
        return 4;
    }
}