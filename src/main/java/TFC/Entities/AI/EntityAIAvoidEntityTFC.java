package TFC.Entities.AI;

import java.util.List;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.Vec3;

public class EntityAIAvoidEntityTFC extends EntityAIBase
{
	public final IEntitySelector field_98218_a = new EntityAIAvoidEntitySelectorTFC(this);

	/** The entity we are attached to */
	private EntityCreature theEntity;
	private double farSpeed;
	private double nearSpeed;
	private Entity closestLivingEntity;
	private float distanceFromEntity;

	/** The PathEntity of our entity */
	private PathEntity entityPathEntity;

	/** The PathNavigate of our entity */
	private PathNavigate entityPathNavigate;

	/** The class of the entity we should avoid */
	private Class targetEntityClass;

	public EntityAIAvoidEntityTFC(EntityCreature par1EntityCreature, Class par2Class, float par3, double par4, double par6)
	{
		this.theEntity = par1EntityCreature;
		this.targetEntityClass = par2Class;
		this.distanceFromEntity = par3;
		this.farSpeed = par4;
		this.nearSpeed = par6;
		this.entityPathNavigate = par1EntityCreature.getNavigator();
		this.setMutexBits(1);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute()
	{
		if (this.targetEntityClass == EntityPlayer.class)
			if (this.theEntity instanceof EntityTameable && ((EntityTameable)this.theEntity).isTamed())
				return false;

		List list = this.theEntity.worldObj.selectEntitiesWithinAABB(this.targetEntityClass, this.theEntity.boundingBox.expand((double)this.distanceFromEntity, 3.0D, (double)this.distanceFromEntity), this.field_98218_a);

		if (list.isEmpty())
			return false;

		this.closestLivingEntity = (Entity)list.get(0);

		Vec3 vec3 = RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.theEntity, 16, 7, this.theEntity.worldObj.getWorldVec3Pool().getVecFromPool(this.closestLivingEntity.posX, this.closestLivingEntity.posY, this.closestLivingEntity.posZ));
		if (vec3 == null)
			return false;
		else if (this.closestLivingEntity.getDistanceSq(vec3.xCoord, vec3.yCoord, vec3.zCoord) < this.closestLivingEntity.getDistanceSqToEntity(this.theEntity))
			return false;
		else
		{
			this.entityPathEntity = this.entityPathNavigate.getPathToXYZ(vec3.xCoord, vec3.yCoord, vec3.zCoord);
			return this.entityPathEntity == null ? false : this.entityPathEntity.isDestinationSame(vec3);
		}
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean continueExecuting()
	{
		return !this.entityPathNavigate.noPath();
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting()
	{
		this.entityPathNavigate.setPath(this.entityPathEntity, this.farSpeed);
	}

	/**
	 * Resets the task
	 */
	public void resetTask()
	{
		this.closestLivingEntity = null;
	}

	/**
	 * Updates the task
	 */
	public void updateTask()
	{
		if (this.theEntity.getDistanceSqToEntity(this.closestLivingEntity) < 49.0D)
			this.theEntity.getNavigator().setSpeed(this.nearSpeed);
		else
			this.theEntity.getNavigator().setSpeed(this.farSpeed);
	}

	static EntityCreature func_98217_a(EntityAIAvoidEntityTFC par0EntityAIAvoidEntity)
	{
		return par0EntityAIAvoidEntity.theEntity;
	}
}
