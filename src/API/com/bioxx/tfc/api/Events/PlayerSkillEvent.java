package com.bioxx.tfc.api.Events;

import net.minecraft.entity.player.EntityPlayer;

import net.minecraftforge.event.entity.EntityEvent;

import cpw.mods.fml.common.eventhandler.Cancelable;

@Cancelable
public class PlayerSkillEvent extends EntityEvent
{
	protected PlayerSkillEvent(EntityPlayer entity)
	{
		super(entity);
	}

	@Cancelable
	public static class Increase extends PlayerSkillEvent
	{
		public final int skillGain;
		public final String skillName;

		public Increase(EntityPlayer entity, String name, int skill)
		{
			super(entity);
			this.skillGain = skill;
			this.skillName = name;
		}

	}
}
