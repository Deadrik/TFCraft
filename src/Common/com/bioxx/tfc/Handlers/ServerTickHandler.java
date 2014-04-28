package com.bioxx.tfc.Handlers;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;

import net.minecraft.world.World;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;

public class ServerTickHandler
{
	@SubscribeEvent
	public void onServerWorldTick(WorldTickEvent event)
	{
		if(event.phase == Phase.START)
		{
//			System.out.println("-------------WORLD SERVER TICK START--------------");
			World world = event.world;
			if(world.provider.dimensionId == 0)
				TFC_Core.SetupWorld(world);
			TFC_Time.UpdateTime(world);
		}
	}

	@SubscribeEvent
	public void onServerPlayerTick(PlayerTickEvent event)
	{
		if(event.phase == Phase.START)
		{
//			System.out.println("-------------PLAYER SERVER TICK START--------------");
//			EntityPlayer player = event.player;
//			World world = player.worldObj;
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
