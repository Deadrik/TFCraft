package TFC.Entities.AI;

import TFC.*;
import TFC.Entities.EntityTameableTFC;
import net.minecraft.src.EntityAIBase;
import net.minecraft.src.EntityLiving;

public class EntityAISitTFC extends EntityAIBase
{
    private EntityTameableTFC theEntity;

    /** If the EntityTameable is sitting. */
    private boolean isSitting = false;

    public EntityAISitTFC(EntityTameableTFC par1EntityTameable)
    {
        this.theEntity = par1EntityTameable;
        this.setMutexBits(5);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (!this.theEntity.isTamed())
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
            EntityLiving var1 = this.theEntity.getOwner();
            return var1 == null ? true : (this.theEntity.getDistanceSqToEntity(var1) < 144.0D && var1.getAITarget() != null ? false : this.isSitting);
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.theEntity.getNavigator().clearPathEntity();
        this.theEntity.setSitting(true);
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        this.theEntity.setSitting(false);
    }

    /**
     * Sets the sitting flag.
     */
    public void setIsSitting(boolean par1)
    {
        this.isSitting = par1;
    }
}
