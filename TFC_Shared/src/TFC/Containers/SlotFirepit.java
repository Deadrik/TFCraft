package TFC.Containers;

import TFC.*;
import TFC.Core.TFC_Core;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;

public class SlotFirepit extends Slot

{
	public SlotFirepit(EntityPlayer entityplayer, IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack)
	{
		return false;
	}
	
	public int getSlotStackLimit()
    {
	    return 64;
    }
}
