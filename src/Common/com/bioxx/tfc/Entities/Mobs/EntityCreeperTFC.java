package com.bioxx.tfc.Entities.Mobs;

import com.bioxx.tfc.Core.TFC_Achievements;
import com.bioxx.tfc.api.Interfaces.IInnateArmor;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityCreeperTFC extends EntityCreeper implements IInnateArmor
{
	public EntityCreeperTFC(World par1World)
	{
		super(par1World);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAICreeperSwell(this));
        this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityOcelotTFC.class, 6.0F, 1.0D, 1.2D));
        this.tasks.addTask(4, new EntityAIAttackOnCollide(this, 1.0D, false));
        this.tasks.addTask(5, new EntityAIWander(this, 0.8D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(500);//MaxHealth
	}

	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
	{
		Entity entity = par1DamageSource.getEntity();
		if(entity != null && entity instanceof EntityPlayer && 
				((EntityPlayer) entity).getHeldItem() != null && ((EntityPlayer) entity).getHeldItem().getItem().equals(TFCItems.stick)){
			((EntityPlayer)entity).triggerAchievement(TFC_Achievements.achPokeCreeper);
		}
		return super.attackEntityFrom(par1DamageSource, par2);
	}

	@Override
	public int getCrushArmor() {
		return 1000;
	}
	@Override
	public int getSlashArmor() {
		return 0;
	}
	@Override
	public int getPierceArmor() {
		return -335;
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
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource p_70645_1_)
    {
        super.onDeath(p_70645_1_);

        if (p_70645_1_.getEntity() instanceof EntitySkeletonTFC)
        {
            int i = Item.getIdFromItem(Items.record_13);
            int j = Item.getIdFromItem(Items.record_wait);
            int k = i + this.rand.nextInt(j - i + 1);
            this.dropItem(Item.getItemById(k), 1);
        }
    }

}
