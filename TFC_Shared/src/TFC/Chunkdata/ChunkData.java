package TFC.Chunkdata;

import TFC.Core.TFC_Time;
import net.minecraft.src.NBTTagCompound;

public class ChunkData 
{
	public int chunkX;
	public int chunkZ;
	public long lastVisited;
	public int spawnProtection;
	
	
	public ChunkData()
	{

	}
	
	public ChunkData(NBTTagCompound tag)
	{
		chunkX = tag.getInteger("chunkX");
		chunkZ = tag.getInteger("chunkZ");
		lastVisited = tag.getLong("lastVisited");
		spawnProtection = tag.getInteger("spawnProtection");
		
		long visit = (TFC_Time.getTotalTicks() - lastVisited) / TFC_Time.hourLength;
		spawnProtection -= visit;
		if(spawnProtection < -24)
			spawnProtection = -24;
	}
	
	public NBTTagCompound getTag()
	{
		NBTTagCompound tag = new NBTTagCompound("Spawn Protection");
		
		tag.setInteger("chunkX", chunkX);
		tag.setInteger("chunkZ", chunkZ);
		tag.setInteger("spawnProtection", spawnProtection);
		tag.setLong("lastVisited", lastVisited);
		return tag;
	}
	
	public ChunkData CreateNew(int x, int z)
	{
		chunkX = x;
		chunkZ = z;
		lastVisited = 0;
		spawnProtection = -24;
		return this;
	}
}
