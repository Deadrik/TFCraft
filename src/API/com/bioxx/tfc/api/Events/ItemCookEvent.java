package com.bioxx.tfc.api.Events;

import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;

@Cancelable
public class ItemCookEvent extends Event 
{	
	/**
	 * The item which is currently being cooked.
	 */
	public ItemStack input1;
	/**
	 * The result item from the cooking process if it is allowed to finish.
	 */
	public ItemStack result;
	/**
	 * Fires when an item is about to cook
	 * @param i1 is the item which is currently cooking
	 * @param r is the result item from the melt if allowed to finish
	 */
	public ItemCookEvent(ItemStack i1, ItemStack r)
	{
		super();
		input1 = i1;
		result = r;
	}

	public static class Food extends ItemCookEvent 
	{
		public int[] fuelTasteMod;
		public Food(ItemStack i1, ItemStack r, int[] fuelTaste) 
		{
			super(i1, r);
			fuelTasteMod = fuelTaste;
		}

	}
}
