package com.bioxx.tfc.api.Entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;

public interface IAnimal
{
	public enum GenderEnum
	{
		MALE,FEMALE;
		public static final GenderEnum[] GENDERS = {MALE, FEMALE};
	}
	
	public enum InteractionEnum
	{
		MOUNT,SHEAR,MILK,BREED, NAME, TOLERATEPLAYER;
		public static final InteractionEnum[] INTERACTIONS = {MOUNT,SHEAR,MILK,BREED, NAME, TOLERATEPLAYER};
	}

	boolean canFamiliarize();

	boolean canMateWith(IAnimal animal);

	boolean checkFamiliarity(InteractionEnum interaction, EntityPlayer player);

	EntityAgeable createChildTFC(EntityAgeable entityageable);

	/**
	 * Represents interaction with the animal that makes the animal happy, thus increasing it's familiarization
	 * @param ep
	 */
	void familiarize(EntityPlayer ep);

	int getAge();

	/**
	 * @return Aggression modifier of the animal. Used for rendering and various other purposes.
	 * Aggression affects how likely an animal is to attack another player or animal and the ferocity of which it does so.
	 */
	float getAggressionMod();

	int getAnimalTypeID();

	Vec3 getAttackedVec();

	int getBirthDay();

	int getDueDay();

	EntityLiving getEntity();
	
	/**
	 * Represents how familiar the animal is with players. This is used for most human interaction.
	 * @return	familiarity
	 */
	int getFamiliarity();

	boolean getFamiliarizedToday();

	Entity getFearSource();

	GenderEnum getGender();

	int getHunger();

	//	It would be nice to call this isInLove() except that would shadow
	//	EntityAnimal.isInLove() causing MCP to obfuscate this method, which
	//	we don't want because the callers won't be obfuscated.
	//	
	boolean getInLove();
	/**
	 * @return The length of time (in days) until this type of animal reatures maturity
	 */
	int getNumberOfDaysToAdult();

	/**
	 * @return Obedience modifier of the animal. Used for rendering and various other purposes.
	 * Obedience affects how well the animal responds to player commands and how willingly it does so.
	 * Obedience and Aggression collectively determine whether an animal is domesticated.
	 */
	float getObedienceMod();

	//public int getSex();
	/**
	 * @return Size modifier of the animal. Used for rendering and various other purposes.
	 * Size is primarilly affected by the area the animal lives in; forests generally have smaller animals, as do colder climates.
	 */
	float getSizeMod();

	/**
	 * @return Strength modifier of the animal. Used for rendering and various other purposes.
	 * Absolute strength is affected by size, but inherant musculature also plays a roll.
	 */
	float getStrengthMod();

	/**
	 * Used to calculate whether an animal should become more familiar or less familiar with players
	 */
	void handleFamiliarityUpdate();

	boolean isAdult();

	/**
	 * separate from isBreedingItem, this just determines if an animal would eat from an itemstack.
	 * @param item the item
	 * @return see desc.
	 */
	boolean isFood(ItemStack item);

	boolean isPregnant();

	void mate(IAnimal animal);

	void setAge(int age);

	void setAggressionMod(float aggression);

	void setAttackedVec(Vec3 attackedVec);

	void setBirthDay(int day);
	
	void setFamiliarity(int f);
	
	void setFearSource(Entity fearSource);

	void setHunger(int h);

	void setInLove(boolean b);
	
	void setObedienceMod(float obedience);
	
	void setSizeMod(float size);

	void setStrengthMod(float strength);

	boolean trySetName(String name, EntityPlayer player);
}
