package TFC.Chunkdata;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import TFC.Core.TFC_Time;

public class ChunkData 
{
	public int chunkX;
	public int chunkZ;
	public long lastVisited;
	public int spawnProtection;
	public List<String> oreList1;
	public List<String> oreList2;
	public List<String> oreList3;
	public int[] heightmap;

	public ChunkData()
	{
		oreList1 = new ArrayList<String>();
		oreList2 = new ArrayList<String>();
		oreList3 = new ArrayList<String>();
		heightmap = new int[256];
	}

	public ChunkData(NBTTagCompound tag)
	{
		oreList1 = new ArrayList<String>();
		oreList2 = new ArrayList<String>();
		oreList3 = new ArrayList<String>();
		chunkX = tag.getInteger("chunkX");
		chunkZ = tag.getInteger("chunkZ");
		lastVisited = tag.getLong("lastVisited");
		spawnProtection = tag.getInteger("spawnProtection");

		long visit = (TFC_Time.getTotalTicks() - lastVisited) / TFC_Time.hourLength;
		spawnProtection -= visit;
		if(spawnProtection < -24) {
			spawnProtection = -24;
		}

		if(tag.hasKey("OreList1"))
		{
			NBTTagCompound list = tag.getCompoundTag("OreList1");

			for (int i = 0; i < list.getByte("Ore Count"); i++)
			{
				oreList1.add(list.getString("Ore"+i));
			}
		}

		if(tag.hasKey("OreList2"))
		{
			NBTTagCompound list = tag.getCompoundTag("OreList2");

			for (int i = 0; i < list.getByte("Ore Count"); i++)
			{
				oreList2.add(list.getString("Ore"+i));
			}
		}

		if(tag.hasKey("OreList3"))
		{
			NBTTagCompound list = tag.getCompoundTag("OreList3");

			for (int i = 0; i < list.getByte("Ore Count"); i++)
			{
				oreList3.add(list.getString("Ore"+i));
			}
		}

		lastVisited = TFC_Time.getTotalTicks();

		heightmap = tag.getIntArray("heightmap");
		if(heightmap.length == 0){
			heightmap = new int[256];
		}
	}

	public NBTTagCompound getTag()
	{
		NBTTagCompound tag = new NBTTagCompound("Spawn Protection");

		tag.setInteger("chunkX", chunkX);
		tag.setInteger("chunkZ", chunkZ);
		long visit = (TFC_Time.getTotalTicks() - lastVisited) / TFC_Time.hourLength;
		spawnProtection -= visit;
		if(spawnProtection < -24) {
			spawnProtection = -24;
		}
		tag.setInteger("spawnProtection", spawnProtection);
		tag.setLong("lastVisited", lastVisited);
		tag.setIntArray("heightmap", heightmap);

		if(oreList1.size() > 0)
		{
			NBTTagCompound list = new NBTTagCompound("OreList1");
			list.setByte("Ore Count", (byte) oreList1.size());
			for (int i = 0; i < oreList1.size(); i++)
			{
				list.setString("Ore"+i, oreList1.get(i));
			}
		}	
		if(oreList2.size() > 0)
		{
			NBTTagCompound list = new NBTTagCompound("OreList2");
			list.setByte("Ore Count", (byte) oreList2.size());
			for (int i = 0; i < oreList2.size(); i++)
			{
				list.setString("Ore"+i, oreList2.get(i));
			}
		}	
		if(oreList3.size() > 0)
		{
			NBTTagCompound list = new NBTTagCompound("OreList3");
			list.setByte("Ore Count", (byte) oreList3.size());
			for (int i = 0; i < oreList3.size(); i++)
			{
				list.setString("Ore"+i, oreList3.get(i));
			}
		}	
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

	public int getSpawnProtectionWithUpdate()
	{
		long visit = (TFC_Time.getTotalTicks() - lastVisited) / TFC_Time.hourLength;
		spawnProtection -= visit;
		if(spawnProtection < -24) {
			spawnProtection = -24;
		}

		lastVisited = TFC_Time.getTotalTicks();

		if(spawnProtection > 4320) {
			spawnProtection = 4320;
		}

		return spawnProtection;
	}

	public void setSpawnProtectionWithUpdate(int amount)
	{
		long visit = (TFC_Time.getTotalTicks() - lastVisited) / TFC_Time.hourLength;
		spawnProtection -= visit;

		if(spawnProtection < -24) {
			spawnProtection = -24;
		}

		spawnProtection += amount;

		if(spawnProtection > 4320) {
			spawnProtection = 4320;
		}

		lastVisited = TFC_Time.getTotalTicks();
	}
}
