package TFC.Core.Alloy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import TFC.API.Enums.EnumMetalType;

public class AlloyManager 
{
	public static AlloyManager instance;;
	
	public List<Alloy> Alloys;
	
	static
	{
		instance = new AlloyManager();
		Alloy Bronze = new Alloy(EnumMetalType.BRONZE);
		Bronze.addIngred(EnumMetalType.COPPER, 88, 92);
		Bronze.addIngred(EnumMetalType.TIN, 8, 12);
		instance.addAlloy(Bronze);
	}
	
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
	
	public EnumMetalType matchesAlloy(List<AlloyMetal> ingred)
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
