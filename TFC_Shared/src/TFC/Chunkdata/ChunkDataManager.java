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

import TFC.Core.TFC_Time;

import net.minecraft.src.ModLoader;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TerraFirmaCraft;
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
	
	public static boolean addProtection(int x, int z, int amount)
	{
		synchronized(chunkmap)
		{
			ChunkData d = (ChunkData) chunkmap.get(x + "," + z);
			if(d != null)
			{
				if(d.spawnProtection < 4320)
					d.spawnProtection += amount;
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
	
	@Deprecated
	public static ChunkData getChunkData(World world, int x, int z)
	{
		FileInputStream fin;
		try 
		{
			String path = "/" + world.getWorldInfo().getWorldName();

			if(!ModLoader.getMinecraftServerInstance().isDedicatedServer())
				path = "\\saves\\" + world.getWorldInfo().getWorldName();

			File file = new File(TerraFirmaCraft.proxy.getMinecraftDir(), path + "\\chunkmanager\\" + x + "," + z + ".cm");

			if(file.canRead())
			{
				fin = new FileInputStream(file);
				DataInputStream di = new DataInputStream(fin);
				NBTTagCompound tag = new NBTTagCompound();
				tag = (NBTTagCompound) NBTTagCompound.readNamedTag(di);
				return new ChunkData(tag);
			}
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	@Deprecated
	public static void saveChunkData(World world, ChunkData data)
	{
		FileOutputStream fout;
		try 
		{
			String path = "/" + world.getWorldInfo().getWorldName();

			if(!ModLoader.getMinecraftServerInstance().isDedicatedServer())
				path = "\\saves\\" + world.getWorldInfo().getWorldName();

			File file = new File(TerraFirmaCraft.proxy.getMinecraftDir(), path + "\\chunkmanager\\" + data.chunkX + "," + data.chunkZ + ".cm");

			if (file.getParentFile() != null)
			{
				file.getParentFile().mkdirs();
			}

			if (!file.exists() && !file.createNewFile())
			{
				return;
			}

			if(file.canWrite())
			{
				//fout = new FileOutputStream(path + "/chunkmanager/" + data.chunkX + "," + data.chunkZ + ".cm");
				fout = new FileOutputStream(file);
				DataOutputStream dout = new DataOutputStream(fout);
				NBTTagCompound tag = data.getTag();
				tag.writeNamedTag(tag, dout);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
