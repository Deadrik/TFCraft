package com.bioxx.tfc.GUI.WAILA;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import com.bioxx.tfc.Food.CropIndex;
import com.bioxx.tfc.Food.CropManager;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.TileEntities.TECrop;

public class TFCCrop implements IWailaDataProvider
{
	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		if (accessor.getTileEntity() instanceof TECrop)
		{
			TECrop te = (TECrop) accessor.getTileEntity();

			CropIndex crop = CropManager.getInstance().getCropFromId(te.cropId);
			ItemStack itemstack;

			if (crop.Output2 != null)
				itemstack = new ItemStack(crop.Output2);
			else
				itemstack = new ItemStack(crop.Output1);

			ItemFoodTFC.createTag(itemstack);
			return itemstack;
		}
		else
			return null;
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		return currenttip;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		if (accessor.getTileEntity() instanceof TECrop)
		{
			TECrop te = (TECrop) accessor.getTileEntity();
			CropIndex crop = CropManager.getInstance().getCropFromId(te.cropId);
			int percentGrowth = (int) Math.min((te.growth / crop.numGrowthStages) * 100, 100);

			if (percentGrowth < 100)
				currenttip.add(StatCollector.translateToLocal("gui.growth") + " : " + String.valueOf(percentGrowth) + "%");
			else
				currenttip.add(StatCollector.translateToLocal("gui.growth") + " : " + StatCollector.translateToLocal("gui.mature"));
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
		reg.registerStackProvider(new TFCCrop(), TECrop.class);
		reg.registerBodyProvider(new TFCCrop(), TECrop.class);
	}
}