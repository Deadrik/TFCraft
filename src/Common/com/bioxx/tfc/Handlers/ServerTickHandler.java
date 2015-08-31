package com.bioxx.tfc.Handlers;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.api.TFCOptions;

public class ServerTickHandler
{
	private long wSeed = Long.MIN_VALUE;
	public int ticks;
	@SubscribeEvent
	public void onServerWorldTick(WorldTickEvent event)
	{
		World world = event.world;
		if(event.phase == Phase.START)
		{
			if(world.provider.dimensionId == 0 && world.getWorldInfo().getSeed() != wSeed)
			{
				TFC_Core.setupWorld(world);
				wSeed = world.getWorldInfo().getSeed();
			}
			TFC_Time.updateTime(world);

			/*if(ServerOverrides.isServerEmpty())
				return;*/
			if(MinecraftServer.getServer().getCurrentPlayerCount() == 0 && TFCOptions.simSpeedNoPlayers > 0)
			{
				ticks++;
				long t = world.getWorldInfo().getWorldTotalTime();
				long w = world.getWorldInfo().getWorldTime();
				if(ticks < TFCOptions.simSpeedNoPlayers)
				{
					world.getWorldInfo().incrementTotalWorldTime(t-1L);
					world.getWorldInfo().setWorldTime(w-1L);
				}
				else
				{
					ticks = 0;
				}
			}
		}
		/*else if(event.phase == Phase.END)
		{
		
		}*/
	}
}
