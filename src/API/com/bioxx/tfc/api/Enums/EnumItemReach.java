package com.bioxx.tfc.api.Enums;

public enum EnumItemReach 
{
	SHORT("Short", /*reach*/0.75),
	
	MEDIUM("Medium", /*reach*/1),
	
	FAR("Far", /*reach*/1.25);

	public final double multiplier;
	private final String name;
	private static final EnumItemReach DISTANCES[] = new EnumItemReach[] {
		SHORT, MEDIUM, FAR};

	private EnumItemReach(String s, double i)
	{
		name = s;
		multiplier = i;
	}
	
	public String getName()
	{
		return name;
	}
}
