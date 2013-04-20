package TFC.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerTFC extends Container
{

	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	protected boolean mergeItemStack(ItemStack is, int slotStart, int slotFinish, boolean par4)
    {
        boolean var5 = false;
        int slotIndex = slotStart;

        if (par4)
        {
            slotIndex = slotFinish - 1;
        }

        Slot slot;
        ItemStack slotstack;

        if (is.isStackable())
        {
            while (is.stackSize > 0 && (!par4 && slotIndex < slotFinish || par4 && slotIndex >= slotStart))
            {
                slot = (Slot)this.inventorySlots.get(slotIndex);
                slotstack = slot.getStack();

                if (slotstack != null && slotstack.itemID == is.itemID && (!is.getHasSubtypes() || is.getItemDamage() == slotstack.getItemDamage()) && ItemStack.areItemStackTagsEqual(is, slotstack) && 
                		slotstack.stackSize < slot.getSlotStackLimit())
                {
                    int mergedStackSize = is.stackSize + getSmaller(slotstack.stackSize, slot.getSlotStackLimit());

                    //First we check if we can add the two stacks together and the resulting stack is smaller than the maximum size for the slot or the stack
                    if (mergedStackSize <= is.getMaxStackSize() && mergedStackSize <= slot.getSlotStackLimit())
                    {
                        is.stackSize = 0;
                        slotstack.stackSize = mergedStackSize;
                        slot.onSlotChanged();
                        var5 = true;
                    }
                    else if (slotstack.stackSize < is.getMaxStackSize())
                    {
                        is.stackSize -= is.getMaxStackSize() - slotstack.stackSize;
                        slotstack.stackSize = is.getMaxStackSize();
                        slot.onSlotChanged();
                        var5 = true;
                    }
                }

                if (par4)
                {
                    --slotIndex;
                }
                else
                {
                    ++slotIndex;
                }
            }
        }

        if (is.stackSize > 0)
        {
            if (par4)
            {
                slotIndex = slotFinish - 1;
            }
            else
            {
                slotIndex = slotStart;
            }

            while (!par4 && slotIndex < slotFinish || par4 && slotIndex >= slotStart)
            {
                slot = (Slot)this.inventorySlots.get(slotIndex);
                slotstack = slot.getStack();
                if (slotstack == null && slot.isItemValid(is) && slot.getSlotStackLimit() < is.stackSize)
                {
                    slot.putStack(is.copy());
                    slot.onSlotChanged();
                    is.stackSize -= slot.getSlotStackLimit();
                    var5 = true;
                    break;
                }
                else if (slotstack == null && slot.isItemValid(is))
                {
                    slot.putStack(is.copy());
                    slot.onSlotChanged();
                    is.stackSize = 0;
                    var5 = true;
                    break;
                }

                if (par4)
                {
                    --slotIndex;
                }
                else
                {
                    ++slotIndex;
                }
            }
        }

        return var5;
    }
	
	protected int getSmaller(int i, int j)
	{
		if(i < j) return i;
		else return j;
	}
	
}
