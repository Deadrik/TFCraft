package com.bioxx.tfc.Core.Metal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.minecraft.item.Item;

import com.bioxx.tfc.api.Metal;

public class MetalRegistry 
{
	public static MetalRegistry instance = new MetalRegistry();
	
	private Map<String, Metal> hash;
	
	public MetalRegistry()
	{
		hash = new HashMap<String, Metal>();
	}
	
	//Returns true if the metal was added or false if a metal with a similar name already exists;
	public boolean addMetal(Metal m, Alloy.EnumTier soloTier)
	{
		if(hash.containsKey(m.name))
			return false;

		hash.put(m.name, m);
		
		Alloy alloy = new Alloy(m, soloTier);
		alloy.addIngred(m, 99, 100);
		AlloyManager.INSTANCE.addAlloy(alloy);
		
		return true;
	}
	
	public Metal getMetalFromItem(Item i)
	{
		Iterator<Metal> iter = hash.values().iterator();
		while(iter.hasNext())
		{
			Metal m = iter.next();
			if(m.ingot == i || m.meltedItem == i)
				return m;
		}
		
		return null;
	}
	
	public Metal getMetalFromString(String s)
	{
		if(hash.containsKey(s))
			return hash.get(s);
		else return null;
	}
}
