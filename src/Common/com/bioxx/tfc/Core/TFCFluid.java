package com.bioxx.tfc.Core;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.TFCItems;

public class TFCFluid extends Fluid
{
	public TFCFluid(String fluidName) {
		super(fluidName);
	}

	public static final TFCFluid SALTWATER = new TFCFluid("saltwater").setBaseColor(0x354d35);
	public static final TFCFluid FRESHWATER = new TFCFluid("freshwater").setBaseColor(0x354d35);
	public static final TFCFluid HOTWATER = (TFCFluid) new TFCFluid("hotwater").setTemperature(372/*Kelvin*/);
	public static final TFCFluid LAVA = (TFCFluid) new TFCFluid("lavatfc").setLuminosity(15).setDensity(3000).setViscosity(6000).setTemperature(1300).setUnlocalizedName(Blocks.lava.getUnlocalizedName());
	public static final TFCFluid RUM = new TFCFluid("rum").setBaseColor(0x6e0123);
	public static final TFCFluid BEER = new TFCFluid("beer").setBaseColor(0xc39e37);
	public static final TFCFluid RYEWHISKEY = new TFCFluid("ryewhiskey").setBaseColor(0xc77d51);
	public static final TFCFluid WHISKEY = new TFCFluid("whiskey").setBaseColor(0x583719);
	public static final TFCFluid SAKE = new TFCFluid("sake").setBaseColor(0xb7d9bc);
	public static final TFCFluid VODKA = new TFCFluid("vodka").setBaseColor(0xdcdcdc);
	public static final TFCFluid CIDER = new TFCFluid("cider").setBaseColor(0xb0ae32);
	public static final TFCFluid TANNIN = new TFCFluid("tannin").setBaseColor(0x63594e);
	public static final TFCFluid VINEGAR = new TFCFluid("vinegar").setBaseColor(0xc7c2aa);
	public static final TFCFluid BRINE = new TFCFluid("brine").setBaseColor(0xc7c2aa);
	public static final TFCFluid LIMEWATER = new TFCFluid("limewater").setBaseColor(0xb4b4b4);
	public static final TFCFluid MILK = new TFCFluid("milk").setBaseColor(0xffffff);
	public static final TFCFluid MILKCURDLED = new TFCFluid("milkcurdled").setBaseColor(0xfffbe8);

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


	public static void register()
	{
		FluidRegistry.registerFluid(LAVA);
		FluidRegistry.registerFluid(SALTWATER);
		FluidRegistry.registerFluid(FRESHWATER);
		FluidRegistry.registerFluid(HOTWATER);
		FluidRegistry.registerFluid(RUM);
		FluidRegistry.registerFluid(BEER);
		FluidRegistry.registerFluid(RYEWHISKEY);
		FluidRegistry.registerFluid(WHISKEY);
		FluidRegistry.registerFluid(SAKE);
		FluidRegistry.registerFluid(VODKA);
		FluidRegistry.registerFluid(CIDER);
		FluidRegistry.registerFluid(TANNIN);
		FluidRegistry.registerFluid(VINEGAR);
		FluidRegistry.registerFluid(BRINE);
		FluidRegistry.registerFluid(LIMEWATER);
		FluidRegistry.registerFluid(MILK);
		FluidRegistry.registerFluid(MILKCURDLED);
	}

	public static void registerFluidContainers()
	{
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluid(LAVA.getName()), new ItemStack(TFCItems.BlueSteelBucketLava), new ItemStack(TFCItems.BlueSteelBucketEmpty));
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluid(FRESHWATER.getName()), new ItemStack(TFCItems.RedSteelBucketWater), new ItemStack(TFCItems.RedSteelBucketEmpty));
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluid(SALTWATER.getName()), new ItemStack(TFCItems.RedSteelBucketSaltWater), new ItemStack(TFCItems.RedSteelBucketEmpty));
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluid(FRESHWATER.getName()), new ItemStack(TFCItems.WoodenBucketWater), new ItemStack(TFCItems.WoodenBucketEmpty));
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluid(SALTWATER.getName()), new ItemStack(TFCItems.WoodenBucketSaltWater), new ItemStack(TFCItems.WoodenBucketEmpty));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(RUM, 250), new ItemStack(TFCItems.Rum), new ItemStack(Items.glass_bottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(BEER, 250), new ItemStack(TFCItems.Beer), new ItemStack(Items.glass_bottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(RYEWHISKEY, 250), new ItemStack(TFCItems.RyeWhiskey), new ItemStack(Items.glass_bottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(WHISKEY, 250), new ItemStack(TFCItems.Whiskey), new ItemStack(Items.glass_bottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(SAKE, 250), new ItemStack(TFCItems.Sake), new ItemStack(Items.glass_bottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(CIDER, 250), new ItemStack(TFCItems.Cider), new ItemStack(Items.glass_bottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(VODKA, 250), new ItemStack(TFCItems.Vodka), new ItemStack(Items.glass_bottle));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(MILK, 1000), new ItemStack(TFCItems.WoodenBucketMilk), new ItemStack(TFCItems.WoodenBucketEmpty));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(VINEGAR, 1000), new ItemStack(TFCItems.Vinegar), new ItemStack(TFCItems.WoodenBucketEmpty));
	}
}
