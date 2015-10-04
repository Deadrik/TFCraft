package com.bioxx.tfc.Entities.AI;

import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;

import com.bioxx.tfc.api.Entities.IAnimal;
import com.bioxx.tfc.api.Entities.IAnimal.InteractionEnum;

public class EntityAITargetNonTamedTFC extends EntityAITargetNonTamed
{
	private EntityTameable entityTameable;
    private Class targetClass;
	//private static final String __OBFID = "CL_00001623";

	public EntityAITargetNonTamedTFC(EntityTameable entity, Class targetClass, int targetChance, boolean shouldCheckSight)
    {
		super(entity, targetClass, targetChance, shouldCheckSight);
        this.targetClass = targetClass;
		this.entityTameable = entity;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
	public boolean shouldExecute()
    {
		if (entityTameable instanceof IAnimal)
    	{
			IAnimal animal = (IAnimal) entityTameable;
			int familiarity = animal.getFamiliarity();
			if (this.targetClass == EntityPlayer.class && animal.checkFamiliarity(InteractionEnum.TOLERATEPLAYER, null))
			{
				return false;
			}
			else if (familiarity > 0 && this.taskOwner.getRNG().nextInt(familiarity) != 0)
				return false; // The more familiar the animal, the less likely it will attack.
    	}

		return super.shouldExecute();
    }
}