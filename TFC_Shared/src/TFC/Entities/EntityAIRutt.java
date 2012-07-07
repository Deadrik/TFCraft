package TFC.Entities;

import java.util.*;

import TFC.Core.TFCSeasons;

import net.minecraft.src.*;

public class EntityAIRutt extends EntityAIBase
{
    private EntityAnimalTFC theAnimal;
    World theWorld;
    private EntityAnimalTFC targetMate;
    int field_48261_b;
    float field_48262_c;

    public EntityAIRutt (EntityAnimalTFC par1EntityAnimal, float par2)
    {
        field_48261_b = 0;
        theAnimal = par1EntityAnimal;
        theWorld = par1EntityAnimal.worldObj;
        field_48262_c = par2;
        setMutexBits (3);
    }


    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute ()
    {
        if (theAnimal.hunger < 84000 || theAnimal.sex == 0 || !(TFCSeasons.getMonth() >= theAnimal.matingStart && TFCSeasons.getMonth() <= theAnimal.matingEnd)||theAnimal.breeding != 0)
        {
            return false;
        }
        else
        {        	        	
           theAnimal.rutting = true;
            return true;
        }
    }


    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting ()
    {
        return !((theAnimal.getHealth() <= theAnimal.getMaxHealth() / 4)||(theAnimal.ruttVictor));
    }


    /**
     * Resets the task
     */
    public void resetTask ()
    {
        	theAnimal.rutting = false;
        	if (theAnimal.ruttVictor){
        		theAnimal.setInLove(true);
        	}
    }


    /**
     * Updates the task
     */
    public void updateTask ()
    {
    	float f = 8F;
        List list = theWorld.getEntitiesWithinAABB (theAnimal.getClass (), theAnimal.boundingBox.expand (f, f, f));

        for (Iterator iterator = list.iterator () ; iterator.hasNext () ;)
        {
            Entity entity = (Entity) iterator.next ();
            EntityAnimalTFC entityanimal = (EntityAnimalTFC) entity;

            if (entityanimal.rutting)
            {
                theAnimal.setAttackTarget(entityanimal);
            }
        }
    }


    private EntityAnimalTFC func_48258_h ()
    {
        float f = 8F;
        List list = theWorld.getEntitiesWithinAABB (theAnimal.getClass (), theAnimal.boundingBox.expand (f, f, f));

        for (Iterator iterator = list.iterator () ; iterator.hasNext () ;)
        {
            Entity entity = (Entity) iterator.next ();
            EntityAnimalTFC entityanimal = (EntityAnimalTFC) entity;

            if (theAnimal.func_48135_b (entityanimal))
            {
                return entityanimal;
            }
        }

        return null;
    }


    private void func_48257_i ()
    {
       theAnimal.mate(targetMate);
    }
}
