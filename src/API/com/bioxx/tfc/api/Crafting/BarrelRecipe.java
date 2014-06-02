package com.bioxx.tfc.api.Crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class BarrelRecipe
{
	ItemStack inItemStack;
	FluidStack inFluid;
	ItemStack outItemStack;
	FluidStack outFluid;
	int sealTime = 8;

	public BarrelRecipe(ItemStack inputItem, FluidStack inputFluid, ItemStack outIS, FluidStack outputFluid)
	{
		this.inItemStack = inputItem;
		inFluid = inputFluid;
		this.outItemStack = outIS;
		outFluid = outputFluid;
	}

	public BarrelRecipe(ItemStack inputItem, FluidStack inputFluid, ItemStack outIS, FluidStack outputFluid, int seal)
	{
		this(outIS, outputFluid, outIS, outputFluid);
		this.sealTime = seal;
	}

	public Boolean matches(ItemStack item, FluidStack fluid)
	{
		return ((inItemStack != null && inItemStack.isItemEqual(item)) || inItemStack == null) && ((inFluid != null && inFluid.isFluidEqual(fluid)) || inFluid == null);
	}

	public Boolean isInFluid(FluidStack item)
	{
		return inFluid.isFluidEqual(item);
	}

	public ItemStack getInItem()
	{
		return inItemStack;
	}

	public FluidStack getInFluid()
	{
		return inFluid;
	}

	public ItemStack getResult(ItemStack inIS, FluidStack inFS, int sealedTime)
	{
		return outItemStack;
	}

	public FluidStack getResultFluid(ItemStack inIS, FluidStack inFS, int sealedTime)
	{
		return outFluid;
	}
}
