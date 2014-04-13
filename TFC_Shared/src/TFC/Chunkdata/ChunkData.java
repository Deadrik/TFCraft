package TFC.Chunkdata;

import net.minecraft.nbt.NBTTagCompound;
import TFC.Core.TFC_Time;

public class ChunkData 
{
	public int chunkX;
	public int chunkZ;
	public long lastVisited;
	public long previousVisit;
	public int spawnProtection;
	public int[] heightmap;
	public int sluicedAmount = 0;

	public int lastSpringGen;

	public ChunkData()
	{
		heightmap = new int[256];
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

		lastVisited = TFC_Time.getTotalTicks();

		heightmap = tag.getIntArray("heightmap");
		if(heightmap.length == 0)
			heightmap = new int[256];
		sluicedAmount = tag.getInteger("sluicedAmount");

		lastSpringGen = tag.getInteger("lastSpringGen");
	}

	public NBTTagCompound getTag()
	{
		NBTTagCompound tag = new NBTTagCompound("Spawn Protection");

		tag.setInteger("chunkX", chunkX);
		tag.setInteger("chunkZ", chunkZ);
		long visit = (TFC_Time.getTotalTicks() - lastVisited) / TFC_Time.hourLength;
		spawnProtection -= visit;
		if(spawnProtection < -24)
			spawnProtection = -24;
		tag.setInteger("spawnProtection", spawnProtection);
		tag.setLong("lastVisited", lastVisited);
		tag.setIntArray("heightmap", heightmap);
		tag.setInteger("lastSpringGen", lastSpringGen);
		tag.setInteger("sluicedAmount", sluicedAmount);
		return tag;
	}

	public ChunkData CreateNew(int x, int z)
	{
		chunkX = x;
		chunkZ = z;
		lastVisited = 0;
		spawnProtection = -24;
		lastSpringGen = TFC_Time.getYear();
		return this;
	}

	public int getSpawnProtectionWithUpdate()
	{
		long visit = (TFC_Time.getTotalTicks() - lastVisited) / TFC_Time.hourLength;
		spawnProtection -= visit;
		if(spawnProtection < -24)
			spawnProtection = -24;

		lastVisited = TFC_Time.getTotalTicks();

		if(spawnProtection > 4320)
			spawnProtection = 4320;

		return spawnProtection;
	}

	public void setSpawnProtectionWithUpdate(int amount)
	{
		long visit = (TFC_Time.getTotalTicks() - lastVisited) / TFC_Time.hourLength;
		spawnProtection -= visit;

		if(spawnProtection < -24)
			spawnProtection = -24;

		spawnProtection += amount;

		if(spawnProtection > 4320)
			spawnProtection = 4320;

		lastVisited = TFC_Time.getTotalTicks();
	}
}
