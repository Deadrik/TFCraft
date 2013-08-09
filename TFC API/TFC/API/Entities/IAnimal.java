package TFC.API.Entities;

import net.minecraft.entity.EntityLiving;

public interface IAnimal
{
	public enum GenderEnum
	{
		MALE,FEMALE;
		public static final GenderEnum[] genders = {FEMALE, MALE};
	}

	public GenderEnum getGender();

	public int getAge();
	/**
	 * @return The length of time (in days) until this type of animal reatures maturity
	 */
	public int getNumberOfDaysToAdult();

	public boolean isAdult();
	/**
	 * @return Size modifier of the animal. Used for rendering and various other purposes.
	 */
	public float getSize();

	public boolean isPregnant();

	public boolean isInLove();

	public void setInLove(boolean b);

	public EntityLiving getEntity();

	public boolean canMateWith(IAnimal animal);

	public void mate(IAnimal animal);

	public long getAnimalID();

	public void setAnimalID(long id);

	public int getHunger();

	public void setHunger(int h);

}
