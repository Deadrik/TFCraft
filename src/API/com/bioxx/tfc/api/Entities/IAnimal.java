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
		public static final GenderEnum[] genders = {MALE, FEMALE};
	}
	
	public enum InteractionEnum
	{
		MOUNT,SHEAR,MILK,BREED, NAME, TOLERATEPLAYER;
		public static final InteractionEnum[] interactions = {MOUNT,SHEAR,MILK,BREED, NAME, TOLERATEPLAYER};
	}

	public GenderEnum getGender();

	public int getBirthDay();

	public int getDueDay();

	/**
	 * @return The length of time (in days) until this type of animal reatures maturity
	 */
	public int getNumberOfDaysToAdult();

	public boolean isAdult();

	//public int getSex();
	/**
	 * @return Size modifier of the animal. Used for rendering and various other purposes.
	 * Size is primarilly affected by the area the animal lives in; forests generally have smaller animals, as do colder climates.
	 */
	public float getSize();

	/**
	 * @return Strength modifier of the animal. Used for rendering and various other purposes.
	 * Absolute strength is affected by size, but inherant musculature also plays a roll.
	 */
	public float getStrength();

	/**
	 * @return Aggression modifier of the animal. Used for rendering and various other purposes.
	 * Aggression affects how likely an animal is to attack another player or animal and the ferocity of which it does so.
	 */
	public float getAggression();
	
	/**
	 * separate from isBreedingItem, this just determines if an animal would eat from an itemstack.
	 * @param item the item
	 * @return see desc.
	 */
	public boolean isFood(ItemStack item);

	/**
	 * @return Obedience modifier of the animal. Used for rendering and various other purposes.
	 * Obedience affects how well the animal responds to player commands and how willingly it does so.
	 * Obedience and Aggression collectively determine whether an animal is domesticated.
	 */
	public float getObedience();

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

	public void setAge(int par1);

	public int getHunger();

	public Vec3 getAttackedVec();

	public void setAttackedVec(Vec3 attackedVec);

	public Entity getFearSource();

	public void setFearSource(Entity fearSource);

	public void setHunger(int h);

	public int getAnimalTypeID();
	
	public boolean trySetName(String name, EntityPlayer player);
	
	/**
	 * Represents how familiar the animal is with players. This is used for most human interaction.
	 * @return	familiarity
	 */
	public int getFamiliarity();
	/**
	 * Used to calculate whether an animal should become more familiar or less familiar with players
	 */
	public void handleFamiliarityUpdate();
	
	/**
	 * Represents interaction with the animal that makes the animal happy, thus increasing it's familiarization
	 * @param ep
	 */
	public void familiarize(EntityPlayer ep);
	
	public boolean checkFamiliarity(InteractionEnum interaction, EntityPlayer player);

	public boolean getFamiliarizedToday();
}
