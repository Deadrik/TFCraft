package TFC.Containers.Slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import TFC.TFCItems;
import TFC.Items.ItemMeltedMetal;
import TFC.Items.Pottery.ItemPotteryMold;

public class SlotLiquidVessel extends Slot
{
	public SlotLiquidVessel(IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack)
	{
		if ((itemstack.getItem() instanceof ItemMeltedMetal && itemstack.getItemDamage() > 1) ||
				(itemstack.getItem() == TFCItems.CeramicMold && itemstack.getItemDamage() == 1) ||
				(itemstack.getItem() instanceof ItemPotteryMold && itemstack.getItemDamage() > 0))
		{
			return true;
		}
		return false;
	}

	@Override
	public int getSlotStackLimit()
	{
		return 1;
	}
}
