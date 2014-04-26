package TFC.Containers.Slots;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import TFC.Items.Tools.ItemHammer;

public class SlotAnvilHammer extends Slot
{
	public SlotAnvilHammer(EntityPlayer entityplayer, IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);
	}

	public boolean isItemValid(ItemStack itemstack)
	{
		if(itemstack.getItem() instanceof ItemHammer) {
			return true;
		} else {
			return false;
		}
	}
}
