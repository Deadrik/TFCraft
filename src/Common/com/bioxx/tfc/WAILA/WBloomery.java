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
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.TileEntities.TEBloomery;
import com.bioxx.tfc.api.TFCOptions;

public class WBloomery implements IWailaDataProvider
{
	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
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
		NBTTagCompound tag = accessor.getNBTData();
		boolean isLit = tag.getBoolean("isLit");
		int charcoalCount = tag.getInteger("charcoalCount");
		int oreCount = tag.getInteger("oreCount");

		currenttip.add(TFC_Core.translate("gui.Bloomery.Charcoal") + " : " + charcoalCount);
		currenttip.add(TFC_Core.translate("gui.Bloomery.Ore") + " : " + oreCount);

		if (isLit)
		{
			long timeLeft = tag.getLong("fuelTimeLeft");
			long currentTime = TFC_Time.getTotalTicks();
			long timeProcessed = (int) Math.max(timeLeft - currentTime, 0);
			int percent = (int) Math.min(100 - ((timeProcessed / (TFC_Time.hourLength * TFCOptions.bloomeryBurnTime)) * 100), 100);

			currenttip.add(TFC_Core.translate("gui.progress") + " : " + String.valueOf(percent) + "%");
		}

		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		return currenttip;
	}

	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x, int y, int z)
	{
		if (te != null)
			te.writeToNBT(tag);
		return tag;
	}

	public static void callbackRegister(IWailaRegistrar reg)
	{
		reg.registerBodyProvider(new WBloomery(), TEBloomery.class);
		reg.registerNBTProvider(new WBloomery(), TEBloomery.class);
	}
}