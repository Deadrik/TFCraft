package com.bioxx.tfc.Entities.Mobs;

import com.bioxx.tfc.Core.TFC_MobData;
import com.bioxx.tfc.api.ICausesDamage;
import com.bioxx.tfc.api.IInnateArmor;
import com.bioxx.tfc.api.Enums.EnumDamageType;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.world.World;

public class EntityEndermanTFC extends EntityEnderman implements ICausesDamage, IInnateArmor
{
	public static boolean[] carriableBlocks = new boolean[256];

	public EntityEndermanTFC(World par1World)
	{
		super(par1World);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(TFC_MobData.EndermanDamage);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(TFC_MobData.EndermanHealth);//MaxHealth
	}

	@Override
	public EnumDamageType GetDamageType() {
		// TODO Auto-generated method stub
		return EnumDamageType.GENERIC;
	}

	@Override
	public int GetCrushArmor() {
		return -335;
	}
	@Override
	public int GetSlashArmor() {
		return -335;
	}
	@Override
	public int GetPierceArmor() {
		return -335;
	}

}
