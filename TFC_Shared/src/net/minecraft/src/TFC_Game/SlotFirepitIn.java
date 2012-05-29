package net.minecraft.src.TFC_Game;

import net.minecraft.src.*;
import net.minecraft.src.TFC_Core.TFC_Core;

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
