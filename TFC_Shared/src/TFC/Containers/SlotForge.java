package TFC.Containers;

import TFC.Core.HeatManager;
import TFC.Items.ItemTerraOre;
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
		if(!(itemstack.getItem() instanceof ItemTerraOre))
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
