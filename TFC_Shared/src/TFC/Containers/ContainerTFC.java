package TFC.Containers;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;
import net.minecraft.src.TerraFirmaCraft;

public class ContainerTFC extends Container
{

    @Override
    public boolean canInteractWith(EntityPlayer var1)
    {
        // TODO Auto-generated method stub
        return false;
    }
    
    @SideOnly(Side.CLIENT)

    /**
     * places itemstacks in first x slots, x being aitemstack.lenght
     */
    public void putStacksInSlots(ItemStack[] par1ArrayOfItemStack)
    {
        for (int var2 = 0; var2 < par1ArrayOfItemStack.length; ++var2)
        {
        	if(par1ArrayOfItemStack[var2] != null)
        		this.getSlot(var2).putStack(par1ArrayOfItemStack[var2]);
        }
    }
    
    protected boolean mergeItemStack(ItemStack is, int start, int end, boolean par4, int maxStack)
    {
        boolean var5 = false;
        int var6 = start;

        if (par4)
        {
            var6 = end - 1;
        }

        Slot var7;
        ItemStack var8;

        if (is.isStackable())
        {
            while (is.stackSize > 0 && (!par4 && var6 < end || par4 && var6 >= start))
            {
                var7 = (Slot)this.inventorySlots.get(var6);
                var8 = var7.getStack();

                if (var8 != null && var8.itemID == is.itemID && (!is.getHasSubtypes() || is.getItemDamage() == var8.getItemDamage()) && 
                		ItemStack.areItemStacksEqual(is, var8) && var8.stackSize+is.stackSize < maxStack)
                {
                    int var9 = var8.stackSize + is.stackSize;

                    if (var9 <= is.getMaxStackSize())
                    {
                        is.stackSize = 0;
                        var8.stackSize = var9;
                        var7.onSlotChanged();
                        var5 = true;
                    }
                    else if (var8.stackSize < is.getMaxStackSize())
                    {
                        is.stackSize -= is.getMaxStackSize() - var8.stackSize;
                        var8.stackSize = is.getMaxStackSize();
                        var7.onSlotChanged();
                        var5 = true;
                    }
                }

                if (par4)
                {
                    --var6;
                }
                else
                {
                    ++var6;
                }
            }
        }

        if (is.stackSize > 0)
        {
            if (par4)
            {
                var6 = end - 1;
            }
            else
            {
                var6 = start;
            }

            while (!par4 && var6 < end || par4 && var6 >= start)
            {
                var7 = (Slot)this.inventorySlots.get(var6);
                var8 = var7.getStack();

                if (var8 == null)
                {
                    ItemStack newIS = is.copy();
                    if(newIS.stackSize > maxStack)
                    {
                        newIS.stackSize = maxStack;
                        is.stackSize-=maxStack;
                    }
                    else
                    {
                        is.stackSize = 0;
                    }
                    var7.putStack(newIS);
                    var7.onSlotChanged();
                    var5 = true;
                    break;
                }

                if (par4)
                {
                    --var6;
                }
                else
                {
                    ++var6;
                }
            }
        }

        return var5;
    }

}
