package net.minecraft.src.TFC_Game;

import net.minecraft.src.*;
import net.minecraft.src.TFC_Core.General.HeatManager;

public class SlotForge extends Slot

{
	public SlotForge(EntityPlayer entityplayer, IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);

	}

	public boolean isItemValid(ItemStack itemstack)
	{
	    HeatManager manager = HeatManager.getInstance();
	    if(manager.findMatchingIndex(itemstack) == null)
	    {
	        return false;
	    }
		if(!(itemstack.getItem().shiftedIndex == TFCItems.OreChunk.shiftedIndex))
		{
			return true;
		}
		return false;
	}
	
	public int getSlotStackLimit()
	{
	    return 1;
	}
}
