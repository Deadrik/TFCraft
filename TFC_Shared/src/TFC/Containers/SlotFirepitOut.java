package TFC.Containers;

import TFC.*;
import TFC.Core.TFC_Core;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;

public class SlotFirepitOut extends Slot

{
	public SlotFirepitOut(EntityPlayer entityplayer, IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);

	}

	public boolean isItemValid(ItemStack itemstack)
	{
		if(itemstack.itemID == TFCItems.CeramicMold.shiftedIndex) {
			return true;
		}

		return false;
	}
	
    public int getSlotStackLimit()
    {
        return 1;
    }
}
