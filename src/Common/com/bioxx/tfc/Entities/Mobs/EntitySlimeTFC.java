package com.bioxx.tfc.Entities.Mobs;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import com.bioxx.tfc.Core.TFC_MobData;

public class EntitySlimeTFC extends EntitySlime
{
	public EntitySlimeTFC(World par1World)
	{
		super(par1World);
	}

	@Override
	protected void setSlimeSize(int par1)
	{
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(par1*TFC_MobData.SLIME_HEALTH);
		this.setHealth(this.getMaxHealth());
	}

	@Override
	protected EntitySlimeTFC createInstance()
	{
		return new EntitySlimeTFC(this.worldObj);
	}

	/**
	 * Will get destroyed next tick.
	 */
	@Override
	public void setDead()
	{
		int var1 = this.getSlimeSize();
		if (!this.worldObj.isRemote && var1 > 1 && this.getHealth() <= 0)
		{
			int var2 = 2 + this.rand.nextInt(3);
			for (int var3 = 0; var3 < var2; ++var3)
			{
				float var4 = (var3 % 2 - 0.5F) * var1 / 4.0F;
				float var5 = (var3 / 2 - 0.5F) * var1 / 4.0F;
				EntitySlimeTFC var6 = this.createInstance();
				var6.setSlimeSize(var1 / 2);
				var6.setLocationAndAngles(this.posX + var4, this.posY + 0.5D, this.posZ + var5, this.rand.nextFloat() * 360.0F, 0.0F);
				this.worldObj.spawnEntityInWorld(var6);
			}
		}
		super.setDead();
	}

	/**
	 * Called by a player entity when they collide with an entity
	 */
	@Override
	public void onCollideWithPlayer(EntityPlayer par1EntityPlayer)
	{
		if (this.canDamagePlayer())
		{
			int var2 = this.getSlimeSize();
			if (this.canEntityBeSeen(par1EntityPlayer) && this.getDistanceSqToEntity(par1EntityPlayer) < 0.6D * var2 * 0.6D * var2 && par1EntityPlayer.attackEntityFrom(DamageSource.causeMobDamage(this), this.getAttackStrength()))
				this.worldObj.playSoundAtEntity(this, "mob.slime.attack", 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
		}
	}

	/**
	 * Indicates weather the slime is able to damage the player (based upon the slime's size)
	 */
	@Override
	protected boolean canDamagePlayer()
	{
		return this.getSlimeSize() > 1;
	}

	/**
	 * Gets the amount of damage dealt to the player when "attacked" by the slime.
	 */
	@Override
	protected int getAttackStrength()
	{
		return this.getSlimeSize() * TFC_MobData.SLIME_DAMAGE;
	}
}
