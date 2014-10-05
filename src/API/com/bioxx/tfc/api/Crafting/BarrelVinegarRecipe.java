package com.bioxx.tfc.api.Crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import com.bioxx.tfc.api.Enums.EnumFoodGroup;
import com.bioxx.tfc.api.Interfaces.IFood;

public class BarrelVinegarRecipe extends BarrelRecipe
{

	public BarrelVinegarRecipe(FluidStack inputFluid, FluidStack outputFluid)
	{
		super(null, inputFluid, null, outputFluid);
		this.setMinTechLevel(0);
	}

	@Override
	public Boolean matches(ItemStack item, FluidStack fluid)
	{
		if(item.getItem() instanceof IFood)
		{
			if(fluid.isFluidEqual(barrelFluid) && ((IFood)item.getItem()).getFoodGroup() == EnumFoodGroup.Fruit && ((IFood)item.getItem()).getFoodWeight(item) > 1f*(fluid.amount/100))
			{
				return true;
			}
		}
		return false;
	}
}
