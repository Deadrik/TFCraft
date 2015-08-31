package com.bioxx.tfc.Core;

import net.minecraft.util.IIcon;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import com.bioxx.tfc.api.TFCBlocks;

public class FluidBaseTFC extends Fluid
{
	public FluidBaseTFC(String fluidName) {
		super(fluidName);
	}

	private int color = 0xffffff;

	public FluidBaseTFC setBaseColor(int c)
	{
		color = c;
		return this;
	}

	@Override
	public int getColor(FluidStack fs)
	{
		return color;
	}

	@Override
	public int getColor()
	{
		return color;
	}

	@Override
	public IIcon getStillIcon()
	{
		if(this.stillIcon == null)
			return TFCBlocks.hotWater.getIcon(0, 0);
		return this.stillIcon;
	}

	@Override
	public IIcon getFlowingIcon()
	{
		if(this.flowingIcon == null)
			return TFCBlocks.hotWater.getIcon(2, 0);
		return this.flowingIcon;
	}
}
