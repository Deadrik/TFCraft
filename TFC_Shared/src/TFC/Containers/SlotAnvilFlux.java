package TFC.Containers;

import TFC.*;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;

public class SlotAnvilFlux extends Slot

{
	public SlotAnvilFlux(EntityPlayer entityplayer, IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);

	}

	public boolean isItemValid(ItemStack itemstack)
	{
		if(itemstack.itemID == TFCItems.Flux.shiftedIndex) {
			return true;
		} else {
			return false;
		}
	}
}
