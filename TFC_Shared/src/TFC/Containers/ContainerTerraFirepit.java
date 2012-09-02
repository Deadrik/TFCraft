package TFC.Containers;

import java.util.ArrayList;

import TFC.Core.TFCItems;
import TFC.TileEntities.TileEntityTerraFirepit;

import net.minecraft.src.*;

public class ContainerTerraFirepit extends Container
{
    private TileEntityTerraFirepit firepit;

    private float firetemp;
    private int charcoal;


    public ContainerTerraFirepit(InventoryPlayer inventoryplayer, TileEntityTerraFirepit tileentityfirepit, World world, int x, int y, int z)
    {
        firepit = tileentityfirepit;
        firetemp = -1111;
        charcoal = 0;

        //Input slot
        addSlotToContainer(new SlotFirepitIn(inventoryplayer.player,tileentityfirepit, 1, 80, 20));
        //fuel stack
        addSlotToContainer(new SlotFirepitFuel(inventoryplayer.player, tileentityfirepit, 0, 8, 8));
        addSlotToContainer(new SlotFirepit(inventoryplayer.player, tileentityfirepit, 3, 8, 26));
        addSlotToContainer(new SlotFirepit(inventoryplayer.player, tileentityfirepit, 4, 8, 44));
        addSlotToContainer(new SlotFirepit(inventoryplayer.player, tileentityfirepit, 5, 8, 62));

        //item output
        addSlotToContainer(new SlotFirepitOut(inventoryplayer.player, tileentityfirepit, 7, 71, 48));
        addSlotToContainer(new SlotFirepitOut(inventoryplayer.player, tileentityfirepit, 8, 89, 48));

        //byproducts out
        addSlotToContainer(new SlotFirepit(inventoryplayer.player, tileentityfirepit, 2, 127, 23));
        addSlotToContainer(new SlotFirepit(inventoryplayer.player, tileentityfirepit, 6, 145, 23));
        addSlotToContainer(new SlotFirepit(inventoryplayer.player, tileentityfirepit, 9, 127, 41));
        addSlotToContainer(new SlotFirepit(inventoryplayer.player, tileentityfirepit, 10, 145, 41));


        //slag output
        //addSlot(new SlotFirepit(inventoryplayer.player, tileentityfirepit, 9, 98, 62));

        for(int i = 0; i < 3; i++)
        {
            for(int k = 0; k < 9; k++)
            {
            	addSlotToContainer(new Slot(inventoryplayer, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
            }

        }

        for(int j = 0; j < 9; j++)
        {
        	addSlotToContainer(new Slot(inventoryplayer, j, 8 + j * 18, 142));
        }

    }

    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return true;
    }

    public ItemStack slotClick(int i, int j, boolean flag, EntityPlayer entityplayer)
    {
        ItemStack itemstack = null;
        if (j > 1)
        {
            return null;
        }
        if (j == 0 || j == 1)
        {
            InventoryPlayer inventoryplayer = entityplayer.inventory;
            if (i == -999)
            {
                if (inventoryplayer.getItemStack() != null && i == -999)
                {
                    if (j == 0)
                    {
                        entityplayer.dropPlayerItem(inventoryplayer.getItemStack());
                        inventoryplayer.setItemStack(null);
                    }
                    if (j == 1)
                    {
                        entityplayer.dropPlayerItem(inventoryplayer.getItemStack().splitStack(1));
                        if (inventoryplayer.getItemStack().stackSize == 0)
                        {
                            inventoryplayer.setItemStack(null);
                        }
                    }
                }
            }
            else if (flag)
            {
                ItemStack itemstack1 = playerTransferStackInSlot(i, entityplayer);
                if (itemstack1 != null)
                {
                    int k = itemstack1.itemID;
                    itemstack = itemstack1.copy();
                    Slot slot1 = (Slot)inventorySlots.get(i);
                    if (slot1 != null && slot1.getStack() != null && slot1.getStack().itemID == k)
                    {
                        retrySlotClick(i, j, flag, entityplayer);
                    }
                }
            }
            else
            {
                if (i < 0)
                {
                    return null;
                }
                Slot slot = (Slot)inventorySlots.get(i);
                if (slot != null)
                {
                    slot.onSlotChanged();
                    ItemStack itemstack2 = slot.getStack();
                    ItemStack itemstack3 = inventoryplayer.getItemStack();
                    if (itemstack2 != null)
                    {
                        itemstack = itemstack2.copy();
                    }
                    if (itemstack2 == null)
                    {
                        if (itemstack3 != null && slot.isItemValid(itemstack3))
                        {
                            int l = j != 0 ? 1 : itemstack3.stackSize;
                            if (l > slot.getSlotStackLimit())
                            {
                                l = slot.getSlotStackLimit();
                            }
                            slot.putStack(itemstack3.splitStack(l));
                            if (itemstack3.stackSize == 0)
                            {
                                inventoryplayer.setItemStack(null);
                            }
                        }
                    }
                    else if (itemstack3 == null)
                    {
                        int i1 = j != 0 ? (itemstack2.stackSize + 1) / 2 : itemstack2.stackSize;
                        ItemStack itemstack5 = slot.decrStackSize(i1);
                        inventoryplayer.setItemStack(itemstack5);
                        if (itemstack2.stackSize == 0)
                        {
                            slot.putStack(null);
                        }
                        slot.onPickupFromSlot(inventoryplayer.getItemStack());
                    }
                    else if (slot.isItemValid(itemstack3))
                    {
                        if (itemstack2.itemID != itemstack3.itemID || itemstack2.getHasSubtypes() && itemstack2.getItemDamage() != itemstack3.getItemDamage() || !ItemStack.areItemStacksEqual(itemstack2, itemstack3))
                        {
                            if (itemstack3.stackSize <= slot.getSlotStackLimit())
                            {
                                ItemStack itemstack4 = itemstack2;
                                slot.putStack(itemstack3);
                                inventoryplayer.setItemStack(itemstack4);
                            }
                        }
                        else
                        {
                            int j1 = j != 0 ? 1 : itemstack3.stackSize;
                            if (j1 > slot.getSlotStackLimit() - itemstack2.stackSize)
                            {
                                j1 = slot.getSlotStackLimit() - itemstack2.stackSize;
                            }
                            if (j1 > itemstack3.getMaxStackSize() - itemstack2.stackSize)
                            {
                                j1 = itemstack3.getMaxStackSize() - itemstack2.stackSize;
                            }
                            itemstack3.splitStack(j1);
                            if (itemstack3.stackSize == 0)
                            {
                                inventoryplayer.setItemStack(null);
                            }
                            itemstack2.stackSize += j1;
                        }
                    }
                    else if (itemstack2.itemID == itemstack3.itemID && itemstack3.getMaxStackSize() > 1 && (!itemstack2.getHasSubtypes() || itemstack2.getItemDamage() == itemstack3.getItemDamage()) && ItemStack.areItemStacksEqual(itemstack2, itemstack3))
                    {
                        int k1 = itemstack2.stackSize;
                        if (k1 > 0 && k1 + itemstack3.stackSize <= itemstack3.getMaxStackSize())
                        {
                            itemstack3.stackSize += k1;
                            itemstack2.splitStack(k1);
                            if (itemstack2.stackSize == 0)
                            {
                                slot.putStack(null);
                            }
                            slot.onPickupFromSlot(inventoryplayer.getItemStack());
                        }
                    }
                }
            }
        }
        return itemstack;
    }

    public ItemStack playerTransferStackInSlot(int i, EntityPlayer entityplayer)
    {
        Slot slot = (Slot)inventorySlots.get(i);
        Slot slotinput = (Slot)inventorySlots.get(0);
        Slot slotfuel = (Slot)inventorySlots.get(1);
        if(slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            if(i <= 10)
            {
                if(!entityplayer.inventory.addItemStackToInventory(itemstack1.copy()))
                {
                    return null;
                }
                slot.putStack(null);
            }
            else
            {
                if(itemstack1.itemID == TFCItems.Logs.shiftedIndex)
                {
                    if(slotfuel.getHasStack())
                    {
                        return null;
                    }
                    ItemStack stack = itemstack1.copy();
                    stack.stackSize = 1;
                    slotfuel.putStack(stack);
                    itemstack1.stackSize--;
                }
                else
                {
                    if(slotinput.getHasStack())
                    {
                        return null;
                    }
                    ItemStack stack = itemstack1.copy();
                    stack.stackSize = 1;
                    slotinput.putStack(stack);
                    itemstack1.stackSize--;
                }
            }
            if(itemstack1.stackSize == 0)
            {
                slot.putStack(null);
            } else
            {
                slot.onSlotChanged();
            }
        }
        return null;
    }

    public void updateCraftingResults()
    {
        for (int var1 = 0; var1 < this.inventorySlots.size(); ++var1)
        {
            ItemStack var2 = ((Slot)this.inventorySlots.get(var1)).getStack();
            ItemStack var3 = (ItemStack)this.inventoryItemStacks.get(var1);

            if (!ItemStack.areItemStacksEqual(var3, var2))
            {
                var3 = var2 == null ? null : var2.copy();
                this.inventoryItemStacks.set(var1, var3);

                for (int var4 = 0; var4 < this.crafters.size(); ++var4)
                {
                    ((ICrafting)this.crafters.get(var4)).updateCraftingInventorySlot(this, var1, var3);
                }
            }
        }
        for (int var1 = 0; var1 < this.crafters.size(); ++var1)
        {
            ICrafting var2 = (ICrafting)this.crafters.get(var1);
            if (this.firetemp != this.firepit.fireTemperature)
            {
                var2.updateCraftingInventoryInfo(this, 0, (int)this.firepit.fireTemperature);
            }
            if (this.charcoal != this.firepit.charcoalCounter)
            {
                var2.updateCraftingInventoryInfo(this, 1, (int)this.firepit.charcoalCounter);
            }
        }

        firetemp = this.firepit.fireTemperature;
        charcoal = this.firepit.charcoalCounter;
        
    }

    public void updateProgressBar(int par1, int par2)
    {
        if (par1 == 0)
        {
            this.firepit.fireTemperature = par2;
        }
        if (par1 == 1)
        {
            this.firepit.charcoalCounter = par2;
        }

    }
}
