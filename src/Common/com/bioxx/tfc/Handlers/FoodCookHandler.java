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
			float mod = 1.0f;
			if(!((IFood)event.result.getItem()).canSmoke())
				mod = 0.5f;

			TFC_Core.setSweetMod(event.result, Math.round(event.fuelTasteMod[0] * mod));
			TFC_Core.setSourMod(event.result, Math.round(event.fuelTasteMod[1] * mod));
			TFC_Core.setSaltyMod(event.result, Math.round(event.fuelTasteMod[2] * mod));
			TFC_Core.setBitterMod(event.result, Math.round(event.fuelTasteMod[3] * mod));
			TFC_Core.setSavoryMod(event.result, Math.round(event.fuelTasteMod[4] * mod));
		}
	}
}
