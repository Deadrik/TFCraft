package TFC.Chunkdata;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import TFC.TerraFirmaCraft;
import TFC.Core.TFC_Time;

import net.minecraft.src.ModLoader;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

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
				if(d.spawnProtection < 24*TFC_Time.daysInMonth*6)
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
