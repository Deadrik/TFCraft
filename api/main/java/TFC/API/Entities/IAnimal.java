package TFC.API.Entities;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;

public interface IAnimal
{
	public enum GenderEnum
	{
		MALE,FEMALE;
		public static final GenderEnum[] genders = {MALE, FEMALE};
	}

	public GenderEnum getGender();
	
	public int getSex();

	public int getBirthDay();
	/**
	 * @return The length of time (in days) until this type of animal reatures maturity
	 */
	public int getNumberOfDaysToAdult();

	public boolean isAdult();
	
	//public int getSex();
	/**
	 * @return Size modifier of the animal. Used for rendering and various other purposes.
	 */
	public float getSize();

	public boolean isPregnant();
	
	public EntityAgeable createChildTFC(EntityAgeable entityageable);

	//	It would be nice to call this isInLove() except that would shadow
	//	EntityAnimal.isInLove() causing MCP to obfuscate this method, which
	//	we don't want because the callers won't be obfuscated.
	//	
	public boolean getInLove();

	public void setInLove(boolean b);

	public EntityLiving getEntity();

	public boolean canMateWith(IAnimal animal);

	public void mate(IAnimal animal);

	public long getAnimalID();
	
	public void setAge(int par1);

	public void setAnimalID(long id);

	public int getHunger();

	public void setHunger(int h);

}
