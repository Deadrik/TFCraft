package com.bioxx.tfc.api.Events;

import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;

@Cancelable
public class ItemMeltEvent extends Event 
{	
	/**
	 * The item which is currently being worked.
	 */
	public ItemStack input1;
	/**
	 * The result item from the crafting process if it is allowed to finish.
	 */
	public ItemStack result;
	/**
	 * Fires when an item is about to melt
	 * @param i1 is the item which is currently melting
	 * @param r is the result item from the melt if allowed to finish
	 */
	public ItemMeltEvent(ItemStack i1, ItemStack r)
	{
		super();
		input1 = i1;
		result = r;
	}
}
