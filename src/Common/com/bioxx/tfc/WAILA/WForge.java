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
import com.bioxx.tfc.Items.ItemCoal;
import com.bioxx.tfc.TileEntities.TEForge;
import com.bioxx.tfc.api.TFCItems;

public class WForge implements IWailaDataProvider
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
		if (accessor.getTileEntity() instanceof TEForge)
		{
			NBTTagCompound tag = accessor.getNBTData();
			NBTTagList taglist = tag.getTagList("Items", 10);
			ItemStack fireItemStacks[] = new ItemStack[14];
			for (int i = 0; i < taglist.tagCount(); i++)
			{
				NBTTagCompound nbt = taglist.getCompoundTagAt(i);
				byte slot = nbt.getByte("Slot");
				if (slot >= 0 && slot < fireItemStacks.length)
					fireItemStacks[slot] = ItemStack.loadItemStackFromNBT(nbt);
			}

			if (fireItemStacks != null)
			{
				int fuelCount = 0;
				boolean hasMold = false;

				for (int i = 5; i <= 9; i++) // Fuels are stored in slots 5 through 9 per te.HandleFuelStack()
				{
					if (fireItemStacks[i] != null && fireItemStacks[i].getItem() != null && fireItemStacks[i].getItem() instanceof ItemCoal)
						fuelCount++;
				}

				if (fuelCount > 0)
					currenttip.add(TFC_Core.translate("gui.fuel") + " : " + fuelCount + "/5");

				for (int j = 10; j <= 13; j++) // Molds are stored in slots 7 through 9 per te.getMold()
				{
					if (fireItemStacks[j] != null && fireItemStacks[j].getItem() == TFCItems.CeramicMold && fireItemStacks[j].stackSize > 0)
						hasMold = true;
				}
				if (hasMold)
					currenttip.add(TFC_Core.translate("item.Mold.Ceramic Mold.name") + EnumChatFormatting.GREEN.toString() + " \u2714");
				else
					currenttip.add(TFC_Core.translate("item.Mold.Ceramic Mold.name") + EnumChatFormatting.RED.toString() + " \u2718");
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
		reg.registerBodyProvider(new WForge(), TEForge.class);
		reg.registerNBTProvider(new WForge(), TEForge.class);
	}
}