package TFC.Chunkdata;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import TFC.Core.TFC_Settings;
import TFC.Core.TFC_Time;

public class ChunkDataManager 
{
	public static Map chunkmap = Collections.synchronizedMap(new HashMap());
	
	public static void removeData(int x, int z)
	{
		synchronized(chunkmap)
		{
			if(chunkmap.containsKey(x + "," + z))
			{
				chunkmap.remove(x + "," + z);
			}
			else
			{
				System.out.println("Tried to unload chunkdata from the chunkmap that didnt exist at " + x + "," + z);
			}
		}
	}
	
	public static ChunkData getData(int x, int z)
	{
		synchronized(chunkmap)
		{
			if(chunkmap.containsKey(x + "," + z))
			{
				return (ChunkData) chunkmap.get(x + "," + z);
			}
			else return null;
		}
	}
	
	public static boolean addProtection(int x, int z, int amount)
	{
		synchronized(chunkmap)
		{
			ChunkData d = (ChunkData) chunkmap.get(x + "," + z);
			if(d != null)
			{
				if(d.spawnProtection < 24*TFC_Time.daysInMonth*TFC_Settings.maxProtectionMonths)
					d.setSpawnProtectionWithUpdate(amount);
				return true;
			}
		}
		return false;
	}
	
	public static boolean setLastVisted(int x, int z)
	{
		synchronized(chunkmap)
		{
			ChunkData d = (ChunkData) chunkmap.get(x + "," + z);
			if(d != null)
			{
				d.lastVisited = TFC_Time.getTotalTicks();
				return true;
			}
		}
		return false;
	}
}
