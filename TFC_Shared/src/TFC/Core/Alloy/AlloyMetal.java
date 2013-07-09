package TFC.Core.Alloy;

import TFC.API.Enums.EnumMetalType;

public class AlloyMetal 
{
	protected EnumMetalType metalType;
	int metal;
	
	public AlloyMetal(EnumMetalType e, int m)
	{
		metalType = e;
		metal = m;
	}
}
