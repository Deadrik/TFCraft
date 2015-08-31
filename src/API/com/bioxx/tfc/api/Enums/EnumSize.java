package com.bioxx.tfc.api.Enums;

public enum EnumSize 
{
	TINY("Tiny", /*size*/64),
	
	VERYSMALL("Very Small", /*size*/32),
	
	SMALL("Small", /*size*/16),
	
	MEDIUM("Medium", /*size*/8),
	
	LARGE("Large", /*size*/4),
	
	VERYLARGE("Very Large", /*size*/2),
	
	HUGE("Huge", /*size*/1);

	public final int stackSize;
	private final String name;
	private static final EnumSize SIZES[] = new EnumSize[] {
		TINY, VERYSMALL, SMALL, MEDIUM, LARGE, VERYLARGE, HUGE};

	private EnumSize(String s, int i)
	{
		name = s;
		stackSize = i;
	}
	
	public String getName()
	{
		return name;
	}
}
