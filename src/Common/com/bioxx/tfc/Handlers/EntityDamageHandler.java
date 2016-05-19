package com.bioxx.tfc.Handlers;

import java.util.Random;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MathHelper;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_MobData;
import com.bioxx.tfc.Core.Player.FoodStatsTFC;
import com.bioxx.tfc.Entities.EntityJavelin;
import com.bioxx.tfc.Items.ItemTFCArmor;
import com.bioxx.tfc.api.Enums.EnumDamageType;
import com.bioxx.tfc.api.Events.EntityArmorCalcEvent;
import com.bioxx.tfc.api.Interfaces.ICausesDamage;
import com.bioxx.tfc.api.Interfaces.IInnateArmor;

public class EntityDamageHandler
{
	@SubscribeEvent
	public void onEntityHurt(LivingHurtEvent event)
	{
		EntityLivingBase entity = event.entityLiving;
		if(entity instanceof EntityPlayer)
		{
			float curMaxHealth = (float)((EntityPlayer)entity).getEntityAttribute(SharedMonsterAttributes.maxHealth).getAttributeValue();
			float newMaxHealth = FoodStatsTFC.getMaxHealth((EntityPlayer)entity);
			float h = ((EntityPlayer)entity).getHealth();
			if(newMaxHealth != curMaxHealth)
				((EntityPlayer)entity).getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(newMaxHealth);
			if(newMaxHealth < h)
				((EntityPlayer)entity).setHealth(newMaxHealth);
		}

		if(event.source == DamageSource.onFire)
		{
			event.ammount = 50;
		}
		else if(event.source == DamageSource.fall)
		{
			float healthMod = TFC_Core.getEntityMaxHealth(entity)/1000f;
			event.ammount *= 80*healthMod;
		}
		else if(event.source == DamageSource.drown)
		{
			event.ammount = 50;
		}
		else if(event.source == DamageSource.lava)
		{
			event.ammount = 100;
		}
		else if(event.source == DamageSource.inWall)
		{
			event.ammount = 100;
		}
		else if(event.source == DamageSource.fallingBlock)
		{
			event.ammount = 100;
		}
		else if(event.source.isExplosion())
		{
			event.ammount *= 30;
		}
		else if (event.source == DamageSource.magic && entity.getHealth() > 25)
		{
			event.ammount = 25;
		}
		else if ("player".equals(event.source.damageType) || "mob".equals(event.source.damageType) || "arrow".equals(event.source.damageType))
		{
			event.ammount = applyArmorCalculations(entity, event.source, event.ammount);
			if ("arrow".equals(event.source.damageType))
			{
				Entity e = ((EntityDamageSourceIndirect)event.source).getSourceOfDamage();
				if(e instanceof EntityJavelin)
				{
					((EntityJavelin)e).setDamageTaken((short) (((EntityJavelin) e).damageTaken+10));
					if (((EntityJavelin) e).damageTaken >= ((EntityJavelin) e).pickupItem.getMaxDamage())
					{
						e.setDead();
					}
				}
			}
		}
	}

