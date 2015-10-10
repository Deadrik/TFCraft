package com.bioxx.tfc.WAILA;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Entities.Mobs.EntityCowTFC;
import com.bioxx.tfc.api.Entities.IAnimal;
import com.bioxx.tfc.api.Entities.IAnimal.GenderEnum;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaEntityAccessor;
import mcp.mobius.waila.api.IWailaEntityProvider;
import mcp.mobius.waila.api.IWailaRegistrar;

public class WMobs implements IWailaEntityProvider
{

	@Override
	public Entity getWailaOverride(IWailaEntityAccessor accessor, IWailaConfigHandler config)
	{
		return null;
	}

	@Override
	public List<String> getWailaHead(Entity entity, List<String> currenttip, IWailaEntityAccessor accessor, IWailaConfigHandler config)
	{
		String head = currenttip.get(0);

		if (entity instanceof IAnimal)
		{
			IAnimal animal = (IAnimal) entity;

			if (config.getConfig("tfc.baby"))
			{
				if (!animal.isAdult())
					head = EnumChatFormatting.WHITE + TFC_Core.translate("gui.baby") + " " + head;
			}

			if (config.getConfig("tfc.gender"))
			{
				if (animal.getGender() == GenderEnum.MALE)
					head += " \u2642";
				else if (animal.getGender() == GenderEnum.FEMALE)
					head += " \u2640";
			}
		}

		currenttip.set(0, head);

		return currenttip;
	}

	@Override
	public List<String> getWailaBody(Entity entity, List<String> currenttip, IWailaEntityAccessor accessor, IWailaConfigHandler config)
	{
		IAnimal animal = (IAnimal) entity;
		NBTTagCompound nbt = accessor.getNBTData();

		int familiarity = nbt.getInteger("Familiarity");
		boolean familiarizedToday = animal.getFamiliarizedToday();
		boolean pregnant = animal.isPregnant();

		if (pregnant)
		{
			currenttip.add(TFC_Core.translate("entity.pregnant") + " : " + TFC_Time.getDateStringFromHours(animal.getDueDay() * TFC_Time.HOURS_IN_DAY));
		}

		if (config.getConfig("tfc.familiarToday") && animal.canFamiliarize())
		{
			if (familiarizedToday)
				currenttip.add(TFC_Core.translate("gui.familiarized") + EnumChatFormatting.GREEN.toString() + " \u2714");
			else
				currenttip.add(TFC_Core.translate("gui.familiarized") + EnumChatFormatting.RED.toString() + " \u2718");
		}

		if (config.getConfig("tfc.familiarity"))
		{
			currenttip.add(TFC_Core.translate("gui.familiarity") + " : " + familiarity + "%");
		}

		if (animal instanceof EntityCowTFC && animal.getGender() == GenderEnum.FEMALE && animal.isAdult())
		{
			EntityCowTFC cow = (EntityCowTFC) entity;
			if (cow.isMilkable())
				currenttip.add(TFC_Core.translate("fluid.milk") + EnumChatFormatting.GREEN.toString() + " \u2714");
			else
				currenttip.add(TFC_Core.translate("fluid.milk") + EnumChatFormatting.RED.toString() + " \u2718");
		}

		return currenttip;
	}

	@Override
	public List<String> getWailaTail(Entity entity, List<String> currenttip, IWailaEntityAccessor accessor, IWailaConfigHandler config)
	{
		return currenttip;
	}

	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, Entity ent, NBTTagCompound tag, World world)
	{
		return tag;
	}

	public static void callbackRegister(IWailaRegistrar reg)
	{
		reg.addConfig("TerraFirmaCraft", "tfc.baby");
		reg.addConfig("TerraFirmaCraft", "tfc.gender");
		reg.addConfig("TerraFirmaCraft", "tfc.familiarToday");
		reg.addConfig("TerraFirmaCraft", "tfc.familiarity", false);

		reg.registerHeadProvider(new WMobs(), IAnimal.class);
		reg.registerBodyProvider(new WMobs(), IAnimal.class);
	}

}
