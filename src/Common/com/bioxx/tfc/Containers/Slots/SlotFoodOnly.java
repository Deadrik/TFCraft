package com.bioxx.tfc.Containers.Slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.bioxx.tfc.Food.ItemMeal;
import com.bioxx.tfc.api.IFood;
import com.bioxx.tfc.api.IItemFoodBlock;
import com.bioxx.tfc.api.ISize;
import com.bioxx.tfc.api.Enums.EnumSize;

public class SlotFoodOnly extends Slot
{
	EnumSize size = EnumSize.MEDIUM;
	public SlotFoodOnly(IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack)
	{
		if (itemstack.getItem() instanceof ISize && ((ISize) itemstack.getItem()).getSize(itemstack).stackSize >= size.stackSize &&
			!(itemstack.getItem() instanceof ItemMeal) &&
			(itemstack.getItem() instanceof IFood || itemstack.getItem() instanceof IItemFoodBlock) &&
			(itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey("foodWeight") && itemstack.getTagCompound().hasKey("foodDecay")))
			return true;
		return false;
	}
}
