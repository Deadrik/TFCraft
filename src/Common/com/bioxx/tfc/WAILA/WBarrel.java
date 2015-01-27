package com.bioxx.tfc.WAILA;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.TileEntities.TEBarrel;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.TFCFluids;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Crafting.BarrelBriningRecipe;
import com.bioxx.tfc.api.Crafting.BarrelManager;
import com.bioxx.tfc.api.Crafting.BarrelRecipe;
import com.bioxx.tfc.api.Enums.EnumFoodGroup;
import com.bioxx.tfc.api.Interfaces.IFood;

public class WBarrel implements IWailaDataProvider
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
			NBTTagCompound tag = accessor.getNBTData();
			FluidStack fluid = FluidStack.loadFluidStackFromNBT(tag.getCompoundTag("fluidNBT"));

			if (fluid != null)
			{
				head += " (" + fluid.getLocalizedName() + ")";
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
			ItemStack inStack = null;

			NBTTagCompound tag = accessor.getNBTData();

			NBTTagList nbttaglist = tag.getTagList("Items", 10);
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(0);
			byte byte0 = nbttagcompound1.getByte("Slot");

			if (byte0 >= 0 && byte0 < te.storage.length)
				inStack = ItemStack.loadItemStackFromNBT(nbttagcompound1);

			Boolean sealed = te.getSealed();
			int sealTime = accessor.getNBTInteger(tag, "SealTime");
			FluidStack fluid = FluidStack.loadFluidStackFromNBT(tag.getCompoundTag("fluidNBT"));
			BarrelRecipe recipe = BarrelManager.getInstance().findMatchingRecipe(inStack, fluid, sealed, te.getTechLevel());

			if (sealed && fluid != null && fluid.getFluid() == TFCFluids.MILKCURDLED &&
					(inStack == null || (inStack.getItem() instanceof IFood && ((IFood) inStack.getItem()).getFoodGroup() != EnumFoodGroup.Dairy &&
							((IFood) inStack.getItem()).isEdible(inStack) && Food.getWeight(inStack) <= 20.0f)))
				recipe = new BarrelRecipe(null, new FluidStack(TFCFluids.MILKCURDLED, 10000), ItemFoodTFC.createTag(new ItemStack(TFCItems.Cheese, 1), 160), null).setMinTechLevel(0);

			// Fluid Amount
			if (fluid != null)
			{
				currenttip.add(String.valueOf(fluid.amount) + "/" + String.valueOf(te.getMaxLiquid()) + " mB");
			}

			// Sealed Date
			if (sealed && sealTime != 0)
			{
				currenttip.add(TFC_Core.translate("gui.Barrel.SealedOn") + " : " + TFC_Time.getDateStringFromHours(sealTime));
			}

			// Output
			if (recipe != null)
			{
				if (!(recipe instanceof BarrelBriningRecipe))
				{
					currenttip.add(TFC_Core.translate("gui.Output") + " : " + recipe.getRecipeName());
				}
				else if (sealed && fluid != null && fluid.getFluid() == TFCFluids.BRINE)
				{
					if (inStack != null && inStack.getItem() instanceof IFood && (((IFood) inStack.getItem()).getFoodGroup() == EnumFoodGroup.Fruit ||
							((IFood) inStack.getItem()).getFoodGroup() == EnumFoodGroup.Vegetable || ((IFood) inStack.getItem()).getFoodGroup() == EnumFoodGroup.Protein ||
							((IFood) inStack.getItem()) == TFCItems.Cheese) && !Food.isBrined(inStack))
					{
						currenttip.add(TFC_Core.translate("gui.barrel.brining"));
					}
				}
			}
			else if (recipe == null && sealed && fluid != null && inStack != null && inStack.getItem() instanceof IFood &&
					fluid.getFluid() == TFCFluids.VINEGAR)
			{
				if (!Food.isPickled(inStack) && Food.getWeight(inStack) / fluid.amount <= Global.FOOD_MAX_WEIGHT / te.getMaxLiquid())
				{
					if ((((IFood) inStack.getItem()).getFoodGroup() == EnumFoodGroup.Fruit || ((IFood) inStack.getItem()).getFoodGroup() == EnumFoodGroup.Vegetable ||
							((IFood) inStack.getItem()).getFoodGroup() == EnumFoodGroup.Protein || ((IFood) inStack.getItem()) == TFCItems.Cheese) &&
							Food.isBrined(inStack))
					{
						currenttip.add(TFC_Core.translate("gui.barrel.pickling"));
					}
				}
				else if (Food.isPickled(inStack) && Food.getWeight(inStack) / fluid.amount <= Global.FOOD_MAX_WEIGHT / te.getMaxLiquid() * 2)
				{
					currenttip.add(TFC_Core.translate("gui.barrel.preserving"));
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

	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x, int y, int z) {
		if (te != null)
			te.writeToNBT(tag);
		return tag;
	}

	public static void callbackRegister(IWailaRegistrar reg)
	{
		reg.registerHeadProvider(new WBarrel(), TEBarrel.class);
		reg.registerBodyProvider(new WBarrel(), TEBarrel.class);
		reg.registerNBTProvider(new WBarrel(), TEBarrel.class);
	}
}