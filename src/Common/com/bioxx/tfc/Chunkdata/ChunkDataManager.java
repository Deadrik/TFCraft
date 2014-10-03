package com.bioxx.tfc.Chunkdata;

import java.util.HashMap;

import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.api.TFCOptions;

public class ChunkDataManager 
{
	private World world;

	private HashMap<Chunk,ChunkData> chunkmap = new HashMap<Chunk,ChunkData>();
	private HashMap<Chunk,ChunkData> chunkmapunload = new HashMap<Chunk,ChunkData>();

	public ChunkDataManager(World world)
	{
		this.world = world;
	}

	public ChunkData removeData(int x, int z)
	{
		Chunk c = world.getChunkFromChunkCoords(x, z);
		if(chunkmap.containsKey(c))
		{
			chunkmapunload.put(c, chunkmap.get(c));
			chunkmap.remove(c);
			return (ChunkData) chunkmapunload.get(c);
		}
		else if(chunkmapunload.containsKey(c))
		{
			chunkmapunload.remove(c);
		}
		return null;
	}

	public void addData(Chunk c, ChunkData cd)
	{
		chunkmap.put(c, cd);
	}

	public ChunkData getData(int x, int z)
	{
		Chunk c = world.getChunkFromChunkCoords(x, z);
		if(chunkmap.containsKey(c))
			return (ChunkData) chunkmap.get(c);
		else if(chunkmapunload.containsKey(c))
		{
			ChunkData cd = (ChunkData) chunkmapunload.get(c);
			chunkmapunload.remove(c);
			return cd;
		}
		else return null;
	}

	public ChunkData getData(Chunk c)
	{
		if(chunkmap.containsKey(c))
			return (ChunkData) chunkmap.get(c);
		else if(chunkmapunload.containsKey(c))
		{
			ChunkData cd = (ChunkData) chunkmapunload.get(c);
			chunkmapunload.remove(c);
			return cd;
		}
		else return null;
	}

	public boolean hasData(Chunk c)
	{
		if(chunkmap.containsKey(c))
			return true;
		return false;
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
