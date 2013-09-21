package TFC.Handlers;

import java.util.Random;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentThorns;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
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
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import TFC.API.ICausesDamage;
import TFC.API.Enums.EnumDamageType;
import TFC.API.Events.EntityArmorCalcEvent;
import TFC.Core.TFC_MobData;
import TFC.Entities.EntityJavelin;
import TFC.Items.ItemTFCArmor;

public class EntityDamageHandler
{
	@ForgeSubscribe
	public void onEntityHurt(LivingHurtEvent event) 
	{
		EntityLivingBase entity = event.entityLiving;


		if(event.source == DamageSource.onFire)
		{
			event.ammount = 50;
		}
		else if(event.source == DamageSource.fall)
		{
			event.ammount *= 80;
		}
		else if(event.source == DamageSource.drown)
		{
			event.ammount = 50;
		}
		else if(event.source == DamageSource.lava)
		{
			event.ammount = 100;
		}
		else if(event.source == DamageSource.starve)
		{
			event.ammount *= 10;
		}
		else if(event.source == DamageSource.inWall)
		{
			event.ammount = 100;
		}
		else if(event.source.isExplosion())
		{
			event.ammount *= 30;
		}
		else if(event.source.damageType == "player" || event.source.damageType == "mob" || event.source.damageType == "arrow")
		{
			event.ammount = applyArmorCalculations(entity, event.source, event.ammount);
			if(event.source.damageType == "arrow")
			{
				Entity e = ((EntityDamageSourceIndirect)event.source).getSourceOfDamage();
				if(e instanceof EntityJavelin)
				{
					((EntityJavelin)e).setDamageTaken((short) (((EntityJavelin) e).damageTaken+10));

					if(((EntityJavelin)e).damageTaken >= Item.itemsList[((EntityJavelin)e).itemID].getMaxDamage()) 
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
				pierceRating = ((ItemTFCArmor)armor[location].getItem()).ArmorType.getPiercingAR();
				slashRating = ((ItemTFCArmor)armor[location].getItem()).ArmorType.getSlashingAR();
				crushRating = ((ItemTFCArmor)armor[location].getItem()).ArmorType.getCrushingAR();

				//3. Convert the armor rating to % damage reduction
				float pierceMult = getDamageReduction(pierceRating);
				float slashMult = getDamageReduction(slashRating);
				float crushMult = getDamageReduction(crushRating);

				//4. Reduce incoming damage
				EnumDamageType damageType = EnumDamageType.GENERIC;
				//4.1 Determine the source of the damage and get the appropriate Damage Type
				if(source.getSourceOfDamage() instanceof EntityPlayer)
				{
					EntityPlayer player = (EntityPlayer)source.getSourceOfDamage();
					if(player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ICausesDamage)
					{
						damageType = ((ICausesDamage)player.getCurrentEquippedItem().getItem()).GetDamageType();
					}
				}
				else if(source.getSourceOfDamage() instanceof ICausesDamage)
				{
					damageType = ((ICausesDamage)source.getSourceOfDamage()).GetDamageType();
				}
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

				//5. Damage the armor that was hit
				armor[location].damageItem((int) processArmorDamage(armor[location], damage), entity);
			}
			else if(armor[location] == null || (armor[location] != null && !(armor[location].getItem() instanceof ItemTFCArmor)))
			{
				//a. If the attack hits an unprotected head, it does 75% more damage
				//b. If the attack hits unprotected feet, it applies a slow to the player
				if(location == 3)
				{
					damage *= 1.75f;
				}
				else if(location == 0)
				{
					entity.addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), 100, 1));
				}
			}
			//6. Apply the damage to the player
			EntityArmorCalcEvent eventPost = new EntityArmorCalcEvent(entity, damage, EntityArmorCalcEvent.EventType.POST);
			MinecraftForge.EVENT_BUS.post(eventPost);
			//System.out.println(entity.getClass()+", "+eventPre.incomingDamage+", "+eventPost.incomingDamage);
			entity.setHealth(entity.getMaxHealth()-eventPost.incomingDamage);
		}

		return 0;
	}

	private int getRandomSlot(Random rand)
	{
		int chance = rand.nextInt(100);
		if(chance < 10) {
			return 3;//Helm
		} else if(chance < 20) {
			return 0;//Feet
		} else if(chance < 80) {
			return 2;//Chest
		}
		else {
			return 1;//Legs
		}
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
	 * @param AR Armor Rating supplied by the armor
	 * @return Multiplier for damage reduction e.g. damage * multiplier = final damage
	 */
	protected float getDamageReduction(int AR)
	{
		return (1000f / (1000f + AR));
	}

	@ForgeSubscribe
	public void onAttackEntity(AttackEntityEvent event)
	{
		if(event.entityLiving.worldObj.isRemote)
		{
			return;
		}

		EntityLivingBase attacker = event.entityLiving;
		Entity target = event.target;
		ItemStack stack = attacker.getCurrentItemOrArmor(0);
		if (stack != null && stack.getItem().onLeftClickEntity(stack, event.entityPlayer, event.target))
		{
			return;
		}
		if (target.canAttackWithItem())
		{
			if (!target.hitByEntity(target))
			{
				float i = TFC_MobData.SteveDamage;
				if(stack != null) 
				{
					i = (float)event.entityPlayer.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
					//event.entityPlayer.addChatMessage("Damage: " + i);
					if(i == 1.0f)
					{
						i = TFC_MobData.SteveDamage;
						//i = event.entityPlayer.inventory.getCurrentItem().getItem().getDamageVsEntity(target, event.entityPlayer.inventory.getCurrentItem());
					}
				}

				if (event.entityPlayer.isPotionActive(Potion.damageBoost))
				{
					i += 3 << event.entityPlayer.getActivePotionEffect(Potion.damageBoost).getAmplifier();
				}

				if (event.entityPlayer.isPotionActive(Potion.weakness))
				{
					i -= 2 << event.entityPlayer.getActivePotionEffect(Potion.weakness).getAmplifier();
				}

				int j = 0;
				float k = 0;

				if (target instanceof EntityLiving)
				{
					k = EnchantmentHelper.getEnchantmentModifierLiving(event.entityPlayer, (EntityLiving) target);
					j += EnchantmentHelper.getKnockbackModifier(event.entityPlayer, (EntityLiving) target);
				}

				if (event.entityPlayer.isSprinting())
				{
					++j;
				}

				if (i > 0 || k > 0)
				{
					boolean flag = event.entityPlayer.fallDistance > 0.0F && !event.entityPlayer.onGround && 
							!event.entityPlayer.isOnLadder() && !event.entityPlayer.isInWater() && 
							!event.entityPlayer.isPotionActive(Potion.blindness) && event.entityPlayer.ridingEntity == null && 
							target instanceof EntityLiving;

					if (flag && i > 0)
					{
						i += event.entity.worldObj.rand.nextInt((int) (i / 2 + 2));
					}

					i += k;
					boolean flag1 = false;
					int l = EnchantmentHelper.getFireAspectModifier(event.entityPlayer);

					if (target instanceof EntityLiving && l > 0 && !target.isBurning())
					{
						flag1 = true;
						target.setFire(1);
					}

					boolean flag2 = target.attackEntityFrom(DamageSource.causePlayerDamage(event.entityPlayer), i);

					if (flag2)
					{
						if (j > 0)
						{
							target.addVelocity(-MathHelper.sin(event.entityPlayer.rotationYaw * (float)Math.PI / 180.0F) * j * 0.5F, 0.1D, 
									MathHelper.cos(event.entityPlayer.rotationYaw * (float)Math.PI / 180.0F) * j * 0.5F);
							event.entityPlayer.motionX *= 0.6D;
							event.entityPlayer.motionZ *= 0.6D;
							event.entityPlayer.setSprinting(false);
						}

						if (flag)
						{
							event.entityPlayer.onCriticalHit(target);
						}

						if (k > 0)
						{
							event.entityPlayer.onEnchantmentCritical(target);
						}

						if (i >= 18)
						{
							event.entityPlayer.triggerAchievement(AchievementList.overkill);
						}

						event.entityPlayer.setLastAttacker(target);

						if (target instanceof EntityLiving)
						{
							EnchantmentThorns.func_92096_a(event.entityPlayer, (EntityLiving) target, event.entity.worldObj.rand);
						}
					}

					ItemStack itemstack = event.entityPlayer.getCurrentEquippedItem();
					Object object = target;

					if (target instanceof EntityDragonPart)
					{
						IEntityMultiPart ientitymultipart = ((EntityDragonPart)target).entityDragonObj;

						if (ientitymultipart != null && ientitymultipart instanceof EntityLiving)
						{
							object = ientitymultipart;
						}
					}

					if (itemstack != null && object instanceof EntityLiving)
					{
						itemstack.hitEntity((EntityLiving)object, event.entityPlayer);

						if (itemstack.stackSize <= 0)
						{
							event.entityPlayer.destroyCurrentEquippedItem();
						}
					}

					if (target instanceof EntityLivingBase)
					{
						event.entityPlayer.addStat(StatList.damageDealtStat,Math.round(i * 10.0f));

						if (l > 0 && flag2)
						{
							target.setFire(l * 4);
						}
						else if (flag1)
						{
							target.extinguish();
						}
					}

					event.entityPlayer.addExhaustion(0.3F);
				}
			}
		}
		event.setCanceled(true);
	}
}
