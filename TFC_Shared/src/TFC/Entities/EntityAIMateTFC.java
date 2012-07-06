package TFC.Entities;

import java.util.*;

import net.minecraft.src.*;

public class EntityAIMateTFC extends EntityAIBase
{
    private EntityAnimalTFC theAnimal;
    World theWorld;
    private EntityAnimalTFC targetMate;
    int field_48261_b;
    float field_48262_c;

    public EntityAIMateTFC (EntityAnimalTFC par1EntityAnimal, float par2)
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
        if (!theAnimal.isInLove ())
        {
            return false;
        }
        else
        {
            targetMate = func_48258_h ();
            return targetMate != null;
        }
    }


    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting ()
    {
        return targetMate.isEntityAlive () && targetMate.isInLove () && field_48261_b < 60 && targetMate.sex + theAnimal.sex == 1;
    }


    /**
     * Resets the task
     */
    public void resetTask ()
    {
        if (!theAnimal.mateForLife)
        {
            targetMate.mate = null;
            targetMate = null;
            theAnimal.mate = null;
            field_48261_b = 0;
        }
    }


    /**
     * Updates the task
     */
    public void updateTask ()
    {
        theAnimal.getLookHelper().setLookPositionWithEntity (targetMate, 10F, theAnimal.getVerticalFaceSpeed ());
        theAnimal.getNavigator().tryMoveToEntityLiving(targetMate, field_48262_c);
        field_48261_b++;

        if (field_48261_b == 60)
        {
            func_48257_i ();
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
        EntityAnimalTFC entityanimal = (EntityAnimalTFC) theAnimal.spawnBabyAnimal (targetMate);

        if (entityanimal == null)
        {
            return;
        }

        theAnimal.setGrowingAge (6000);
        targetMate.setGrowingAge (6000);
        theAnimal.resetInLove ();
        targetMate.resetInLove ();
        entityanimal.setGrowingAge (-24000 * entityanimal.adultAge);
        entityanimal.setLocationAndAngles (theAnimal.posX, theAnimal.posY, theAnimal.posZ, 0.0F, 0.0F);
        theWorld.spawnEntityInWorld (entityanimal);
        Random random = theAnimal.getRNG ();

        for (int i = 0 ; i < 7 ; i++)
        {
            double d = random.nextGaussian () * 0.02D;
            double d1 = random.nextGaussian () * 0.02D;
            double d2 = random.nextGaussian () * 0.02D;
            theWorld.spawnParticle ("heart", (theAnimal.posX + (double) (random.nextFloat () * theAnimal.width * 2.0F)) - (double) theAnimal.width, theAnimal.posY + 0.5D + (double) (random.nextFloat () * theAnimal.height), (theAnimal.posZ + (double) (random.nextFloat () * theAnimal.width * 2.0F)) - (double) theAnimal.width, d, d1, d2);
        }
    }
}
