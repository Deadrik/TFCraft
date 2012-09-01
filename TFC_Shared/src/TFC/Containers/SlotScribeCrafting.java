package TFC.Containers;

import TFC.Core.TFCItems;
import net.minecraft.src.*;

public class SlotScribeCrafting extends Slot
{
	EntityPlayer player;
	public SlotScribeCrafting(EntityPlayer entityplayer, IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);
		player = entityplayer;
	}

	public boolean isItemValid(ItemStack itemstack)
	{
		if(itemstack.itemID == TFCItems.terraInk.shiftedIndex)
		{
			return true;
		}
		return false;
	}

	public void onPickupFromSlot(ItemStack itemstack)
	{
		super.onPickupFromSlot(itemstack);
	}

	public void onSlotChanged()
	{
		inventory.onInventoryChanged();

	}
}
