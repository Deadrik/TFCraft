package TFC.Containers.Slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import TFC.TFCItems;
import TFC.Core.TFC_ItemHeat;
import TFC.Items.ItemMeltedMetal;
import TFC.Items.Pottery.ItemPotteryMold;

public class SlotMoldTool2 extends Slot

{
	public SlotMoldTool2(IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);

	}

	@Override
	public boolean isItemValid(ItemStack itemstack)
	{    	
		if(itemstack.getItem() instanceof ItemPotteryMold || (itemstack.getItem() instanceof ItemMeltedMetal && TFC_ItemHeat.getIsLiquid(itemstack)) ||
				(itemstack.getItem().itemID == TFCItems.CeramicMold.itemID && itemstack.getItemDamage() == 1))
		{
			return true;
		}
		return false;
	}
	
	@Override
	public int getSlotStackLimit() {
		return 1;
	}
}
