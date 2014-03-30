package TFC.Handlers;

import net.minecraft.entity.player.EntityPlayer;
import TFC.API.Constant.Global;
import TFC.API.Events.GetSkillMultiplierEvent;
import TFC.Core.TFC_Core;
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
			event.skillResult = 1-(500f/(500f+TFC_Core.getSkillStats((EntityPlayer)event.entity).getSkill(event.skillname)));
		}
	}
}
