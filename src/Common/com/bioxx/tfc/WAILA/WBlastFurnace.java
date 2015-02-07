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
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.TileEntities.TEBlastFurnace;
import com.bioxx.tfc.api.HeatIndex;
import com.bioxx.tfc.api.HeatRegistry;
import com.bioxx.tfc.api.TFC_ItemHeat;

public class WBlastFurnace implements IWailaDataProvider
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
		if (accessor.getTileEntity() instanceof TEBlastFurnace)
		{
			TEBlastFurnace te = (TEBlastFurnace) accessor.getTileEntity();
			NBTTagCompound tag = accessor.getNBTData();

			int charcoalCount = tag.getInteger("charcoalCount");
			int oreCount = tag.getByte("oreCount");
			int stackSize = tag.getInteger("maxValidStackSize");
			float temperature = 0;
			ItemStack oreStack = null;

			NBTTagList nbttaglist = tag.getTagList("Items", 10);
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(0);
			byte byte0 = nbttagcompound1.getByte("Slot");

			if (byte0 >= 0 && byte0 < te.fireItemStacks.length)
				oreStack = ItemStack.loadItemStackFromNBT(nbttagcompound1);

			HeatRegistry manager = HeatRegistry.getInstance();

			if (oreStack != null)
			{
				HeatIndex index = manager.findMatchingIndex(oreStack);
				if (index != null)
				{
					temperature = TFC_ItemHeat.GetTemp(oreStack);
				}
			}
			String temp = TFC_ItemHeat.getHeatColor(temperature, te.maxFireTempScale);

			currenttip.add(TFC_Core.translate("gui.Bloomery.Charcoal") + " : " + charcoalCount + "/" + stackSize * 4);
			currenttip.add(TFC_Core.translate("gui.Bloomery.Ore") + " : " + oreCount + "/" + stackSize * 4);

			if (te.storage[1] != null)
				currenttip.add(TFC_Core.translate("gui.plans.tuyere") + EnumChatFormatting.GREEN.toString() + " \u2714");
			else
				currenttip.add(TFC_Core.translate("gui.plans.tuyere") + EnumChatFormatting.RED.toString() + " \u2718");

			if (temperature > 0)
			{
				currenttip.add(TFC_Core.translate("gui.temperature") + " : ");
				currenttip.add(temp);
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
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x, int y, int z)
	{
		if (te != null)
			te.writeToNBT(tag);
		return tag;
	}

	public static void callbackRegister(IWailaRegistrar reg)
	{
		reg.registerBodyProvider(new WBlastFurnace(), TEBlastFurnace.class);
		reg.registerNBTProvider(new WBlastFurnace(), TEBlastFurnace.class);
	}
}