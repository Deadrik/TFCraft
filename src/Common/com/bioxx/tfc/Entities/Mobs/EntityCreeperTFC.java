package com.bioxx.tfc.Entities.Mobs;

import com.bioxx.tfc.Core.TFC_Achievements;
import com.bioxx.tfc.api.Interfaces.IInnateArmor;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityCreeperTFC extends EntityCreeper implements IInnateArmor
{
	public EntityCreeperTFC(World par1World)
	{
		super(par1World);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(500);//MaxHealth
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
    {
		Entity entity = par1DamageSource.getEntity();
		if(entity instanceof EntityPlayer){
			((EntityPlayer)entity).triggerAchievement(TFC_Achievements.achPokeCreeper);
		}
		return super.attackEntityFrom(par1DamageSource, par2);
    }
	
	@Override
	public int GetCrushArmor() {
		return 1000;
	}
	@Override
	public int GetSlashArmor() {
		return 0;
	}
	@Override
	public int GetPierceArmor() {
		return -335;
	}

}
