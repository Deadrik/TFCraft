package com.bioxx.tfc.Entities.Mobs;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.living.ZombieEvent.SummonAidEvent;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_MobData;
import com.bioxx.tfc.Food.CropIndex;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Enums.EnumDamageType;
import com.bioxx.tfc.api.Interfaces.ICausesDamage;
import com.bioxx.tfc.api.Interfaces.IInnateArmor;

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
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(TFC_MobData.ZOMBIE_DAMAGE);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(TFC_MobData.ZOMBIE_HEALTH);//MaxHealth
	}

	@Override
	public boolean getCanSpawnHere()
	{
		int x = MathHelper.floor_double(this.posX);
		int y = MathHelper.floor_double(this.boundingBox.minY);
		int z = MathHelper.floor_double(this.posZ);
		Block b = this.worldObj.getBlock(x, y, z);

		if(b == TFCBlocks.leaves || b == TFCBlocks.leaves2 || b == TFCBlocks.thatch)
			return false;

		return super.getCanSpawnHere();
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
			Random r0 = new Random();
			if (r0.nextInt(3) == 0)
				this.dropItem(TFCItems.wroughtIronIngot, 1);
			break;
		case 1:
			ItemStack is1 = new ItemStack(TFCItems.carrot);
			Random r1 = new Random();
			if(r1.nextInt(100) < 100)
			{
				float weight = CropIndex.getWeight(30.0f, r1);
				ItemFoodTFC.createTag(is1, weight, weight/2);
				entityDropItem(is1, 0);
			}
			break;
		case 2:
			ItemStack is2 = new ItemStack(TFCItems.potato);
			Random r2 = new Random();
			if(r2.nextInt(100) < 100)
			{
				float weight = CropIndex.getWeight(55.0f, r2);
				ItemFoodTFC.createTag(is2, weight, weight/2);
				entityDropItem(is2, 0);
			}
			break;
		}
	}

	@Override
	protected void addRandomArmor()
	{
		this.setCurrentItemOrArmor(0, null);
		this.setCurrentItemOrArmor(1, null);
		this.setCurrentItemOrArmor(2, null);
		this.setCurrentItemOrArmor(3, null);
		this.setCurrentItemOrArmor(4, null);

		if (this.rand.nextFloat() < (this.worldObj.difficultySetting == EnumDifficulty.HARD ? 0.05F : 0.01F))
		{
			int var1 = this.rand.nextInt(3);
			if (var1 == 0)
				this.setCurrentItemOrArmor(0, new ItemStack(TFCItems.bronzePick));
			else
				this.setCurrentItemOrArmor(0, new ItemStack(TFCItems.bronzeShovel));
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
	public EnumDamageType getDamageType() 
	{
		return EnumDamageType.SLASHING;
	}

	@Override
	public boolean attackEntityFrom(DamageSource ds, float par2)
	{
		if (ForgeHooks.onLivingAttack(this, ds, par2)) return false;
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
			else if (ds.isFireDamage() && this.isPotionActive(Potion.fireResistance))
			{
				return false;
			}
			else
			{
				if ((ds == DamageSource.anvil || ds == DamageSource.fallingBlock) && this.getEquipmentInSlot(4) != null)
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
					this.damageEntity(ds, par2 - this.lastDamage);
					this.lastDamage = par2;
					flag = false;
				}
				else
				{
					this.lastDamage = par2;
					this.prevHealth = this.getHealth();
					this.hurtResistantTime = this.maxHurtResistantTime;
					this.damageEntity(ds, par2);
					this.hurtTime = this.maxHurtTime = 10;
				}

				this.attackedAtYaw = 0.0F;
				Entity entity = ds.getEntity();

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

					if (ds != DamageSource.drown)
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
						this.attackedAtYaw = (int) (Math.random() * 2.0D) * 180;
					}
				}

				if (this.getHealth() <= 0.0F)
				{
					if (flag)
						this.playSound(this.getDeathSound(), this.getSoundVolume(), this.getSoundPitch());
					this.onDeath(ds);
				}
				else if (flag)
				{
					this.playSound(this.getHurtSound(), this.getSoundVolume(), this.getSoundPitch());
				}

				summonAid(ds);

				return true;
			}
		}
	}

	private void summonAid(DamageSource ds) 
	{
		EntityLivingBase entitylivingbase = this.getAttackTarget();

		if (entitylivingbase == null && this.getEntityToAttack() instanceof EntityLivingBase)
		{
			entitylivingbase = (EntityLivingBase)this.getEntityToAttack();
		}

		if (entitylivingbase == null && ds.getEntity() instanceof EntityLivingBase)
		{
			entitylivingbase = (EntityLivingBase)ds.getEntity();
		}

		int i = MathHelper.floor_double(this.posX);
		int j = MathHelper.floor_double(this.posY);
		int k = MathHelper.floor_double(this.posZ);

		SummonAidEvent summonAid = ForgeEventFactory.fireZombieSummonAid(this, worldObj, i, j, k, entitylivingbase, this.getEntityAttribute(field_110186_bp).getAttributeValue());

		if (summonAid.getResult() == Result.DENY)
		{
			return;
		}
		else if (summonAid.getResult() == Result.ALLOW || entitylivingbase != null && this.worldObj.difficultySetting == EnumDifficulty.HARD && this.rand.nextFloat() < this.getEntityAttribute(field_110186_bp).getAttributeValue())
		{
			EntityZombie entityzombie;
			if (summonAid.customSummonedAid != null && summonAid.getResult() == Result.ALLOW)
			{
				entityzombie = summonAid.customSummonedAid;
			}
			else
			{
				entityzombie = new EntityZombieTFC(this.worldObj);
			}

			for (int l = 0; l < 50; ++l)
			{
				int i1 = i + MathHelper.getRandomIntegerInRange(this.rand, 7, 40) * MathHelper.getRandomIntegerInRange(this.rand, -1, 1);
				int j1 = j + MathHelper.getRandomIntegerInRange(this.rand, 7, 40) * MathHelper.getRandomIntegerInRange(this.rand, -1, 1);
				int k1 = k + MathHelper.getRandomIntegerInRange(this.rand, 7, 40) * MathHelper.getRandomIntegerInRange(this.rand, -1, 1);

				if (World.doesBlockHaveSolidTopSurface(this.worldObj, i1, j1 - 1, k1) && this.worldObj.getBlockLightValue(i1, j1, k1) < 10 && TFC_Core.getCDM(worldObj).getData(i1 >> 4, k1 >> 4).getSpawnProtectionWithUpdate() <= 0)
				{
					entityzombie.setPosition(i1, j1, k1);

					if (this.worldObj.checkNoEntityCollision(entityzombie.boundingBox) && this.worldObj.getCollidingBoundingBoxes(entityzombie, entityzombie.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(entityzombie.boundingBox))
					{
						this.worldObj.spawnEntityInWorld(entityzombie);
						if (entitylivingbase != null) entityzombie.setAttackTarget(entitylivingbase);
						entityzombie.onSpawnWithEgg((IEntityLivingData)null);
						this.getEntityAttribute(field_110186_bp).applyModifier(new AttributeModifier("Zombie reinforcement caller charge", -0.05000000074505806D, 0));
						entityzombie.getEntityAttribute(field_110186_bp).applyModifier(new AttributeModifier("Zombie reinforcement callee charge", -0.05000000074505806D, 0));
						break;
					}
				}
			}
		}
	}

	@Override
	public int getCrushArmor()
	{
		return 1000;//equates to ~50% less damage taken
	}

	@Override
	public int getSlashArmor()
	{
		return -335;//equates to ~50% more damage taken
	}

	@Override
	public int getPierceArmor()
	{
		return 0;
	}
}
