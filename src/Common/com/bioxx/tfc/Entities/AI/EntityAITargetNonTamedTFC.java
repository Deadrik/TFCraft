package com.bioxx.tfc.Entities.AI;

import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;

import com.bioxx.tfc.api.Entities.IAnimal;
import com.bioxx.tfc.api.Entities.IAnimal.InteractionEnum;

public class EntityAITargetNonTamedTFC extends EntityAITargetNonTamed
{
    private EntityTameable theTameable;
    private Class targetClass;
    private static final String __OBFID = "CL_00001623";

    public EntityAITargetNonTamedTFC(EntityTameable theEntity, Class targetClass, int p_i1666_3_, boolean p_i1666_4_)
    {
        super(theEntity, targetClass, p_i1666_3_, p_i1666_4_);
        this.targetClass = targetClass;
        this.theTameable = theEntity;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
	public boolean shouldExecute()
    {
    	if(theTameable instanceof IAnimal && this.targetClass == EntityPlayer.class && ((IAnimal)theTameable).checkFamiliarity(InteractionEnum.TOLERATEPLAYER, null))
    	{
    		return false;
    	}
        return super.shouldExecute();
    }
}