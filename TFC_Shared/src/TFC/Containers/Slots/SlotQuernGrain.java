package TFC.Containers.Slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import TFC.TFCItems;

public class SlotQuernGrain extends Slot {
	public SlotQuernGrain(IInventory iinventory, int i, int j, int k) {
		super(iinventory, i, j, k);
	}

	public boolean isItemValid(ItemStack itemstack) {
		if(itemstack.getItem() == TFCItems.WheatGrain
			|| itemstack.getItem() == TFCItems.BarleyGrain
			|| itemstack.getItem() == TFCItems.RyeGrain
			|| itemstack.getItem() == TFCItems.OatGrain
			|| itemstack.getItem() == TFCItems.RiceGrain
			|| itemstack.getItem() == TFCItems.MaizeEar)
		{
			return true;
		}
		return false;
	}
}
