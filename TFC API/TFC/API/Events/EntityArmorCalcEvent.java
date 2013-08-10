package TFC.API.Events;

import net.minecraft.entity.EntityLiving;
import net.minecraftforge.event.entity.EntityEvent;

public class EntityArmorCalcEvent extends EntityEvent 
{	
	public static enum EventType { PRE, POST }
	
	public int incomingDamage;
	public final EventType eventType;
	
	/**
	 * incomingDamage can be modified and is used for further calculations in the armor code
	 */
	public EntityArmorCalcEvent(EntityLiving p, int damage, EventType eType)
	{
		super(p);
		incomingDamage = damage;
		eventType = eType;
	}
}
