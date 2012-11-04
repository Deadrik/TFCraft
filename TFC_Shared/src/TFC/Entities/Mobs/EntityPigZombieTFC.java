package TFC.Entities.Mobs;

import TFC.*;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import java.util.Iterator;
import java.util.List;

import net.minecraft.src.DamageSource;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class EntityPigZombieTFC extends EntityZombieTFC
{
    /** Above zero if this PigZombie is Angry. */
    private int angerLevel = 0;

    /** A random delay until this PigZombie next makes a sound. */
    private int randomSoundDelay = 0;

    public EntityPigZombieTFC(World par1World)
    {
        super(par1World);
    }


    /**
     * Called when the entity is attacked.
     */
    @Override
    public boolean attackEntityFrom(DamageSource par1DamageSource, int par2)
    {
        Entity var3 = par1DamageSource.getEntity();

        if (var3 instanceof EntityPlayer)
        {
            List var4 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(32.0D, 32.0D, 32.0D));
            Iterator var5 = var4.iterator();

            while (var5.hasNext())
            {
                Entity var6 = (Entity)var5.next();

                if (var6 instanceof EntityPigZombieTFC)
                {
                    EntityPigZombieTFC var7 = (EntityPigZombieTFC)var6;
                    var7.becomeAngryAt(var3);
                }
            }

            this.becomeAngryAt(var3);
        }

        return super.attackEntityFrom(par1DamageSource, par2);
    }

    /**
     * Causes this PigZombie to become angry at the supplied Entity (which will be a player).
     */
    private void becomeAngryAt(Entity par1Entity)
    {
        this.entityToAttack = par1Entity;
        this.angerLevel = 400 + this.rand.nextInt(400);
        this.randomSoundDelay = this.rand.nextInt(40);
    }

    /**
     * Drop 0-2 items of this living's type
     */
    @Override
    protected void dropFewItems(boolean par1, int par2)
    {
        int var3 = this.rand.nextInt(2 + par2);
        int var4;

        for (var4 = 0; var4 < var3; ++var4)
        {
            this.dropItem(Item.rottenFlesh.shiftedIndex, 1);
        }

        var3 = this.rand.nextInt(2 + par2);

        for (var4 = 0; var4 < var3; ++var4)
        {
            this.dropItem(Item.goldNugget.shiftedIndex, 1);
        }
    }
    @Override
    protected void dropRareDrop(int par1)
    {
        this.dropItem(Item.ingotGold.shiftedIndex, 1);
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    @Override
    protected int getDropItemId()
    {
        return Item.rottenFlesh.shiftedIndex;
    }

    @Override
    protected void func_82164_bB()
    {
        this.setCurrentItemOrArmor(0, new ItemStack(Item.swordGold));
    }
    
    @Override
    public int getAttackStrength(Entity par1Entity)
    {
        ItemStack var2 = this.getHeldItem();
        int var3 = 250;

        if (var2 != null)
        {
            var3 += var2.getDamageVsEntity(this);
        }

        return var3;
    }
}
