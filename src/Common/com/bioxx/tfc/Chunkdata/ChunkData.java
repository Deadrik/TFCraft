package com.bioxx.tfc.Chunkdata;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.WorldGen.DataLayer;
import com.bioxx.tfc.api.TFCOptions;

public class ChunkData 
{
	public int chunkX;
	public int chunkZ;
	public long lastVisited;
	public long previousVisit;
	public int spawnProtection;
	public int protectionBuffer = TFCOptions.protectionBuffer >= 0 ? TFCOptions.protectionBuffer * -1 : -24; //Set buffer to default if invalid value in config.
	public int[] heightmap;
	public DataLayer[] rainfallMap;

	public int sluicedAmount;

	public float fishPop = -1;
	public static final float FISH_POP_MAX = 60;

	public int lastSpringGen;
	public int cropInfestation;
	public boolean isUnloaded;
	
	private final Chunk chunk;

	public ChunkData(Chunk chunk)
	{
		this.chunk = chunk;
		
		heightmap = new int[256];
		rainfallMap = new DataLayer[256];
	}

	public ChunkData(Chunk chunk, NBTTagCompound tag)
	{
		this.chunk = chunk;
		
		chunkX = tag.getInteger("chunkX");  // could be removed, but that's too radical
		chunkZ = tag.getInteger("chunkZ");
		lastVisited = tag.getLong("lastVisited");
		spawnProtection = tag.getInteger("spawnProtection");

		updateSpawnProtection();

		heightmap = tag.getIntArray("heightmap");
		if(heightmap.length == 0)
			heightmap = new int[256];
		sluicedAmount = tag.getInteger("sluicedAmount");

		lastSpringGen = tag.getInteger("lastSpringGen");
		cropInfestation = tag.getInteger("cropInfestation");

		fishPop = Math.min(tag.getFloat("fishPopulation"),FISH_POP_MAX);
	}

	public NBTTagCompound getTag()
	{
		NBTTagCompound tag = new NBTTagCompound();

		tag.setInteger("chunkX", chunkX);
		tag.setInteger("chunkZ", chunkZ);
		
		updateSpawnProtection();
		
		tag.setInteger("spawnProtection", spawnProtection);
		tag.setLong("lastVisited", lastVisited);
		tag.setIntArray("heightmap", heightmap);
		tag.setInteger("lastSpringGen", lastSpringGen);
		tag.setInteger("sluicedAmount", sluicedAmount);
		tag.setInteger("cropInfestation", cropInfestation);
		tag.setFloat("fishPopulation", Math.max(fishPop,-1F));
		return tag;
	}

	public ChunkData createNew(World world, int x, int z)
	{
		chunkX = x;
		chunkZ = z;
		lastVisited = 0;
		spawnProtection = protectionBuffer;
		lastSpringGen = TFC_Time.getYear();
		return this;
	}

	public int getSpawnProtectionWithUpdate()
	{
		updateSpawnProtection();

		if (spawnProtection > 24 * TFC_Time.daysInMonth * TFCOptions.maxProtectionMonths)
			spawnProtection = 24 * TFC_Time.daysInMonth * TFCOptions.maxProtectionMonths;

		return spawnProtection;
	}

	public void setSpawnProtectionWithUpdate(int amount)
	{
		updateSpawnProtection();
		
		spawnProtection += amount;

		if (spawnProtection > 24 * TFC_Time.daysInMonth * TFCOptions.maxProtectionMonths)
			spawnProtection = 24 * TFC_Time.daysInMonth * TFCOptions.maxProtectionMonths;
	}
	
	private void updateSpawnProtection()
	{
		long now = TFC_Time.getTotalTicks();
		
		if (lastVisited < now)
		{
			long visit = (now - lastVisited) / TFC_Time.HOUR_LENGTH;
			spawnProtection -= visit;
			lastVisited += visit * TFC_Time.HOUR_LENGTH;  // =now, but taking rounding from integer division above into account

			if (spawnProtection < protectionBuffer)
				spawnProtection = protectionBuffer;
		
			chunk.setChunkModified();
		}
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
		if (rainfallMap[x * z] != null)
			return rainfallMap[x * z].floatdata1;
		else
			return 0.0f;
	}
}
