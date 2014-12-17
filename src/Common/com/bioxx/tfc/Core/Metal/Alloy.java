package com.bioxx.tfc.Core.Metal;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import com.bioxx.tfc.api.Metal;

public class Alloy 
{
	public List<AlloyMetal> AlloyIngred;
	public Metal outputType;
	public float outputAmount;
	EnumTier furnaceTier;

	public Alloy(Metal type, EnumTier tier)
	{
		this();
		outputType = type;
		outputAmount = 0;
		furnaceTier = tier;
	}

	public Alloy(Metal type, float am)
	{
		this();
		outputType = type;
		outputAmount = am;
	}

	public Alloy()
	{
		AlloyIngred = new ArrayList<AlloyMetal>();
	}

	public void addIngred(AlloyMetal am)
	{
		AlloyIngred.add(am);
	}

	public void addIngred(Metal e, float m)
	{
		AlloyIngred.add(new AlloyMetal(e, m));
	}

	public void addIngred(Metal e, float min, float max)
	{
		AlloyIngred.add(new AlloyMetalCompare(e, min, max));
	}

	public boolean matches(Alloy a)
	{
		Iterator<AlloyMetal> iter = a.AlloyIngred.iterator();
		boolean matches = true;
		while(iter.hasNext() && matches == true)
		{
			AlloyMetal am = iter.next();
			matches = searchForAlloyMetal(am);
		}
		return matches;
	}

	public Alloy matches(List<AlloyMetal> a)
	{
		Iterator<AlloyMetal> iter = a.iterator();
		boolean matches = true;
		int amount = 0;
		while(iter.hasNext() && matches == true)
		{
			AlloyMetal am = iter.next();
			matches = searchForAlloyMetal(am);
			amount += am.metal;
		}

		if(!matches)
			return null;
		else
			return new Alloy(this.outputType, amount);
	}

	public boolean searchForAlloyMetal(AlloyMetal am)
	{
		Iterator<AlloyMetal> iter = AlloyIngred.iterator();
		while(iter.hasNext())
		{
			AlloyMetalCompare amc = (AlloyMetalCompare) iter.next();
			if(amc.compare(am))
				return true;
		}
		return false;
	}

	public float getPercentForMetal(Metal m)
	{
		Iterator<AlloyMetal> iter = AlloyIngred.iterator();
		AlloyMetal am = new AlloyMetal(m, -1);
		while(iter.hasNext())
		{
			AlloyMetal amc = iter.next();
			if(amc.metalType == m)
				return amc.metal;
		}
		return 0;
	}

	public enum EnumTier
	{
		TierI(1, "Pit Kiln"),
		TierII(2, "Beehive Kiln"),
		TierIII(3, "Bloomery"),
		TierIV(4, "Blast Furnace"),
		TierV(5, "Crucible"),
		TierVI(6), TierVII(7), TierVIII(8), TierIX(9), TierX(10);

		public final int tier;
		public final String name;

		EnumTier(int t)
		{
			tier = t;
            name = name();
		}

        EnumTier(int t, String n)
        {
            tier = t;
            name = n;
        }

        @Override
        public String toString()
        {
            return name;
        }
    }

	public void toPacket(DataOutputStream dos)
	{
		try
		{
			if(outputType != null)
				dos.writeUTF(outputType.Name);
			else
				dos.writeUTF("Unknown");

			dos.writeFloat(outputAmount);
			dos.writeInt(AlloyIngred.size());
			for(int i = 0; i < AlloyIngred.size(); i++)
			{
				AlloyMetal am = AlloyIngred.get(i);
				dos.writeUTF(am.metalType.Name);
				dos.writeFloat(am.metal);
			}
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	public Alloy fromPacket(DataInputStream dis)
	{
		try
		{
			outputType = MetalRegistry.instance.getMetalFromString(dis.readUTF());
			outputAmount = dis.readFloat();
			int size = dis.readInt();
			for(int i = 0; i < size; i++)
			{
				AlloyMetal am = new AlloyMetal(MetalRegistry.instance.getMetalFromString(dis.readUTF()), dis.readFloat());
				this.AlloyIngred.add(am);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return this;
	}

	public void toNBT(NBTTagCompound nbt)
	{
		nbt.setString("outputType", outputType.Name);
		nbt.setFloat("outputAmount", outputAmount);
		NBTTagList nbtlist = new NBTTagList();
		for(int i = 0; i < AlloyIngred.size(); i++)
		{
			NBTTagCompound nbt1 = new NBTTagCompound();
			AlloyMetal am = AlloyIngred.get(i);
			nbt1.setString("metalType", am.metalType.Name);
			nbt1.setFloat("amount", am.metal);
			nbtlist.appendTag(nbt1);
		}
		nbt.setTag("metalList", nbtlist);
	}

	public Alloy fromNBT(NBTTagCompound nbt)
	{
		outputType = MetalRegistry.instance.getMetalFromString(nbt.getString("outputType"));
		outputAmount = nbt.getFloat("outputAmount");
		NBTTagList nbtlist = nbt.getTagList("metalList", 10);
		for(int i = 0; i < nbtlist.tagCount(); i++)
		{
			NBTTagCompound nbt1 = nbtlist.getCompoundTagAt(i);
			AlloyMetal am = new AlloyMetal(MetalRegistry.instance.getMetalFromString(nbt1.getString("metalType")), nbt1.getFloat("amount"));
			this.AlloyIngred.add(am);
		}
		return this;
	}

    public EnumTier getFurnaceTier()
    {
        return furnaceTier;
    }
}
