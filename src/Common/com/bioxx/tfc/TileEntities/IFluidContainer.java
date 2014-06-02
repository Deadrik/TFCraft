package com.bioxx.tfc.TileEntities;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public interface IFluidContainer {

	public int getMaxFluid();
	public FluidStack getFluid(ItemStack is);
}
