package TFC.Core.Metal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import TFC.API.Metal;

public class AlloyManager 
{
	public static AlloyManager instance = new AlloyManager();
	
	public List<Alloy> Alloys;
	
	
	public AlloyManager()
	{
		Alloys = new ArrayList<Alloy>();
	}
	
	public void addAlloy(Alloy a)
	{
		Alloys.add(a);
	}
	
	public boolean matches(List<AlloyMetal> ingred)
	{
		Iterator<Alloy> iter = Alloys.iterator();
		Alloy match = null;
		while(iter.hasNext() && match == null)
		{
			match = iter.next();
			match = match.matches(ingred);
		}
		return match != null;
	}
	
	public Metal matchesAlloy(List<AlloyMetal> ingred)
	{
		Iterator<Alloy> iter = Alloys.iterator();
		Alloy match = null;
		while(iter.hasNext() && match == null)
		{
			match = iter.next();
			match = match.matches(ingred);
		}
		return match.outputType;
	}
}
