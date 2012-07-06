package TFC.Containers;

import TFC.Core.TFC_Core;
import net.minecraft.src.*;

public class SlotFirepitIn extends Slot

{
	public SlotFirepitIn(EntityPlayer entityplayer, IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);

	}

	public int getSlotStackLimit()
    {
	    return 1;
    }

}
