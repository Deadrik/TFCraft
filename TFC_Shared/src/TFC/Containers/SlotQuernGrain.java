package TFC.Containers;

import TFC.*;
import TFC.Enums.EnumSize;
import TFC.Food.ItemTerraFood;
import TFC.Items.ISize;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;

public class SlotQuernGrain extends Slot

{
	public SlotQuernGrain(IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);

	}

	public boolean isItemValid(ItemStack itemstack)
	{    	
		if(itemstack.getItem() == TFCItems.WheatGrain || itemstack.getItem() == TFCItems.BarleyGrain || itemstack.getItem() == TFCItems.RyeGrain || 
				itemstack.getItem() == TFCItems.OatGrain || itemstack.getItem() == TFCItems.RiceGrain || itemstack.getItem() == TFCItems.MaizeEar)
		{
			return true;
		}
		
		return false;
	}
}
