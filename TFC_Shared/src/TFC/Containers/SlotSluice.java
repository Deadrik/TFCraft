package TFC.Containers;

import TFC.*;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;

public class SlotSluice extends Slot

{
	public SlotSluice(EntityPlayer entityplayer, IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);

	}
	@Override
	public boolean isItemValid(ItemStack itemstack)
	{
		return false;
	}
}
