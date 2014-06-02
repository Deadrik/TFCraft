package com.bioxx.tfc.api.Crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

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
		out.stackSize = inIS.stackSize;
		return out;
	}

	@Override
	public FluidStack getResultFluid(ItemStack inIS, FluidStack inFS, int sealedTime)
	{
		FluidStack fs = outFluid.copy(); 
		fs.amount *= inIS.stackSize;
		return fs;
	}
}
