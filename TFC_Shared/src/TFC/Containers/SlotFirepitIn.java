package TFC.Containers;

import TFC.*;
import TFC.Core.TFC_Core;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.Slot;

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
