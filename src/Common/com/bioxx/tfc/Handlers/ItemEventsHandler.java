package com.bioxx.tfc.Handlers;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import com.bioxx.tfc.TileEntities.TEFirepit;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;

/**
 * @author Dries007
 */
public class ItemEventsHandler
{
	@SubscribeEvent
	public void onItemExpire(ItemExpireEvent event)
	{
		EntityItem entity = event.entityItem;
		World world = entity.worldObj;
		if (!world.isRemote)
		{
			Item item = entity.getEntityItem().getItem();
			if (item == TFCItems.Logs || item == Item.getItemFromBlock(TFCBlocks.Peat))
			{
				if (world.getTileEntity(MathHelper.floor_double(entity.posX), MathHelper.floor_double(entity.posY), MathHelper.floor_double(entity.posZ)) instanceof TEFirepit)
				{
					event.setCanceled(true); // Adds the normal lifetime of the itemstack back
				}
			}
		}
	}
}
