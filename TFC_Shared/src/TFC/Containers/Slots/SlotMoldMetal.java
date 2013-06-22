package TFC.Containers.Slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import TFC.Core.TFC_ItemHeat;
import TFC.Items.ItemMeltedMetal;

public class SlotMoldMetal extends Slot

{
	public SlotMoldMetal(IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);

	}

	@Override
	public boolean isItemValid(ItemStack itemstack)
	{    	
		if(itemstack.getItem() instanceof ItemMeltedMetal && TFC_ItemHeat.getIsLiquid(itemstack))
		{
			return true;
		}
		return false;
	}
}
