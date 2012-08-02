package TFC.Entities;

import TFC.Core.RandomPositionGeneratorTFC;
import net.minecraft.src.*;

public class EntityAIPanicTFC extends EntityAIBase
{
	private EntityAnimalTFC field_48316_a;
	private float speed;
	private double field_48315_c;
	private double field_48312_d;
	private double field_48313_e;
	private boolean scared;
	private boolean group;

	public EntityAIPanicTFC(EntityAnimalTFC par1EntityCreature, float par2, boolean scaredBaby, boolean groupPanic)
	{
		this.field_48316_a = par1EntityCreature;
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

		if(scared && field_48316_a.fearSource != null){
			if( field_48316_a.isChild()){
				return true;
			}
			return false;
		}

		if(field_48316_a.fearSource == null){
			return false;
		}
		
		if(field_48316_a.terrified){
			return true;
		}

		if (this.field_48316_a.getAITarget() == null)
		{
			return false;
		}
		else
		{
			Vec3D var1 = RandomPositionGeneratorTFC.func_48622_a(this.field_48316_a, 5, 4);

			if (var1 == null)
			{
				return false;
			}
			else
			{
				this.field_48315_c = field_48316_a.posX + (field_48316_a.fearSource.posX - field_48316_a.posX)*10;
				this.field_48312_d = field_48316_a.posY;
				this.field_48313_e = field_48316_a.posZ + (field_48316_a.fearSource.posZ - field_48316_a.posZ)*10;
				return true;
			}
		}
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting()
	{
		Vec3D var1 = RandomPositionGeneratorTFC.func_48622_a(this.field_48316_a, 5, 4);

		this.field_48315_c = field_48316_a.posX + (field_48316_a.fearSource.posX - field_48316_a.posX)*10;
		this.field_48312_d = field_48316_a.posY;
		this.field_48313_e = field_48316_a.posZ + (field_48316_a.fearSource.posZ - field_48316_a.posZ)*10;

		this.field_48316_a.getNavigator().tryMoveToXYZ(this.field_48315_c, this.field_48312_d, this.field_48313_e, this.speed);
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean continueExecuting()
	{
		return !this.field_48316_a.getNavigator().noPath();
	}
	
	public void resetTask(){
		field_48316_a.fearSource = null;
	}
}
