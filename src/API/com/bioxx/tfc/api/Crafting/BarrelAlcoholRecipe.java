package com.bioxx.tfc.api.Crafting;

import java.util.Stack;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.api.Food;

public class BarrelAlcoholRecipe extends BarrelRecipe
{

	public BarrelAlcoholRecipe(ItemStack inputItem, FluidStack inputFluid,
			ItemStack outIS, FluidStack outputFluid) {
		super(inputItem, inputFluid, outIS, outputFluid);
		this.sealTime = 72;
	}

	@Override
	public Stack<ItemStack> getResult(ItemStack inIS, FluidStack inFS, int sealedTime)
	{
		Stack<ItemStack> result = new Stack<ItemStack>();
		result.push(recipeOutIS);
		return result;
	}

	@Override
	public FluidStack getResultFluid(ItemStack inIS, FluidStack inFS, int sealedTime)
	{
		float amt = inFS.amount/10000f;
		FluidStack out = recipeOutFluid.copy();
		if(out.tag == null)
			out.tag = new NBTTagCompound();
		float weight = Food.getWeight(inIS);
		out.tag.setFloat("potency", (weight/Food.getWeight(recipeIS))/amt);
		return recipeOutFluid;
	}

	@Override
	public Boolean matches(ItemStack itemstack, FluidStack inFluid)
	{
		if(recipeIS.hasTagCompound())
		{
			if(itemstack == null || !itemstack.hasTagCompound())
			{
				return false;
			}
			if(recipeIS.getItem() instanceof ItemFoodTFC)
			{
				if(!(itemstack.getItem() instanceof ItemFoodTFC))
				{
					return false;
				}
				float recipeWeight = Food.getWeight(recipeIS);
				float itemstackWeight = Food.getWeight(itemstack);
				float percent = itemstackWeight/(recipeWeight * ((float)inFluid.amount/(float)recipeFluid.amount));
				if (percent < 0.25f || percent > 0.75f)
					return false;
			}
		}
		return OreDictionary.itemMatches(recipeIS, itemstack, false) && inFluid.isFluidEqual(recipeFluid);
	}
}
