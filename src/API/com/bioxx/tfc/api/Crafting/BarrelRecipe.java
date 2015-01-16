package com.bioxx.tfc.api.Crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class BarrelRecipe
{
	ItemStack recipeIS;
	FluidStack recipeFluid;
	ItemStack recipeOutIS;
	FluidStack recipeOutFluid;
	public int sealTime = 8;
	public boolean removesLiquid = true;
	boolean isSealedRecipe = true;
	public int minTechLevel = 1;
	public boolean allowAnyStack = true;

	public BarrelRecipe(ItemStack inputItem, FluidStack inputFluid, ItemStack outIS, FluidStack outputFluid)
	{
		this.recipeIS = inputItem;
		recipeFluid = inputFluid;
		this.recipeOutIS = outIS;
		recipeOutFluid = outputFluid;
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
		boolean iStack = removesLiquid ? true : (recipeIS != null && item != null && fluid != null && recipeFluid != null && item.stackSize >= (int)Math.ceil(fluid.amount/recipeFluid.amount));
		boolean fStack = !removesLiquid ? true : (recipeFluid != null && item != null && fluid != null && recipeOutFluid != null && fluid.amount >= item.stackSize*recipeOutFluid.amount);

		boolean anyStack = !removesLiquid && !isSealedRecipe && this.recipeOutIS == null && allowAnyStack;
		boolean itemsEqual = (item == null && recipeIS == null) || OreDictionary.itemMatches(recipeIS, item, false);

		return ((recipeIS != null && itemsEqual && (iStack || anyStack)) || recipeIS == null) &&
				((recipeFluid != null && recipeFluid.isFluidEqual(fluid) && (fStack || anyStack)) || recipeFluid == null);
	}

	public Boolean isInFluid(FluidStack item)
	{
		return recipeFluid.isFluidEqual(item);
	}

	public ItemStack getInItem()
	{
		return recipeIS;
	}

	public FluidStack getInFluid()
	{
		return recipeFluid;
	}

	public ItemStack getRecipeOutIS()
	{
		return recipeOutIS;
	}

	public FluidStack getRecipeOutFluid()
	{
		return recipeOutFluid;
	}

	public int getSealTime()
	{
		return sealTime;
	}

	public boolean isRemovesLiquid()
	{
		return removesLiquid;
	}

	public int getMinTechLevel()
	{
		return minTechLevel;
	}

	public boolean isAllowAnyStack()
	{
		return allowAnyStack;
	}

	public String getRecipeName()
	{
		String s = "";
		if(this.recipeOutIS != null)
		{
			if(recipeOutIS.stackSize > 1)
				s += recipeOutIS.stackSize+"x ";
			s += recipeOutIS.getDisplayName();
		}
		if(recipeOutFluid != null && !this.recipeFluid.isFluidEqual(recipeOutFluid))
			s = recipeOutFluid.getFluid().getLocalizedName(recipeOutFluid);
		return s;
	}

	public boolean isSealedRecipe()
	{
		return this.isSealedRecipe;
	}

	protected int getnumberOfRuns(ItemStack inIS, FluidStack inFS)
	{
		int runs = 0;
		int div = 0;
		if(inIS != null && recipeIS != null)
		{
			runs = inIS.stackSize/this.recipeIS.stackSize;
			div = inFS.amount/this.getInFluid().amount;
		}
		return Math.min(runs, div);
	}

	public ItemStack getResult(ItemStack inIS, FluidStack inFS, int sealedTime)
	{
		ItemStack is = null;
		if(recipeOutIS != null)
		{
			is = recipeOutIS.copy();
			is.stackSize*= this.getnumberOfRuns(inIS, inFS);
			return is;
		}
		if(!removesLiquid && inIS != null)
		{
			is = inIS;
			is.stackSize -= inFS.amount/this.recipeOutFluid.amount;
		}
		return is;
	}

	public FluidStack getResultFluid(ItemStack inIS, FluidStack inFS, int sealedTime)
	{
		if(recipeOutFluid != null)
		{
			FluidStack fs = recipeOutFluid.copy();
			if(!removesLiquid && fs != null)
			{
				fs.amount = inFS.amount;
			}
			else if(fs != null && recipeOutIS != null)
			{
				fs.amount*=recipeOutIS.stackSize;
			}
			return fs;
		}
		return null;
	}
}
