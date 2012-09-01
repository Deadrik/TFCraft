package TFC.Containers;

import TFC.Core.TFCItems;
import TFC.Core.TFC_Core;
import net.minecraft.src.*;

public class SlotFirepitOut extends Slot

{
	public SlotFirepitOut(EntityPlayer entityplayer, IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);

	}

	public boolean isItemValid(ItemStack itemstack)
	{
		if(itemstack.itemID == TFCItems.terraCeramicMold.shiftedIndex) {
			return true;
		}

		return false;
	}
	
    public int getSlotStackLimit()
    {
        return 1;
    }
}
