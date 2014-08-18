package com.bioxx.tfc.Handlers.Client;

import net.minecraftforge.client.event.RenderPlayerEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PlayerRenderHandler {

	@SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onPlayerRenderTick(RenderPlayerEvent.Specials.Pre e) {
		
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
