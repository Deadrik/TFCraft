package com.bioxx.tfc.Handlers;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.api.TFCOptions;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;

public class ServerTickHandler
{
	long wSeed = Long.MIN_VALUE;
	public int ticks = 0;
	@SubscribeEvent
	public void onServerWorldTick(WorldTickEvent event)
	{
		World world = event.world;
		if(event.phase == Phase.START)
		{
			if(world.provider.dimensionId == 0 && world.getWorldInfo().getSeed() != wSeed)
			{
				TFC_Core.SetupWorld(world);
				wSeed = world.getWorldInfo().getSeed();
			}
			TFC_Time.UpdateTime(world);

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
		else if(event.phase == Phase.END)
		{
			/*if(ServerOverrides.isServerEmpty())
				return;*/


		}
	}

	//	private int chunkPruneTimer = 0;
	//	
	//	@Override
	//	public void tickStart(EnumSet<TickType> type, Object... tickData) 
	//	{
	//		if(type.contains(TickType.WORLDLOAD))
	//		{
	//			World world = (World)tickData[0];
	//			if(world.provider.dimensionId == 0)
	//			{
	//				TFC_Core.SetupWorld(world);
	//			}
	//		}
	//		
	//		if(type.contains(TickType.WORLD))
	//		{
	//			WorldServer world = (WorldServer)tickData[0];
	//			//Allow the server to increment time
	//			TFC_Time.UpdateTime(world);
	//		}
	//
	//		if (type.contains(TickType.PLAYER))
	//		{
	//			World world;
	//			EntityPlayer player = (EntityPlayer)tickData[0];
	//			world = player.worldObj;
	//		}
	//	}
	//
	//	@Override
	//	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
	//		// TODO Auto-generated method stub
	//
	//	}
	//
	//	@Override
	//	public EnumSet<TickType> ticks() {
	//		// TODO Auto-generated method stub
	//		return EnumSet.of(TickType.WORLD, TickType.WORLDLOAD, TickType.PLAYER);
	//	}
	//
	//	@Override
	//	public String getLabel() {
	//		// TODO Auto-generated method stub
	//		return "TFC Server";
	//	}
}
