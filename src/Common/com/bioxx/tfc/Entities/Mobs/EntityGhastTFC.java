package com.bioxx.tfc.Entities.Mobs;

import com.bioxx.tfc.Core.TFC_MobData;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.world.World;

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
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(TFC_MobData.GhastHealth);//MaxHealth
	}
}
