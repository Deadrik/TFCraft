package TFC.Containers;

import TFC.Core.HeatManager;
import net.minecraft.src.*;

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
