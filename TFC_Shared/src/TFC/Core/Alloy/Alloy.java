package TFC.Core.Alloy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import TFC.API.Enums.EnumMetalType;

public class Alloy 
{
	public List<AlloyMetal> AlloyIngred;
	public EnumMetalType outputType;
	public int outputAmount;
	
	public Alloy(EnumMetalType type)
	{
		AlloyIngred = new ArrayList<AlloyMetal>();
		outputType = type;
		outputAmount = 0;
	}
	
	public Alloy(EnumMetalType type, int am)
	{
		AlloyIngred = new ArrayList<AlloyMetal>();
		outputType = type;
		outputAmount = am;
	}
	
	public void addIngred(AlloyMetal am)
	{
		AlloyIngred.add(am);
	}
	
	public void addIngred(EnumMetalType e, int m)
	{
		AlloyIngred.add(new AlloyMetal(e, m));
	}

	public void addIngred(EnumMetalType e, int min, int max)
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
}
