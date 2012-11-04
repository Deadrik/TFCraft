package TFC.Containers;

import TFC.*;
import TFC.Enums.EnumSize;
import TFC.Food.ItemTerraFood;
import TFC.Items.ISize;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;

public class SlotQuern extends Slot

{
	public SlotQuern(IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);

	}

	public boolean isItemValid(ItemStack itemstack)
	{    	
		if(itemstack.getItem() == TFCItems.Quern)
		{
			return true;
		}
		
		return false;
	}
}
