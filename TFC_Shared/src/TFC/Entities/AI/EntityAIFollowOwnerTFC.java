package TFC.Entities.AI;

import TFC.*;
import TFC.Entities.EntityTameableTFC;
import net.minecraft.src.EntityAIBase;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.MathHelper;
import net.minecraft.src.PathNavigate;
import net.minecraft.src.World;

public class EntityAIFollowOwnerTFC extends EntityAIBase
{
    private EntityTameableTFC thePet;
    private EntityLiving theOwner;
    World theWorld;
    private float field_48303_f;
    private PathNavigate petPathfinder;
    private int field_48310_h;
    float maxDist;
    float minDist;
    private boolean field_48311_i;

    public EntityAIFollowOwnerTFC(EntityTameableTFC par1EntityTameable, float par2, float par3, float par4)
    {
        this.thePet = par1EntityTameable;
        this.theWorld = par1EntityTameable.worldObj;
        this.field_48303_f = par2;
        this.petPathfinder = par1EntityTameable.getNavigator();
        this.minDist = par3;
        this.maxDist = par4;
        this.setMutexBits(3);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        EntityLiving var1 = this.thePet.getOwner();

        if (var1 == null)
        {
            return false;
        }
        else if (this.thePet.isSitting())
        {
            return false;
        }
        else if (this.thePet.getDistanceSqToEntity(var1) < (double)(this.minDist * this.minDist))
        {
            return false;
        }
        else
        {
            this.theOwner = var1;
            return true;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return !this.petPathfinder.noPath() && this.thePet.getDistanceSqToEntity(this.theOwner) > (double)(this.maxDist * this.maxDist) && !this.thePet.isSitting();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.field_48310_h = 0;
        this.field_48311_i = this.thePet.getNavigator().getAvoidsWater();
        this.thePet.getNavigator().setAvoidsWater(false);
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        this.theOwner = null;
        this.petPathfinder.clearPathEntity();
        this.thePet.getNavigator().setAvoidsWater(this.field_48311_i);
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        this.thePet.getLookHelper().setLookPositionWithEntity(this.theOwner, 10.0F, (float)this.thePet.getVerticalFaceSpeed());

        if (!this.thePet.isSitting())
        {
            if (--this.field_48310_h <= 0)
            {
                this.field_48310_h = 10;

                if (!this.petPathfinder.tryMoveToEntityLiving(this.theOwner, this.field_48303_f))
                {
                    if (this.thePet.getDistanceSqToEntity(this.theOwner) >= 144.0D)
                    {
                        int var1 = MathHelper.floor_double(this.theOwner.posX) - 2;
                        int var2 = MathHelper.floor_double(this.theOwner.posZ) - 2;
                        int var3 = MathHelper.floor_double(this.theOwner.boundingBox.minY);

                        for (int var4 = 0; var4 <= 4; ++var4)
                        {
                            for (int var5 = 0; var5 <= 4; ++var5)
                            {
                                if ((var4 < 1 || var5 < 1 || var4 > 3 || var5 > 3) && this.theWorld.isBlockNormalCube(var1 + var4, var3 - 1, var2 + var5) && !this.theWorld.isBlockNormalCube(var1 + var4, var3, var2 + var5) && !this.theWorld.isBlockNormalCube(var1 + var4, var3 + 1, var2 + var5))
                                {
                                    this.thePet.setLocationAndAngles((double)((float)(var1 + var4) + 0.5F), (double)var3, (double)((float)(var2 + var5) + 0.5F), this.thePet.rotationYaw, this.thePet.rotationPitch);
                                    this.petPathfinder.clearPathEntity();
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
