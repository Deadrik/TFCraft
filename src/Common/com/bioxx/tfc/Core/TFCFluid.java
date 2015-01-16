package com.bioxx.tfc.Core;

import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import com.bioxx.tfc.api.TFCBlocks;

public class TFCFluid extends Fluid
{
	public TFCFluid(String fluidName) {
		super(fluidName);
	}

	public static Fluid SALTWATER;
	public static Fluid FRESHWATER;
	public static Fluid HOTWATER;
	public static Fluid LAVA;
	public static Fluid RUM;
	public static Fluid BEER;
	public static Fluid RYEWHISKEY;
	public static Fluid WHISKEY;
	public static Fluid CORNWHISKEY;
	public static Fluid SAKE;
	public static Fluid VODKA;
	public static Fluid CIDER;
	public static Fluid TANNIN;
	public static Fluid VINEGAR;
	public static Fluid BRINE;
	public static Fluid LIMEWATER;
	public static Fluid MILK;
	public static Fluid MILKCURDLED;
	public static Fluid MILKVINEGAR;
	public static Fluid OLIVEOIL;

	private int color = 0xffffff;

	public TFCFluid setBaseColor(int c)
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
			return TFCBlocks.HotWater.getIcon(0, 0);
		return this.stillIcon;
	}

	@Override
	public IIcon getFlowingIcon()
	{
		if(this.flowingIcon == null)
			return TFCBlocks.HotWater.getIcon(2, 0);
		return this.flowingIcon;
	}
}
