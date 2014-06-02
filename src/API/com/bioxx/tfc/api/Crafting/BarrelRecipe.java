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
	boolean removesLiquid = true;

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

	public BarrelRecipe setRemovesLiquid(boolean b)
	{
		this.removesLiquid = b;
		return this;
	}

	public Boolean matches(ItemStack item, FluidStack fluid)
	{
		boolean iStack = removesLiquid ? true : (inItemStack != null && item.stackSize >= (int)Math.ceil(fluid.amount/inFluid.amount));
		boolean fStack = removesLiquid ? true : (inFluid != null && fluid.amount >= item.stackSize*inFluid.amount);

		return ((inItemStack != null && inItemStack.isItemEqual(item) && iStack) || inItemStack == null) && 
				((inFluid != null && inFluid.isFluidEqual(fluid) && fStack) || inFluid == null);
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
		if(outItemStack != null)
		{
			ItemStack is = outItemStack.copy();
			return is;
		}
		return null;
	}

	public FluidStack getResultFluid(ItemStack inIS, FluidStack inFS, int sealedTime)
	{
		if(outFluid != null)
		{
			FluidStack fs = outFluid.copy();
			if(!removesLiquid)
			{
				fs.amount = inFS.amount;
			}
			return fs;
		}
		return null;
	}
}
