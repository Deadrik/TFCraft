package com.bioxx.tfc.Entities.Mobs;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.bioxx.tfc.Core.TFC_MobData;
import com.bioxx.tfc.Core.WeatherManager;
import com.bioxx.tfc.api.Enums.EnumDamageType;
import com.bioxx.tfc.api.Interfaces.ICausesDamage;
import com.bioxx.tfc.api.Interfaces.IInnateArmor;

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
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(TFC_MobData.ENDERMAN_DAMAGE);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(TFC_MobData.ENDERMAN_HEALTH);//MaxHealth
	}

	@Override
	public EnumDamageType getDamageType() {
		// TODO Auto-generated method stub
		return EnumDamageType.GENERIC;
	}

	@Override
	public int getCrushArmor() {
		return -335;
	}
	@Override
	public int getSlashArmor() {
		return -335;
	}
	@Override
	public int getPierceArmor() {
		return -335;
	}

	/**
     * Checks if this entity is either in water or on an open air block in rain (used in wolves).
     */
    @Override
	public boolean isWet()
    {
        return this.inWater || WeatherManager.isRainingOnCoord(this.worldObj, (int) this.posX, (int) this.posY + 1, (int) this.posZ) || this.worldObj.canLightningStrikeAt(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) || this.worldObj.canLightningStrikeAt(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY + (double)this.height), MathHelper.floor_double(this.posZ));
    }
}
