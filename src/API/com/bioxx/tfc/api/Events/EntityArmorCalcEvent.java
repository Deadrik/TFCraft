package com.bioxx.tfc.api.Events;

import net.minecraft.entity.EntityLivingBase;

import net.minecraftforge.event.entity.EntityEvent;

public class EntityArmorCalcEvent extends EntityEvent 
{	
	public static enum EventType { PRE, POST }

	public float incomingDamage;
	public final EventType eventType;

	/**
	 * incomingDamage can be modified and is used for further calculations in the armor code
	 */
	public EntityArmorCalcEvent(EntityLivingBase p, float damage, EventType eType)
	{
		super(p);
		incomingDamage = damage;
		eventType = eType;
	}
}
