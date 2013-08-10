package TFC.Entities.AI;

import java.util.*;

import TFC.*;
import TFC.Entities.EntityAnimalTFC;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.feature.*;

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
            if (targetMate != null){
            	if ((targetMate.sex + theAnimal.sex != 1)||(theAnimal.pregnant || targetMate.pregnant)){
            		return false;
            	}
            }
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

            if (theAnimal.canMateWith(entityanimal))
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
