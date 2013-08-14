package TFC.Containers.Slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import TFC.TFCItems;

public class SlotQuernGrain extends Slot {
	public SlotQuernGrain(IInventory iinventory, int i, int j, int k) {
		super(iinventory, i, j, k);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack) {
		if(itemstack.getItem() == TFCItems.WheatGrain
				|| itemstack.getItem() == TFCItems.BarleyGrain
				|| itemstack.getItem() == TFCItems.RyeGrain
				|| itemstack.getItem() == TFCItems.OatGrain
				|| itemstack.getItem() == TFCItems.RiceGrain
				|| itemstack.getItem() == TFCItems.MaizeEar
				|| (itemstack.getItem() == TFCItems.OreChunk && itemstack.getItemDamage() == 16)//Kaolinite
				|| (itemstack.getItem() == TFCItems.OreChunk && itemstack.getItemDamage() == 20))//Graphite
		{
			return true;
		}
		return false;
	}
}
