package net.minecraft.src.TFC_Core.Custom;

import java.util.*;
import net.minecraft.src.*;

public class EntityAIMoveTowardsFood extends EntityAIBase
{
    private EntityAnimalTFC theEntity;
    private Entity targetEntity;
    private double movePosX;
    private double movePosY;
    private double movePosZ;
    private float field_48330_f;
    private float field_48331_g;

    public EntityAIMoveTowardsFood (EntityAnimalTFC par1EntityCreature, float par2, float par3)
    {
	theEntity = par1EntityCreature;
	field_48330_f = par2;
	field_48331_g = par3;
	setMutexBits (1);
    }


    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute ()
    {
	targetEntity = null;
	List <EntityItem> closeItems = theEntity.worldObj.getEntitiesWithinAABB (net.minecraft.src.EntityItem.class, theEntity.boundingBox.expand (10D, 2D, 10D));
	for (EntityItem first:
	closeItems)
	{
	    for (int second:
	    theEntity.fooditems)
	    {
		if (((int)first.item.itemID) == second)
		{
		    targetEntity = first;
		}
	    }
	    if (targetEntity != null){
	    break;
	    }
	}
	
	if (targetEntity == null || theEntity.hunger > 144000)
	{
	    return false;
	}

	if (targetEntity.getDistanceSqToEntity (theEntity) > (double) (field_48331_g * field_48331_g))
	{
	    return false;
	}

	Vec3D vec3d = RandomPositionGeneratorTFC.func_48620_a (theEntity, 10, 10, Vec3D.createVector (targetEntity.posX, targetEntity.posY, targetEntity.posZ));

	if (vec3d == null)
	{
	    return false;
	}
	else
	{
	    movePosX = vec3d.xCoord;
	    movePosY = vec3d.yCoord;
	    movePosZ = vec3d.zCoord;
	    return true;
	}
    }


    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting ()
    {
	return !theEntity.getNavigator ().noPath () && targetEntity.getDistanceSqToEntity (theEntity) < (double) (field_48331_g * field_48331_g);
    }


    /**
     * Resets the task
     */
    public void resetTask ()
    {
	targetEntity = null;
    }


    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting ()
    {
	theEntity.getNavigator ().tryMoveToXYZ /*(movePosX, movePosY, movePosZ*/(targetEntity.posX, targetEntity.posY, targetEntity.posZ, field_48330_f);
    }
}
