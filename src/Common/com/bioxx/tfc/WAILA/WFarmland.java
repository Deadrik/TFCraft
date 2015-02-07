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
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.Player.SkillStats.SkillRank;
import com.bioxx.tfc.TileEntities.TEFarmland;
import com.bioxx.tfc.api.Constant.Global;

public class WFarmland implements IWailaDataProvider
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
		if (accessor.getTileEntity() instanceof TEFarmland)
		{
			SkillRank rank = TFC_Core.getSkillStats(accessor.getPlayer()).getSkillRank(Global.SKILL_AGRICULTURE);
			if (rank == SkillRank.Expert || rank == SkillRank.Master)
			{
				TEFarmland te = (TEFarmland) accessor.getTileEntity();
				NBTTagCompound tag = accessor.getNBTData();

				int nutrients[] = tag.getIntArray("nutrients");
				int soilMax = te.getSoilMax();

				for (int i = 0; i < nutrients.length; i++)
				{
					int percent = Math.max(nutrients[i] * 100 / soilMax, 0);

					if (i == 0)
						currenttip.add(EnumChatFormatting.RED + TFC_Core.translate("gui.Nutrient.A") + " : " + percent + "%");
					else if (i == 1)
						currenttip.add(EnumChatFormatting.GOLD + TFC_Core.translate("gui.Nutrient.B") + " : " + percent + "%");
					else if (i == 2)
						currenttip.add(EnumChatFormatting.YELLOW + TFC_Core.translate("gui.Nutrient.C") + " : " + percent + "%");
					else if (i == 3 && percent != 0)
						currenttip.add(EnumChatFormatting.WHITE + TFC_Core.translate("item.Fertilizer.name") + " : " + percent + "%");
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
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x, int y, int z)
	{
		if (te != null)
			te.writeToNBT(tag);
		return tag;
	}

	public static void callbackRegister(IWailaRegistrar reg)
	{
		reg.registerBodyProvider(new WFarmland(), TEFarmland.class);
		reg.registerNBTProvider(new WFarmland(), TEFarmland.class);
	}
}