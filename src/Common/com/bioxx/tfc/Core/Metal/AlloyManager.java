package com.bioxx.tfc.Core.Metal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.bioxx.tfc.api.Metal;

public class AlloyManager 
{
	public static final AlloyManager INSTANCE = new AlloyManager();
	
	public List<Alloy> alloys;
	
	
	public AlloyManager()
	{
		alloys = new ArrayList<Alloy>();
	}
	
	public void addAlloy(Alloy a)
	{
		alloys.add(a);
	}
	
	public boolean matches(List<AlloyMetal> ingred)
	{
		Iterator<Alloy> iter = alloys.iterator();
		Alloy match = null;
		while(iter.hasNext() && match == null)
		{
			match = iter.next();
			match = match.matches(ingred);
		}
		return match != null;
	}
	
	public Metal matchesAlloy(List<AlloyMetal> ingred, Alloy.EnumTier furnaceTier)
	{
		Iterator<Alloy> iter = alloys.iterator();
		Alloy match = null;
		while (iter.hasNext())
		{
			match = iter.next();
			if(furnaceTier.tier >= match.furnaceTier.tier)
				match = match.matches(ingred);
			else
				match = null;
			
			if(match != null)
				return match.outputType;
		}
		return null;
	}
}
