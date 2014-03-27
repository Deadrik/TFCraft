package TFC.Containers.Slots;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotForgeFuel extends Slot
{
	public SlotForgeFuel(EntityPlayer entityplayer, IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);
	}

	public boolean isItemValid(ItemStack itemstack)
	{
		if(itemstack.getItem() == Items.coal)
			return true;
		return false;
	}

	public int getSlotStackLimit()
	{
		return 1;
	}
}
