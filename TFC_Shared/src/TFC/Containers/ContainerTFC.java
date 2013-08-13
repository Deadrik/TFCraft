package TFC.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
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
		float temp3 = is3.getTagCompound().hasKey("temperature") ? is3.getTagCompound().getFloat("temperature") : -1000;
		float temp4 = is4.getTagCompound().hasKey("temperature") ? is4.getTagCompound().getFloat("temperature") : -1000;
		is3.getTagCompound().removeTag("temperature");
		is4.getTagCompound().removeTag("temperature");

		boolean isBaseEqual = is3.getTagCompound().equals(is4.getTagCompound());

		if(isBaseEqual && ((temp3 - temp4) < 5 || (temp3 - temp4) > -5)) {
			return true;
		}

		return false;
	}
}
