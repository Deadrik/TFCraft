package com.bioxx.tfc.api.Enums;

public enum EnumWeight 
{
	LIGHT("Light", /*size*/4),
	
	MEDIUM("Medium", /*size*/2),
	
	HEAVY("Heavy", /*size*/1);

	public final int multiplier;
	private final String name;
	private static final EnumWeight WEIGHTS[] = new EnumWeight[] {
		LIGHT, MEDIUM, HEAVY};

	private EnumWeight(String s, int i)
	{
		name = s;
		multiplier = i;
	}
	
	public String getName()
	{
		return name;
	}
}