	protected int applyArmorCalculations(EntityLivingBase entity, DamageSource source, float originalDamage)
	{
		ItemStack[] armor = entity.getLastActiveItems();
		int pierceRating = 0;
		int slashRating = 0;
		int crushRating = 0;

		EntityArmorCalcEvent eventPre = new EntityArmorCalcEvent(entity, originalDamage, EntityArmorCalcEvent.EventType.PRE);
		MinecraftForge.EVENT_BUS.post(eventPre);
		float damage = eventPre.incomingDamage;

		if (!source.isUnblockable() && armor != null)
		{
			//1. Get Random Hit Location
			int location = getRandomSlot(entity.getRNG());

			//2. Get Armor Rating for armor in hit Location
			if(armor[location] != null && armor[location].getItem() instanceof ItemTFCArmor)
			{
				pierceRating = ((ItemTFCArmor)armor[location].getItem()).armorTypeTFC.getPiercingAR();
				slashRating = ((ItemTFCArmor)armor[location].getItem()).armorTypeTFC.getSlashingAR();
				crushRating = ((ItemTFCArmor)armor[location].getItem()).armorTypeTFC.getCrushingAR();
				if(entity instanceof IInnateArmor)
				{
					pierceRating += ((IInnateArmor)entity).getPierceArmor();
					slashRating += ((IInnateArmor)entity).getSlashArmor();
					crushRating += ((IInnateArmor) entity).getCrushArmor();
				}

				//3. Convert the armor rating to % damage reduction
				float pierceMult = getDamageReduction(pierceRating);
				float slashMult = getDamageReduction(slashRating);
				float crushMult = getDamageReduction(crushRating);

				//4. Reduce incoming damage
				damage = processDamageSource(source, damage, pierceMult,
						slashMult, crushMult);

				//5. Damage the armor that was hit
				armor[location].damageItem((int) processArmorDamage(armor[location], damage), entity);
			}
			else if (armor[location] == null || armor[location] != null && !(armor[location].getItem() instanceof ItemTFCArmor))
			{
				if(entity instanceof IInnateArmor)
				{
					pierceRating += ((IInnateArmor)entity).getPierceArmor();
					slashRating += ((IInnateArmor)entity).getSlashArmor();
					crushRating += ((IInnateArmor) entity).getCrushArmor();
				}
				//1. Convert the armor rating to % damage reduction
				float pierceMult = getDamageReduction(pierceRating);
				float slashMult = getDamageReduction(slashRating);
				float crushMult = getDamageReduction(crushRating);
				//4. Reduce incoming damage
				damage = processDamageSource(source, damage, pierceMult, slashMult, crushMult);

				//a. If the attack hits an unprotected head, it does 75% more damage
				//b. If the attack hits unprotected feet, it applies a slow to the player
				if(location == 3)
					damage *= 1.75f;
				else if(location == 0)
					entity.addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), 100, 1));
			}
			//6. Apply the damage to the player
			EntityArmorCalcEvent eventPost = new EntityArmorCalcEvent(entity, damage, EntityArmorCalcEvent.EventType.POST);
			MinecraftForge.EVENT_BUS.post(eventPost);
			//TerraFirmaCraft.log.info(entity.getClass()+", "+eventPre.incomingDamage+", "+eventPost.incomingDamage);
			float hasHealth = entity.getHealth();
			entity.setHealth(entity.getHealth()-eventPost.incomingDamage);
			entity.func_110142_aN().func_94547_a(source, hasHealth, eventPost.incomingDamage);
		}
		return 0;
	}

	private float processDamageSource(DamageSource source, float damage,
			float pierceMult, float slashMult, float crushMult)
	{
		EnumDamageType damageType = getDamageType(source);
		//4.2 Reduce the damage based upon the incoming Damage Type
		if(damageType == EnumDamageType.PIERCING)
		{
			damage *= pierceMult;
		}
		else if(damageType == EnumDamageType.SLASHING)
		{
			damage *= slashMult;
		}
		else if(damageType == EnumDamageType.CRUSHING)
		{
			damage *= crushMult;
		}
		else if(damageType == EnumDamageType.GENERIC)
		{
			damage *= (crushMult + slashMult + pierceMult) / 3 - 0.25;
		}
		return Math.max(0, damage);
	}

	private EnumDamageType getDamageType(DamageSource source) 
	{
		//4.1 Determine the source of the damage and get the appropriate Damage Type
		if(source.getSourceOfDamage() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)source.getSourceOfDamage();
			if(player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ICausesDamage)
			{
				return ((ICausesDamage)player.getCurrentEquippedItem().getItem()).getDamageType();
			}
		}

		if(source.getSourceOfDamage() instanceof EntityLiving)
		{
			EntityLiving el = (EntityLiving)source.getSourceOfDamage();
			if(el.getHeldItem() != null && el.getHeldItem().getItem() instanceof ICausesDamage)
			{
				return ((ICausesDamage)el.getHeldItem().getItem()).getDamageType();
			}
		}

		if(source.getSourceOfDamage() instanceof ICausesDamage)
		{
			return ((ICausesDamage)source.getSourceOfDamage()).getDamageType();
		}

		return EnumDamageType.GENERIC;
	}

	private int getRandomSlot(Random rand)
	{
		int chance = rand.nextInt(100);
		if(chance < 10)
			return 3;//Helm
		else if(chance < 20)
			return 0;//Feet
		else if(chance < 80)
			return 2;//Chest
		else
			return 1;//Legs
	}

	private float processArmorDamage(ItemStack armor, float baseDamage)
	{
		if(armor.hasTagCompound())
		{
			NBTTagCompound nbt = armor.getTagCompound();
			if(nbt.hasKey("armorReductionBuff"))
			{
				float reductBuff = nbt.getByte("armorReductionBuff")/100f;
				return baseDamage - (baseDamage * reductBuff);
			}
		}
		return baseDamage;
	}

	/**
	 * @param armorRating Armor Rating supplied by the armor
	 * @return Multiplier for damage reduction e.g. damage * multiplier = final damage
	 */
	protected float getDamageReduction(int armorRating)
	{
		if(armorRating == -1000)
			armorRating=-999;
		return 1000f / (1000f + armorRating);
	}

	@SubscribeEvent
	public void onAttackEntity(AttackEntityEvent event)
	{
		if(event.entityLiving.worldObj.isRemote)
			return;

		EntityLivingBase attacker = event.entityLiving;
		EntityPlayer player = event.entityPlayer;
		Entity target = event.target;
		ItemStack stack = attacker.getEquipmentInSlot(0);
		if (stack != null && stack.getItem().onLeftClickEntity(stack, player, target))
			return;

		if (target.canAttackWithItem())
		{
			if (!target.hitByEntity(target))
			{
				float damageAmount = TFC_MobData.STEVE_DAMAGE;
				if(stack != null)
				{
					damageAmount = (float)player.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
					//player.addChatMessage("Damage: " + i);
					if(damageAmount == 1.0f)
					{
						damageAmount = TFC_MobData.STEVE_DAMAGE;
						//i = player.inventory.getCurrentItem().getItem().getDamageVsEntity(target, player.inventory.getCurrentItem());
					}
				}

				if (player.isPotionActive(Potion.damageBoost))
					damageAmount += 3 << player.getActivePotionEffect(Potion.damageBoost).getAmplifier();

				if (player.isPotionActive(Potion.weakness))
					damageAmount -= 2 << player.getActivePotionEffect(Potion.weakness).getAmplifier();

				int knockback = 0;
				float enchantmentDamage = 0;

				if (target instanceof EntityLiving)
				{
					enchantmentDamage = EnchantmentHelper.getEnchantmentModifierLiving(player, (EntityLiving) target);
					knockback += EnchantmentHelper.getKnockbackModifier(player, (EntityLiving) target);
				}

				if (player.isSprinting())
					++knockback;

				if (damageAmount > 0 || enchantmentDamage > 0)
				{
					boolean criticalHit = player.fallDistance > 0.0F && !player.onGround && 
							!player.isOnLadder() && !player.isInWater() && 
							!player.isPotionActive(Potion.blindness) && player.ridingEntity == null && 
							target instanceof EntityLiving;

					if (criticalHit && damageAmount > 0)
						damageAmount += event.entity.worldObj.rand.nextInt((int) (damageAmount / 2 + 2));

					damageAmount += enchantmentDamage;
					boolean onFire = false;
					int fireAspect = EnchantmentHelper.getFireAspectModifier(player);

					if (target instanceof EntityLiving && fireAspect > 0 && !target.isBurning())
					{
						onFire = true;
						target.setFire(1);
					}

					boolean entityAttacked = target.attackEntityFrom(DamageSource.causePlayerDamage(player), damageAmount);

					if (entityAttacked)
					{
						if (knockback > 0)
						{
							target.addVelocity(-MathHelper.sin(player.rotationYaw * (float)Math.PI / 180.0F) * knockback * 0.5F, 0.1D, 
									MathHelper.cos(player.rotationYaw * (float)Math.PI / 180.0F) * knockback * 0.5F);
							player.motionX *= 0.6D;
							player.motionZ *= 0.6D;
							player.setSprinting(false);
						}

						if (criticalHit)
							player.onCriticalHit(target);

						if (enchantmentDamage > 0)
							player.onEnchantmentCritical(target);

						if (damageAmount >= 18)
							player.triggerAchievement(AchievementList.overkill);

						player.setLastAttacker(target);

						if (target instanceof EntityLiving)
							target.attackEntityFrom(DamageSource.causeThornsDamage(attacker), damageAmount);
					}

					ItemStack itemstack = player.getCurrentEquippedItem();
					Object object = target;

					if (target instanceof EntityDragonPart)
					{
						IEntityMultiPart ientitymultipart = ((EntityDragonPart)target).entityDragonObj;
						if (ientitymultipart instanceof EntityLiving)
							object = ientitymultipart;
					}

					if (itemstack != null && object instanceof EntityLiving)
					{
						itemstack.hitEntity((EntityLiving)object, player);
						if (itemstack.stackSize <= 0)
							player.destroyCurrentEquippedItem();
					}

					if (target instanceof EntityLivingBase)
					{
						player.addStat(StatList.damageDealtStat,Math.round(damageAmount * 10.0f));
						if (fireAspect > 0 && entityAttacked)
							target.setFire(fireAspect * 4);
						else if (onFire)
							target.extinguish();
					}

					player.addExhaustion(0.3F);
				}
			}
		}
		event.setCanceled(true);
	}
}
