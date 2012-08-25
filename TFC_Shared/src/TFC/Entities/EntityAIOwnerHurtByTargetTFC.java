package TFC.Entities;

import net.minecraft.src.*;

public class EntityAIOwnerHurtByTargetTFC extends EntityAITarget
{
    EntityTameableTFC field_48394_a;
    EntityLiving field_48393_b;

    public EntityAIOwnerHurtByTargetTFC(EntityTameableTFC par1EntityTameable)
    {
        super(par1EntityTameable, 32.0F, false);
        this.field_48394_a = par1EntityTameable;
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (!this.field_48394_a.isTamed())
        {
            return false;
        }
        else
        {
            EntityLiving var1 = this.field_48394_a.getOwner();

            if (var1 == null)
            {
                return false;
            }
            else
            {
                this.field_48393_b = var1.getAITarget();
                return this.isSuitableTarget(field_48393_b, false);
            }
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.taskOwner.setAttackTarget(this.field_48393_b);
        super.startExecuting();
    }
}
