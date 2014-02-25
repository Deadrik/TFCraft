package TFC.API.Events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.Cancelable;
import net.minecraftforge.event.entity.EntityEvent;

@Cancelable
public class GetSkillMultiplierEvent extends EntityEvent
{
	public final String skillname;
	public float skillResult;

	public GetSkillMultiplierEvent(EntityPlayer entity, String skillName, float result)
	{
		super(entity);
		skillname = skillName;
		skillResult = result;
	}
}
