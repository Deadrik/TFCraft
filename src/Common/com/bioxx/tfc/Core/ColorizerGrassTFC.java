package com.bioxx.tfc.Core;

public class ColorizerGrassTFC
{
	/** Color buffer for grass */
	private static int[] grassBuffer = new int[65536];

	public static void setGrassBiomeColorizer(int[] par0ArrayOfInteger)
	{
		grassBuffer = par0ArrayOfInteger.clone();
	}

	/*
	 * Gets grass color from temperature and humidity. Args: temperature, humidity
	 *
	public static int getGrassColor(double par0, double par2)
	{
		//ratio of 75:129:40 is good for R:G:B, which = 0.58 : 1 : 0.33; 0.58 * 0.58 ~= 0.33 for warmish temperatures

		//ratio of 161:177:30 good for cold = 0.91 : 1 : 0.17
		double temp = par0;
		double blue = ((par2 * par0 * 1.9) + 0.99)/4d;
		double red = Math.sqrt(blue);
		temp = Math.max(temp,0.5);
		if(temp>=0.5 && temp <= 0.6)
		{
			blue /= (2 - (temp-0.5)*10);
			red += 0.33 * (1 - (temp-0.5)*10);
		}
		int var4 = (int)(red * 255.0D / (1.8D+(par2/2)));
		int var5 = (int)(blue * 255.0D / (1.8D+par2));
		int var7 = (int)( 255.0D / (1.8D));
		return (var4 << 16 )| (var7 << 8 )| var5;
	}*/

	/**
	 * Gets grass color from temperature and humidity. Args: temperature, humidity
	 */
	public static int getGrassColor(double par0, double par2)
	{
		par2 *= par0;
		int var4 = (int)((1.0D - par0) * 255.0D);
		int var5 = (int)((1.0D - par2) * 255.0D);
		return grassBuffer[var5 << 8 | var4];
	}

	public static int getGrassDead()
	{
		return 5198608;
	}
}
