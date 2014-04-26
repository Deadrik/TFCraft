package TFC.Containers.Slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import TFC.TFCItems;
import TFC.Items.ItemMeltedMetal;

public class SlotMetal extends Slot
{
	public SlotMetal(IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack)
	{
		if(itemstack.getItem() instanceof ItemMeltedMetal || itemstack.getItem() == TFCItems.CeramicMold)
			return true;
		return false;
	}
}
