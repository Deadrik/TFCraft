package TFC.Blocks.Terrain;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.TerraFirmaCraft;

public class TFC_Fluids
{
	public static final Fluid SALTWATER = new Fluid("saltwater")
	{
		@Override
		public String getLocalizedName()
		{
			return StatCollector.translateToLocal("tile.Salt Water.name");
		}
	}.setBlock(TFCBlocks.SaltWaterStill).setUnlocalizedName(TFCBlocks.SaltWaterStill.getUnlocalizedName());
	
	public static final Fluid FRESHWATER = new Fluid("freshwater")
	{
		@Override
		public String getLocalizedName()
		{
			return StatCollector.translateToLocal("tile.Fresh Water.name");
		}
	}.setBlock(TFCBlocks.FreshWaterStill).setUnlocalizedName(TFCBlocks.FreshWaterStill.getUnlocalizedName());

	public static final Fluid HOTWATER = new Fluid("hotwater")
	{
		@Override
		public String getLocalizedName()
		{
			return StatCollector.translateToLocal("tile.Hot Water.name");
		}
	}.setBlock(TFCBlocks.HotWaterStill).setUnlocalizedName(TFCBlocks.HotWaterStill.getUnlocalizedName()).setTemperature(372/*Kelvin*/);

	public static void register()
	{
		FluidRegistry.registerFluid(SALTWATER);
		FluidRegistry.registerFluid(FRESHWATER);
		FluidRegistry.registerFluid(HOTWATER);
	}

	public static void registerFluidContainers()
	{
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.LAVA, new ItemStack(TFCItems.BlueSteelBucketLava), new ItemStack(TFCItems.BlueSteelBucketEmpty));
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluid(FRESHWATER.getName()), new ItemStack(TFCItems.RedSteelBucketWater), new ItemStack(TFCItems.RedSteelBucketEmpty));
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluid(SALTWATER.getName()), new ItemStack(TFCItems.RedSteelBucketSaltWater), new ItemStack(TFCItems.RedSteelBucketEmpty));
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluid(FRESHWATER.getName()), new ItemStack(TFCItems.WoodenBucketWater), new ItemStack(TFCItems.WoodenBucketEmpty));
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluid(SALTWATER.getName()), new ItemStack(TFCItems.WoodenBucketSaltWater), new ItemStack(TFCItems.WoodenBucketEmpty));
	}

	public static void registerFluidIcons()
	{
		TerraFirmaCraft.proxy.registerFluidIcons(SALTWATER, TFCBlocks.SaltWaterStill);
		TerraFirmaCraft.proxy.registerFluidIcons(FRESHWATER, TFCBlocks.FreshWaterStill);
		TerraFirmaCraft.proxy.registerFluidIcons(HOTWATER, TFCBlocks.HotWaterStill);
	}
}
