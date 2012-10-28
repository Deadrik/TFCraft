package TFC.Containers;

import TFC.Core.TFCItems;
import TFC.Enums.EnumSize;
import TFC.Items.ISize;
import TFC.Items.ItemTerraFood;
import net.minecraft.src.*;

public class SlotFoodOnly extends Slot

{
	EnumSize size = EnumSize.MEDIUM;
	public SlotFoodOnly(IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);

	}

	public boolean isItemValid(ItemStack itemstack)
	{    	
		if(itemstack.getItem() instanceof ISize && ((ISize)itemstack.getItem()).getSize().stackSize >= size.stackSize && itemstack.getItem() instanceof ItemTerraFood)
		{
			return true;
		}
		
		return false;
	}
}
