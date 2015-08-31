package com.bioxx.tfc.WAILA;

import java.util.*;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.Metal.*;
import com.bioxx.tfc.TileEntities.TECrucible;
import com.bioxx.tfc.api.Metal;
import com.bioxx.tfc.api.TFC_ItemHeat;
import com.bioxx.tfc.api.Constant.Global;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;

public class WCrucible implements IWailaDataProvider
{
	private Map<String, MetalPair> metals = new HashMap<String, MetalPair>();
	private Alloy currentAlloy;

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
		//Empty list and clear alloy before adding from NBT, since this is continually called.
		metals.clear();
		currentAlloy = null;

		if (accessor.getTileEntity() instanceof TECrucible)
		{
			NBTTagCompound tag = accessor.getNBTData();
			NBTTagList taglist = tag.getTagList("Metals", 10);

			//Add metals to now empty list.
			for (int i = 0; i < taglist.tagCount(); i++)
			{
				NBTTagCompound nbt = taglist.getCompoundTagAt(i);
				int id = nbt.getInteger("ID");
				float amount = nbt.getShort("Amount");
				float amountF = amount + nbt.getFloat("AmountF");
				Metal metal = MetalRegistry.instance.getMetalFromItem(Item.getItemById(id));
				addMetal(metal, amountF);
			}

			//Alloy Components
			if (currentAlloy != null)
			{
				String metalTypeUnits = EnumChatFormatting.UNDERLINE + TFC_Core.translate("gui.metal.Unknown");
				if (currentAlloy.outputType != null)
				{
					metalTypeUnits = EnumChatFormatting.UNDERLINE + TFC_Core.translate("gui.metal." + currentAlloy.outputType.name.replace(" ", ""));
				}

				int output = Math.round(currentAlloy.outputAmount);
				metalTypeUnits += "\u00B7 " + TFC_Core.translate("gui.units") + " : " + output;

				currenttip.add(metalTypeUnits);

				for (int c = 0; c < currentAlloy.alloyIngred.size(); c++)
				{
					double m = currentAlloy.alloyIngred.get(c).metal;
					m = Math.round(m * 100d) / 100d;
					if (currentAlloy.alloyIngred.get(c).metalType != null)
					{
						currenttip.add("\u00B7 " + TFC_Core.translate("gui.metal." + currentAlloy.alloyIngred.get(c).metalType.name.replace(" ", ""))
								+ " : " + m + "%");
					}
				}
			}

			//Current Temperature
			int temperature = tag.getInteger("temp");
			String temp = TFC_ItemHeat.getHeatColor(temperature, Integer.MAX_VALUE);
			if (temperature > 0)
			{
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
		reg.registerBodyProvider(new WCrucible(), TECrucible.class);
		reg.registerNBTProvider(new WCrucible(), TECrucible.class);
	}

	public boolean addMetal(Metal m, float amt)
	{
		if (getTotalMetal() + amt <= 3000 && !"Unknown".equals(m.name))
		{
			if (metals.containsKey(m.name))
				metals.get(m.name).amount += amt;
			else
				metals.put(m.name, new MetalPair(m, amt));

			updateCurrentAlloy();
			return true;
		}
		return false;
	}

	public float getTotalMetal()
	{
		Iterator<MetalPair> iter = metals.values().iterator();
		float totalAmount = 0;
		while (iter.hasNext())
		{
			MetalPair m = iter.next();
			if (m != null)
				totalAmount += m.amount;
		}
		return totalAmount;
	}

	private void updateCurrentAlloy()
	{
		List<AlloyMetal> a = new ArrayList<AlloyMetal>();
		Iterator<MetalPair> iter = metals.values().iterator();
		float totalAmount = getTotalMetal();
		while (iter.hasNext())
		{
			MetalPair m = iter.next();
			if (m != null)
				a.add(new AlloyMetal(m.type, (m.amount / totalAmount) * 100f));
		}

		Metal match = AlloyManager.INSTANCE.matchesAlloy(a, Alloy.EnumTier.TierV);
		if (match != null)
		{
			currentAlloy = new Alloy(match, totalAmount);
			currentAlloy.alloyIngred = a;
		}
		else
		{
			currentAlloy = new Alloy(Global.UNKNOWN, totalAmount);
			currentAlloy.alloyIngred = a;
		}
	}
}