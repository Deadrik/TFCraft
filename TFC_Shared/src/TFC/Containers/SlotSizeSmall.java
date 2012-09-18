package TFC.Containers;

import TFC.Core.TFCItems;
import TFC.Enums.EnumSize;
import TFC.Items.ISize;
import net.minecraft.src.*;

public class SlotSizeSmall extends Slot

{
	EnumSize size = EnumSize.MEDIUM;
	public SlotSizeSmall(IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);

	}

	public boolean isItemValid(ItemStack itemstack)
	{    	
		if(itemstack.getItem() instanceof ISize && ((ISize)itemstack.getItem()).getSize().stackSize >= size.stackSize)
		{
			return true;
		}
		else if (!(itemstack.getItem() instanceof ISize))
			return true;
		
		return false;
	}

	public void onPickupFromSlot(ItemStack itemstack)
	{
		super.onPickupFromSlot(itemstack);
	}
}
