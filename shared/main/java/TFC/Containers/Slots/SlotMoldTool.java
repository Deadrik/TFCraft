package TFC.Containers.Slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import TFC.Core.TFC_ItemHeat;
import TFC.Items.ItemMeltedMetal;
import TFC.Items.Pottery.ItemPotteryMold;

public class SlotMoldTool extends Slot

{
	public SlotMoldTool(IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);

	}

	@Override
	public boolean isItemValid(ItemStack itemstack)
	{    	
		if(itemstack.getItem() instanceof ItemPotteryMold || (itemstack.getItem() instanceof ItemMeltedMetal && TFC_ItemHeat.getIsLiquid(itemstack)))
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
