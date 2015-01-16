package com.bioxx.tfc.GUI.WAILA;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.TileEntities.TEBarrel;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.TFCFluids;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Crafting.BarrelBriningRecipe;
import com.bioxx.tfc.api.Enums.EnumFoodGroup;
import com.bioxx.tfc.api.Interfaces.IFood;

public class TFCBarrel implements IWailaDataProvider
{
	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		return null;
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		String head = currenttip.get(0);
		if (accessor.getTileEntity() instanceof TEBarrel)
		{
			TEBarrel te = (TEBarrel) accessor.getTileEntity();

			if (te.getFluidStack() != null)
			{
				head += " (" + te.fluid.getFluid().getLocalizedName(te.getFluidStack()) + ")";
				currenttip.set(0, head);
			}
		}
		return currenttip;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		if (accessor.getTileEntity() instanceof TEBarrel)
		{
			TEBarrel te = (TEBarrel) accessor.getTileEntity();
			ItemStack inStack = te.getStackInSlot(0);
			NBTTagCompound tag = accessor.getNBTData();
			int sealTime = accessor.getNBTInteger(tag, "SealTime");

			if (te.getFluidStack() != null)
			{
				currenttip.add(String.valueOf(te.getFluidLevel()) + "/" + String.valueOf(te.getMaxLiquid()) + " mB");
			}
			if (te.getSealed() && sealTime != 0)
			{
				currenttip.add(StatCollector.translateToLocal("gui.Barrel.SealedOn") + " : " + TFC_Time.getDateStringFromHours(sealTime));
			}

			if (te.recipe != null)
			{
				if (!(te.recipe instanceof BarrelBriningRecipe))
				{
					currenttip.add(StatCollector.translateToLocal("gui.Output") + " : " + te.recipe.getRecipeName());
				}
				else if (te.getSealed() && te.getFluidStack() != null && te.getFluidStack().getFluid() == TFCFluids.BRINE)
				{
					if (inStack != null && inStack.getItem() instanceof IFood && (((IFood) inStack.getItem()).getFoodGroup() == EnumFoodGroup.Fruit ||
							((IFood) inStack.getItem()).getFoodGroup() == EnumFoodGroup.Vegetable || ((IFood) inStack.getItem()).getFoodGroup() == EnumFoodGroup.Protein ||
							((IFood) inStack.getItem()) == TFCItems.Cheese) && !Food.isBrined(inStack))
					{
						currenttip.add(StatCollector.translateToLocal("gui.barrel.brining"));
					}
				}
			}
			else if (te.recipe == null && te.getSealed() && te.getFluidStack() != null && inStack != null && inStack.getItem() instanceof IFood &&
					te.getFluidStack().getFluid() == TFCFluids.VINEGAR)
			{
				if (!Food.isPickled(inStack) && Food.getWeight(inStack) / te.getFluidStack().amount <= Global.FOOD_MAX_WEIGHT / te.getMaxLiquid())
				{
					if ((((IFood) inStack.getItem()).getFoodGroup() == EnumFoodGroup.Fruit || ((IFood) inStack.getItem()).getFoodGroup() == EnumFoodGroup.Vegetable ||
							((IFood) inStack.getItem()).getFoodGroup() == EnumFoodGroup.Protein || ((IFood) inStack.getItem()) == TFCItems.Cheese) &&
							Food.isBrined(inStack))
					{
						currenttip.add(StatCollector.translateToLocal("gui.barrel.pickling"));
					}
				}
				else if (Food.isPickled(inStack) && Food.getWeight(inStack) / te.getFluidStack().amount <= Global.FOOD_MAX_WEIGHT / te.getMaxLiquid() * 2)
				{
					currenttip.add(StatCollector.translateToLocal("gui.barrel.preserving"));
				}
			}
		}
		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		return currenttip;
	}

	public static void callbackRegister(IWailaRegistrar reg)
	{
		reg.registerHeadProvider(new TFCBarrel(), TEBarrel.class);
		reg.registerBodyProvider(new TFCBarrel(), TEBarrel.class);
	}
}