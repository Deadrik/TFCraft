package com.bioxx.tfc.Handlers;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.world.ChunkDataEvent;

import com.bioxx.tfc.Chunkdata.ChunkData;
import com.bioxx.tfc.Chunkdata.ChunkDataManager;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ChunkDataEventHandler 
{
	@SubscribeEvent
	public void onDataLoad(ChunkDataEvent.Load event)
	{
		if(!event.world.isRemote)
		{
			NBTTagCompound eventTag = event.getData();

			if(eventTag.hasKey("Spawn Protection"))
			{
				NBTTagCompound spawnProtectionTag = eventTag.getCompoundTag("Spawn Protection");
				ChunkData data = new ChunkData(spawnProtectionTag);
				ChunkDataManager.addData(data.chunkX, data.chunkZ, data);
			}
			else
			{
				NBTTagCompound levelTag = eventTag.getCompoundTag("Level");
				ChunkData data = new ChunkData().CreateNew(levelTag.getInteger("xPos"), levelTag.getInteger("zPos"));
				ChunkDataManager.addData(data.chunkX, data.chunkZ, data);
			}
		}
	}

	@SubscribeEvent
	public void onDataSave(ChunkDataEvent.Save event)
	{
		if(!event.world.isRemote)
		{
			NBTTagCompound levelTag = event.getData().getCompoundTag("Level");
			int x = levelTag.getInteger("xPos");
			int z = levelTag.getInteger("zPos");
			ChunkData data = ChunkDataManager.getData(x, z);

			if(data != null)
			{
				NBTTagCompound spawnProtectionTag = data.getTag();
				// Why was this line here in the first place?
				//spawnProtectionTag = new NBTTagCompound();
				event.getData().setTag("Spawn Protection", spawnProtectionTag);
			} else
				System.out.println("Attempting to save Chunkdata that has already been unloaded.");
		}
	}
}
