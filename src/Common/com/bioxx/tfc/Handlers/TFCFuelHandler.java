package com.bioxx.tfc.Handlers;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.IFuelHandler;

public class TFCFuelHandler implements IFuelHandler
{
	public static Map<Item, Integer> fuelItems = new HashMap<Item, Integer>();

	public static void registerFuel(Item item, int burnTime)
	{
		if(!fuelItems.containsKey(item))
			fuelItems.put(item, burnTime);
	}

	@Override
	public int getBurnTime(ItemStack is)
	{
		Item item = is.getItem();
		Integer burnTime = fuelItems.get(item);
		if(burnTime != null) return burnTime;

		return 0;
	}
}
