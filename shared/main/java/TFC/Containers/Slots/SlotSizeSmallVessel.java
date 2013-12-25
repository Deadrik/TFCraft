package TFC.Containers.Slots;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import TFC.API.IBag;
import TFC.API.ISize;
import TFC.API.Enums.EnumSize;
import TFC.Items.ItemMeltedMetal;
import TFC.Items.Pottery.ItemPotteryBase;

public class SlotSizeSmallVessel extends Slot

{
	EnumSize size = EnumSize.SMALL;
	List exceptions;
	
	public SlotSizeSmallVessel(IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);
		exceptions = new ArrayList<Item>();

	}
	@Override
	public boolean isItemValid(ItemStack itemstack)
	{    	
		boolean except = exceptions.contains(itemstack.getItem());
		
		if(itemstack.getItem() instanceof IBag || itemstack.getItem() instanceof ItemMeltedMetal || 
				itemstack.getItem() instanceof ItemPotteryBase)
			return false;
		
		if(itemstack.getItem() instanceof ISize && ((ISize)itemstack.getItem()).getSize().stackSize >= size.stackSize && !except)
		{
			return true;
		}
		else if (!(itemstack.getItem() instanceof ISize) && !except)
			return true;
		
		return false;
	}
	
	public SlotSizeSmallVessel addItemException(ArrayList<Item> ex)
	{
		exceptions = ex;
		return this;
	}

}
