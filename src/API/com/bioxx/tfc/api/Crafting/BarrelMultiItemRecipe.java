package com.bioxx.tfc.api.Crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.Interfaces.IFood;

public class BarrelMultiItemRecipe extends BarrelRecipe
{
	public BarrelMultiItemRecipe(ItemStack inputItem, FluidStack inputFluid,
			ItemStack outIS, FluidStack outputFluid) {
		super(inputItem, inputFluid, outIS, outputFluid);
	}

	@Override
	public ItemStack getResult(ItemStack inIS, FluidStack inFS, int sealedTime)
	{
		ItemStack out = outItemStack.copy();
		if(inIS != null && inIS.getItem() instanceof IFood)
		{
			int w = (int)Math.floor(Food.getWeight(inIS));
			if(w * outFluid.amount <= inFS.amount)
			{
				Food.setWeight(out, w*Food.getWeight(outItemStack));
			}
		}
		else
		{
			out.stackSize = inIS.stackSize;
		}
		return out;
	}

	@Override
	public FluidStack getResultFluid(ItemStack inIS, FluidStack inFS, int sealedTime)
	{
		FluidStack fs = outFluid.copy(); 

		if(inIS != null && inIS.getItem() instanceof IFood)
		{
			int w = (int)Math.floor(Food.getWeight(inIS));
			if(w * outFluid.amount <= inFS.amount)
			{
				fs.amount = (w * outFluid.amount);
			}
		}
		else
		{
			fs.amount *= inIS.stackSize;
		}
		return fs;
	}
}
