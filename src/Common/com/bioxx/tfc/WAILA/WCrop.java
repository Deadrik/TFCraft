package com.bioxx.tfc.WAILA;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Food.CropIndex;
import com.bioxx.tfc.Food.CropManager;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.TileEntities.TECrop;

public class WCrop implements IWailaDataProvider
{
	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		if (accessor.getTileEntity() instanceof TECrop)
		{
			NBTTagCompound tag = accessor.getNBTData();
			int cropId = tag.getInteger("cropId");

			CropIndex crop = CropManager.getInstance().getCropFromId(cropId);
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
			NBTTagCompound tag = accessor.getNBTData();
			float growth = tag.getFloat("growth");
			int cropId = tag.getInteger("cropId");

			CropIndex crop = CropManager.getInstance().getCropFromId(cropId);
			int percentGrowth = (int) Math.min((growth / crop.numGrowthStages) * 100, 100);

			if (percentGrowth < 100)
				currenttip.add(TFC_Core.translate("gui.growth") + " : " + String.valueOf(percentGrowth) + "%");
			else
				currenttip.add(TFC_Core.translate("gui.growth") + " : " + TFC_Core.translate("gui.mature"));
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
		reg.registerStackProvider(new WCrop(), TECrop.class);
		reg.registerBodyProvider(new WCrop(), TECrop.class);
		reg.registerNBTProvider(new WCrop(), TECrop.class);
	}

	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x, int y, int z)
	{
		if (te != null)
			te.writeToNBT(tag);
		return tag;
	}
}
