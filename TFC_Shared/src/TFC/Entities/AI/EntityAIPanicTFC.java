package TFC.Entities.AI;

import TFC.*;
import TFC.Entities.EntityAnimalTFC;
import net.minecraft.src.EntityAIBase;
import net.minecraft.src.RandomPositionGenerator;
import net.minecraft.src.Vec3;

public class EntityAIPanicTFC extends EntityAIBase
{
	private EntityAnimalTFC theAnimal;
	private float speed;
	private double randPosX;
	private double randPosY;
	private double randPosZ;
	private boolean scared;
	private boolean group;

	public EntityAIPanicTFC(EntityAnimalTFC par1EntityCreature, float par2, boolean scaredBaby, boolean groupPanic)
	{
		this.theAnimal = par1EntityCreature;
		this.speed = par2;
		this.setMutexBits(1);
		scared = scaredBaby;
		group = groupPanic;
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute()
	{

		if(scared && theAnimal.fearSource != null){
			if( theAnimal.isChild()){
				return true;
			}
			return false;
		}

		if(theAnimal.fearSource == null){
			return false;
		}
		
		if(theAnimal.terrified){
			return true;
		}

		if (this.theAnimal.getAITarget() == null)
		{
			return false;
		}
		else
		{
			Vec3 var1 = RandomPositionGenerator.findRandomTarget(this.theAnimal, 5, 4);

			if (var1 == null)
			{
				return false;
			}
			else
			{
				this.randPosX = theAnimal.posX + (theAnimal.fearSource.posX - theAnimal.posX)*10;
				this.randPosY = theAnimal.posY;
				this.randPosZ = theAnimal.posZ + (theAnimal.fearSource.posZ - theAnimal.posZ)*10;
				return true;
			}
		}
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting()
	{
		Vec3 var1 = RandomPositionGenerator.findRandomTarget(this.theAnimal, 5, 4);

		this.randPosX = theAnimal.posX + (theAnimal.fearSource.posX - theAnimal.posX)*10;
		this.randPosY = theAnimal.posY;
		this.randPosZ = theAnimal.posZ + (theAnimal.fearSource.posZ - theAnimal.posZ)*10;

		this.theAnimal.getNavigator().tryMoveToXYZ(this.randPosX, this.randPosY, this.randPosZ, this.speed);
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean continueExecuting()
	{
		return !this.theAnimal.getNavigator().noPath();
	}
	
	public void resetTask(){
		theAnimal.fearSource = null;
	}
}
