package TFC.Entities.AI;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIHurtByTarget;

public class EntityAIHurtByTargetTFC extends EntityAIHurtByTarget
{
	boolean field_48395_a;
	EntityCreature entity;

	public EntityAIHurtByTargetTFC(EntityCreature par1EntityLiving, boolean par2)
	{
		super(par1EntityLiving, par2);
		this.field_48395_a = par2;
		this.setMutexBits(1);
		entity = par1EntityLiving;
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute()
	{    	
		return (!(entity.isChild()) && /*this.func_48376_a(this.taskOwner.getAITarget()*/entity.getAITarget() != null);
	}
	@Override
	public void startExecuting()
	{       
		super.startExecuting();
	}
}
