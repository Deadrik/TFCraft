package TFC.Entities.AI;

import java.util.Iterator;
import java.util.List;

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
import net.minecraft.entity.passive.EntityAnimal;
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

public class EntityAIFollowParentTFC extends EntityAIBase
{
    /** The child that is following its parent. */
    EntityAnimalTFC childAnimal;
    EntityLiving parentAnimal;
    float field_48248_c;
    private int field_48246_d;

    public EntityAIFollowParentTFC(EntityAnimalTFC par1EntityAnimal, float par2)
    {
        this.childAnimal = par1EntityAnimal;
        this.parentAnimal = childAnimal.parent;
        this.field_48248_c = par2;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        double var3 = Double.MAX_VALUE;
        if (this.childAnimal.getGrowingAge() >= 0 || childAnimal.worldObj.loadedEntityList.contains(childAnimal.parent))
        {
            return false;
        }
        else if(this.parentAnimal != null || childAnimal.worldObj.loadedEntityList.contains(childAnimal.parent))
        {

            double var8 = this.childAnimal.getDistanceSqToEntity(this.parentAnimal);

            if (var8 <= var3)
            {
                var3 = var8;
            }
        }
        else
        {
            List var1 = this.childAnimal.worldObj.getEntitiesWithinAABB(EntityLiving.class, this.childAnimal.boundingBox.expand(8.0D, 4.0D, 8.0D));
            EntityLiving var2 = null;
            Iterator var5 = var1.iterator();

            while (var5.hasNext())
            {
                Entity var6 = (Entity)var5.next();
                EntityLiving var7 = (EntityLiving)var6;
                if(var7 instanceof EntityAnimal){
                    if (!(((EntityAnimal)var7).getGrowingAge() >= 0))
                    {
                        return false;
                    }
                }
                double var8 = this.childAnimal.getDistanceSqToEntity(var7);

                if (var8 <= var3)
                {
                    var3 = var8;
                    var2 = var7;
                    if(parentAnimal instanceof EntityPlayer){

                    }
                    else{

                    }
                }

            }

            if (var2 == null)
            {
                return false;
            }
        }
        if (var3 < 4.0D)
        {
            return false;
        }
        else
        {
            return true;
        }

    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        if (!this.parentAnimal.isEntityAlive() || childAnimal.parent==null || !childAnimal.worldObj.loadedEntityList.contains(childAnimal.parent))
        {
            return false;
        }
        else
        {
            double var1 = this.childAnimal.getDistanceSqToEntity(this.parentAnimal);
            return var1 >= 4.0D && var1 <= 256.0D;
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.field_48246_d = 0;
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        if(childAnimal != null && childAnimal.parent != null)
        {
            if (--this.field_48246_d <= 0)
            {
                this.field_48246_d = 10;
                this.childAnimal.getNavigator().tryMoveToEntityLiving(this.parentAnimal, this.field_48248_c);
            }
            double var1 = this.childAnimal.getDistanceSqToEntity(childAnimal.parent);
            float a = (this.childAnimal.getGrowingAge()/(TFC_Time.getYearRatio() * this.childAnimal.adultAge * -24000F));
            if (parentAnimal instanceof EntityAnimalTFC){
                if ((a >= 0.9F)&& var1 <= 3.0F && this.parentAnimal.getClass() == this.childAnimal.getClass() && this.childAnimal.hunger < 144000 && ((EntityAnimalTFC)this.parentAnimal).sex == 0){
                    this.childAnimal.hunger+=24000;
                    ((EntityAnimalTFC)this.parentAnimal).hunger-=(int) 24000*(1.1 - a);
                }
            }
        }
    }
}
