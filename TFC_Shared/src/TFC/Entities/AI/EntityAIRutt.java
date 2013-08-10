package TFC.Entities.AI;

import java.util.*;

import TFC.*;
import TFC.Core.TFC_Time;
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
    	if (theAnimal.getAttackTarget()!= null){
    		return false;
    	}
    	if (theAnimal.getHealth()*3 <= theAnimal.getMaxHealth()){
    		theAnimal.ruttVictor = false;
    		if(theAnimal.getAttackTarget()!=null&&theAnimal.getAttackTarget().getClass() == theAnimal.getClass()){
    			((EntityAnimalTFC)theAnimal.getAttackTarget()).ruttVictor = true;    			
    		}
    		return false;
    	}
        if (theAnimal.hunger < 84000 || theAnimal.sex == 1 || !(TFC_Time.getMonth() >= theAnimal.matingStart && TFC_Time.getMonth() <= theAnimal.matingEnd)||theAnimal.breeding != 0||theAnimal.getAttackTarget()!=null)
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
        return !((theAnimal.getHealth() <= theAnimal.getMaxHealth() / 4)||(theAnimal.ruttVictor))||theAnimal.getAttackTarget()!=null;
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

    /**
     * Removed for having no use. Please delete to clear up the code when confirmed by dunk
     * 
    private EntityAnimalTFC func_48258_h ()
    {
        float f = 8F;
        List list = theWorld.getEntitiesWithinAABB (theAnimal.getClass (), theAnimal.boundingBox.expand (f, f, f));

        for (Iterator iterator = list.iterator () ; iterator.hasNext () ;)
        {
            Entity entity = (Entity) iterator.next ();
            EntityAnimalTFC entityanimal = (EntityAnimalTFC) entity;

            if (theAnimal.canMateWith (entityanimal))
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
    */
}
