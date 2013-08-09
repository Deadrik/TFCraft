package TFC.Entities.AI;

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
import net.minecraft.entity.passive.EntityTameable;
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


public class EntityAITargetTFC extends EntityAITarget
{
    public EntityAITargetTFC(EntityLiving par1EntityLiving, boolean par3)
    {
    	super((EntityCreature)par1EntityLiving, par3);
    }

	@Override
	public boolean shouldExecute() 
	{
		return taskOwner.getAttackTarget() != null;
	}

	/**Removed during the update to 1.6.2*/
    /*
    public boolean continueExecuting()
    {
        Entity var1 = this.taskOwner.getEntityToAttack();

        if (var1 == null)
        {
            return false;
        }
        else if (!var1.isEntityAlive())
        {
            return false;
        }
        else if (this.taskOwner.getDistanceSqToEntity(var1) > (double)(this.targetDistance * this.targetDistance))
        {
            return false;
        }
        else
        {
            if (this.field_48380_e)
            {
                if (!this.taskOwner.getEntitySenses().canSee(var1))
                {
                    if (++this.field_48378_g > 60)
                    {
                        return false;
                    }
                }
                else
                {
                    this.field_48378_g = 0;
                }
            }

            return true;
        }
    }

    public void startExecuting()
    {
        this.field_48381_b = 0;
        this.field_48377_f = 0;
        this.field_48378_g = 0;
    }

    public void resetTask()
    {
        this.taskOwner.setAttackTarget((EntityLiving)null);
    }

    protected boolean func_48376_a(EntityLiving par1EntityLiving, boolean par2)
    {
        if (par1EntityLiving == null)
        {
            return false;
        }
        else if (par1EntityLiving == this.taskOwner)
        {
            return false;
        }
        else if (!par1EntityLiving.isEntityAlive())
        {
            return false;
        }
        else if (par1EntityLiving.boundingBox.maxY > this.taskOwner.boundingBox.minY && par1EntityLiving.boundingBox.minY < this.taskOwner.boundingBox.maxY)
        {
            if (!(EntityCreeper.class != par1EntityLiving.getClass() && EntityGhast.class != par1EntityLiving.getClass()))
            {
                return false;
            }
            else
            {
                if (this.taskOwner instanceof EntityTameable && ((EntityTameable)this.taskOwner).isTamed())
                {
                    if (par1EntityLiving instanceof EntityTameable && ((EntityTameable)par1EntityLiving).isTamed())
                    {
                        return false;
                    }

                    if (par1EntityLiving == ((EntityTameable)this.taskOwner).getOwner())
                    {
                        return false;
                    }
                }
                else if (par1EntityLiving instanceof EntityPlayer && !par2 && ((EntityPlayer)par1EntityLiving).capabilities.disableDamage)
                {
                    return false;
                }

                if (!this.taskOwner.isWithinHomeDistance(MathHelper.floor_double(par1EntityLiving.posX), MathHelper.floor_double(par1EntityLiving.posY), MathHelper.floor_double(par1EntityLiving.posZ)))
                {
                    return false;
                }
                else if (this.field_48380_e && !this.taskOwner.getEntitySenses().canSee(par1EntityLiving))
                {
                    return false;
                }
                else
                {
                    if (this.field_48383_a)
                    {
                        if (--this.field_48377_f <= 0)
                        {
                            this.field_48381_b = 0;
                        }

                        if (this.field_48381_b == 0)
                        {
                            this.field_48381_b = this.func_48375_a(par1EntityLiving) ? 1 : 2;
                        }

                        if (this.field_48381_b == 2)
                        {
                            return false;
                        }
                    }

                    return true;
                }
            }
        }
        else
        {
            return false;
        }
    }

    private boolean func_48375_a(EntityLiving par1EntityLiving)
    {
        this.field_48377_f = 10 + this.taskOwner.getRNG().nextInt(5);
        PathEntity var2 = this.taskOwner.getNavigator().getPathToEntityLiving(par1EntityLiving);

        if (var2 == null)
        {
            return false;
        }
        else
        {
            PathPoint var3 = var2.getFinalPathPoint();

            if (var3 == null)
            {
                return false;
            }
            else
            {
                int var4 = var3.xCoord - MathHelper.floor_double(par1EntityLiving.posX);
                int var5 = var3.zCoord - MathHelper.floor_double(par1EntityLiving.posZ);
                return (double)(var4 * var4 + var5 * var5) <= 2.25D;
            }
        }
    }*/
}
