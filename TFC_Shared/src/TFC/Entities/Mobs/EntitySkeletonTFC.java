package TFC.Entities.Mobs;

import TFC.*;
import TFC.Entities.EntityArrowTFC;
import java.util.Calendar;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.feature.*;

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
        return Item.arrow.itemID;
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
                this.dropItem(Item.coal.itemID, 1);
            }
        }
        else
        {
            var3 = this.rand.nextInt(3 + par2);

            for (var4 = 0; var4 < var3; ++var4)
            {
                this.dropItem(Item.arrow.itemID, 1);
            }
        }

        var3 = this.rand.nextInt(3 + par2);

        for (var4 = 0; var4 < var3; ++var4)
        {
            this.dropItem(Item.bone.itemID, 1);
        }
    }
    @Override
    protected void dropRareDrop(int par1)
    {
        if (this.getSkeletonType() == 1)
        {
            this.entityDropItem(new ItemStack(Item.skull.itemID, 1, 1), 0.0F);
        }
    }
    @Override
    protected void addRandomArmor()
    {
        super.addRandomArmor();
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
            this.addRandomArmor();
            this.func_82162_bC();
        }

        this.func_98053_h(this.rand.nextFloat() < pickUpLootProability[this.worldObj.difficultySetting]);

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
    public void attackEntityWithRangedAttack(EntityLiving par1EntityLiving,float par2)
    {
    	EntityArrowTFC var2 = new EntityArrowTFC(this.worldObj, this, par1EntityLiving, 1.6F, 12.0F);
        int var3 = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, this.getHeldItem());
        int var4 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, this.getHeldItem());
        var2.setDamage((double)(par2 * 2.0F) + this.rand.nextGaussian() * 0.25D + (double)((float)this.worldObj.difficultySetting * 0.11F));

        
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
