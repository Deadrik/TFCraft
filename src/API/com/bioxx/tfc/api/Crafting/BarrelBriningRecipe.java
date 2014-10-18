package com.bioxx.tfc.api.Crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.Interfaces.IFood;

public class BarrelBriningRecipe extends BarrelRecipe
{

	public BarrelBriningRecipe(FluidStack inputFluid, FluidStack outputFluid)
	{
		super(null, inputFluid, null, outputFluid, 4);
	}

	@Override
	public Boolean matches(ItemStack item, FluidStack fluid)
	{
		if(item != null && item.getItem() instanceof IFood && !Food.isBrined(item))
		{
			float w = (float)((IFood)item.getItem()).getFoodWeight(item);
			if(fluid.isFluidEqual(barrelFluid) && (w <= 1f*(fluid.amount/this.barrelFluid.amount)))
			{
				return true;
			}
		}
		return false;
	}
}
