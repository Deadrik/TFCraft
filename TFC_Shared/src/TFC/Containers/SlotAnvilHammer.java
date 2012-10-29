package TFC.Containers;

import TFC.*;
import TFC.Items.ItemHammer;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;

public class SlotAnvilHammer extends Slot

{
	public SlotAnvilHammer(EntityPlayer entityplayer, IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);
	}

	public boolean isItemValid(ItemStack itemstack)
	{
		if(itemstack.getItem() instanceof ItemHammer) {
			return true;
		} else {
			return false;
		}
	}
}
