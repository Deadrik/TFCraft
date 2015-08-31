package com.bioxx.tfc.Entities.Mobs;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.world.World;

import com.bioxx.tfc.Core.TFC_MobData;

public class EntityGhastTFC extends EntityGhast
{
	public EntityGhastTFC(World par1World)
	{
		super(par1World);

	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(TFC_MobData.GHAST_HEALTH);//MaxHealth
	}
}
