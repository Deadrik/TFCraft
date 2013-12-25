package TFC.Containers.Slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import TFC.API.ISize;

public class SlotSizeMedium extends SlotSize
{
	public SlotSizeMedium(IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack)
	{    	
		if(itemstack.getItem() instanceof ISize && ((ISize)itemstack.getItem()).getSize(itemstack).stackSize >= size.stackSize)
		{
			return true;
		}
		else if (!(itemstack.getItem() instanceof ISize)) {
			return true;
		}

		return false;
	}
}
