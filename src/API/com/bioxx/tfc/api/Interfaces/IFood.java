package com.bioxx.tfc.api.Interfaces;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.bioxx.tfc.api.Enums.EnumFoodGroup;

public interface IFood
{
	EnumFoodGroup getFoodGroup();

	int getFoodID();

	float getDecayRate(ItemStack is);

	float getFoodMaxWeight(ItemStack is);

	/**
	 * @return Returns an ItemStack that will replace the current ItemStack when the food has reached maximum decay.
	 * Normally returns null.
	 */
	ItemStack onDecayed(ItemStack is, World world, int i, int j, int k);
	/**
	 * @return Is this food edible as is.
	 */
	boolean isEdible(ItemStack is);
	/**
	 * @return Is this item usable in meals
	 */
	boolean isUsable(ItemStack is);

	int getTasteSweet(ItemStack is);

	int getTasteSour(ItemStack is);

	int getTasteSalty(ItemStack is);

	int getTasteBitter(ItemStack is);

	int getTasteSavory(ItemStack is);

	boolean renderDecay();

	boolean renderWeight();
}
