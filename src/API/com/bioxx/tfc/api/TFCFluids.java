package com.bioxx.tfc.api;

import net.minecraft.init.Blocks;

import net.minecraftforge.fluids.Fluid;

import com.bioxx.tfc.Core.FluidBaseTFC;

public class TFCFluids
{
	public static final Fluid SALTWATER = new FluidBaseTFC("saltwater").setBaseColor(0x354d35);
	public static final Fluid FRESHWATER = new FluidBaseTFC("freshwater").setBaseColor(0x354d35);
	public static final Fluid HOTWATER = new FluidBaseTFC("hotwater").setBaseColor(0x1f5099).setTemperature(350/*Kelvin, Rough temp of spring in Aachen, Germany */);
	public static final Fluid LAVA = new FluidBaseTFC("lavatfc").setLuminosity(15).setDensity(3000).setViscosity(6000).setTemperature(1300).setUnlocalizedName(Blocks.lava.getUnlocalizedName());
	public static final Fluid RUM = new FluidBaseTFC("rum").setBaseColor(0x6e0123);
	public static final Fluid BEER = new FluidBaseTFC("beer").setBaseColor(0xc39e37);
	public static final Fluid RYEWHISKEY = new FluidBaseTFC("ryewhiskey").setBaseColor(0xc77d51);
	public static final Fluid WHISKEY = new FluidBaseTFC("whiskey").setBaseColor(0x583719);
	public static final Fluid CORNWHISKEY = new FluidBaseTFC("cornwhiskey").setBaseColor(0xd9c7b7);
	public static final Fluid SAKE = new FluidBaseTFC("sake").setBaseColor(0xb7d9bc);
	public static final Fluid VODKA = new FluidBaseTFC("vodka").setBaseColor(0xdcdcdc);
	public static final Fluid CIDER = new FluidBaseTFC("cider").setBaseColor(0xb0ae32);
	public static final Fluid TANNIN = new FluidBaseTFC("tannin").setBaseColor(0x63594e);
	public static final Fluid VINEGAR = new FluidBaseTFC("vinegar").setBaseColor(0xc7c2aa);
	public static final Fluid BRINE = new FluidBaseTFC("brine").setBaseColor(0xdcd3c9);
	public static final Fluid LIMEWATER = new FluidBaseTFC("limewater").setBaseColor(0xb4b4b4);
	public static final Fluid MILK = new FluidBaseTFC("milk").setBaseColor(0xffffff);
	public static final Fluid MILKCURDLED = new FluidBaseTFC("milkcurdled").setBaseColor(0xfffbe8);
	public static final Fluid MILKVINEGAR = new FluidBaseTFC("milkvinegar").setBaseColor(0xfffbe8);
	public static final Fluid OLIVEOIL = new FluidBaseTFC("oliveoil").setBaseColor(0x6a7537);

}
