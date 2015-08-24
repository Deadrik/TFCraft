package com.bioxx.tfc.Entities.AI;

import java.util.List;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.Vec3;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.Entities.IAnimal;

public class EntityAIPanicTFC extends EntityAIBase
{
	private final EntityCreature theEntityCreature;
	private final boolean alertHerd;
	private final double speed;
	private double randPosX;
	private double randPosY;
	private double randPosZ;

	public EntityAIPanicTFC(EntityCreature par1EntityCreature, double par2, boolean par3)
	{
		this.theEntityCreature = par1EntityCreature;
		this.speed = par2;
		this.alertHerd = par3;
		this.setMutexBits(1);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute()
	{
		if (this.theEntityCreature.getAITarget() == null && !this.theEntityCreature.isBurning() &&
			(this.theEntityCreature instanceof IAnimal && ((IAnimal) this.theEntityCreature).getAttackedVec() == null ||
				!(this.theEntityCreature instanceof IAnimal)))
		{
			return false;
		}
		else
		{
			Vec3 attackedVec = this.theEntityCreature instanceof IAnimal ? ((IAnimal) this.theEntityCreature).getAttackedVec() : null;
			//TerraFirmaCraft.log.info(attackedVec != null);
			Vec3 vec3 = RandomPositionGenerator.findRandomTarget(this.theEntityCreature, 5, 4);
			if (attackedVec != null)
			{
				if (this.theEntityCreature instanceof IAnimal)
					attackedVec = updateAttackVec((IAnimal) this.theEntityCreature, attackedVec);
				vec3 = RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.theEntityCreature, 5, 4, attackedVec);
			}
			if (vec3 == null)
			{
				return false;
			}
			else
			{
				this.randPosX = vec3.xCoord;
				this.randPosY = vec3.yCoord;
				this.randPosZ = vec3.zCoord;
				if (alertHerd && this.theEntityCreature instanceof IAnimal)
				{
					List list = this.theEntityCreature.worldObj.getEntitiesWithinAABB(this.theEntityCreature.getClass(),
							this.theEntityCreature.boundingBox.expand(8, 8, 8));
					for (Object entity : list)
					{
						//TerraFirmaCraft.log.info(entity);
						((IAnimal) entity).setAttackedVec(attackedVec);
					}
				}
				return true;
			}
		}
	}

	public Vec3 updateAttackVec(IAnimal theCreature, Vec3 attackedVec)
	{
		if (theCreature.getFearSource() != null &&
			TFC_Core.getEntityPos(theEntityCreature).distanceTo(attackedVec) > this.theEntityCreature.getDistanceToEntity(theCreature.getFearSource()))
		{
			Vec3 newVec = Vec3.createVectorHelper(theCreature.getFearSource().posX, theCreature.getFearSource().posY, theCreature.getFearSource().posZ);
			theCreature.setAttackedVec(newVec);
			return newVec;
		}
		return attackedVec;
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting()
	{
		this.theEntityCreature.getNavigator().tryMoveToXYZ(this.randPosX, this.randPosY, this.randPosZ, this.speed);
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean continueExecuting()
	{
		return !this.theEntityCreature.getNavigator().noPath();
	}
}
