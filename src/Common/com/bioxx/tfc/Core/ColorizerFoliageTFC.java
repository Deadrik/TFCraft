package com.bioxx.tfc.Core;

public class ColorizerFoliageTFC
{
	/** Color buffer for foliage */
	private static int[] foliageBuffer = new int[65536];

	public static void setFoliageBiomeColorizer(int[] par0ArrayOfInteger)
	{
		foliageBuffer = par0ArrayOfInteger.clone();
	}

	/**
	 * Gets foliage color from temperature and humidity. Args: temperature, humidity
	 */
	public static int getFoliageColor(double par0, double par2)
	{
		par2 *= par0;
		int var4 = (int)((1.0D - par0) * 255.0D);
		int var5 = (int)((1.0D - par2) * 255.0D);
		return foliageBuffer[var5 << 8 | var4];
	}

	/**
	 * Gets the foliage color for pine type (metadata 1) trees
	 */
	public static int getFoliageColorPine()
	{
		return 6396257;
	}

	/**
	 * Gets the foliage color for birch type (metadata 2) trees
	 */
	public static int getFoliageColorBirch()
	{
		return 8431445;
	}

	public static int getFoliageColorBasic()
	{
		return 4764952;
	}

	public static int getFoliageDead()
	{
		return 9004839;
	}

	public static int getFoliageOrange()
	{
		return 15765504;
	}

	public static int getFoliageYellow()
	{
		return 16763904;
	}

	public static int getFoliageRed()
	{
		return 9312280;
	}
}
