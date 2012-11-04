package TFC.Entities.Mobs;

import TFC.*;
import net.minecraft.src.DamageSource;
import net.minecraft.src.Entity;
import net.minecraft.src.EntitySilverfish;
import net.minecraft.src.EnumCreatureAttribute;
import net.minecraft.src.World;

public class EntitySilverfishTFC extends EntitySilverfish
{
    public EntitySilverfishTFC(World par1World)
    {
        super(par1World);
    }

    @Override
    public int getMaxHealth()
    {
        return 400;
    }

    /**
     * Basic mob attack. Default to touch of death in EntityCreature. Overridden by each mob to define their attack.
     */
    @Override
    protected void attackEntity(Entity par1Entity, float par2)
    {
        if (this.attackTime <= 0 && par2 < 1.2F && par1Entity.boundingBox.maxY > this.boundingBox.minY && par1Entity.boundingBox.minY < this.boundingBox.maxY)
        {
            this.attackTime = 20;
            par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), this.getAttackStrength(par1Entity));
        }
    }

    @Override
    public int getAttackStrength(Entity par1Entity)
    {
        return 50;
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.ARTHROPOD;
    }
}
