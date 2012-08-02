package TFC.Entities;

import java.util.Iterator;
import java.util.List;

import net.minecraft.src.*;

public class EntityAIHurtByTargetTFC extends EntityAIHurtByTarget
{
    boolean field_48395_a;
    EntityLiving entity;

    public EntityAIHurtByTargetTFC(EntityLiving par1EntityLiving, boolean par2)
    {
        super(par1EntityLiving, par2);
        this.field_48395_a = par2;
        this.setMutexBits(1);
        entity = par1EntityLiving;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {    	
        return (!(entity.isChild()) && /*this.func_48376_a(this.taskOwner.getAITarget()*/entity.getAITarget() != null);
    }
    public void startExecuting()
    {       
        super.startExecuting();
    }
}
