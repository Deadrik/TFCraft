package TFC.Entities.AI;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import TFC.Entities.EntityTameableTFC;

public class EntityAIOwnerHurtTargetTFC extends EntityAITarget
{
	EntityTameableTFC field_48392_a;
	EntityLivingBase field_48391_b;

	public EntityAIOwnerHurtTargetTFC(EntityTameableTFC par1EntityTameable)
	{
		super(par1EntityTameable, 32.0F, false);
		this.field_48392_a = par1EntityTameable;
		this.setMutexBits(1);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute()
	{
		if (!this.field_48392_a.isTamed())
		{
			return false;
		}
		else
		{
			EntityLivingBase var1 = this.field_48392_a.getOwner();

			if (var1 == null)
			{
				return false;
			}
			else
			{
				this.field_48391_b = var1.getLastAttackingEntity();
				return this.isSuitableTarget(field_48391_b, false);
			}
		}
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting()
	{
		this.taskOwner.setAttackTarget(this.field_48391_b);
		super.startExecuting();
	}
}
