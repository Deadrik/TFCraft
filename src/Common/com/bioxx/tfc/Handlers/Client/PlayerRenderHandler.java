package com.bioxx.tfc.Handlers.Client;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import net.minecraftforge.client.event.RenderPlayerEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Core.Player.InventoryPlayerTFC;
import com.bioxx.tfc.Items.ItemQuiver;
import com.bioxx.tfc.Render.RenderLargeItem;
import com.bioxx.tfc.Render.RenderQuiver;

public class PlayerRenderHandler {

	public static final RenderQuiver RENDER_QUIVER = new RenderQuiver();
	public static final RenderLargeItem RENDER_LARGE = new RenderLargeItem();
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onPlayerRenderTick(RenderPlayerEvent.Specials.Pre e) {
		EntityLivingBase el = e.entityLiving;
		if(el instanceof EntityPlayer){
			if(((EntityPlayer)el).inventory instanceof InventoryPlayerTFC){
				ItemStack[] equipables = ((InventoryPlayerTFC)((EntityPlayer)el).inventory).extraEquipInventory;
				for(ItemStack i : equipables){
					if(i != null && i.getItem() instanceof ItemQuiver){
						RENDER_QUIVER.render(e.entityLiving,i);
					}
					else if(i != null){
						RENDER_LARGE.render(el, i);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent e) {

	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onPlayerTick(TickEvent.PlayerTickEvent e) {
		/*if (e.side.isClient()) {
		
		}*/
	}
}
