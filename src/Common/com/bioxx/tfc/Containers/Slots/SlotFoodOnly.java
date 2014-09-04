package com.bioxx.tfc.Containers.Slots;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import com.bioxx.tfc.api.Enums.EnumFoodGroup;
import com.bioxx.tfc.api.Interfaces.IFood;
import com.bioxx.tfc.api.Interfaces.ISize;

public class SlotFoodOnly extends SlotSize
{
	List excpetionsFG = new ArrayList<EnumFoodGroup>();
	List inclusionsFG = new ArrayList<EnumFoodGroup>();
	public SlotFoodOnly(IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack)
	{
		if(itemstack.getItem() instanceof IFood)
		{
			EnumFoodGroup efg = ((IFood)itemstack.getItem()).getFoodGroup();
			if(efg == null)
				return false;
			boolean except = excpetionsFG.contains(efg);
			boolean include = inclusionsFG.contains(efg) || inclusionsFG.size() == 0;
			if (except || !include)
				return false;
			if (itemstack.getItem() instanceof ISize && ((ISize) itemstack.getItem()).getSize(itemstack).stackSize >= size.stackSize)
				return super.isItemValid(itemstack);
		}
		return false;
	}

	public SlotFoodOnly addFGException(EnumFoodGroup... ex)
	{
		for(int i = 0; i < ex.length; i++)
			excpetionsFG.add(ex[i]);
		return this;
	}

	public SlotFoodOnly addFGInclusion(EnumFoodGroup... ex)
	{
		for(int i = 0; i < ex.length; i++)
			inclusionsFG.add(ex[i]);
		return this;
	}
}
