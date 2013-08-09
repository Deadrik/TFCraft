package TFC.Entities.AI;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import TFC.Entities.EntityTameableTFC;

public class EntityAIFollowOwnerTFC extends EntityAIBase
{
	private EntityTameableTFC thePet;
	private EntityLivingBase theOwner;
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
	 @Override
	 public boolean shouldExecute()
	 {
		 EntityLivingBase var1 = this.thePet.getOwner();

		 if (var1 == null)
		 {
			 return false;
		 }
		 else if (this.thePet.isSitting())
		 {
			 return false;
		 }
		 else if (this.thePet.getDistanceSqToEntity(var1) < this.minDist * this.minDist)
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
	 @Override
	 public boolean continueExecuting()
	 {
		 return !this.petPathfinder.noPath() && this.thePet.getDistanceSqToEntity(this.theOwner) > this.maxDist * this.maxDist && !this.thePet.isSitting();
	 }

	 /**
	  * Execute a one shot task or start executing a continuous task
	  */
	 @Override
	 public void startExecuting()
	 {
		 this.field_48310_h = 0;
		 this.field_48311_i = this.thePet.getNavigator().getAvoidsWater();
		 this.thePet.getNavigator().setAvoidsWater(false);
	 }

	 /**
	  * Resets the task
	  */
	 @Override
	 public void resetTask()
	 {
		 this.theOwner = null;
		 this.petPathfinder.clearPathEntity();
		 this.thePet.getNavigator().setAvoidsWater(this.field_48311_i);
	 }

	 /**
	  * Updates the task
	  */
	 @Override
	 public void updateTask()
	 {
		 this.thePet.getLookHelper().setLookPositionWithEntity(this.theOwner, 10.0F, this.thePet.getVerticalFaceSpeed());

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
									 this.thePet.setLocationAndAngles(var1 + var4 + 0.5F, var3, var2 + var5 + 0.5F, this.thePet.rotationYaw, this.thePet.rotationPitch);
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
