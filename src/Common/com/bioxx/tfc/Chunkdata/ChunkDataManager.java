package com.bioxx.tfc.Chunkdata;

import net.minecraft.util.LongHashMap;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.api.TFCOptions;

public class ChunkDataManager 
{
	///private World world;

	private LongHashMap chunkmap = new LongHashMap();

	public ChunkDataManager(World world)
	{
		//this.world = world;
	}

	public void removeData(int x, int z)
	{
		long key = ChunkCoordIntPair.chunkXZ2Int(x, z);
		if(chunkmap.containsItem(key))
		{
			chunkmap.remove(key);
		}
	}

	public void addData(long key, ChunkData cd)
	{
		chunkmap.add(key, cd);
	}

	public void addData(Chunk chunk, ChunkData cd)
	{
		chunkmap.add(ChunkCoordIntPair.chunkXZ2Int(chunk.xPosition, chunk.zPosition), cd);
	}

	public void addData(int x, int z, ChunkData cd)
	{
		chunkmap.add(ChunkCoordIntPair.chunkXZ2Int(x, z), cd);
	}

	public ChunkData getData(int x, int z)
	{
		long key = ChunkCoordIntPair.chunkXZ2Int(x, z);
		if(chunkmap.containsItem(key))
			return (ChunkData) chunkmap.getValueByKey(key);
		else return null;
	}

	public ChunkData getData(long key)
	{
		if(chunkmap.containsItem(key))
			return (ChunkData) chunkmap.getValueByKey(key);
		else return null;
	}

	public boolean hasData(long key)
	{
		return chunkmap.containsItem(key);
	}

	public boolean addProtection(int x, int z, int amount)
	{
		ChunkData d = getData(x,z);
		if(d != null)
		{
			if(d.spawnProtection < 24*TFC_Time.daysInMonth*TFCOptions.maxProtectionMonths)
				d.setSpawnProtectionWithUpdate(amount);
			return true;
		}
		return false;
	}

	public void setFishPop(int x, int z, float fishPop)
	{
		ChunkData d = getData(x,z);
		if(d != null && fishPop >= 0)
		{
			d.fishPop = fishPop;
		}
	}

	public int getFishPop(int x, int z)
	{
		ChunkData d = getData(x,z);
		if(d != null)
		{
			return (int)d.fishPop;
		}
		return -1;
	}

	public boolean catchFish(int x, int z)
	{
		ChunkData d = getData(x,z);
		if(d != null)
		{
			if(d.fishPop > 0){
				d.fishPop--;
				return true;
			}
		}
		return false;
	}

	public boolean setLastVisted(int x, int z)
	{
		ChunkData d = getData(x,z);
		if(d != null)
		{
			d.lastVisited = TFC_Time.getTotalTicks();
			return true;
		}
		return false;
	}
}
