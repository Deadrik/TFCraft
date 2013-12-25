package TFC.Entities.Mobs;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import TFC.TFCItems;
import TFC.API.ICausesDamage;
import TFC.API.Enums.EnumDamageType;
import TFC.Core.TFC_MobData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityZombieTFC extends EntityZombie implements ICausesDamage
{
	private int field_82234_d = 0;

	public EntityZombieTFC(World par1World)
	{
		super(par1World);
	}
	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setAttribute(TFC_MobData.ZombieDamage);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(TFC_MobData.ZombieHealth);//MaxHealth
	}

	/**
	 * Returns the current armor value as determined by a call to InventoryPlayer.getTotalArmorValue
	 */
	@Override
	public int getTotalArmorValue()
	{
		int var1 = super.getTotalArmorValue() + 2;

		if (var1 > 20)
		{
			var1 = 20;
		}

		return var1;
	}

	@Override
	protected void dropRareDrop(int par1)
	{
		switch (this.rand.nextInt(3))
		{
		case 0:
			this.dropItem(TFCItems.WroughtIronIngot.itemID, 1);
			break;
		case 1:
			this.dropItem(TFCItems.Carrot.itemID, 1);
			break;
		case 2:
			this.dropItem(TFCItems.Potato.itemID, 1);
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

		if (this.rand.nextFloat() < (this.worldObj.difficultySetting == 3 ? 0.05F : 0.01F))
		{
			int var1 = this.rand.nextInt(3);

			if (var1 == 0)
			{
				this.setCurrentItemOrArmor(0, new ItemStack(TFCItems.BronzePick));
			}
			else
			{
				this.setCurrentItemOrArmor(0, new ItemStack(TFCItems.BronzeShovel));
			}
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
		{
			this.worldObj.playSoundEffect(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, "mob.zombie.remedy", 1.0F + this.rand.nextFloat(), this.rand.nextFloat() * 0.7F + 0.3F);
		}
		else
		{
			super.handleHealthUpdate(par1);
		}
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
                if ((par1DamageSource == DamageSource.anvil || par1DamageSource == DamageSource.fallingBlock) && this.getCurrentItemOrArmor(4) != null)
                {
                    this.getCurrentItemOrArmor(4).damageItem((int)(par2 * 4.0F + this.rand.nextFloat() * par2 * 2.0F), this);
                    par2 *= 0.75F;
                }

                this.limbSwingAmount = 1.5F;
                boolean flag = true;

                if ((float)this.hurtResistantTime > (float)this.maxHurtResistantTime / 2.0F)
                {
                    if (par2 <= this.lastDamage)
                    {
                        return false;
                    }

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
                    {
                        this.setRevengeTarget((EntityLivingBase)entity);
                    }

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
                    {
                        this.setBeenAttacked();
                    }

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
                    {
                        this.playSound(this.getDeathSound(), this.getSoundVolume(), this.getSoundPitch());
                    }

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
}
