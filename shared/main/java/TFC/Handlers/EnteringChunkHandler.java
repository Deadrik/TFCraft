package TFC.Handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityEvent.EnteringChunk;
import TFC.Chunkdata.ChunkDataManager;
import TFC.Core.TFC_Time;

public class EnteringChunkHandler
{
	@ForgeSubscribe
	public void onEnterChunk(EnteringChunk event) 
	{
		if (event.entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)event.entity;

			if(!player.worldObj.isRemote)
			{
				NBTTagCompound nbt = player.getEntityData();
				long spawnProtectionTimer = nbt.hasKey("spawnProtectionTimer") ? nbt.getLong("spawnProtectionTimer") : TFC_Time.getTotalTicks() + TFC_Time.hourLength;
						
				
				if(event.newChunkX != event.oldChunkX || event.newChunkZ != event.oldChunkZ )
				{
					ChunkDataManager.setLastVisted(event.oldChunkX, event.oldChunkZ);
					//Reset the timer since we've entered a new chunk
					spawnProtectionTimer = TFC_Time.getTotalTicks() + TFC_Time.hourLength;
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
