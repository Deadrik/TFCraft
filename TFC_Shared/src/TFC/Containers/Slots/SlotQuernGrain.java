package TFC.Containers.Slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
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
				|| itemstack.getItem() == Item.bone
				|| (itemstack.getItem() == TFCItems.OreChunk && itemstack.getItemDamage() == 16)//Kaolinite
				|| (itemstack.getItem() == TFCItems.OreChunk && itemstack.getItemDamage() == 20)//Graphite
				|| (itemstack.getItem() == TFCItems.OreChunk && itemstack.getItemDamage() == 27)//Cinnabar
				|| (itemstack.getItem() == TFCItems.OreChunk && itemstack.getItemDamage() == 28)//Cryolite
				|| (itemstack.getItem() == TFCItems.OreChunk && itemstack.getItemDamage() == 34)//Lapis Lazuli
				|| (itemstack.getItem() == TFCItems.OreChunk && itemstack.getItemDamage() == 11)//Limonite
				|| (itemstack.getItem() == TFCItems.OreChunk && itemstack.getItemDamage() == 9)//Malachite
				|| (itemstack.getItem() == TFCItems.OreChunk && itemstack.getItemDamage() == 3))//Hematite
		{
			return true;
		}
		return false;
	}
}
