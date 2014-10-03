package com.bioxx.tfc.Chunkdata;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.WorldGen.DataLayer;

public class ChunkData 
{
	public int chunkX;
	public int chunkZ;
	public long lastVisited;
	public long previousVisit;
	public int spawnProtection;
	public int[] heightmap;
	public DataLayer[] rainfallMap;

	public int sluicedAmount = 0;

	public float fishPop = -1;
	public static final float fishPopMax = 60;

	public int lastSpringGen;
	public int cropInfestation = 0;

	public ChunkData()
	{
		heightmap = new int[256];
		rainfallMap = new DataLayer[256];
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
		cropInfestation = tag.getInteger("cropInfestation");

		fishPop = Math.min(tag.getFloat("fishPopulation"),fishPopMax);
	}

	public NBTTagCompound getTag()
	{
		NBTTagCompound tag = new NBTTagCompound();

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
		tag.setInteger("cropInfestation", cropInfestation);
		tag.setFloat("fishPopulation", Math.max(fishPop,-1F));
		return tag;
	}

	public ChunkData CreateNew(World world, int x, int z)
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

	public void infest()
	{
		Math.min(cropInfestation++, 10);
	}

	public void uninfest()
	{
		Math.max(cropInfestation--, 0);
	}

	/**
	 * Returns a cached rainfall value for this chunk. The cache is created client side when the chunk loads on the client.
	 * @param x Chunk X
	 * @param z Chunk Z
	 */
	public float getRainfall(int x, int z)
	{
		return rainfallMap[x*z].floatdata1;
	}
}
