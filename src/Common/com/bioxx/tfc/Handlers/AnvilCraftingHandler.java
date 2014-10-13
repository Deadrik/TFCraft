package com.bioxx.tfc.Handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.Metal;
import com.bioxx.tfc.api.TFC_ItemHeat;
import com.bioxx.tfc.api.Events.AnvilCraftEvent;
import com.bioxx.tfc.api.Events.ItemCookEvent;
import com.bioxx.tfc.api.Interfaces.IFood;
import com.bioxx.tfc.api.Interfaces.ISmeltable;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class AnvilCraftingHandler
{
	@SubscribeEvent
	public void onAnvilCraft(AnvilCraftEvent event)
	{
		if(event.input1.getItem() == TFCItems.Bloom && event.input1.getItemDamage() > 100)
		{
			int dam = event.input1.getItemDamage();
			float temp = event.input1.getTagCompound()!=null?TFC_ItemHeat.GetTemp(event.input1):0;
			ItemStack out1 = new ItemStack(TFCItems.Bloom, dam/100, 100);
			ItemStack out2 = new ItemStack(TFCItems.Bloom, 1, dam % 100);
			TFC_ItemHeat.SetTemp(out1, temp);
			TFC_ItemHeat.SetTemp(out2, temp);
			TFC_Core.giveItemToPlayer(out1, (EntityPlayer)event.entity);
			TFC_Core.giveItemToPlayer(out2, (EntityPlayer)event.entity);
			event.result = null;
		}
	}

	private boolean isMetal(ItemStack is, Metal m)
	{
		if(((ISmeltable)is.getItem()).GetMetalType(is) == m)
			return true;
		return false;
	}

	@SubscribeEvent
	public void onItemMelt(ItemCookEvent event)
	{
		if(event.input1 != null)
		{
			if((event.input1.getItem() == TFCItems.Bloom || event.input1.getItem() == TFCItems.RawBloom) && event.result.getItemDamage() > 100)
			{
				event.result = event.input1;
				event.result.setItemDamage(event.result.getItemDamage()-1);
			}
			else if((event.input1.getItem() == TFCItems.Bloom || event.input1.getItem() == TFCItems.RawBloom) && event.result.getItemDamage() <= 100)
			{
				event.result.setItemDamage(100-event.input1.getItemDamage());
			}
			else if (event.result != null && event.result.getItem() instanceof IFood)
			{
				event.result.stackTagCompound = event.input1.stackTagCompound;
			}
		}
	}
}
