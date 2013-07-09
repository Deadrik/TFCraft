package TFC.Core.Alloy;

import TFC.API.Enums.EnumMetalType;

public class AlloyMetalCompare extends AlloyMetal
{
	float metalMin;
	float metalMax;
	
	public AlloyMetalCompare(EnumMetalType e, int min)
	{
		super(e, min);
	}
	
	public AlloyMetalCompare(EnumMetalType e, int min, int max)
	{
		super(e, min);
		metalType = e;
		metalMin = min;
		metalMax = max;
	}
	
	public boolean compare(AlloyMetal b)
	{
		if(this.metalType == b.metalType && b.metal > this.metalMin && b.metal < this.metalMax)
			return true;
			
		return false;
	}
}
