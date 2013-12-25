package TFC.Handlers;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.ChunkDataEvent;
import TFC.Chunkdata.ChunkData;
import TFC.Chunkdata.ChunkDataManager;

public class ChunkDataEventHandler 
{
	@ForgeSubscribe
	public void onDataLoad(ChunkDataEvent.Load event) 
	{
		if(!event.world.isRemote)
		{
			NBTTagCompound eventTag = event.getData();

			if(eventTag.hasKey("Spawn Protection"))
			{
				NBTTagCompound spawnProtectionTag = eventTag.getCompoundTag("Spawn Protection");
				ChunkData data = new ChunkData(spawnProtectionTag);
				String key = data.chunkX + "," + data.chunkZ;
				ChunkDataManager.chunkmap.put(key, data);
			}
			else
			{
				NBTTagCompound levelTag = eventTag.getCompoundTag("Level");
				ChunkData data = new ChunkData().CreateNew(levelTag.getInteger("xPos"), levelTag.getInteger("zPos"));
				String key = data.chunkX + "," + data.chunkZ;
				ChunkDataManager.chunkmap.put(key, data);
			}
		}
	}

	@ForgeSubscribe
	public void onDataSave(ChunkDataEvent.Save event) 
	{
		if(!event.world.isRemote)
		{
			NBTTagCompound eventTag = event.getData();
			NBTTagCompound levelTag = eventTag.getCompoundTag("Level");

			int x = levelTag.getInteger("xPos");
			int z = levelTag.getInteger("zPos");
			String key = x + "," + z;
			if(ChunkDataManager.chunkmap.containsKey(key))
			{
				NBTTagCompound spawnProtectionTag = ((ChunkData)ChunkDataManager.chunkmap.get(key)).getTag();
				if(spawnProtectionTag != null)
				{
					eventTag.setCompoundTag("Spawn Protection", spawnProtectionTag);
				}
			}
		}
	}
}
