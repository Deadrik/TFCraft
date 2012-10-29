package TFC.Containers;

import java.util.ArrayList;
import java.util.List;

import TFC.*;
import TFC.Enums.EnumSize;
import TFC.Items.ISize;
import net.minecraft.src.IInventory;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;

public class SlotSize extends Slot
{
	EnumSize size = EnumSize.MEDIUM;
	
	List excpetions;
	
	public SlotSize(IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);
		excpetions = new ArrayList<Item>();
	}
	@Override
	public boolean isItemValid(ItemStack itemstack)
	{    	
		boolean except = excpetions.contains(itemstack.getItem());
		
		if(itemstack.getItem() instanceof ISize && ((ISize)itemstack.getItem()).getSize().stackSize >= size.stackSize && !except)
		{
			return true;
		}
		else if (!(itemstack.getItem() instanceof ISize) && !except)
			return true;
		
		return false;
	}
	
	public SlotSize setSize(EnumSize s)
	{
		size = s;
		return this;
	}
	
	public SlotSize addItemException(ArrayList<Item> ex)
	{
		excpetions = ex;
		return this;
	}
}
