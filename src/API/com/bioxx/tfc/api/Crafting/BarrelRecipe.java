package com.bioxx.tfc.api.Crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class BarrelRecipe
{
	ItemStack inItemStack;
	FluidStack barrelFluid;
	ItemStack outItemStack;
	FluidStack outFluid;
	public int sealTime = 8;
	public boolean removesLiquid = true;
	boolean isSealedRecipe = true;
	public int minTechLevel = 1;
	public boolean allowAnyStack = true;

	public BarrelRecipe(ItemStack inputItem, FluidStack inputFluid, ItemStack outIS, FluidStack outputFluid)
	{
		this.inItemStack = inputItem;
		barrelFluid = inputFluid;
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

	public BarrelRecipe setAllowAnyStack(boolean b)
	{
		this.allowAnyStack = b;
		return this;
	}

	public BarrelRecipe setMinTechLevel(int t)
	{
		this.minTechLevel = t;
		return this;
	}

	public BarrelRecipe setSealedRecipe(boolean b)
	{
		this.isSealedRecipe = b;
		return this;
	}

	public Boolean matches(ItemStack item, FluidStack fluid)
	{
		boolean iStack = removesLiquid ? true : (inItemStack != null && item != null && fluid != null && barrelFluid != null && item.stackSize >= (int)Math.ceil(fluid.amount/barrelFluid.amount));
		boolean fStack = !removesLiquid ? true : (barrelFluid != null && item != null && fluid != null && outFluid != null && fluid.amount >= item.stackSize*outFluid.amount);

		boolean anyStack = !removesLiquid && !isSealedRecipe && this.outItemStack == null && allowAnyStack;
		boolean itemsEqual = OreDictionary.itemMatches(inItemStack, item, false);

		return ((inItemStack != null && itemsEqual && (iStack || anyStack)) || inItemStack == null) &&
				((barrelFluid != null && barrelFluid.isFluidEqual(fluid) && (fStack || anyStack)) || barrelFluid == null);
	}

	public Boolean isInFluid(FluidStack item)
	{
		return barrelFluid.isFluidEqual(item);
	}

	public ItemStack getInItem()
	{
		return inItemStack.copy();
	}

	public FluidStack getInFluid()
	{
		return barrelFluid.copy();
	}

	public String getRecipeName()
	{
		String s = "";
		if(this.outItemStack != null)
			s=/*outItemStack.stackSize+"x " +*/ outItemStack.getDisplayName();
		if(!this.barrelFluid.isFluidEqual(outFluid))
			s=outFluid.getFluid().getLocalizedName();
		return s;
	}

	public boolean isSealedRecipe()
	{
		return this.isSealedRecipe;
	}

	protected int getnumberOfRuns(ItemStack inIS, FluidStack inFS)
	{
		int runs = 0;
		runs = inIS.stackSize/this.inItemStack.stackSize;
		return Math.min(runs, inFS.amount/this.getInFluid().amount);
	}

	public ItemStack getResult(ItemStack inIS, FluidStack inFS, int sealedTime)
	{
		ItemStack is = null;
		if(outItemStack != null)
		{
			is = outItemStack.copy();
			is.stackSize*= this.getnumberOfRuns(inIS, inFS);
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
			else
			{
				fs.amount*=outItemStack.stackSize;
			}
			return fs;
		}
		return null;
	}
}
