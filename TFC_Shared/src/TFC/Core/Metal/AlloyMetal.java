package TFC.Core.Metal;

import TFC.API.Metal;

public class AlloyMetal 
{
	public Metal metalType;
	/**
	 * Value represented as a percentage.
	 */
	public float metal;
	
	public AlloyMetal(Metal e, float m)
	{
		metalType = e;
		metal = m;
	}
}
