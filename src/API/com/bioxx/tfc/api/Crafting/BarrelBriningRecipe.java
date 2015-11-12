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
			float w = Food.getWeight(item);
			if (fluid.isFluidEqual(recipeFluid) && w <= 1f * (fluid.amount / this.recipeFluid.amount))
			{
				return true;
			}
		}
		return false;
	}
}
