package TFC.Entities.Mobs;

import TFC.*;
import TFC.Entities.EntityArrowTFC;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import java.util.Calendar;

import net.minecraft.src.Block;
import net.minecraft.src.DamageSource;
import net.minecraft.src.Enchantment;
import net.minecraft.src.EnchantmentHelper;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityAIArrowAttack;
import net.minecraft.src.EntityAIAttackOnCollide;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntitySkeleton;
import net.minecraft.src.EnumCreatureAttribute;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Potion;
import net.minecraft.src.PotionEffect;
import net.minecraft.src.World;
import net.minecraft.src.WorldProviderHell;

public class EntitySkeletonTFC extends EntitySkeleton
{
    public EntitySkeletonTFC(World par1World)
    {
        super(par1World);
    }
    @Override
    public int getMaxHealth()
    {
        return 1000;
    }
    @Override
    public void onEntityUpdate()
    {
    	super.onEntityUpdate();
    	
    	if (this.isBurning())
        {
            this.attackEntityFrom(DamageSource.onFire, 50);
        }
    }
    @Override
    public boolean attackEntityAsMob(Entity par1Entity)
    {
        if (super.attackEntityAsMob(par1Entity))
        {
            if (this.getSkeletonType() == 1 && par1Entity instanceof EntityLiving)
            {
                ((EntityLiving)par1Entity).addPotionEffect(new PotionEffect(Potion.wither.id, 200));
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public int getAttackStrength(Entity par1Entity)
    {
        if (this.getSkeletonType() == 1)
        {
            ItemStack var2 = this.getHeldItem();
            int var3 = 200;

            if (var2 != null)
            {
                var3 += var2.getDamageVsEntity(this);
            }

            return var3;
        }
        else
        {
            return super.getAttackStrength(par1Entity);
        }
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    @Override
    protected int getDropItemId()
    {
        return Item.arrow.shiftedIndex;
    }

    /**
     * Drop 0-2 items of this living's type
     */
    @Override
    protected void dropFewItems(boolean par1, int par2)
    {
        int var3;
        int var4;

        if (this.getSkeletonType() == 1)
        {
            var3 = this.rand.nextInt(3 + par2) - 1;

            for (var4 = 0; var4 < var3; ++var4)
            {
                this.dropItem(Item.coal.shiftedIndex, 1);
            }
        }
        else
        {
            var3 = this.rand.nextInt(3 + par2);

            for (var4 = 0; var4 < var3; ++var4)
            {
                this.dropItem(Item.arrow.shiftedIndex, 1);
            }
        }

        var3 = this.rand.nextInt(3 + par2);

        for (var4 = 0; var4 < var3; ++var4)
        {
            this.dropItem(Item.bone.shiftedIndex, 1);
        }
    }
    @Override
    protected void dropRareDrop(int par1)
    {
        if (this.getSkeletonType() == 1)
        {
            this.entityDropItem(new ItemStack(Item.skull.shiftedIndex, 1, 1), 0.0F);
        }
    }
    @Override
    protected void func_82164_bB()
    {
        super.func_82164_bB();
        this.setCurrentItemOrArmor(0, new ItemStack(Item.bow));
    }

    @SideOnly(Side.CLIENT)

    /**
     * Returns the texture's file path as a String.
     */
    @Override
    public String getTexture()
    {
        return this.getSkeletonType() == 1 ? "/mob/skeleton_wither.png" : super.getTexture();
    }
    @Override
    public void initCreature()
    {
        if (this.worldObj.provider instanceof WorldProviderHell && this.getRNG().nextInt(5) > 0)
        {
            this.tasks.addTask(4, new EntityAIAttackOnCollide(this, EntityPlayer.class, this.moveSpeed, false));
            this.setSkeletonType(1);
            this.setCurrentItemOrArmor(0, new ItemStack(TFCItems.StoneKnife));
        }
        else
        {
            this.tasks.addTask(4, new EntityAIArrowAttack(this, this.moveSpeed, 60, 10.0F));
            this.func_82164_bB();
            this.func_82162_bC();
        }

        this.canPickUpLoot = this.rand.nextFloat() < field_82181_as[this.worldObj.difficultySetting];

        if (this.getCurrentItemOrArmor(4) == null)
        {
            Calendar var1 = this.worldObj.getCurrentDate();

            if (var1.get(2) + 1 == 10 && var1.get(5) == 31 && this.rand.nextFloat() < 0.25F)
            {
                this.setCurrentItemOrArmor(4, new ItemStack(this.rand.nextFloat() < 0.1F ? Block.pumpkinLantern : Block.pumpkin));
                this.equipmentDropChances[4] = 0.0F;
            }
        }
    }
    @Override
    public void attackEntityWithRangedAttack(EntityLiving par1EntityLiving)
    {
    	EntityArrowTFC var2 = new EntityArrowTFC(this.worldObj, this, par1EntityLiving, 1.6F, 12.0F);
        int var3 = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, this.getHeldItem());
        int var4 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, this.getHeldItem());

        if (var3 > 0)
        {
            var2.setDamage(var2.getDamage() + (double)var3 * 0.5D + 0.5D);
        }

        if (var4 > 0)
        {
            var2.setKnockbackStrength(var4);
        }

        if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, this.getHeldItem()) > 0 || this.getSkeletonType() == 1)
        {
            var2.setFire(100);
        }

        this.worldObj.playSoundAtEntity(this, "random.bow", 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        this.worldObj.spawnEntityInWorld(var2);
    }
}
