package TFC.API.Events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityEvent;
import cpw.mods.fml.common.eventhandler.Cancelable;

@Cancelable
public class PlayerSkillEvent extends EntityEvent
{
	public final World world;

	protected PlayerSkillEvent(EntityPlayer entity, World world)
	{
		super(entity);
		this.world = world;
	}

	@Cancelable
	public class Increase extends PlayerSkillEvent
	{
		public final float skillGain;
		public final String skillName;

		public Increase(EntityPlayer entity, World world, String name, float skill) 
		{
			super(entity, world);
			this.skillGain = skill;
			this.skillName = name;
		}

	}
}
