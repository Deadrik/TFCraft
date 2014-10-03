package com.bioxx.tfc.api.Crafting;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

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
	public ItemStack getResult(ItemStack inIS, FluidStack inFS, int sealedTime)
	{
		return outItemStack;
	}

	@Override
	public FluidStack getResultFluid(ItemStack inIS, FluidStack inFS, int sealedTime)
	{
		float amt = inFS.amount/10000f;
		FluidStack out = outFluid.copy();
		if(out.tag == null)
			out.tag = new NBTTagCompound();
		float weight = Food.getWeight(inIS);
		out.tag.setFloat("potency", (weight/Food.getWeight(inItemStack))/amt);
		return outFluid;
	}

	@Override
	public Boolean matches(ItemStack item, FluidStack fluid)
	{
		if(inItemStack.hasTagCompound())
		{
			if(!item.hasTagCompound())
			{
				return false;
			}
			if(inItemStack.getItem() instanceof ItemFoodTFC)
			{
				if(!(item.getItem() instanceof ItemFoodTFC))
				{
					return false;
				}
				float inW = inItemStack.getTagCompound().getFloat("foodWeight");
				float itW = item.getTagCompound().getFloat("foodWeight");
				float percent = itW/(inW * ((float)fluid.amount/(float)barrelFluid.amount));
				if(percent < 0.25f)
					return false;
			}
		}
		return super.matches(item, fluid);
	}
}
