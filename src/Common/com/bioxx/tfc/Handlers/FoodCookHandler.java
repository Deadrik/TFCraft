package com.bioxx.tfc.Handlers;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.Events.ItemCookEvent;
import com.bioxx.tfc.api.Interfaces.IFood;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class FoodCookHandler
{
	@SubscribeEvent
	public void onAnvilCraft(ItemCookEvent.Food event)
	{
		if(event.result != null && event.result.getItem() instanceof IFood)
		{
			TFC_Core.setSweetMod(event.result, event.fuelTasteMod[0]);
			TFC_Core.setSourMod(event.result, event.fuelTasteMod[1]);
			TFC_Core.setSaltyMod(event.result, event.fuelTasteMod[2]);
			TFC_Core.setBitterMod(event.result, event.fuelTasteMod[3]);
			TFC_Core.setSavoryMod(event.result, event.fuelTasteMod[4]);
		}
	}
}
