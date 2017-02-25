package com.bioxx.tfc.api.Crafting;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.FluidStack;

import com.bioxx.tfc.api.Food;
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
	public Boolean matches(ItemStack itemstack, FluidStack inFluid)
	{
		if(itemstack != null && itemstack.getItem() instanceof IFood)
		{
			if (!((Food.getDecay(itemstack) / Food.getWeight(itemstack) * 100) > 5.0F) && (inFluid.isFluidEqual(recipeFluid) &&
					((IFood) itemstack.getItem()).getFoodGroup() == EnumFoodGroup.Fruit &&
					((Food.getWeight(itemstack) >= 1F * (inFluid.amount / 100)) &&
					Food.getWeight(itemstack) <= 1.0501F * (inFluid.amount / 100))))
			{
				return true;
			}

		}
		return false;
	}
}
