package TFC.Containers.Slots;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import TFC.API.HeatRegistry;
import TFC.Items.ItemOre;

public class SlotForge extends Slot
{
	public SlotForge(EntityPlayer entityplayer, IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);
	}

	public boolean isItemValid(ItemStack itemstack)
	{
		HeatRegistry manager = HeatRegistry.getInstance();
		if(manager.findMatchingIndex(itemstack) == null)
			return false;

		if(!(itemstack.getItem() instanceof ItemOre))
			return true;

		return false;
	}

	public int getSlotStackLimit()
	{
		return 1;
	}
}
