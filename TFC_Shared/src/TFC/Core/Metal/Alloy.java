package TFC.Core.Metal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import TFC.API.Metal;

public class Alloy 
{
	public List<AlloyMetal> AlloyIngred;
	public Metal outputType;
	public int outputAmount;
	EnumTier furnaceTier;
	
	public Alloy(Metal type, EnumTier tier)
	{
		AlloyIngred = new ArrayList<AlloyMetal>();
		outputType = type;
		outputAmount = 0;
		furnaceTier = tier;
	}
	
	public Alloy(Metal type, int am)
	{
		AlloyIngred = new ArrayList<AlloyMetal>();
		outputType = type;
		outputAmount = am;
	}
	
	public void addIngred(AlloyMetal am)
	{
		AlloyIngred.add(am);
	}
	
	public void addIngred(Metal e, int m)
	{
		AlloyIngred.add(new AlloyMetal(e, m));
	}

	public void addIngred(Metal e, int min, int max)
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
		{
			return new Alloy(this.outputType, amount);
		}
	}
	
	public boolean searchForAlloyMetal(AlloyMetal am)
	{
		Iterator<AlloyMetal> iter = AlloyIngred.iterator();
		boolean hasMetal = false;
		while(iter.hasNext())
		{
			AlloyMetalCompare amc = (AlloyMetalCompare) iter.next();
			if(amc.compare(am))
				return true;
		}
		return false;
	}
	
	public enum EnumTier
	{
		TierI(1),//Pit Kiln
		TierII(2),//Beehive Kiln
		TierIII(3),//Bloomery
		TierIV(4),//Blast Furnace
		TierV(5), //Crucible
		TierVI(6), TierVII(7), TierVIII(8), TierIX(9), TierX(10);
		
		public int tier;
		
		EnumTier(int t)
		{
			tier = t;
		}
	}
}
