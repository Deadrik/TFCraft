package com.bioxx.tfc.Entities.Mobs;

import com.bioxx.tfc.Core.TFC_MobData;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.world.World;

public class EntityBlazeTFC extends EntityBlaze
{
	public EntityBlazeTFC(World par1World)
	{
		super(par1World);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(TFC_MobData.BlazeDamage);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(TFC_MobData.BlazeHealth);//MaxHealth
	}
}
