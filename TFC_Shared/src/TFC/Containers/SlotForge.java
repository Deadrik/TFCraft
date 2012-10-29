package TFC.Containers;

import TFC.*;
import TFC.Core.HeatManager;
import TFC.Items.ItemOre;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;

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
		if(!(itemstack.getItem() instanceof ItemOre))
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
