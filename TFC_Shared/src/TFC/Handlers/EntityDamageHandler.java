package TFC.Handlers;

import java.util.Iterator;
import java.util.List;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentThorns;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import TFC.API.ICausesDamage;
import TFC.API.Enums.EnumDamageType;
import TFC.Items.ItemCustomArmor;

public class EntityDamageHandler
{
	@ForgeSubscribe
	public void onEntityHurt(LivingHurtEvent event) 
	{
		EntityLiving entity = event.entityLiving;


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
		else if(event.source.isExplosion())
		{
			event.ammount *= 30;
		}
		else if(event.source.damageType == "player" || event.source.damageType == "mob")
		{
			applyArmorCalculations(entity, event.source, event.ammount);
			event.ammount = 0;
		}
	}

	protected int applyArmorCalculations(EntityLiving entity, DamageSource source, int originalDamage)
	{
		ItemStack[] armor = entity.getLastActiveItems();
		int pierceRating = 0;
		int slashRating = 0;
		int crushRating = 0;
		int damage = originalDamage;
		
		if (!source.isUnblockable() && armor != null)
		{
			//1. Get Random Hit Location
			int location = entity.getRNG().nextInt(4);
			
			//2. Get Armor Rating for armor in hit Location
			if(armor[location] != null && armor[location].getItem() instanceof ItemCustomArmor)
			{
				pierceRating = ((ItemCustomArmor)armor[location].getItem()).ArmorType.getPiercingAR();
				slashRating = ((ItemCustomArmor)armor[location].getItem()).ArmorType.getSlashingAR();
				crushRating = ((ItemCustomArmor)armor[location].getItem()).ArmorType.getCrushingAR();
				
				//3. Convert the armor rating to % damage reduction
				float pierceMult = getDamageReduction(pierceRating);
				float slashMult = getDamageReduction(slashRating);
				float crushMult = getDamageReduction(crushRating);
				
				//4. Reduce incoming damage
				EnumDamageType damageType = null;
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
			}
			//5. Apply the damage to the player
			entity.setEntityHealth(entity.getHealth()-damage);
			//6. Damage the armor that was hit
			armor[location].damageItem(originalDamage, entity);


		}

		return 0;
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
		EntityLiving par1Entity = event.entityLiving;
		ItemStack stack = par1Entity.getCurrentItemOrArmor(0);
        if (stack != null && stack.getItem().onLeftClickEntity(stack, event.entityPlayer, event.target))
        {
            return;
        }
        if (par1Entity.canAttackWithItem())
        {
            if (!event.entity.func_85031_j(event.entityPlayer))
            {
                int i = event.entityPlayer.inventory.getDamageVsEntity(par1Entity);

                if (event.entityPlayer.isPotionActive(Potion.damageBoost))
                {
                    i += 3 << event.entityPlayer.getActivePotionEffect(Potion.damageBoost).getAmplifier();
                }

                if (event.entityPlayer.isPotionActive(Potion.weakness))
                {
                    i -= 2 << event.entityPlayer.getActivePotionEffect(Potion.weakness).getAmplifier();
                }

                int j = 0;
                int k = 0;

                if (par1Entity instanceof EntityLiving)
                {
                    k = EnchantmentHelper.getEnchantmentModifierLiving(event.entityPlayer, par1Entity);
                    j += EnchantmentHelper.getKnockbackModifier(event.entityPlayer, par1Entity);
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
                    		par1Entity instanceof EntityLiving;

                    if (flag && i > 0)
                    {
                        i += event.entity.worldObj.rand.nextInt(i / 2 + 2);
                    }

                    i += k;
                    boolean flag1 = false;
                    int l = EnchantmentHelper.getFireAspectModifier(event.entityPlayer);

                    if (par1Entity instanceof EntityLiving && l > 0 && !par1Entity.isBurning())
                    {
                        flag1 = true;
                        par1Entity.setFire(1);
                    }

                    boolean flag2 = par1Entity.attackEntityFrom(DamageSource.causePlayerDamage(event.entityPlayer), i);

                    if (flag2)
                    {
                        if (j > 0)
                        {
                            par1Entity.addVelocity(-MathHelper.sin(event.entityPlayer.rotationYaw * (float)Math.PI / 180.0F) * j * 0.5F, 0.1D, 
                            		MathHelper.cos(event.entityPlayer.rotationYaw * (float)Math.PI / 180.0F) * j * 0.5F);
                            event.entityPlayer.motionX *= 0.6D;
                            event.entityPlayer.motionZ *= 0.6D;
                            event.entityPlayer.setSprinting(false);
                        }

                        if (flag)
                        {
                        	event.entityPlayer.onCriticalHit(par1Entity);
                        }

                        if (k > 0)
                        {
                        	event.entityPlayer.onEnchantmentCritical(par1Entity);
                        }

                        if (i >= 18)
                        {
                        	event.entityPlayer.triggerAchievement(AchievementList.overkill);
                        }

                        event.entityPlayer.setLastAttackingEntity(par1Entity);

                        if (par1Entity instanceof EntityLiving)
                        {
                            EnchantmentThorns.func_92096_a(event.entityPlayer, par1Entity, event.entity.worldObj.rand);
                        }
                    }

                    ItemStack itemstack = event.entityPlayer.getCurrentEquippedItem();
                    Object object = par1Entity;

                    if (event.entity instanceof EntityDragonPart)
                    {
                        IEntityMultiPart ientitymultipart = ((EntityDragonPart)event.entity).entityDragonObj;

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

                    if (par1Entity instanceof EntityLiving)
                    {
                        if (par1Entity.isEntityAlive())
                        {
                        	alertWolves(event.entityPlayer,par1Entity, true);
                        }

                        event.entityPlayer.addStat(StatList.damageDealtStat, i);

                        if (l > 0 && flag2)
                        {
                            par1Entity.setFire(l * 4);
                        }
                        else if (flag1)
                        {
                            par1Entity.extinguish();
                        }
                    }

                    event.entityPlayer.addExhaustion(0.3F);
                }
            }
        }
        event.setCanceled(true);
	}
	
	protected void alertWolves(EntityPlayer player, EntityLiving par1EntityLiving, boolean par2)
    {
        if (!(par1EntityLiving instanceof EntityCreeper) && !(par1EntityLiving instanceof EntityGhast))
        {
            if (par1EntityLiving instanceof EntityWolf)
            {
                EntityWolf entitywolf = (EntityWolf)par1EntityLiving;

                if (entitywolf.isTamed() && player.username.equals(entitywolf.getOwnerName()))
                {
                    return;
                }
            }

            if (!(par1EntityLiving instanceof EntityPlayer) || player.func_96122_a((EntityPlayer)par1EntityLiving))
            {
                List list = player.worldObj.getEntitiesWithinAABB(EntityWolf.class, AxisAlignedBB.getAABBPool().getAABB(player.posX, player.posY, player.posZ, player.posX + 1.0D, player.posY + 1.0D, player.posZ + 1.0D).expand(16.0D, 4.0D, 16.0D));
                Iterator iterator = list.iterator();

                while (iterator.hasNext())
                {
                    EntityWolf entitywolf1 = (EntityWolf)iterator.next();

                    if (entitywolf1.isTamed() && entitywolf1.getEntityToAttack() == null && player.username.equals(entitywolf1.getOwnerName()) && (!par2 || !entitywolf1.isSitting()))
                    {
                        entitywolf1.setSitting(false);
                        entitywolf1.setTarget(par1EntityLiving);
                    }
                }
            }
        }
    }
}
