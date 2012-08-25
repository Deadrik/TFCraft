package TFC.Containers;

import TFC.Core.CraftingManagerTFC;
import TFC.TileEntities.TileEntityTerraWorkbench;
import net.minecraft.src.Block;
import net.minecraft.src.Container;
import net.minecraft.src.CraftingManager;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryCraftResult;
import net.minecraft.src.InventoryCrafting;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;
import net.minecraft.src.SlotCrafting;
import net.minecraft.src.World;

public class ContainerKnapping extends Container
{
    /** The crafting matrix inventory (3x3). */
    public InventoryCrafting craftMatrix = new InventoryCrafting(this, 5, 5);


    /** The crafting result, size 1. */
    public IInventory craftResult = new InventoryCraftResult();
    private World worldObj;

    public ContainerKnapping(InventoryPlayer inventoryplayer, ItemStack is, World world)
    {
        for (int j1 = 0; j1 < 25; j1++)
        {
            craftMatrix.setInventorySlotContents(j1, is.copy());
        }
        this.worldObj = world;

        int var6;
        int var7;

        addSlotToContainer(new SlotCraftingMetal(inventoryplayer.player, craftMatrix, craftResult,0, 128, 35));

        for(int j = 0; j < 9; j++)
        {
        	addSlotToContainer(new Slot(inventoryplayer, j, 8 + j * 18, 151));
        }

        for(int i = 0; i < 3; i++)
        {
            for(int k = 0; k < 9; k++)
            {
            	addSlotToContainer(new Slot(inventoryplayer, k + i * 9 + 9, 8 + k * 18, 93 + i * 18));
            }

        }

        for (int l = 0; l < 5; l++)
        {
            for (int k1 = 0; k1 < 5; k1++)
            {
            	addSlotToContainer(new SlotBlocked(craftMatrix, k1 + l * 5, 8 + k1 * 16, l * 16 - 1));
            }
        }



        this.onCraftMatrixChanged(this.craftMatrix);
    }

    /**
     * Callback for when the crafting matrix is changed.
     */
    public void onCraftMatrixChanged(IInventory par1IInventory)
    {
        this.craftResult.setInventorySlotContents(0, CraftingManagerTFC.getInstance().findMatchingRecipe(this.craftMatrix));
    }

    /**
     * Called to transfer a stack from one inventory to the other eg. when shift clicking.
     */
    public ItemStack transferStackInSlot(int par1)
    {
        ItemStack var2 = null;
        Slot grabbedSlot = (Slot)this.inventorySlots.get(par1);

        if(grabbedSlot != null && grabbedSlot.getHasStack())
        {
            ItemStack var4 = grabbedSlot.getStack();
            var2 = var4.copy();

            if(par1 < 10)
            {
                if (!this.mergeItemStack(var4, 10, 36, true))
                {
                    return null;
                }
            }
            else if(par1 >= 10 && par1 < 37)
            {
                if (!this.mergeItemStack(var4, 0, 9, true))
                {
                    return null;
                }
            }
            else if(par1 >= 37 && par1 < 62)
            {
                if (!this.mergeItemStack(var4, 0, 36, true))
                {
                    return null;
                }
            }

            if (var4.stackSize == 0)
            {
                grabbedSlot.putStack((ItemStack)null);
            }
            else
            {
                grabbedSlot.onSlotChanged();
            }

            if (var4.stackSize == var2.stackSize)
            {
                return null;
            }

            grabbedSlot.onPickupFromSlot(var4);
        }

        //        if (var3 != null && var3.getHasStack())
            //        {
            //            ItemStack var4 = var3.getStack();
        //            var2 = var4.copy();
        //            
        //            if((var3) instanceof SlotBlocked && 
        //                    var3.getHasStack())
        //            {
        //                var3.inventory.setInventorySlotContents(par1-1, null);
        //                this.onCraftMatrixChanged(this.craftMatrix);
        //            }
        //
        //            if (par1 == 0)
        //            {
        //                if (!this.mergeItemStack(var4, 10, 46, true))
        //                {
        //                    return null;
        //                }
        //
        //                //var3.func_48433_a(var4, var2);
        //            }
        //            else if (par1 >= 10 && par1 < 37)
        //            {
        //                if (!this.mergeItemStack(var4, 0, 9, false))
        //                {
        //                    return null;
        //                }
        //            }
        //            else if (par1 >= 0 && par1 < 9)
        //            {
        //                if (!this.mergeItemStack(var4, 10, 37, false))
        //                {
        //                    return null;
        //                }
        //            }
        //            else if (!this.mergeItemStack(var4, 10, 46, false))
        //            {
        //                return null;
        //            }
        //
        //            if (var4.stackSize == 0)
        //            {
        //                var3.putStack((ItemStack)null);
        //            }
        //            else
        //            {
        //                var3.onSlotChanged();
        //            }
        //
        //            if (var4.stackSize == var2.stackSize)
        //            {
        //                return null;
        //            }
        //
        //            var3.onPickupFromSlot(var4);
        //        }

        return var2;
    }

    @Override
    public boolean canInteractWith(EntityPlayer var1)
    {
        // TODO Auto-generated method stub
        return true;
    }
}
