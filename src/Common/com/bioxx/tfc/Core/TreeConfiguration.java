package com.bioxx.tfc.Core;

public class TreeConfiguration 
{
	public String name;
	/**
	 * The Tree Index. This is used for assigning textures, creating items, and 
	 * choosing the correct tree schematics during world generation.
	 */
	public int index = -1;
	/**
	 * Minimum Allowed Moisture
	 */
	public float minRain;
	/**
	 * Maximum Allowed Moisture
	 */
	public float maxRain;
	/**
	 * Minimum Allowed Temperature
	 */
	public float minTemp;
	/**
	 * Maximum Allowed Temperature
	 */
	public float maxTemp;

	public float minEVT;
	public float maxEVT;
	public boolean isEvergreen;

	public TreeConfiguration(String n, int i, float minR, float maxR, float minT, float maxT, float minEVT, float maxEVT, boolean eg)
	{
		name = n;
		index = i;
		minRain = minR;
		maxRain = maxR;
		minTemp = minT;
		maxTemp = maxT;
	}
}
