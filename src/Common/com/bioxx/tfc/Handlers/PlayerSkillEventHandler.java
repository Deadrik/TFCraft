package com.bioxx.tfc.Handlers;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Events.GetSkillMultiplierEvent;

import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class PlayerSkillEventHandler 
{
	@SubscribeEvent
	public void onGetMultiplier(GetSkillMultiplierEvent event)
	{
		if(event.skillname == Global.SKILL_COOKING)
		{
			event.skillResult = 1-(250f/(250f+TFC_Core.getSkillStats((EntityPlayer)event.entity).getSkill(event.skillname)));
		}
		else if(event.skillname == Global.SKILL_AGRICULTURE)
		{
			event.skillResult = 1-(300f/(300f+TFC_Core.getSkillStats((EntityPlayer)event.entity).getSkill(event.skillname)));
		}
	}
}
