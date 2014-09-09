package com.bioxx.tfc.api.Crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import com.bioxx.tfc.Food.Food;
import com.bioxx.tfc.api.Interfaces.IFood;

public class BarrelBriningRecipe extends BarrelRecipe
{

	public BarrelBriningRecipe(FluidStack inputFluid, FluidStack outputFluid)
	{
		super(null, inputFluid, null, outputFluid, 24);
	}

	@Override
	public Boolean matches(ItemStack item, FluidStack fluid)
	{
		if(item != null && item.getItem() instanceof IFood && !Food.isBrined(item))
		{
			if(fluid.isFluidEqual(barrelFluid) && ((IFood)item.getItem()).getFoodWeight(item) > 1f*(fluid.amount/100))
			{
				return true;
			}
		}
		return false;
	}
}
