package com.bioxx.tfc.Entities.AI;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.passive.EntityTameable;

public class EntityAISitTFC extends EntityAISit {
	
	private EntityTameable theEntity;
    /** If the EntityTameable is sitting. */
    private boolean isSitting;
	
	public EntityAISitTFC(EntityTameable theEntity) {
		super(theEntity);
		this.theEntity = theEntity;
        this.setMutexBits(5);
	}

	  /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
	public boolean shouldExecute()
    {
		//boolean superResult = super.shouldExecute();
        if (!this.theEntity.isTamed() && !this.theEntity.isSitting())
        {
            return false;
        }
        else if (this.theEntity.isInWater())
        {
            return false;
        }
        else if (!this.theEntity.onGround)
        {
            return false;
        }
        else
        {
            EntityLivingBase entitylivingbase = this.theEntity.getOwner();
			return entitylivingbase == null ? true : this.theEntity.getDistanceSqToEntity(entitylivingbase) < 144.0D && entitylivingbase.getAITarget() != null ? this.isSitting && !this.theEntity.isTamed() : this.isSitting;
        }
    }
    
    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
	public void startExecuting()
    {
    	super.startExecuting();
        this.theEntity.getNavigator().clearPathEntity();
        this.theEntity.setSitting(true);
    }

    /**
     * Resets the task
     */
    @Override
	public void resetTask()
    {
    	super.resetTask();
        this.theEntity.setSitting(false);
    }
	
    /**
     * Sets the sitting flag.
     */
    @Override
	public void setSitting(boolean sitting)
    {
    	super.setSitting(sitting);
        this.isSitting = sitting;
    }
}
