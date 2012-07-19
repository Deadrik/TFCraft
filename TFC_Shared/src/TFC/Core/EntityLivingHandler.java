package TFC.Core;

import java.util.ArrayList;

import TFC.Entities.EntitySheepTFC;

import net.minecraft.src.DamageSource;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntitySheep;
import net.minecraft.src.World;
import net.minecraft.src.forge.IEntityLivingHandler;

public class EntityLivingHandler implements IEntityLivingHandler
{

    @Override
    public boolean onEntityLivingSpawn(EntityLiving entity, World world,
            float x, float y, float z)
    {
        if (entity instanceof EntitySheepTFC)
        {
            ((EntitySheepTFC)entity).setFleeceColor(EntitySheepTFC.getRandomFleeceColor(world.rand));
        }
        return false;
    }

    @Override
    public boolean onEntityLivingDeath(EntityLiving entity, DamageSource killer)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onEntityLivingSetAttackTarget(EntityLiving entity,
            EntityLiving target)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean onEntityLivingAttacked(EntityLiving entity,
            DamageSource attack, int damage)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onEntityLivingJump(EntityLiving entity)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean onEntityLivingFall(EntityLiving entity, float distance)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean onEntityLivingUpdate(EntityLiving entity)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int onEntityLivingHurt(EntityLiving entity, DamageSource source,
            int damage)
    {
        // TODO Auto-generated method stub
        return damage;
    }

    @Override
    public void onEntityLivingDrops(EntityLiving entity, DamageSource source,
            ArrayList<EntityItem> drops, int lootingLevel, boolean recentlyHit,
            int specialDropValue)
    {
        // TODO Auto-generated method stub
        
    }

}
