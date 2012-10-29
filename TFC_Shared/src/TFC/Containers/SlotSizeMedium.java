package TFC.Containers;

import TFC.*;
import TFC.Enums.EnumSize;
import TFC.Items.ISize;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;

public class SlotSizeMedium extends SlotSize
{
	public SlotSizeMedium(IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);
	}
	
	@Override
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
}
