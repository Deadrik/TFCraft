package com.bioxx.tfc.api.Crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class BarrelRecipe
{
	ItemStack inItemStack;
	FluidStack inFluid;
	ItemStack outItemStack;
	FluidStack outFluid;
	public int sealTime = 8;
	public boolean removesLiquid = true;
	boolean isSealedRecipe = true;

	public BarrelRecipe(ItemStack inputItem, FluidStack inputFluid, ItemStack outIS, FluidStack outputFluid)
	{
		this.inItemStack = inputItem;
		inFluid = inputFluid;
		this.outItemStack = outIS;
		outFluid = outputFluid;
	}

	public BarrelRecipe(ItemStack inputItem, FluidStack inputFluid, ItemStack outIS, FluidStack outputFluid, int seal)
	{
		this(inputItem, inputFluid, outIS, outputFluid);
		this.sealTime = seal;
	}

	public BarrelRecipe setRemovesLiquid(boolean b)
	{
		this.removesLiquid = b;
		return this;
	}

	public BarrelRecipe setSealedRecipe(boolean b)
	{
		this.isSealedRecipe = b;
		return this;
	}

	public Boolean matches(ItemStack item, FluidStack fluid)
	{
		boolean iStack = removesLiquid ? true : (inItemStack != null && item != null && fluid != null && inFluid != null && item.stackSize >= (int)Math.ceil(fluid.amount/inFluid.amount));
		boolean fStack = !removesLiquid ? true : (inFluid != null && item != null && fluid != null && outFluid != null && fluid.amount >= item.stackSize*outFluid.amount);

		boolean anyStack = !removesLiquid && !isSealedRecipe && this.outItemStack == null;

		return ((inItemStack != null && inItemStack.isItemEqual(item) && (iStack || anyStack)) || inItemStack == null) && 
				((inFluid != null && inFluid.isFluidEqual(fluid) && (fStack || anyStack)) || inFluid == null);
	}

	public Boolean isInFluid(FluidStack item)
	{
		return inFluid.isFluidEqual(item);
	}

	public ItemStack getInItem()
	{
		return inItemStack.copy();
	}

	public FluidStack getInFluid()
	{
		return inFluid.copy();
	}

	public String getRecipeName()
	{
		String s = "";
		if(this.outItemStack != null)
			s=/*outItemStack.stackSize+"x " +*/ outItemStack.getDisplayName();
		if(!this.inFluid.isFluidEqual(outFluid))
			s=outFluid.getFluid().getLocalizedName();
		return s;
	}

	public boolean isSealedRecipe()
	{
		return this.isSealedRecipe;
	}

	public ItemStack getResult(ItemStack inIS, FluidStack inFS, int sealedTime)
	{
		ItemStack is = null;
		if(outItemStack != null)
		{
			is = outItemStack.copy();
			return is;
		}
		if(!removesLiquid)
		{
			is = inIS;
			is.stackSize -= inFS.amount/this.outFluid.amount;
		}
		return is;
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
