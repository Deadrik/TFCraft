package TFC.Entities;

import net.minecraft.src.*;

public class EntityAIOwnerHurtTargetTFC extends EntityAITarget
{
    EntityTameableTFC field_48392_a;
    EntityLiving field_48391_b;

    public EntityAIOwnerHurtTargetTFC(EntityTameableTFC par1EntityTameable)
    {
        super(par1EntityTameable, 32.0F, false);
        this.field_48392_a = par1EntityTameable;
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (!this.field_48392_a.isTamed())
        {
            return false;
        }
        else
        {
            EntityLiving var1 = this.field_48392_a.getOwner();

            if (var1 == null)
            {
                return false;
            }
            else
            {
                this.field_48391_b = var1.getLastAttackingEntity();
                return mod_TFC_Core.proxy.aiTargetShouldExecute(this, field_48391_b, false);
            }
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.taskOwner.setAttackTarget(this.field_48391_b);
        super.startExecuting();
    }
}
