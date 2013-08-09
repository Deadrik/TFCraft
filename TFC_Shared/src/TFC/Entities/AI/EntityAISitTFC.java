package TFC.Entities.AI;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import TFC.Entities.EntityTameableTFC;

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
	@Override
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
			EntityLivingBase var1 = this.theEntity.getOwner();
			return var1 == null ? true : (this.theEntity.getDistanceSqToEntity(var1) < 144.0D && var1.getAITarget() != null ? false : this.isSitting);
		}
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting()
	{
		this.theEntity.getNavigator().clearPathEntity();
		this.theEntity.setSitting(true);
	}

	/**
	 * Resets the task
	 */
	@Override
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
