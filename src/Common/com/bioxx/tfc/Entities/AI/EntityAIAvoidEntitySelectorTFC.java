package com.bioxx.tfc.Entities.AI;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.util.Vec3;

class EntityAIAvoidEntitySelectorTFC implements IEntitySelector
{
	private final EntityAIAvoidEntityTFC entityAvoiderAI;

	EntityAIAvoidEntitySelectorTFC(EntityAIAvoidEntityTFC par1EntityAIAvoidEntity)
	{
		this.entityAvoiderAI = par1EntityAIAvoidEntity;
	}

	/**
	 * Return whether the specified entity is applicable to this filter.
	 */
	@Override
	public boolean isEntityApplicable(Entity par1Entity)
	{
		EntityCreature creature = EntityAIAvoidEntityTFC.getEntity(this.entityAvoiderAI);
		//TFC calculation for animal field-of-view. Generous in favour of approaching entity, with 340 degree FOV around the creature.
		Vec3 distanceVec = Vec3.createVectorHelper(creature.posX - par1Entity.posX,creature.posY - par1Entity.posY,creature.posZ - par1Entity.posZ);
		double cosAngleAbs = Math.abs(distanceVec.dotProduct(creature.getLookVec()) / (distanceVec.lengthVector() * creature.getLookVec().lengthVector()));
		boolean fovSight = cosAngleAbs < 0.985;
		//TFC calculation for motion-based sight. If entity is within FOV, RNG is used to see if entity will be spotted. 
		//cosAngleAbs is smaller as the entity is closer to the centre of vision, and so n gets smaller.
		//Entity motion also plays a role, affecting the maximum size of n and also the minimum, the first part of n is to make n >= 1
		//this is relevant when the the entity is very close to the centre of the FOV, therefore motion is squared against 8, making minute movements
		//more impactful on detection, however if the entity is spotted when it is not moving, it is not an instant spot success
		Vec3 movement = Vec3.createVectorHelper(par1Entity.motionX, par1Entity.motionY, par1Entity.motionZ);
		double inverseMotion = movement.lengthVector() > 0 ? 1D / (movement.lengthVector()*2):1;
		inverseMotion = inverseMotion > 1? 1: inverseMotion;
		int n = (int) Math.ceil(8 * inverseMotion * inverseMotion) + (int) (cosAngleAbs * 1.015 * 30 * inverseMotion);
		boolean motionSight = fovSight && creature.getRNG().nextInt(n) == 0;
		//vanilla calculation for direct line-of-sight to entity
		boolean sight =  par1Entity.isEntityAlive() && creature.getEntitySenses().canSee(par1Entity);
		return sight && motionSight;
	}
}
