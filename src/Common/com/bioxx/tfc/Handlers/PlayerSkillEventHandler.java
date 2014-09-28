package com.bioxx.tfc.Handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Events.GetSkillMultiplierEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class PlayerSkillEventHandler 
{
	@SubscribeEvent
	public void onGetMultiplier(GetSkillMultiplierEvent event)
	{
		if(StatCollector.translateToLocal(event.skillname) == StatCollector.translateToLocal(Global.SKILL_COOKING))
		{
			event.skillResult = 1-(250f/(250f+TFC_Core.getSkillStats((EntityPlayer)event.entity).getSkillRaw(event.skillname)));
		}
		else if(StatCollector.translateToLocal(event.skillname) == StatCollector.translateToLocal(Global.SKILL_AGRICULTURE))
		{
			event.skillResult = 1-(300f/(300f+TFC_Core.getSkillStats((EntityPlayer)event.entity).getSkillRaw(event.skillname)));
		}
		else if(StatCollector.translateToLocal(event.skillname) == StatCollector.translateToLocal(Global.SKILL_PROSPECTING))
		{
			event.skillResult = 1-(1500f/(1500f+TFC_Core.getSkillStats((EntityPlayer)event.entity).getSkillRaw(event.skillname)));
		}
	}
}
