package com.bioxx.tfc.Handlers.Client;

import com.bioxx.tfc.Render.RenderQuiver;

import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PlayerRenderHandler {

	public static final RenderQuiver renderQuiver = new RenderQuiver();
	
	@SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onPlayerRenderTick(RenderPlayerEvent.Specials.Pre e) {
		renderQuiver.render(e.entityLiving);
	}
	
	@SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent e) {
    	
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
		if (e.side.isClient()) {
			
		}
    }
}
