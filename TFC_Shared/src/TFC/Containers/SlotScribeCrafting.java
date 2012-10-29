package TFC.Containers;

import TFC.*;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;

public class SlotScribeCrafting extends Slot
{
	EntityPlayer player;
	public SlotScribeCrafting(EntityPlayer entityplayer, IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);
		player = entityplayer;
	}
	@Override
	public boolean isItemValid(ItemStack itemstack)
	{
		if(itemstack.itemID == TFCItems.Ink.shiftedIndex)
		{
			return true;
		}
		return false;
	}

	@Override
	public void onSlotChanged()
	{
		inventory.onInventoryChanged();
	}
}
