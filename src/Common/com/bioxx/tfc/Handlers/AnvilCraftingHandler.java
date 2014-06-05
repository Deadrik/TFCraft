package com.bioxx.tfc.Handlers;

import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.api.TFC_ItemHeat;
import com.bioxx.tfc.api.Events.AnvilCraftEvent;
import com.bioxx.tfc.api.Events.ItemMeltEvent;
import com.bioxx.tfc.api.Interfaces.IFood;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
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
			ItemStack out2 = new ItemStack(TFCItems.Bloom, 1, dam-(dam/100*100));
			TFC_ItemHeat.SetTemp(out1, temp);
			TFC_ItemHeat.SetTemp(out2, temp);
			if(!((EntityPlayer)event.entity).inventory.addItemStackToInventory(out1))
				event.entity.worldObj.spawnEntityInWorld(new EntityItem(event.entity.worldObj, event.entity.posX, event.entity.posY, event.entity.posZ, out1));

			if(out2.getItemDamage() > 0 && !((EntityPlayer)event.entity).inventory.addItemStackToInventory(out2))
				event.entity.worldObj.spawnEntityInWorld(new EntityItem(event.entity.worldObj, event.entity.posX, event.entity.posY, event.entity.posZ, out2));
			event.result = null;
		}
	}

	@SubscribeEvent
	public void onItemMelt(ItemMeltEvent event)
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
