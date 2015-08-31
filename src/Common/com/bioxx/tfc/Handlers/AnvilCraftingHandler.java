package com.bioxx.tfc.Handlers;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.TFC_ItemHeat;
import com.bioxx.tfc.api.Events.AnvilCraftEvent;
import com.bioxx.tfc.api.Events.ItemCookEvent;
import com.bioxx.tfc.api.Interfaces.IFood;

public class AnvilCraftingHandler
{
	@SubscribeEvent
	public void onAnvilCraft(AnvilCraftEvent event)
	{
		if(event.input1.getItem() == TFCItems.bloom && event.input1.getItemDamage() > 100)
		{
			TileEntity te = event.anvilTE;
			World world = te.getWorldObj();
			int dam = event.input1.getItemDamage();
			float temp = event.input1.getTagCompound()!=null?TFC_ItemHeat.getTemp(event.input1):0;
			int count = dam/100;
			int rem = dam % 100;
			while(count > 0)
			{
				ItemStack out1 = new ItemStack(TFCItems.bloom, 1, 100);
				TFC_ItemHeat.setTemp(out1, temp);

				EntityItem ei = new EntityItem(world, te.xCoord + 0.5, te.yCoord + 1.5, te.zCoord + 0.5, out1);
				ei.motionX = 0; ei.motionY = 0; ei.motionZ = 0; ei.delayBeforeCanPickup = 0;
				world.spawnEntityInWorld(ei);

				count--;
			}
			if(rem > 0)
			{
				ItemStack out2 = new ItemStack(TFCItems.bloom, 1, rem);
				TFC_ItemHeat.setTemp(out2, temp);

				EntityItem ei = new EntityItem(world, te.xCoord + 0.5, te.yCoord + 1.5, te.zCoord + 0.5, out2);
				ei.motionX = 0; ei.motionY = 0; ei.motionZ = 0; ei.delayBeforeCanPickup = 0;
				world.spawnEntityInWorld(ei);
			}
			event.result = null;
		}
	}

	/*private boolean isMetal(ItemStack is, Metal m)
	{
		if(((ISmeltable)is.getItem()).GetMetalType(is) == m)
			return true;
		return false;
	}*/

	@SubscribeEvent
	public void onItemMelt(ItemCookEvent event)
	{
		if(event.input1 != null)
		{
			if((event.input1.getItem() == TFCItems.bloom || event.input1.getItem() == TFCItems.rawBloom) && event.result.getItemDamage() > 100)
			{
				event.result = event.input1;
				event.result.setItemDamage(event.result.getItemDamage()-1);
			}
			else if((event.input1.getItem() == TFCItems.bloom || event.input1.getItem() == TFCItems.rawBloom) && event.result.getItemDamage() <= 100)
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
