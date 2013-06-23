package TFC.Containers.Slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import TFC.API.IBag;
import TFC.API.ISize;
import TFC.API.Enums.EnumSize;

public class SlotSizeSmallVessel extends Slot

{
	EnumSize size = EnumSize.SMALL;
	public SlotSizeSmallVessel(IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);

	}
	@Override
	public boolean isItemValid(ItemStack itemstack)
	{    	
		if(itemstack.getItem() instanceof IBag)
			return false;
		
		if(itemstack.getItem() instanceof ISize && ((ISize)itemstack.getItem()).getSize().stackSize >= size.stackSize)
		{
			return true;
		}
		else if (!(itemstack.getItem() instanceof ISize))
			return true;
		
		return false;
	}

}
