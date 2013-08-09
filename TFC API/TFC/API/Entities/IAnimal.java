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

	public boolean matesForLife();

	public boolean isRutting();

	public boolean isPregnant();

	public boolean isInLove();

	public EntityLiving getEntity();

	public boolean canMateWith(IAnimal animal);

	public void mate(IAnimal animal);

	public IAnimal getMate();

	public void setMate(IAnimal mate);

}
