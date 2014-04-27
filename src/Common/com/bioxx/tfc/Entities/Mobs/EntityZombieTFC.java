package com.bioxx.tfc.Entities.Mobs;

import java.util.Random;

import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.Core.TFC_MobData;
import com.bioxx.tfc.Food.CropIndex;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.api.ICausesDamage;
import com.bioxx.tfc.api.IInnateArmor;
import com.bioxx.tfc.api.Enums.EnumDamageType;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityZombieTFC extends EntityZombie implements ICausesDamage, IInnateArmor
{
	public EntityZombieTFC(World par1World)
	{
		super(par1World);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(TFC_MobData.ZombieDamage);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(TFC_MobData.ZombieHealth);//MaxHealth
	}

	/**
	 * Returns the current armor value as determined by a call to InventoryPlayer.getTotalArmorValue
	 */
	@Override
	public int getTotalArmorValue()
	{
		int var1 = super.getTotalArmorValue() + 2;
		if (var1 > 20)
			var1 = 20;
		return var1;
	}

	@Override
	protected void dropRareDrop(int par1)
	{
		switch (this.rand.nextInt(3))
		{
		case 0:
			this.dropItem(TFCItems.WroughtIronIngot, 1);
			break;
		case 1:
			ItemStack is1 = new ItemStack(TFCItems.Carrot);
			Random R1 = new Random();
			if(R1.nextInt(100) < 100)
			{
				float weight = CropIndex.getWeight(30.0f, R1);
				ItemFoodTFC.createTag(is1, weight, weight/2);
				entityDropItem(is1, 0);
			}
			break;
		case 2:
			ItemStack is2 = new ItemStack(TFCItems.Potato);
			Random R2 = new Random();
			if(R2.nextInt(100) < 100)
			{
				float weight = CropIndex.getWeight(55.0f, R2);
				ItemFoodTFC.createTag(is2, weight, weight/2);
				entityDropItem(is2, 0);
			}
		}
	}

	@Override
	protected void addRandomArmor()
	{
		super.addRandomArmor();
		this.setCurrentItemOrArmor(1, null);
		this.setCurrentItemOrArmor(2, null);
		this.setCurrentItemOrArmor(3, null);
		this.setCurrentItemOrArmor(4, null);

		if (this.rand.nextFloat() < (this.worldObj.difficultySetting == EnumDifficulty.HARD ? 0.05F : 0.01F))
		{
			int var1 = this.rand.nextInt(3);
			if (var1 == 0)
				this.setCurrentItemOrArmor(0, new ItemStack(TFCItems.BronzePick));
			else
				this.setCurrentItemOrArmor(0, new ItemStack(TFCItems.BronzeShovel));
		}
	}

	@Override
	protected void enchantEquipment()
	{
	}


	@Override
	@SideOnly(Side.CLIENT)
	public void handleHealthUpdate(byte par1)
	{
		if (par1 == 16)
			this.worldObj.playSoundEffect(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, "mob.zombie.remedy", 1.0F + this.rand.nextFloat(), this.rand.nextFloat() * 0.7F + 0.3F);
		else
			super.handleHealthUpdate(par1);
	}

	@Override
	public EnumDamageType GetDamageType() 
	{
		return EnumDamageType.SLASHING;
	}

	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
	{
		if (ForgeHooks.onLivingAttack(this, par1DamageSource, par2)) return false;
		if (this.isEntityInvulnerable())
		{
			return false;
		}
		else if (this.worldObj.isRemote)
		{
			return false;
		}
		else
		{
			this.entityAge = 0;

			if (this.getHealth() <= 0.0F)
			{
				return false;
			}
			else if (par1DamageSource.isFireDamage() && this.isPotionActive(Potion.fireResistance))
			{
				return false;
			}
			else
			{
				if ((par1DamageSource == DamageSource.anvil || par1DamageSource == DamageSource.fallingBlock) && this.getEquipmentInSlot(4) != null)
				{
					this.getEquipmentInSlot(4).damageItem((int)(par2 * 4.0F + this.rand.nextFloat() * par2 * 2.0F), this);
					par2 *= 0.75F;
				}

				this.limbSwingAmount = 1.5F;
				boolean flag = true;

				if (this.hurtResistantTime > this.maxHurtResistantTime / 2.0F)
				{
					if (par2 <= this.lastDamage)
						return false;
					this.damageEntity(par1DamageSource, par2 - this.lastDamage);
					this.lastDamage = par2;
					flag = false;
				}
				else
				{
					this.lastDamage = par2;
					this.prevHealth = this.getHealth();
					this.hurtResistantTime = this.maxHurtResistantTime;
					this.damageEntity(par1DamageSource, par2);
					this.hurtTime = this.maxHurtTime = 10;
				}

				this.attackedAtYaw = 0.0F;
				Entity entity = par1DamageSource.getEntity();

				if (entity != null)
				{
					if (entity instanceof EntityLivingBase)
						this.setRevengeTarget((EntityLivingBase)entity);

					if (entity instanceof EntityPlayer)
					{
						this.recentlyHit = 100;
						this.attackingPlayer = (EntityPlayer)entity;
					}
					else if (entity instanceof EntityWolf)
					{
						EntityWolf entitywolf = (EntityWolf)entity;

						if (entitywolf.isTamed())
						{
							this.recentlyHit = 100;
							this.attackingPlayer = null;
						}
					}
				}

				if (flag)
				{
					this.worldObj.setEntityState(this, (byte)2);

					if (par1DamageSource != DamageSource.drown)
						this.setBeenAttacked();

					if (entity != null)
					{
						double d0 = entity.posX - this.posX;
						double d1;

						for (d1 = entity.posZ - this.posZ; d0 * d0 + d1 * d1 < 1.0E-4D; d1 = (Math.random() - Math.random()) * 0.01D)
						{
							d0 = (Math.random() - Math.random()) * 0.01D;
						}

						this.attackedAtYaw = (float)(Math.atan2(d1, d0) * 180.0D / Math.PI) - this.rotationYaw;
						this.knockBack(entity, par2, d0, d1);
					}
					else
					{
						this.attackedAtYaw = (float)((int)(Math.random() * 2.0D) * 180);
					}
				}

				if (this.getHealth() <= 0.0F)
				{
					if (flag)
						this.playSound(this.getDeathSound(), this.getSoundVolume(), this.getSoundPitch());
					this.onDeath(par1DamageSource);
				}
				else if (flag)
				{
					this.playSound(this.getHurtSound(), this.getSoundVolume(), this.getSoundPitch());
				}
				return true;
			}
		}
	}

	@Override
	public int GetCrushArmor()
	{
		return 1000;//equates to ~50% less damage taken
	}

	@Override
	public int GetSlashArmor()
	{
		return -335;//equates to ~50% more damage taken
	}

	@Override
	public int GetPierceArmor()
	{
		return 0;
	}
}
