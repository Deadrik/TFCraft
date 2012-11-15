package TFC.Entities.AI;

import java.util.List;

import TFC.*;
import TFC.Entities.EntityAnimalTFC;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityAIBase;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.PathEntity;
import net.minecraft.src.PathNavigate;
import net.minecraft.src.RandomPositionGenerator;
import net.minecraft.src.Vec3;

public class EntityAIAvoidEntityTFC extends EntityAIBase
{
	/** The entity we are attached to */
	private EntityAnimalTFC theEntity;
	private float field_48242_b;
	private float field_48243_c;
	private Entity field_48240_d;
	private float field_48241_e;
	private PathEntity field_48238_f;
	private float rad;

	/** The PathNavigate of our entity */
	private PathNavigate entityPathNavigate;

	/** The class of the entity we should avoid */
	private Class targetEntityClass;

	public EntityAIAvoidEntityTFC(EntityAnimalTFC par1EntityCreature, Class par2Class, float par3, float par4, float par5)
	{
		this.theEntity = par1EntityCreature;
		this.targetEntityClass = par2Class;
		this.field_48241_e = par3;
		this.rad = par3;
		this.field_48242_b = par4;
		this.field_48243_c = par5;
		this.entityPathNavigate = par1EntityCreature.getNavigator();
		this.setMutexBits(1);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute()
	{
		if(theEntity.fearSource == null ){
			if (this.targetEntityClass == EntityPlayer.class)
			{
				this.field_48240_d = this.theEntity.worldObj.getClosestPlayerToEntity(this.theEntity, (double)this.rad);

				if (this.field_48240_d == null)
				{
					return false;
				}
			}
			else
			{
				List var1 = this.theEntity.worldObj.getEntitiesWithinAABB(this.targetEntityClass, this.theEntity.boundingBox.expand((double)this.rad, 3.0D, (double)this.rad));

				if (var1.size() == 0)
				{
					return false;
				}

				this.field_48240_d = (Entity)var1.get(0);
			}
			if(field_48240_d.isSneaking()){
				rad = field_48241_e/2;    		
			}
			if(field_48240_d.posX != field_48240_d.prevPosX || field_48240_d.posZ != field_48240_d.prevPosZ){
				theEntity.panic+=300/this.field_48240_d.getDistanceSqToEntity(this.theEntity);
				//System.out.println("Panic: "+theEntity.panic);
				if (field_48240_d.isSneaking()){
					theEntity.panic-=100/this.field_48240_d.getDistanceSqToEntity(this.theEntity);
				}
			}
			if (!this.theEntity.getEntitySenses().canSee(this.field_48240_d))
			{
				return false;
			}	
		}
		else{
			field_48240_d = theEntity.fearSource;
		}
		if (field_48240_d == null){
			return false;
		}
		if(theEntity.panic >= 50 || this.field_48240_d.getDistanceSqToEntity(this.theEntity) < rad)
		{
			if (this.field_48240_d.getDistanceSqToEntity(this.theEntity) > rad*16){
				theEntity.panic=0;
			}
			List var1 = this.theEntity.worldObj.getEntitiesWithinAABB(this.theEntity.getClass(), this.theEntity.boundingBox.expand(12.0, 3.0D, 12.0));
			if (var1.size()!=0){
				for (int x = 0; x < var1.size();x++){
					if(((EntityAnimalTFC)var1.get(x)).panic < 50 && var1.get(x) != theEntity){
						((EntityAnimalTFC)var1.get(x)).fearSource = field_48240_d;
						((EntityAnimalTFC)var1.get(x)).panic = 100;
					}
				}
			}
			Vec3 var2 = RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.theEntity, 16, 7, Vec3.createVectorHelper(this.field_48240_d.posX, this.field_48240_d.posY, this.field_48240_d.posZ));

			if (var2 == null)
			{
				return false;
			}
			else if (this.field_48240_d.getDistanceSq(var2.xCoord, var2.yCoord, var2.zCoord) < this.field_48240_d.getDistanceSqToEntity(this.theEntity))
			{
				return false;
			}
			else
			{
				this.field_48238_f = this.entityPathNavigate.getPathToXYZ(var2.xCoord, var2.yCoord, var2.zCoord);
				return this.field_48238_f == null ? false : this.field_48238_f.isDestinationSame(var2);
			}
		}
		return false;
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean continueExecuting()
	{
		return !this.entityPathNavigate.noPath();
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting()
	{
		this.entityPathNavigate.setPath(this.field_48238_f, this.field_48242_b);
	}

	/**
	 * Resets the task
	 */
	public void resetTask()
	{
		this.field_48240_d = null;
	}

	/**
	 * Updates the task
	 */
	public void updateTask()
	{
		if (this.theEntity.getDistanceSqToEntity(this.field_48240_d) < rad*2)
		{
			this.theEntity.getNavigator().setSpeed(this.field_48243_c);
		}
		else
		{
			this.theEntity.getNavigator().setSpeed(this.field_48242_b);
		}
	}
}
