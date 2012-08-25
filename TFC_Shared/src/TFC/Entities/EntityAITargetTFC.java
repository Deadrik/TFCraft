package TFC.Entities;

import net.minecraft.src.*;


public class EntityAITargetTFC extends EntityAITarget
{
    protected float targetDistance;
    protected boolean field_48380_e;
    private boolean field_48383_a;
    private int field_48381_b;
    private int field_48377_f;
    private int field_48378_g;

    public EntityAITargetTFC(EntityAnimalTFC par1EntityLiving, float par2, boolean par3)
    {
        this((EntityLiving)par1EntityLiving, par2, par3, false);
    }

    public EntityAITargetTFC(EntityLiving par1EntityLiving, float par2, boolean par3, boolean par4)
    {
    	super(par1EntityLiving, par2, par3,par4);
        taskOwner = par1EntityLiving;
    }

    public boolean shouldExecute(){
    	return taskOwner.getAttackTarget() != null;
    }
    public boolean continueExecuting()
    {
        EntityLiving var1 = this.taskOwner.getAttackTarget();

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

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.field_48381_b = 0;
        this.field_48377_f = 0;
        this.field_48378_g = 0;
    }

    /**
     * Resets the task
     */
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
    }
}
