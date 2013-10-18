package TFC.Containers.Slots;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import TFC.API.ISize;
import TFC.API.Enums.EnumSize;
import TFC.Items.Tools.ItemTerraTool;
import TFC.Items.Tools.ItemWeapon;

public class SlotChest extends Slot
{
	EnumSize size = EnumSize.LARGE;
	
	List exceptions;
	
	public SlotChest(IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);
		exceptions = new ArrayList<Item>();
	}
	@Override
	public boolean isItemValid(ItemStack itemstack)
	{    	
		boolean except = exceptions.contains(itemstack.getItem());
		
		if((itemstack.getItem() instanceof ItemTool || itemstack.getItem() instanceof ItemTerraTool || itemstack.getItem() instanceof ItemWeapon ||
				itemstack.getItem() instanceof ItemHoe) && itemstack.getItem() instanceof ISize && 
				((ISize)itemstack.getItem()).getSize().stackSize < EnumSize.SMALL.stackSize )
			return false;
		
		if(itemstack.getItem() instanceof ISize && ((ISize)itemstack.getItem()).getSize().stackSize >= size.stackSize && !except)
		{
			return true;
		}
		else if (!(itemstack.getItem() instanceof ISize) && !except)
			return true;
		
		return false;
	}
	
	public SlotChest setSize(EnumSize s)
	{
		size = s;
		return this;
	}
	
	public SlotChest addItemException(ArrayList<Item> ex)
	{
		exceptions = ex;
		return this;
	}
}
