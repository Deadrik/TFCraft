package com.bioxx.tfc.Containers.Slots;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.bioxx.tfc.Items.ItemMeltedMetal;
import com.bioxx.tfc.Items.Pottery.ItemPotteryBase;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Interfaces.IBag;
import com.bioxx.tfc.api.Interfaces.IFood;
import com.bioxx.tfc.api.Interfaces.IItemFoodBlock;
import com.bioxx.tfc.api.Interfaces.ISize;

public class SlotSizeSmallVessel extends Slot
{
	private EnumSize size = EnumSize.SMALL;
	private List<Item> exceptions;

	public SlotSizeSmallVessel(IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);
		exceptions = new ArrayList<Item>();
	}

	@Override
	public boolean isItemValid(ItemStack itemstack)
	{
		if(itemstack.getItem() instanceof IBag ||
				itemstack.getItem() instanceof ItemMeltedMetal ||
				itemstack.getItem() instanceof ItemPotteryBase)
		{
			return false;
		}

		if (itemstack.getItem() instanceof ISize && ((ISize) itemstack.getItem()).getSize(itemstack).stackSize >= size.stackSize &&
				(itemstack.getItem() instanceof IFood || itemstack.getItem() instanceof IItemFoodBlock) &&
				!(itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey(Food.WEIGHT_TAG) && itemstack.getTagCompound().hasKey(Food.DECAY_TAG)))
				return false;

		boolean except = exceptions.contains(itemstack.getItem());
		if(itemstack.getItem() instanceof ISize && ((ISize)itemstack.getItem()).getSize(itemstack).stackSize >= size.stackSize && !except)
			return true;
		else if (!(itemstack.getItem() instanceof ISize) && !except)
			return true;
		return false;
	}

	public SlotSizeSmallVessel addItemException(List<Item> ex)
	{
		exceptions = ex;
		return this;
	}
}
