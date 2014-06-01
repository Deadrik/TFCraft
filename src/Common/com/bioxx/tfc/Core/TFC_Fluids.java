package com.bioxx.tfc.Core;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;

import com.bioxx.tfc.TFCItems;

public class TFC_Fluids
{
	public static final Fluid SALTWATER = new Fluid("saltwater")
	{
		@Override
		public String getLocalizedName()
		{
			return StatCollector.translateToLocal("tile.Salt Water.name");
		}
	};

	public static final Fluid FRESHWATER = new Fluid("freshwater")
	{
		@Override
		public String getLocalizedName()
		{
			return StatCollector.translateToLocal("tile.Fresh Water.name");
		}
	};

	public static final Fluid HOTWATER = new Fluid("hotwater")
	{
		@Override
		public String getLocalizedName()
		{
			return StatCollector.translateToLocal("tile.Hot Water.name");
		}
	}.setTemperature(372/*Kelvin*/);

	public static final Fluid LAVA = new Fluid("lavatfc") {
		@Override
		public String getLocalizedName() {
			return StatCollector.translateToLocal("tile.lava.name");
		}
	}.setLuminosity(15).setDensity(3000).setViscosity(6000).setTemperature(1300).setUnlocalizedName(Blocks.lava.getUnlocalizedName());

	public static final Fluid RUM = new Fluid("rum");

	public static void register()
	{
		FluidRegistry.registerFluid(LAVA);
		FluidRegistry.registerFluid(SALTWATER);
		FluidRegistry.registerFluid(FRESHWATER);
		FluidRegistry.registerFluid(HOTWATER);
		FluidRegistry.registerFluid(RUM);
	}

	public static void registerFluidContainers()
	{
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluid(LAVA.getName()), new ItemStack(TFCItems.BlueSteelBucketLava), new ItemStack(TFCItems.BlueSteelBucketEmpty));
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluid(FRESHWATER.getName()), new ItemStack(TFCItems.RedSteelBucketWater), new ItemStack(TFCItems.RedSteelBucketEmpty));
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluid(SALTWATER.getName()), new ItemStack(TFCItems.RedSteelBucketSaltWater), new ItemStack(TFCItems.RedSteelBucketEmpty));
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluid(FRESHWATER.getName()), new ItemStack(TFCItems.WoodenBucketWater), new ItemStack(TFCItems.WoodenBucketEmpty));
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluid(SALTWATER.getName()), new ItemStack(TFCItems.WoodenBucketSaltWater), new ItemStack(TFCItems.WoodenBucketEmpty));
	}
}
