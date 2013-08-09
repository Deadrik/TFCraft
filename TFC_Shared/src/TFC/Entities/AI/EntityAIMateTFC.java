package TFC.Entities.AI;

import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;
import TFC.API.Entities.IAnimal;
import TFC.API.Entities.IAnimal.GenderEnum;

public class EntityAIMateTFC extends EntityAIBase
{
	private IAnimal theAnimal;
	World theWorld;
	private IAnimal targetMate;
	int field_48261_b;
	float field_48262_c;

	public EntityAIMateTFC (IAnimal par1EntityAnimal, World world, float par2)
	{
		field_48261_b = 0;
		theAnimal = par1EntityAnimal;
		theWorld = world;
		field_48262_c = par2;
		setMutexBits (3);
	}


	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute ()
	{
		if (!theAnimal.isInLove())
		{
			return false;
		}
		else
		{
			targetMate = func_48258_h();
			if (targetMate != null){
				if ((targetMate.getGender() == GenderEnum.MALE && theAnimal.getGender() == GenderEnum.MALE) 
						|| (theAnimal.isPregnant() || targetMate.isPregnant())){
					return false;
				}
			}
			return targetMate != null;
		}
	}


	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean continueExecuting ()
	{
		return targetMate.getEntity().isEntityAlive() && targetMate.isInLove() && field_48261_b < 60 && 
				((targetMate.getGender() == GenderEnum.FEMALE && theAnimal.getGender() == GenderEnum.MALE) || 
						(targetMate.getGender() == GenderEnum.MALE && theAnimal.getGender() == GenderEnum.FEMALE));
	}


	/**
	 * Resets the task
	 */
	@Override
	public void resetTask ()
	{
		if (!theAnimal.matesForLife())
		{
			targetMate.setMate(null);
			targetMate = null;
			theAnimal.setMate(null);
			field_48261_b = 0;
		}
	}


	/**
	 * Updates the task
	 */
	@Override
	public void updateTask ()
	{
		theAnimal.getEntity().getLookHelper().setLookPositionWithEntity(targetMate.getEntity(), 10F, 
				theAnimal.getEntity().getVerticalFaceSpeed());
		theAnimal.getEntity().getNavigator().tryMoveToEntityLiving(targetMate.getEntity(), field_48262_c);
		field_48261_b++;

		if (field_48261_b == 60)
		{
			theAnimal.mate(targetMate);
		}
	}


	private IAnimal func_48258_h ()
	{
		float f = 8F;
		List list = theWorld.getEntitiesWithinAABB (theAnimal.getClass (), theAnimal.getEntity().boundingBox.expand (f, f, f));

		for (Iterator iterator = list.iterator () ; iterator.hasNext () ;)
		{
			Entity entity = (Entity) iterator.next();
			if(entity instanceof IAnimal)
			{
				IAnimal entityanimal = (IAnimal) entity;
				if (theAnimal.canMateWith(entityanimal))
				{
					return entityanimal;
				}
			}
		}

		return null;
	}
}
