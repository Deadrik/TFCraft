package TFC.Containers.Slots;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import TFC.API.ISize;
import TFC.API.Enums.EnumSize;

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
		if(itemstack.getItem() instanceof ISize && ((ISize)itemstack.getItem()).getSize(itemstack).stackSize >= size.stackSize && !except)
			return true;
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
