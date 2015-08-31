package com.bioxx.tfc.Handlers.Client;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;

import com.bioxx.tfc.Core.TFC_Time;

public class ClientTickHandler// implements ITickHandler
{
	@SubscribeEvent
	public void onClientPlayerTick(PlayerTickEvent event)
	{
		/*if(event.phase == Phase.START)
		{
//			TerraFirmaCraft.log.info("-------------PLAYER CLIENT TICK START--------------");
//			EntityPlayer player = event.player;
//			World world = player.worldObj;
		}*/
		
		if(event.phase == Phase.END)
		{
//			TerraFirmaCraft.log.info("-------------PLAYER CLIENT TICK END--------------");
			EntityPlayer player = event.player;
			World world = player.worldObj;

			//Allow the client to increment time
			TFC_Time.updateTime(world);
		}
	}




//	@Override
//	public void tickStart(EnumSet<TickType> type, Object... tickData) 
//	{
//		if(type.contains(TickType.PLAYER))
//		{
//			EntityPlayer player = (EntityPlayer)tickData[0];
//			World world = player.worldObj;
//		}
//	}
//
//	@Override
//	public void tickEnd(EnumSet<TickType> type, Object... tickData) 
//	{
//		if(type.contains(TickType.PLAYER))
//		{
//			EntityPlayer player = (EntityPlayer)tickData[0];
//			World world = player.worldObj;
//
//			//Allow the client to increment time
//			TFC_Time.UpdateTime(world);
//		}
//	}
//
//	@Override
//	public EnumSet<TickType> ticks() {
//		// TODO Auto-generated method stub
//		return EnumSet.of(TickType.PLAYER, TickType.RENDER);
//	}
//
//	@Override
//	public String getLabel() {
//		// TODO Auto-generated method stub
//		return "TFC Client";
//	}

}
