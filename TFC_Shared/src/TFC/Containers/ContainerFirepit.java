package TFC.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import TFC.TileEntities.TileEntityFirepit;

public class ContainerFirepit extends ContainerTFC
{
    private TileEntityFirepit firepit;

    private float firetemp;
    private int charcoal;


    public ContainerFirepit(InventoryPlayer inventoryplayer, TileEntityFirepit tileentityfirepit, World world, int x, int y, int z)
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
    @Override
    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return true;
    }
    
    @Override
    public ItemStack transferStackInSlot(EntityPlayer p, int i)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot) inventorySlots.get(i);
        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (i < 11)
            {
                if (!mergeItemStack(itemstack1, 11, inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (!mergeItemStack(itemstack1, 0, 10, true))
            {
                return null;
            }
            if (itemstack1.stackSize == 0)
            {
                slot.putStack(null);
            }
            else
            {
                slot.onSlotChanged();
            }
        }
        detectAndSendChanges();
        return itemstack;
    }
    
    @Override
    public void detectAndSendChanges()
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
                    ((ICrafting)this.crafters.get(var4)).sendSlotContents(this, var1, var3);
                }
            }
        }
        for (int var1 = 0; var1 < this.crafters.size(); ++var1)
        {
            ICrafting var2 = (ICrafting)this.crafters.get(var1);
            if (this.firetemp != this.firepit.fireTemperature)
            {
                var2.sendProgressBarUpdate(this, 0, (int)this.firepit.fireTemperature);
            }
            if (this.charcoal != this.firepit.charcoalCounter)
            {
                var2.sendProgressBarUpdate(this, 1, this.firepit.charcoalCounter);
            }
        }

        firetemp = this.firepit.fireTemperature;
        charcoal = this.firepit.charcoalCounter;
        
    }

    @Override
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
