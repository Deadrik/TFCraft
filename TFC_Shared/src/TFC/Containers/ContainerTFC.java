package TFC.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ContainerTFC extends Container
{

	@Override
	public boolean canInteractWith(EntityPlayer var1) 
	{
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

				if (slotstack != null
						&& slotstack.itemID == is.itemID
						//                	&& !is.getHasSubtypes()
						&& is.getItemDamage() == slotstack.getItemDamage()
						&& ItemStack.areItemStackTagsEqual(is, slotstack)
						&& slotstack.stackSize < slot.getSlotStackLimit())
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
		if(i < j) {
			return i;
		} else {
			return j;
		}
	}

	@Override
	public void detectAndSendChanges()
	{
		for (int i = 0; i < this.inventorySlots.size(); ++i)
		{
			ItemStack itemstack = ((Slot)this.inventorySlots.get(i)).getStack();
			ItemStack itemstack1 = (ItemStack)this.inventoryItemStacks.get(i);

			if (!areItemStacksEqual(itemstack1, itemstack))
			{
				itemstack1 = itemstack == null ? null : itemstack.copy();
				this.inventoryItemStacks.set(i, itemstack1);

				for (int j = 0; j < this.crafters.size(); ++j)
				{
					((ICrafting)this.crafters.get(j)).sendSlotContents(this, i, itemstack1);
				}
			}
		}
	}	

	public boolean areItemStacksEqual(ItemStack is1, ItemStack is2)
	{
		return is1 == null && is2 == null ? true : (is1 != null && is2 != null ? isItemStackEqual(is1, is2) : false);
	}

	private boolean isItemStackEqual(ItemStack is1, ItemStack is2)
	{
		return is1.stackSize != is2.stackSize ? false : 
			(is1.itemID != is2.itemID ? false : 
				(is1.getItemDamage() != is2.getItemDamage() ? false : 
					(is1.stackTagCompound == null && is2.stackTagCompound != null ? false : 
						is1.stackTagCompound == null || areCompoundsEqual(is1, is2))));
	}

	private boolean areCompoundsEqual(ItemStack is1, ItemStack is2)
	{
		ItemStack is3 = is1.copy(); 
		ItemStack is4 = is2.copy(); 

		NBTTagCompound is3Tags = is3.getTagCompound();
		NBTTagCompound is4Tags = is4.getTagCompound();

		if (is3Tags == null)
		{
			return (is4Tags == null) || is4Tags.hasNoTags();
		}

		if (is4Tags == null)
		{
			return is3Tags.hasNoTags();
		}

		float temp3 = is3Tags.hasKey("temperature") ? is3Tags.getFloat("temperature") : -1000;
		float temp4 = is4Tags.hasKey("temperature") ? is4Tags.getFloat("temperature") : -1000;
		is3Tags.removeTag("temperature");
		is4Tags.removeTag("temperature");

		return is3Tags.equals(is4Tags) &&  Math.abs(temp3 - temp4) < 5;
	}
}
