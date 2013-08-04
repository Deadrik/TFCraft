package TFC.Core.Metal;

import TFC.API.Metal;

public class AlloyMetalCompare extends AlloyMetal
{
	float metalMin;
	float metalMax;
	
	public AlloyMetalCompare(Metal e, int min)
	{
		super(e, min);
	}
	
	public AlloyMetalCompare(Metal e, int min, int max)
	{
		super(e, min);
		metalType = e;
		metalMin = min;
		metalMax = max;
	}
	
	public boolean compare(AlloyMetal b)
	{
		if(this.metalType == b.metalType && b.metal >= this.metalMin && b.metal <= this.metalMax)
			return true;
			
		return false;
	}
}
