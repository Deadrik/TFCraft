package com.bioxx.tfc.Handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.event.entity.EntityEvent.EnteringChunk;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;

public class EnteringChunkHandler
{
	@SubscribeEvent
	public void onEnterChunk(EnteringChunk event)
	{
		if (event.entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)event.entity;
			if(!player.worldObj.isRemote)
			{
				NBTTagCompound nbt = player.getEntityData();
				long spawnProtectionTimer = nbt.hasKey("spawnProtectionTimer") ? nbt.getLong("spawnProtectionTimer") : TFC_Time.getTotalTicks() + TFC_Time.HOUR_LENGTH;


				if (TFC_Core.getCDM(event.entity.worldObj) != null && (event.newChunkX != event.oldChunkX || event.newChunkZ != event.oldChunkZ))
				{
					TFC_Core.getCDM(event.entity.worldObj).setLastVisted(event.oldChunkX, event.oldChunkZ);
					//Reset the timer since we've entered a new chunk
					spawnProtectionTimer = TFC_Time.getTotalTicks() + TFC_Time.HOUR_LENGTH;
					writeProtectionToNBT(nbt, spawnProtectionTimer);
				}
			}
		}
	}

	public void writeProtectionToNBT(NBTTagCompound nbt, long spawnProtectionTimer)
	{
		nbt.setLong("spawnProtectionTimer", spawnProtectionTimer);
	}
}
